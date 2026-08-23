package com.dental.system.dao;

import java.math.BigDecimal;
import com.dental.system.model.Appointment;
import java.util.List;

public interface InDashboardDAO {
    int getTotalPatients();

    int getTotalAppointments();

    int getAppointmentCountByStatus(String status);

    BigDecimal getTotalRevenue();

    List<Appointment> getRecentAppointments();
}
