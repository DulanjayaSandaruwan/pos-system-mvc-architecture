package controller;

import model.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-08-18
 **/

public interface CustomerManage {
    public boolean saveCustomer(Customer C) throws SQLException;
    public Customer searchCustomer(String id) throws SQLException;
    public boolean updateCustomer(Customer C) throws SQLException;
    public boolean deleteCustomer(String id) throws SQLException;
    public ArrayList<Customer> selectAllCustomers() throws SQLException;

}
