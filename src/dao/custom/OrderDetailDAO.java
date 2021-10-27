package dao.custom;

import dao.CrudDAO;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO extends CrudDAO<OrderDetail,String> {
    ArrayList<OrderDetail> selectOrder(String orderid) throws SQLException, ClassNotFoundException;
}
