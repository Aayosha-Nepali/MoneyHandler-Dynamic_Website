package com.moneyhandler.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Handles logout functionality.
 */
@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false); // don't create if not exists
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/pages/login.jsp");
    }
}
