package com.yutsuki.serverApi.service;

import com.yutsuki.serverApi.common.ApiProperties;
import com.yutsuki.serverApi.common.ResponseUtil;
import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.exception.FileUploadException;
import com.yutsuki.serverApi.model.request.UploadFileRequest;
import com.yutsuki.serverApi.model.response.UploadFileResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.ObjectUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

@Service
@Slf4j
@AllArgsConstructor
public class StorageService {
    private final ApiProperties apiProperties;

    public ResponseEntity<?> store(UploadFileRequest request) throws BaseException {
        try {
            if (request.getFile().isEmpty()) {
                log.warn("StorageService::(block). File is empty. {}", request.getFile().getOriginalFilename());
                throw FileUploadException.fileIsEmpty();
            }
            String contentType = request.getFile().getContentType();
            if (ObjectUtils.isEmpty(contentType)) {
                log.warn("StorageService::(block). File content type is empty. {}", request.getFile());
                throw FileUploadException.fileIsEmpty();
            }

            List<String> supportType = Arrays.asList("image/png", "image/jpeg","image/webp");
            if (!supportType.contains(contentType)) {
                log.warn("StorageService::(block). File content type is not support. {}", request.getFile());
                throw FileUploadException.fileContentTypeIsNotSupport();
            }
            if (Objects.isNull(request.getFile().getOriginalFilename())) {
                log.warn("StorageService::(block). File name is null. {}", request.getFile());
                throw FileUploadException.originalFileNameIsEmpty();
            }

            if (!Files.exists(apiProperties.getUploadLocation())) {
                init();
            }
            String fileName = UUID.randomUUID() + "." + request.getFile().getContentType().split("/")[1];
            Path destinationFile = this.apiProperties.getUploadLocation().resolve(
                    fileName).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.apiProperties.getUploadLocation().toAbsolutePath())) {
                log.warn("StorageService::(block). Cannot store file outside current directory. {}", request.getFile());
                throw FileUploadException.fileNotUpload();
            }

            if (Boolean.TRUE.equals(request.isCompress())) {
                byte[] compressedImageBytes = compressImage(request.getFile().getBytes(), contentType);
                try (InputStream inputStream = new ByteArrayInputStream(compressedImageBytes)) {
                    Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                    Path path = Paths.get(this.apiProperties.getUploadLocation().toString(), fileName);
                    UploadFileResponse response = UploadFileResponse.builder()
                            .imageName(fileName)
                            .urlPath(path)
                            .size(Files.size(destinationFile))
                            .build();
                    return ResponseUtil.success(response);
                }
            }
            try (InputStream inputStream = request.getFile().getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                Path path = Paths.get(this.apiProperties.getUploadLocation().toString(), fileName);
                UploadFileResponse response = UploadFileResponse.builder()
                        .imageName(fileName)
                        .urlPath(path)
                        .size(Files.size(destinationFile))
                        .build();
                return ResponseUtil.success(response);
            }
        } catch (IOException e) {
            log.error("StorageService::(block). Cannot store file. {}", request.getFile(), e);
            throw FileUploadException.fileNotUpload(e.getMessage());
        }
    }

    private byte[] compressImage(byte[] imageBytes, String contentType) throws IOException {
        String formatName = contentType.split("/")[1];

        if ("webp".equalsIgnoreCase(formatName)) {
            // สำหรับ WebP ให้ส่งคืนข้อมูลเดิมโดยไม่บีบอัด
            log.info("WebP image detected. Skipping compression.");
            return imageBytes;
        }

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        log.info("Content type: {}", contentType);

        ImageWriter writer = ImageIO.getImageWritersByFormatName(formatName).next();

        ImageWriteParam params = writer.getDefaultWriteParam();
        if (params.canWriteCompressed()) {
            params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            params.setCompressionQuality(apiProperties.getCompressQuality());
        }

        try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream)) {
            writer.setOutput(ios);
            writer.write(null, new IIOImage(image, null, null), params);
        }
        writer.dispose();
        return outputStream.toByteArray();
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

    public ResponseEntity<?> delete(String filename) throws BaseException {
        try {
            Path file = load(filename);
            if (!Files.exists(file)) {
                log.warn("StorageService::delete. File does not exist: {}", filename);
                return ResponseEntity.notFound().build();
            }
            Files.delete(file);
            return ResponseUtil.success();
        } catch (IOException e) {
            log.error("StorageService::(block). Could not delete file: {}, error:{}", filename, e.getMessage());
            throw FileUploadException.failDeleteFile();
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
