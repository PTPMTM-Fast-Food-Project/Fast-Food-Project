package com.ecommerce.library.dto;

public class CartItemDto {
    private Long id;

    private ShoppingCartDto cart;

    private ProductDto product;

    private int quantity;

    private double unitPrice;

    public CartItemDto() {
    }

    public CartItemDto(Long id, ShoppingCartDto cart, ProductDto product, int quantity, double unitPrice) {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShoppingCartDto getCart() {
        return cart;
    }

    public void setCart(ShoppingCartDto cart) {
        this.cart = cart;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}