package com.moneyhandler.controller;

import com.itextpdf.text.DocumentException;
import com.moneyhandler.dao.AdminTransactionDAO;
import com.moneyhandler.model.AdminTransactionModel;
import com.moneyhandler.util.PDFExportUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

/**
 * Controller to export all transactions (admin) to PDF.
 */
@WebServlet("/admin/export/transactions")
public class AdminTransactionExportController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AdminTransactionDAO transactionDAO = new AdminTransactionDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<AdminTransactionModel> transactions = transactionDAO.getAllTransactions();

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=all_transactions.pdf");

        try {
            PDFExportUtil.exportTransactionsToPDF(transactions, resp.getOutputStream());
        } catch (DocumentException e) {
            e.printStackTrace();
            resp.getWriter().write("Failed to generate PDF. Please try again.");
        }
    }
}
