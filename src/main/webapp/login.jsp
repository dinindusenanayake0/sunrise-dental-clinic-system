<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Sunrise Dental Clinic</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body.login-page {
            min-height: 100vh;
            margin: 0;
            background: linear-gradient(
                        135deg,
                        #eef8ff 0%,
                        #dcefff 40%,
                        #c8e5ff 70%,
                        #eef8ff 100%
                );
        }

        .login-illustration {
            display: block;
            width: 190px;
            height: auto;
            margin: 8px auto 18px;
        }

        .login-card {
            width: 400px;
            border: none;
            border-radius: 20px;
            box-shadow: 0 18px 45px rgba(13, 110, 253, 0.14);
        }

        .login-title {
            color: #0d6efd;
            font-weight: 700;
        }
    </style>
</head>
<body class="login-page">

<div class="container vh-100 d-flex justify-content-center align-items-center">

    <div class="card p-4 login-card">

        <h3 class="text-center mb-4 login-title">Sunrise Dental Clinic</h3>
        <img src="<%= request.getContextPath() %>/images/login.svg"
             alt="Dental Clinic"
             class="login-illustration">
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