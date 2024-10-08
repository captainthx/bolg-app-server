package com.yutsuki.serverApi.utils;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;


public class Comm {
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.USER_AGENT);
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }


    public static String getDeviceType(String userAgent) {
        String deviceType;

        String iPhone = "iPhone";
        String Android = "Android";
        String Windows = "Windows";
        String Unknown = "Unknown";

        if (userAgent.contains(iPhone)) {
            deviceType = iPhone;
        } else if (userAgent.contains(Android)) {
            deviceType = Android;
        } else if (userAgent.contains(Windows)) {
            deviceType = Windows;
        } else {
            deviceType = Unknown;
        }

        return deviceType;
    }


    public static String randomNumber(int min, int max) {
        int range = (max - min) + 1;
        return String.valueOf((int) Math.floor(Math.random() * range) + min);
    }


    public static String randomSetNumber(int length) {
        return randomNumber((int) Math.pow(10, length - 1), (int) Math.pow(10, length) - 1);
    }

    public static String encodeBase64(String str) {
        return java.util.Base64.getEncoder().encodeToString(str.getBytes());
    }

    public static String decodeBase64(String str) {
        byte[] decodedBytes = java.util.Base64.getDecoder().decode(str);
        return new String(decodedBytes);
    }


}
