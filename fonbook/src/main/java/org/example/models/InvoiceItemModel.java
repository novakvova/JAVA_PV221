package org.example.models;

import lombok.Data;

@Data
public class InvoiceItemModel {
    private Long id;
    private String name;
    private String location;
    private String image;
    private Double amount;
}
