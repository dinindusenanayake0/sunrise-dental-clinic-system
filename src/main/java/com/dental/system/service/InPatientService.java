package com.dental.system.service;

import com.dental.system.model.Patient;

import java.util.List;

public interface InPatientService {

    boolean addPatient(Patient patient);

    List<Patient> getAllPatients();

    Patient getPatientById(int patientId);

    boolean updatePatient(Patient patient);

    boolean deletePatient(int patientId);
}
