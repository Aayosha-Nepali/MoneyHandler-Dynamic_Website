package com.moneyhandler.filter;

import com.moneyhandler.model.UserModel;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Filter to enforce session-based authentication.
 */
@WebFilter(urlPatterns = {"/user/*", "/admin/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        UserModel user = (session != null) ? (UserModel) session.getAttribute("loggedInUser") : null;

        String path = req.getRequestURI();

        if (user == null) {
            // Not logged in
            resp.sendRedirect(req.getContextPath() + "/login");
        } else if (path.startsWith(req.getContextPath() + "/admin") &&
                !"admin@moneyhandler.com".equalsIgnoreCase(user.getEmail())) {
            // Unauthorized admin access — force logout or send to login
            session.invalidate(); // optional
            resp.sendRedirect(req.getContextPath() + "/login?unauthorized=true");

        } else {
            chain.doFilter(request, response);
        }
    }
}
