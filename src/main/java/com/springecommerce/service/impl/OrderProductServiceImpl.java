package com.springecommerce.service.impl;

import com.springecommerce.dto.ProductQuantityDTO;
import com.springecommerce.entity.Customer;
import com.springecommerce.entity.Order;
import com.springecommerce.entity.OrderProduct;
import com.springecommerce.entity.Product;
import com.springecommerce.error.CustomException;
import com.springecommerce.repository.OrderProductRepository;
import com.springecommerce.service.CustomerService;
import com.springecommerce.service.OrderProductService;
import com.springecommerce.service.OrderService;
import com.springecommerce.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderProductServiceImpl implements OrderProductService {


    private final OrderProductRepository orderProductRepository;

    private final OrderService orderService;

    private final ProductService productService;

    private final CustomerService customerService;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository, OrderService orderService, ProductService productService, CustomerService customerService) {
        this.orderProductRepository = orderProductRepository;
        this.orderService = orderService;
        this.productService = productService;
        this.customerService = customerService;
    }

    @Override
    public OrderProduct addToCart(Long customerId, Long productId, Integer quantity) throws CustomException{

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
    public void removeFromCart(Long customerId, Long productId) throws CustomException {
        Customer customer = customerService.getCustomerById(customerId);
        Order order = orderService.getCurrentCustomerOrder(customerId);
        Product product = productService.getProductById(productId);

        Optional<OrderProduct> orderProduct = orderProductRepository.findByOrderAndProduct(order,product);
        if (!orderProduct.isPresent()) {
            throw new CustomException("No such product to delete", HttpStatus.NOT_FOUND);
        }

        orderProductRepository.deleteByOrderAndProduct(order,product);
        orderService.computeAndUpdateTotalAmount(order);
    }

    @Override
    public List<ProductQuantityDTO> openCart(Long customerId) throws CustomException {
        Customer customer = customerService.getCustomerById(customerId);
        Long orderId = orderService.getCurrentCustomerOrder(customerId).getOrderId();

        return orderProductRepository.findProductsAndQuantitiesByOrderId(orderId);
    }


    @Override
    @Transactional
    public void clearCart(Long customerId) throws CustomException {
        Customer customer = customerService.getCustomerById(customerId);
        Order order = orderService.getCurrentCustomerOrder(customerId);

        orderProductRepository.deleteByOrderId(order.getOrderId());

        orderService.computeAndUpdateTotalAmount(order);
    }
}