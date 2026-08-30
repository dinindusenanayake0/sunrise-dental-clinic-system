package com.dental.system.Filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "/dashboard",
        "/patients",
        "/appointments",
        "/invoice",
        "/invoice/*",
        "/invoices",
        "/reports",
        "/users",
        "/api/*",

        "/dashboard.jsp",
        "/patients.jsp",
        "/appointments.jsp",
        "/billing.jsp",
        "/invoice-view.jsp",
        "/invoices.jsp",
        "/reports.jsp",
        "/users.jsp",
        "/help.jsp",
        "/add-patient.jsp"
})
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {

        HttpServletRequest request =
                (HttpServletRequest) servletRequest;

        HttpServletResponse response =
                (HttpServletResponse) servletResponse;

        HttpSession session =
                request.getSession(false);

        if (session == null ||
                session.getAttribute("loggedUser") == null) {

            if (request.getRequestURI().contains("/api/")) {

                response.setStatus(
                        HttpServletResponse.SC_UNAUTHORIZED
                );

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                response.getWriter().print(
                        "{\"message\":\"Unauthorized access\"}"
                );

            } else {

                response.sendRedirect(
                        request.getContextPath() + "/login.jsp"
                );
            }

            return;
        }

        filterChain.doFilter(
                servletRequest,
                servletResponse
        );
    }
}