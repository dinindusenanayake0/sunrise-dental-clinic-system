package com.dental.system.controller;

import com.dental.system.dao.AppointmentDAO;
import com.dental.system.model.Appointment;
import com.dental.system.service.AppointmentService;
import com.dental.system.service.InAppointmentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/appointments")
public class AppointmentApiServlet extends HttpServlet {

    private final InAppointmentService appointmentService;

    public AppointmentApiServlet() {
        this.appointmentService = new AppointmentService(new AppointmentDAO());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        response.setCharacterEncoding("UTF-8");

        String appointmentNumber = request.getParameter("number");

        if (appointmentNumber != null && !appointmentNumber.trim().isEmpty()) {

            Appointment appointment = appointmentService.getAppointmentByNumber(appointmentNumber.trim());

            PrintWriter out = response.getWriter();

            if (appointment == null) {

                response.setStatus(HttpServletResponse.SC_NOT_FOUND);

                out.print("{\"message\":\"Appointment not found\"}");

                out.flush();
                return;
            }

            StringBuilder json = new StringBuilder();

            json.append("{");

            json.append("\"appointmentId\":")
                    .append(appointment.getAppointmentId())
                    .append(",");

            json.append("\"appointmentNumber\":\"")
                    .append(escapeJson(
                            appointment.getAppointmentNumber()
                    ))
                    .append("\",");

            json.append("\"patientId\":")
                    .append(appointment.getPatientId())
                    .append(",");

            json.append("\"appointmentDate\":\"")
                    .append(appointment.getAppointmentDate())
                    .append("\",");

            json.append("\"appointmentTime\":\"")
                    .append(appointment.getAppointmentTime())
                    .append("\",");

            json.append("\"dentistName\":\"")
                    .append(escapeJson(
                            appointment.getDentistName()
                    ))
                    .append("\",");

            json.append("\"treatmentType\":\"")
                    .append(escapeJson(
                            appointment.getTreatmentType()
                    ))
                    .append("\",");

            json.append("\"status\":\"")
                    .append(escapeJson(
                            appointment.getStatus()
                    ))
                    .append("\",");

            json.append("\"notes\":\"")
                    .append(escapeJson(
                            appointment.getNotes()
                    ))
                    .append("\"");

            json.append("}");

            out.print(json);
            out.flush();
            return;
        }

        List<Appointment> appointments = appointmentService.getAllAppointments();

        PrintWriter out = response.getWriter();

        StringBuilder json = new StringBuilder();

        json.append("[");

        for (int i = 0; i < appointments.size(); i++) {

            Appointment appointment = appointments.get(i);

            json.append("{");

            json.append("\"appointmentId\":")
                    .append(appointment.getAppointmentId())
                    .append(",");

            json.append("\"appointmentNumber\":\"")
                    .append(escapeJson(
                            appointment.getAppointmentNumber()
                    ))
                    .append("\",");

            json.append("\"patientId\":")
                    .append(appointment.getPatientId())
                    .append(",");

            json.append("\"appointmentDate\":\"")
                    .append(appointment.getAppointmentDate())
                    .append("\",");

            json.append("\"appointmentTime\":\"")
                    .append(appointment.getAppointmentTime())
                    .append("\",");

            json.append("\"dentistName\":\"")
                    .append(escapeJson(
                            appointment.getDentistName()
                    ))
                    .append("\",");

            json.append("\"treatmentType\":\"")
                    .append(escapeJson(
                            appointment.getTreatmentType()
                    ))
                    .append("\",");

            json.append("\"status\":\"")
                    .append(escapeJson(
                            appointment.getStatus()
                    ))
                    .append("\",");

            json.append("\"notes\":\"")
                    .append(escapeJson(
                            appointment.getNotes()
                    ))
                    .append("\"");

            json.append("}");

            if (i < appointments.size() - 1) {

                json.append(",");
            }
        }

        json.append("]");

        out.print(json);
        out.flush();
    }

    private String escapeJson(String value) {

        if (value == null) {return "";}

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}