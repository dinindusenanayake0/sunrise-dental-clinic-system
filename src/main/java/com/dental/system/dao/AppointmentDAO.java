package com.dental.system.dao;

import com.dental.system.model.Appointment;
import com.dental.system.util.DBCon;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AppointmentDAO implements InAppointmentDAO {

    // Add a new appointment
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

    // Get all appointments
    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();

        String sql = "SELECT * FROM appointments ORDER BY appointment_id DESC";

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

    // Get appointment by appointment number
    @Override
    public Appointment getAppointmentByNumber(String appointmentNumber) {

        String sql =
                "SELECT * FROM appointments WHERE appointment_number = ?";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    appointmentNumber
            );

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Appointment(
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
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to retrieve appointment by number: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return null;
    }

    // Get appointment by ID
    @Override
    public Appointment getAppointmentById(int appointmentId) {

        String sql = "SELECT * FROM appointments WHERE appointment_id = ?";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, appointmentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {

                    return new Appointment(
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
                }
            }

        } catch (SQLException e) {
            System.out.println("Failed to retrieve appointment by ID : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Update appointment details
    @Override
    public boolean updateAppointment(Appointment appointment) {

        String sql = "UPDATE appointments SET patient_id = ?, appointment_date = ?, appointment_time = ?, dentist_name = ?, treatment_type = ?, status = ?, notes = ? WHERE appointment_id = ? ";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    appointment.getPatientId()
            );

            statement.setDate(
                    2,
                    Date.valueOf(
                            appointment.getAppointmentDate()
                    )
            );

            statement.setTime(
                    3,
                    Time.valueOf(
                            appointment.getAppointmentTime()
                    )
            );

            statement.setString(
                    4,
                    appointment.getDentistName()
            );

            statement.setString(
                    5,
                    appointment.getTreatmentType()
            );

            statement.setString(
                    6,
                    appointment.getStatus()
            );

            statement.setString(
                    7,
                    appointment.getNotes()
            );

            statement.setInt(
                    8,
                    appointment.getAppointmentId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Failed to update appointment: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return false;
    }

    // Update appointment status
    @Override
    public boolean updateAppointmentStatus(int appointmentId, String status) {

        String sql = "UPDATE appointments SET status = ? WHERE appointment_id = ?";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, appointmentId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Failed to update appointment status: : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    // Generate the next appointment number
    @Override
    public String generateNextAppointmentNumber() {

        String sql = "SELECT COALESCE(MAX(appointment_id), 0) + 1 AS next_id FROM appointments";

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

    @Override
    public boolean existsScheduledAppointment(
            String dentistName,
            LocalDate appointmentDate,
            LocalTime appointmentTime
    ) {

        String sql = "SELECT COUNT(*) FROM appointments " +
                     "WHERE dentist_name = ? " +
                     "AND appointment_date = ? " +
                     "AND appointment_time = ? " +
                     "AND status = 'Scheduled'";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, dentistName);
            statement.setDate(2, java.sql.Date.valueOf(appointmentDate));
            statement.setTime(3, java.sql.Time.valueOf(appointmentTime));

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
