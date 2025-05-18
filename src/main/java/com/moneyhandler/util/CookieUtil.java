package com.moneyhandler.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

/**
 * Utility for handling cookies in MoneyHandler.
 */
public class CookieUtil {

    /**
     * Adds a cookie.
     * 
     * @param response the response object
     * @param name cookie name
     * @param value cookie value
     * @param maxAge max age in seconds
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/"); // accessible across entire app
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * Gets a cookie by name.
     * 
     * @param request the request
     * @param name the cookie name
     * @return Cookie or null
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> name.equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    /**
     * Deletes a cookie by name.
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
