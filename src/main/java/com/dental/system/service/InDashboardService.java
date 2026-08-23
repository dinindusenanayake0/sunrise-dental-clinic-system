package com.dental.system.service;

import java.math.BigDecimal;
import com.dental.system.model.Appointment;
import java.util.List;

public interface InDashboardService {

    int getTotalPatients();

    int getTotalAppointments();

    int getAppointmentCountByStatus(String status);

    BigDecimal getTotalRevenue();

    List<Appointment> getRecentAppointments();

}
