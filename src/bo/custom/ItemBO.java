package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;
import dto.ItemSellsDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ItemBO extends SuperBO {
    boolean ifItemExist(String id) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

    List<String> getItemIds() throws SQLException, ClassNotFoundException;

    boolean addItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    boolean updateQTY(String itemCode, int qty) throws SQLException, ClassNotFoundException;

    ArrayList<ItemSellsDTO> selectAllItemSell() throws SQLException, ClassNotFoundException;

    int getItemQTYOnHand(String id) throws SQLException, ClassNotFoundException;


}
