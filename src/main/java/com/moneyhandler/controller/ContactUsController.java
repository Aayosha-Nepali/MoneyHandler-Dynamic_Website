package com.moneyhandler.controller;

import com.moneyhandler.dao.ContactDAO;
import com.moneyhandler.model.ContactModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/user/contactus")
public class ContactUsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ContactDAO contactDAO = new ContactDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/user/contactus.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String subject = req.getParameter("subject");
        String message = req.getParameter("message");

        ContactModel contact = new ContactModel();
        contact.setName(name);
        contact.setEmail(email);
        contact.setSubject(subject);
        contact.setMessage(message);

        boolean saved = contactDAO.saveContact(contact);

        if (saved) {
            req.setAttribute("success", "Thank you for contacting us! We'll get back to you soon.");
        } else {
            req.setAttribute("error", "Failed to send your message. Please try again.");
        }

        req.getRequestDispatcher("/WEB-INF/pages/user/contactus.jsp").forward(req, resp);
    }
}
