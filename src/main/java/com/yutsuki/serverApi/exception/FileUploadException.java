package com.yutsuki.serverApi.exception;

public class FileUploadException extends BaseException {
    public FileUploadException(String message) {
        super(message);
    }

    public static FileUploadException fileIsEmpty() {
        return new FileUploadException("File is empty");
    }

    public static FileUploadException originalFileNameIsEmpty() {
        return new FileUploadException("Original file name is empty");
    }

    public static FileUploadException fileSizeIsMax() {
        return new FileUploadException("File size is max size");
    }

    public static FileUploadException fileContentTypeIsEmpty() {
        return new FileUploadException("File content type is empty");
    }

    public static FileUploadException fileContentTypeIsNotSupport() {
        return new FileUploadException("File content type is not support");
    }

    public static FileUploadException fileNotUpload() {
        return new FileUploadException("File not upload outside current directory");
    }

    public static FileUploadException fileNotUpload(String message) {
        return new FileUploadException(message);
    }

    public static FileUploadException cannotReadFile() {
        return new FileUploadException("Cannot read file");
    }

    public static FileUploadException failReadStoredFiles() {
        return new FileUploadException("Fail to read stored files");
    }

    public static FileUploadException failDeleteFile() {
        return new FileUploadException("Fail to delete file");
    }
}
