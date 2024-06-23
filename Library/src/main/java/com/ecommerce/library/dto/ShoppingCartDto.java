package com.ecommerce.library.dto;

import com.ecommerce.library.model.Customer;

import java.util.Set;

public class ShoppingCartDto {
    private Long id;

    private Customer customer;

    private double totalPrice;

    private int totalItems;

    private Set<CartItemDto> cartItems;

    public ShoppingCartDto() {
    }

    public ShoppingCartDto(Long id, Customer customer, double totalPrice, int totalItems, Set<CartItemDto> cartItems) {
        this.id = id;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.totalItems = totalItems;
        this.cartItems = cartItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public Set<CartItemDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }
}