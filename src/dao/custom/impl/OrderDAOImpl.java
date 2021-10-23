package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import model.Customer;
import model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean ifOrderExist(String oid) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order` (OrderId, CustID, orderDate, time, cost) VALUES (?,?,?,?,?)",
                order.getOrderId(),
                order.getCustID(),
                order.getOrderDate(),
                order.getOrderTime(),
                order.getCost());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM `Order` WHERE OrderId=?", s);
    }

    @Override
    public boolean update(Order order) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order search(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order` WHERE OrderId=?", s);
        rst.next();
        return new Order(
                s,
                rst.getString(2),
                rst.getString(3),
                rst.getString(4),
                rst.getDouble(5),
                null

        );
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Order> allOrders = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order`");
        while (rst.next()) {
            allOrders.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    null
            ));
        }
        return allOrders;
    }
}
