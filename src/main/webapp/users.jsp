<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dental.system.model.User" %>

<%
    HttpSession currentSession = request.getSession(false);

    if (currentSession == null || currentSession.getAttribute("loggedUser") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    User loggedUser =
            (User) currentSession.getAttribute("loggedUser");

    if (!"Administrator".equalsIgnoreCase(loggedUser.getRole())) {
        response.sendRedirect(request.getContextPath() + "/dashboard");
        return;
    }

    List<User> users =
            (List<User>) request.getAttribute("users");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Manage Staff - Sunrise Dental Clinic</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <!-- Manage staff styles -->
    <style>

        .staff-card {
            border: none;
            border-radius: 18px;
        }

        .staff-avatar {
            width: 45px;
            height: 45px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 20px;
        }

        .table thead th {
            white-space: nowrap;
        }

        .password-toggle-btn {
            min-width: 46px;
        }

    </style>

</head>

<body class="bg-light">

<jsp:include page="/components/sidebar.jsp"/>

<div class="main-content">

    <div class="container-fluid py-4 px-4">

        <div class="d-flex justify-content-between align-items-center mb-4">

            <div>

                <h2 class="fw-bold mb-1">
                    Manage Staff
                </h2>

                <p class="text-muted mb-0">
                    Add, edit and manage system users.
                </p>

            </div>

            <button type="button"
                    class="btn btn-primary"
                    onclick="openAddUserModal()">

                <i class="bi bi-person-plus-fill me-1"></i>
                Add User

            </button>

        </div>


        <div class="card staff-card shadow-sm">

            <div class="card-body p-4">

                <div class="table-responsive">

                    <table class="table table-hover align-middle mb-0">

                        <thead class="table-light">

                        <tr>

                            <th>User ID</th>

                            <th>User</th>

                            <th>Username</th>

                            <th>Role</th>

                            <th class="text-center">
                                Actions
                            </th>

                        </tr>

                        </thead>

                        <tbody>

                        <%
                            if (users != null && !users.isEmpty()) {

                                for (User user : users) {

                                    boolean isOwnAccount =
                                            user.getUserId() == loggedUser.getUserId();
                        %>

                        <tr>

                            <td>
                                <%= user.getUserId() %>
                            </td>

                            <td>

                                <div class="d-flex align-items-center">

                                    <div class="staff-avatar
                                                bg-primary-subtle
                                                text-primary
                                                me-3">

                                        <i class="bi bi-person-fill"></i>

                                    </div>

                                    <div>

                                        <div class="fw-semibold">
                                            <%= user.getUsername() %>
                                        </div>

                                        <% if (isOwnAccount) { %>

                                        <small class="text-muted">
                                            Your account
                                        </small>

                                        <% } %>

                                    </div>

                                </div>

                            </td>

                            <td>
                                <%= user.getUsername() %>
                            </td>

                            <td>

                                <% if ("Administrator".equalsIgnoreCase(user.getRole())) { %>

                                <span class="badge bg-primary">
                                    Administrator
                                </span>

                                <% } else { %>

                                <span class="badge bg-secondary">
                                    User
                                </span>

                                <% } %>

                            </td>

                            <td class="text-center">

                                <button type="button"
                                        class="btn btn-sm btn-outline-primary me-1"
                                        onclick="openEditUser(
                                                '<%= user.getUserId() %>',
                                                '<%= user.getUsername() %>',
                                                '<%= user.getRole() %>',
                                                <%= isOwnAccount %>
                                        )">

                                    <i class="bi bi-pencil-square"></i>

                                </button>

                                <% if (!isOwnAccount) { %>

                                <button type="button"
                                        class="btn btn-sm btn-outline-danger"
                                        onclick="confirmDeleteUser(
                                                <%= user.getUserId() %>,
                                                '<%= user.getUsername() %>'
                                        )">

                                    <i class="bi bi-trash-fill"></i>

                                </button>

                                <% } else { %>

                                <button type="button"
                                        class="btn btn-sm btn-outline-secondary"
                                        disabled>

                                    <i class="bi bi-trash-fill"></i>

                                </button>

                                <% } %>

                            </td>

                        </tr>

                        <%
                                }

                            } else {
                        %>

                        <tr>

                            <td colspan="5"
                                class="text-center text-muted py-4">

                                No users found.

                            </td>

                        </tr>

                        <% } %>

                        </tbody>

                    </table>

                </div>

            </div>

        </div>

    </div>

</div>


