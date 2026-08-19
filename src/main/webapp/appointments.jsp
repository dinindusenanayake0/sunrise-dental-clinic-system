<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dental.system.model.Patient" %>
<%@ page import="com.dental.system.model.Appointment" %>

<%
    HttpSession currentSession = request.getSession(false);

    if (currentSession == null ||
            currentSession.getAttribute("loggedUser") == null) {

        response.sendRedirect("login.jsp");
        return;
    }

    List<Patient> patients =
            (List<Patient>) request.getAttribute("patients");

    List<Appointment> appointments =
            (List<Appointment>) request.getAttribute("appointments");

    Appointment editAppointment =
            (Appointment) request.getAttribute("editAppointment");

    boolean editMode = editAppointment != null;
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Appointments - Sunrise Dental Clinic</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

</head>

<body class="bg-light">

<jsp:include page="/components/sidebar.jsp"/>

<div class="main-content">

<div class="container py-4 px-4">

    <div class="d-flex justify-content-between align-items-center mb-4">

        <div>

            <h2 class="fw-bold mb-1">
                Appointment Management
            </h2>

            <p class="text-muted mb-0">
                Register and view patient appointments.
            </p>

        </div>
    </div>


    <div class="card border-0 shadow-sm mb-5">

        <div class="card-header bg-primary text-white py-3">

            <h5 class="mb-0">

                <i class="bi <%= editMode
                        ? "bi-pencil-square"
                        : "bi-calendar-plus" %> me-2"></i>

                <%= editMode
                        ? "Edit Appointment"
                        : "Register New Appointment" %>

            </h5>

        </div>

        <div class="card-body p-4">

            <% if (patients == null || patients.isEmpty()) { %>

            <div class="alert alert-warning">

                <i class="bi bi-exclamation-triangle-fill me-2"></i>

                No registered patients found.

                Please register a patient before creating an appointment.

                <a href="add-patient.jsp"
                   class="alert-link">

                    Add Patient

                </a>

            </div>

            <% } else { %>

            <form action="appointments"
                  method="post">

                <input type="hidden"
                       name="formAction"
                       value="<%= editAppointment != null ? "update" : "add" %>">

                <% if (editAppointment != null) { %>
                    <input type="hidden"
                           name="appointmentId"
                           value="<%= editAppointment.getAppointmentId() %>">
                <% } %>

                <div class="row">

                    <div class="col-md-6 mb-3">

                        <label for="patientId"
                               class="form-label">

                            Patient
                            <span class="text-danger">*</span>

                        </label>

                        <select class="form-select"
                                id="patientId"
                                name="patientId"
                                required>

                            <option value="">
                                Select a patient
                            </option>

                            <% for (Patient patient : patients) { %>

                            <option value="<%= patient.getPatientId() %>"
                                    <%= editMode &&
                                        editAppointment.getPatientId() == patient.getPatientId()
                                            ? "selected"
                                            : "" %>>

                                P<%= String.format("%04d", patient.getPatientId()) %>
                                -
                                <%= patient.getFirstName() %>
                                <%= patient.getLastName() %>

                            </option>

                            <% } %>

                        </select>

                    </div>


                    <div class="col-md-3 mb-3">

                        <label for="appointmentDate"
                               class="form-label">

                            Appointment Date
                            <span class="text-danger">*</span>

                        </label>

                        <input type="date"
                               class="form-control"
                               id="appointmentDate"
                               name="appointmentDate"
                               value="<%= editMode
                                              ? editAppointment.getAppointmentDate()
                                              : "" %>"
                               required>

                    </div>


                    <div class="col-md-3 mb-3">

                        <label for="appointmentTime"
                               class="form-label">

                            Appointment Time
                            <span class="text-danger">*</span>

                        </label>

                        <input type="time"
                               class="form-control"
                               id="appointmentTime"
                               name="appointmentTime"
                               value="<%= editMode
                                              ? editAppointment.getAppointmentTime()
                                              : "" %>"
                               required>

                    </div>

                </div>

                <div class="row">


                    <div class="col-md-6 mb-3">

                        <label for="dentistName"
                               class="form-label">

                            Dentist
                            <span class="text-danger">*</span>

                        </label>

                        <select class="form-select"
                                id="dentistName"
                                name="dentistName"
                                required>

                            <option value="">
                                Select a dentist
                            </option>

                            <option value="Dr. Perera"
                                    <%= editMode &&
                                        "Dr. Perera".equals(editAppointment.getDentistName())
                                            ? "selected"
                                            : "" %>>
                                Dr. Perera
                            </option>

                            <option value="Dr. Silva"
                                    <%= editMode &&
                                        "Dr. Silva".equals(editAppointment.getDentistName())
                                            ? "selected"
                                            : "" %>>
                                Dr. Silva
                            </option>

                            <option value="Dr. Fernando"
                                    <%= editMode &&
                                        "Dr. Fernando".equals(editAppointment.getDentistName())
                                            ? "selected"
                                            : "" %>>
                                Dr. Fernando
                            </option>

                        </select>

                    </div>


                    <div class="col-md-6 mb-3">

                        <label for="treatmentType"
                               class="form-label">

                            Treatment Type
                            <span class="text-danger">*</span>

                        </label>

                        <select class="form-select"
                                id="treatmentType"
                                name="treatmentType"
                                required>

                            <option value="">
                                Select a treatment
                            </option>

                            <option value="Dental Consultation"
                                    <%= editMode &&
                                        "Dental Consultation".equals(
                                                editAppointment.getTreatmentType()
                                        ) ? "selected" : "" %>>
                                Dental Consultation
                            </option>

                            <option value="Dental Cleaning"
                                    <%= editMode &&
                                        "Dental Cleaning".equals(
                                                editAppointment.getTreatmentType()
                                        ) ? "selected" : "" %>>
                                Dental Cleaning
                            </option>

                            <option value="Tooth Filling"
                                    <%= editMode &&
                                        "Tooth Filling".equals(
                                                editAppointment.getTreatmentType()
                                        ) ? "selected" : "" %>>
                                Tooth Filling
                            </option>

                            <option value="Tooth Extraction"
                                    <%= editMode &&
                                        "Tooth Extraction".equals(
                                                editAppointment.getTreatmentType()
                                        ) ? "selected" : "" %>>
                                Tooth Extraction
                            </option>

                            <option value="Root Canal Treatment"
                                    <%= editMode &&
                                        "Root Canal Treatment".equals(
                                                editAppointment.getTreatmentType()
                                        ) ? "selected" : "" %>>
                                Root Canal Treatment
                            </option>

                            <option value="Teeth Whitening"
                                    <%= editMode &&
                                        "Teeth Whitening".equals(
                                                editAppointment.getTreatmentType()
                                        ) ? "selected" : "" %>>
                                Teeth Whitening
                            </option>

                            <option value="Dental Checkup"
                                    <%= editMode &&
                                        "Dental Checkup".equals(
                                                editAppointment.getTreatmentType()
                                        ) ? "selected" : "" %>>
                                Dental Checkup
                            </option>

                        </select>

                    </div>

                </div>


                <div class="mb-3">

                    <label for="notes"
                           class="form-label">

                        Notes

                    </label>

                    <textarea class="form-control"
                              id="notes"
                              name="notes"
                              rows="3"
                              placeholder="Enter additional appointment notes"><%= editMode &&
                                                editAppointment.getNotes() != null
                                                    ? editAppointment.getNotes()
                                                    : "" %></textarea>

                </div>

                <div class="d-flex justify-content-end">

                    <div class="d-flex justify-content-end">

                        <% if (editMode) { %>

                        <a href="<%= request.getContextPath() %>/appointments"
                           class="btn btn-outline-secondary me-2">

                            <i class="bi bi-x-circle me-1"></i>
                            Cancel Edit

                        </a>

                        <% } else { %>

                        <button type="reset"
                                class="btn btn-outline-secondary me-2">

                            <i class="bi bi-arrow-counterclockwise me-1"></i>
                            Clear

                        </button>

                        <% } %>

                        <button type="submit"
                                class="btn <%= editMode ? "btn-warning" : "btn-primary" %>">

                            <i class="bi <%= editMode
                                    ? "bi-check-circle"
                                    : "bi-calendar-check" %> me-1"></i>

                            <%= editMode
                                    ? "Update Appointment"
                                    : "Register Appointment" %>

                        </button>

                    </div>

                </div>

            </form>

            <% } %>

        </div>

    </div>


    <div class="card border-0 shadow-sm">

        <div class="card-header bg-white py-3">

            <div class="d-flex justify-content-between align-items-center">

                <h5 class="mb-0">

                    <i class="bi bi-calendar3 me-2 text-primary"></i>

                    Registered Appointments

                </h5>

                <div class="input-group"
                     style="max-width: 360px;">

                    <span class="input-group-text">

                        <i class="bi bi-search"></i>

                    </span>

                    <input type="text"
                           id="appointmentSearch"
                           class="form-control"
                           placeholder="Search by appointment number">

                </div>

            </div>

        </div>

        <div class="card-body">

            <div class="table-responsive">

                <table class="table table-hover align-middle"
                       id="appointmentTable">

                    <thead class="table-primary">

                    <tr>

                        <th>Appointment No.</th>
                        <th>Patient</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Dentist</th>
                        <th>Treatment</th>
                        <th>Status</th>
                        <th>Action</th>

                    </tr>

                    </thead>

                    <tbody>

                    <% if (appointments != null &&
                            !appointments.isEmpty()) { %>

                    <% for (Appointment appointment : appointments) { %>

                    <tr>

                        <td class="fw-semibold text-primary">

                            <%= appointment.getAppointmentNumber() %>

                        </td>

                        <td>

                            <%
                                String patientName =
                                        "Patient ID: " +
                                                appointment.getPatientId();

                                if (patients != null) {

                                    for (Patient patient : patients) {

                                        if (patient.getPatientId() ==
                                                appointment.getPatientId()) {

                                            patientName =
                                                    patient.getFirstName() +
                                                            " " +
                                                            patient.getLastName();

                                            break;
                                        }
                                    }
                                }
                            %>

                            <%= patientName %>

                        </td>

                        <td>
                            <%= appointment.getAppointmentDate() %>
                        </td>

                        <td>
                            <%= appointment.getAppointmentTime() %>
                        </td>

                        <td>
                            <%= appointment.getDentistName() %>
                        </td>

                        <td>
                            <%= appointment.getTreatmentType() %>
                        </td>

                        <td>

                            <% if ("Scheduled".equalsIgnoreCase(
                                    appointment.getStatus())) { %>

                            <span class="badge bg-warning text-dark">
                                Scheduled
                            </span>

                            <% } else if ("Completed".equalsIgnoreCase(
                                    appointment.getStatus())) { %>

                            <span class="badge bg-success">
                                Completed
                            </span>

                            <% } else if ("Cancelled".equalsIgnoreCase(
                                    appointment.getStatus())) { %>

                            <span class="badge bg-danger">
                                Cancelled
                            </span>

                            <% } else { %>

                            <span class="badge bg-secondary">

                                <%= appointment.getStatus() %>

                            </span>

                            <% } %>

                        </td>

                        <td>

                            <% boolean isCancelled =
                                    "Cancelled".equalsIgnoreCase(
                                            appointment.getStatus()
                                    ); %>

                            <% if (isCancelled) { %>

                                <button type="button"
                                        class="btn btn-sm btn-outline-secondary"
                                        title="Cancelled appointment cannot be edited"
                                        disabled>

                                    <i class="bi bi-pencil-square"></i>

                                </button>

                            <% } else { %>

                                <a href="<%= request.getContextPath() %>/appointments?action=edit&id=<%= appointment.getAppointmentId() %>"
                                   class="btn btn-sm btn-outline-warning"
                                   title="Edit Appointment">

                                    <i class="bi bi-pencil-square"></i>

                                </a>

                            <% } %>


                            <button type="button"
                                    class="btn btn-sm btn-outline-primary"
                                    title="View Appointment Details"
                                    data-bs-toggle="modal"
                                    data-bs-target="#viewAppointmentModal"
                                    onclick="loadAppointmentDetails(
                                            '<%= appointment.getAppointmentId() %>',
                                            '<%= appointment.getAppointmentNumber() %>',
                                            '<%= patientName %>',
                                            '<%= appointment.getAppointmentDate() %>',
                                            '<%= appointment.getAppointmentTime() %>',
                                            '<%= appointment.getDentistName() %>',
                                            '<%= appointment.getTreatmentType() %>',
                                            '<%= appointment.getStatus() %>',
                                            '<%= appointment.getNotes() == null ? "" : appointment.getNotes() %>'
                                    )">

                                <i class="bi bi-eye"></i>

                            </button>


                            <% if (isCancelled) { %>

                                <button type="button"
                                        class="btn btn-sm btn-outline-secondary"
                                        title="Invoice cannot be generated for a cancelled appointment"
                                        disabled>

                                    <i class="bi bi-receipt"></i>

                                </button>

                            <% } else { %>

                                <a href="<%= request.getContextPath() %>/invoice?appointmentId=<%= appointment.getAppointmentId() %>"
                                   class="btn btn-sm btn-outline-success"
                                   title="Generate or View Invoice">

                                    <i class="bi bi-receipt"></i>

                                </a>

                            <% } %>

                        </td>

                    </tr>

                    <% } %>

                    <% } else { %>

                    <tr>

                        <td colspan="8"
                            class="text-center text-muted py-5">

                            <i class="bi bi-calendar-x fs-1 d-block mb-2"></i>

                            No appointments have been registered yet.

                        </td>

                    </tr>

                    <% } %>

                    </tbody>

                </table>

            </div>

            <div id="noAppointmentResults"
                 class="alert alert-warning text-center d-none">

                No matching appointments found.

            </div>

        </div>

    </div>

