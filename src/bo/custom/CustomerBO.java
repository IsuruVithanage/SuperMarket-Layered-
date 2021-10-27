package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerBO extends SuperBO {
    boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

    List<String> getCustomerIds() throws SQLException, ClassNotFoundException;

    boolean addCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;
}
