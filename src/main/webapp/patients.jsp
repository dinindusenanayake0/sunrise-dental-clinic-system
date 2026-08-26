<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dental.system.model.Patient" %>

<%
    HttpSession currentSession = request.getSession(false);

    if (currentSession == null ||
            currentSession.getAttribute("loggedUser") == null) {

        response.sendRedirect("login.jsp");
        return;
    }

    List<Patient> patients =
            (List<Patient>) request.getAttribute("patients");
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Patients - Sunrise Dental Clinic</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Bootstrap Icons -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <!-- SweetAlert2 -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>


    <!-- Styles -->
    <style>
        .patient-table {
            min-width: 1100px;
        }

        .patient-table th,
        .patient-table td {
            vertical-align: middle;
        }

        .patient-table th {
            white-space: nowrap;
        }

        .patient-table .patient-id-column {
            width: 90px;
        }

        .patient-table .name-column {
            min-width: 150px;
        }

        .patient-table .gender-column {
            width: 90px;
        }

        .patient-table .dob-column {
            min-width: 120px;
            white-space: nowrap;
        }

        .patient-table .phone-column {
            min-width: 120px;
            white-space: nowrap;
        }

        .patient-table .email-column {
            min-width: 220px;
        }

        .patient-table .address-column {
            min-width: 260px;
            max-width: 320px;
            white-space: normal;
        }

        .patient-table .action-column {
            width: 90px;
            white-space: nowrap;
        }
    </style>
</head>

<body class="bg-light">

<jsp:include page="/components/sidebar.jsp"/>

<div class="main-content">

