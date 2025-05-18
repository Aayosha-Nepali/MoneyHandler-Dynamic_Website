package com.moneyhandler.controller;

import com.moneyhandler.dao.ExpenseDAO;
import com.moneyhandler.dao.ExpenseTypeDAO;
import com.moneyhandler.model.ExpenseModel;
import com.moneyhandler.model.ExpenseTypeModel;
import com.moneyhandler.model.UserModel;
import com.moneyhandler.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * Controller to handle user-side Expense listing and filtering.
 */
@WebServlet("/user/expenses")
public class ExpenseController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ExpenseDAO expenseDAO = new ExpenseDAO();
    private final ExpenseTypeDAO expenseTypeDAO = new ExpenseTypeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel currentUser = SessionUtil.getLoggedInUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        int userId = currentUser.getUserId();

        // Filter parameters
        String type = req.getParameter("type");
        String from = req.getParameter("from");
        String to = req.getParameter("to");

        Date fromDate = (from != null && !from.isEmpty()) ? Date.valueOf(from) : null;
        Date toDate = (to != null && !to.isEmpty()) ? Date.valueOf(to) : null;

        List<ExpenseModel> expenses = expenseDAO.getExpensesByUser(userId, type, fromDate, toDate);
        List<ExpenseTypeModel> expenseTypes = expenseTypeDAO.getAllTypes();

        req.setAttribute("expenseList", expenses);
        req.setAttribute("expenseType", expenseTypes);
        req.setAttribute("selectedType", type);
        req.setAttribute("fromDate", from);
        req.setAttribute("toDate", to);

        req.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(req, resp);
    }
}
