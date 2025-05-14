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

@WebServlet("/user/add-expense")
public class AddExpenseController extends HttpServlet {
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

        List<ExpenseTypeModel> expenseTypes = expenseTypeDAO.getAllTypes();
        req.setAttribute("expenseTypeList", expenseTypes);
        req.getRequestDispatcher("/WEB-INF/pages/addexpense.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = SessionUtil.getLoggedInUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            int expenseTypeId = Integer.parseInt(req.getParameter("expenseTypeId"));
            double amount = Double.parseDouble(req.getParameter("amount"));
            String vendor = req.getParameter("vendor");
            LocalDate date = LocalDate.parse(req.getParameter("date"));

            ExpenseModel expense = new ExpenseModel();
            expense.setUserId(user.getUserId());
            expense.setExpenseTypeId(expenseTypeId);
            expense.setAmount(amount);
            expense.setVendor(vendor);
            expense.setDate(date);

            boolean isAdded = expenseDAO.addExpense(expense);
            if (isAdded) {
                req.setAttribute("success", "Expense added successfully.");
            } else {
                req.setAttribute("error", "Failed to add expense.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Invalid input. Please check your values.");
        }

        List<ExpenseTypeModel> expenseTypes = expenseTypeDAO.getAllTypes();
        req.setAttribute("expenseTypeList", expenseTypes);
        req.getRequestDispatcher("/WEB-INF/pages/addexpense.jsp").forward(req, resp);
    }
}
