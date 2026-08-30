<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.dental.system.model.Invoice" %>
<%@ page import="com.dental.system.model.Appointment" %>
<%@ page import="com.dental.system.model.Patient" %>

<%!
    private String safeValue(Object value) {
        if (value == null) {
            return "Not available";
        }

        return String.valueOf(value)
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
%>

<%
    Invoice invoice =
            (Invoice) request.getAttribute("invoice");

    Appointment appointment =
            (Appointment) request.getAttribute("appointment");

    Patient patient =
            (Patient) request.getAttribute("patient");

    if (invoice == null ||
        appointment == null ||
        patient == null) {

        response.sendRedirect(
                request.getContextPath()
                        + "/appointments"
        );

        return;
    }

    DecimalFormat currency =
            new DecimalFormat("#,##0.00");

    String source = request.getParameter("source");
    boolean fromInvoices = "invoices".equalsIgnoreCase(source);

    String paymentStatus =
            invoice.getPaymentStatus() == null
                    ? "Pending"
                    : invoice.getPaymentStatus().trim();

    String badgeClass;

    switch (paymentStatus.toLowerCase()) {

        case "paid":
            badgeClass = "status-paid";
            break;

        case "partial":
        case "partially paid":
            badgeClass = "status-partial";
            break;

        case "cancelled":
        case "canceled":
            badgeClass = "status-cancelled";
            break;

        case "pending":
        case "unpaid":
        default:
            badgeClass = "status-pending";
            break;
    }

    String invoiceDate =
            invoice.getInvoiceDate() == null
                    ? "Not available"
                    : String.valueOf(invoice.getInvoiceDate());

    String firstName =
            patient.getFirstName() == null
                    ? ""
                    : patient.getFirstName().trim();

    String lastName =
            patient.getLastName() == null
                    ? ""
                    : patient.getLastName().trim();

    String patientName =
            (firstName + " " + lastName).trim();

    if (patientName.isEmpty()) {
        patientName = "Not available";
    }

    String remarks =
            invoice.getRemarks();

    if (remarks == null ||
        remarks.trim().isEmpty()) {

        remarks = "No remarks available.";
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Invoice - <%= safeValue(invoice.getInvoiceNumber()) %>
    </title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
          rel="stylesheet">

    <style>

        * {
            box-sizing: border-box;
        }

        @page {
            size: A4 portrait;
            margin: 8mm;
        }

        body {
            margin: 0;
            background: #eeeeee;
            color: #111111;
            font-family: Arial, Helvetica, sans-serif;
            font-size: 12px;
        }

        .page-container {
            width: 100%;
            max-width: 794px;
            margin: 0 auto;
            padding: 24px 14px;
        }

        .page-title-row {
            margin-bottom: 12px;
        }

        .page-title {
            margin: 0;
            font-size: 18px;
            font-weight: 700;
            color: #111111;
        }

        .page-description {
            margin: 3px 0 0;
            color: #555555;
            font-size: 12px;
        }

        .invoice-card {
            width: 100%;
            background: #ffffff;
            border: 1px solid #bdbdbd;
            overflow: hidden;
            box-shadow: 0 5px 18px rgba(0, 0, 0, 0.08);
        }

        .invoice-header {
            width: 100%;
            padding: 18px 22px;
            background: #3385FF;
            color: #ffffff;
        }

        .invoice-header-grid {
            display: grid;
            grid-template-columns: 65% 35%;
            align-items: center;
            width: 100%;
        }

        .clinic-section {
            min-width: 0;
            padding-right: 20px;
        }

        .invoice-meta-section {
            min-width: 0;
            text-align: right;
        }

        .clinic-name {
            margin: 0 0 8px;
            font-size: 21px;
            font-weight: 700;
        }

        .clinic-name i {
            margin-right: 6px;
        }

        .clinic-detail {
            margin: 2px 0;
            color: #e2e2e2;
            font-size: 11px;
        }

        .clinic-detail i {
            display: inline-block;
            width: 15px;
            margin-right: 4px;
        }

        .invoice-heading {
            margin: 0;
            font-size: 24px;
            font-weight: 700;
            letter-spacing: 1px;
        }

        .invoice-number {
            margin: 4px 0 8px;
            font-size: 12px;
        }

        .invoice-date {
            margin: 0 0 8px;
            font-size: 11px;
        }

        .status-badge {
            display: inline-block;
            padding: 4px 10px;
            border: 1px solid #cccccc;
            border-radius: 15px;
            background: #ffffff;
            color: #111111;
            font-size: 10px;
            font-weight: 700;
        }

        .status-paid {
            background: #ffffff;
        }

        .status-partial {
            background: #eeeeee;
        }

        .status-pending {
            background: #dddddd;
        }

        .status-cancelled {
            background: #cccccc;
        }

        .invoice-body {
            padding: 16px 20px;
        }

        .information-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 12px;
        }

        .information-card {
            height: 100%;
            border: 1px solid #cccccc;
            background: #ffffff;
            overflow: hidden;
        }

        .information-header {
            padding: 8px 11px;
            background: #eeeeee;
            border-bottom: 1px solid #cccccc;
        }

        .information-header h2 {
            display: flex;
            align-items: center;
            gap: 6px;
            margin: 0;
            color: #111111;
            font-size: 12px;
            font-weight: 700;
        }

        .information-body {
            padding: 6px 10px;
        }

        .details-table {
            width: 100%;
            margin: 0;
            border-collapse: collapse;
        }

        .details-table th,
        .details-table td {
            padding: 5px 3px;
            vertical-align: top;
            border-bottom: 1px solid #e2e2e2;
            font-size: 10.5px;
        }

        .details-table tr:last-child th,
        .details-table tr:last-child td {
            border-bottom: 0;
        }

        .details-table th {
            width: 40%;
            color: #555555;
            font-weight: 600;
        }

        .details-table td {
            color: #111111;
            font-weight: 500;
            overflow-wrap: anywhere;
        }

        .payment-section {
            margin-top: 12px;
            border: 1px solid #cccccc;
            overflow: hidden;
        }

        .payment-header {
            padding: 8px 11px;
            background: #3385FF;
            color: #ffffff;
        }

        .payment-header h2 {
            display: flex;
            align-items: center;
            gap: 6px;
            margin: 0;
            font-size: 12px;
            font-weight: 700;
        }

        .payment-table {
            width: 100%;
            margin: 0;
            border-collapse: collapse;
        }

        .payment-table td {
            padding: 6px 11px;
            border-bottom: 1px solid #e2e2e2;
            font-size: 10.5px;
        }

        .payment-table tr:last-child td {
            border-bottom: 0;
        }

        .payment-table td:last-child {
            text-align: right;
            font-weight: 600;
        }

        .payment-label {
            color: #555555;
        }

        .total-row td {
            padding-top: 7px;
            padding-bottom: 7px;
            background: #eeeeee;
            color: #003D99;
            font-size: 11.5px;
            font-weight: 700;
        }

        .amount-paid,
        .balance-amount,
        .discount-amount {
            color: #111111;
        }

        .remarks-section {
            margin-top: 12px;
            padding: 9px 11px;
            border: 1px solid #cccccc;
            background: #f7f7f7;
        }

        .remarks-title {
            display: flex;
            align-items: center;
            gap: 6px;
            margin: 0 0 5px;
            font-size: 11.5px;
            font-weight: 700;
        }

        .remarks-text {
            margin: 0;
            color: #444444;
            font-size: 10.5px;
            line-height: 1.35;
            white-space: pre-wrap;
            overflow-wrap: anywhere;
        }

        .thank-you-section {
            padding: 12px 8px 2px;
            text-align: center;
        }

        .thank-you-section h2 {
            margin: 0 0 4px;
            color: #111111;
            font-size: 12px;
            font-weight: 700;
        }

        .thank-you-section p {
            max-width: 600px;
            margin: 0 auto;
            color: #555555;
            font-size: 10px;
            line-height: 1.35;
        }

        .action-row {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 10px;
            margin-top: 12px;
        }

        .action-row .btn {
            min-height: 34px;
            padding: 6px 12px;
            border-radius: 4px;
            font-size: 11px;
            font-weight: 600;
            display: flex;
            align-items: center;
        }

        .btn-print {
            background: #3385FF;
            border-color: #3385FF;
            color: #ffffff;
        }

        .btn-print:hover {
            background: #002966;
            border-color: #000000;
            color: #ffffff;
        }

        @media screen and (max-width: 767.98px) {

            .page-container {
                padding: 12px 8px;
            }

            .invoice-header {
                padding: 16px;
            }

            .invoice-header-grid {
                grid-template-columns: 1fr;
                gap: 16px;
            }

            .clinic-section {
                padding-right: 0;
            }

            .invoice-meta-section {
                text-align: left;
            }

            .invoice-body {
                padding: 12px;
            }

            .information-grid {
                grid-template-columns: 1fr;
            }

            .action-row {
                flex-direction: column-reverse;
                align-items: stretch;
            }

            .action-row .btn {
                width: 100%;
            }
        }

        @media print {

            html,
            body {
                width: 210mm;
                margin: 0;
                padding: 0;
                background: #ffffff;
            }

            .no-print {
                display: none !important;
            }

            .page-container {
                width: 100%;
                max-width: 794px;
                margin: 0 auto;
                padding: 24px 14px;
            }

            .invoice-card {
                width: 100%;
                background: #ffffff;
                border: 1px solid #bdbdbd;
                box-shadow: none;
            }

            .invoice-header {
                width: 100% !important;
                padding: 18px 22px !important;
                background: #3385FF !important;
                color: #ffffff !important;
                print-color-adjust: exact;
                -webkit-print-color-adjust: exact;
            }

            .invoice-header-grid {
                display: grid !important;
                grid-template-columns: 65% 35% !important;
                align-items: center !important;
                width: 100% !important;
            }

            .clinic-section {
                min-width: 0 !important;
                padding-right: 20px !important;
            }

            .invoice-meta-section {
                min-width: 0 !important;
                text-align: right !important;
            }

            .information-grid {
                display: grid !important;
                grid-template-columns: 1fr 1fr !important;
                gap: 12px !important;
            }

            .payment-header {
                background: #3385FF !important;
                color: #ffffff !important;
            }

            .total-row td {
                background: #eeeeee !important;
                color: #003D99 !important;
            }

            .invoice-header,
            .payment-header,
            .total-row td,
            .status-badge {
                print-color-adjust: exact;
                -webkit-print-color-adjust: exact;
            }

            .information-card,
            .payment-section,
            .remarks-section,
            .thank-you-section {
                break-inside: avoid;
                page-break-inside: avoid;
            }
        }

    </style>

</head>

<body>

<div class="page-container">

    <div class="page-title-row no-print">

        <h1 class="page-title">
            Invoice Details
        </h1>

        <p class="page-description">
            Patient, appointment and payment information.
        </p>

    </div>

    <article class="invoice-card">

        <header class="invoice-header">

            <div class="invoice-header-grid">

                <div class="clinic-section">

                    <h1 class="clinic-name">

                        <i class="bi bi-heart-pulse-fill"></i>

                        Sunrise Dental Clinic

                    </h1>

                    <p class="clinic-detail">

                        <i class="bi bi-geo-alt-fill"></i>

                        123 Main Street, Gampaha

                    </p>

                    <p class="clinic-detail">

                        <i class="bi bi-telephone-fill"></i>

                        +94 77 123 4567

                    </p>

                    <p class="clinic-detail">

                        <i class="bi bi-envelope-fill"></i>

                        info@sunrisedental.com

                    </p>

                </div>

                <div class="invoice-meta-section">

                    <h2 class="invoice-heading">
                        INVOICE
                    </h2>

                    <p class="invoice-number">
                        <%= safeValue(invoice.getInvoiceNumber()) %>
                    </p>

                    <p class="invoice-date">

                        <strong>Date:</strong>

                        <%= safeValue(invoiceDate) %>

                    </p>

                    <span class="status-badge <%= badgeClass %>">
                        <%= safeValue(paymentStatus) %>
                    </span>

                </div>

            </div>

        </header>

        <div class="invoice-body">

            <div class="information-grid">

                <section class="information-card">

                    <div class="information-header">

                        <h2>

                            <i class="bi bi-person-fill"></i>

                            Patient Details

                        </h2>

                    </div>

                    <div class="information-body">

                        <table class="details-table">

                            <tbody>

                            <tr>

                                <th>Patient ID</th>

                                <td>
                                    <%= safeValue(patient.getPatientId()) %>
                                </td>

                            </tr>

                            <tr>

                                <th>Name</th>

                                <td>
                                    <%= safeValue(patientName) %>
                                </td>

                            </tr>

                            <tr>

                                <th>Phone</th>

                                <td>
                                    <%= safeValue(patient.getPhone()) %>
                                </td>

                            </tr>

                            <tr>

                                <th>Email</th>

                                <td>
                                    <%= safeValue(patient.getEmail()) %>
                                </td>

                            </tr>

                            </tbody>

                        </table>

                    </div>

                </section>

                <section class="information-card">

                    <div class="information-header">

                        <h2>

                            <i class="bi bi-calendar-check-fill"></i>

                            Appointment Details

                        </h2>

                    </div>

                    <div class="information-body">

                        <table class="details-table">

                            <tbody>

                            <tr>

                                <th>Appointment No.</th>

                                <td>
                                    <%= safeValue(appointment.getAppointmentNumber()) %>
                                </td>

                            </tr>

                            <tr>

                                <th>Date</th>

                                <td>
                                    <%= safeValue(appointment.getAppointmentDate()) %>
                                </td>

                            </tr>

                            <tr>

                                <th>Time</th>

                                <td>
                                    <%= safeValue(appointment.getAppointmentTime()) %>
                                </td>

                            </tr>

                            <tr>

                                <th>Dentist</th>

                                <td>
                                    <%= safeValue(appointment.getDentistName()) %>
                                </td>

                            </tr>

                            <tr>

                                <th>Treatment</th>

                                <td>
                                    <%= safeValue(appointment.getTreatmentType()) %>
                                </td>

                            </tr>

                            </tbody>

                        </table>

                    </div>

                </section>

            </div>

            <section class="payment-section">

                <div class="payment-header">

                    <h2>

                        <i class="bi bi-receipt-cutoff"></i>

                        Payment Summary

                    </h2>

                </div>

                <table class="payment-table">

                    <tbody>

                    <tr>
                        <td>Treatment Cost</td>
                        <td class="text-end">
                            LKR <%= currency.format(invoice.getTreatmentCost()) %>
                        </td>
                    </tr>

                    <tr>

                        <td class="payment-label">
                            Doctor Fee
                        </td>

                        <td>
                            LKR
                            <%= currency.format(invoice.getDoctorFee()) %>
                        </td>

                    </tr>

                    <tr>

                        <td class="payment-label">
                            Hospital Fee
                        </td>

                        <td>
                            LKR
                            <%= currency.format(invoice.getHospitalFee()) %>
                        </td>

                    </tr>

                    <tr>

                        <td class="payment-label">
                            Additional Fee
                        </td>

                        <td>
                            LKR
                            <%= currency.format(invoice.getAdditionalFee()) %>
                        </td>

                    </tr>

                    <tr>

                        <td class="payment-label">
                            Discount
                        </td>

                        <td class="discount-amount">

                            - LKR
                            <%= currency.format(invoice.getDiscount()) %>

                        </td>

                    </tr>

                    <tr class="total-row">

                        <td>
                            Total Amount
                        </td>

                        <td>

                            LKR
                            <%= currency.format(invoice.getTotalAmount()) %>

                        </td>

                    </tr>

                    <tr>

                        <td class="payment-label">
                            Amount Paid
                        </td>

                        <td class="amount-paid">

                            LKR
                            <%= currency.format(invoice.getAmountPaid()) %>

                        </td>

                    </tr>

                    <tr>

                        <td class="payment-label">
                            Balance Amount
                        </td>

                        <td class="balance-amount">

                            LKR
                            <%= currency.format(invoice.getBalanceAmount()) %>

                        </td>

                    </tr>

                    <tr>

                        <td class="payment-label">
                            Payment Method
                        </td>

                        <td>
                            <%= safeValue(invoice.getPaymentMethod()) %>
                        </td>

                    </tr>

                    </tbody>

                </table>

            </section>

            <section class="remarks-section">

                <h2 class="remarks-title">

                    <i class="bi bi-chat-left-text-fill"></i>

                    Remarks

                </h2>

                <p class="remarks-text">
                    <%= safeValue(remarks) %>
                </p>

            </section>

            <section class="thank-you-section">

                <h2>
                    Thank You for Choosing Sunrise Dental Clinic
                </h2>

                <p>
                    We appreciate your trust in our dental services.
                    We wish you excellent oral health and look forward
                    to serving you again.
                </p>

            </section>

            <div class="d-flex justify-content-between align-items-center no-print">

                <button type="button"
                        class="btn btn-primary"
                        onclick="window.print()">

                    <i class="bi bi-printer-fill"></i>
                    Print Invoice

                </button>

            </div>

        </div>

    </article>

</div>

</body>

</html>