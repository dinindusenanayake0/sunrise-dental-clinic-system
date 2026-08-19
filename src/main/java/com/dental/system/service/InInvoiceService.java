package com.dental.system.service;

import com.dental.system.model.Invoice;

import java.util.List;

public interface InInvoiceService {

    boolean addInvoice(Invoice invoice);

    Invoice getInvoiceByAppointmentId(int appointmentId);

    List<Invoice> getAllInvoices();
}
