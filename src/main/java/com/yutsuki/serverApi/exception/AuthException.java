package com.yutsuki.serverApi.exception;

public class AuthException extends BaseException {
    public AuthException(String message) {
        super(message);
    }

    public static AuthException unauthenticated() {
        return new AuthException("User is not authenticated.");
    }

    public static AuthException accountNotFound() {
        return new AuthException("Account not found.");
    }

    public static AuthException tokenUnauthorized() {
        return new AuthException("Token unauthorized.");
    }

    public static AuthException tokenExpired() {
        return new AuthException("Token expired.");
    }

    public static AuthException invalidName() {
        return new AuthException("Invalid name!");
    }

    public static AuthException invalidMobile() {
        return new AuthException("Invalid mobile!");
    }

    public static AuthException invalidUsername() {
        return new AuthException("Invalid username!");
    }

    public static AuthException invalidPassword() {
        return new AuthException("Invalid password!");
    }

    public static AuthException invalidRefreshToken() {
        return new AuthException("Invalid refresh token!");
    }
}
