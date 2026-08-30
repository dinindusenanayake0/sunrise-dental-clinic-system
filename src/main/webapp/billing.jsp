<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.dental.system.model.Appointment" %>
<%@ page import="com.dental.system.model.Patient" %>
<%@ page import="com.dental.system.model.Invoice" %>
<%@ page import="java.math.BigDecimal" %>

<%
    HttpSession currentSession = request.getSession(false);

    if (currentSession == null ||
            currentSession.getAttribute("loggedUser") == null) {

        response.sendRedirect("login.jsp");
        return;
    }

    Appointment appointment =
            (Appointment) request.getAttribute("appointment");

    Patient patient =
            (Patient) request.getAttribute("patient");

    if (appointment == null || patient == null) {
        response.sendRedirect("appointments?invoiceError=missingData");
        return;
    }
%>

<%
    Invoice existingInvoice =
            (Invoice) request.getAttribute("existingInvoice");

    boolean paymentMode = existingInvoice != null;

    BigDecimal treatmentCost =
            (BigDecimal) request.getAttribute("treatmentCost");

    if (treatmentCost == null) {
        treatmentCost = BigDecimal.ZERO;
    }
%>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Create Invoice - Sunrise Dental Clinic</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>

<body class="bg-light">

<!-- Billing section -->
<div class="container py-5">

    <div class="d-flex justify-content-between align-items-center mb-4">

        <div>

            <h2 class="fw-bold mb-1">
                <%= paymentMode ? "Add Payment" : "Create Invoice" %>
            </h2>

            <p class="text-muted mb-0">
                <%= paymentMode
                        ? "Add a payment to the existing invoice."
                        : "Review appointment details and enter payment information." %>
            </p>

        </div>

        <a href="<%= request.getContextPath() %>/appointments"
           class="btn btn-secondary">

            <i class="bi bi-arrow-left me-1"></i>
            Back to Appointments
        </a>

    </div>

    <div class="row g-4">

        <div class="col-lg-5">

            <div class="card border-0 shadow-sm mb-4">

                <div class="card-header bg-primary text-white py-3">

                    <h5 class="mb-0">

                        <i class="bi bi-person-vcard me-2"></i>
                        Patient Details
                    </h5>

                </div>

                <div class="card-body">

                    <div class="mb-3">

                        <small class="text-muted d-block">
                            Patient ID
                        </small>

                        <span class="fw-semibold">
                            P<%= String.format("%04d", patient.getPatientId()) %>
                        </span>

                    </div>

                    <div class="mb-3">

                        <small class="text-muted d-block">
                            Patient Name
                        </small>

                        <span class="fw-semibold">
                            <%= patient.getFirstName() %>
                            <%= patient.getLastName() %>
                        </span>

                    </div>

                    <div class="mb-3">

                        <small class="text-muted d-block">
                            Phone
                        </small>

                        <span class="fw-semibold">
                            <%= patient.getPhone() %>
                        </span>

                    </div>

                    <div class="mb-3">

                        <small class="text-muted d-block">
                            Email
                        </small>

                        <span class="fw-semibold">

                            <%= patient.getEmail() == null ||
                                    patient.getEmail().trim().isEmpty()
                                    ? "-"
                                    : patient.getEmail() %>

                        </span>

                    </div>

                    <div>

                        <small class="text-muted d-block">
                            Address
                        </small>

                        <span class="fw-semibold">

                            <%= patient.getAddress() == null ||
                                    patient.getAddress().trim().isEmpty()
                                    ? "-"
                                    : patient.getAddress() %>

                        </span>

                    </div>

                </div>

            </div>

            <div class="card border-0 shadow-sm">

                <div class="card-header bg-white py-3">

                    <h5 class="mb-0">

                        <i class="bi bi-calendar-event me-2 text-primary"></i>
                        Appointment Details
                    </h5>

                </div>

                <div class="card-body">

                    <div class="row g-3">

                        <div class="col-md-6">

                            <small class="text-muted d-block">
                                Appointment Number
                            </small>

                            <span class="fw-semibold text-primary">
                                <%= appointment.getAppointmentNumber() %>
                            </span>

                        </div>

                        <div class="col-md-6">

                            <small class="text-muted d-block">
                                Status
                            </small>

                            <span class="badge bg-warning text-dark">
                                <%= appointment.getStatus() %>
                            </span>

                        </div>

                        <div class="col-md-6">

                            <small class="text-muted d-block">
                                Date
                            </small>

                            <span class="fw-semibold">
                                <%= appointment.getAppointmentDate() %>
                            </span>

                        </div>

                        <div class="col-md-6">

                            <small class="text-muted d-block">
                                Time
                            </small>

                            <span class="fw-semibold">
                                <%= appointment.getAppointmentTime() %>
                            </span>

                        </div>

                        <div class="col-md-6">

                            <small class="text-muted d-block">
                                Dentist
                            </small>

                            <span class="fw-semibold">
                                <%= appointment.getDentistName() %>
                            </span>

                        </div>

                        <div class="col-md-6">

                            <small class="text-muted d-block">
                                Treatment
                            </small>

                            <span class="fw-semibold">
                                <%= appointment.getTreatmentType() %>
                            </span>

                        </div>

                        <div class="col-12">

                            <small class="text-muted d-block">
                                Notes
                            </small>

                            <div class="bg-light border rounded p-3">

                                <%= appointment.getNotes() == null ||
                                        appointment.getNotes().trim().isEmpty()
                                        ? "No additional notes."
                                        : appointment.getNotes() %>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </div>

        <div class="col-lg-7">

            <div class="card border-0 shadow-sm">

                <div class="card-header bg-primary text-white py-3">

                    <h5 class="mb-0">

                        <i class="bi bi-receipt me-2"></i>
                        Payment Details
                    </h5>

                </div>

                <!-- Invoice payment form -->
                <div class="card-body p-4">

                    <form action="<%= request.getContextPath() %>/invoice"
                          method="post"
                          id="invoiceForm">

                        <input type="hidden"
                               name="appointmentId"
                               value="<%= appointment.getAppointmentId() %>">

                        <div class="row">

                            <div class="col-md-6 mb-3">

                                <label for="treatmentCost"
                                       class="form-label">

                                    Treatment Cost
                                </label>

                                <div class="input-group">

                                    <span class="input-group-text">
                                        LKR
                                    </span>

                                    <input type="number"
                                           class="form-control"
                                           id="treatmentCost"
                                           value="<%= treatmentCost %>"
                                           readonly>

                                </div>

                            </div>

                            <div class="col-md-6 mb-3">

                                <label for="doctorFee"
                                       class="form-label">

                                    Doctor Fee
                                    <span class="text-danger">*</span>
                                </label>

                                <div class="input-group">

                                    <span class="input-group-text">
                                        LKR
                                    </span>

                                    <input type="number"
                                           class="form-control amount-field"
                                           id="doctorFee"
                                           name="doctorFee"
                                           min="0.01"
                                           step="0.01"
                                           value="<%= paymentMode
                                                   ? existingInvoice.getDoctorFee()
                                                   : "" %>"
                                           <%= paymentMode ? "readonly" : "" %>
                                           required>

                                </div>

                            </div>

                            <div class="col-md-6 mb-3">

                                <label for="hospitalFee"
                                       class="form-label">

                                    Hospital Fee
                                    <span class="text-danger">*</span>
                                </label>

                                <div class="input-group">

                                    <span class="input-group-text">
                                        LKR
                                    </span>

                                    <input type="number"
                                           class="form-control amount-field"
                                           id="hospitalFee"
                                           name="hospitalFee"
                                           min="0.01"
                                           step="0.01"
                                           value="<%= paymentMode
                                                   ? existingInvoice.getHospitalFee()
                                                   : "" %>"
                                           <%= paymentMode ? "readonly" : "" %>
                                           required>

                                </div>

                            </div>

                        </div>

                        <div class="row">

                            <div class="col-md-6 mb-3">

                                <label for="additionalFee"
                                       class="form-label">

                                    Additional Fee
                                </label>

                                <div class="input-group">

                                    <span class="input-group-text">
                                        LKR
                                    </span>

                                    <input type="number"
                                           class="form-control amount-field"
                                           id="additionalFee"
                                           name="additionalFee"
                                           min="0"
                                           step="0.01"
                                           value="<%= paymentMode
                                                   ? existingInvoice.getAdditionalFee()
                                                   : "0.00" %>"
                                           <%= paymentMode ? "readonly" : "" %>
                                           >

                                </div>

                            </div>

                            <div class="col-md-6 mb-3">

                                <label for="discount"
                                       class="form-label">

                                    Discount
                                </label>

                                <div class="input-group">

                                    <span class="input-group-text">
                                        LKR
                                    </span>

                                    <input type="number"
                                           class="form-control amount-field"
                                           id="discount"
                                           name="discount"
                                           min="0"
                                           step="0.01"
                                           value="<%= paymentMode
                                                   ? existingInvoice.getDiscount()
                                                   : "0.00" %>"
                                           <%= paymentMode ? "readonly" : "" %>
                                           >

                                </div>

                            </div>

                        </div>

                        <div class="card bg-light border-0 mb-4">

                            <div class="card-body">

                                <div class="d-flex justify-content-between mb-2">

                                    <span class="text-muted">
                                        Subtotal
                                    </span>

                                    <span class="fw-semibold"
                                          id="subtotalDisplay">

                                        LKR 0.00
                                    </span>

                                </div>

                                <div class="d-flex justify-content-between mb-2">

                                    <span class="text-muted">
                                        Discount
                                    </span>

                                    <span class="fw-semibold"
                                          id="discountDisplay">

                                        LKR 0.00
                                    </span>

                                </div>

                                <hr>

                                <div class="d-flex justify-content-between">

                                    <span class="fw-bold fs-5">
                                        Total Amount
                                    </span>

                                    <span class="fw-bold fs-5 text-primary"
                                          id="totalDisplay">

                                        LKR 0.00
                                    </span>

                                </div>

                            </div>

                        </div>

                        <% if (paymentMode) { %>

                        <div class="card bg-light border-0 mb-4">
                            <div class="card-body">

                                <div class="d-flex justify-content-between mb-2">
                                    <span class="text-muted">Invoice Number</span>
                                    <span class="fw-semibold">
                                        <%= existingInvoice.getInvoiceNumber() %>
                                    </span>
                                </div>

                                <div class="d-flex justify-content-between mb-2">
                                    <span class="text-muted">Total Amount</span>
                                    <span class="fw-semibold">
                                        LKR <%= existingInvoice.getTotalAmount() %>
                                    </span>
                                </div>

                                <div class="d-flex justify-content-between mb-2">
                                    <span class="text-muted">Already Paid</span>
                                    <span class="fw-semibold text-success">
                                        LKR <%= existingInvoice.getAmountPaid() %>
                                    </span>
                                </div>

                                <div class="d-flex justify-content-between">
                                    <span class="text-muted">Current Balance</span>
                                    <span class="fw-semibold text-danger">
                                        LKR <%= existingInvoice.getBalanceAmount() %>
                                    </span>
                                </div>

                            </div>
                        </div>

                        <% } %>

                        <div class="row">

                            <div class="col-md-6 mb-3">

                                <label for="amountPaid" class="form-label">
                                    <%= paymentMode ? "New Payment Amount" : "Amount Paid" %>
                                    <span class="text-danger">*</span>
                                </label>

                                <div class="input-group">

                                    <span class="input-group-text">
                                        LKR
                                    </span>

                                    <input type="number"
                                           class="form-control"
                                           id="amountPaid"
                                           name="amountPaid"
                                           min="0"
                                           step="0.01"
                                           value="0.00"
                                           required>

                                </div>

                            </div>

                            <div class="col-md-6 mb-3">

                                <label for="paymentMethod"
                                       class="form-label">

                                    Payment Method
                                    <span class="text-danger">*</span>
                                </label>

                                <select class="form-select"
                                        id="paymentMethod"
                                        name="paymentMethod"
                                        required>

                                    <option value="">
                                        Select payment method
                                    </option>

                                    <option value="Cash">
                                        Cash
                                    </option>

                                    <option value="Card" disabled>
                                        Card
                                    </option>

                                    <option value="Bank Transfer" disabled>
                                        Bank Transfer
                                    </option>

                                </select>

                            </div>

                        </div>

                        <div class="card bg-light border-0 mb-4">

                            <div class="card-body">

                                <div class="d-flex justify-content-between">

                                    <span class="fw-semibold">
                                        Balance Amount
                                    </span>

                                    <span class="fw-bold text-danger"
                                          id="balanceDisplay">

                                        LKR 0.00
                                    </span>

                                </div>

                            </div>

                        </div>

                        <div class="mb-4">

                            <label for="remarks"
                                   class="form-label">

                                Remarks
                            </label>

                            <textarea class="form-control"
                                      id="remarks"
                                      name="remarks"
                                      rows="3"
                                      placeholder="Enter payment or invoice notes"></textarea>

                        </div>

                        <div class="d-flex justify-content-end gap-2">

                            <a href="<%= request.getContextPath() %>/appointments"
                               class="btn btn-outline-secondary">

                                Cancel
                            </a>

                            <button type="submit"
                                    class="btn btn-primary">

                                <i class="bi <%= paymentMode
                                        ? "bi-cash-coin"
                                        : "bi-receipt-cutoff" %> me-1"></i>

                                <%= paymentMode
                                        ? "Add Payment"
                                        : "Generate Invoice" %>
                            </button>

                        </div>

                    </form>

                </div>

            </div>

        </div>

    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    const treatmentCost = document.getElementById("treatmentCost");
    const doctorFee = document.getElementById("doctorFee");
    const hospitalFee = document.getElementById("hospitalFee");
    const additionalFee = document.getElementById("additionalFee");
    const discount = document.getElementById("discount");
    const amountPaid = document.getElementById("amountPaid");

    function getNumber(element) {
        const value = parseFloat(element.value);
        return Number.isNaN(value) ? 0 : value;
    }

    function formatMoney(value) {
        return "LKR " + value.toFixed(2);
    }

    function calculateInvoicePreview() {

        const subtotal =
            getNumber(treatmentCost)
            + getNumber(doctorFee)
            + getNumber(hospitalFee)
            + getNumber(additionalFee);

        const discountValue = getNumber(discount);

        const total = Math.max(
            subtotal - discountValue,
            0
        );

        const paid = getNumber(amountPaid);

        const previousPaid =
            <%= paymentMode
                    ? existingInvoice.getAmountPaid()
                    : "0" %>;

        const balance = Math.max(
            total - previousPaid - paid,
            0
        );

        document.getElementById("subtotalDisplay").textContent =
            formatMoney(subtotal);

        document.getElementById("discountDisplay").textContent =
            formatMoney(discountValue);

        document.getElementById("totalDisplay").textContent =
            formatMoney(total);

        document.getElementById("balanceDisplay").textContent =
            formatMoney(balance);
    }

    document.querySelectorAll(
        ".amount-field, #amountPaid"
    ).forEach(function (field) {

        field.addEventListener(
            "input",
            calculateInvoicePreview
        );
    });

    calculateInvoicePreview();
