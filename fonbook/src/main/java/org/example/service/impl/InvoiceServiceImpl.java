package org.example.service.impl;

import org.example.exception.InvoiceNotFoundException;
import org.example.entities.Invoice;
import org.example.mapper.InvoiceMapper;
import org.example.models.InvoiceCreateModel;
import org.example.models.InvoiceItemModel;
import org.example.repo.InvoiceRepository;
import org.example.service.IInvoiceService;
import org.example.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements IInvoiceService {

    @Autowired
    private InvoiceRepository repo;
    @Autowired
    private StorageService storageService;
    @Autowired
    private InvoiceMapper invoiceMapper;

    @Override
    public Invoice saveInvice(InvoiceCreateModel model) {
        try {
            Invoice invoice = new Invoice();
            invoice.setName(model.getName());
            invoice.setAmount(model.getAmount());
            var imageName = storageService.saveFile(model.getImage());
            invoice.setImage(imageName);
            invoice.setLocation(model.getLocation());
            return repo.save(invoice);
        }
        catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<InvoiceItemModel> getAllInvoices() {
        return invoiceMapper.MapInvoices(repo.findAll());
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        Optional<Invoice> opt = repo.findById(id);
        if(opt.isPresent()) {
            return opt.get();
        } else {
            throw new InvoiceNotFoundException("Invoice with Id : "+id+" Not Found");
        }
    }

    @Override
    public void deleteInvoiceById(Long id) {
        repo.delete(getInvoiceById(id));
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        repo.save(invoice);
    }
}
