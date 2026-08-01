package com.dental.system.dao;

import com.dental.system.model.Patient;
import com.dental.system.util.DBCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO implements InPatientDAO {

    //Add patient to the database
    @Override
    public boolean addPatient(Patient patient) {
        String sql = "INSERT INTO patients(first_name, last_name, gender, date_of_birth, phone, email, address) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, patient.getFirstName());
            statement.setString(2, patient.getLastName());
            statement.setString(3, patient.getGender());
            statement.setDate(4, Date.valueOf(patient.getDateOfBirth()));
            statement.setString(5, patient.getPhone());
            statement.setString(6, patient.getEmail());
            statement.setString(7, patient.getAddress());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Patient added failed..");
            e.printStackTrace();
        }
        return false;
    }


    //Get all patients records
    @Override
    public List<Patient> getAllPatients() {

        List<Patient> patients = new ArrayList<>();

        String sql = "SELECT * FROM patients ORDER BY patient_id DESC";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Patient patient = new Patient();

                patient.setPatientId(resultSet.getInt("patient_id"));
                patient.setFirstName(resultSet.getString("first_name"));
                patient.setLastName(resultSet.getString("last_name"));
                patient.setGender(resultSet.getString("gender"));
                patient.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
                patient.setPhone(resultSet.getString("phone"));
                patient.setEmail(resultSet.getString("email"));
                patient.setAddress(resultSet.getString("address"));

                patients.add(patient);
            }
        } catch (SQLException e) {
            System.out.println("Patients records loading failed...");
            e.printStackTrace();
        }
        return patients;

    }

    @Override
    public Patient getPatientById(int patientId) {

        String sql = " SELECT * FROM patients WHERE patient_id = ? ";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, patientId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {

                    return new Patient(
                            resultSet.getInt("patient_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("gender"),
                            resultSet.getDate("date_of_birth").toLocalDate(),
                            resultSet.getString("phone"),
                            resultSet.getString("email"),
                            resultSet.getString("address")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve patient by ID : " + e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    //Update patient details
    @Override
    public boolean updatePatient(Patient patient) {

        String sql = "UPDATE patients SET first_name = ?, last_name = ?, gender = ?, date_of_birth = ?, phone = ?, email = ?, address = ? WHERE patient_id = ? ";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, patient.getFirstName());
            statement.setString(2, patient.getLastName());
            statement.setString(3, patient.getGender());
            statement.setDate(4, Date.valueOf(patient.getDateOfBirth()));
            statement.setString(5, patient.getPhone());
            statement.setString(6, patient.getEmail());
            statement.setString(7, patient.getAddress());
            statement.setInt(8, patient.getPatientId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Patient update failed..");
            e.printStackTrace();
        }
        return false;
    }

    //Delete a patient
    @Override
    public boolean deletePatient(int patientId) {

        String sql = "DELETE FROM patients WHERE patient_id = ? ";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, patientId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Patient delete failed..");
            e.printStackTrace();
        }
        return false;
    }
}