<!-- Open add user modal -->
<div class="modal fade"
     id="addUserModal"
     tabindex="-1">

    <div class="modal-dialog modal-dialog-centered">

        <div class="modal-content border-0 rounded-4">

            <form method="post"
                  action="<%= request.getContextPath() %>/users"
                  id="addUserForm"
                  autocomplete="off">

                <div class="modal-header">

                    <h5 class="modal-title fw-bold">
                        Add New User
                    </h5>

                    <button type="button"
                            class="btn-close"
                            data-bs-dismiss="modal">
                    </button>

                </div>

                <div class="modal-body">

                    <input type="hidden"
                           name="formAction"
                           value="add">

                    <div class="mb-3">

                        <label class="form-label">
                            Username
                        </label>

                        <input type="text"
                               name="username"
                               id="addUsername"
                               class="form-control"
                               autocomplete="off"
                               required>

                    </div>

                    <div class="mb-3">

                        <label class="form-label">
                            Password
                        </label>

                        <div class="input-group">

                            <input type="password"
                                   name="password"
                                   id="addPassword"
                                   class="form-control"
                                   autocomplete="new-password"
                                   required>

                            <button type="button"
                                    class="btn btn-outline-secondary password-toggle-btn"
                                    onclick="togglePasswordVisibility(
                                            'addPassword',
                                            'addPasswordIcon'
                                    )">

                                <i id="addPasswordIcon"
                                   class="bi bi-eye"></i>

                            </button>

                        </div>

                    </div>

                    <div class="mb-3">

                        <label class="form-label">
                            Role
                        </label>

                        <select name="role"
                                id="addRole"
                                class="form-select"
                                required>

                            <option value="USER"
                                    selected>
                                User
                            </option>

                            <option value="Administrator">
                                Administrator
                            </option>

                        </select>

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

                        <i class="bi bi-person-plus-fill me-1"></i>
                        Add User

                    </button>

                </div>

            </form>

        </div>

    </div>

</div>

<!-- Edit User Modal -->
<div class="modal fade"
     id="editUserModal"
     tabindex="-1">

    <div class="modal-dialog modal-dialog-centered">

        <div class="modal-content border-0 rounded-4">

            <form method="post"
                  action="<%= request.getContextPath() %>/users"
                  id="editUserForm"
                  autocomplete="off">

                <div class="modal-header">

                    <h5 class="modal-title fw-bold">
                        Edit User
                    </h5>

                    <button type="button"
                            class="btn-close"
                            data-bs-dismiss="modal">
                    </button>

                </div>

                <div class="modal-body">

                    <input type="hidden"
                           name="formAction"
                           value="update">

                    <input type="hidden"
                           name="userId"
                           id="editUserId">

                    <div class="mb-3">

                        <label class="form-label">
                            Username
                        </label>

                        <input type="text"
                               name="username"
                               id="editUsername"
                               class="form-control"
                               autocomplete="off"
                               required>

                    </div>

                    <div class="mb-3">

                        <label class="form-label">
                            New Password
                        </label>

                        <div class="input-group">

                            <input type="password"
                                   name="password"
                                   id="editPassword"
                                   class="form-control"
                                   autocomplete="new-password"
                                   >

                            <button type="button"
                                    class="btn btn-outline-secondary password-toggle-btn"
                                    onclick="togglePasswordVisibility(
                                            'editPassword',
                                            'editPasswordIcon'
                                    )">

                                <i id="editPasswordIcon"
                                   class="bi bi-eye"></i>

                            </button>

                        </div>

                        <div class="form-text">
                            Leave blank to keep current password
                        </div>

                    </div>

                    <div class="mb-3">

                        <label class="form-label">
                            Role
                        </label>

                        <select name="role"
                                id="editRole"
                                class="form-select"
                                required>

                            <option value="USER">
                                User
                            </option>

                            <option value="Administrator">
                                Administrator
                            </option>

                        </select>

                        <div id="ownAccountMessage"
                             class="form-text d-none">

                            Your role cannot be changed

                        </div>

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

                        <i class="bi bi-check-circle-fill me-1"></i>
                        Update User

                    </button>

                </div>

            </form>

        </div>

    </div>

