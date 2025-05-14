package com.moneyhandler.controller;

import com.moneyhandler.dao.ExpenseDAO;
import com.moneyhandler.model.UserModel;
import com.moneyhandler.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Handles deletion of an expense record.
 */
@WebServlet("/user/delete-expense")
public class DeleteExpenseController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ExpenseDAO expenseDAO = new ExpenseDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = SessionUtil.getLoggedInUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            int expenseId = Integer.parseInt(req.getParameter("id"));
            boolean deleted = expenseDAO.deleteExpense(expenseId, user.getUserId());

            if (deleted) {
                resp.sendRedirect(req.getContextPath() + "/user/expenses?success=deleted");
            } else {
                resp.sendRedirect(req.getContextPath() + "/user/expenses?error=failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/user/expenses?error=invalid");
        }
    }
}
