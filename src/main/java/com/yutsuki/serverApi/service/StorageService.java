package com.yutsuki.serverApi.service;

import com.yutsuki.serverApi.common.ApiProperties;
import com.yutsuki.serverApi.common.ResponseUtil;
import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.exception.FileUploadException;
import com.yutsuki.serverApi.model.response.UploadFileResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Slf4j
public class StorageService {
    @javax.annotation.Resource
    private ApiProperties apiProperties;
    @javax.annotation.Resource
    private SecurityService securityService;

    public ResponseEntity<?> store(MultipartFile file) throws BaseException {
        try {
            if (file.isEmpty()) {
                log.warn("StorageService::(block). File is empty. {}", file.getOriginalFilename());
                throw FileUploadException.fileIsEmpty();
            }

//            if (file.getSize() > 1048576 * 2) {
//                log.warn("StorageService::(block). File size is max size. {}", file);
//                throw FileUploadException.fileSizeIsMax();
//            }

            String contentType = file.getContentType();
            if (ObjectUtils.isEmpty(contentType)){
                log.warn("StorageService::(block). File content type is empty. {}", file);
                throw FileUploadException.fileIsEmpty();
            }

            List<String> supportType = Arrays.asList("image/png", "image/jpeg");
            if (!supportType.contains(contentType)) {
                log.warn("StorageService::(block). File content type is not support. {}", file);
                throw FileUploadException.fileContentTypeIsNotSupport();
            }
            if (Objects.isNull(file.getOriginalFilename())) {
                log.warn("StorageService::(block). File name is null. {}", file);
                throw FileUploadException.originalFileNameIsEmpty();
            }

            if (!Files.exists(apiProperties.getUploadLocation())) {
                init();
            }
            Account account = securityService.getUserDetail();

            String fileName = UUID.randomUUID() + "_" + account.getName() +"."+ file.getContentType().split("/")[1];
            Path destinationFile = this.apiProperties.getUploadLocation().resolve(
                    fileName).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.apiProperties.getUploadLocation().toAbsolutePath())) {
                log.warn("StorageService::(block). Cannot store file outside current directory. {}", file);
                throw FileUploadException.fileNotUpload();
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                Path path = Paths.get(this.apiProperties.getUploadLocation().toString(), fileName);
                UploadFileResponse response = UploadFileResponse.builder()
                        .imageName(fileName)
                        .urlPath(path)
                        .build();
                return ResponseUtil.success(response);
            }
        } catch (IOException e) {
            log.error("StorageService::(block). Cannot store file. {}", file, e);
            throw FileUploadException.fileNotUpload(e.getMessage());
        }
    }


    public Path load(String filename) {
        return apiProperties.getUploadLocation().resolve(filename);
    }

    public Resource loadAsResource(String filename) throws FileUploadException {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.error("StorageService::(block). Could not read file: {}", filename);
                throw FileUploadException.cannotReadFile();
            }
        } catch (MalformedURLException e) {
            log.error("StorageService::(block). Failed to read stored files", e);
            throw FileUploadException.failReadStoredFiles();
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(apiProperties.getUploadLocation().toFile());
    }

    public void init() {
        try {
            Files.createDirectories(apiProperties.getUploadLocation());
        } catch (IOException e) {
            log.error("StorageService::(block). Could not initialize storage error: {}", e.getMessage());
        }
    }
}
