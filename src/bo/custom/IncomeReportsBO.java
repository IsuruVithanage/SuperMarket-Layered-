package bo.custom;

import model.ItemSells;
import model.MounthlyIncome;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IncomeReportsBO {
    ArrayList<ItemSells> selectAllItemSell() throws SQLException, ClassNotFoundException;

    ArrayList<MounthlyIncome> mounthlyIncome() throws SQLException, ClassNotFoundException;
}
