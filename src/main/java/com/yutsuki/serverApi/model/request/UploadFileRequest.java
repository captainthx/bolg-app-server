package com.yutsuki.serverApi.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class UploadFileRequest {
    private MultipartFile file;
    private boolean compress = false;
}
