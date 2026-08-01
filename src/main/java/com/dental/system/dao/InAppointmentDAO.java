package com.dental.system.dao;

import com.dental.system.model.Appointment;

import java.util.List;

public interface InAppointmentDAO {

    boolean addAppointment(Appointment appointment);

    List<Appointment> getAllAppointments();

    Appointment getAppointmentByNumber(String appointmentNumber);

    Appointment getAppointmentById(int appointmentId);

    boolean updateAppointment(Appointment appointment);

    boolean deleteAppointment(int appointmentId);

    String generateNextAppointmentNumber();

    boolean updateAppointmentStatus(int appointmentId, String status);

}
