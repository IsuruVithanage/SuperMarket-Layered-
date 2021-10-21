package dao;

import db.DbConnection;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    //Save Customer Details in customer table
    public boolean saveCustomer(Customer c) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Customer VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(query);
        stm.setObject(1, c.getCustomerId());
        stm.setObject(2, c.getCustomerTitle());
        stm.setObject(3, c.getCustomerName());
        stm.setObject(4, c.getCustomerAddress());
        stm.setObject(5, c.getCity());
        stm.setObject(6, c.getProvince());
        stm.setObject(7, c.getPostalCode());

        return stm.executeUpdate() > 0;
    }

    //Pass All customer IDs using Arraylist
    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Customer").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    //Generate Customer Id
    public String creatCustId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT CustID FROM Customer ORDER BY CustID DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "C-00" + tempId;
            } else if (tempId <= 99) {
                return "C-0" + tempId;
            } else {
                return "C-" + tempId;
            }

        } else {
            return "C-001";
        }
    }

    //Get a customer object passing ths customer id
    public Customer getCustomer(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer WHERE CustID=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();

        if (rst.next()) {
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );

        } else {
            return null;
        }
    }

    public ArrayList<Customer> getAllCustomer() throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer");
        ResultSet rst = stm.executeQuery();
        ArrayList<Customer> customer = new ArrayList<>();
        while (rst.next()) {
            customer.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }
        return customer;
    }

    public void deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Customer WHERE CustID='" + id + "'").executeUpdate();
    }

    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Customer (CustID, CustTitle, CustName, CustAddress, City, Province, PostalCode) VALUES (?,?,?,?,?,?,?)",
                customer.getCustomerId(),
                customer.getCustomerTitle(),
                customer.getCustomerName(),
                customer.getCustomerAddress(),
                customer.getCity(),
                customer.getProvince(),
                customer.getPostalCode());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Customer search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
