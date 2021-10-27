package bo.custom.impl;

import bo.custom.CustomerIncomeBO;
import dao.custom.OrderDAO;
import dao.custom.impl.OrderDAOImpl;
import model.CustIncomeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerIncomeBOImpl implements CustomerIncomeBO {
    private final OrderDAO orderDAO=new OrderDAOImpl();

    @Override
    public ArrayList<CustIncomeDTO> getCustomerIncome() throws SQLException, ClassNotFoundException {
        return orderDAO.cutomerIncome();
    }
}
