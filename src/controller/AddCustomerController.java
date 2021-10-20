package controller;

import com.jfoenix.controls.JFXTextField;
import dao.CustomerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
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

    public void initialize(){
        setCustId();
    }

    //Add a new Customer to customer Table
    public void addCustomer(ActionEvent actionEvent)  {
        Customer customer=new Customer(CustID.getText(),txtTitle.getText(),txtCustName.getText(),txtAddress.getText(),txtCity.getText(),txtProvince.getText(),txtPostal.getText());

        try {
            if(new CustomerController().saveCustomer(customer)){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();

            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        setCustId();

    }

    //Set generated Customer ID
    private void setCustId() {
        try {
            CustID.setText(new CustomerController().creatCustId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Done will go back to make Customer Window
    public void done(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MakeCustomerOrder.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)contextAddCust.getScene().getWindow();
        window.setScene(new Scene(load));
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
