package org.example.service;

import org.example.entities.Invoice;
import org.example.models.InvoiceCreateModel;

import java.util.List;

public interface IInvoiceService {
    public Invoice saveInvice(InvoiceCreateModel model);
    public List<Invoice> getAllInvoices();
    public Invoice getInvoiceById(Long id);
    public void deleteInvoiceById(Long id);
    public void updateInvoice(Invoice invoice);
}
