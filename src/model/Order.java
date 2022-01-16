package model;

import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-08-20
 **/
public class Order {
    private String orderId;
    private String orderDate;
    private String custId;

    private ArrayList<OrderDetails> orders;

    public Order() {
    }

    public Order(String orderId, String orderDate, String custId, ArrayList<OrderDetails> orders) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.custId = custId;
        this.orders = orders;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public ArrayList<OrderDetails> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<OrderDetails> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", custId='" + custId + '\'' +
                ", orders=" + orders +
                '}';
    }
}
