package dao;

import db.DbConnection;
import javafx.collections.ObservableList;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderController {

    //Save data in order Table
    public boolean placeOrder(Order order){
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
    }

    //Save Data in OrderDetail table
    private boolean saveOrderDetail(String orderId, ArrayList<ItemDetails> items) throws SQLException, ClassNotFoundException {
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
    }

    //Update item QTY on hand
    private boolean updateQTY(String itemCode,int qty) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Item SET QtyOnHand=(QtyOnHand-"+qty+")WHERE  ItemCode='"+itemCode+"'");
        return stm.executeUpdate()>0;
    }

    //Generate Order Id
    public String creatOrderId() throws SQLException, ClassNotFoundException {
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
    }

    //Pass All Order IDs using Arraylist
    public List<String> getOrderIds(String id) throws SQLException, ClassNotFoundException {
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
    }

    public int getItemQTYOnHand(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item WHERE ItemCode=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        int QTYOnHand = 0;
        while (rst.next()){
                    QTYOnHand = rst.getInt(4);

        }
        return QTYOnHand;
    }

    public ArrayList<ItemDetails>  selectOrder(String orderId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM OrderDetail WHERE OrderId=?");
        stm.setObject(1, orderId);

        ResultSet rst = stm.executeQuery();

        ArrayList<ItemDetails> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(new ItemDetails(
                    rst.getString(1),
                    rst.getInt(3),
                    rst.getDouble(5),
                    rst.getDouble(4))
            );
        }
        return ids;
    }

    public boolean updateOrder(String orderId, ArrayList<ItemDetails> newItems, ObservableList<CartTM> deletedItem) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDetails> prevItems = selectOrder(orderId);
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Item SET QtyOnHand=? WHERE ItemCode=?");
        for (ItemDetails temp1:prevItems) {
            for (ItemDetails temp2:newItems) {
                if (temp1.getItemID().equals(temp2.getItemID())&&temp1.getQty()!=temp2.getQty()){
                    int prevQTYonhand = getItemQTYOnHand(temp1.getItemID());
                    stm.setObject(1,prevQTYonhand+temp1.getQty()-temp2.getQty());
                    stm.setObject(2,temp1.getItemID());

                }
            }
        }
        for (ItemDetails temp1:prevItems) {
            for (CartTM temp2:deletedItem) {
                if (temp1.getItemID().equals(temp2.getItemId())){
                    int prevQTYonhand = getItemQTYOnHand(temp1.getItemID());
                    stm.setObject(1,prevQTYonhand+temp2.getQty());
                    stm.setObject(2,temp1.getItemID());

                }
            }
        }
        deleteOrder(orderId);
        deleteItem(orderId);

        return stm.executeUpdate()>0;

    }

    public void deleteOrder(String id) throws SQLException, ClassNotFoundException {
        DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Order` WHERE OrderID='"+id+"'").executeUpdate();
    }

    public void deleteItem(String id) throws SQLException, ClassNotFoundException {
        DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM OrderDetail WHERE OrderID='"+id+"'").executeUpdate();
    }

    public ArrayList<Order> getAllOrders(String custid) throws SQLException, ClassNotFoundException {
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
    }

    //Pass a array by entering all sells that gose from a specific item
    public ArrayList<ItemSells> selectTopItem() throws SQLException, ClassNotFoundException {
        List<String> itemId = new ItemController().getAllItemIds();
        ArrayList<ItemSells> itemsList = new ArrayList<>();

        for (String id:itemId) {
            itemsList.add(new ItemSells(id,0));
        }
        ArrayList<ItemDetails> itemsell = null;


        for (int j = 0; j <itemId.size() ; j++) {
            itemsell = new ItemController().selectItemsell(itemId.get(j));
            for (int i = 0; i < itemsell.size(); i++) {
                int sell = itemsList.get(j).getSell();
                itemsList.get(j).setSell(sell + itemsell.get(i).getQty());
            }
        }
        return itemsList;


    }

    public ArrayList<CustIncome> cutomerIncome() throws SQLException, ClassNotFoundException {
        List<String> custId = new CustomerController().getCustomerIds();
        ArrayList<CustIncome> custList = new ArrayList<>();

        for (String id:custId) {
            custList.add(new CustIncome(id,0));
        }
        ArrayList<Order> orders = null;

        for (int j = 0; j <custId.size() ; j++) {
            orders = getAllOrders(custId.get(j));
            for (int i = 0; i < orders.size(); i++) {
                double sell = custList.get(j).getIncome();
                custList.get(j).setIncome(sell + orders.get(i).getCost());
            }
        }
        return custList;

    }

    public ArrayList<MounthlyIncome> mounthlyIncome() throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order`");
        ResultSet rst = stm.executeQuery();
        ArrayList<MounthlyIncome> income = new ArrayList<>();
        ArrayList<Order> order = new ArrayList<>();

        for (int i = 0; i < 13; i++) {
            income.add(new MounthlyIncome(i,0));
        }

        while (rst.next()) {
            order.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    null
            ));

        }

        for (Order o:order) {
            String[] d=o.getOrderDate().split("-");
            System.out.println(d[1]);
            for (int i = 0; i < income.size(); i++) {
                if (Integer.parseInt(d[1])==i+1){
                    income.get(i).setIncome(income.get(i).getIncome()+o.getCost());
                }
            }
        }
        return income;
    }

}
