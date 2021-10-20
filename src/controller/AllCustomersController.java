package controller;

import dao.CustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import model.CustomerTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class AllCustomersController {
    public AnchorPane contectCust;
    public TableView tblCust;
    public TableColumn colID;
    public TableColumn colTitle;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;
    public TableColumn colDelete;

    public void initialize(){

        colID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("customerTitle"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));

        try {
            setCustomersToTable(new CustomerController().getAllCustomer());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setCustomersToTable(ArrayList<Customer> customers) {
        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
        customers.forEach(e->{
            Button btn = new Button("Delete");
            CustomerTM tm = new CustomerTM(e.getCustomerId(),e.getCustomerTitle(),e.getCustomerName(),e.getCustomerAddress(),e.getCity(),e.getProvince(),e.getPostalCode(),btn);
            obList.add(tm);
            btn.setOnAction((ei)-> {
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("Are you sure");
                alert.setContentText("Select okay or cancel this alert.");
                Optional<ButtonType> result = alert.showAndWait();
                if(!result.isPresent()) {
                    // alert is exited, no button has been pressed.
                }else if(result.get() == ButtonType.OK) {

                    try {
                        new CustomerController().deleteCustomer(tm.getCustomerId());
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    obList.remove(tm);
                    tblCust.refresh();

                }else if(result.get() == ButtonType.CANCEL) {
                    // cancel button is pressed
                }

            });
        });
        tblCust.setItems(obList);
    }


}
