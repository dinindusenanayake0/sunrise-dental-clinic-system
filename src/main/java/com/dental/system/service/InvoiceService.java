package com.dental.system.service;

import com.dental.system.dao.InInvoiceDAO;
import com.dental.system.model.Invoice;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceService implements InInvoiceService {

    private final InInvoiceDAO invoiceDAO;

    public InvoiceService(InInvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    // Add invoice
    @Override
    public boolean addInvoice(Invoice invoice) {
        if (invoice == null) {
            System.out.println("Invoice creation failed : invoice is null");
            return false;
        }

        if (invoice.getAppointmentId() <= 0) {
            System.out.println("Invoice creation failed : invalid appointment number");
            return false;
        }

        BigDecimal treatmentCost = getSafeAmount(invoice.getTreatmentCost());
        BigDecimal doctorFee = getSafeAmount(invoice.getDoctorFee());
        BigDecimal hospitalFee = getSafeAmount(invoice.getHospitalFee());
        BigDecimal additionalFee = getSafeAmount(invoice.getAdditionalFee());
        BigDecimal discount = getSafeAmount(invoice.getDiscount());
        BigDecimal amountPaid = getSafeAmount(invoice.getAmountPaid());

        if (treatmentCost.compareTo(BigDecimal.ZERO) < 0 ||
                doctorFee.compareTo(BigDecimal.ZERO) < 0 ||
                hospitalFee.compareTo(BigDecimal.ZERO) < 0 ||
                additionalFee.compareTo(BigDecimal.ZERO) < 0 ||
                discount.compareTo(BigDecimal.ZERO) < 0 ||
                amountPaid.compareTo(BigDecimal.ZERO) < 0) {

            System.out.println("Invoice validation failed: negative amounts are not allowed.");
            return false;
        }

        BigDecimal subtotal = treatmentCost.add(doctorFee).add(hospitalFee).add(additionalFee);

        if (subtotal.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        if (discount.compareTo(subtotal) > 0) {
            System.out.println("Discount exceeds subtotal.");
            return false;
        }

        BigDecimal totalAmount = subtotal.subtract(discount);

        if (amountPaid.compareTo(totalAmount) > 0) {
            System.out.println("Amount paid exceeds total amount.");
            return false;
        }

        BigDecimal balanceAmount = totalAmount.subtract(amountPaid);
        String invoiceNumber = invoiceDAO.generateNextInvoiceNumber();

        if (invoiceNumber == null || invoiceNumber.trim().isEmpty()) {
            System.out.println("Invoice number generation failed.");
            return false;
        }

        String paymentStatus = determinePaymentStatus(totalAmount, amountPaid);

        if (!isValidPaymentMethod(invoice.getPaymentMethod())) {
            System.out.println("Invalid payment method.");
            return false;
        }

        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setTreatmentCost(treatmentCost);
        invoice.setDoctorFee(doctorFee);
        invoice.setHospitalFee(hospitalFee);
        invoice.setAdditionalFee(additionalFee);
        invoice.setDiscount(discount);
        invoice.setTotalAmount(totalAmount);
        invoice.setAmountPaid(amountPaid);
        invoice.setBalanceAmount(balanceAmount);
        invoice.setPaymentStatus(paymentStatus);

        return invoiceDAO.addInvoice(invoice);
    }

    // Calculate treatment cost
    public BigDecimal calculateTreatmentCost(String treatmentType) {

        if (treatmentType == null ||
                treatmentType.trim().isEmpty()) {

            return BigDecimal.ZERO;
        }

        BigDecimal total =
                BigDecimal.ZERO;

        String[] treatments =
                treatmentType.split(",");

        for (String treatment : treatments) {

            String name =
                    treatment.trim();

            switch (name) {

                case "Dental Consultation":
                    total = total.add(
                            new BigDecimal("1500.00")
                    );
                    break;

                case "Dental Cleaning":
                    total = total.add(
                            new BigDecimal("2500.00")
                    );
                    break;

                case "Tooth Filling":
                    total = total.add(
                            new BigDecimal("3000.00")
                    );
                    break;

                case "Tooth Extraction":
                    total = total.add(
                            new BigDecimal("4000.00")
                    );
                    break;

                case "Root Canal Treatment":
                    total = total.add(
                            new BigDecimal("12000.00")
                    );
                    break;

                case "Teeth Whitening":
                    total = total.add(
                            new BigDecimal("8000.00")
                    );
                    break;

                case "Dental Checkup":
                    total = total.add(
                            new BigDecimal("1000.00")
                    );
                    break;
            }
        }

        return total;
    }


    // Convert null amount to zero
    private BigDecimal getSafeAmount(BigDecimal amount) {
        if (amount == null) {
            return BigDecimal.ZERO;
        }
        return amount;
    }

    // Determine invoice payment status
    private String determinePaymentStatus(BigDecimal totalAmount, BigDecimal amountPaid) {
        if (amountPaid.compareTo(BigDecimal.ZERO) == 0) {
            return "Pending";
        }
        if (amountPaid.compareTo(totalAmount) < 0) {
            return "Partially Paid";
        }
        return "Paid";
    }

    // Get invoice by appointment ID
    @Override
    public Invoice getInvoiceByAppointmentId(int appointmentId) {
        if (appointmentId <= 0) {
            return null;
        }
        return invoiceDAO.getInvoiceByAppointmentId(appointmentId);
    }


    // Update invoice
    @Override
    public boolean updateInvoice(Invoice invoice) {

        if (invoice == null || invoice.getInvoiceId() <= 0) {
            return false;
        }

        BigDecimal totalAmount =
                getSafeAmount(invoice.getTotalAmount());

        BigDecimal amountPaid =
                getSafeAmount(invoice.getAmountPaid());

        if (amountPaid.compareTo(BigDecimal.ZERO) < 0 ||
                amountPaid.compareTo(totalAmount) > 0) {

            return false;
        }

        if (!isValidPaymentMethod(invoice.getPaymentMethod())) {
            return false;
        }

        BigDecimal balanceAmount =
                totalAmount.subtract(amountPaid);

        invoice.setAmountPaid(amountPaid);
        invoice.setBalanceAmount(balanceAmount);

        invoice.setPaymentStatus(
                determinePaymentStatus(
                        totalAmount,
                        amountPaid
                )
        );

        return invoiceDAO.updateInvoice(invoice);
    }


    // Get all invoices
    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceDAO.getAllInvoices();
    }


    // Validate payment method
    private boolean isValidPaymentMethod(String paymentMethod) {

        if (paymentMethod == null) {
            return false;
        }

        return "Cash".equalsIgnoreCase(paymentMethod.trim())
                || "Card".equalsIgnoreCase(paymentMethod.trim())
                || "Bank Transfer".equalsIgnoreCase(paymentMethod.trim());
    }



}
