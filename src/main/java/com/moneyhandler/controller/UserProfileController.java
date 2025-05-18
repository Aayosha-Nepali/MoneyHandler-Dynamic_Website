package com.moneyhandler.controller;

import com.moneyhandler.dao.UserDAO;
import com.moneyhandler.model.UserModel;
import com.moneyhandler.util.ImageUtil;
import com.moneyhandler.util.PasswordUtil;
import com.moneyhandler.util.SessionUtil;
import com.moneyhandler.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/user/profile")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 5MB
public class UserProfileController extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();
    private final ImageUtil imageUtil = new ImageUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel currentUser = SessionUtil.getLoggedInUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        req.setAttribute("user", currentUser);
        req.getRequestDispatcher("/WEB-INF/pages/user/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel currentUser = SessionUtil.getLoggedInUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String contact = req.getParameter("contactnumber");
        String newPassword = req.getParameter("password");
        String confirmPassword = req.getParameter("retypePassword");

        Part imagePart = req.getPart("profilePicture");

        if (!ValidationUtil.isValidPhoneNumber(contact)) {
            req.setAttribute("error", "Invalid phone number.");
            req.setAttribute("user", currentUser);
            req.getRequestDispatcher("/WEB-INF/pages/user/profile.jsp").forward(req, resp);
            return;
        }

        currentUser.setContactNumber(contact);

        // Handle password change (optional)
        if (!ValidationUtil.isNullOrEmpty(newPassword)) {
            if (!ValidationUtil.doPasswordsMatch(newPassword, confirmPassword)) {
                req.setAttribute("error", "Passwords do not match.");
                req.setAttribute("user", currentUser);
                req.getRequestDispatcher("/WEB-INF/pages/user/profile.jsp").forward(req, resp);
                return;
            }
            if (!ValidationUtil.isValidPassword(newPassword)) {
                req.setAttribute("error", "Password must be at least 8 characters, include uppercase, number, and symbol.");
                req.setAttribute("user", currentUser);
                req.getRequestDispatcher("/WEB-INF/pages/user/profile.jsp").forward(req, resp);
                return;
            }
            String encryptedPassword = PasswordUtil.encrypt(currentUser.getUsername(), newPassword);
            currentUser.setPassword(encryptedPassword);
        }

        // Handle image update (optional)
        if (imagePart != null && imagePart.getSize() > 0) {
            if (!ValidationUtil.isValidImageExtension(imagePart)) {
                req.setAttribute("error", "Invalid image file type.");
                req.setAttribute("user", currentUser);
                req.getRequestDispatcher("/WEB-INF/pages/user/profile.jsp").forward(req, resp);
                return;
            }
            String imagePath = imageUtil.getImageNameFromPart(imagePart);
            currentUser.setImagePath(imagePath);
            imageUtil.uploadImage(imagePart, req.getServletContext().getRealPath("/"), "user");
        }

        boolean updated = userDAO.updateUserProfile(currentUser);
        if (updated) {
            SessionUtil.setLoggedInUser(req, currentUser); // update session user
            req.setAttribute("success", "Profile updated successfully.");
        } else {
            req.setAttribute("error", "Failed to update profile.");
        }

        req.setAttribute("user", currentUser);
        req.getRequestDispatcher("/WEB-INF/pages/user/profile.jsp").forward(req, resp);
    }
}
