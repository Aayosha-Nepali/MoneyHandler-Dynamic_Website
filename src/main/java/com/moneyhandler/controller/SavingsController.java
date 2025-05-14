package com.moneyhandler.controller;

import com.moneyhandler.dao.SavingsDAO;
import com.moneyhandler.model.UserModel;
import com.moneyhandler.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

/**
 * Controller to handle savings page display.
 */
@WebServlet("/user/savings")
public class SavingsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final SavingsDAO savingsDAO = new SavingsDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = SessionUtil.getLoggedInUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        int userId = user.getUserId();

        // Fetch data
        Map<String, Double> monthlySavings = savingsDAO.getMonthlySavings(userId);
        double totalSavings = savingsDAO.getTotalSavings(userId);

        // Set attributes for JSP
        req.setAttribute("monthlySavings", monthlySavings);
        req.setAttribute("totalSavings", totalSavings);

        // Forward to JSP
        req.getRequestDispatcher("/WEB-INF/pages/savings.jsp").forward(req, resp);
    }
}
