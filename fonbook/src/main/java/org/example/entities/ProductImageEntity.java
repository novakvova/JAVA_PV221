package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_productImages")
public class ProductImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String name;

    private int priority;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    private boolean isDelete;

    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    private ProductEntity product;
}
