package controller;

import db.DbConnection;
import model.Customer;
import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-08-18
 **/
public class ItemController implements ItemManage {

    public List<String> getItemCodes() throws SQLException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Item").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override
    public boolean saveItem(Item t) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Item VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,t.getCode());
        stm.setObject(2,t.getDescription());
        stm.setObject(3,t.getPackSize());
        stm.setObject(4,t.getUnitPrice());
        stm.setObject(5,t.getQtyOnHand());

        return stm.executeUpdate()>0;

    }

    @Override
    public Item searchItem(String code) throws SQLException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Item WHERE ItemCode=?");
        stm.setObject(1, code);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5)
            );

        } else {
            return null;
        }
    }

    @Override
    public boolean updateItem(Item t) throws SQLException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Item SET  Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode=?");
        stm.setObject(1,t.getDescription());
        stm.setObject(2,t.getPackSize());
        stm.setObject(3,t.getUnitPrice());
        stm.setObject(4,t.getQtyOnHand());
        stm.setObject(5,t.getCode());

        return stm.executeUpdate()>0;
    }

    @Override
    public boolean deleteItem(String code) throws SQLException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Item WHERE ItemCode='"+code+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public ArrayList<Item> selectAllItems() throws SQLException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item");
        ResultSet rst = stm.executeQuery();
        ArrayList<Item> items = new ArrayList<>();
        while (rst.next()) {
            items.add(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5)
            ));
        }
        return items;
    }

}
