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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">

    <div class="card shadow">

        <div class="card-header bg-primary text-white">
            <h3>Sunrise Dental Clinic</h3>
        </div>

        <div class="card-body">

            <h4>Welcome to Dashboard</h4>

            <p>You have successfully logged in.</p>

            <a href="logout" class="btn btn-danger">
                Logout
            </a>

        </div>

    </div>

</div>

</body>
</html>