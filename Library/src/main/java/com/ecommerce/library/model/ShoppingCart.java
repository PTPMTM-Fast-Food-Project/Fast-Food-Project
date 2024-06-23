package com.ecommerce.library.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    private double totalPrice;

    private int totalItems;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "cart")
    private Set<CartItem> cartItems;

    public ShoppingCart() {
        this.cartItems = new HashSet<>();
        this.totalItems = 0;
        this.totalPrice = 0.0;
    }

    public ShoppingCart(Long id, Customer customer, double totalPrice, int totalItems, Set<CartItem> cartItems) {
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

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ", customer=" + customer.getUsername() +
                ", totalPrice=" + totalPrice +
                ", totalItems=" + totalItems +
                ", cartItems=" + cartItems.size() +
                '}';
    }
}