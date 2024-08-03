package com.yutsuki.serverApi.utils;

import java.util.Objects;

public class ValidateUtil {
    private static final String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String ipv4Regex = "^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$";

    public static boolean invalidIpv4(String str) {
        return Objects.isNull(str) || !str.matches(ipv4Regex);
    }

    public static boolean invalidEmail(String str) {
        return Objects.isNull(str) || !str.matches(emailRegex);
    }

    public static boolean invalidUsername(String str) {
        return Objects.isNull(str) || notRange(str, 4, 30);
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
    public static  boolean invalidPostContentLimit(String content) {
        return notRange(content, 1, 4000);
    }

    public static boolean invalidPostImage(String image) {
        return Objects.isNull(image) || notRange(image, 1, 255);
    }
    public static boolean invalidPostImageLimit(String image) {
        return notRange(image, 1, 255);
    }
}
