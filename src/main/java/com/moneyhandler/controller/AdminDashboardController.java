package com.moneyhandler.controller;

import com.moneyhandler.dao.AdminDAO;
import com.moneyhandler.util.SessionUtil;
import com.moneyhandler.model.UserModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/admindashboard")
public class AdminDashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AdminDAO adminDAO = new AdminDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel currentUser = SessionUtil.getLoggedInUser(req);
        if (currentUser == null || !"admin@moneyhandler.com".equalsIgnoreCase(currentUser.getEmail())) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int userCount = adminDAO.getTotalUserCount();
        int incomeCount = adminDAO.getTotalIncomeRecordCount();
        int expenseCount = adminDAO.getTotalExpenseRecordCount();

        req.setAttribute("userCount", userCount);
        req.setAttribute("incomeCount", incomeCount);
        req.setAttribute("expenseCount", expenseCount);

        req.getRequestDispatcher("/WEB-INF/pages/admin/admindashboard.jsp").forward(req, resp);
    }
}
