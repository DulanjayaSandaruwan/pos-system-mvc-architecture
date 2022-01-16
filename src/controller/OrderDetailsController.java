package controller;

import db.DbConnection;
import model.Customer;
import model.Order;
import model.OrderDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-08-20
 **/
public class OrderDetailsController implements OrderDetailsManage {

    public String getOrderId() throws SQLException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT OrderId FROM `Order` ORDER BY OrderId DESC LIMIT 1").executeQuery();
            if(rst.next()){
                int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
                tempId = tempId+1;
                if(tempId<=9){
                    return  "O-00"+tempId;
                }
                else if(tempId<99) {
                    return "O-0"+tempId;
                }
                else {
                    return "O-"+tempId;
                }
            }
            else {
                return "O-001";
            }
    }

    public boolean placeOrder(Order order){
        try {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO `Order` VALUES(?, ?, ?)");
                stm.setObject(1,order.getOrderId());
                stm.setObject(2,order.getOrderDate());
                stm.setObject(3,order.getCustId());


                if(stm.executeUpdate()>0){

                    return saveOrderDetails(order.getOrderId(), order.getOrders());
                }
                else {
                    return false;
                }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private boolean saveOrderDetails(String orderId, ArrayList<OrderDetails> orders) throws SQLException {
        for (OrderDetails temp:orders
             ) {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO `Order Detail` VALUES(?, ?, ?, ?)");
            stm.setObject(1,temp.getItemCode());
            stm.setObject(2,orderId);
            stm.setObject(3,temp.getOrderQty());
            stm.setObject(4,temp.getDiscount());

            if(stm.executeUpdate()>0){
                if(updateQty(temp.getItemCode(), temp.getOrderQty())){

                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(String itemCode, int qty) throws SQLException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Item SET qtyOnHand=(qtyOnHand-" + qty + ") WHERE Itemcode='" + itemCode + "'");
            return stm.executeUpdate()>0;
    }

    @Override
    public OrderDetails searchOrder(String code) throws SQLException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM `Order Detail` WHERE OrderId=?");
        stm.setObject(1, code);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new OrderDetails(
                    rst.getString(1),
                    rst.getInt(3),
                    rst.getDouble(4)
            );

        } else {
            return null;
        }
    }

    @Override
    public boolean updateOrderDetails(OrderDetails o, String orderId) throws SQLException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `Order Detail` SET Qty=?, Discount=? WHERE OrderId=?");
        stm.setObject(1,o.getOrderQty());
        stm.setObject(2,o.getDiscount());
        stm.setObject(3,orderId);

        return stm.executeUpdate()>0;
    }

    @Override
    public boolean deleteOrderDetails(String code) throws SQLException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Order Detail` WHERE OrderId='"+code+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }
}
