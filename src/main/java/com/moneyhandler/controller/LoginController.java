package com.moneyhandler.controller;

import com.moneyhandler.model.UserModel;
import com.moneyhandler.service.LoginService;
import com.moneyhandler.util.PasswordUtil;
import com.moneyhandler.util.SessionUtil;
import com.moneyhandler.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final LoginService loginService = new LoginService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //  Forward to protected JSP inside WEB-INF
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (ValidationUtil.isNullOrEmpty(email) || ValidationUtil.isNullOrEmpty(password)) {
            req.setAttribute("error", "Email and password are required.");
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
            return;
        }

        UserModel user = loginService.findUserByEmail(email);
        if (user == null) {
            req.setAttribute("error", "Account not found.");
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
            return;
        }

        String decryptedPassword = PasswordUtil.decrypt(user.getPassword(), user.getUsername());
        if (decryptedPassword != null && decryptedPassword.equals(password)) {
            @SuppressWarnings("unused")
			HttpSession session = req.getSession();
            SessionUtil.setLoggedInUser(req, user);

            if ("admin@moneyhandler.com".equalsIgnoreCase(email)) {
                resp.sendRedirect(req.getContextPath() + "/admin/admindashboard");
            } else {
                resp.sendRedirect(req.getContextPath() + "/user/userdashboard");
            }
        } else {
            req.setAttribute("error", "Invalid email or password.");
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
        }
    }
}
