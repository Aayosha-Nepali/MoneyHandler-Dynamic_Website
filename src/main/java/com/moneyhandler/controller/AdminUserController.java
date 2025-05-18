package com.moneyhandler.controller;

import com.moneyhandler.dao.AdminUserDAO;
import com.moneyhandler.model.UserModel;
import com.moneyhandler.util.PasswordUtil;
import com.moneyhandler.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/** 
 * Controller for managing users (admin-side).
 */
@WebServlet(urlPatterns = {"/admin/users", "/admin/users/adduser"})
public class AdminUserController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AdminUserDAO userDAO = new AdminUserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/admin/users/add".equals(path)) {
            // Show add user form
            req.getRequestDispatcher("/WEB-INF/pages/admin/adduser.jsp").forward(req, resp);
        } else {
            // Show all users
            List<UserModel> users = userDAO.getAllUsers();
            req.setAttribute("users", users);
            req.getRequestDispatcher("/WEB-INF/pages/admin/users.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle POST from add user form
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String dobStr = req.getParameter("dob");
        String contact = req.getParameter("contactnumber");
        String password = req.getParameter("password");
        String retype = req.getParameter("retypePassword");

        // Validation
        if (ValidationUtil.isNullOrEmpty(username) || ValidationUtil.isNullOrEmpty(email)
                || ValidationUtil.isNullOrEmpty(dobStr) || ValidationUtil.isNullOrEmpty(contact)
                || ValidationUtil.isNullOrEmpty(password) || ValidationUtil.isNullOrEmpty(retype)) {

            req.setAttribute("error", "All fields are required.");
            req.getRequestDispatcher("/WEB-INF/pages/admin/adduser.jsp").forward(req, resp);
            return;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            req.setAttribute("error", "Invalid email format.");
            req.getRequestDispatcher("/WEB-INF/pages/admin/adduser.jsp").forward(req, resp);
            return;
        }

        if (!ValidationUtil.isValidPhoneNumber(contact)) {
            req.setAttribute("error", "Invalid contact number.");
            req.getRequestDispatcher("/WEB-INF/pages/admin/adduser.jsp").forward(req, resp);
            return;
        }

        if (!ValidationUtil.isValidPassword(password)) {
            req.setAttribute("error", "Password must contain 1 uppercase letter, 1 number, 1 symbol, and be 8+ characters.");
            req.getRequestDispatcher("/WEB-INF/pages/admin/adduser.jsp").forward(req, resp);
            return;
        }

        if (!ValidationUtil.doPasswordsMatch(password, retype)) {
            req.setAttribute("error", "Passwords do not match.");
            req.getRequestDispatcher("/WEB-INF/pages/admin/adduser.jsp").forward(req, resp);
            return;
        }

        LocalDate dob;
        try {
            dob = LocalDate.parse(dobStr);
        } catch (Exception e) {
            req.setAttribute("error", "Invalid date format.");
            req.getRequestDispatcher("/WEB-INF/pages/admin/adduser.jsp").forward(req, resp);
            return;
        }

        if (!ValidationUtil.isAgeAtLeast12(dob)) {
            req.setAttribute("error", "User must be at least 12 years old.");
            req.getRequestDispatcher("/WEB-INF/pages/admin/adduser.jsp").forward(req, resp);
            return;
        }

        // Encrypt password and insert
        String encryptedPassword = PasswordUtil.encrypt(username, password);
        UserModel user = new UserModel(username, email, dob, contact, encryptedPassword, "default_user.png");

        boolean added = userDAO.addUser(user);
        if (added) {
            req.setAttribute("success", "User successfully added.");
            req.getRequestDispatcher("/WEB-INF/pages/admin/adduser.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Failed to add user. Please try again.");
            req.getRequestDispatcher("/WEB-INF/pages/admin/adduser.jsp").forward(req, resp);
        }
    }
}
