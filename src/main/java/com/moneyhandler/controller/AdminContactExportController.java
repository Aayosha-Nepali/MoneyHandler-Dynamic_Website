package com.moneyhandler.controller;

import com.moneyhandler.dao.AdminContactDAO;
import com.moneyhandler.model.ContactModel;
import com.moneyhandler.util.PDFExportUtil;

import com.itextpdf.text.DocumentException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

/**
 * Servlet to export contact messages to PDF for admin.
 */
@WebServlet("/admin/export/contact")
public class AdminContactExportController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AdminContactDAO contactDAO = new AdminContactDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ContactModel> messages = contactDAO.getAllMessages();

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=contact_messages.pdf");

        try {
            PDFExportUtil.exportContactMessagesToPDF(messages, resp.getOutputStream());
        } catch (DocumentException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Could not generate PDF.");
        }
    }
}
