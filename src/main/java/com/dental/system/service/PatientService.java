package com.dental.system.service;

import com.dental.system.dao.InPatientDAO;
import com.dental.system.model.Patient;

import java.util.List;

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
        if (patient.getGender() == null || patient.getGender().trim().isEmpty()){
            return false;
        }
        if (patient.getDateOfBirth() == null){
            return false;
        }
        if (patient.getPhone() == null || patient.getPhone().trim().isEmpty()){
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
        if (patient.getGender() == null || patient.getGender().trim().isEmpty()){
            return false;
        }
        if (patient.getDateOfBirth() == null){
            return false;
        }
        if (patient.getPhone() == null || patient.getPhone().trim().isEmpty()){
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
}
