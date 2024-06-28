package com.yutsuki.serverApi.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.file.Path;

@Getter
@Setter
@ToString
@Builder
public class UploadFileResponse {
    private Path urlPath;
    private String imageName;
}
