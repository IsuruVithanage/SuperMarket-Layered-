package bo.custom;

import model.CustIncomeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerIncomeBO {
    ArrayList<CustIncomeDTO> getCustomerIncome() throws SQLException, ClassNotFoundException;
}
