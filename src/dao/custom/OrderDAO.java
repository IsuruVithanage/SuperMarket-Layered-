package dao.custom;

import dao.CrudDAO;
import model.CustIncomeDTO;
import model.MounthlyIncome;
import model.Order;
import model.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderDAO extends CrudDAO<Order, String> {
    boolean ifOrderExist(String oid) throws SQLException, ClassNotFoundException;

    List<String> getOrderIds() throws SQLException, ClassNotFoundException;

    String generateNewOrderId() throws SQLException, ClassNotFoundException;

    ArrayList<Order> searchOrderbyCustId(String custId) throws SQLException, ClassNotFoundException;

    List<String> getOrderIdsbyCustomer(String id) throws SQLException, ClassNotFoundException;

    ArrayList<CustIncomeDTO> cutomerIncome() throws SQLException, ClassNotFoundException;

    ArrayList<MounthlyIncome> mounthlyIncome() throws SQLException, ClassNotFoundException;


}
