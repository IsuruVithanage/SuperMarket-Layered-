package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
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
        /*ArrayList<ItemDetails> prevItems = selectOrder(orderId);
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Item SET QtyOnHand=? WHERE ItemCode=?");
        for (ItemDetails temp1 : prevItems) {
            for (ItemDetails temp2 : newItems) {
                if (temp1.getItemID().equals(temp2.getItemID()) && temp1.getQty() != temp2.getQty()) {
                    int prevQTYonhand = getItemQTYOnHand(temp1.getItemID());
                    stm.setObject(1, prevQTYonhand + temp1.getQty() - temp2.getQty());
                    stm.setObject(2, temp1.getItemID());

                }
            }
        }
        for (ItemDetails temp1 : prevItems) {
            for (CartTM temp2 : deletedItem) {
                if (temp1.getItemID().equals(temp2.getItemId())) {
                    int prevQTYonhand = getItemQTYOnHand(temp1.getItemID());
                    stm.setObject(1, prevQTYonhand + temp2.getQty());
                    stm.setObject(2, temp1.getItemID());

                }
            }
        }
        deleteOrder(orderId);
        deleteItem(orderId);

        return stm.executeUpdate() > 0;*/
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
        return null;
    }
}
