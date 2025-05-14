package com.moneyhandler.controller;

import com.moneyhandler.dao.IncomeDAO;
import com.moneyhandler.dao.IncomeTypeDAO;
import com.moneyhandler.model.IncomeModel;
import com.moneyhandler.model.IncomeTypeModel;
import com.moneyhandler.model.UserModel;
import com.moneyhandler.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller to handle income update (GET + POST).
 */
@WebServlet("/user/income/updateincome")
public class UpdateIncomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    private final IncomeDAO incomeDAO = new IncomeDAO();
    private final IncomeTypeDAO incomeTypeDAO = new IncomeTypeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionUtil.isLoggedIn(req)) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            int incomeId = Integer.parseInt(req.getParameter("id"));
            IncomeModel income = incomeDAO.getIncomeById(incomeId);

            if (income == null) {
                resp.sendRedirect(req.getContextPath() + "/user/incomes?error=Income not found");
                return;
            }

            List<IncomeTypeModel> incomeTypes = incomeTypeDAO.getAllTypes();

            req.setAttribute("income", income);
            req.setAttribute("incomeTypes", incomeTypes);
            req.getRequestDispatcher("/WEB-INF/pages/user/update_income.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/user/incomes?error=Invalid request");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionUtil.isLoggedIn(req)) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            int incomeId = Integer.parseInt(req.getParameter("incomeId"));
            double amount = Double.parseDouble(req.getParameter("amount"));
            int incomeTypeId = Integer.parseInt(req.getParameter("incomeTypeId"));
            LocalDate date = LocalDate.parse(req.getParameter("date"));

            UserModel user = SessionUtil.getLoggedInUser(req);

            IncomeModel income = new IncomeModel();
            income.setIncomeId(incomeId);
            income.setUserId(user.getUserId());
            income.setIncomeTypeId(incomeTypeId);
            income.setAmount(amount);
            income.setDate(date);

            boolean updated = incomeDAO.updateIncome(income);

            if (updated) {
                resp.sendRedirect(req.getContextPath() + "/user/incomes?success=Income updated");
            } else {
                req.setAttribute("error", "Update failed");
                req.getRequestDispatcher("/WEB-INF/pages/user/update_income.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Invalid input data");
            req.getRequestDispatcher("/WEB-INF/pages/user/update_income.jsp").forward(req, resp);
        }
    }
}
