package com.dental.system.service;

import com.dental.system.model.Appointment;
import com.dental.system.model.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface InReportService {

    int getAppointmentCountByStatus(String status);

    List<Appointment> getAppointmentsByDateRange(LocalDate startDate, LocalDate endDate);

    BigDecimal getTotalRevenue();

    List<Invoice> getAllInvoices();

}
