package com.yutsuki.serverApi.exception;

public class EmailException extends BaseException {
    public EmailException(String message) {
        super(message);
    }

    public static EmailException templateNotFound() {
        return new EmailException("Email template not found");
    }

    public static EmailException sendFailed(String message) {
        return new EmailException("Error sending email : " + message);
    }

    public static EmailException invalidEmail() {
        return new EmailException("Invalid email");
    }
}
