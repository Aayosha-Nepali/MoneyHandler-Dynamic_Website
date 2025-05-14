package com.moneyhandler.controller;

import com.moneyhandler.dao.IncomeDAO;
import com.moneyhandler.dao.IncomeTypeDAO;
import com.moneyhandler.model.IncomeModel;
import com.moneyhandler.model.IncomeTypeModel;
import com.moneyhandler.model.UserModel;
import com.moneyhandler.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * Controller to handle viewing/filtering income records.
 */
@WebServlet("/user/income")
public class IncomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IncomeDAO incomeDAO = new IncomeDAO();
    private final IncomeTypeDAO incomeTypeDAO = new IncomeTypeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel currentUser = SessionUtil.getLoggedInUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        int userId = currentUser.getUserId();

        // Search parameter
        String search = req.getParameter("search");

        // Filter parameters
        String type = req.getParameter("type");
        String from = req.getParameter("from");
        String to = req.getParameter("to");

        Date fromDate = (from != null && !from.isEmpty()) ? Date.valueOf(from) : null;
        Date toDate = (to != null && !to.isEmpty()) ? Date.valueOf(to) : null;

        List<IncomeModel> incomes;

        if (search != null && !search.trim().isEmpty()) {
            incomes = incomeDAO.searchIncomes(userId, search.trim());
            req.setAttribute("search", search.trim());
        } else {
            incomes = incomeDAO.getIncomesByUser(userId, type, fromDate, toDate);
        }

        List<IncomeTypeModel> incomeType = incomeTypeDAO.getAllTypes();

        req.setAttribute("incomeList", incomes);
        req.setAttribute("incomeType", incomeType);
        req.setAttribute("selectedType", type);
        req.setAttribute("fromDate", from);
        req.setAttribute("toDate", to);

        req.getRequestDispatcher("/WEB-INF/pages/income.jsp").forward(req, resp);
    }
}
