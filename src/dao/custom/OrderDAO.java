package dao.custom;

import dao.CrudDAO;
import model.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderDAO extends CrudDAO<Order, String> {
    boolean ifOrderExist(String oid) throws SQLException, ClassNotFoundException;

    List<String> getOrderIds() throws SQLException, ClassNotFoundException;

    String generateNewOrderId() throws SQLException, ClassNotFoundException;

    ArrayList<Order> searchOrderbyCustId(String custId) throws SQLException, ClassNotFoundException;

    List<String> getOrderIdsbyCustomer(String id) throws SQLException, ClassNotFoundException;


}
