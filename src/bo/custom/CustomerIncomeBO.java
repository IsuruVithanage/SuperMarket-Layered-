package bo.custom;

import bo.SuperBO;
import dto.CustIncomeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerIncomeBO extends SuperBO {
    ArrayList<CustIncomeDTO> getCustomerIncome() throws SQLException, ClassNotFoundException;
}
