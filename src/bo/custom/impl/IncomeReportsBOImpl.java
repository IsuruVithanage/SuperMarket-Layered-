package bo.custom.impl;

import bo.custom.IncomeReportsBO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import model.ItemSells;
import model.MounthlyIncome;

import java.sql.SQLException;
import java.util.ArrayList;

public class IncomeReportsBOImpl implements IncomeReportsBO {
    private final OrderDAO orderDAO=new OrderDAOImpl();
    private final ItemDAO itemDAO=new ItemDAOImpl();

    @Override
    public ArrayList<ItemSells> selectAllItemSell() throws SQLException, ClassNotFoundException {
        return itemDAO.selectAllItemSell();
    }

    @Override
    public ArrayList<MounthlyIncome> mounthlyIncome() throws SQLException, ClassNotFoundException {
        return orderDAO.mounthlyIncome();
    }
}
