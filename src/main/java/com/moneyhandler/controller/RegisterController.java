package com.moneyhandler.controller;

import java.io.IOException;
import java.time.LocalDate;

import com.moneyhandler.model.UserModel;
import com.moneyhandler.service.RegisterService;
import com.moneyhandler.util.ImageUtil;
import com.moneyhandler.util.PasswordUtil;
import com.moneyhandler.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/registration")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final RegisterService registerService = new RegisterService();
    private final ImageUtil imageUtil = new ImageUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String message = validateForm(req);
            if (message != null) {
                req.setAttribute("error", message);
                req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
                return;
            }

            UserModel user = extractUser(req);
            Boolean isAdded = registerService.registerUser(user);

            if (Boolean.TRUE.equals(isAdded)) {
                boolean imageSaved = uploadImage(req);
                if (imageSaved) {
                    resp.sendRedirect(req.getContextPath() + "/login.jsp");  // redirect
                } else {
                    req.setAttribute("error", "Image upload failed. Try again.");
                    req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
                }

            } else {
                req.setAttribute("error", "Failed to create account. Please try again later.");
                req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "An unexpected error occurred.");
            req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
        }
    }

    private String validateForm(HttpServletRequest req) {
        String username = req.getParameter("username");
        String dobStr = req.getParameter("dob");
        String email = req.getParameter("email");
        String contact = req.getParameter("contact");
        String password = req.getParameter("password");
        String retypePassword = req.getParameter("retypePassword");

        if (ValidationUtil.isNullOrEmpty(username)) return "Username is required.";
        if (ValidationUtil.isNullOrEmpty(dobStr)) return "Date of Birth is required.";
        if (ValidationUtil.isNullOrEmpty(email)) return "Email is required.";
        if (ValidationUtil.isNullOrEmpty(contact)) return "Contact number is required.";
        if (ValidationUtil.isNullOrEmpty(password)) return "Password is required.";
        if (ValidationUtil.isNullOrEmpty(retypePassword)) return "Please retype your password.";

        LocalDate dob;
        try {
            dob = LocalDate.parse(dobStr);
        } catch (Exception e) {
            return "Invalid Date format (YYYY-MM-DD required).";
        }

        if (!ValidationUtil.isValidEmail(email)) return "Invalid email format.";
        if (!ValidationUtil.isValidPhoneNumber(contact)) return "Contact must start with 98 and be 10 digits.";
        if (!ValidationUtil.isValidPassword(password)) return "Password must be at least 8 characters with an uppercase letter, a number, and a symbol.";
        if (!ValidationUtil.doPasswordsMatch(password, retypePassword)) return "Passwords do not match.";
        if (!ValidationUtil.isAgeAtLeast12(dob)) return "You must be at least 12 years old.";

        try {
            Part image = req.getPart("profilePicture");
            if (!ValidationUtil.isValidImageExtension(image)) return "Invalid image file. Only JPG, PNG, JPEG, or GIF allowed.";
        } catch (Exception e) {
            return "Error reading image. Please upload a valid file.";
        }

        return null;
    }

    private UserModel extractUser(HttpServletRequest req) throws Exception {
        String username = req.getParameter("username");
        LocalDate dob = LocalDate.parse(req.getParameter("dob"));
        String email = req.getParameter("email");
        String contact = req.getParameter("contact");
        String rawPassword = req.getParameter("password");
        String encryptedPassword = PasswordUtil.encrypt(username, rawPassword);

        Part image = req.getPart("profilePicture");
        String imagePath = imageUtil.getImageNameFromPart(image);

        return new UserModel(username, email, dob, contact, encryptedPassword, imagePath);
    }

    private boolean uploadImage(HttpServletRequest req) throws IOException, ServletException {
        Part image = req.getPart("profilePicture");
        return imageUtil.uploadImage(image, req.getServletContext().getRealPath("/"), "user");
    }
}
