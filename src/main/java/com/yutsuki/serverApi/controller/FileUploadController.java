package com.yutsuki.serverApi.controller;

import com.yutsuki.serverApi.entity.Account;
import com.yutsuki.serverApi.exception.BaseException;
import com.yutsuki.serverApi.exception.FileUploadException;
import com.yutsuki.serverApi.jwt.UserDetailsImp;
import com.yutsuki.serverApi.model.request.UploadFileRequest;
import com.yutsuki.serverApi.service.SecurityService;
import com.yutsuki.serverApi.service.StorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/upload")
@Slf4j
public class FileUploadController {
    @javax.annotation.Resource
    private StorageService storageService;

    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws BaseException {
        Resource file = storageService.loadAsResource(filename);
        if (ObjectUtils.isEmpty(file)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/jpeg")).body(file);
    }

    @PostMapping()
    public ResponseEntity<?> handleFileUpload(UploadFileRequest request) throws BaseException {
        return storageService.store(request);
    }

    @DeleteMapping
    public ResponseEntity<?>deleteFile(@RequestParam String filename) throws BaseException {
        return storageService.delete(filename);
    }
}
