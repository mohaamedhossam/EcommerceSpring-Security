package com.springecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "orders"
)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(
            name = "customer_id",
            referencedColumnName = "customerId"
    )
     @NotNull(message = "please add Customer ID")
    private Customer customer;

    private LocalDate orderDate;
    private int totalAmount;
    private String deliveryAddress;
    private LocalDate expectedDeliveryDate;
    private boolean confirmed = false;

    @OneToMany(
            mappedBy = "order",
            cascade = {CascadeType.ALL}
    )
    @JsonIgnore
    private List<OrderProduct> orderProducts;

    public boolean isConfirmed() {
        return this.confirmed;
    }
}
