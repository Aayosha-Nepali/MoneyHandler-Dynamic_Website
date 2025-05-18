package com.moneyhandler.controller;

import com.moneyhandler.dao.AdminTransactionDAO;
import com.moneyhandler.model.AdminTransactionModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Controller to handle admin-side viewing of all user transactions.
 */
@WebServlet("/transaction")
public class AdminTransactionController extends HttpServlet {

    private static final long serialVersionUID = 1L;
	private final AdminTransactionDAO transactionDAO = new AdminTransactionDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<AdminTransactionModel> transactions = transactionDAO.getAllTransactions();
        req.setAttribute("transactions", transactions);
        req.getRequestDispatcher("/WEB-INF/pages/admin/transaction.jsp").forward(req, resp);
    }
}
