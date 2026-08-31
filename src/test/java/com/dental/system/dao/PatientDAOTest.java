package com.dental.system.dao;

import com.dental.system.model.Patient;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatientDAOTest {

    private static PatientDAO patientDAO;
    private static Patient testPatient;
    private static int createdPatientId;

    @BeforeAll
    static void setUp() {

        patientDAO = new PatientDAO();

        testPatient = new Patient();

        testPatient.setNic("199912345678");
        testPatient.setFirstName("Test");
        testPatient.setLastName("Patient");
        testPatient.setGender("Male");
        testPatient.setDateOfBirth(
                LocalDate.of(1999, 5, 15)
        );
        testPatient.setPhone("0712345678");
        testPatient.setEmail("testpatient@gmail.com");
        testPatient.setAddress("Colombo");
    }

    @Test
    @Order(1)
    void addPatientShouldReturnTrue() {

        Patient existing =
                patientDAO.getPatientByNic(
                        testPatient.getNic()
                );

        if (existing != null) {
            patientDAO.deletePatient(
                    existing.getPatientId()
            );
        }

        boolean result =
                patientDAO.addPatient(testPatient);

        assertTrue(result);

        Patient savedPatient =
                patientDAO.getPatientByNic(
                        testPatient.getNic()
                );

        assertNotNull(savedPatient);

        createdPatientId =
                savedPatient.getPatientId();
    }

    @Test
    @Order(2)
    void getPatientByIdShouldReturnPatient() {

        Patient result =
                patientDAO.getPatientById(
                        createdPatientId
                );

        assertNotNull(result);
        assertEquals(
                "Test",
                result.getFirstName()
        );
    }

    @Test
    @Order(3)
    void getPatientByNicShouldReturnPatient() {

        Patient result =
                patientDAO.getPatientByNic(
                        "199912345678"
                );

        assertNotNull(result);
        assertEquals(
                "199912345678",
                result.getNic()
        );
    }

    @Test
    @Order(4)
    void updatePatientShouldReturnTrue() {

        Patient patient =
                patientDAO.getPatientById(
                        createdPatientId
                );

        assertNotNull(patient);

        patient.setFirstName("Updated");
        patient.setPhone("0771234567");

        boolean result =
                patientDAO.updatePatient(patient);

        assertTrue(result);

        Patient updatedPatient =
                patientDAO.getPatientById(
                        createdPatientId
                );

        assertNotNull(updatedPatient);

        assertEquals(
                "Updated",
                updatedPatient.getFirstName()
        );

        assertEquals(
                "0771234567",
                updatedPatient.getPhone()
        );
    }

    @Test
    @Order(5)
    void getAllPatientsShouldReturnList() {

        assertNotNull(
                patientDAO.getAllPatients()
        );

        assertFalse(
                patientDAO.getAllPatients().isEmpty()
        );
    }

    @Test
    @Order(6)
    void deletePatientShouldReturnTrue() {

        boolean result =
                patientDAO.deletePatient(
                        createdPatientId
                );

        assertTrue(result);

        Patient deletedPatient =
                patientDAO.getPatientById(
                        createdPatientId
                );

        assertNull(deletedPatient);
    }

    @AfterAll
    static void cleanUp() {

        Patient remaining =
                patientDAO.getPatientByNic(
                        "199912345678"
                );

        if (remaining != null) {
            patientDAO.deletePatient(
                    remaining.getPatientId()
            );
        }
    }
}