</script>

<% if ("exceeded".equals(
        request.getParameter("paymentError"))) { %>

<script>
    Swal.fire({
        icon: 'error',
        title: 'Invalid Payment Amount',
        text: 'The payment amount cannot be greater than the remaining balance.',
        confirmButtonColor: '#0d6efd'
    });
</script>

<% } %>

<% if ("true".equals(
        request.getParameter("paymentError"))) { %>

<script>
    Swal.fire({
        icon: 'error',
        title: 'Payment Failed',
        text: 'The payment could not be updated. Please try again.',
        confirmButtonColor: '#0d6efd'
    });
</script>

<% } %>

<% if ("true".equals(request.getParameter("error")) ||
        request.getParameter("error") != null) { %>

<script>
    Swal.fire({
        icon: 'error',
        title: 'Invoice Creation Failed',
        text: 'Please check the entered payment details and try again.',
        confirmButtonColor: '#0d6efd'
    });
</script>
<% } %>

<script>
    const invoiceForm = document.getElementById("invoiceForm");

    invoiceForm.addEventListener("submit", function (event) {

        if (!invoiceForm.checkValidity()) {
            return;
        }

        event.preventDefault();

        const invoiceTab =
            window.open("", "invoiceTab");

        invoiceForm.target = "invoiceTab";
        invoiceForm.submit();

        setTimeout(function () {
            window.location.href =
                "<%= request.getContextPath() %>/appointments";
        }, 500);
    });
</script>
</body>
</html>