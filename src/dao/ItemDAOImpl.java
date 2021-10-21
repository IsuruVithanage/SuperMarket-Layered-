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

public class ItemDAOImpl implements ItemDAO{

    @Override
    public List<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    /*public boolean searchItem(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item WHERE ItemCode=?");
        stm.setObject(1,id);
        ResultSet rst = stm.executeQuery();

        while (rst.next()){
            return true;
        }
        return false;
    }*/

    @Override
    public ArrayList<ItemDetails>  selectItemsell(String itemId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM OrderDetail WHERE ItemCode=?", itemId);
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

    @Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT Itemcode FROM Item ORDER BY Itemcode DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString(1);
            int newItemId = Integer.parseInt(id.replace("I", "")) + 1;
            return String.format("I%03d", newItemId);
        } else {
            return "I001";
        }
    }

    @Override
    public boolean add(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item (Itemcode, Description, PackSize, QtyOnHand, UnitPrice, ItemDiscount) VALUES (?,?,?,?,?,?)",
                item.getItemCode(),
                item.getDescription(),
                item.getPackSize(),
                item.getQtyOnHand(),
                item.getUnitePrice(),
                item.getItemdiscount()
        );
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE id=?", s);
    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Item search(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE id=?", s);
        rst.next();
        return new Item(
                s,
                rst.getString(2),
                rst.getString(3),
                rst.getInt(4),
                rst.getDouble(5),
                rst.getDouble(6)

        );
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItems = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        while (rst.next()) {
            allItems.add(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5),
                    rst.getDouble(6)
            ));
        }
        return allItems;
    }
}
