package com.yutsuki.serverApi.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Setter

public class ApiProperties {
    @Value("${storage.location}")
    private String uploadLocation;
    @Value("${server.resetTokenExpire}")
    @Getter
    private Long resetTokenExpire;
    @Value("${storage.compressQuality}")
    @Getter
    private Float compressQuality;

    public Path getUploadLocation() {
        return Paths.get(uploadLocation);
    }

}
