package com.moneyhandler.controller;

import com.moneyhandler.dao.ReportDAO;
import com.moneyhandler.model.TransactionModel;
import com.moneyhandler.model.UserModel;
import com.moneyhandler.util.PDFExportUtil;
import com.moneyhandler.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/user/reports", "/user/report/export"})
public class ReportController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ReportDAO reportDAO = new ReportDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = SessionUtil.getLoggedInUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        List<TransactionModel> transactions = reportDAO.getAllTransactionsForUser(user.getUserId());
        req.setAttribute("transactions", transactions);
        req.getRequestDispatcher("/WEB-INF/pages/report.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = SessionUtil.getLoggedInUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        List<TransactionModel> transactions = reportDAO.getAllTransactionsForUser(user.getUserId());
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=TransactionReport.pdf");

        PDFExportUtil.generateTransactionReportPDF(transactions, resp.getOutputStream());
    }
}
