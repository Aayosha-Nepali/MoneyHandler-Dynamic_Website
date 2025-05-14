package com.moneyhandler.util;

import com.moneyhandler.model.UserModel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Utility for handling session operations like login/logout.
 */
public class SessionUtil {

    private static final String USER_SESSION_KEY = "loggedInUser";

    // Save user in session after login
    public static void setLoggedInUser(HttpServletRequest request, UserModel user) {
        HttpSession session = request.getSession(true);
        session.setAttribute(USER_SESSION_KEY, user);
    }

    // Get currently logged-in user from session
    public static UserModel getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (UserModel) session.getAttribute(USER_SESSION_KEY);
        }
        return null;
    }

    // Logout user (invalidate session)
    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    // Check if user is logged in
    public static boolean isLoggedIn(HttpServletRequest request) {
        return getLoggedInUser(request) != null;
    }
}
