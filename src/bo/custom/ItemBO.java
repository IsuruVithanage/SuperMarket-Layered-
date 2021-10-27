package bo.custom;

import model.ItemDTO;
import model.ItemSells;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ItemBO {
    boolean ifItemExist(String id) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

    List<String> getItemIds() throws SQLException, ClassNotFoundException;

    boolean addItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    boolean updateQTY(String itemCode, int qty) throws SQLException, ClassNotFoundException;

    ArrayList<ItemSells> selectAllItemSell() throws SQLException, ClassNotFoundException;

    int getItemQTYOnHand(String id) throws SQLException, ClassNotFoundException;


}
