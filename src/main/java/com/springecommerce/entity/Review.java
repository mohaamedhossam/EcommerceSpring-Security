package com.springecommerce.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long reviewID;
    @ManyToOne(
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "productId"
    )
    private Product product;
    @ManyToOne(
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(
            name = "customer_id",
            referencedColumnName = "customerId"
    )
    @JsonIgnore
    private Customer customer;
    private String comment;
}