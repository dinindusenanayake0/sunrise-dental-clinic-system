package com.dental.system.dao;

import com.dental.system.util.DBCon;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dental.system.model.Appointment;
import java.util.ArrayList;
import java.util.List;


public class DashboardDAO implements InDashboardDAO {

    // Get total patient count
    @Override
    public int getTotalPatients() {

        String sql = "SELECT COUNT(*) AS total FROM patients";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("total");
            }

        } catch (SQLException e) {
            System.out.println(
                    "Failed to count patients: " + e.getMessage()
            );
            e.printStackTrace();
        }

        return 0;
    }


    // Get total appointment count
    @Override
    public int getTotalAppointments() {

        String sql = "SELECT COUNT(*) AS total FROM appointments";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("total");
            }

        } catch (SQLException e) {
            System.out.println(
                    "Failed to count appointments: " + e.getMessage()
            );
            e.printStackTrace();
        }

        return 0;
    }


    // Get appointment count by status
    @Override
    public int getAppointmentCountByStatus(String status) {

        String sql = "SELECT COUNT(*) AS total FROM appointments WHERE status = ? ";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("total");
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Failed to count appointments by status: "
                            + e.getMessage()
            );
            e.printStackTrace();
        }

        return 0;
    }


    // Calculate total revenue
    @Override
    public BigDecimal getTotalRevenue() {

        String sql = "SELECT COALESCE(SUM(amount_paid), 0) AS total_revenue FROM invoices ";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getBigDecimal("total_revenue");
            }

        } catch (SQLException e) {
            System.out.println(
                    "Failed to calculate total revenue: "
                            + e.getMessage()
            );
            e.printStackTrace();
        }

        return BigDecimal.ZERO;
    }


    // Get the latest five appointments
    @Override
    public List<Appointment> getRecentAppointments() {

        List<Appointment> appointments = new ArrayList<>();

        String sql = "SELECT * FROM appointments ORDER BY appointment_id DESC LIMIT 5 ";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Appointment appointment = new Appointment();

                appointment.setAppointmentId(
                        resultSet.getInt("appointment_id")
                );

                appointment.setAppointmentNumber(
                        resultSet.getString("appointment_number")
                );

                appointment.setPatientId(
                        resultSet.getInt("patient_id")
                );

                appointment.setAppointmentDate(
                        resultSet.getDate("appointment_date").toLocalDate()
                );

                appointment.setAppointmentTime(
                        resultSet.getTime("appointment_time").toLocalTime()
                );

                appointment.setDentistName(
                        resultSet.getString("dentist_name")
                );

                appointment.setTreatmentType(
                        resultSet.getString("treatment_type")
                );

                appointment.setStatus(
                        resultSet.getString("status")
                );

                appointment.setNotes(
                        resultSet.getString("notes")
                );

                appointments.add(appointment);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Failed to retrieve recent appointments: "
                            + e.getMessage()
            );
            e.printStackTrace();
        }

        return appointments;
    }
}
