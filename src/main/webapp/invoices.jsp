<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.dental.system.model.Invoice" %>

<%
    HttpSession currentSession = request.getSession(false);

    if (currentSession == null ||
            currentSession.getAttribute("loggedUser") == null) {

        response.sendRedirect(
                request.getContextPath() + "/login.jsp"
        );
        return;
    }

    List<Invoice> invoices =
            (List<Invoice>) request.getAttribute("invoices");

    DecimalFormat currency =
            new DecimalFormat("#,##0.00");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Invoices - Sunrise Dental Clinic</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
          rel="stylesheet">

</head>

<body class="bg-light">

<jsp:include page="/components/sidebar.jsp"/>

<div class="main-content">

    <div class="container-fluid py-4 px-4">

        <div class="d-flex justify-content-between
                    align-items-center flex-wrap gap-3 mb-4">

            <div>

                <h2 class="fw-bold mb-1">
                    Billing and Invoices
                </h2>

                <p class="text-muted mb-0">
                    View and print generated patient invoices.
                </p>

            </div>
        </div>


        <div class="row mb-4">

            <div class="col-md-4">

                <div class="card border-0 shadow-sm">

                    <div class="card-body d-flex
                                justify-content-between
                                align-items-center">

                        <div>

                            <p class="text-muted mb-1">
                                Total Invoices
                            </p>

                            <h3 class="fw-bold mb-0">
                                <%= invoices == null
                                        ? 0
                                        : invoices.size() %>
                            </h3>

                        </div>

                        <div class="bg-primary bg-opacity-10
                                    text-primary rounded-circle
                                    p-3">

                            <i class="bi bi-receipt-cutoff fs-3"></i>

                        </div>

                    </div>

                </div>

            </div>

        </div>


        <div class="card border-0 shadow-sm">

            <div class="card-header bg-white py-3">

                <div class="d-flex justify-content-between
                            align-items-center flex-wrap gap-3">

                    <h5 class="mb-0">

                        <i class="bi bi-list-ul me-2 text-primary"></i>
                        Generated Invoices

                    </h5>

                    <div class="input-group"
                         style="max-width: 360px;">

                        <span class="input-group-text">

                            <i class="bi bi-search"></i>

                        </span>

                        <input type="text"
                               id="invoiceSearch"
                               class="form-control"
                               placeholder="Search by invoice number">

                    </div>

                </div>

            </div>

            <div class="card-body">

                <div class="table-responsive">

                    <table class="table table-hover align-middle"
                           id="invoiceTable">

                        <thead class="table-primary">

                        <tr>
                            <th>Invoice No.</th>
                            <th>Appointment ID</th>
                            <th>Invoice Date</th>
                            <th>Total</th>
                            <th>Paid</th>
                            <th>Balance</th>
                            <th>Method</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>

                        </thead>

                        <tbody>

                        <% if (invoices != null &&
                                !invoices.isEmpty()) { %>

                        <% for (Invoice invoice : invoices) { %>

                        <tr>

                            <td class="fw-semibold text-primary">
                                <%= invoice.getInvoiceNumber() %>
                            </td>

                            <td>
                                <%= invoice.getAppointmentId() %>
                            </td>

                            <td>

                                <%= invoice.getInvoiceDate() != null
                                        ? invoice.getInvoiceDate()
                                            .toLocalDate()
                                        : "-" %>

                            </td>

                            <td class="fw-semibold">

                                LKR
                                <%= currency.format(
                                        invoice.getTotalAmount()
                                ) %>

                            </td>

                            <td class="text-success fw-semibold">

                                LKR
                                <%= currency.format(
                                        invoice.getAmountPaid()
                                ) %>

                            </td>

                            <td class="text-danger fw-semibold">

                                LKR
                                <%= currency.format(
                                        invoice.getBalanceAmount()
                                ) %>

                            </td>

                            <td>
                                <%= invoice.getPaymentMethod() %>
                            </td>

                            <td>

                                <% if ("Paid".equalsIgnoreCase(
                                        invoice.getPaymentStatus())) { %>

                                <span class="badge bg-success">
                                    Paid
                                </span>

                                <% } else if (
                                        "Partially Paid".equalsIgnoreCase(
                                                invoice.getPaymentStatus()
                                        )) { %>

                                <span class="badge bg-warning text-dark">
                                    Partially Paid
                                </span>

                                <% } else { %>

                                <span class="badge bg-danger">
                                    Pending
                                </span>

                                <% } %>

                            </td>

                            <td>

                                <a href="<%= request.getContextPath() %>/invoice/view?appointmentId=<%= invoice.getAppointmentId() %>&source=invoices"
                                   class="btn btn-sm btn-outline-primary"
                                   title="View and Print Invoice"
                                   target="_blank"
                                   rel="noopener noreferrer">

                                    <i class="bi bi-eye"></i>

                                </a>

                            </td>

                        </tr>

                        <% } %>

                        <% } else { %>

                        <tr>

                            <td colspan="9"
                                class="text-center text-muted py-5">

                                <i class="bi bi-receipt fs-1
                                          d-block mb-2"></i>

                                No invoices have been generated yet.

                            </td>

                        </tr>

                        <% } %>

                        </tbody>

                    </table>

                </div>

                <div id="noInvoiceResults"
                     class="alert alert-warning
                            text-center d-none">

                    No matching invoices found.

                </div>

            </div>

        </div>

    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

<script>
    const invoiceSearch =
        document.getElementById("invoiceSearch");

    const invoiceTable =
        document.getElementById("invoiceTable");

    const invoiceRows =
        invoiceTable.querySelectorAll("tbody tr");

    const noInvoiceResults =
        document.getElementById("noInvoiceResults");

    invoiceSearch.addEventListener("keyup", function () {

        const searchValue =
            invoiceSearch.value.toLowerCase().trim();

        let visibleCount = 0;

        invoiceRows.forEach(function (row) {

            const invoiceNumberCell =
                row.querySelector("td:first-child");

            if (!invoiceNumberCell) {
                return;
            }

            const invoiceNumber =
                invoiceNumberCell.textContent
                    .toLowerCase()
                    .trim();

            const visible =
                invoiceNumber.includes(searchValue);

            row.style.display =
                visible ? "" : "none";

            if (visible) {
                visibleCount++;
            }
        });

        if (visibleCount === 0 &&
                searchValue !== "") {

            noInvoiceResults.classList.remove("d-none");

        } else {

            noInvoiceResults.classList.add("d-none");
        }
    });
</script>

</body>
</html>