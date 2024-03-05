package com.springecommerce.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long productId;
    private @NotBlank(
            message = "please add product name"
    ) String name;
    private @NotBlank(
            message = "please add product category"
    ) String category;
    private @NotNull(
            message = "please add product price"
    ) @PositiveOrZero(
            message = "please add a valid positive price"
    ) Integer price;
}