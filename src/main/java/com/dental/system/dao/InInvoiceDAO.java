package com.dental.system.dao;

import com.dental.system.model.Invoice;

import java.util.List;

public interface InInvoiceDAO {

    boolean addInvoice(Invoice invoice);

    List<Invoice> getAllInvoices();

    Invoice getInvoiceById(int invoiceId);

    Invoice getInvoiceByNumber(String invoiceNumber);

    Invoice getInvoiceByAppointmentId(int appointmentId);

    boolean updateInvoice(Invoice invoice);

    boolean deleteInvoice(int invoiceId);

    String generateNextInvoiceNumber();


}
