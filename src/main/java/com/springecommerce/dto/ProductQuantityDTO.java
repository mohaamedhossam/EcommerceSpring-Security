package com.springecommerce.dto;

import com.springecommerce.entity.Product;
import lombok.Data;

@Data
public class ProductQuantityDTO {
    private Product product;
    private Integer quantity;

    public ProductQuantityDTO(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}