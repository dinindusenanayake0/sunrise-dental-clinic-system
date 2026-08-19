package com.dental.system.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Invoice {

    private int invoiceId;
    private String invoiceNumber;
    private int appointmentId;

    private BigDecimal doctorFee;
    private BigDecimal hospitalFee;
    private BigDecimal additionalFee;
    private BigDecimal discount;

    private BigDecimal totalAmount;
    private BigDecimal amountPaid;
    private BigDecimal balanceAmount;

    private String paymentMethod;
    private String paymentStatus;

    private LocalDateTime invoiceDate;
    private String remarks;


    //Create default constructor
    public Invoice() {

    }

    //Create constructor
    public Invoice(
            int invoiceId,
            String invoiceNumber,
            int appointmentId,
            BigDecimal doctorFee,
            BigDecimal hospitalFee,
            BigDecimal additionalFee,
            BigDecimal discount,
            BigDecimal totalAmount,
            BigDecimal amountPaid,
            BigDecimal balanceAmount,
            String paymentMethod,
            String paymentStatus,
            LocalDateTime invoiceDate,
            String remarks) {

        this.invoiceId = invoiceId;
        this.invoiceNumber = invoiceNumber;
        this.appointmentId = appointmentId;
        this.doctorFee = doctorFee;
        this.hospitalFee = hospitalFee;
        this.additionalFee = additionalFee;
        this.discount = discount;
        this.totalAmount = totalAmount;
        this.amountPaid = amountPaid;
        this.balanceAmount = balanceAmount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.invoiceDate = invoiceDate;
        this.remarks = remarks;
    }


    //Getters and Setters
    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public BigDecimal getDoctorFee() {
        return doctorFee;
    }

    public void setDoctorFee(BigDecimal doctorFee) {
        this.doctorFee = doctorFee;
    }

    public BigDecimal getHospitalFee() {
        return hospitalFee;
    }

    public void setHospitalFee(BigDecimal hospitalFee) {
        this.hospitalFee = hospitalFee;
    }

    public BigDecimal getAdditionalFee() {
        return additionalFee;
    }

    public void setAdditionalFee(BigDecimal additionalFee) {
        this.additionalFee = additionalFee;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
