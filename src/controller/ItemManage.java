package controller;

import model.Customer;
import model.Item;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-08-18
 **/

public interface ItemManage {
    public boolean saveItem(Item t) throws SQLException;
    public Item searchItem(String code) throws SQLException;
    public boolean updateItem(Item t) throws SQLException;
    public boolean deleteItem(String code) throws SQLException;
    public ArrayList<Item> selectAllItems() throws SQLException;
}
