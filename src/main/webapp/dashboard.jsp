<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.dental.system.model.Appointment" %>
<%@ page import="java.util.List" %>

<%
    HttpSession currentSession = request.getSession(false);

    if (currentSession == null || currentSession.getAttribute("loggedUser") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Integer totalPatients = (Integer) request.getAttribute("totalPatients");
        Integer totalAppointments = (Integer) request.getAttribute("totalAppointments");
        Integer scheduledAppointments = (Integer) request.getAttribute("scheduledAppointments");
        Integer completedAppointments = (Integer) request.getAttribute("completedAppointments");
        Integer cancelledAppointments = (Integer) request.getAttribute("cancelledAppointments");

        java.math.BigDecimal totalRevenue =
                (java.math.BigDecimal) request.getAttribute("totalRevenue");

        if (totalPatients == null) totalPatients = 0;
        if (totalAppointments == null) totalAppointments = 0;
        if (scheduledAppointments == null) scheduledAppointments = 0;
        if (completedAppointments == null) completedAppointments = 0;
        if (cancelledAppointments == null) cancelledAppointments = 0;
        if (totalRevenue == null) totalRevenue = java.math.BigDecimal.ZERO;
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


    <!-- Dashboard styles -->
    <style>
        .dashboard-card {
            border: none;
            border-radius: 18px;
            min-height: 150px;
            transition: all 0.25s ease;
            overflow: hidden;
        }

        .dashboard-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 12px 30px rgba(0, 0, 0, 0.10) !important;
        }

        .dashboard-icon {
            width: 64px;
            height: 64px;
            border-radius: 16px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 28px;
        }

        .dashboard-value {
            font-size: 32px;
            font-weight: 700;
            margin-bottom: 0;
        }

        .dashboard-label {
            color: #6c757d;
            font-size: 15px;
            margin-bottom: 5px;
        }
    </style>
</head>

<body class="bg-light">

<jsp:include page="/components/sidebar.jsp"/>

