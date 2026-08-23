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

    @Override
    public int getTotalPatients() {
        return dashboardDAO.getTotalPatients();
    }

    @Override
    public int getTotalAppointments() {
        return dashboardDAO.getTotalAppointments();
    }

    @Override
    public int getAppointmentCountByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return 0;
        }

        return dashboardDAO.getAppointmentCountByStatus(status);
    }

    @Override
    public BigDecimal getTotalRevenue() {
        return dashboardDAO.getTotalRevenue();
    }

    @Override
    public List<Appointment> getRecentAppointments() {
        return dashboardDAO.getRecentAppointments();
    }

}
