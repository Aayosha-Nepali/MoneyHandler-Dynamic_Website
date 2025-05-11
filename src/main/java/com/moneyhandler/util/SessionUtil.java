package com.moneyhandler.util;

import com.moneyhandler.model.UserModel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class for handling session-related tasks in MoneyHandler.
 */
public class SessionUtil {

    // Get the currently logged-in user from session
    public static UserModel getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // false = don’t create new session
        if (session != null) {
            return (UserModel) session.getAttribute("loggedInUser");
        }
        return null;
    }

    // Check if user is logged in
    public static boolean isLoggedIn(HttpServletRequest request) {
        return getLoggedInUser(request) != null;
    }

    // Logout the user
    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    // Enforce authentication: redirect to login if not logged in
    public static boolean enforceLogin(HttpServletRequest request, HttpServletRequest response) {
        if (!isLoggedIn(request)) {
            return false;
        }
        return true;
    }
}
