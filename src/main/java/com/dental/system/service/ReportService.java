package com.dental.system.service;

import com.dental.system.dao.InReportDAO;
import com.dental.system.model.Appointment;
import com.dental.system.model.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class ReportService implements InReportService {

    private final InReportDAO reportDAO;

    public ReportService(InReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    // Get appointment count by status
    @Override
    public int getAppointmentCountByStatus(String status) {

        if (status == null || status.trim().isEmpty()) {
            return 0;
        }

        return reportDAO.getAppointmentCountByStatus(status);
    }

    // Get appointments by date range
    @Override
    public List<Appointment> getAppointmentsByDateRange(
            LocalDate startDate,
            LocalDate endDate
    ) {

        if (startDate == null || endDate == null) {
            return Collections.emptyList();
        }

        if (startDate.isAfter(endDate)) {
            return Collections.emptyList();
        }

        return reportDAO.getAppointmentsByDateRange(
                startDate,
                endDate
        );
    }

    // Get total revenue
    @Override
    public BigDecimal getTotalRevenue() {
        return reportDAO.getTotalRevenue();
    }

    // Get all invoices
    @Override
    public List<Invoice> getAllInvoices() {
        return reportDAO.getAllInvoices();
    }
}