<!-- Main container -->
<div class="container-fluid py-4 px-4">

    <!-- Page heading -->
    <div class="d-flex justify-content-between align-items-center mb-4">

        <div>

            <h2 class="fw-bold mb-1">
                Patients
            </h2>

            <p class="text-muted mb-0">
                View and search registered patients.
            </p>

        </div>

        <button type="button"
                class="btn btn-primary"
                data-bs-toggle="modal"
                data-bs-target="#addPatientModal">

            <i class="bi bi-person-plus me-1"></i>

            Add Patient
        </button>

    </div>

    <!-- Patient card -->
    <div class="card border-0 shadow-sm">

        <div class="card-body">

            <!-- Update success message -->
            <% if ("true".equals(request.getParameter("updated"))) { %>

            <div class="alert alert-success alert-dismissible fade show"
                 role="alert">

                <i class="bi bi-check-circle-fill me-2"></i>

                Patient updated successfully.

                <button type="button"
                        class="btn-close"
                        data-bs-dismiss="alert">
                </button>

            </div>

            <% } %>

            <!-- Update error message -->
            <% if ("false".equals(request.getParameter("updated"))) { %>

            <div class="alert alert-danger alert-dismissible fade show"
                 role="alert">

                <i class="bi bi-exclamation-circle-fill me-2"></i>

                Failed to update patient.

                <button type="button"
                        class="btn-close"
                        data-bs-dismiss="alert">
                </button>

            </div>

            <% } %>

            <!-- Search -->
            <div class="row mb-4">

                <div class="col-md-5">

                    <label for="patientSearch"
                           class="form-label">

                        Search Patient
                    </label>

                    <div class="input-group">

                        <span class="input-group-text">

                            <i class="bi bi-search"></i>

                        </span>

                        <input type="text"
                               id="patientSearch"
                               class="form-control"
                               placeholder="Search by ID, name, phone or email">

                    </div>

                </div>

            </div>

            <!-- Patient table -->
            <div class="table-responsive">

                <table class="table table-hover align-middle patient-table"
                       id="patientTable">

                    <thead class="table-primary">
                    <tr>
                        <th class="patient-id-column">Patient ID</th>
                        <th class="name-column">Name</th>
                        <th class="gender-column">Gender</th>
                        <th class="dob-column">Date of Birth</th>
                        <th class="phone-column">Phone</th>
                        <th class="email-column">Email</th>
                        <th class="address-column">Address</th>
                        <th class="action-column">Action</th>
                    </tr>
                    </thead>

                    <tbody>

                    <% if (patients != null && !patients.isEmpty()) { %>

                        <% for (Patient patient : patients) { %>

                        <tr>

                            <td class="patient-id-column">
                                <%= patient.getPatientId() %>
                            </td>

                            <td class="name-column">
                                <%= patient.getFirstName() %>
                                <%= patient.getLastName() %>
                            </td>

                            <td class="gender-column">
                                <%= patient.getGender() %>
                            </td>

                            <td class="dob-column">
                                <%= patient.getDateOfBirth() %>
                            </td>

                            <td class="phone-column">
                                <%= patient.getPhone() %>
                            </td>

                            <td class="email-column">

                                <%= patient.getEmail() == null ||
                                        patient.getEmail().trim().isEmpty()
                                        ? "-"
                                        : patient.getEmail() %>

                            </td>

                            <td class="address-column">
                                <%= patient.getAddress() == null ||
                                        patient.getAddress().trim().isEmpty()
                                        ? "-"
                                        : patient.getAddress() %>
                            </td>

                            <td class="action-column">
                                <!-- Edit button -->
                                <button type="button"
                                        class="btn btn-sm btn-warning"
                                        title="Edit Patient"
                                        data-bs-toggle="modal"
                                        data-bs-target="#editPatientModal"
                                        onclick="loadPatientData(
                                                '<%= patient.getPatientId() %>',
                                                '<%= patient.getFirstName() %>',
                                                '<%= patient.getLastName() %>',
                                                '<%= patient.getGender() %>',
                                                '<%= patient.getDateOfBirth() %>',
                                                '<%= patient.getPhone() %>',
                                                '<%= patient.getEmail() == null ? "" : patient.getEmail() %>',
                                                '<%= patient.getAddress() == null ? "" : patient.getAddress() %>'
                                                )">

                                    <i class="bi bi-pencil-square"></i>

                                </button>

                                <!-- Delete button -->
                                <button type="button"
                                        class="btn btn-sm btn-danger"
                                        title="Delete Patient"
                                        onclick="confirmDelete(
                                                <%= patient.getPatientId() %>
                                                )">

                                    <i class="bi bi-trash"></i>

                                </button>

                            </td>

                        </tr>

                        <% } %>

                    <% } else { %>

                    <tr>

                        <td colspan="8"
                            class="text-center text-muted py-4">

                            No patients found.

                        </td>

                    </tr>

                    <% } %>

                    </tbody>

                </table>

            </div>

            <!-- No search results -->
            <div id="noSearchResults"
                 class="alert alert-warning text-center d-none">

                No matching patients found.

            </div>

        </div>

    </div>

</div>

