package bo.custom.impl;

import bo.custom.IncomeReportsBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dto.ItemSellsDTO;
import dto.MounthlyIncomeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class IncomeReportsBOImpl implements IncomeReportsBO {
    private final OrderDAO orderDAO=(OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final ItemDAO itemDAO=(ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<ItemSellsDTO> selectAllItemSell() throws SQLException, ClassNotFoundException {
        return itemDAO.selectAllItemSell();
    }

    @Override
    public ArrayList<MounthlyIncomeDTO> mounthlyIncome() throws SQLException, ClassNotFoundException {
        return orderDAO.mounthlyIncome();
    }
}
