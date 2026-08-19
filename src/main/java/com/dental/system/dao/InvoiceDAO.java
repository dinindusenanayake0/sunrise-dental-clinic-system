package com.dental.system.dao;

import com.dental.system.model.Invoice;
import com.dental.system.util.DBCon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO implements InInvoiceDAO {

    //Add invoice
    @Override
    public boolean addInvoice(Invoice invoice) {
        String sql = """
                INSERT INTO invoices (
                    invoice_number,
                    appointment_id,
                    doctor_fee,
                    hospital_fee,
                    additional_fee,
                    discount,
                    total_amount,
                    amount_paid,
                    balance_amount,
                    payment_method,
                    payment_status,
                    remarks
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, invoice.getInvoiceNumber());
            statement.setInt(2, invoice.getAppointmentId());
            statement.setBigDecimal(3, invoice.getDoctorFee());
            statement.setBigDecimal(4, invoice.getHospitalFee());
            statement.setBigDecimal(5, invoice.getAdditionalFee());
            statement.setBigDecimal(6, invoice.getDiscount());
            statement.setBigDecimal(7, invoice.getTotalAmount());
            statement.setBigDecimal(8, invoice.getAmountPaid());
            statement.setBigDecimal(9, invoice.getBalanceAmount());
            statement.setString(10, invoice.getPaymentMethod());
            statement.setString(11, invoice.getPaymentStatus());
            statement.setString(12, invoice.getRemarks());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Failed to add invoice: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public List<Invoice> getAllInvoices() {

        List<Invoice> invoices = new ArrayList<>();

        String sql = """
            SELECT *
            FROM invoices
            ORDER BY invoice_id DESC
            """;

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
                    "Failed to retrieve invoices: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return invoices;
    }

    @Override
    public Invoice getInvoiceById(int invoiceId) {
        return null;
    }

    @Override
    public Invoice getInvoiceByNumber(String invoiceNumber) {
        return null;
    }

    @Override
    public Invoice getInvoiceByAppointmentId(int appointmentId) {

        String sql = "SELECT * FROM invoices WHERE appointment_id = ?";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, appointmentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    Invoice invoice = new Invoice();

                    invoice.setInvoiceId(resultSet.getInt("invoice_id"));
                    invoice.setInvoiceNumber(resultSet.getString("invoice_number"));
                    invoice.setAppointmentId(resultSet.getInt("appointment_id"));
                    invoice.setDoctorFee(resultSet.getBigDecimal("doctor_fee"));
                    invoice.setHospitalFee(resultSet.getBigDecimal("hospital_fee"));
                    invoice.setAdditionalFee(resultSet.getBigDecimal("additional_fee"));
                    invoice.setDiscount(resultSet.getBigDecimal("discount"));
                    invoice.setTotalAmount(resultSet.getBigDecimal("total_amount"));
                    invoice.setAmountPaid(resultSet.getBigDecimal("amount_paid"));
                    invoice.setBalanceAmount(resultSet.getBigDecimal("balance_amount"));
                    invoice.setPaymentMethod(resultSet.getString("payment_method"));
                    invoice.setPaymentStatus(resultSet.getString("payment_status"));

                    Timestamp invoiceTimestamp = resultSet.getTimestamp("invoice_date");

                    if (invoiceTimestamp != null) {
                        invoice.setInvoiceDate(invoiceTimestamp.toLocalDateTime());
                    }

                    invoice.setRemarks(resultSet.getString("remarks"));
                    return invoice;
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve invoice: " + e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public boolean updateInvoice(Invoice invoice) {
        return false;
    }

    @Override
    public boolean deleteInvoice(int invoiceId) {
        return false;
    }

    @Override
    public String generateNextInvoiceNumber() {

        String sql = "SELECT COALESCE(MAX(invoice_id), 0) + 1 AS next_id FROM invoices";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {

                int nextId = resultSet.getInt("next_id");

                return String.format("INV-%04d", nextId);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to generate invoice number: " + e.getMessage()
            );

            e.printStackTrace();
        }

        return null;
    }
}
