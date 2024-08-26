package org.example.mapper;

import org.example.entities.Invoice;
import org.example.models.InvoiceItemModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(source = "image", target = "image", qualifiedByName = "pathToFile")
    InvoiceItemModel MapInvoice(Invoice invoice);
    List<InvoiceItemModel> MapInvoices(List<Invoice> invoices);

    @Named("pathToFile")
    public static String pathToFile(String image) {
        return "/uploading/"+image;
    }
}
