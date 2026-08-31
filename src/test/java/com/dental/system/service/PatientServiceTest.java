package com.dental.system.service;

import com.dental.system.dao.InPatientDAO;
import com.dental.system.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PatientServiceTest {

    private PatientService patientService;
    private FakePatientDAO fakePatientDAO;

    @BeforeEach
    void setUp() {
        fakePatientDAO = new FakePatientDAO();
        patientService = new PatientService(fakePatientDAO);
    }

    private Patient createValidPatient() {

        Patient patient = new Patient();

        patient.setPatientId(1);
        patient.setNic("200012345678");
        patient.setFirstName("Nimal");
        patient.setLastName("Perera");
        patient.setGender("Male");
        patient.setDateOfBirth(LocalDate.of(2000, 5, 10));
        patient.setPhone("0712345678");
        patient.setEmail("nimal@gmail.com");
        patient.setAddress("Colombo");

        return patient;
    }

    @Test
    void addPatientWithValidDetailsShouldReturnTrue() {

        Patient patient = createValidPatient();

        boolean result = patientService.addPatient(patient);

        assertTrue(result);
    }

    @Test
    void addNullPatientShouldReturnFalse() {

        boolean result = patientService.addPatient(null);

        assertFalse(result);
    }

    @Test
    void addPatientWithInvalidNicShouldReturnFalse() {

        Patient patient = createValidPatient();

        patient.setNic("12345");

        boolean result = patientService.addPatient(patient);

        assertFalse(result);
    }

    @Test
    void addPatientWithDuplicateNicShouldReturnFalse() {

        Patient patient = createValidPatient();

        Patient existingPatient = createValidPatient();
        existingPatient.setPatientId(2);

        fakePatientDAO.existingPatientByNic = existingPatient;

        boolean result = patientService.addPatient(patient);

        assertFalse(result);
    }

    @Test
    void addPatientWithEmptyFirstNameShouldReturnFalse() {

        Patient patient = createValidPatient();

        patient.setFirstName("");

        boolean result = patientService.addPatient(patient);

        assertFalse(result);
    }

    @Test
    void addPatientWithInvalidGenderShouldReturnFalse() {

        Patient patient = createValidPatient();

        patient.setGender("Unknown");

        boolean result = patientService.addPatient(patient);

        assertFalse(result);
    }

    @Test
    void addPatientWithFutureDateOfBirthShouldReturnFalse() {

        Patient patient = createValidPatient();

        patient.setDateOfBirth(
                LocalDate.now().plusDays(1)
        );

        boolean result = patientService.addPatient(patient);

        assertFalse(result);
    }

    @Test
    void addPatientWithInvalidPhoneShouldReturnFalse() {

        Patient patient = createValidPatient();

        patient.setPhone("07123");

        boolean result = patientService.addPatient(patient);

        assertFalse(result);
    }

    @Test
    void addPatientWithInvalidEmailShouldReturnFalse() {

        Patient patient = createValidPatient();

        patient.setEmail("invalid-email");

        boolean result = patientService.addPatient(patient);

        assertFalse(result);
    }

    @Test
    void addPatientWithoutEmailShouldReturnTrue() {

        Patient patient = createValidPatient();

        patient.setEmail("");

        boolean result = patientService.addPatient(patient);

        assertTrue(result);
    }

    @Test
    void getPatientByValidIdShouldReturnPatient() {

        Patient patient = createValidPatient();

        fakePatientDAO.patientById = patient;

        Patient result =
                patientService.getPatientById(1);

        assertNotNull(result);
        assertEquals(1, result.getPatientId());
    }

    @Test
    void getPatientByInvalidIdShouldReturnNull() {

        Patient result =
                patientService.getPatientById(0);

        assertNull(result);
    }

    @Test
    void getPatientByNicShouldReturnPatient() {

        Patient patient = createValidPatient();

        fakePatientDAO.existingPatientByNic = patient;

        Patient result =
                patientService.getPatientByNic("200012345678");

        assertNotNull(result);
        assertEquals(
                "200012345678",
                result.getNic()
        );
    }

    @Test
    void updateValidPatientShouldReturnTrue() {

        Patient patient = createValidPatient();

        fakePatientDAO.existingPatientByNic = patient;

        boolean result =
                patientService.updatePatient(patient);

        assertTrue(result);
    }

    @Test
    void updatePatientWithDuplicateNicShouldReturnFalse() {

        Patient patient = createValidPatient();
        patient.setPatientId(1);

        Patient anotherPatient = createValidPatient();
        anotherPatient.setPatientId(2);

        fakePatientDAO.existingPatientByNic =
                anotherPatient;

        boolean result =
                patientService.updatePatient(patient);

        assertFalse(result);
    }

    @Test
    void deletePatientWithValidIdShouldReturnTrue() {

        boolean result =
                patientService.deletePatient(1);

        assertTrue(result);
    }

    @Test
    void deletePatientWithInvalidIdShouldReturnFalse() {

        boolean result =
                patientService.deletePatient(0);

        assertFalse(result);
    }


    private static class FakePatientDAO
            implements InPatientDAO {

        Patient existingPatientByNic;
        Patient patientById;

        List<Patient> patients =
                new ArrayList<>();

        @Override
        public boolean addPatient(Patient patient) {
            return true;
        }

        @Override
        public List<Patient> getAllPatients() {
            return patients;
        }

        @Override
        public Patient getPatientById(int patientId) {
            return patientById;
        }

        @Override
        public Patient getPatientByNic(String nic) {
            return existingPatientByNic;
        }

        @Override
        public boolean updatePatient(Patient patient) {
            return true;
        }

        @Override
        public boolean deletePatient(int patientId) {
            return true;
        }
    }
}