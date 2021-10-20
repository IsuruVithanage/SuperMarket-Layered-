package dao;

import db.DbConnection;
import model.Customer;
import model.Item;
import model.ItemDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemController {

    //Save Item Details in customer table
    public boolean saveItem(Item i) throws SQLException, ClassNotFoundException {
        String query="INSERT INTO Item VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(query);
        stm.setObject(1,i.getItemCode());
        stm.setObject(2,i.getDescription());
        stm.setObject(3,i.getPackSize());
        stm.setObject(4,i.getQtyOnHand());
        stm.setObject(5,i.getUnitePrice());
        stm.setObject(6,i.getItemdiscount());

        return stm.executeUpdate()>0;
    }

    //Pass all item IDs to load in the combobox
    public List<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Item").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    //Generate Item Id
    public String creatItemId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT ItemCode FROM Item ORDER BY ItemCode DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "I-00"+tempId;
            }else if(tempId<=99){
                return "I-0"+tempId;
            }else{
                return "I-"+tempId;
            }

        }else{
            return "I-001";
        }
    }

    //Pass the specific item object
    public Item getItem(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Item WHERE ItemCode='" + id + "'").
                executeQuery();
        if (rst.next()){
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5),
                    rst.getDouble(6)
            );
        }else{
            return null;
        }
    }

    public ArrayList<Item> getAllItem() throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item");
        ResultSet rst = stm.executeQuery();
        ArrayList<Item> items = new ArrayList<>();
        while (rst.next()) {
            items.add(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5),
                    rst.getDouble(6)
            ));
        }
        return items;
    }

    public void deleteItem(String id) throws SQLException, ClassNotFoundException {
        DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Item WHERE ItemCode='"+id+"'").executeUpdate();
    }

    public boolean searchItem(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item WHERE ItemCode=?");
        stm.setObject(1,id);
        ResultSet rst = stm.executeQuery();

        while (rst.next()){
            return true;
        }
        return false;
    }

    //Select data from item ID to get the item sells
    public ArrayList<ItemDetails>  selectItemsell(String itemId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM OrderDetail WHERE ItemCode=?");
        stm.setObject(1, itemId);

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
}
