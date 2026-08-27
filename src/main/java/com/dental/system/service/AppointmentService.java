package com.dental.system.service;

import com.dental.system.dao.InAppointmentDAO;
import com.dental.system.model.Appointment;

import java.util.List;
import java.time.LocalDate;

public class AppointmentService implements InAppointmentService {

    private final InAppointmentDAO appointmentDAO;

    public AppointmentService(InAppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    // Add a appointment
    @Override
    public boolean addAppointment(Appointment appointment) {
        if (appointment == null) {
            return false;
        }

        if (appointment.getPatientId() <= 0) {
            return false;
        }

        if (!isValidAppointmentDate(
                appointment.getAppointmentDate()
        )) {
            return false;
        }

        if (appointment.getAppointmentTime() == null) {
            return false;
        }

        if (appointment.getDentistName() == null ||
                appointment.getDentistName().trim().isEmpty()) {
            return false;
        }

        if (appointment.getTreatmentType() == null ||
                appointment.getTreatmentType().trim().isEmpty()) {
            return false;
        }


        // Generate Appointment Number
        String appointmentNumber =
                appointmentDAO.generateNextAppointmentNumber();
        if (appointmentNumber == null) {
            return false;
        }
        appointment.setAppointmentNumber(appointmentNumber);


        // Default Status
        if (appointment.getStatus() == null ||
                appointment.getStatus().trim().isEmpty()) {

            appointment.setStatus("Scheduled");
        }
        return appointmentDAO.addAppointment(appointment);
    }

    // Get all appointments
    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }


    // Get appointment by number
    @Override
    public Appointment getAppointmentByNumber(String appointmentNumber) {
        return appointmentDAO.getAppointmentByNumber(appointmentNumber);
    }

    // Get appointment by ID
    @Override
    public Appointment getAppointmentById(int appointmentId) {
        return appointmentDAO.getAppointmentById(appointmentId);
    }


    // Update appointment
    @Override
    public boolean updateAppointment(Appointment appointment) {

        if (appointment == null) {
            return false;
        }

        if (appointment.getAppointmentId() <= 0) {
            return false;
        }

        if (appointment.getPatientId() <= 0) {
            return false;
        }

        if (appointment.getAppointmentNumber() == null ||
                appointment.getAppointmentNumber().trim().isEmpty()) {
            return false;
        }

        if (!isValidAppointmentDate(
                appointment.getAppointmentDate()
        )) {
            return false;
        }

        if (appointment.getAppointmentTime() == null) {
            return false;
        }

        if (appointment.getDentistName() == null ||
                appointment.getDentistName().trim().isEmpty()) {
            return false;
        }

        if (appointment.getTreatmentType() == null ||
                appointment.getTreatmentType().trim().isEmpty()) {
            return false;
        }

        if (appointment.getStatus() == null ||
                appointment.getStatus().trim().isEmpty()) {

            appointment.setStatus("Scheduled");

        } else if (!isValidStatus(appointment.getStatus())) {
            return false;
        }

        return appointmentDAO.updateAppointment(appointment);
    }


    // Update appointment status
    @Override
    public boolean updateAppointmentStatus(int appointmentId, String status) {
        if (appointmentId <= 0) {
            return false;
        }

        if (!isValidStatus(status)) {
            return false;
        }
        return appointmentDAO.updateAppointmentStatus(appointmentId, status);
    }


    // Validate appointment date
    private boolean isValidAppointmentDate(
            LocalDate appointmentDate
    ) {

        if (appointmentDate == null) {
            return false;
        }

        return !appointmentDate.isBefore(
                LocalDate.now()
        );
    }



    private boolean isValidStatus(
            String status
    ) {

        if (status == null) {
            return false;
        }

        return "Scheduled".equalsIgnoreCase(status.trim())
                || "Completed".equalsIgnoreCase(status.trim())
                || "Cancelled".equalsIgnoreCase(status.trim());
    }
}
