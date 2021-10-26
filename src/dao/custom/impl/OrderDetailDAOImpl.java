package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailDAO;
import model.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public boolean add(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO OrderDetail (ItemCode, OrderId, OrderQTY, price, Discount) VALUES (?,?,?,?,?)",
                orderDetail.getItemCode(),
                orderDetail.getOrderID(),
                orderDetail.getOrderqty(),
                orderDetail.getPrice(),
                orderDetail.getDiscount());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM OrderDetail WHERE OrderId=?", s);
    }

    @Override
    public boolean update(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> allOrdeDetails = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM OrderDetail");
        while (rst.next()) {
            allOrdeDetails.add(new OrderDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4),
                    rst.getDouble(5)
            ));
        }
        return allOrdeDetails;
    }

    @Override
    public ArrayList<OrderDetail> selectOrder(String orderid) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> OrdeDetailsList = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM OrderDetail WHERE OrderId=?");
        while (rst.next()) {
            OrdeDetailsList.add(new OrderDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4),
                    rst.getDouble(5)
            ));
        }
        return OrdeDetailsList;
    }


}
