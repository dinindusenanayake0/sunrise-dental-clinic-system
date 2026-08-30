package com.dental.system.controller;

import com.dental.system.dao.PatientDAO;
import com.dental.system.model.Patient;
import com.dental.system.service.InPatientService;
import com.dental.system.service.PatientService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.time.LocalDate;


@WebServlet("/patients")
public class PatientServlet extends HttpServlet {

    private final InPatientService patientService;

    public PatientServlet() {
        this.patientService = new PatientService(new PatientDAO());
    }

    // Handle patient submit
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (!"update".equals(action) &&
                !"delete".equals(action)) {

        //Add patient
        try {
            String nic = request.getParameter("nic");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String gender = request.getParameter("gender");
            LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String address = request.getParameter("address");

            Patient patient = new Patient(
                    0,
                    firstName,
                    lastName,
                    gender,
                    dateOfBirth,
                    phone,
                    email,
                    address
            );

            patient.setNic(nic);

            Patient existingPatient =
                    patientService.getPatientByNic(nic);

            if (existingPatient != null) {
                response.sendRedirect(
                        request.getContextPath() + "/patients?nicExists=true"
                );
                return;
            }

            boolean added = patientService.addPatient(patient);

            if (added) {response.sendRedirect(
                    request.getContextPath() + "/patients?added=true");
            } else {
                response.sendRedirect(request.getContextPath() + "/patients?added=false");
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/patients?added=false");
        }
            return;
        }

        // Delete patient
        if ("delete".equals(action)) {

            try {
                int patientId =
                        Integer.parseInt(
                                request.getParameter("patientId")
                        );

                boolean deleted =
                        patientService.deletePatient(patientId);

                if (deleted) {
                    response.sendRedirect(
                            request.getContextPath()
                                    + "/patients?deleted=true"
                    );
                } else {
                    response.sendRedirect(
                            request.getContextPath()
                                    + "/patients?deleted=false"
                    );
                }

            } catch (Exception e) {
                response.sendRedirect(
                        request.getContextPath()
                                + "/patients?deleted=false"
                );
            }

            return;
        }

        //Update patient
        if ("update".equals(action)) {

            try {
                int patientId = Integer.parseInt(request.getParameter("patientId"));

                String nic = request.getParameter("nic");
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String gender = request.getParameter("gender");
                LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                String address = request.getParameter("address");

                Patient patient = new Patient(
                        patientId,
                        firstName,
                        lastName,
                        gender,
                        dateOfBirth,
                        phone,
                        email,
                        address
                );

                patient.setNic(nic);

                Patient existingPatient =
                        patientService.getPatientByNic(nic);

                if (existingPatient != null &&
                        existingPatient.getPatientId() != patientId) {

                    response.sendRedirect(
                            request.getContextPath() + "/patients?nicExists=true"
                    );
                    return;
                }

                boolean updated = patientService.updatePatient(patient);

                if (updated) {
                    response.sendRedirect("patients?updated=true");
                } else {
                    response.sendRedirect("patients?updated=false");
                }
            } catch (Exception e) {
                response.sendRedirect("patients?updated=false");
            }
            return;
        }
    }


    // Handle patient page requests
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        List<Patient> patients =
                patientService.getAllPatients();

        request.setAttribute(
                "patients",
                patients
        );

        request.getRequestDispatcher("/patients.jsp")
                .forward(request, response);
    }
}
