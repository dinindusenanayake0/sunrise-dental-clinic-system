package com.dental.system.service;

import com.dental.system.dao.InPatientDAO;
import com.dental.system.model.Patient;

import java.util.List;
import java.time.LocalDate;

public class PatientService implements InPatientService {

    private final InPatientDAO inPatientDAO;

    public PatientService(InPatientDAO inPatientDAO) {
        this.inPatientDAO = inPatientDAO;
    }

    @Override
    public boolean addPatient(Patient patient) {
        if (patient == null){
            return false;
        }
        if (patient.getFirstName() == null || patient.getFirstName().trim().isEmpty()){
            return false;
        }
        if (patient.getLastName() == null || patient.getLastName().trim().isEmpty()){
            return false;
        }
        if (!isValidGender(patient.getGender())) {
            return false;
        }

        if (!isValidDateOfBirth(patient.getDateOfBirth())) {
            return false;
        }

        if (!isValidEmail(patient.getEmail())) {
            return false;
        }
        return inPatientDAO.addPatient(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        return inPatientDAO.getAllPatients();
    }

    @Override
    public Patient getPatientById(int patientId) {
        if (patientId <= 0) {
            return null;
        }
        return inPatientDAO.getPatientById(patientId);
    }

    @Override
    public boolean updatePatient(Patient patient) {
        if (patient == null || patient.getPatientId()<= 0){
            return false;
        }
        if (patient.getFirstName() == null || patient.getFirstName().trim().isEmpty()){
            return false;
        }
        if (patient.getLastName() == null || patient.getLastName().trim().isEmpty()){
            return false;
        }
        if (!isValidGender(patient.getGender())) {
            return false;
        }
        if (!isValidDateOfBirth(patient.getDateOfBirth())) {
            return false;
        }
        if (!isValidEmail(patient.getEmail())) {
            return false;
        }
        return inPatientDAO.updatePatient(patient);
    }

    @Override
    public boolean deletePatient(int patientId) {
        if (patientId <= 0){
            return false;
        }
        return inPatientDAO.deletePatient(patientId);
    }

    private boolean isValidPhone(String phone) {

        if (phone == null) {
            return false;
        }

        return phone.trim().matches("\\d{10}");
    }

    private boolean isValidGender(String gender) {

        if (gender == null) {
            return false;
        }

        return "Male".equalsIgnoreCase(gender.trim())
                || "Female".equalsIgnoreCase(gender.trim())
                || "Other".equalsIgnoreCase(gender.trim());
    }

    private boolean isValidDateOfBirth(LocalDate dateOfBirth) {

        if (dateOfBirth == null) {
            return false;
        }

        return !dateOfBirth.isAfter(LocalDate.now());
    }

    private boolean isValidEmail(String email) {

        if (email == null || email.trim().isEmpty()) {
            return true;
        }

        return email.trim()
                .matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}
