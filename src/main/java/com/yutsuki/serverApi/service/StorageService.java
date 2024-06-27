package com.yutsuki.serverApi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Slf4j
public class StorageService {

    @Value("${storage.location}")
    private Path uploadLocation;

    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                log.warn("StorageService::(block). File is empty. {}", file);
                return;
            }
            Path destinationFile = this.uploadLocation.resolve(
                            Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.uploadLocation.toAbsolutePath())) {
                log.warn("StorageService::(block). Cannot store file outside current directory. {}", file);
                return;
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            log.error("StorageService::(block). Cannot store file. {}", file, e);
        }
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.uploadLocation, 1)
                    .filter(path -> !path.equals(this.uploadLocation))
                    .map(this.uploadLocation::relativize);
        } catch (IOException e) {
            log.error("StorageService::(block). Failed to read stored files", e);
        }
        return null;
    }

    public Path load(String filename) {
        return uploadLocation.resolve(filename);
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.error("StorageService::(block). Could not read file: {}", filename);
                return null;
            }
        } catch (MalformedURLException e) {
            log.error("StorageService::(block). Failed to read stored files", e);
        }
        return null;
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(uploadLocation.toFile());
    }

    public void init() {
        try {
            Files.createDirectories(uploadLocation);
        } catch (IOException e) {
            log.error("StorageService::(block). Could not initialize storage", e);
        }
    }
}
