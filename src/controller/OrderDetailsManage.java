package controller;

import model.Item;
import model.Order;
import model.OrderDetails;

import java.sql.SQLException;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-08-20
 **/
public interface OrderDetailsManage {
    public OrderDetails searchOrder(String code) throws SQLException;
    public boolean updateOrderDetails(OrderDetails o, String orderId) throws SQLException;
    public boolean deleteOrderDetails(String code) throws SQLException;
}