<div class="modal fade"
     id="addPatientModal"
     tabindex="-1">

    <div class="modal-dialog modal-lg modal-dialog-centered">

        <div class="modal-content">

            <form action="patients"
                  method="post"
                  id="addPatientForm">

                <div class="modal-header">

                    <h5 class="modal-title">

                        <i class="bi bi-person-plus me-2"></i>

                        Add New Patient
                    </h5>

                    <button type="button"
                            class="btn-close"
                            data-bs-dismiss="modal">
                    </button>

                </div>

                <div class="modal-body">

                    <div class="row">

                        <div class="col-md-6 mb-3">

                            <label class="form-label">
                                First Name
                            </label>

                            <input type="text"
                                   class="form-control"
                                   name="firstName"
                                   required>

                        </div>

                        <div class="col-md-6 mb-3">

                            <label class="form-label">
                                Last Name
                            </label>

                            <input type="text"
                                   class="form-control"
                                   name="lastName"
                                   required>

                        </div>

                    </div>

                    <div class="row">

                        <div class="col-md-6 mb-3">

                            <label class="form-label">
                                Gender
                            </label>

                            <select class="form-select"
                                    name="gender"
                                    required>

                                <option value="">
                                    Select Gender
                                </option>

                                <option value="Male">
                                    Male
                                </option>

                                <option value="Female">
                                    Female
                                </option>

                                <option value="Other">
                                    Other
                                </option>

                            </select>

                        </div>

                        <div class="col-md-6 mb-3">

                            <label class="form-label">
                                Date of Birth
                            </label>

                            <input type="date"
                                   class="form-control"
                                   name="dateOfBirth"
                                   required>

                        </div>

                    </div>

                    <div class="row">

                        <div class="col-md-6 mb-3">

                            <label class="form-label">
                                Phone
                            </label>

                            <input type="text"
                                   class="form-control"
                                   name="phone"
                                   maxlength="10"
                                   pattern="[0-9]{10}"
                                   required>

                        </div>

                        <div class="col-md-6 mb-3">

                            <label class="form-label">
                                Email
                            </label>

                            <input type="email"
                                   class="form-control"
                                   name="email">

                        </div>

                    </div>

                    <div class="mb-3">

                        <label class="form-label">
                            Address
                        </label>

                        <textarea class="form-control"
                                  name="address"
                                  rows="3"></textarea>

                    </div>

                </div>

                <div class="modal-footer">

                    <button type="button"
                            class="btn btn-secondary"
                            data-bs-dismiss="modal">

                        Cancel
                    </button>

                    <button type="submit"
                            class="btn btn-primary">

                        <i class="bi bi-save me-1"></i>

                        Save Patient
                    </button>

                </div>

            </form>

        </div>

    </div>

</div>

<!-- Edit Patient Modal -->
<div class="modal fade"
     id="editPatientModal"
     tabindex="-1"
     aria-hidden="true">

    <div class="modal-dialog modal-lg modal-dialog-centered">

        <div class="modal-content">

            <form action="patients"
                  method="post">


                <div class="modal-header">

                    <h5 class="modal-title">

                        <i class="bi bi-pencil-square me-2"></i>

                        Edit Patient

                    </h5>

                    <button type="button"
                            class="btn-close"
                            data-bs-dismiss="modal"
                            aria-label="Close">
                    </button>

                </div>


                <div class="modal-body">

                    <input type="hidden"
                           name="action"
                           value="update">

                    <input type="hidden"
                           id="editPatientId"
                           name="patientId">

                    <div class="row">


                        <div class="col-md-6 mb-3">

                            <label for="editFirstName"
                                   class="form-label">

                                First Name
                            </label>

                            <input type="text"
                                   class="form-control"
                                   id="editFirstName"
                                   name="firstName"
                                   required>

                        </div>


                        <div class="col-md-6 mb-3">

                            <label for="editLastName"
                                   class="form-label">

                                Last Name
                            </label>

                            <input type="text"
                                   class="form-control"
                                   id="editLastName"
                                   name="lastName"
                                   required>

                        </div>

                    </div>

                    <div class="row">


                        <div class="col-md-6 mb-3">

                            <label for="editGender"
                                   class="form-label">

                                Gender
                            </label>

                            <select class="form-select"
                                    id="editGender"
                                    name="gender"
                                    required>

                                <option value="Male">
                                    Male
                                </option>

                                <option value="Female">
                                    Female
                                </option>

                            </select>

                        </div>


                        <div class="col-md-6 mb-3">

                            <label for="editDateOfBirth"
                                   class="form-label">

                                Date of Birth
                            </label>

                            <input type="date"
                                   class="form-control"
                                   id="editDateOfBirth"
                                   name="dateOfBirth"
                                   required>

                        </div>

                    </div>

                    <div class="row">


                        <div class="col-md-6 mb-3">

                            <label for="editPhone"
                                   class="form-label">

                                Phone
                            </label>

                            <input type="text"
                                   class="form-control"
                                   id="editPhone"
                                   name="phone"
                                   required>

                        </div>


                        <div class="col-md-6 mb-3">

                            <label for="editEmail"
                                   class="form-label">

                                Email
                            </label>

                            <input type="email"
                                   class="form-control"
                                   id="editEmail"
                                   name="email">

                        </div>

                    </div>


                    <div class="mb-3">

                        <label for="editAddress"
                               class="form-label">

                            Address
                        </label>

                        <textarea class="form-control"
                                  id="editAddress"
                                  name="address"
                                  rows="3"></textarea>

                    </div>

                </div>


                <div class="modal-footer">

                    <button type="button"
                            class="btn btn-secondary"
                            data-bs-dismiss="modal">

                        Cancel
                    </button>

                    <button type="submit"
                            class="btn btn-primary">

                        <i class="bi bi-check-circle me-1"></i>

                        Save Changes
                    </button>

                </div>

            </form>

        </div>

    </div>

