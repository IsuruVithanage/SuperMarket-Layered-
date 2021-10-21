package dao;

import db.DbConnection;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT CustID FROM Customer ORDER BY CustID DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString(1);
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C%03d", newCustomerId);
        } else {
            return "C001";
        }
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
        return CrudUtil.executeUpdate("DELETE FROM Customer WHERE id=?", s);
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Customer search(String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE id=?", s);
        rst.next();
        return new Customer(
                s,
                rst.getString(2),
                rst.getString(3),
                rst.getString(4),
                rst.getString(5),
                rst.getString(6),
                rst.getString(7)

        );
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        while (rst.next()) {
            allCustomers.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }
        return allCustomers;
    }
}
