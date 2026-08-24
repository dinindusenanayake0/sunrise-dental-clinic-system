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

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Help - Sunrise Dental Clinic</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <style>

        .help-card {
            border: none;
            border-radius: 18px;
            transition: all 0.25s ease;
        }

        .help-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 12px 30px rgba(0, 0, 0, 0.08) !important;
        }

        .help-icon {
            width: 54px;
            height: 54px;
            border-radius: 14px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
            flex-shrink: 0;
        }

        .step-number {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            background: #0d6efd;
            color: white;
            font-weight: 600;
            margin-right: 10px;
        }

        .help-step {
            display: flex;
            align-items: flex-start;
            margin-bottom: 14px;
        }

        .help-step:last-child {
            margin-bottom: 0;
        }

    </style>

</head>

<body class="bg-light">

<jsp:include page="/components/sidebar.jsp"/>

<div class="main-content">

    <div class="container-fluid py-4 px-4">

        <div class="mb-4">

            <h2 class="fw-bold">
                Help
            </h2>

            <p class="text-muted mb-0">
                Step-by-step instructions for using the Sunrise Dental Clinic Management System.
            </p>

        </div>


        <div class="row g-4">

             <div class="col-lg-6">

                <div class="card help-card shadow-sm h-100">

                    <div class="card-body p-4">

                        <div class="d-flex align-items-center mb-4">

                            <div class="help-icon bg-success-subtle text-success me-3">
                                <i class="bi bi-person-plus-fill"></i>
                            </div>

                            <div>
                                <h5 class="fw-bold mb-1">Manage Patients</h5>
                                <p class="text-muted small mb-0">
                                    Register and maintain patient information.
                                </p>
                            </div>

                        </div>

                        <div class="help-step">
                            <span class="step-number">1</span>
                            <div>Select Patient Management from the sidebar.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">2</span>
                            <div>Click Add Patient and enter the required patient details.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">3</span>
                            <div>Click Save to register the patient.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">4</span>
                            <div>Use the search option to find a patient.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">5</span>
                            <div>Use the Edit option when patient information needs to be updated.</div>
                        </div>

                    </div>

                </div>

            </div>


            <div class="col-lg-6">

                <div class="card help-card shadow-sm h-100">

                    <div class="card-body p-4">

                        <div class="d-flex align-items-center mb-4">

                            <div class="help-icon bg-info-subtle text-info me-3">
                                <i class="bi bi-calendar-plus-fill"></i>
                            </div>

                            <div>
                                <h5 class="fw-bold mb-1">Create an Appointment</h5>
                                <p class="text-muted small mb-0">
                                    Register a new appointment for a patient.
                                </p>
                            </div>

                        </div>

                        <div class="help-step">
                            <span class="step-number">1</span>
                            <div>Select Appointment Management from the sidebar.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">2</span>
                            <div>Click Add Appointment.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">3</span>
                            <div>Select the patient and enter the appointment details.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">4</span>
                            <div>Select the dentist, treatment, date and time.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">5</span>
                            <div>Click Save to create the appointment.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">6</span>
                            <div>A unique appointment number will be generated automatically.</div>
                        </div>

                    </div>

                </div>

            </div>


            <div class="col-lg-6">

                <div class="card help-card shadow-sm h-100">

                    <div class="card-body p-4">

                        <div class="d-flex align-items-center mb-4">

                            <div class="help-icon bg-warning-subtle text-warning me-3">
                                <i class="bi bi-search"></i>
                            </div>

                            <div>
                                <h5 class="fw-bold mb-1">Search and View Appointments</h5>
                                <p class="text-muted small mb-0">
                                    Find an appointment and view its details.
                                </p>
                            </div>

                        </div>

                        <div class="help-step">
                            <span class="step-number">1</span>
                            <div>Open Appointment Management.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">2</span>
                            <div>Use the search box to enter the appointment number.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">3</span>
                            <div>Select the View option to display the complete appointment details.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">4</span>
                            <div>Check the patient, dentist, treatment, date, time and status information.</div>
                        </div>

                    </div>

                </div>

            </div>


            <div class="col-lg-6">

                <div class="card help-card shadow-sm h-100">

                    <div class="card-body p-4">

                        <div class="d-flex align-items-center mb-4">

                            <div class="help-icon bg-danger-subtle text-danger me-3">
                                <i class="bi bi-calendar-x-fill"></i>
                            </div>

                            <div>
                                <h5 class="fw-bold mb-1">Edit or Cancel an Appointment</h5>
                                <p class="text-muted small mb-0">
                                    Manage scheduled appointments when changes are required.
                                </p>
                            </div>

                        </div>

                        <div class="help-step">
                            <span class="step-number">1</span>
                            <div>Find the required appointment from the appointment list.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">2</span>
                            <div>Use Edit to update a scheduled appointment.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">3</span>
                            <div>Use View and select Cancel Appointment if the appointment should be cancelled.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">4</span>
                            <div>Confirm the cancellation when the confirmation message is displayed.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">5</span>
                            <div>Cancelled appointments cannot be edited or used to generate an invoice.</div>
                        </div>

                    </div>

                </div>

            </div>


            <div class="col-lg-6">

                <div class="card help-card shadow-sm h-100">

                    <div class="card-body p-4">

                        <div class="d-flex align-items-center mb-4">

                            <div class="help-icon bg-success-subtle text-success me-3">
                                <i class="bi bi-receipt-cutoff"></i>
                            </div>

                            <div>
                                <h5 class="fw-bold mb-1">Generate an Invoice</h5>
                                <p class="text-muted small mb-0">
                                    Create billing information for an appointment.
                                </p>
                            </div>

                        </div>

                        <div class="help-step">
                            <span class="step-number">1</span>
                            <div>Open Appointment Management.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">2</span>
                            <div>Select the Invoice option for the required appointment.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">3</span>
                            <div>Enter the doctor fee, hospital fee, additional fee and discount.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">4</span>
                            <div>Enter the paid amount and select the payment method.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">5</span>
                            <div>Generate the invoice after checking the payment details.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">6</span>
                            <div>The appointment will be marked as Completed after the invoice is created.</div>
                        </div>

                    </div>

                </div>

            </div>


            <div class="col-lg-6">

                <div class="card help-card shadow-sm h-100">

                    <div class="card-body p-4">

                        <div class="d-flex align-items-center mb-4">

                            <div class="help-icon bg-primary-subtle text-primary me-3">
                                <i class="bi bi-printer-fill"></i>
                            </div>

                            <div>
                                <h5 class="fw-bold mb-1">View and Print an Invoice</h5>
                                <p class="text-muted small mb-0">
                                    View previously generated invoices and print a copy.
                                </p>
                            </div>

                        </div>

                        <div class="help-step">
                            <span class="step-number">1</span>
                            <div>Select Billing / Invoices from the sidebar.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">2</span>
                            <div>Find the required invoice from the invoice list.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">3</span>
                            <div>Click the View button.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">4</span>
                            <div>Check the patient, appointment and payment information.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">5</span>
                            <div>Click Print Invoice to open the browser print option.</div>
                        </div>

                    </div>

                </div>

            </div>


            <div class="col-lg-6">

                <div class="card help-card shadow-sm h-100">

                    <div class="card-body p-4">

                        <div class="d-flex align-items-center mb-4">

                            <div class="help-icon bg-info-subtle text-info me-3">
                                <i class="bi bi-bar-chart-fill"></i>
                            </div>

                            <div>
                                <h5 class="fw-bold mb-1">View and Print Reports</h5>
                                <p class="text-muted small mb-0">
                                    Use clinic reports to view appointment and billing information.
                                </p>
                            </div>

                        </div>

                        <div class="help-step">
                            <span class="step-number">1</span>
                            <div>Select Reports from the sidebar.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">2</span>
                            <div>View the appointment status summary.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">3</span>
                            <div>Select a start date and end date to generate an appointment report.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">4</span>
                            <div>Review the billing and invoice report.</div>
                        </div>

                        <div class="help-step">
                            <span class="step-number">5</span>
                            <div>Use the Print Report button to print the required report.</div>
                        </div>

                    </div>

                </div>

            </div>

        </div>

    </div>

</div>

</body>

</html>