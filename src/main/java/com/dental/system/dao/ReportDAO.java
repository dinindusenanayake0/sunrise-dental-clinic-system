package com.dental.system.dao;

import com.dental.system.model.Appointment;
import com.dental.system.model.Invoice;
import com.dental.system.util.DBCon;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO implements InReportDAO {

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

    @Override
    public List<Appointment> getAppointmentsByDateRange(
            LocalDate startDate,
            LocalDate endDate
    ) {

        List<Appointment> appointments = new ArrayList<>();

        String sql = "SELECT * FROM appointments WHERE appointment_date BETWEEN ? AND ? ORDER BY appointment_date DESC, appointment_time DESC ";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, java.sql.Date.valueOf(startDate));
            statement.setDate(2, java.sql.Date.valueOf(endDate));

            try (ResultSet resultSet = statement.executeQuery()) {

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
            }

        } catch (SQLException e) {
            System.out.println(
                    "Failed to retrieve appointments by date range: "
                            + e.getMessage()
            );
            e.printStackTrace();
        }

        return appointments;
    }

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
                    "Failed to calculate report revenue: "
                            + e.getMessage()
            );
            e.printStackTrace();
        }

        return BigDecimal.ZERO;
    }

    @Override
    public List<Invoice> getAllInvoices() {

        List<Invoice> invoices = new ArrayList<>();

        String sql = "SELECT * FROM invoices ORDER BY invoice_id DESC ";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Invoice invoice = new Invoice();

                invoice.setInvoiceId(
                        resultSet.getInt("invoice_id")
                );

                invoice.setInvoiceNumber(
                        resultSet.getString("invoice_number")
                );

                invoice.setAppointmentId(
                        resultSet.getInt("appointment_id")
                );

                invoice.setDoctorFee(
                        resultSet.getBigDecimal("doctor_fee")
                );

                invoice.setHospitalFee(
                        resultSet.getBigDecimal("hospital_fee")
                );

                invoice.setAdditionalFee(
                        resultSet.getBigDecimal("additional_fee")
                );

                invoice.setDiscount(
                        resultSet.getBigDecimal("discount")
                );

                invoice.setTotalAmount(
                        resultSet.getBigDecimal("total_amount")
                );

                invoice.setAmountPaid(
                        resultSet.getBigDecimal("amount_paid")
                );

                invoice.setBalanceAmount(
                        resultSet.getBigDecimal("balance_amount")
                );

                invoice.setPaymentMethod(
                        resultSet.getString("payment_method")
                );

                invoice.setPaymentStatus(
                        resultSet.getString("payment_status")
                );

                Timestamp invoiceTimestamp =
                        resultSet.getTimestamp("invoice_date");

                if (invoiceTimestamp != null) {
                    invoice.setInvoiceDate(
                            invoiceTimestamp.toLocalDateTime()
                    );
                }

                invoice.setRemarks(
                        resultSet.getString("remarks")
                );

                invoices.add(invoice);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Failed to retrieve invoice report: "
                            + e.getMessage()
            );
            e.printStackTrace();
        }

        return invoices;
    }

}
