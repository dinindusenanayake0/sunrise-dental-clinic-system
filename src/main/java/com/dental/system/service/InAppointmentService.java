package com.dental.system.service;

import com.dental.system.model.Appointment;

import java.util.List;

public interface InAppointmentService {

    boolean addAppointment(Appointment appointment);

    List<Appointment> getAllAppointments();

    Appointment getAppointmentByNumber(String appointmentNumber);

    Appointment getAppointmentById(int appointmentId);

    boolean updateAppointment(Appointment appointment);

    boolean deleteAppointment(int appointmentId);

}
