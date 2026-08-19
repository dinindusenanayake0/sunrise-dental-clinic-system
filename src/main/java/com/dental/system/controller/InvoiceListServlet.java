package com.dental.system.controller;

import com.dental.system.dao.InvoiceDAO;
import com.dental.system.model.Invoice;
import com.dental.system.service.InInvoiceService;
import com.dental.system.service.InvoiceService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/invoices")
public class InvoiceListServlet extends HttpServlet {

    private final InInvoiceService invoiceService;

    public InvoiceListServlet() {
        this.invoiceService =
                new InvoiceService(new InvoiceDAO());
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<Invoice> invoices =
                invoiceService.getAllInvoices();

        request.setAttribute("invoices", invoices);

        request.getRequestDispatcher("/invoices.jsp")
                .forward(request, response);
    }
}
