package com.yutsuki.serverApi.controller;

import com.yutsuki.serverApi.jwt.UserDetailsImp;
import com.yutsuki.serverApi.service.StorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/upload")
@Slf4j
@AllArgsConstructor
public class FileUploadController {

    private final StorageService storageService;

    @GetMapping()
    public String listUploadedFiles(Model model) {
        model.addAttribute("files", storageService.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "serveFile",
                                path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/jpeg")).body(file);
    }

    @PostMapping()
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("uid") Long uid, @AuthenticationPrincipal UserDetailsImp userDetailsImp) {
        log.info("uid {} {}",uid,userDetailsImp);
        log.info("FileUploadController. handleFileUpload. {}", file.getOriginalFilename());
        storageService.store(file);
        return "You successfully uploaded";
    }
}
