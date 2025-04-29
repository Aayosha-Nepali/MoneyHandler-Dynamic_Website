package com.moneyhandler.controller;

import com.moneyhandler.model.UserModel;
import com.moneyhandler.service.RegisterService;
import com.moneyhandler.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Handles user registration logic for MoneyHandler.
 */
@WebServlet(urlPatterns = {"/register" , "/"})
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final RegisterService registerService = new RegisterService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Validate inputs
			String error = validateForm(req);
			if (error != null) {
				handleError(req, resp, error);
				return;
			}

			// Extract user model
			UserModel user = extractUser(req);

			// Call service to insert into DB
			Boolean isRegistered = registerService.registerUser(user);

			if (isRegistered == null) {
				handleError(req, resp, "Server error. Please try again later.");
			} else if (isRegistered) {
				handleSuccess(req, resp, "Account successfully created!");
			} else {
				handleError(req, resp, "Registration failed. Please try again.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			handleError(req, resp, "Unexpected error: " + e.getMessage());
		}
	}

	private String validateForm(HttpServletRequest req) {
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String dobStr = req.getParameter("dob");
		String phone = req.getParameter("phone");

		if (ValidationUtil.isNullOrEmpty(username)) return "Username is required.";
		if (ValidationUtil.isNullOrEmpty(email)) return "Email is required.";
		if (ValidationUtil.isNullOrEmpty(dobStr)) return "Date of birth is required.";
		if (ValidationUtil.isNullOrEmpty(phone)) return "Phone number is required.";

		if (!ValidationUtil.isValidEmail(email)) return "Invalid email format.";
		if (!ValidationUtil.isValidPhoneNumber(phone)) return "Phone number must be 10 digits and start with 98.";

		try {
			LocalDate dob = LocalDate.parse(dobStr);
			if (!ValidationUtil.isAgeAtLeast16(dob)) return "You must be at least 12 years old to register.";
		} catch (Exception e) {
			return "Invalid date format.";
		}

		return null; // all good
	}

	private UserModel extractUser(HttpServletRequest req) {
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		LocalDate dob = LocalDate.parse(req.getParameter("dob"));
		String phone = req.getParameter("phone");

		return new UserModel(username, email, dob, phone);
	}

	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("error", message);
		req.setAttribute("username", req.getParameter("username"));
		req.setAttribute("email", req.getParameter("email"));
		req.setAttribute("dob", req.getParameter("dob"));
		req.setAttribute("phone", req.getParameter("phone"));
		req.getRequestDispatcher("/pages/register.jsp").forward(req, resp);
	}

	private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("success", message);
		req.getRequestDispatcher("/pages/register.jsp").forward(req, resp);
	}
}
