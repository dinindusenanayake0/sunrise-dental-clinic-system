<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.dental.system.model.Appointment" %>
<%@ page import="com.dental.system.model.Invoice" %>

<%
    HttpSession currentSession = request.getSession(false);

    if (currentSession == null || currentSession.getAttribute("loggedUser") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Integer scheduledCount = (Integer) request.getAttribute("scheduledCount");

    Integer completedCount = (Integer) request.getAttribute("completedCount");

    Integer cancelledCount = (Integer) request.getAttribute("cancelledCount");

    BigDecimal totalRevenue = (BigDecimal) request.getAttribute("totalRevenue");

    List<Appointment> filteredAppointments = (List<Appointment>) request.getAttribute("filteredAppointments");

    List<Invoice> invoices = (List<Invoice>) request.getAttribute("invoices");

    String dateError = (String) request.getAttribute("dateError");

    if (scheduledCount == null) scheduledCount = 0;
    if (completedCount == null) completedCount = 0;
    if (cancelledCount == null) cancelledCount = 0;
    if (totalRevenue == null) totalRevenue = BigDecimal.ZERO;

    int totalAppointments = scheduledCount + completedCount + cancelledCount;

    String startDate = request.getParameter("startDate");

    String endDate = request.getParameter("endDate");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <title>Reports - Sunrise Dental Clinic</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">


    <!-- Report styles -->
    <style>

        .report-card {
            border: none;
            border-radius: 18px;
            min-height: 145px;
            transition: all 0.25s ease;
        }

        .report-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 12px 30px rgba(0, 0, 0, 0.10) !important;
        }

        .report-icon {
            width: 62px;
            height: 62px;
            border-radius: 16px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 27px;
        }

        .report-value {
            font-size: 31px;
            font-weight: 700;
            margin-bottom: 0;
        }

        .report-label {
            color: #6c757d;
            margin-bottom: 5px;
            font-size: 15px;
        }

        .section-card {
            border: none;
            border-radius: 18px;
        }

        .report-title {
            font-weight: 700;
            margin-bottom: 4px;
        }

        .table thead th {
            white-space: nowrap;
        }

        @media print {

            .sidebar,
            .no-print {
                display: none !important;
            }

            .main-content {
                margin-left: 0 !important;
            }

            body {
                background: white !important;
            }

            .card {
                box-shadow: none !important;
            }

        }

    </style>
</head>

<body class="bg-light">

<jsp:include page="/components/sidebar.jsp"/>

