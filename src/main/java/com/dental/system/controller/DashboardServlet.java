package com.dental.system.controller;

import com.dental.system.dao.DashboardDAO;
import com.dental.system.service.DashboardService;
import com.dental.system.service.InDashboardService;
import com.dental.system.model.Appointment;

import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final InDashboardService dashboardService;

    public DashboardServlet() {
        this.dashboardService = new DashboardService(new DashboardDAO());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int totalPatients = dashboardService.getTotalPatients();

        int totalAppointments = dashboardService.getTotalAppointments();

        int scheduledAppointments = dashboardService.getAppointmentCountByStatus("Scheduled");

        int completedAppointments = dashboardService.getAppointmentCountByStatus("Completed");

        int cancelledAppointments = dashboardService.getAppointmentCountByStatus("Cancelled");

        BigDecimal totalRevenue = dashboardService.getTotalRevenue();

        List<Appointment> recentAppointments = dashboardService.getRecentAppointments();

        request.setAttribute("totalPatients", totalPatients);
        request.setAttribute("totalAppointments", totalAppointments);
        request.setAttribute("scheduledAppointments", scheduledAppointments);
        request.setAttribute("completedAppointments", completedAppointments);
        request.setAttribute("cancelledAppointments", cancelledAppointments);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("recentAppointments", recentAppointments);

        request.getRequestDispatcher("/dashboard.jsp")
                .forward(request, response);
    }

}
