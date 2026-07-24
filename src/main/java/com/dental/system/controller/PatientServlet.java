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
import java.time.LocalDate;

@WebServlet("/add-patient")
public class PatientServlet extends HttpServlet {

    private final InPatientService patientService;

    public PatientServlet(){
        this.patientService = new PatientService(new PatientDAO());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try{
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
            boolean added = patientService.addPatient(patient);

            if (added){
                response.sendRedirect("add-patient.jsp?success=true");
            }else {
                request.setAttribute("error","Patient added failed...");
                request.getRequestDispatcher("/add-patient.jsp").forward(request, response);
            }
        }catch (Exception e){
            request.setAttribute("error","Invalid Patient details.");
            request.getRequestDispatcher("/add-patient.jsp");
        }
    }

}
