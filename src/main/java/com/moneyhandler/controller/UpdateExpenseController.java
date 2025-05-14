package com.moneyhandler.controller;

import com.moneyhandler.dao.ExpenseDAO;
import com.moneyhandler.dao.ExpenseTypeDAO;
import com.moneyhandler.model.ExpenseModel;
import com.moneyhandler.model.ExpenseTypeModel;
import com.moneyhandler.model.UserModel;
import com.moneyhandler.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/user/update-expense")
public class UpdateExpenseController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ExpenseDAO expenseDAO = new ExpenseDAO();
    private final ExpenseTypeDAO expenseTypeDAO = new ExpenseTypeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = SessionUtil.getLoggedInUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        int expenseId = Integer.parseInt(req.getParameter("id"));
        ExpenseModel expense = expenseDAO.getExpenseById(expenseId);

        if (expense == null || expense.getUserId() != user.getUserId()) {
            resp.sendRedirect(req.getContextPath() + "/user/expenses");
            return;
        }

        List<ExpenseTypeModel> types = expenseTypeDAO.getAllTypes();
        req.setAttribute("expense", expense);
        req.setAttribute("expenseTypeList", types);
        req.getRequestDispatcher("/WEB-INF/pages/updateexpense.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = SessionUtil.getLoggedInUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            int expenseId = Integer.parseInt(req.getParameter("expenseId"));
            int typeId = Integer.parseInt(req.getParameter("expenseTypeId"));
            String vendor = req.getParameter("vendor");
            double amount = Double.parseDouble(req.getParameter("amount"));
            LocalDate date = LocalDate.parse(req.getParameter("date"));

            ExpenseModel expense = new ExpenseModel();
            expense.setExpenseId(expenseId);
            expense.setUserId(user.getUserId());
            expense.setExpenseTypeId(typeId);
            expense.setVendor(vendor);
            expense.setAmount(amount);
            expense.setDate(date);

            boolean updated = expenseDAO.updateExpense(expense);
            if (updated) {
                req.setAttribute("success", "Expense updated successfully.");
            } else {
                req.setAttribute("error", "Failed to update expense.");
            }
            req.setAttribute("expense", expense);

        } catch (Exception e) {
            req.setAttribute("error", "Invalid data. Please check your inputs.");
            e.printStackTrace();
        }

        List<ExpenseTypeModel> types = expenseTypeDAO.getAllTypes();
        req.setAttribute("expenseTypeList", types);
        req.getRequestDispatcher("/WEB-INF/pages/updateexpense.jsp").forward(req, resp);
    }
}
