package controller;

import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailDAOImpl;
import javafx.collections.ObservableList;
import model.*;
import view.tm.CartTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderController {
    /*private final ItemDAO itemDAO = new ItemDAOImpl();
    private final OrderDAO orderDAO = new OrderDAOImpl();
    private final OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
    List<String> monthlist = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    //
    //Save data in order Table
    *//*public boolean placeOrder(Order order){
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO `Order` VALUES(?,?,?,?,?)");
            stm.setObject(1, order.getOrderId());
            stm.setObject(2, order.getCustID());
            stm.setObject(3, order.getOrderDate());
            stm.setObject(4, order.getOrderTime());
            stm.setObject(5, order.getCost());

            if (stm.executeUpdate() > 0){

                if (saveOrderDetail(order.getOrderId(), order.getItems())){
                    con.commit();
                    return true;

                }else {
                    con.rollback();
                    return false;

                }

            }else{
                con.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }*//*

    //
    //Save Data in OrderDetail table
    *//*private boolean saveOrderDetail(String orderId, ArrayList<ItemDetails> items) throws SQLException, ClassNotFoundException {
        for (ItemDetails temp : items
        ) {
            PreparedStatement stm = DbConnection.getInstance().
                    getConnection().
                    prepareStatement("INSERT INTO OrderDetail VALUES(?,?,?,?,?)");
            stm.setObject(1, temp.getItemID());
            stm.setObject(2, orderId);
            stm.setObject(3, temp.getQty());
            stm.setObject(4, temp.getUnitPrice());
            stm.setObject(5, temp.getDiscount());
            if (stm.executeUpdate() > 0) {
                if (updateQTY(temp.getItemID(),temp.getQty())){

                }else {
                    return false;
                }

            } else {
                return false;
            }
        }
        return true;
    }*//*

    //Update item QTY on hand
    *//*private boolean updateQTY(String itemCode,int qty) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Item SET QtyOnHand=(QtyOnHand-"+qty+")WHERE  ItemCode='"+itemCode+"'");
        return stm.executeUpdate()>0;
    }*//*

    //
    //Generate Order Id
    *//*public String creatOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT OrderId FROM `Order` ORDER BY OrderId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "O-00"+tempId;
            }else if(tempId<=99){
                return "O-0"+tempId;
            }else{
                return "O-"+tempId;
            }

        }else{
            return "O-001";
        }
    }*//*

    //
    //Pass All Order IDs using Arraylist
    *//*public List<String> getOrderIds(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order` WHERE CustID=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();

        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }*//*

    *//*public int getItemQTYOnHand(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item WHERE ItemCode=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        int QTYOnHand = 0;
        while (rst.next()) {
            QTYOnHand = rst.getInt(4);

        }
        return QTYOnHand;
    }*//*

    //

    public boolean updateOrder(String orderId, ArrayList<OrderDetail> newItems, ObservableList<CartTM> deletedItem) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> prevItems = orderDetailDAO.selectOrder(orderId);
        boolean state = false;

        for (OrderDetail temp1 : prevItems) {
            for (OrderDetail temp2 : newItems) {
                if (temp1.getItemCode().equals(temp2.getItemCode()) && temp1.getOrderqty() != temp2.getOrderqty()) {
                    int prevQTYonhand = itemDAO.getItemQTYOnHand(temp1.getItemCode());
                    state = itemDAO.updateQTY(temp1.getItemCode(), prevQTYonhand + temp1.getOrderqty() - temp2.getOrderqty());
                }
            }
        }
        for (OrderDetail temp1 : prevItems) {
            for (CartTM temp2 : deletedItem) {
                if (temp1.getItemCode().equals(temp2.getItemId())) {
                    int prevQTYonhand = itemDAO.getItemQTYOnHand(temp1.getItemCode());
                    state = itemDAO.updateQTY(temp1.getItemCode(), prevQTYonhand + temp2.getQty());
                }
            }
        }
        orderDAO.delete(orderId);
        orderDetailDAO.delete(orderId);

        return state;

    }

    //
    *//*public void deleteOrder(String id) throws SQLException, ClassNotFoundException {
        DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Order` WHERE OrderID='"+id+"'").executeUpdate();
    }
*//*
    //
    *//*public void deleteItem(String id) throws SQLException, ClassNotFoundException {
        DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM OrderDetail WHERE OrderID='"+id+"'").executeUpdate();
    }*//*

    //
    *//*public ArrayList<Order> getAllOrders(String custid) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM `Order` WHERE CustID='"+custid+"'").executeQuery();
        ArrayList<Order> orders = new ArrayList<>();
        while (rst.next()) {
            orders.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    null)

            );
        }
        return orders;
    }*//*

    //Pass a array by entering all sells that gose from a specific item
    *//*public ArrayList<ItemSells> selectAllItemSell() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT ItemCode,count(ItemCode) from OrderDetail group by ItemCode;");
        ResultSet rst = stm.executeQuery();
        ArrayList<ItemSells> itemsList = new ArrayList<>();
        while (rst.next()){
            itemsList.add(new ItemSells(
                    rst.getString(1),
                    rst.getInt(2)
            ));
        }
        return itemsList;
    }*//*

    *//*public ArrayList<CustIncomeDTO> cutomerIncome() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT CustID,SUM(cost) From `Order` GROUP BY CustID");
        ResultSet rst = stm.executeQuery();
        ArrayList<CustIncomeDTO> custList = new ArrayList<>();
        while (rst.next()){
            custList.add(new CustIncomeDTO(
                    rst.getString(1),
                    rst.getDouble(2)
            ));
        }
        return custList;

    }*//*

    *//*public ArrayList<MounthlyIncome> mounthlyIncome() throws SQLException, ClassNotFoundException {
        ArrayList<MounthlyIncome> income = new ArrayList<>();
        for (String month : monthlist) {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(TotalPrice) From `Order` WHERE monthname(DeliveryDate)='" + month + "'");
            ResultSet rst = stm.executeQuery();
            while (rst.next()){
                income.add(new MounthlyIncome(month,rst.getDouble(1)));
            }
        }
        return income;
    }*//*
*/
}
