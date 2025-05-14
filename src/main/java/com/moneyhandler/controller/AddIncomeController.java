package com.moneyhandler.controller;

import com.moneyhandler.dao.IncomeDAO;
import com.moneyhandler.dao.IncomeTypeDAO;
import com.moneyhandler.model.IncomeModel;
import com.moneyhandler.model.IncomeTypeModel;
import com.moneyhandler.model.UserModel;
import com.moneyhandler.util.SessionUtil;
import com.moneyhandler.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller to handle income addition logic.
 */
@WebServlet("/user/income/addincome")
public class AddIncomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IncomeDAO incomeDAO = new IncomeDAO();
    private final IncomeTypeDAO incomeTypeDAO = new IncomeTypeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	UserModel user = SessionUtil.getLoggedInUser(req);

    	if (!SessionUtil.isLoggedIn(req)) {
    	    resp.sendRedirect(req.getContextPath() + "/login.jsp");
    	    return;
    	}


        List<IncomeTypeModel> types = incomeTypeDAO.getAllTypes();
        req.setAttribute("incomeTypes", types);
        req.getRequestDispatcher("/WEB-INF/pages/user/addincome.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	UserModel user = SessionUtil.getLoggedInUser(req);

    	if (!SessionUtil.isLoggedIn(req)) {
    	    resp.sendRedirect(req.getContextPath() + "/login.jsp");
    	    return;
    	}


        String amountStr = req.getParameter("amount");
        String typeIdStr = req.getParameter("incomeTypeId");
        String dateStr = req.getParameter("date");

        if (ValidationUtil.isNullOrEmpty(amountStr) || ValidationUtil.isNullOrEmpty(typeIdStr) || ValidationUtil.isNullOrEmpty(dateStr)) {
            req.setAttribute("error", "All fields are required.");
            doGet(req, resp);
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            int typeId = Integer.parseInt(typeIdStr);
            LocalDate date = LocalDate.parse(dateStr);

            IncomeModel income = new IncomeModel();
            income.setAmount(amount);
            income.setIncomeTypeId(typeId);
            income.setUserId(user.getUserId());
            income.setDate(date);

            boolean added = incomeDAO.addIncome(income);

            if (added) {
                req.setAttribute("success", "Income added successfully.");
            } else {
                req.setAttribute("error", "Failed to add income.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Invalid input. Please check your data.");
        }

        doGet(req, resp); // Show form again with message
    }
}
