package com.yutsuki.serverApi.utils;

import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

public class ValidateUtil {
    private static final String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String ipv4Regex = "^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$";
    private static final String usernameRegex = "^[a-zA-Z0-9_]{4,20}$";

    public static boolean invalidIpv4(String str) {
        return Objects.isNull(str) || !str.matches(ipv4Regex);
    }

    public static boolean invalidEmail(String str) {
        return Objects.isNull(str) || !str.matches(emailRegex);
    }

    public static boolean invalidUsername(String str) {
        return Objects.isNull(str) || !Pattern.matches(usernameRegex, str);
    }

    public static boolean invalidPassword(String str) {
        return Objects.isNull(str) || notRange(str, 4, 30);
    }

    public static boolean notRange(String str, int min, int max) {
        int len = str.length();
        return len < min || len > max;
    }

    public static boolean invalidPostTitle(String title) {
        return Objects.isNull(title) || notRange(title, 1, 255);
    }

    public static boolean invalidPostTitleLimit(String title) {
        return notRange(title, 1, 255);
    }

    public static boolean invalidPostContent(String content) {
        return Objects.isNull(content) || notRange(content, 1, 4000);
    }

    public static boolean invalidPostContentLimit(String content) {
        return notRange(content, 1, 4000);
    }

    public static boolean invalidPostImage(String image) {
        return Objects.isNull(image) || notRange(image, 1, 255);
    }

    public static boolean invalidImageLimit(String image) {
        return notRange(image, 1, 255);
    }

    public static boolean invalidMobile(String mobile) {
        return Objects.isNull(mobile) || notRange(mobile, 1, 10);
    }

    public static boolean invalidName(String name) {
        return Objects.isNull(name) || notRange(name, 6, 20);
    }

}
