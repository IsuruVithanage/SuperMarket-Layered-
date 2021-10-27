package bo.custom;

import bo.SuperBO;
import javafx.collections.ObservableList;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import entity.OrderDetail;
import view.tm.CartTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ManageOrderBO extends SuperBO {
    List<String> getOrderIdsbyCustomer(String id) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetail> selectOrder(String orderid) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;

    boolean deleteOrder(String id) throws SQLException, ClassNotFoundException;

    boolean updateOrder(String orderId, ArrayList<OrderDetail> newItems, ObservableList<CartTM> deletedItem) throws SQLException, ClassNotFoundException;

    boolean addOrder(ItemDTO item) throws SQLException, ClassNotFoundException;

    boolean placeOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException;

    List<String> getCustomerIds() throws SQLException, ClassNotFoundException;

    String generateNewOrderId() throws SQLException, ClassNotFoundException;

    List<String> getItemIds() throws SQLException, ClassNotFoundException;


}
