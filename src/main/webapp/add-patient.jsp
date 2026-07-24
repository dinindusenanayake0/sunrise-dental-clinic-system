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
    <title>Add Patient - Sunrise Dental Clinic</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">

<div class="container mt-5">

    <div class="card shadow">

        <div class="card-header bg-primary text-white">
            <h3>Add New Patient</h3>
        </div>

        <div class="card-body">

            <% if(request.getParameter("success") != null){ %>
                <div class="alert alert-success">
                    Patient added successfully.
                </div>
            <% } %>

            <% if(request.getAttribute("error") != null){ %>
                <div class="alert alert-danger">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>

            <form action="add-patient" method="post">

                <div class="row">

                    <div class="col-md-6 mb-3">
                        <label class="form-label">First Name</label>
                        <input type="text"
                               class="form-control"
                               name="firstName"
                               required>
                    </div>

                    <div class="col-md-6 mb-3">
                        <label class="form-label">Last Name</label>
                        <input type="text"
                               class="form-control"
                               name="lastName"
                               required>
                    </div>

                </div>

                <div class="row">

                    <div class="col-md-6 mb-3">
                        <label class="form-label">Gender</label>

                        <select class="form-select" name="gender" required>
                            <option value="">Select Gender</option>
                            <option>Male</option>
                            <option>Female</option>
                        </select>
                    </div>

                    <div class="col-md-6 mb-3">
                        <label class="form-label">Date of Birth</label>
                        <input type="date"
                               class="form-control"
                               name="dateOfBirth"
                               required>
                    </div>

                </div>

                <div class="row">

                    <div class="col-md-6 mb-3">
                        <label class="form-label">Phone</label>
                        <input type="text"
                               class="form-control"
                               name="phone"
                               required>
                    </div>

                    <div class="col-md-6 mb-3">
                        <label class="form-label">Email</label>
                        <input type="email"
                               class="form-control"
                               name="email">
                    </div>

                </div>

                <div class="mb-3">
                    <label class="form-label">Address</label>

                    <textarea class="form-control"
                              name="address"
                              rows="3"></textarea>
                </div>

                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-save me-1"></i>
                    Save Patient
                </button>

                <a href="dashboard.jsp" class="btn btn-secondary">
                    <i class="bi bi-arrow-left me-1"></i>
                    Back to Dashboard
                </a>

            </form>

        </div>

    </div>

</div>

</body>
</html>