</div>

<!-- View Appointment Details -->
<div class="modal fade"
     id="viewAppointmentModal"
     tabindex="-1"
     aria-hidden="true">

    <div class="modal-dialog modal-lg modal-dialog-centered">

        <div class="modal-content">

            <div class="modal-header bg-primary text-white">

                <h5 class="modal-title">

                    <i class="bi bi-calendar-event me-2"></i>

                    Appointment Details

                </h5>

                <button type="button"
                        class="btn-close btn-close-white"
                        data-bs-dismiss="modal"
                        aria-label="Close">
                </button>

            </div>

            <div class="modal-body p-4">

                <div class="row g-3">

                    <div class="col-md-6">

                        <label class="text-muted small">
                            Appointment Number
                        </label>

                        <p class="fw-bold text-primary mb-0"
                           id="viewAppointmentNumber">
                        </p>

                    </div>

                    <div class="col-md-6">

                        <label class="text-muted small">
                            Patient
                        </label>

                        <p class="fw-semibold mb-0"
                           id="viewPatientName">
                        </p>

                    </div>

                    <div class="col-md-6">

                        <label class="text-muted small">
                            Appointment Date
                        </label>

                        <p class="fw-semibold mb-0"
                           id="viewAppointmentDate">
                        </p>

                    </div>

                    <div class="col-md-6">

                        <label class="text-muted small">
                            Appointment Time
                        </label>

                        <p class="fw-semibold mb-0"
                           id="viewAppointmentTime">
                        </p>

                    </div>

                    <div class="col-md-6">

                        <label class="text-muted small">
                            Dentist
                        </label>

                        <p class="fw-semibold mb-0"
                           id="viewDentistName">
                        </p>

                    </div>

                    <div class="col-md-6">

                        <label class="text-muted small">
                            Treatment Type
                        </label>

                        <p class="fw-semibold mb-0"
                           id="viewTreatmentType">
                        </p>

                    </div>

                    <div class="col-md-6">

                        <label class="text-muted small">
                            Status
                        </label>

                        <div id="viewAppointmentStatus">
                        </div>

                    </div>

                    <div class="col-12">

                        <label class="text-muted small">
                            Notes
                        </label>

                        <div class="border rounded bg-light p-3"
                             id="viewAppointmentNotes">
                        </div>

                    </div>

                </div>

            </div>

            <div class="modal-footer">
                <button type="button"
                        class="btn btn-danger"
                        id="cancelAppointmentBtn"
                        onclick="confirmCancelAppointment()">

                    <i class="bi bi-x-circle me-1"></i>
                    Cancel Appointment

                </button>

                <button type="button"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal">

                    Close

                </button>


            </div>

        </div>

    </div>

