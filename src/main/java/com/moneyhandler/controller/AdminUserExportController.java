package com.moneyhandler.controller;

import com.moneyhandler.dao.AdminUserDAO;
import com.moneyhandler.model.UserModel;
import com.moneyhandler.util.PDFExportUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/export/users")
public class AdminUserExportController extends HttpServlet {

    private final AdminUserDAO adminUserDAO = new AdminUserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserModel> users = adminUserDAO.getAllUsers();

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=Users_Report.pdf");

        try {
            PDFExportUtil.exportUsersToPDF(users, resp.getOutputStream());
        } catch (com.itextpdf.text.DocumentException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF.");
        }
    }

}
