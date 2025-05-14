package com.moneyhandler.controller;

import com.moneyhandler.dao.IncomeDAO;
import com.moneyhandler.model.UserModel;
import com.moneyhandler.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Handles deletion of income records.
 */
@WebServlet("/user/income/delete")
public class DeleteIncomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IncomeDAO incomeDAO = new IncomeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Make sure the user is logged in
        if (!SessionUtil.isLoggedIn(req)) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            int incomeId = Integer.parseInt(req.getParameter("id"));
            UserModel user = SessionUtil.getLoggedInUser(req);
            int userId = user.getUserId();  // ✅ Correct: get user ID from session

            boolean deleted = incomeDAO.deleteIncome(incomeId, userId);

            if (deleted) {
                resp.sendRedirect(req.getContextPath() + "/user/incomes?success=Income deleted successfully");
            } else {
                resp.sendRedirect(req.getContextPath() + "/user/incomes?error=Failed to delete income");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/user/incomes?error=Invalid request");
        }
    }
}
