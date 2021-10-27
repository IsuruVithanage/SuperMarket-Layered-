package bo.custom.impl;

import bo.custom.CustomerBO;
import bo.custom.ItemBO;
import bo.custom.ManageOrderBO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailDAOImpl;
import db.DbConnection;
import javafx.collections.ObservableList;
import model.*;
import view.tm.CartTM;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageOrderBOImpl implements ManageOrderBO {
    private final OrderDAO orderDAO = new OrderDAOImpl();
    private final CustomerBO customerBO = new CustomerBOImpl();
    private final ItemBO itemBO = new ItemBOImpl();
    private final OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();

    @Override
    public List<String> getOrderIdsbyCustomer(String id) throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderIdsbyCustomer(id);
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.searchCustomer(id);
    }

    @Override
    public ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException {
        return itemBO.searchItem(id);
    }

    @Override
    public ArrayList<OrderDetail> selectOrder(String orderid) throws SQLException, ClassNotFoundException {
        return orderDetailDAO.selectOrder(orderid);
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemBO.deleteItem(id);
    }

    @Override
    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
        if (orderDAO.delete(id)) {
            ArrayList<OrderDetail> orderDetails = orderDetailDAO.selectOrder(id);
            for (OrderDetail orderDetail : orderDetails) {
                itemBO.updateQTY(orderDetail.getItemCode(), itemBO.getItemQTYOnHand(orderDetail.getItemCode()) + orderDetail.getOrderqty());
            }
            orderDetailDAO.delete(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateOrder(String orderId, ArrayList<OrderDetail> newItems, ObservableList<CartTM> deletedItem) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> prevItems = selectOrder(orderId);
        boolean state = false;

        for (OrderDetail temp1 : prevItems) {
            for (OrderDetail temp2 : newItems) {
                if (temp1.getItemCode().equals(temp2.getItemCode()) && temp1.getOrderqty() != temp2.getOrderqty()) {
                    int prevQTYonhand = itemBO.getItemQTYOnHand(temp1.getItemCode());
                    state = itemBO.updateQTY(temp1.getItemCode(), -(temp1.getOrderqty() - temp2.getOrderqty()));
                }
            }
        }
        for (OrderDetail temp1 : prevItems) {
            for (CartTM temp2 : deletedItem) {
                if (temp1.getItemCode().equals(temp2.getItemId())) {
                    int prevQTYonhand = itemBO.getItemQTYOnHand(temp1.getItemCode());
                    state = itemBO.updateQTY(temp1.getItemCode(), - temp2.getQty());
                }
            }
        }
        orderDAO.delete(orderId);
        orderDetailDAO.delete(orderId);

        return state;
    }

    @Override
    public boolean addOrder(ItemDTO item) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean placeOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        Order order = new Order(
                orderDTO.getOrderId(),
                orderDTO.getCustID(),
                orderDTO.getOrderDate(),
                orderDTO.getOrderTime(),
                orderDTO.getCost()
        );
        boolean orderAdded = orderDAO.add(order);

        if (!orderAdded) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetail detail : orderDTO.getItems()) {
            boolean orderDetailsAdded = orderDetailDAO.add(detail);
            if (!orderDetailsAdded) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            boolean update = itemBO.updateQTY(detail.getItemCode(), detail.getOrderqty());
            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        //if every thing ok
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        return customerBO.getCustomerIds();
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewOrderId();
    }

    @Override
    public List<String> getItemIds() throws SQLException, ClassNotFoundException {
        return itemBO.getItemIds();
    }


}