</div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- Search function -->
<script>

    const searchInput =
        document.getElementById("patientSearch");

    const table =
        document.getElementById("patientTable");

    const rows =
        table.querySelectorAll("tbody tr");

    const noSearchResults =
        document.getElementById("noSearchResults");

    searchInput.addEventListener("keyup", function () {

        const searchValue =
            searchInput.value.toLowerCase().trim();

        let visibleRowCount = 0;

        rows.forEach(function (row) {

            const rowText =
                row.textContent.toLowerCase();

            const isVisible =
                rowText.includes(searchValue);

            row.style.display =
                isVisible ? "" : "none";

            if (isVisible) {
                visibleRowCount++;
            }

        });

        if (visibleRowCount === 0 &&
                searchValue !== "") {

            noSearchResults.classList.remove("d-none");

        } else {

            noSearchResults.classList.add("d-none");

        }

    });

</script>

<!-- Load patient data into edit modal -->
<script>

    function loadPatientData(
        patientId,
        firstName,
        lastName,
        gender,
        dateOfBirth,
        phone,
        email,
        address
    ) {

        document.getElementById("editPatientId").value =
            patientId;

        document.getElementById("editFirstName").value =
            firstName;

        document.getElementById("editLastName").value =
            lastName;

        document.getElementById("editGender").value =
            gender;

        document.getElementById("editDateOfBirth").value =
            dateOfBirth;

        document.getElementById("editPhone").value =
            phone;

        document.getElementById("editEmail").value =
            email;

        document.getElementById("editAddress").value =
            address;
    }

</script>

<!-- Delete confirmation popup -->
<script>

    function confirmDelete(patientId) {

        Swal.fire({

            title: 'Delete Patient?',

            text: 'This action cannot be undone.',

            icon: 'warning',

            showCancelButton: true,

            confirmButtonColor: '#dc3545',

            cancelButtonColor: '#6c757d',

            confirmButtonText: 'Yes, Delete',

            cancelButtonText: 'Cancel',

            reverseButtons: true

        }).then((result) => {

            if (result.isConfirmed) {

                window.location.href =
                    "patients?action=delete&id=" + patientId;
            }

        });

    }

</script>

<!-- Delete success popup -->
<% if ("true".equals(request.getParameter("deleted"))) { %>

<script>

    Swal.fire({

        icon: 'success',

        title: 'Deleted!',

        text: 'Patient has been deleted successfully.',

        confirmButtonColor: '#0d6efd'

    });

</script>

<% } %>

<!-- Delete failed popup -->
<% if ("false".equals(request.getParameter("deleted"))) { %>

<script>

    Swal.fire({

        icon: 'error',

        title: 'Delete Failed',

        text: 'Patient could not be deleted.',

        confirmButtonColor: '#0d6efd'

    });


    const addPatientModal =
        document.getElementById("addPatientModal");

    if (addPatientModal) {

        addPatientModal.addEventListener(
            "hidden.bs.modal",
            function () {

                document.getElementById(
                    "addPatientForm"
                ).reset();
            }
        );
    }

</script>

<% } %>

<!-- Patient added success popup -->
<% if ("true".equals(request.getParameter("added"))) { %>

        <script>

            Swal.fire({
                icon: "success",
                title: "Patient Added",
                text: "Patient has been added successfully.",
                confirmButtonColor: "#0d6efd"
            });

        </script>

<% } %>

</body>
</html>