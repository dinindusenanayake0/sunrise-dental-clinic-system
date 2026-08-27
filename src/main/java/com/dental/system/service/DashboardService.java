package com.dental.system.service;

import com.dental.system.dao.InDashboardDAO;
import com.dental.system.model.Appointment;
import java.util.List;
import java.math.BigDecimal;

public class DashboardService implements InDashboardService {

    private final InDashboardDAO dashboardDAO;

    public DashboardService(InDashboardDAO dashboardDAO) {
        this.dashboardDAO = dashboardDAO;
    }

    // Get total patient count
    @Override
    public int getTotalPatients() {
        return dashboardDAO.getTotalPatients();
    }

    // Get total appointment count
    @Override
    public int getTotalAppointments() {
        return dashboardDAO.getTotalAppointments();
    }

    // Get appointment count by status
    @Override
    public int getAppointmentCountByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return 0;
        }

        return dashboardDAO.getAppointmentCountByStatus(status);
    }

    // Get total revenue
    @Override
    public BigDecimal getTotalRevenue() {
        return dashboardDAO.getTotalRevenue();
    }

    // Get recent appointments
    @Override
    public List<Appointment> getRecentAppointments() {
        return dashboardDAO.getRecentAppointments();
    }

}
