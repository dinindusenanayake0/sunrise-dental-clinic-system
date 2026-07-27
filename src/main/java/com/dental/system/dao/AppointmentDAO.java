package com.dental.system.dao;

import com.dental.system.model.Appointment;
import com.dental.system.util.DBCon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AppointmentDAO implements InAppointmentDAO {

    @Override
    public boolean addAppointment(Appointment appointment) {

        String sql = """
                INSERT INTO appointments
                (
                    appointment_number,
                    patient_id,
                    appointment_date,
                    appointment_time,
                    dentist_name,
                    treatment_type,
                    status,
                    notes
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, appointment.getAppointmentNumber());
            statement.setInt(2, appointment.getPatientId());
            statement.setDate(3, Date.valueOf(appointment.getAppointmentDate()));
            statement.setTime(4, Time.valueOf(appointment.getAppointmentTime()));
            statement.setString(5, appointment.getDentistName());
            statement.setString(6, appointment.getTreatmentType());
            statement.setString(7, appointment.getStatus());
            statement.setString(8, appointment.getNotes());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Add appointment failed.");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();

        String sql = """
            SELECT *
            FROM appointments
            ORDER BY appointment_id DESC
            """;

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Appointment appointment = new Appointment(
                        resultSet.getInt("appointment_id"),
                        resultSet.getString("appointment_number"),
                        resultSet.getInt("patient_id"),
                        resultSet.getDate("appointment_date").toLocalDate(),
                        resultSet.getTime("appointment_time").toLocalTime(),
                        resultSet.getString("dentist_name"),
                        resultSet.getString("treatment_type"),
                        resultSet.getString("status"),
                        resultSet.getString("notes")
                );

                appointments.add(appointment);
            }

        } catch (SQLException e) {
            System.out.println("Failed to retrieve appointments.");
            e.printStackTrace();
        }

        return appointments;
    }

    @Override
    public Appointment getAppointmentByNumber(String appointmentNumber) {
        return null;
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        return null;
    }

    @Override
    public boolean updateAppointment(Appointment appointment) {
        return false;
    }

    @Override
    public boolean deleteAppointment(int appointmentId) {
        return false;
    }

    @Override
    public String generateNextAppointmentNumber() {

        String sql = """
            SELECT COALESCE(MAX(appointment_id), 0) + 1 AS next_id
            FROM appointments
            """;

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {

                int nextId = resultSet.getInt("next_id");

                return String.format("APT-%04d", nextId);
            }

        } catch (SQLException e) {
            System.out.println("Failed to generate appointment number.");
            e.printStackTrace();
        }

        return null;
    }
}
