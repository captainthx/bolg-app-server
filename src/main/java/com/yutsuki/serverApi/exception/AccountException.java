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

    public static AccountException avatarUrlIsEmpty() {
        return new AccountException("Avatar url is empty.");
    }
}
