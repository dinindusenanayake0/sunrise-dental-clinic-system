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

    // Add patient
    @Override
    public boolean addPatient(Patient patient) {
        if (patient == null){
            return false;
        }
        if (!isValidNic(patient.getNic())) {
            return false;
        }

        Patient existingPatient =
                inPatientDAO.getPatientByNic(patient.getNic());

        if (existingPatient != null) {
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

        if (!isValidPhone(patient.getPhone())) {
            return false;
        }

        if (!isValidEmail(patient.getEmail())) {
            return false;
        }
        return inPatientDAO.addPatient(patient);
    }


    // Get all patients
    @Override
    public List<Patient> getAllPatients() {
        return inPatientDAO.getAllPatients();
    }


    // Get patient by ID
    @Override
    public Patient getPatientById(int patientId) {
        if (patientId <= 0) {
            return null;
        }
        return inPatientDAO.getPatientById(patientId);
    }

    // Get patient by NIC
    @Override
    public Patient getPatientByNic(String nic) {
        if (nic == null || nic.trim().isEmpty()) {
            return null;
        }

        return inPatientDAO.getPatientByNic(nic.trim());
    }

    // Update patient
    @Override
    public boolean updatePatient(Patient patient) {
        if (patient == null || patient.getPatientId()<= 0){
            return false;
        }
        if (!isValidNic(patient.getNic())) {
            return false;
        }

        Patient existingPatient =
                inPatientDAO.getPatientByNic(patient.getNic());

        if (existingPatient != null &&
                existingPatient.getPatientId() != patient.getPatientId()) {
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
        if (!isValidPhone(patient.getPhone())) {
            return false;
        }
        if (!isValidEmail(patient.getEmail())) {
            return false;
        }
        return inPatientDAO.updatePatient(patient);
    }


    // Delete patient
    @Override
    public boolean deletePatient(int patientId) {
        if (patientId <= 0){
            return false;
        }
        return inPatientDAO.deletePatient(patientId);
    }

    // Validate phone number
    private boolean isValidPhone(String phone) {

        if (phone == null) {
            return false;
        }

        return phone.trim().matches("\\d{10}");
    }

    // Validate gender
    private boolean isValidGender(String gender) {

        if (gender == null) {
            return false;
        }

        return "Male".equalsIgnoreCase(gender.trim())
                || "Female".equalsIgnoreCase(gender.trim())
                || "Other".equalsIgnoreCase(gender.trim());
    }

    // Validate date of birth
    private boolean isValidDateOfBirth(LocalDate dateOfBirth) {

        if (dateOfBirth == null) {
            return false;
        }

        return !dateOfBirth.isAfter(LocalDate.now());
    }

    // Validate NIC
    private boolean isValidNic(String nic) {

        if (nic == null || nic.trim().isEmpty()) {
            return false;
        }

        return nic.trim().matches("^(\\d{9}[VvXx]|\\d{12})$");
    }

    // Validate email
    private boolean isValidEmail(String email) {

        if (email == null || email.trim().isEmpty()) {
            return true;
        }

        return email.trim()
                .matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}
