package com.moneyhandler.controller;

import com.moneyhandler.dao.AdminContactDAO;
import com.moneyhandler.model.ContactModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

/**
 * Controller to manage contact messages for admin.
 */
@WebServlet("/admin/contact")
public class AdminContactController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AdminContactDAO contactDAO = new AdminContactDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ContactModel> contacts = contactDAO.getAllContacts();
        req.setAttribute("contacts", contacts);
        req.getRequestDispatcher("WEB-INF/pages/admin/admin-contact.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("updateStatus".equals(action)) {
            int contactId = Integer.parseInt(req.getParameter("contactId"));
            String newStatus = req.getParameter("status");
            contactDAO.updateStatus(contactId, newStatus);
        }

        if ("delete".equals(action)) {
            int contactId = Integer.parseInt(req.getParameter("contactId"));
            contactDAO.deleteContact(contactId);
        }

        // Refresh the page after update or delete
        resp.sendRedirect(req.getContextPath() + "/admin/contact");
    }
}
