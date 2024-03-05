//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
import com.springecommerce.service.OrderService;
import com.springecommerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderProductServiceImplTest {
    @Mock
    private OrderProductRepository orderProductRepository;
    @Mock
    private OrderService orderService;
    @Mock
    private ProductService productService;
    @Mock
    private CustomerService customerService;
    @InjectMocks
    private OrderProductServiceImpl orderProductService;

    OrderProductServiceImplTest() {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddToCart() throws ProductNotFoundException, CustomerNotFoundException {
        Long customerId = 1L;
        Long productId = 1L;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        Order order = new Order();
        order.setOrderId(1L);

        Product product = new Product();
        product.setProductId(productId);

        OrderProduct savedOrderProduct = new OrderProduct();
        savedOrderProduct.setOrder(order);
        savedOrderProduct.setProduct(product);
        savedOrderProduct.setQuantity(5);

        when(customerService.getCustomerById(customerId)).thenReturn(customer);
        when(orderService.getCurrentCustomerOrder(customerId)).thenReturn(order);
        when(productService.getProductById(productId)).thenReturn(product);
        when(orderProductRepository.findByOrderAndProduct(order, product)).thenReturn(Optional.empty());
        when(orderProductRepository.save(any())).thenReturn(savedOrderProduct);

        OrderProduct orderProduct = this.orderProductService.addToCart(1L, 1L, 5);

        assertNotNull(orderProduct);
        assertEquals(order, orderProduct.getOrder());
        assertEquals(product, orderProduct.getProduct());
        assertEquals(5, orderProduct.getQuantity());
        verify(this.orderProductRepository, times(1)).save(orderProduct);
        verify(this.orderService, times(1)).computeAndUpdateTotalAmount(order);
    }

    @Test
    @DisplayName("Test add to cart to product already in the cart")
    public void testAddToCart_alreadyExistsProduct() throws ProductNotFoundException, CustomerNotFoundException {
        Long customerId = 1L;
        Long productId = 1L;
        Integer quantity = 1;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        Order order = new Order();
        order.setOrderId(1L);

        Product product = new Product();
        product.setProductId(productId);

        OrderProduct existingOrderProduct = new OrderProduct();
        existingOrderProduct.setQuantity(5);

        when(customerService.getCustomerById(customerId)).thenReturn(customer);
        when(orderService.getCurrentCustomerOrder(customerId)).thenReturn(order);
        when(productService.getProductById(productId)).thenReturn(product);
        when(orderProductRepository.findByOrderAndProduct(order, product)).thenReturn(Optional.of(existingOrderProduct));
        when(orderProductRepository.save(any())).thenReturn(existingOrderProduct);

        OrderProduct updatedOrderProduct = orderProductService.addToCart(customerId, productId, quantity);

        assertEquals(5 + quantity, updatedOrderProduct.getQuantity());
        verify(orderService, times(1)).computeAndUpdateTotalAmount(order);
    }

    @Test
    public void testRemoveFromCart() throws CustomerNotFoundException, ProductNotFoundException, OrderProductNotFoundException {
        Long customerId = 1L;
        Long productId = 1L;

        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        Order order = new Order();
        order.setOrderId(1L);

        Product product = new Product();
        product.setProductId(productId);

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrderProductId(1L);
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setQuantity(5);

        when(customerService.getCustomerById(customerId)).thenReturn(customer);
        when(orderService.getCurrentCustomerOrder(customerId)).thenReturn(order);
        when(productService.getProductById(productId)).thenReturn(product);
        when(orderProductRepository.findByOrderAndProduct(order, product)).thenReturn(Optional.of(orderProduct));

        orderProductService.removeFromCart(customerId, productId);

        verify(orderProductRepository, times(1)).deleteByOrderAndProduct(order, product);
        verify(this.orderService, times(1)).computeAndUpdateTotalAmount(order);
    }

    @Test
    public void testRemoveFromCart_whenProductDoesnotExist_inCart() throws CustomerNotFoundException, ProductNotFoundException, OrderProductNotFoundException {
        Long customerId = 1L;
        Long productId = 1L;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        Order order = new Order();
        order.setOrderId(1L);

        Product product = new Product();
        product.setProductId(productId);

        when(this.customerService.getCustomerById(customerId)).thenReturn(customer);
        when(this.orderService.getCurrentCustomerOrder(customerId)).thenReturn(order);
        when(this.productService.getProductById(productId)).thenReturn(product);
        when(this.orderProductRepository.findByOrderAndProduct(order, product)).thenReturn(Optional.empty());

        assertThrows(OrderProductNotFoundException.class, () -> {
            this.orderProductService.removeFromCart(customerId, productId);
        });
        verify(this.orderProductRepository, Mockito.never()).deleteByOrderAndProduct(order, product);
        verify(this.orderService, Mockito.never()).computeAndUpdateTotalAmount(order);
    }

    @Test
    public void testOpenCart() throws CustomerNotFoundException {
        Long customerId = 1L;
        Long orderId = 1L;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        Order order = new Order();
        order.setOrderId(orderId);

        List<ProductQuantityDTO> products = Arrays.asList(
                new ProductQuantityDTO(new Product(), 1),
                new ProductQuantityDTO(new Product(), 2)
        );

        when(this.customerService.getCustomerById(customerId)).thenReturn(customer);
        when(this.orderService.getCurrentCustomerOrder(customerId)).thenReturn(order);
        when(this.orderProductRepository.findProductsAndQuantitiesByOrderId(orderId)).thenReturn(products);
        List<ProductQuantityDTO> result = this.orderProductService.openCart(customerId);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testClearCart() throws CustomerNotFoundException {
        Long customerId = 1L;
        Long orderId = 1L;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        Order order = new Order();
        order.setOrderId(orderId);

        when(customerService.getCustomerById(customerId)).thenReturn(customer);
        when(orderService.getCurrentCustomerOrder(customerId)).thenReturn(order);
        orderProductService.clearCart(customerId);
        verify(orderProductRepository, times(1)).deleteByOrderId(orderId);
        verify(orderService, times(1)).computeAndUpdateTotalAmount(order);
    }
}
