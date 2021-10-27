package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;
import dto.ItemSellsDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

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

    @Override
    public boolean updateQTY(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET QtyOnHand=(QtyOnHand-?) WHERE  ItemCode=?", qty, itemCode);
    }

    @Override
    public ArrayList<ItemSellsDTO> selectAllItemSell() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT ItemCode,count(ItemCode) from OrderDetail group by ItemCode");
        ArrayList<ItemSellsDTO> itemsList = new ArrayList<>();
        while (rst.next()) {
            itemsList.add(new ItemSellsDTO(
                    rst.getString(1),
                    rst.getInt(2)
            ));
        }
        return itemsList;
    }

    @Override
    public int getItemQTYOnHand(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE ItemCode=?", id);
        int QTYOnHand = 0;
        while (rst.next()) {
            QTYOnHand = rst.getInt(4);

        }
        return QTYOnHand;
    }

    @Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT ItemCode FROM Item WHERE ItemCode=?", code).next();
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
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE ItemCode=?", s);
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
