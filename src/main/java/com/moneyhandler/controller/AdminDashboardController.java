package com.moneyhandler.controller;

import com.google.gson.Gson;
import com.moneyhandler.dao.AdminDAO;
import com.moneyhandler.service.AdminDashboardService;
import com.moneyhandler.model.UserModel;
import com.moneyhandler.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admindashboard")
public class AdminDashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AdminDAO adminDAO = new AdminDAO();
    private final AdminDashboardService dashboardService = new AdminDashboardService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel currentUser = SessionUtil.getLoggedInUser(req);
        if (currentUser == null || !"admin@moneyhandler.com".equalsIgnoreCase(currentUser.getEmail())) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Total cards
        int userCount = adminDAO.getTotalUserCount();
        int incomeCount = adminDAO.getTotalIncomeRecordCount();
        int expenseCount = adminDAO.getTotalExpenseRecordCount();

        req.setAttribute("userCount", userCount);
        req.setAttribute("incomeCount", incomeCount);
        req.setAttribute("expenseCount", expenseCount);

        // Line & Pie chart data
        var chartData = dashboardService.getLineChartData(); // contains labels, income, expense, savings

        Gson gson = new Gson();

        req.setAttribute("chartLabels", gson.toJson(chartData.get("labels")));
        req.setAttribute("incomeValues", gson.toJson(chartData.get("income")));
        req.setAttribute("expenseValues", gson.toJson(chartData.get("expense")));
        req.setAttribute("savingValues", gson.toJson(chartData.get("savings")));


        req.getRequestDispatcher("/WEB-INF/pages/admin/admindashboard.jsp").forward(req, resp);
    }
}