<div class="main-content">
    <div class="container py-4 px-4">
        <div class="mb-4">
            <h2 class="fw-bold">Dashboard</h2>
            <p class="text-muted">
            Manage the dental clinic system using the options below.
            </p>
        </div>

        <!-- Dashboard summary section -->
        <div class="row g-4">
            <div class="col-md-6 col-xl-4">
                <div class="card dashboard-card shadow-sm">
                    <div class="card-body d-flex align-items-center justify-content-between p-4">
                        <div>
                            <p class="dashboard-label">Total Patients</p>
                            <h2 class="dashboard-value">
                            <%= totalPatients %>
                            </h2>
                            <small class="text-muted">
                            Registered patients
                            </small>
                        </div>

                        <div class="dashboard-icon bg-primary-subtle text-primary">
                             <i class="bi bi-people-fill"></i>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-xl-4">
                <div class="card dashboard-card shadow-sm">
                     <div class="card-body d-flex align-items-center justify-content-between p-4">
                        <div>
                            <p class="dashboard-label">Total Appointments</p>
                            <h2 class="dashboard-value">
                            <%= totalAppointments %>
                            </h2>
                            <small class="text-muted">
                            All appointments
                            </small>
                        </div>

                        <div class="dashboard-icon bg-info-subtle text-info">
                            <i class="bi bi-calendar2-check-fill"></i>
                        </div>
                     </div>
                </div>
            </div>

            <div class="col-md-6 col-xl-4">
                <div class="card dashboard-card shadow-sm">
                    <div class="card-body d-flex align-items-center justify-content-between p-4">
                        <div>
                            <p class="dashboard-label">Scheduled</p>
                            <h2 class="dashboard-value">
                            <%= scheduledAppointments %>
                            </h2>
                            <small class="text-muted">
                            Upcoming appointments
                            </small>
                            </div>

                            <div class="dashboard-icon bg-warning-subtle text-warning">
                                <i class="bi bi-clock-fill"></i>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 col-xl-4">
                    <div class="card dashboard-card shadow-sm">
                        <div class="card-body d-flex align-items-center justify-content-between p-4">
                            <div>
                                <p class="dashboard-label">Completed</p>
                                <h2 class="dashboard-value">
                                <%= completedAppointments %>
                                </h2>
                                <small class="text-muted">
                                Completed appointments
                                </small>
                            </div>

                            <div class="dashboard-icon bg-success-subtle text-success">
                                <i class="bi bi-check-circle-fill"></i>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 col-xl-4">
                    <div class="card dashboard-card shadow-sm">
                        <div class="card-body d-flex align-items-center justify-content-between p-4">
                            <div>
                                <p class="dashboard-label">Cancelled</p>
                                <h2 class="dashboard-value">
                                <%= cancelledAppointments %>
                                </h2>
                                <small class="text-muted">
                                Cancelled appointments
                                </small>
                            </div>

                            <div class="dashboard-icon bg-danger-subtle text-danger">
                                <i class="bi bi-x-circle-fill"></i>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 col-xl-4">
                    <div class="card dashboard-card shadow-sm">
                        <div class="card-body d-flex align-items-center justify-content-between p-4">
                            <div>
                                <p class="dashboard-label">Total Revenue</p>
                                <h2 class="dashboard-value">
                                LKR <%= String.format("%,.2f", totalRevenue) %>
                                </h2>
                                <small class="text-muted">
                                Total amount received
                                </small>
                            </div>

                            <div class="dashboard-icon bg-success-subtle text-success">
                                <i class="bi bi-cash-stack"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row g-4 mt-1">

            <div class="col-xl-8">
                <div class="card border-0 shadow-sm rounded-4 h-100">

                    <div class="card-header bg-white border-0 pt-4 px-4">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h5 class="fw-bold mb-1">Recent Appointments</h5>
                                <p class="text-muted small mb-0">
                                    Latest appointment activity
                                </p>
                            </div>

                            <a href="<%= request.getContextPath() %>/appointments"
                               class="btn btn-sm btn-outline-primary">
                                View All
                            </a>
                        </div>
                    </div>

                    <div class="card-body px-4 pb-4">

                        <div class="table-responsive">

                            <table class="table align-middle mb-0">
                                <thead class="table-light">
                                <tr>
                                    <th>Appointment No.</th>
                                    <th>Patient ID</th>
                                    <th>Dentist</th>
                                    <th>Date</th>
                                    <th>Status</th>
                                </tr>
                                </thead>

                                <tbody>

                                <%
                                    List<Appointment> recentAppointments =
                                            (List<Appointment>) request.getAttribute("recentAppointments");

                                    if (recentAppointments != null && !recentAppointments.isEmpty()) {

                                        for (Appointment appointment : recentAppointments) {

                                            String badgeClass = "bg-secondary";

                                            if ("Scheduled".equalsIgnoreCase(appointment.getStatus())) {
                                                badgeClass = "bg-warning text-dark";
                                            } else if ("Completed".equalsIgnoreCase(appointment.getStatus())) {
                                                badgeClass = "bg-success";
                                            } else if ("Cancelled".equalsIgnoreCase(appointment.getStatus())) {
                                                badgeClass = "bg-danger";
                                            }
                                %>

                                <tr>

                                    <td class="fw-semibold">
                                        <%= appointment.getAppointmentNumber() %>
                                    </td>

                                    <td>
                                        <%= appointment.getPatientId() %>
                                    </td>

                                    <td>
                                        <%= appointment.getDentistName() %>
                                    </td>

                                    <td>
                                        <%= appointment.getAppointmentDate() %>
                                    </td>

                                    <td>
                                        <span class="badge <%= badgeClass %>">
                                            <%= appointment.getStatus() %>
                                        </span>
                                    </td>

                                </tr>

                                <%
                                        }

                                    } else {
                                %>

                                <tr>
                                    <td colspan="5"
                                        class="text-center text-muted py-4">
                                        No appointments found.
                                    </td>
                                </tr>

                                <%
                                    }
                                %>

                                </tbody>
                            </table>

                        </div>

                    </div>

                </div>
            </div>

            <!-- Quick actions -->
            <div class="col-xl-4">

                <div class="card border-0 shadow-sm rounded-4 h-100">

                    <div class="card-header bg-white border-0 pt-4 px-4">
                        <h5 class="fw-bold mb-1">Quick Actions</h5>
                        <p class="text-muted small mb-0">
                            Common clinic tasks
                        </p>
                    </div>

                    <div class="card-body px-4 pb-4">

                        <div class="d-grid gap-3">

                            <a href="<%= request.getContextPath() %>/patients"
                               class="btn btn-light border text-start p-3 rounded-3">

                                <i class="bi bi-person-plus-fill text-primary me-2"></i>

                                Manage Patients
                            </a>

                            <a href="<%= request.getContextPath() %>/appointments"
                               class="btn btn-light border text-start p-3 rounded-3">

                                <i class="bi bi-calendar-plus-fill text-primary me-2"></i>

                                New Appointment
                            </a>

                            <a href="<%= request.getContextPath() %>/invoices"
                               class="btn btn-light border text-start p-3 rounded-3">

                                <i class="bi bi-receipt-cutoff text-success me-2"></i>

                                View Invoices
                            </a>

                            <a href="<%= request.getContextPath() %>/reports"
                               class="btn btn-light border text-start p-3 rounded-3">

                                <i class="bi bi-bar-chart-fill text-info me-2"></i>

                                View Reports
                            </a>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    </div>
</div>
</body>
</html>