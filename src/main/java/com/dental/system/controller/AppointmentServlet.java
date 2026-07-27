package com.dental.system.controller;

import com.dental.system.dao.AppointmentDAO;
import com.dental.system.dao.PatientDAO;
import com.dental.system.model.Appointment;
import com.dental.system.model.Patient;
import com.dental.system.service.AppointmentService;
import com.dental.system.service.InAppointmentService;
import com.dental.system.service.InPatientService;
import com.dental.system.service.PatientService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/appointments")
public class AppointmentServlet extends HttpServlet {

    private final InAppointmentService appointmentService;
    private final InPatientService patientService;

    public AppointmentServlet() {
        this.appointmentService = new AppointmentService(new AppointmentDAO());

        this.patientService = new PatientService(new PatientDAO());
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<Patient> patients =
                patientService.getAllPatients();

        List<Appointment> appointments =
                appointmentService.getAllAppointments();

        request.setAttribute("patients", patients);
        request.setAttribute("appointments", appointments);

        request.getRequestDispatcher("/appointments.jsp")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int patientId = Integer.parseInt(request.getParameter("patientId"));
            LocalDate appointmentDate = LocalDate.parse(request.getParameter("appointmentDate"));
            LocalTime appointmentTime = LocalTime.parse(request.getParameter("appointmentTime"));
            String dentistName = request.getParameter("dentistName");
            String treatmentType = request.getParameter("treatmentType");
            String notes = request.getParameter("notes");

            Appointment appointment = new Appointment(
                    0, null, patientId, appointmentDate,
                    appointmentTime, dentistName, treatmentType, "Scheduled", notes);

            boolean added = appointmentService.addAppointment(appointment);

            if (added) {
                response.sendRedirect("appointments?success=true");
            } else {
                response.sendRedirect("appointments?success=false");
            }
        } catch (Exception e) {
            e.printStackTrace();

            response.sendRedirect("appointments?success=false");
        }
    }
}