</div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    let selectedAppointmentId = null;

    function loadAppointmentDetails(
        appointmentId,
        appointmentNumber,
        patientName,
        appointmentDate,
        appointmentTime,
        dentistName,
        treatmentType,
        status,
        notes
    ) {

        selectedAppointmentId = appointmentId;

        document.getElementById("viewAppointmentNumber").textContent =
            appointmentNumber;

        document.getElementById("viewPatientName").textContent =
            patientName;

        document.getElementById("viewAppointmentDate").textContent =
            appointmentDate;

        document.getElementById("viewAppointmentTime").textContent =
            appointmentTime;

        document.getElementById("viewDentistName").textContent =
            dentistName;

        document.getElementById("viewTreatmentType").textContent =
            treatmentType;

        document.getElementById("viewAppointmentNotes").textContent =
            notes === "" ? "No additional notes." : notes;

        const statusContainer =
            document.getElementById("viewAppointmentStatus");

        let badgeClass = "bg-secondary";

        if (status.toLowerCase() === "scheduled") {
            badgeClass = "bg-warning text-dark";
        } else if (status.toLowerCase() === "completed") {
            badgeClass = "bg-success";
        } else if (status.toLowerCase() === "cancelled") {
            badgeClass = "bg-danger";
        }

        statusContainer.innerHTML =
            '<span class="badge ' +
            badgeClass +
            '">' +
            status +
            '</span>';

        const cancelButton =
            document.getElementById("cancelAppointmentBtn");

        if (status.toLowerCase() === "scheduled") {
            cancelButton.classList.remove("d-none");
        } else {
            cancelButton.classList.add("d-none");
        }
    }

