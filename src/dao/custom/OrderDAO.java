package dao.custom;

import dao.CrudDAO;
import model.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderDAO extends CrudDAO<OrderDTO, String> {
    boolean ifOrderExist(String oid) throws SQLException, ClassNotFoundException;

    List<String> getOrderIds() throws SQLException, ClassNotFoundException;

    String generateNewOrderId() throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> searchOrderbyCustId(String custId) throws SQLException, ClassNotFoundException;

    List<String> getOrderIdsbyCustomer(String id) throws SQLException, ClassNotFoundException;


}
