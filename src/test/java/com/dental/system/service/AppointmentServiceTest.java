package com.dental.system.service;

import com.dental.system.dao.InAppointmentDAO;
import com.dental.system.model.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentServiceTest {

    private AppointmentService appointmentService;
    private FakeAppointmentDAO fakeAppointmentDAO;

    @BeforeEach
    void setUp() {
        fakeAppointmentDAO = new FakeAppointmentDAO();
        appointmentService = new AppointmentService(fakeAppointmentDAO);
    }

    private Appointment createValidAppointment() {

        Appointment appointment = new Appointment();

        appointment.setAppointmentId(1);
        appointment.setPatientId(1);
        appointment.setAppointmentNumber("APT-0001");
        appointment.setAppointmentDate(LocalDate.now().plusDays(1));
        appointment.setAppointmentTime(LocalTime.of(10, 30));
        appointment.setDentistName("Dr. Perera");
        appointment.setTreatmentType("Dental Cleaning");
        appointment.setStatus("Scheduled");
        appointment.setNotes("Routine appointment");

        return appointment;
    }

    @Test
    void addAppointmentWithValidDetailsShouldReturnTrue() {

        Appointment appointment = createValidAppointment();

        fakeAppointmentDAO.nextAppointmentNumber = "APT-0002";

        boolean result =
                appointmentService.addAppointment(appointment);

        assertTrue(result);

        assertEquals(
                "APT-0002",
                appointment.getAppointmentNumber()
        );
    }

    @Test
    void addNullAppointmentShouldReturnFalse() {

        boolean result =
                appointmentService.addAppointment(null);

        assertFalse(result);
    }

    @Test
    void addAppointmentWithInvalidPatientIdShouldReturnFalse() {

        Appointment appointment = createValidAppointment();

        appointment.setPatientId(0);

        boolean result =
                appointmentService.addAppointment(appointment);

        assertFalse(result);
    }

    @Test
    void addAppointmentWithPastDateShouldReturnFalse() {

        Appointment appointment = createValidAppointment();

        appointment.setAppointmentDate(
                LocalDate.now().minusDays(1)
        );

        boolean result =
                appointmentService.addAppointment(appointment);

        assertFalse(result);
    }

    @Test
    void addAppointmentWithoutTimeShouldReturnFalse() {

        Appointment appointment = createValidAppointment();

        appointment.setAppointmentTime(null);

        boolean result =
                appointmentService.addAppointment(appointment);

        assertFalse(result);
    }

    @Test
    void addAppointmentWithoutDentistShouldReturnFalse() {

        Appointment appointment = createValidAppointment();

        appointment.setDentistName("");

        boolean result =
                appointmentService.addAppointment(appointment);

        assertFalse(result);
    }

    @Test
    void addAppointmentWithoutTreatmentShouldReturnFalse() {

        Appointment appointment = createValidAppointment();

        appointment.setTreatmentType("");

        boolean result =
                appointmentService.addAppointment(appointment);

        assertFalse(result);
    }

    @Test
    void addAppointmentWithMultipleTreatmentsShouldReturnTrue() {

        Appointment appointment = createValidAppointment();

        appointment.setTreatmentType(
                "Dental Cleaning, Tooth Filling, Teeth Whitening"
        );

        fakeAppointmentDAO.nextAppointmentNumber = "APT-0002";

        boolean result =
                appointmentService.addAppointment(appointment);

        assertTrue(result);
    }

    @Test
    void addAppointmentWhenNumberGenerationFailsShouldReturnFalse() {

        Appointment appointment = createValidAppointment();

        fakeAppointmentDAO.nextAppointmentNumber = null;

        boolean result =
                appointmentService.addAppointment(appointment);

        assertFalse(result);
    }

    @Test
    void addAppointmentWithoutStatusShouldSetScheduled() {

        Appointment appointment = createValidAppointment();

        appointment.setStatus(null);

        fakeAppointmentDAO.nextAppointmentNumber = "APT-0002";

        boolean result =
                appointmentService.addAppointment(appointment);

        assertTrue(result);

        assertEquals(
                "Scheduled",
                appointment.getStatus()
        );
    }

    @Test
    void addAppointmentWhenDoctorAlreadyBookedShouldReturnFalse() {

        Appointment appointment = createValidAppointment();

        fakeAppointmentDAO.scheduledAppointmentExists = true;

        boolean result =
                appointmentService.addAppointment(appointment);

        assertFalse(result);
    }

    @Test
    void updateValidAppointmentShouldReturnTrue() {

        Appointment appointment = createValidAppointment();

        boolean result =
                appointmentService.updateAppointment(appointment);

        assertTrue(result);
    }

    @Test
    void updateAppointmentWithInvalidIdShouldReturnFalse() {

        Appointment appointment = createValidAppointment();

        appointment.setAppointmentId(0);

        boolean result =
                appointmentService.updateAppointment(appointment);

        assertFalse(result);
    }

    @Test
    void updateAppointmentWithInvalidStatusShouldReturnFalse() {

        Appointment appointment = createValidAppointment();

        appointment.setStatus("Unknown");

        boolean result =
                appointmentService.updateAppointment(appointment);

        assertFalse(result);
    }

    @Test
    void updateAppointmentWithoutStatusShouldSetScheduled() {

        Appointment appointment = createValidAppointment();

        appointment.setStatus("");

        boolean result =
                appointmentService.updateAppointment(appointment);

        assertTrue(result);

        assertEquals(
                "Scheduled",
                appointment.getStatus()
        );
    }

    @Test
    void updateAppointmentStatusWithValidStatusShouldReturnTrue() {

        boolean result =
                appointmentService.updateAppointmentStatus(
                        1,
                        "Completed"
                );

        assertTrue(result);
    }

    @Test
    void updateAppointmentStatusWithInvalidIdShouldReturnFalse() {

        boolean result =
                appointmentService.updateAppointmentStatus(
                        0,
                        "Completed"
                );

        assertFalse(result);
    }

    @Test
    void updateAppointmentStatusWithInvalidStatusShouldReturnFalse() {

        boolean result =
                appointmentService.updateAppointmentStatus(
                        1,
                        "Unknown"
                );

        assertFalse(result);
    }

    @Test
    void getAppointmentByIdShouldReturnAppointment() {

        Appointment appointment = createValidAppointment();

        fakeAppointmentDAO.appointmentById = appointment;

        Appointment result =
                appointmentService.getAppointmentById(1);

        assertNotNull(result);

        assertEquals(
                1,
                result.getAppointmentId()
        );
    }

    @Test
    void getAppointmentByNumberShouldReturnAppointment() {

        Appointment appointment = createValidAppointment();

        fakeAppointmentDAO.appointmentByNumber =
                appointment;

        Appointment result =
                appointmentService.getAppointmentByNumber(
                        "APT-0001"
                );

        assertNotNull(result);

        assertEquals(
                "APT-0001",
                result.getAppointmentNumber()
        );
    }

    private static class FakeAppointmentDAO
            implements InAppointmentDAO {

        String nextAppointmentNumber = "APT-0001";

        Appointment appointmentById;
        Appointment appointmentByNumber;

        boolean scheduledAppointmentExists = false;

        List<Appointment> appointments =
                new ArrayList<>();

        @Override
        public boolean addAppointment(
                Appointment appointment
        ) {
            return true;
        }

        @Override
        public List<Appointment> getAllAppointments() {
            return appointments;
        }

        @Override
        public Appointment getAppointmentByNumber(
                String appointmentNumber
        ) {
            return appointmentByNumber;
        }

        @Override
        public Appointment getAppointmentById(
                int appointmentId
        ) {
            return appointmentById;
        }

        @Override
        public boolean updateAppointment(
                Appointment appointment
        ) {
            return true;
        }

        @Override
        public String generateNextAppointmentNumber() {
            return nextAppointmentNumber;
        }

        @Override
        public boolean updateAppointmentStatus(
                int appointmentId,
                String status
        ) {
            return true;
        }

        @Override
        public boolean existsScheduledAppointment(
                String dentistName,
                LocalDate appointmentDate,
                LocalTime appointmentTime
        ) {
            return scheduledAppointmentExists;
        }
    }
}