</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- Password visibility -->
<script>

    function togglePasswordVisibility(
            inputId,
            iconId
    ) {

        const passwordInput =
                document.getElementById(inputId);

        const passwordIcon =
                document.getElementById(iconId);

        if (!passwordInput || !passwordIcon) {
            return;
        }

        if (passwordInput.type === "password") {

            passwordInput.type =
                    "text";

            passwordIcon.className =
                    "bi bi-eye-slash";

        } else {

            passwordInput.type =
                    "password";

            passwordIcon.className =
                    "bi bi-eye";
        }
    }


    <!-- Rest form -->
    function resetAddUserForm() {

        const form =
                document.getElementById("addUserForm");

        const passwordInput =
                document.getElementById("addPassword");

        const passwordIcon =
                document.getElementById("addPasswordIcon");

        const roleSelect =
                document.getElementById("addRole");

        if (form) {

            form.reset();
        }

        if (passwordInput) {

            passwordInput.value = "";

            passwordInput.type =
                    "password";
        }

        if (passwordIcon) {

            passwordIcon.className =
                    "bi bi-eye";
        }

        if (roleSelect) {

            roleSelect.value =
                    "USER";
        }
    }


    function openAddUserModal() {

        resetAddUserForm();

        const modalElement =
                document.getElementById("addUserModal");

        const modal =
                bootstrap.Modal.getOrCreateInstance(
                        modalElement
                );

        modal.show();
    }


    function openEditUser(
            userId,
            username,
            role,
            isOwnAccount
    ) {

        document.getElementById("editUserId").value =
                userId;

        document.getElementById("editUsername").value =
                username;

        document.getElementById("editPassword").value =
                "";

        document.getElementById("editPassword").type =
                "password";

        document.getElementById("editPasswordIcon").className =
                "bi bi-eye";

        const roleSelect =
                document.getElementById("editRole");

        const ownAccountMessage =
                document.getElementById("ownAccountMessage");

        if ("Administrator".toLowerCase() ===
                role.toLowerCase()) {

            roleSelect.value =
                    "Administrator";

        } else {

            roleSelect.value =
                    "USER";
        }

        if (isOwnAccount) {

            roleSelect.disabled =
                    true;

            ownAccountMessage.classList.remove(
                    "d-none"
            );

        } else {

            roleSelect.disabled =
                    false;

            ownAccountMessage.classList.add(
                    "d-none"
            );
        }

        const modalElement =
                document.getElementById("editUserModal");

        const modal =
                bootstrap.Modal.getOrCreateInstance(
                        modalElement
                );

        modal.show();
    }


    <!-- Confirm user deletion -->
    function confirmDeleteUser(
            userId,
            username
    ) {

        Swal.fire({

            title: "Delete User?",

            text: "Are you sure you want to delete " + username + "?",

            icon: "warning",

            showCancelButton: true,

            confirmButtonColor: "#dc3545",

            cancelButtonColor: "#6c757d",

            confirmButtonText: "Yes, Delete",

            cancelButtonText: "Cancel"

        }).then((result) => {

            if (result.isConfirmed) {

                window.location.href =
                        "<%= request.getContextPath() %>/users?action=delete&id="
                        + userId;
            }
        });
    }


    const addUserModalElement =
            document.getElementById("addUserModal");

    if (addUserModalElement) {

        addUserModalElement.addEventListener(
                "hidden.bs.modal",
                function () {

                    resetAddUserForm();
                }
        );
    }


    const editUserModalElement =
            document.getElementById("editUserModal");

    if (editUserModalElement) {

        editUserModalElement.addEventListener(
                "hidden.bs.modal",
                function () {

                    const editForm =
                            document.getElementById("editUserForm");

                    const passwordInput =
                            document.getElementById("editPassword");

                    const passwordIcon =
                            document.getElementById("editPasswordIcon");

                    const roleSelect =
                            document.getElementById("editRole");

                    if (editForm) {

                        editForm.reset();
                    }

                    if (passwordInput) {

                        passwordInput.value =
                                "";

                        passwordInput.type =
                                "password";
                    }

                    if (passwordIcon) {

                        passwordIcon.className =
                                "bi bi-eye";
                    }

                    if (roleSelect) {

                        roleSelect.disabled =
                                false;
                    }
                }
        );
    }

</script>


<% if ("true".equals(request.getParameter("addSuccess"))) { %>

<script>

    Swal.fire({

        icon: "success",

        title: "User Added",

        text: "The user has been added successfully.",

        confirmButtonColor: "#0d6efd"

    });

</script>

<% } %>


<% if ("true".equals(request.getParameter("addError"))) { %>

<script>

    Swal.fire({

        icon: "error",

        title: "Unable to Add User",

        text: "Please check the details. The username may already exist.",

        confirmButtonColor: "#0d6efd"

    });

</script>

<% } %>


<% if ("true".equals(request.getParameter("updateSuccess"))) { %>

<script>

    Swal.fire({

        icon: "success",

        title: "User Updated",

        text: "The user details have been updated successfully.",

        confirmButtonColor: "#0d6efd"

    });

</script>

<% } %>


<% if ("true".equals(request.getParameter("updateError"))) { %>

<script>

    Swal.fire({

        icon: "error",

        title: "Update Failed",

        text: "The user details could not be updated.",

        confirmButtonColor: "#0d6efd"

    });

</script>

<% } %>


<% if ("true".equals(request.getParameter("deleteSuccess"))) { %>

<script>

    Swal.fire({

        icon: "success",

        title: "User Deleted",

        text: "The user has been deleted successfully.",

        confirmButtonColor: "#0d6efd"

    });

</script>

<% } %>


<% if ("true".equals(request.getParameter("deleteError"))) { %>

<script>

    Swal.fire({

        icon: "error",

        title: "Delete Failed",

        text: "The user could not be deleted.",

        confirmButtonColor: "#0d6efd"

    });

</script>

<% } %>


<% if ("true".equals(request.getParameter("selfDeleteError"))) { %>

<script>

    Swal.fire({

        icon: "warning",

        title: "Action Not Allowed",

        text: "You cannot delete your own administrator account.",

        confirmButtonColor: "#0d6efd"

    });

</script>

<% } %>

</body>

</html>