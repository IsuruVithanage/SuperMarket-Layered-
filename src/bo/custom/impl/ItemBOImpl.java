package bo.custom.impl;

import bo.custom.ItemBO;
import dao.custom.ItemDAO;
import dao.custom.impl.ItemDAOImpl;
import model.Item;
import model.ItemDTO;
import model.ItemSells;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    private final ItemDAO itemDAO = new ItemDAOImpl();

    @Override
    public boolean ifItemExist(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.ifItemExist(id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewID();
    }

    @Override
    public List<String> getItemIds() throws SQLException, ClassNotFoundException {
        return itemDAO.getAllItemIds();
    }

    @Override
    public boolean addItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(
                itemDTO.getItemCode(),
                itemDTO.getDescription(),
                itemDTO.getPackSize(),
                itemDTO.getQtyOnHand(),
                itemDTO.getUnitePrice(),
                itemDTO.getItemdiscount()
        ));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException {
        Item i = itemDAO.search(id);
        return new ItemDTO(
                i.getItemCode(),
                i.getDescription(),
                i.getPackSize(),
                i.getQtyOnHand(),
                i.getUnitePrice(),
                i.getItemdiscount()
        );
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item i : items) {
            itemDTOS.add(new ItemDTO(
                    i.getItemCode(),
                    i.getDescription(),
                    i.getPackSize(),
                    i.getQtyOnHand(),
                    i.getUnitePrice(),
                    i.getItemdiscount()
            ));
        }
        return itemDTOS;
    }

    @Override
    public boolean updateQTY(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return itemDAO.updateQTY(itemCode, qty);
    }

    @Override
    public ArrayList<ItemSells> selectAllItemSell() throws SQLException, ClassNotFoundException {
        return itemDAO.selectAllItemSell();
    }

    @Override
    public int getItemQTYOnHand(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.getItemQTYOnHand(id);
    }
}