<div class="main-content">

    <div class="container-fluid py-4 px-4">

        <div class="mb-4">

            <h2 class="fw-bold">Reports</h2>

            <p class="text-muted mb-0">View and print appointment and billing reports.</p>

        </div>


        <!-- Appointment status summary -->
        <div id="appointmentStatusReport"
             class="card section-card shadow-sm mb-4">

            <div class="card-body p-4">

                <div class="d-flex justify-content-between
                            align-items-center mb-4">

                    <div>

                        <h5 class="report-title">Appointment Status Summary</h5>

                        <p class="text-muted small mb-0">
                            Current appointment status overview.
                        </p>

                    </div>

                    <button type="button"
                            class="btn btn-outline-primary no-print"
                            onclick="printReport(
                                    'appointmentStatusReport',
                                    'Appointment Status Summary Report'
                            )">

                        <i class="bi bi-printer-fill me-1"></i>
                        Print Report

                    </button>

                </div>


                <div class="row g-4">

                    <div class="col-md-6 col-xl-3">

                        <div class="card report-card bg-light">

                            <div class="card-body
                                        d-flex
                                        align-items-center
                                        justify-content-between
                                        p-4">

                                <div>

                                    <p class="report-label">
                                        Total Appointments
                                    </p>

                                    <h2 class="report-value">
                                        <%= totalAppointments %>
                                    </h2>

                                    <small class="text-muted">
                                        All appointments
                                    </small>

                                </div>

                                <div class="report-icon
                                            bg-primary-subtle
                                            text-primary">

                                    <i class="bi bi-calendar2-check-fill"></i>

                                </div>

                            </div>

                        </div>

                    </div>


                    <div class="col-md-6 col-xl-3">

                        <div class="card report-card bg-light">

                            <div class="card-body
                                        d-flex
                                        align-items-center
                                        justify-content-between
                                        p-4">

                                <div>

                                    <p class="report-label">
                                        Scheduled
                                    </p>

                                    <h2 class="report-value">
                                        <%= scheduledCount %>
                                    </h2>

                                    <small class="text-muted">
                                        Appointments
                                    </small>

                                </div>

                                <div class="report-icon
                                            bg-warning-subtle
                                            text-warning">

                                    <i class="bi bi-clock-fill"></i>

                                </div>

                            </div>

                        </div>

                    </div>


                    <div class="col-md-6 col-xl-3">

                        <div class="card report-card bg-light">

                            <div class="card-body
                                        d-flex
                                        align-items-center
                                        justify-content-between
                                        p-4">

                                <div>

                                    <p class="report-label">
                                        Completed
                                    </p>

                                    <h2 class="report-value">
                                        <%= completedCount %>
                                    </h2>

                                    <small class="text-muted">
                                        Appointments
                                    </small>

                                </div>

                                <div class="report-icon
                                            bg-success-subtle
                                            text-success">

                                    <i class="bi bi-check-circle-fill"></i>

                                </div>

                            </div>

                        </div>

                    </div>


                    <div class="col-md-6 col-xl-3">

                        <div class="card report-card bg-light">

                            <div class="card-body
                                        d-flex
                                        align-items-center
                                        justify-content-between
                                        p-4">

                                <div>

                                    <p class="report-label">
                                        Cancelled
                                    </p>

                                    <h2 class="report-value">
                                        <%= cancelledCount %>
                                    </h2>

                                    <small class="text-muted">
                                        Appointments
                                    </small>

                                </div>

                                <div class="report-icon
                                            bg-danger-subtle
                                            text-danger">

                                    <i class="bi bi-x-circle-fill"></i>

                                </div>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </div>


        <!-- Appointment date range filter -->
        <div class="card section-card shadow-sm mb-4">

            <div class="card-body p-4">

                <div class="mb-4">

                    <h5 class="report-title">
                        Appointment Date Range Report
                    </h5>

                    <p class="text-muted small mb-0">
                        Select a single date or date range to generate an appointment report.
                    </p>

                </div>


                <form method="get"
                      action="<%= request.getContextPath() %>/reports"
                      class="row g-3 align-items-end no-print">

                    <div class="col-md-4">

                        <label class="form-label">
                            Start Date
                        </label>

                        <input type="date"
                               name="startDate"
                               class="form-control"
                               value="<%= startDate != null ? startDate : "" %>"
                               required>

                    </div>


                    <div class="col-md-4">

                        <label class="form-label">
                            End Date
                        </label>

                        <input type="date"
                               name="endDate"
                               class="form-control"
                               value="<%= endDate != null ? endDate : "" %>"
                               required>

                    </div>


                    <div class="col-md-4">

                        <button type="submit"
                                class="btn btn-primary me-2">

                            <i class="bi bi-search me-1"></i>
                            Generate Report

                        </button>

                        <a href="<%= request.getContextPath() %>/reports"
                           class="btn btn-outline-secondary">

                            Clear

                        </a>

                    </div>

                </form>


                <% if (dateError != null) { %>

                <div class="alert alert-danger mt-4 mb-0">
                    <%= dateError %>
                </div>

                <% } %>

            </div>

        </div>


        <!-- Date range report form -->
        <% if (filteredAppointments != null) { %>

        <div id="appointmentDateReport"
             class="card section-card shadow-sm mb-4">

            <div class="card-body p-4">

                <div class="d-flex
                            justify-content-between
                            align-items-center
                            mb-4">

                    <div>

                        <h5 class="report-title">
                            Appointment Date Range Report
                        </h5>

                        <p class="text-muted small mb-0">

                            <%= startDate %>
                            to
                            <%= endDate %>

                        </p>

                    </div>

                    <button type="button"
                            class="btn btn-outline-primary no-print"
                            onclick="printReport(
                                    'appointmentDateReport',
                                    'Appointment Date Range Report'
                            )">

                        <i class="bi bi-printer-fill me-1"></i>
                        Print Report

                    </button>

                </div>


                <div class="table-responsive">

                    <table class="table table-hover align-middle mb-0">

                        <thead class="table-light">

                        <tr>

                            <th>Appointment No.</th>
                            <th>Patient ID</th>
                            <th>Dentist</th>
                            <th>Treatment</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Status</th>

                        </tr>

                        </thead>


                        <tbody>

                        <%
                            if (!filteredAppointments.isEmpty()) {

                                for (Appointment appointment :
                                        filteredAppointments) {

                                    String badgeClass =
                                            "bg-secondary";

                                    if ("Scheduled".equalsIgnoreCase(
                                            appointment.getStatus())) {

                                        badgeClass =
                                                "bg-warning text-dark";

                                    } else if ("Completed".equalsIgnoreCase(
                                            appointment.getStatus())) {

                                        badgeClass =
                                                "bg-success";

                                    } else if ("Cancelled".equalsIgnoreCase(
                                            appointment.getStatus())) {

                                        badgeClass =
                                                "bg-danger";
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
                                <%= appointment.getTreatmentType() %>
                            </td>

                            <td>
                                <%= appointment.getAppointmentDate() %>
                            </td>

                            <td>
                                <%= appointment.getAppointmentTime() %>
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

                            <td colspan="7"
                                class="text-center
                                       text-muted
                                       py-4">

                                No appointments found for the selected date range.

                            </td>

                        </tr>

                        <% } %>

                        </tbody>

                    </table>

                </div>

            </div>

        </div>

        <% } %>


        <!-- Filtered appointment report -->
        <div id="invoiceReport"
             class="card section-card shadow-sm">

            <div class="card-body p-4">

                <div class="d-flex
                            justify-content-between
                            align-items-center
                            mb-4">

                    <div>

                        <h5 class="report-title">
                            Billing and Invoice Report
                        </h5>

                        <p class="text-muted small mb-0">
                            Generated invoice and payment details.
                        </p>

                    </div>


                    <div class="no-print">

                        <button type="button"
                                class="btn btn-outline-primary me-2"
                                onclick="printReport(
                                        'invoiceReport',
                                        'Billing and Invoice Report'
                                )">

                            <i class="bi bi-printer-fill me-1"></i>
                            Print Report

                        </button>

                        <a href="<%= request.getContextPath() %>/invoices"
                           class="btn btn-outline-secondary">

                            View Invoices

                        </a>

                    </div>

                </div>


                <div class="row g-4 mb-4">

                    <div class="col-md-6 col-xl-4">

                        <div class="card report-card bg-light">

                            <div class="card-body
                                        d-flex
                                        align-items-center
                                        justify-content-between
                                        p-4">

                                <div>

                                    <p class="report-label">
                                        Total Revenue
                                    </p>

                                    <h4 class="fw-bold mb-1">

                                        LKR
                                        <%= String.format(
                                                "%,.2f",
                                                totalRevenue
                                        ) %>

                                    </h4>

                                    <small class="text-muted">
                                        Amount received
                                    </small>

                                </div>

                                <div class="report-icon
                                            bg-success-subtle
                                            text-success">

                                    <i class="bi bi-cash-stack"></i>

                                </div>

                            </div>

                        </div>

                    </div>


                    <div class="col-md-6 col-xl-4">

                        <div class="card report-card bg-light">

                            <div class="card-body
                                        d-flex
                                        align-items-center
                                        justify-content-between
                                        p-4">

                                <div>

                                    <p class="report-label">
                                        Total Invoices
                                    </p>

                                    <h2 class="report-value">

                                        <%= invoices != null
                                                ? invoices.size()
                                                : 0 %>

                                    </h2>

                                    <small class="text-muted">
                                        Generated invoices
                                    </small>

                                </div>

                                <div class="report-icon
                                            bg-primary-subtle
                                            text-primary">

                                    <i class="bi bi-receipt-fill"></i>

                                </div>

                            </div>

                        </div>

                    </div>


                    <div class="col-md-6 col-xl-4">

                        <div class="card report-card bg-light">

                            <div class="card-body
                                        d-flex
                                        align-items-center
                                        justify-content-between
                                        p-4">

                                <div>

                                    <p class="report-label">
                                        Outstanding Balance
                                    </p>

                                    <%
                                        BigDecimal totalOutstanding =
                                                BigDecimal.ZERO;

                                        if (invoices != null) {

                                            for (Invoice invoice : invoices) {

                                                if (invoice.getBalanceAmount()
                                                        != null) {

                                                    totalOutstanding =
                                                            totalOutstanding.add(
                                                                    invoice.getBalanceAmount()
                                                            );
                                                }
                                            }
                                        }
                                    %>

                                    <h4 class="fw-bold mb-1">

                                        LKR
                                        <%= String.format(
                                                "%,.2f",
                                                totalOutstanding
                                        ) %>

                                    </h4>

                                    <small class="text-muted">
                                        Pending balance
                                    </small>

                                </div>

                                <div class="report-icon
                                            bg-warning-subtle
                                            text-warning">

                                    <i class="bi bi-wallet2"></i>

                                </div>

                            </div>

                        </div>

                    </div>

                </div>

                <!-- Billing and invoice report -->
                <div class="table-responsive">

                    <table class="table table-hover align-middle mb-0">

                        <thead class="table-light">

                        <tr>

                            <th>Invoice No.</th>
                            <th>Appointment ID</th>
                            <th>Date</th>
                            <th>Total</th>
                            <th>Paid</th>
                            <th>Balance</th>
                            <th>Payment Method</th>
                            <th>Status</th>

                        </tr>

                        </thead>


                        <tbody>

                        <%
                            if (invoices != null &&
                                    !invoices.isEmpty()) {

                                for (Invoice invoice : invoices) {

                                    String paymentBadge =
                                            "bg-danger";

                                    if ("Paid".equalsIgnoreCase(
                                            invoice.getPaymentStatus())) {

                                        paymentBadge =
                                                "bg-success";

                                    } else if (
                                            "Partially Paid".equalsIgnoreCase(
                                                    invoice.getPaymentStatus())) {

                                        paymentBadge =
                                                "bg-warning text-dark";
                                    }
                        %>

                        <tr>

                            <td class="fw-semibold">
                                <%= invoice.getInvoiceNumber() %>
                            </td>

                            <td>
                                <%= invoice.getAppointmentId() %>
                            </td>

                            <td>

                                <%= invoice.getInvoiceDate() != null
                                        ? invoice.getInvoiceDate().toLocalDate()
                                        : "-" %>

                            </td>

                            <td>

                                LKR
                                <%= String.format(
                                        "%,.2f",
                                        invoice.getTotalAmount()
                                ) %>

                            </td>

                            <td>

                                LKR
                                <%= String.format(
                                        "%,.2f",
                                        invoice.getAmountPaid()
                                ) %>

                            </td>

                            <td>

                                LKR
                                <%= String.format(
                                        "%,.2f",
                                        invoice.getBalanceAmount()
                                ) %>

                            </td>

                            <td>
                                <%= invoice.getPaymentMethod() %>
                            </td>

                            <td>

                                <span class="badge <%= paymentBadge %>">

                                    <%= invoice.getPaymentStatus() %>

                                </span>

                            </td>

                        </tr>

                        <%
                                }

                            } else {
                        %>

                        <tr>

                            <td colspan="8"
                                class="text-center
                                       text-muted
                                       py-4">

                                No invoices found.

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


<script>
    function printReport(sectionId, reportTitle) {

        const section = document.getElementById(sectionId);

        if (!section) {
            return;
        }

        const generatedDate = new Date().toLocaleString();

        const printWindow = window.open(
                '',
                '',
                'width=1100,height=800'
        );

        printWindow.document.write(
                '<html>' +
                '<head>' +
                '<title>' + reportTitle + '</title>' +

                '<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" ' +
                'rel="stylesheet">' +

                '<style>' +

                'body {' +
                'font-family: Arial, sans-serif;' +
                'padding: 30px;' +
                'color: #212529;' +
                '}' +

                '.print-header {' +
                'text-align: center;' +
                'margin-bottom: 30px;' +
                'border-bottom: 2px solid #212529;' +
                'padding-bottom: 15px;' +
                '}' +

                '.print-header h2 {' +
                'margin-bottom: 5px;' +
                'font-weight: 700;' +
                '}' +

                '.print-header h4 {' +
                'margin-bottom: 5px;' +
                '}' +

                '.card {' +
                'border: none !important;' +
                'box-shadow: none !important;' +
                '}' +

                '.report-card {' +
                'border: 1px solid #ddd !important;' +
                '}' +

                '.report-icon {' +
                'display: none !important;' +
                '}' +

                '.no-print {' +
                'display: none !important;' +
                '}' +

                'table {' +
                'width: 100%;' +
                'font-size: 12px;' +
                '}' +

                'th {' +
                'background: #f2f2f2 !important;' +
                'font-weight: 700;' +
                '}' +

                'th, td {' +
                'border: 1px solid #ddd !important;' +
                'padding: 8px !important;' +
                '}' +

                '.badge {' +
                'color: #212529 !important;' +
                'border: 1px solid #999;' +
                '}' +

                '@media print {' +
                'body {' +
                'padding: 0;' +
                '}' +
                '}' +

                '</style>' +
                '</head>' +

                '<body>' +

                '<div class="print-header">' +
                '<h2>Sunrise Dental Clinic</h2>' +
                '<h4>' + reportTitle + '</h4>' +
                '<p>Generated on: ' + generatedDate + '</p>' +
                '</div>' +

                section.innerHTML +

                '</body>' +
                '</html>'
        );

        printWindow.document.close();

        printWindow.onload = function () {

            printWindow.focus();

            setTimeout(function () {

                printWindow.print();
                printWindow.close();

            }, 300);
        };
    }
</script>

</body>

</html>