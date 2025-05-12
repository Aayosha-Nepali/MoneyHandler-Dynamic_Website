package com.moneyhandler.controller;

import com.moneyhandler.model.UserModel;
import com.moneyhandler.service.DashboardService;
import com.moneyhandler.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Controller for rendering the user dashboard.
 */
@WebServlet("/user/dashboard")
public class UserDashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final DashboardService dashboardService = new DashboardService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel currentUser = SessionUtil.getLoggedInUser(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        int userId = currentUser.getUserId();

        // Get total values
        double totalIncome = dashboardService.getTotalIncome(userId);
        double totalExpense = dashboardService.getTotalExpense(userId);
        double totalSaving = dashboardService.getTotalSavings(userId);

        // Get chart data (labels + datasets)
        Map<String, List<?>> chartData = dashboardService.getLineChartData(userId); // 


        req.setAttribute("totalIncome", totalIncome);
        req.setAttribute("totalExpense", totalExpense);
        req.setAttribute("totalSaving", totalSaving);
        req.setAttribute("chartLabels", chartData.get("labels"));
        req.setAttribute("incomeValues", chartData.get("income"));
        req.setAttribute("expenseValues", chartData.get("expense"));
        req.setAttribute("savingValues", chartData.get("savings"));

        req.getRequestDispatcher("/WEB-INF/pages/user_dashboard.jsp").forward(req, resp);
    }
}
