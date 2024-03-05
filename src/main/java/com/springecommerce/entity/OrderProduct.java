package com.springecommerce.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "order_product_map"
)
public class OrderProduct {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long orderProductId;
    @ManyToOne
    @JoinColumn(
            name = "order_id"
    )
    private Order order;
    @ManyToOne
    @JoinColumn(
            name = "product_id"
    )
    private Product product;

    private Integer quantity;
}