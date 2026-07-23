<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Sunrise Dental Clinic</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container vh-100 d-flex justify-content-center align-items-center">

    <div class="card shadow p-4" style="width: 400px;">

        <h3 class="text-center mb-4">Sunrise Dental Clinic</h3>

        <form action="login" method="post">

            <div class="mb-3">
                <label class="form-label">Username</label>
                <input type="text"
                       class="form-control"
                       name="username"
                       placeholder="Enter Username"
                       required>
            </div>

            <div class="mb-3">
                <label class="form-label">Password</label>
                <input type="password"
                       class="form-control"
                       name="password"
                       placeholder="Enter Password"
                       required>
            </div>

            <% String error = (String) request.getAttribute("error");
               if (error != null) { %>

                <div class="alert alert-danger">
                    <%= error %>
                </div>

            <% } %>

            <button type="submit" class="btn btn-primary w-100">
                Login
            </button>

        </form>

    </div>

</div>

</body>
</html>