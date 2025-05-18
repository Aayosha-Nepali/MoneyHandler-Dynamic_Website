package com.moneyhandler.controller;

import com.moneyhandler.model.UserModel;
import com.moneyhandler.service.LoginService;
import com.moneyhandler.util.CookieUtil;
import com.moneyhandler.util.PasswordUtil;
import com.moneyhandler.util.SessionUtil;
import com.moneyhandler.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(asyncSupported = true, urlPatterns = { "/login", "/" })
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final LoginService loginService = new LoginService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie savedEmailCookie = CookieUtil.getCookie(req, "savedEmail");
        if (savedEmailCookie != null) {
            req.setAttribute("savedEmail", savedEmailCookie.getValue());
        }
        req.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (ValidationUtil.isNullOrEmpty(email) || ValidationUtil.isNullOrEmpty(password)) {
            req.setAttribute("error", "Email and password are required.");
            req.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(req, resp);
            return;
        }

        UserModel user = loginService.findUserByEmail(email);
        if (user == null) {
            req.setAttribute("error", "Account not found.");
            req.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(req, resp);
            return;
        }

        String decryptedPassword = PasswordUtil.decrypt(email, user.getPassword());
        if (decryptedPassword != null && decryptedPassword.equals(password)) {
        	// to remember email
            CookieUtil.addCookie(resp, "savedEmail", email, 7 * 24 * 60 * 60); // for 7 days
            @SuppressWarnings("unused")
			HttpSession session = req.getSession();
            SessionUtil.setLoggedInUser(req, user);

            if ("admin@moneyhandler.com".equalsIgnoreCase(email)) {
                resp.sendRedirect(req.getContextPath() + "/admindashboard");
            } else {
                resp.sendRedirect(req.getContextPath() + "/userdashboard");
            }
        } else {
            req.setAttribute("error", "Invalid email or password.");
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
        }
    }
}
