package controller;

import com.jfoenix.controls.JFXTextField;
import dao.custom.impl.CustomerDAOImpl;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import util.LoadFXMLFile;

import java.io.IOException;
import java.sql.SQLException;

public class AddCustomerController {

    public JFXTextField txtTitle;
    public JFXTextField txtCity;
    public JFXTextField txtCustName;
    public JFXTextField txtAddress;
    public JFXTextField txtProvince;
    public JFXTextField txtPostal;
    public Label CustID;
    public AnchorPane contextAddCust;

    public void initialize() {
        setCustId();
    }

    //Add a new Customer to customer Table
    public void addCustomer(ActionEvent actionEvent) {
        Customer customer = new Customer(CustID.getText(), txtTitle.getText(), txtCustName.getText(), txtAddress.getText(), txtCity.getText(), txtProvince.getText(), txtPostal.getText());
        try {
            if (new CustomerDAOImpl().add(customer)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        setCustId();

    }

    //Set generated Customer ID
    private void setCustId() {
        try {
            CustID.setText(new CustomerDAOImpl().generateNewID());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    //Done will go back to make Customer Window
    public void done(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.load("MakeCustomerOrder", contextAddCust);
    }

    public void clear(ActionEvent actionEvent) {
        txtTitle.clear();
        txtAddress.clear();
        txtCity.clear();
        txtCustName.clear();
        txtPostal.clear();
        setCustId();
    }
}
