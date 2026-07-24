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
        }catch (SQLException e){
            System.out.println("Patient added failed..");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Patient> getAllPatients() {
        return new ArrayList<>();
    }

    @Override
    public Patient getPatientById(int patientId) {
        return null;
    }

    @Override
    public boolean updatePatient(Patient patient) {
        return false;
    }

    @Override
    public boolean deletePatient(int patientId) {
        return false;
    }
}
