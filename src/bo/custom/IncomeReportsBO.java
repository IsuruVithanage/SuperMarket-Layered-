package bo.custom;

import bo.SuperBO;
import dto.ItemSellsDTO;
import dto.MounthlyIncomeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IncomeReportsBO extends SuperBO {
    ArrayList<ItemSellsDTO> selectAllItemSell() throws SQLException, ClassNotFoundException;

    ArrayList<MounthlyIncomeDTO> mounthlyIncome() throws SQLException, ClassNotFoundException;
}
