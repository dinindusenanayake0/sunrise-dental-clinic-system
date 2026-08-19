<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String currentPage = request.getRequestURI();
    String contextPath = request.getContextPath();
%>

<style>
    :root {
        --sidebar-width: 260px;
        --sidebar-bg: #0b3d91;
        --sidebar-hover: #1456b8;
        --sidebar-active: #ffffff;
    }

    .app-sidebar {
        position: fixed;
        top: 0;
        left: 0;
        width: var(--sidebar-width);
        height: 100vh;
        background: var(--sidebar-bg);
        color: white;
        z-index: 1040;
        display: flex;
        flex-direction: column;
        transition: transform 0.3s ease;
    }

    .sidebar-brand {
        padding: 24px 20px;
        border-bottom: 1px solid rgba(255, 255, 255, 0.15);
    }

    .sidebar-brand h4 {
        margin: 0;
        font-weight: 700;
        font-size: 20px;
    }

    .sidebar-brand small {
        color: rgba(255, 255, 255, 0.75);
    }

    .sidebar-menu {
        flex: 1;
        padding: 18px 12px;
        overflow-y: auto;
    }

    .sidebar-link {
        display: flex;
        align-items: center;
        gap: 12px;
        color: rgba(255, 255, 255, 0.85);
        text-decoration: none;
        padding: 12px 14px;
        border-radius: 8px;
        margin-bottom: 6px;
        transition: 0.2s ease;
    }

    .sidebar-link:hover {
        background: var(--sidebar-hover);
        color: white;
    }

    .sidebar-link.active {
        background: var(--sidebar-active);
        color: var(--sidebar-bg);
        font-weight: 600;
    }

    .sidebar-link i {
        font-size: 18px;
        width: 22px;
        text-align: center;
    }

    .sidebar-footer {
        padding: 16px 12px;
        border-top: 1px solid rgba(255, 255, 255, 0.15);
    }

    .logout-link {
        color: #ffffff;
        border: 1px solid rgba(255, 255, 255, 0.45);
    }

    .logout-link:hover {
        background: #dc3545;
        border-color: #dc3545;
    }

    .sidebar-toggle {
        display: none;
        position: fixed;
        top: 15px;
        left: 15px;
        z-index: 1050;
    }

    .main-content {
        margin-left: var(--sidebar-width);
        min-height: 100vh;
        transition: margin-left 0.3s ease;
    }

    @media (max-width: 991.98px) {
        .app-sidebar {
            transform: translateX(-100%);
        }

        .app-sidebar.show {
            transform: translateX(0);
        }

        .sidebar-toggle {
            display: inline-flex;
        }

        .main-content {
            margin-left: 0;
            padding-top: 55px;
        }
    }
</style>

<button type="button"
        class="btn btn-primary sidebar-toggle"
        id="sidebarToggle">

    <i class="bi bi-list fs-5"></i>

</button>

<aside class="app-sidebar" id="appSidebar">

    <div class="sidebar-brand">

        <h4>
            <i class="bi bi-heart-pulse-fill me-2"></i>
            Sunrise Dental
        </h4>

        <small>Clinic Management System</small>

    </div>

    <nav class="sidebar-menu">

        <a href="<%= contextPath %>/dashboard.jsp"
           class="sidebar-link
           <%= currentPage.contains("dashboard") ? "active" : "" %>">

            <i class="bi bi-speedometer2"></i>
            Dashboard

        </a>

        <a href="<%= contextPath %>/patients"
           class="sidebar-link
           <%= currentPage.contains("patient") ? "active" : "" %>">

            <i class="bi bi-people-fill"></i>
            Patient Management

        </a>

        <a href="<%= contextPath %>/appointments"
           class="sidebar-link
           <%= currentPage.contains("appointment") ? "active" : "" %>">

            <i class="bi bi-calendar-check-fill"></i>
            Appointment Management

        </a>

        <a href="<%= contextPath %>/invoices"
           class="sidebar-link
           <%= currentPage.contains("invoice") ||
               currentPage.contains("billing")
               ? "active" : "" %>">

            <i class="bi bi-receipt-cutoff"></i>
            Billing / Invoices

        </a>

        <a href="<%= contextPath %>/reports"
           class="sidebar-link
           <%= currentPage.contains("report") ? "active" : "" %>">

            <i class="bi bi-bar-chart-fill"></i>
            Reports

        </a>

        <a href="<%= contextPath %>/help.jsp"
           class="sidebar-link
           <%= currentPage.contains("help") ? "active" : "" %>">

            <i class="bi bi-question-circle-fill"></i>
            Help

        </a>

    </nav>

    <div class="sidebar-footer">

        <a href="<%= contextPath %>/logout"
           class="sidebar-link logout-link">

            <i class="bi bi-box-arrow-right"></i>
            Logout

        </a>

    </div>

</aside>

<script>
    const sidebarToggle =
        document.getElementById("sidebarToggle");

    const appSidebar =
        document.getElementById("appSidebar");

    if (sidebarToggle && appSidebar) {
        sidebarToggle.addEventListener("click", function () {
            appSidebar.classList.toggle("show");
        });
    }
</script>