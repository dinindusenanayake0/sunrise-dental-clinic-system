package com.dental.system.controller;

import com.dental.system.dao.AppointmentDAO;
import com.dental.system.dao.InvoiceDAO;
import com.dental.system.dao.PatientDAO;
import com.dental.system.model.Appointment;
import com.dental.system.model.Invoice;
import com.dental.system.model.Patient;
import com.dental.system.service.AppointmentService;
import com.dental.system.service.InAppointmentService;
import com.dental.system.service.InInvoiceService;
import com.dental.system.service.InPatientService;
import com.dental.system.service.InvoiceService;
import com.dental.system.service.PatientService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/invoice")
public class InvoiceServlet extends HttpServlet {

    private final InInvoiceService invoiceService;
    private final InAppointmentService appointmentService;
    private final InPatientService patientService;

    public InvoiceServlet() {

        this.invoiceService = new InvoiceService(new InvoiceDAO());
        this.appointmentService = new AppointmentService(new AppointmentDAO());
        this.patientService = new PatientService(new PatientDAO());
    }

    // Load invoice page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String appointmentIdValue = request.getParameter("appointmentId");

            if (appointmentIdValue == null || appointmentIdValue.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/appointments?invoiceError=invalidId");
                return;
            }

            int appointmentId = Integer.parseInt(appointmentIdValue);

            Invoice existingInvoice =
                    invoiceService.getInvoiceByAppointmentId(appointmentId);


            // Check existing invoice
            if (existingInvoice != null) {

                if ("Paid".equalsIgnoreCase(
                        existingInvoice.getPaymentStatus())) {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/invoice/view?appointmentId="
                                    + appointmentId
                    );
                    return;
                }

                request.setAttribute(
                        "existingInvoice",
                        existingInvoice
                );
            }

            Appointment appointment = appointmentService.getAppointmentById(appointmentId);

            if (appointment == null) {
                response.sendRedirect(request.getContextPath() + "/appointments?invoiceError=appointmentNotFound");
                return;
            }

            BigDecimal treatmentCost =
                    ((InvoiceService) invoiceService)
                            .calculateTreatmentCost(
                                    appointment.getTreatmentType()
                            );

            request.setAttribute(
                    "treatmentCost",
                    treatmentCost
            );

            Patient patient = patientService.getPatientById(appointment.getPatientId());

            if (patient == null) {

                response.sendRedirect(request.getContextPath() + "/appointments?invoiceError=patientNotFound");
                return;
            }
            request.setAttribute("appointment", appointment);
            request.setAttribute("patient", patient);
            request.getRequestDispatcher("/billing.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/appointments?invoiceError=invalidId");
        }
    }


    // Handle invoice submit
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));

            Appointment appointment =
                    appointmentService.getAppointmentById(appointmentId);

            if (appointment == null) {
                response.sendRedirect(
                        request.getContextPath()
                                + "/appointments?invoiceError=appointmentNotFound"
                );
                return;
            }

            BigDecimal treatmentCost =
                    ((InvoiceService) invoiceService)
                            .calculateTreatmentCost(
                                    appointment.getTreatmentType()
                            );


            BigDecimal doctorFee = parseAmount(request.getParameter("doctorFee"));
            BigDecimal hospitalFee = parseAmount(request.getParameter("hospitalFee"));
            BigDecimal additionalFee = parseAmount(request.getParameter("additionalFee"));
            BigDecimal discount = parseAmount(request.getParameter("discount"));
            BigDecimal amountPaid = parseAmount(request.getParameter("amountPaid"));
            String paymentMethod = request.getParameter("paymentMethod");
            String remarks = request.getParameter("remarks");


            Invoice invoice = new Invoice();

            invoice.setAppointmentId(appointmentId);
            invoice.setTreatmentCost(treatmentCost);
            invoice.setDoctorFee(doctorFee);
            invoice.setHospitalFee(hospitalFee);
            invoice.setAdditionalFee(additionalFee);
            invoice.setDiscount(discount);
            invoice.setAmountPaid(amountPaid);
            invoice.setPaymentMethod(paymentMethod);
            invoice.setRemarks(remarks);

            Invoice existingInvoice =
                    invoiceService.getInvoiceByAppointmentId(
                            invoice.getAppointmentId()
                    );


            // Update payment existing invoice
            if (existingInvoice != null) {

                if ("Paid".equalsIgnoreCase(
                        existingInvoice.getPaymentStatus())) {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/invoice/view?appointmentId="
                                    + appointmentId
                    );
                    return;
                }

                BigDecimal previousPaid =
                        existingInvoice.getAmountPaid() == null
                                ? BigDecimal.ZERO
                                : existingInvoice.getAmountPaid();

                BigDecimal newTotalPaid =
                        previousPaid.add(amountPaid);

                if (newTotalPaid.compareTo(
                        existingInvoice.getTotalAmount()) > 0) {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/invoice?appointmentId="
                                    + appointmentId
                                    + "&paymentError=exceeded"
                    );
                    return;
                }

                existingInvoice.setAmountPaid(newTotalPaid);
                existingInvoice.setPaymentMethod(paymentMethod);
                existingInvoice.setRemarks(remarks);

                boolean updated =
                        invoiceService.updateInvoice(existingInvoice);

                if (updated) {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/invoice/view?appointmentId="
                                    + appointmentId
                                    + "&paymentUpdated=true"
                    );

                } else {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/invoice?appointmentId="
                                    + appointmentId
                                    + "&paymentError=true"
                    );
                }

                return;
            }

            // Add invoice
            boolean added = invoiceService.addInvoice(invoice);

            if (added) {
                appointmentService.updateAppointmentStatus(invoice.getAppointmentId(),"Completed");
                response.sendRedirect(request.getContextPath() + "/invoice/view?appointmentId=" + invoice.getAppointmentId());
            } else {
                response.sendRedirect(request.getContextPath() + "/invoice?appointmentId=" + invoice.getAppointmentId() + "&error=true");
            }

        } catch (Exception e) {
            System.out.println("Invoice creation error: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/appointments?invoiceError=true");
        }
    }

    // Convert amount
    private BigDecimal parseAmount(String value) {
        if (value == null || value.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(value.trim());
    }
}



