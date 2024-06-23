package com.ecommerce.library.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private Date orderDate;
    private Date deliveryDate;
    private String orderStatus;
    private double totalPrice;
    private double tax;
    private int quantity;
    private String paymentMethod;
    private boolean isAccept;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetail> orderDetailList;

    public Order() {
    }

    public Order(Long id, Date orderDate, Date deliveryDate, String orderStatus, double totalPrice,
                 double tax, int quantity, String paymentMethod, boolean isAccept, Customer customer,
                 List<OrderDetail> orderDetailList) {
        this.id = id;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.tax = tax;
        this.quantity = quantity;
        this.paymentMethod = paymentMethod;
        this.isAccept = isAccept;
        this.customer = customer;
        this.orderDetailList = orderDetailList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean accept) {
        isAccept = accept;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(totalPrice, order.totalPrice) == 0 &&
                Double.compare(tax, order.tax) == 0 &&
                quantity == order.quantity &&
                isAccept == order.isAccept &&
                Objects.equals(id, order.id) &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(deliveryDate, order.deliveryDate) &&
                Objects.equals(orderStatus, order.orderStatus) &&
                Objects.equals(paymentMethod, order.paymentMethod) &&
                Objects.equals(customer, order.customer) &&
                Objects.equals(orderDetailList, order.orderDetailList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDate, deliveryDate, orderStatus, totalPrice, tax,
                quantity, paymentMethod, isAccept, customer, orderDetailList);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", totalPrice=" + totalPrice +
                ", tax='" + tax + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", customer=" + customer.getUsername() +
                ", orderDetailList=" + orderDetailList.size() +
                '}';
    }
}