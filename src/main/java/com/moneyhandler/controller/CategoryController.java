package com.moneyhandler.controller;

import com.moneyhandler.dao.CategoryDAO;
import com.moneyhandler.model.CategoryModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Controller for managing income/expense categories by admin.
 */
@WebServlet("/admin/categories")
public class CategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CategoryModel> incomeCategories = categoryDAO.getCategoriesByType("income");
        List<CategoryModel> expenseCategories = categoryDAO.getCategoriesByType("expense");

        req.setAttribute("incomeCategories", incomeCategories);
        req.setAttribute("expenseCategories", expenseCategories);

        req.getRequestDispatcher("/WEB-INF/pages/admin/categories.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            String type = req.getParameter("type");
            String name = req.getParameter("name");

            if (type != null && name != null && !name.trim().isEmpty()) {
                categoryDAO.addCategory(type, name);
            }

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("categoryId"));
            String name = req.getParameter("name");
            categoryDAO.updateCategoryName(id, name);

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("categoryId"));
            categoryDAO.deleteCategory(id);
        }

        // Redirect to avoid form resubmission
        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }
}
