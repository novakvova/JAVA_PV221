package org.example.entites;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="cats")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 150, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String type;
    private int age;
}
