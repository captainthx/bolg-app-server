package com.yutsuki.serverApi.exception;

public class EmailException extends BaseException {
    public EmailException(String message) {
        super(message);
    }

    public static EmailException templateNotFound() {
        return new EmailException("Email template not found");
    }
}
