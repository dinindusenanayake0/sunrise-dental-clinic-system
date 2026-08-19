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

@WebServlet("/invoice/view")
public class InvoiceViewServlet extends HttpServlet {

    private final InInvoiceService invoiceService;
    private final InAppointmentService appointmentService;
    private final InPatientService patientService;

    public InvoiceViewServlet(){
        this.invoiceService = new InvoiceService(new InvoiceDAO());
        this.appointmentService = new AppointmentService(new AppointmentDAO());
        this.patientService = new PatientService(new PatientDAO());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));

            Invoice invoice = invoiceService.getInvoiceByAppointmentId(appointmentId);
            Appointment appointment = appointmentService.getAppointmentById(appointmentId);

            if (invoice == null || appointment == null){
                response.sendRedirect(request.getContextPath() + "/appointments?invoiceNotFound=true");
                return;
            }

            Patient patient = patientService.getPatientById(appointment.getPatientId());

            if (patient == null){
                response.sendRedirect(request.getContextPath() + "/appointments?patientNotFound=true");
                return;
            }

            request.setAttribute("invoice", invoice);
            request.setAttribute("patient", patient);
            request.setAttribute("appointment", appointment);

            request.getRequestDispatcher("/invoice-view.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/appointments");
        }
    }
}
