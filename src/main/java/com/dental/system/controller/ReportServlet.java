package com.dental.system.controller;

import com.dental.system.dao.ReportDAO;
import com.dental.system.model.Appointment;
import com.dental.system.model.Invoice;
import com.dental.system.service.InReportService;
import com.dental.system.service.ReportService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/reports")
public class ReportServlet extends HttpServlet {

    private final InReportService reportService;

    public ReportServlet() {
        this.reportService = new ReportService(new ReportDAO());
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        int scheduledCount =
                reportService.getAppointmentCountByStatus("Scheduled");

        int completedCount =
                reportService.getAppointmentCountByStatus("Completed");

        int cancelledCount =
                reportService.getAppointmentCountByStatus("Cancelled");

        BigDecimal totalRevenue =
                reportService.getTotalRevenue();

        List<Invoice> invoices =
                reportService.getAllInvoices();

        request.setAttribute("scheduledCount", scheduledCount);
        request.setAttribute("completedCount", completedCount);
        request.setAttribute("cancelledCount", cancelledCount);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("invoices", invoices);

        String startDateValue = request.getParameter("startDate");
        String endDateValue = request.getParameter("endDate");

        if (startDateValue != null && !startDateValue.isBlank()
                && endDateValue != null && !endDateValue.isBlank()) {

            try {

                LocalDate startDate = LocalDate.parse(startDateValue);
                LocalDate endDate = LocalDate.parse(endDateValue);

                if (startDate.isAfter(endDate)) {

                    request.setAttribute(
                            "dateError",
                            "Start date cannot be after end date."
                    );

                } else {

                    List<Appointment> appointments =
                            reportService.getAppointmentsByDateRange(
                                    startDate,
                                    endDate
                            );

                    request.setAttribute(
                            "filteredAppointments",
                            appointments
                    );
                }

            } catch (Exception e) {

                request.setAttribute(
                        "dateError",
                        "Please select valid dates."
                );
            }
        }

        request.getRequestDispatcher("/reports.jsp")
                .forward(request, response);
    }
}
