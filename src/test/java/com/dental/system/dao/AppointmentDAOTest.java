package com.dental.system.dao;

import com.dental.system.model.Appointment;
import com.dental.system.model.Patient;
import com.dental.system.util.DBCon;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppointmentDAOTest {

    private static AppointmentDAO appointmentDAO;
    private static PatientDAO patientDAO;

    private static int testPatientId;
    private static int createdAppointmentId;

    private static String testNic;
    private static String testAppointmentNumber;

    @BeforeAll
    static void setUp() {

        appointmentDAO = new AppointmentDAO();
        patientDAO = new PatientDAO();

        String timeValue =
                String.valueOf(System.currentTimeMillis());

        testNic =
                timeValue.substring(
                        timeValue.length() - 12
                );

        Patient patient = new Patient();

        patient.setNic(testNic);
        patient.setFirstName("DAO");
        patient.setLastName("TestPatient");
        patient.setGender("Male");
        patient.setDateOfBirth(
                LocalDate.of(2000, 1, 1)
        );
        patient.setPhone("0712345678");
        patient.setEmail("daotest@gmail.com");
        patient.setAddress("Colombo");

        boolean patientAdded =
                patientDAO.addPatient(patient);

        assertTrue(patientAdded);

        Patient savedPatient =
                patientDAO.getPatientByNic(testNic);

        assertNotNull(savedPatient);

        testPatientId =
                savedPatient.getPatientId();
    }

    @Test
    @Order(1)
    void generateNextAppointmentNumberShouldReturnNumber() {

        String number =
                appointmentDAO
                        .generateNextAppointmentNumber();

        assertNotNull(number);
        assertTrue(
                number.startsWith("APT-")
        );
    }

    @Test
    @Order(2)
    void addAppointmentShouldReturnTrue() {

        testAppointmentNumber =
                appointmentDAO
                        .generateNextAppointmentNumber();

        Appointment appointment =
                new Appointment();

        appointment.setAppointmentNumber(
                testAppointmentNumber
        );

        appointment.setPatientId(
                testPatientId
        );

        appointment.setAppointmentDate(
                LocalDate.now().plusDays(2)
        );

        appointment.setAppointmentTime(
                LocalTime.of(10, 30)
        );

        appointment.setDentistName(
                "Dr. Perera"
        );

        appointment.setTreatmentType(
                "Dental Cleaning, Tooth Filling"
        );

        appointment.setStatus(
                "Scheduled"
        );

        appointment.setNotes(
                "DAO integration test"
        );

        boolean result =
                appointmentDAO.addAppointment(
                        appointment
                );

        assertTrue(result);

        Appointment savedAppointment =
                appointmentDAO
                        .getAppointmentByNumber(
                                testAppointmentNumber
                        );

        assertNotNull(savedAppointment);

        createdAppointmentId =
                savedAppointment
                        .getAppointmentId();
    }

    @Test
    @Order(3)
    void getAppointmentByIdShouldReturnAppointment() {

        Appointment result =
                appointmentDAO
                        .getAppointmentById(
                                createdAppointmentId
                        );

        assertNotNull(result);

        assertEquals(
                testAppointmentNumber,
                result.getAppointmentNumber()
        );
    }

    @Test
    @Order(4)
    void getAppointmentByNumberShouldReturnAppointment() {

        Appointment result =
                appointmentDAO
                        .getAppointmentByNumber(
                                testAppointmentNumber
                        );

        assertNotNull(result);

        assertEquals(
                testPatientId,
                result.getPatientId()
        );
    }

    @Test
    @Order(5)
    void getAllAppointmentsShouldReturnList() {

        assertNotNull(
                appointmentDAO
                        .getAllAppointments()
        );

        assertFalse(
                appointmentDAO
                        .getAllAppointments()
                        .isEmpty()
        );
    }

    @Test
    @Order(6)
    void updateAppointmentShouldReturnTrue() {

        Appointment appointment =
                appointmentDAO
                        .getAppointmentById(
                                createdAppointmentId
                        );

        assertNotNull(appointment);

        appointment.setDentistName(
                "Dr. Silva"
        );

        appointment.setTreatmentType(
                "Dental Cleaning, Teeth Whitening"
        );

        appointment.setNotes(
                "Updated DAO test"
        );

        boolean result =
                appointmentDAO
                        .updateAppointment(
                                appointment
                        );

        assertTrue(result);

        Appointment updated =
                appointmentDAO
                        .getAppointmentById(
                                createdAppointmentId
                        );

        assertNotNull(updated);

        assertEquals(
                "Dr. Silva",
                updated.getDentistName()
        );

        assertEquals(
                "Dental Cleaning, Teeth Whitening",
                updated.getTreatmentType()
        );
    }

    @Test
    @Order(7)
    void updateAppointmentStatusShouldReturnTrue() {

        boolean result =
                appointmentDAO
                        .updateAppointmentStatus(
                                createdAppointmentId,
                                "Completed"
                        );

        assertTrue(result);

        Appointment updated =
                appointmentDAO
                        .getAppointmentById(
                                createdAppointmentId
                        );

        assertNotNull(updated);

        assertEquals(
                "Completed",
                updated.getStatus()
        );
    }

    @AfterAll
    static void cleanUp() {

        if (createdAppointmentId > 0) {

            String sql =
                    "DELETE FROM appointments " +
                            "WHERE appointment_id = ?";

            try (Connection connection =
                         DBCon.getConnection();

                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setInt(
                        1,
                        createdAppointmentId
                );

                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (testPatientId > 0) {

            patientDAO.deletePatient(
                    testPatientId
            );
        }
    }
}