package com.dental.system.service;

import com.dental.system.dao.InAppointmentDAO;
import com.dental.system.model.Appointment;

import java.util.List;

public class AppointmentService implements InAppointmentService {

    private final InAppointmentDAO appointmentDAO;

    public AppointmentService(InAppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    @Override
    public boolean addAppointment(Appointment appointment) {
        if (appointment == null) {
            return false;
        }

        if (appointment.getPatientId() <= 0) {
            return false;
        }

        if (appointment.getAppointmentDate() == null) {
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

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }

    @Override
    public Appointment getAppointmentByNumber(String appointmentNumber) {
        return appointmentDAO.getAppointmentByNumber(appointmentNumber);
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        return appointmentDAO.getAppointmentById(appointmentId);
    }

    @Override
    public boolean updateAppointment(Appointment appointment) {
        return appointmentDAO.updateAppointment(appointment);
    }

    @Override
    public boolean deleteAppointment(int appointmentId) {
        return appointmentDAO.deleteAppointment(appointmentId);
    }

    @Override
    public boolean updateAppointmentStatus(int appointmentId, String status) {
        if (appointmentId <= 0) {
            return false;
        }

        if (status == null || status.trim().isEmpty()) {
            return false;
        }
        return appointmentDAO.updateAppointmentStatus(appointmentId, status);
    }


}
