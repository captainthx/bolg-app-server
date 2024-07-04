package com.yutsuki.serverApi.exception;

public class AccountException extends BaseException {
    public AccountException(String message) {
        super(message);
    }

    public static AccountException accountNotFound() {
        return new AccountException("Account not found.");
    }

    public static AccountException accountListEmpty() {
        return new AccountException("Account list is empty.");
    }

    public static AccountException avatarNameIsEmpty() {
        return new AccountException("Avatar name is empty.");
    }

    public static AccountException resetPasswordTokenExpired() {
        return new AccountException("Reset password token is expired.");
    }

    public static AccountException resetPasswordTokenInvalid() {
        return new AccountException("Reset password token is invalid.");
    }
}
