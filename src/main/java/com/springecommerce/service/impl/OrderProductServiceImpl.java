package com.springecommerce.service.impl;

import com.springecommerce.dto.ProductQuantityDTO;
import com.springecommerce.entity.Customer;
import com.springecommerce.entity.Order;
import com.springecommerce.entity.OrderProduct;
import com.springecommerce.entity.Product;
import com.springecommerce.error.CustomerNotFoundException;
import com.springecommerce.error.OrderProductNotFoundException;
import com.springecommerce.error.ProductNotFoundException;
import com.springecommerce.repository.OrderProductRepository;
import com.springecommerce.service.CustomerService;
import com.springecommerce.service.OrderProductService;
import com.springecommerce.service.OrderService;
import com.springecommerce.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Override
    public OrderProduct addToCart(Long customerId, Long productId, Integer quantity) throws ProductNotFoundException, CustomerNotFoundException {

        Customer customer = customerService.getCustomerById(customerId); // to check if ther is a customer with this id
        Order order = orderService.getCurrentCustomerOrder(customerId);
        Product product = productService.getProductById(productId);
        OrderProduct orderProduct = orderProductRepository.findByOrderAndProduct(order,product).orElse(null);;

        if(orderProduct != null){
            orderProduct.setQuantity(orderProduct.getQuantity()+quantity);
        }else {
            orderProduct = OrderProduct.builder()
                    .order(order)
                    .product(product)
                    .quantity(quantity)
                    .build();
        }
        OrderProduct orderProductDB = orderProductRepository.save(orderProduct);
        orderService.computeAndUpdateTotalAmount(order);
        return orderProductDB ;
    }

    @Override
    @Transactional
    public void removeFromCart(Long customerId, Long productId) throws CustomerNotFoundException, ProductNotFoundException, OrderProductNotFoundException {
        Customer customer = customerService.getCustomerById(customerId);
        Order order = orderService.getCurrentCustomerOrder(customerId);
        Product product = productService.getProductById(productId);

        Optional<OrderProduct> orderProduct = orderProductRepository.findByOrderAndProduct(order,product);
        if (!orderProduct.isPresent()) {
            throw new OrderProductNotFoundException("No such product to delete");
        }

        orderProductRepository.deleteByOrderAndProduct(order,product);
        orderService.computeAndUpdateTotalAmount(order);
    }

    @Override
    public List<ProductQuantityDTO> openCart(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerService.getCustomerById(customerId);
        Long orderId = orderService.getCurrentCustomerOrder(customerId).getOrderId();

        return orderProductRepository.findProductsAndQuantitiesByOrderId(orderId);
    }


    @Override
    @Transactional
    public void clearCart(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerService.getCustomerById(customerId);
        Order order = orderService.getCurrentCustomerOrder(customerId);

        orderProductRepository.deleteByOrderId(order.getOrderId());

        orderService.computeAndUpdateTotalAmount(order);
    }
}