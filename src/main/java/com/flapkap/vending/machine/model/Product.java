package com.flapkap.vending.machine.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Builder
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
    private int price;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Seller seller;

    public Product(String name, int amount, int price, Seller seller) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.seller = seller;
    }
}
