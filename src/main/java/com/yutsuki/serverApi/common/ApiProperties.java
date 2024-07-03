package com.yutsuki.serverApi.common;

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
    @Value("${storage.email}")
    private String emailLocation;

    public Path getUploadLocation() {
        return Paths.get(uploadLocation);
    }

    public String getEmailLocation() {
        return emailLocation;
    }
}