</script>

<script>
    function confirmCancelAppointment() {

        if (!selectedAppointmentId) {
            Swal.fire({
                icon: 'error',
                title: 'Invalid Appointment',
                text: 'Appointment ID was not found.',
                confirmButtonColor: '#0d6efd'
            });
            return;
        }

        Swal.fire({
            title: 'Cancel Appointment?',
            text: 'This appointment will be marked as cancelled.',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#dc3545',
            cancelButtonColor: '#6c757d',
            confirmButtonText: 'Yes, Cancel',
            cancelButtonText: 'No'
        }).then((result) => {

            if (result.isConfirmed) {
                window.location.href =
                    '<%= request.getContextPath() %>/appointments?action=cancel&id='
                    + selectedAppointmentId;
            }
        });
    }
</script>

<script>

    const appointmentDate =
        document.getElementById("appointmentDate");

    const editMode =
        <%= editMode %>;

    if (appointmentDate && !editMode) {

        const today =
            new Date().toISOString().split("T")[0];

        appointmentDate.setAttribute("min", today);
    }

</script>


<script>
    const appointmentSearch =
        document.getElementById("appointmentSearch");

    const appointmentTable =
        document.getElementById("appointmentTable");

    const appointmentRows =
        appointmentTable.querySelectorAll("tbody tr");

    const noAppointmentResults =
        document.getElementById("noAppointmentResults");

    appointmentSearch.addEventListener("keyup", function () {

        const searchValue =
            appointmentSearch.value.toLowerCase().trim();

        let visibleCount = 0;

        appointmentRows.forEach(function (row) {

            const appointmentNumberCell =
                row.querySelector("td:first-child");

            if (!appointmentNumberCell) {
                return;
            }

            const appointmentNumber =
                appointmentNumberCell.textContent
                    .toLowerCase()
                    .trim();

            const visible =
                appointmentNumber.includes(searchValue);

            row.style.display =
                visible ? "" : "none";

            if (visible) {
                visibleCount++;
            }
        });

        if (visibleCount === 0 && searchValue !== "") {
            noAppointmentResults.classList.remove("d-none");
        } else {
            noAppointmentResults.classList.add("d-none");
        }
    });
