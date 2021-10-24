package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import model.Customer;
import model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean ifOrderExist(String oid) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<String> getOrderIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order`");
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderId FROM `Order` ORDER BY OrderId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString(1);
            int newCustomerId = Integer.parseInt(id.replace("O", "")) + 1;
            return String.format("O%03d", newCustomerId);
        } else {
            return "O001";
        }
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
