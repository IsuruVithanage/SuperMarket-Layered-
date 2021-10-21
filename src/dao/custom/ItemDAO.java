package dao.custom;

import dao.CrudDAO;
import model.Item;
import model.ItemDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item,String> {
    boolean ifItemExist(String code) throws SQLException, ClassNotFoundException;
    String generateNewID() throws SQLException, ClassNotFoundException;
    List<String> getAllItemIds() throws SQLException, ClassNotFoundException;
    ArrayList<ItemDetails> selectItemsell(String itemId) throws SQLException, ClassNotFoundException;
}
