package bo.custom.impl;

import bo.custom.CustomerIncomeBO;
import dao.DAOFactory;
import dao.custom.OrderDAO;
import dto.CustIncomeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerIncomeBOImpl implements CustomerIncomeBO {
    private final OrderDAO orderDAO= (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public ArrayList<CustIncomeDTO> getCustomerIncome() throws SQLException, ClassNotFoundException {
        return orderDAO.cutomerIncome();
    }
}
