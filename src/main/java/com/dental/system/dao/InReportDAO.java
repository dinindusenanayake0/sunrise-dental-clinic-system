package com.dental.system.dao;

import com.dental.system.model.Appointment;
import com.dental.system.model.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface InReportDAO {

    int getAppointmentCountByStatus(String status);

    List<Appointment> getAppointmentsByDateRange(LocalDate startDate, LocalDate endDate);

    BigDecimal getTotalRevenue();

    List<Invoice> getAllInvoices();

}
