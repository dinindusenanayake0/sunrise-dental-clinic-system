package com.dental.system.dao;

import com.dental.system.model.Patient;
import java.util.List;

public interface InPatientDAO {

    boolean addPatient(Patient patient);

    List<Patient> getAllPatients();

    Patient getPatientById(int patientId);

    Patient getPatientByNic(String nic);

    boolean updatePatient(Patient patient);

    boolean deletePatient(int patientId);
}
