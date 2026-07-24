<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    HttpSession currentSession = request.getSession(false);

    if (currentSession == null || currentSession.getAttribute("loggedUser") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Dashboard - Sunrise Dental Clinic</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>

<body class="bg-light">

<nav class="navbar navbar-dark bg-primary shadow-sm">
    <div class="container">
        <a class="navbar-brand fw-bold" href="dashboard.jsp">
            <i class="bi bi-hospital me-2"></i>
            Sunrise Dental Clinic
        </a>

        <a href="logout" class="btn btn-outline-light">
            <i class="bi bi-box-arrow-right me-1"></i>
            Logout
        </a>
    </div>
</nav>

<div class="container py-5">

    <div class="mb-4">
        <h2 class="fw-bold">Dashboard</h2>
        <p class="text-muted">
            Manage the dental clinic system using the options below.
        </p>
    </div>

    <div class="row g-4">

        <div class="col-md-6 col-lg-4">
            <div class="card h-100 border-0 shadow-sm">

                <div class="card-body text-center p-4">
                    <div class="display-5 text-primary mb-3">
                        <i class="bi bi-person-plus-fill"></i>
                    </div>

                    <h4 class="card-title">Add Patient</h4>

                    <p class="card-text text-muted">
                        Register a new patient in the clinic system.
                    </p>

                    <a href="add-patient.jsp" class="btn btn-primary">
                        <i class="bi bi-plus-circle me-1"></i>
                        Add Patient
                    </a>
                </div>

            </div>
        </div>

        <div class="col-md-6 col-lg-4">
            <div class="card h-100 border-0 shadow-sm">

                <div class="card-body text-center p-4">
                    <div class="display-5 text-primary mb-3">
                        <i class="bi bi-people-fill"></i>
                    </div>

                    <h4 class="card-title">View Patients</h4>

                    <p class="card-text text-muted">
                        View and manage registered patient information.
                    </p>

                    <a href="#" class="btn btn-secondary disabled">
                        Coming Soon
                    </a>
                </div>

            </div>
        </div>

        <div class="col-md-6 col-lg-4">
            <div class="card h-100 border-0 shadow-sm">

                <div class="card-body text-center p-4">
                    <div class="display-5 text-primary mb-3">
                        <i class="bi bi-calendar-check-fill"></i>
                    </div>

                    <h4 class="card-title">Appointments</h4>

                    <p class="card-text text-muted">
                        Manage patient appointments and schedules.
                    </p>

                    <a href="#" class="btn btn-secondary disabled">
                        Coming Soon
                    </a>
                </div>

            </div>
        </div>

    </div>

</div>

</body>
</html>