package com.flapkap.vending.machine.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @Min(value = 1, message = "Amount must be at least 1")
    private int amount;

    @Min(value = 1, message = "Price must be at least 1")
    private double price;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Seller seller;
}
