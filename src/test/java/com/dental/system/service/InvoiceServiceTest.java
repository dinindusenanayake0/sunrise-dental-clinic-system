package com.dental.system.service;

import com.dental.system.dao.InInvoiceDAO;
import com.dental.system.model.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceServiceTest {

    private InvoiceService invoiceService;
    private FakeInvoiceDAO fakeInvoiceDAO;

    @BeforeEach
    void setUp() {
        fakeInvoiceDAO = new FakeInvoiceDAO();
        invoiceService = new InvoiceService(fakeInvoiceDAO);
    }

    private Invoice createValidInvoice() {

        Invoice invoice = new Invoice();

        invoice.setInvoiceId(1);
        invoice.setAppointmentId(1);

        invoice.setTreatmentCost(
                new BigDecimal("2500.00")
        );

        invoice.setDoctorFee(
                new BigDecimal("2000.00")
        );

        invoice.setHospitalFee(
                new BigDecimal("1000.00")
        );

        invoice.setAdditionalFee(
                new BigDecimal("500.00")
        );

        invoice.setDiscount(
                new BigDecimal("500.00")
        );

        invoice.setAmountPaid(
                new BigDecimal("1000.00")
        );

        invoice.setPaymentMethod("Cash");
        invoice.setRemarks("Test invoice");

        return invoice;
    }


    @Test
    void addInvoiceWithValidDetailsShouldReturnTrue() {

        Invoice invoice = createValidInvoice();

        fakeInvoiceDAO.nextInvoiceNumber =
                "INV-0001";

        boolean result =
                invoiceService.addInvoice(invoice);

        assertTrue(result);

        assertEquals(
                "INV-0001",
                invoice.getInvoiceNumber()
        );

        assertEquals(
                0,
                new BigDecimal("5500.00")
                        .compareTo(
                                invoice.getTotalAmount()
                        )
        );

        assertEquals(
                0,
                new BigDecimal("4500.00")
                        .compareTo(
                                invoice.getBalanceAmount()
                        )
        );

        assertEquals(
                "Partially Paid",
                invoice.getPaymentStatus()
        );
    }


    @Test
    void addNullInvoiceShouldReturnFalse() {

        boolean result =
                invoiceService.addInvoice(null);

        assertFalse(result);
    }


    @Test
    void addInvoiceWithInvalidAppointmentIdShouldReturnFalse() {

        Invoice invoice = createValidInvoice();

        invoice.setAppointmentId(0);

        boolean result =
                invoiceService.addInvoice(invoice);

        assertFalse(result);
    }


    @Test
    void addInvoiceWithNegativeTreatmentCostShouldReturnFalse() {

        Invoice invoice = createValidInvoice();

        invoice.setTreatmentCost(
                new BigDecimal("-100.00")
        );

        boolean result =
                invoiceService.addInvoice(invoice);

        assertFalse(result);
    }


    @Test
    void addInvoiceWithNegativeDoctorFeeShouldReturnFalse() {

        Invoice invoice = createValidInvoice();

        invoice.setDoctorFee(
                new BigDecimal("-100.00")
        );

        boolean result =
                invoiceService.addInvoice(invoice);

        assertFalse(result);
    }


    @Test
    void addInvoiceWithZeroSubtotalShouldReturnFalse() {

        Invoice invoice = createValidInvoice();

        invoice.setTreatmentCost(
                BigDecimal.ZERO
        );

        invoice.setDoctorFee(
                BigDecimal.ZERO
        );

        invoice.setHospitalFee(
                BigDecimal.ZERO
        );

        invoice.setAdditionalFee(
                BigDecimal.ZERO
        );

        invoice.setDiscount(
                BigDecimal.ZERO
        );

        invoice.setAmountPaid(
                BigDecimal.ZERO
        );

        boolean result =
                invoiceService.addInvoice(invoice);

        assertFalse(result);
    }


    @Test
    void addInvoiceWithDiscountGreaterThanSubtotalShouldReturnFalse() {

        Invoice invoice = createValidInvoice();

        invoice.setTreatmentCost(
                BigDecimal.ZERO
        );

        invoice.setDoctorFee(
                new BigDecimal("1000.00")
        );

        invoice.setHospitalFee(
                BigDecimal.ZERO
        );

        invoice.setAdditionalFee(
                BigDecimal.ZERO
        );

        invoice.setDiscount(
                new BigDecimal("1500.00")
        );

        invoice.setAmountPaid(
                BigDecimal.ZERO
        );

        boolean result =
                invoiceService.addInvoice(invoice);

        assertFalse(result);
    }


    @Test
    void addInvoiceWithAmountPaidGreaterThanTotalShouldReturnFalse() {

        Invoice invoice = createValidInvoice();

        invoice.setAmountPaid(
                new BigDecimal("6000.00")
        );

        boolean result =
                invoiceService.addInvoice(invoice);

        assertFalse(result);
    }


    @Test
    void addInvoiceWithInvalidPaymentMethodShouldReturnFalse() {

        Invoice invoice = createValidInvoice();

        invoice.setPaymentMethod("Cheque");

        boolean result =
                invoiceService.addInvoice(invoice);

        assertFalse(result);
    }


    @Test
    void addInvoiceWithZeroPaymentShouldSetPending() {

        Invoice invoice = createValidInvoice();

        invoice.setAmountPaid(
                BigDecimal.ZERO
        );

        boolean result =
                invoiceService.addInvoice(invoice);

        assertTrue(result);

        assertEquals(
                "Pending",
                invoice.getPaymentStatus()
        );
    }


    @Test
    void addInvoiceWithPartialPaymentShouldSetPartiallyPaid() {

        Invoice invoice = createValidInvoice();

        invoice.setAmountPaid(
                new BigDecimal("1000.00")
        );

        boolean result =
                invoiceService.addInvoice(invoice);

        assertTrue(result);

        assertEquals(
                "Partially Paid",
                invoice.getPaymentStatus()
        );
    }


    @Test
    void addInvoiceWithFullPaymentShouldSetPaid() {

        Invoice invoice = createValidInvoice();

        invoice.setAmountPaid(
                new BigDecimal("5500.00")
        );

        boolean result =
                invoiceService.addInvoice(invoice);

        assertTrue(result);

        assertEquals(
                "Paid",
                invoice.getPaymentStatus()
        );

        assertEquals(
                0,
                BigDecimal.ZERO.compareTo(
                        invoice.getBalanceAmount()
                )
        );
    }


    @Test
    void addInvoiceWithNullOptionalFeesShouldUseZero() {

        Invoice invoice = createValidInvoice();

        invoice.setTreatmentCost(
                new BigDecimal("2500.00")
        );

        invoice.setDoctorFee(
                new BigDecimal("2000.00")
        );

        invoice.setHospitalFee(null);
        invoice.setAdditionalFee(null);
        invoice.setDiscount(null);
        invoice.setAmountPaid(null);

        boolean result =
                invoiceService.addInvoice(invoice);

        assertTrue(result);

        assertEquals(
                0,
                new BigDecimal("4500.00")
                        .compareTo(
                                invoice.getTotalAmount()
                        )
        );

        assertEquals(
                "Pending",
                invoice.getPaymentStatus()
        );
    }


    @Test
    void addInvoiceWhenInvoiceNumberGenerationFailsShouldReturnFalse() {

        Invoice invoice = createValidInvoice();

        fakeInvoiceDAO.nextInvoiceNumber =
                null;

        boolean result =
                invoiceService.addInvoice(invoice);

        assertFalse(result);
    }


    @Test
    void calculateSingleTreatmentCostShouldReturnCorrectAmount() {

        BigDecimal result =
                invoiceService.calculateTreatmentCost(
                        "Dental Cleaning"
                );

        assertEquals(
                0,
                new BigDecimal("2500.00")
                        .compareTo(result)
        );
    }


    @Test
    void calculateMultipleTreatmentCostShouldReturnTotal() {

        BigDecimal result =
                invoiceService.calculateTreatmentCost(
                        "Dental Cleaning, Tooth Filling, Teeth Whitening"
                );

        assertEquals(
                0,
                new BigDecimal("13500.00")
                        .compareTo(result)
        );
    }


    @Test
    void calculateEmptyTreatmentShouldReturnZero() {

        BigDecimal result =
                invoiceService.calculateTreatmentCost("");

        assertEquals(
                0,
                BigDecimal.ZERO.compareTo(result)
        );
    }


    @Test
    void calculateNullTreatmentShouldReturnZero() {

        BigDecimal result =
                invoiceService.calculateTreatmentCost(null);

        assertEquals(
                0,
                BigDecimal.ZERO.compareTo(result)
        );
    }


    @Test
    void updateValidInvoiceShouldReturnTrue() {

        Invoice invoice = createValidInvoice();

        invoice.setInvoiceId(1);

        invoice.setTotalAmount(
                new BigDecimal("5500.00")
        );

        invoice.setAmountPaid(
                new BigDecimal("1500.00")
        );

        boolean result =
                invoiceService.updateInvoice(invoice);

        assertTrue(result);

        assertEquals(
                "Partially Paid",
                invoice.getPaymentStatus()
        );

        assertEquals(
                0,
                new BigDecimal("4000.00")
                        .compareTo(
                                invoice.getBalanceAmount()
                        )
        );
    }


    @Test
    void updateInvoiceWithInvalidIdShouldReturnFalse() {

        Invoice invoice = createValidInvoice();

        invoice.setInvoiceId(0);

        invoice.setTotalAmount(
                new BigDecimal("5500.00")
        );

        boolean result =
                invoiceService.updateInvoice(invoice);

        assertFalse(result);
    }


    @Test
    void updateInvoiceWithNegativeAmountPaidShouldReturnFalse() {

        Invoice invoice = createValidInvoice();

        invoice.setTotalAmount(
                new BigDecimal("5500.00")
        );

        invoice.setAmountPaid(
                new BigDecimal("-100.00")
        );

        boolean result =
                invoiceService.updateInvoice(invoice);

        assertFalse(result);
    }


    @Test
    void updateInvoiceWithAmountPaidGreaterThanTotalShouldReturnFalse() {

        Invoice invoice = createValidInvoice();

        invoice.setTotalAmount(
                new BigDecimal("5500.00")
        );

        invoice.setAmountPaid(
                new BigDecimal("6000.00")
        );

        boolean result =
                invoiceService.updateInvoice(invoice);

        assertFalse(result);
    }


    @Test
    void updateInvoiceWithInvalidPaymentMethodShouldReturnFalse() {

        Invoice invoice = createValidInvoice();

        invoice.setTotalAmount(
                new BigDecimal("5500.00")
        );

        invoice.setPaymentMethod("Cheque");

        boolean result =
                invoiceService.updateInvoice(invoice);

        assertFalse(result);
    }


    @Test
    void getInvoiceByValidAppointmentIdShouldReturnInvoice() {

        Invoice invoice = createValidInvoice();

        fakeInvoiceDAO.invoiceByAppointmentId =
                invoice;

        Invoice result =
                invoiceService
                        .getInvoiceByAppointmentId(1);

        assertNotNull(result);

        assertEquals(
                1,
                result.getAppointmentId()
        );
    }


    @Test
    void getInvoiceByInvalidAppointmentIdShouldReturnNull() {

        Invoice result =
                invoiceService
                        .getInvoiceByAppointmentId(0);

        assertNull(result);
    }


    @Test
    void getAllInvoicesShouldReturnList() {

        fakeInvoiceDAO.invoices.add(
                createValidInvoice()
        );

        List<Invoice> result =
                invoiceService.getAllInvoices();

        assertNotNull(result);

        assertEquals(
                1,
                result.size()
        );
    }


    private static class FakeInvoiceDAO
            implements InInvoiceDAO {

        String nextInvoiceNumber =
                "INV-0001";

        Invoice invoiceByAppointmentId;

        List<Invoice> invoices =
                new ArrayList<>();


        @Override
        public boolean addInvoice(
                Invoice invoice
        ) {
            return true;
        }


        @Override
        public List<Invoice> getAllInvoices() {
            return invoices;
        }


        @Override
        public Invoice getInvoiceById(
                int invoiceId
        ) {
            return null;
        }


        @Override
        public Invoice getInvoiceByNumber(
                String invoiceNumber
        ) {
            return null;
        }


        @Override
        public Invoice getInvoiceByAppointmentId(
                int appointmentId
        ) {
            return invoiceByAppointmentId;
        }


        @Override
        public boolean updateInvoice(
                Invoice invoice
        ) {
            return true;
        }


        @Override
        public boolean deleteInvoice(
                int invoiceId
        ) {
            return true;
        }


        @Override
        public String generateNextInvoiceNumber() {
            return nextInvoiceNumber;
        }
    }
}