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

        String action = request.getParameter("action");
        String idValue = request.getParameter("id");

        if ("cancel".equals(action) && idValue != null) {

            try {
                int appointmentId = Integer.parseInt(idValue);

                Appointment appointment =
                        appointmentService.getAppointmentById(appointmentId);

                if (appointment == null) {
                    response.sendRedirect(
                            request.getContextPath()
                                    + "/appointments?cancelError=true"
                    );
                    return;
                }

                if (!"Scheduled".equalsIgnoreCase(
                        appointment.getStatus()
                )) {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/appointments?cancelError=true"
                    );
                    return;
                }


                boolean cancelled =
                        appointmentService.updateAppointmentStatus(
                                appointmentId,
                                "Cancelled"
                        );

                if (cancelled) {
                    response.sendRedirect(
                            request.getContextPath()
                                    + "/appointments?cancelSuccess=true"
                    );
                } else {
                    response.sendRedirect(
                            request.getContextPath()
                                    + "/appointments?cancelError=true"
                    );
                }

                return;

            } catch (NumberFormatException e) {
                response.sendRedirect(
                        request.getContextPath()
                                + "/appointments?cancelError=true"
                );
                return;
            }
        }


        if ("edit".equalsIgnoreCase(action)) {

            try {
                int appointmentId =
                        Integer.parseInt(request.getParameter("id"));

                Appointment editAppointment =
                        appointmentService.getAppointmentById(appointmentId);

                if (editAppointment != null) {
                    request.setAttribute(
                            "editAppointment",
                            editAppointment
                    );
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }


        List<Patient> patients =
                patientService.getAllPatients();


        List<Appointment> appointments =
                appointmentService.getAllAppointments();

        request.setAttribute("patients", patients);
        request.setAttribute("appointments", appointments);

        request.getRequestDispatcher("/appointments.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String formAction = request.getParameter("formAction");

            if ("cancel".equalsIgnoreCase(formAction)) {

                int appointmentId = Integer.parseInt(
                        request.getParameter("appointmentId")
                );

                Appointment existingAppointment =
                        appointmentService.getAppointmentById(appointmentId);

                if (existingAppointment == null) {
                    response.sendRedirect(
                            request.getContextPath()
                                    + "/appointments?error=notFound"
                    );
                    return;
                }

                if (!"Scheduled".equalsIgnoreCase(
                        existingAppointment.getStatus()
                )) {
                    response.sendRedirect(
                            request.getContextPath()
                                    + "/appointments?error=cancelFailed"
                    );
                    return;
                }

                boolean cancelled =
                        appointmentService.updateAppointmentStatus(
                                appointmentId,
                                "Cancelled"
                        );

                if (cancelled) {
                    response.sendRedirect(
                            request.getContextPath()
                                    + "/appointments?success=cancelled"
                    );
                } else {
                    response.sendRedirect(
                            request.getContextPath()
                                    + "/appointments?error=cancelFailed"
                    );
                }

                return;
            }

            int patientId = Integer.parseInt(request.getParameter("patientId"));
            LocalDate appointmentDate = LocalDate.parse(request.getParameter("appointmentDate"));
            LocalTime appointmentTime = LocalTime.parse(request.getParameter("appointmentTime"));
            String dentistName = request.getParameter("dentistName");
            String treatmentType = request.getParameter("treatmentType");
            String notes = request.getParameter("notes");

            if ("update".equalsIgnoreCase(formAction)) {

                int appointmentId =
                        Integer.parseInt(
                                request.getParameter("appointmentId")
                        );

                Appointment existingAppointment =
                        appointmentService.getAppointmentById(appointmentId);

                if (existingAppointment == null) {

                    response.sendRedirect(
                            "appointments?error=notFound"
                    );
                    return;
                }

                if (!"Scheduled".equalsIgnoreCase(
                        existingAppointment.getStatus()
                )) {

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/appointments?error=cancelledEdit"
                    );
                    return;
                }

                Appointment appointment =
                        new Appointment(
                                appointmentId,
                                existingAppointment.getAppointmentNumber(),
                                patientId,
                                appointmentDate,
                                appointmentTime,
                                dentistName,
                                treatmentType,
                                existingAppointment.getStatus(),
                                notes
                        );

                boolean updated =
                        appointmentService.updateAppointment(appointment);

                if (updated) {
                    response.sendRedirect(
                            "appointments?success=updated"
                    );
                } else {
                    response.sendRedirect(
                            "appointments?error=updateFailed"
                    );
                }

                return;
            }

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