</script>


<% if ("added".equals(request.getParameter("success"))) { %>
<% if ("updated".equals(request.getParameter("success"))) { %>

<script>
    function confirmCancelAppointment() {

        if (!selectedAppointmentId) {
            return;
        }

        Swal.fire({
            title: 'Cancel Appointment?',
            text: 'This appointment will be marked as cancelled.',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#dc3545',
            cancelButtonColor: '#6c757d',
            confirmButtonText: 'Yes, Cancel',
            cancelButtonText: 'No'
        }).then((result) => {

            if (result.isConfirmed) {
                window.location.href =
                    '<%= request.getContextPath() %>/appointments?action=cancel&id='
                    + selectedAppointmentId;
            }
        });
    }
</script>

<script>

    Swal.fire({
        icon: 'success',
        title: 'Appointment Updated!',
        text: 'The appointment has been updated successfully.',
        confirmButtonColor: '#0d6efd'
    });

</script>

<% } %>

<script>

    Swal.fire({

        icon: 'success',

        title: 'Appointment Registered!',

        text: 'The appointment has been registered successfully.',

        confirmButtonColor: '#0d6efd'

    });

</script>

<% } %>


<% if ("false".equals(request.getParameter("success"))) { %>

<script>

    Swal.fire({

        icon: 'error',

        title: 'Registration Failed',

        text: 'The appointment could not be registered. Please check the details and try again.',

        confirmButtonColor: '#0d6efd'

    });

</script>

<% } %>
<% if ("true".equals(request.getParameter("cancelSuccess"))) { %>

<script>
    Swal.fire({
        icon: 'success',
        title: 'Appointment Cancelled',
        text: 'The appointment has been cancelled successfully.',
        confirmButtonColor: '#0d6efd'
    });
</script>

<% } %>

<% if ("true".equals(request.getParameter("cancelError"))) { %>

<script>
    Swal.fire({
        icon: 'error',
        title: 'Cancellation Failed',
        text: 'The appointment could not be cancelled.',
        confirmButtonColor: '#0d6efd'
    });
</script>

<% } %>
</body>
</html>