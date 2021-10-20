package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CashierWindowController {
    public AnchorPane contextCashier;

    public void logout(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginWIndow.fxml");
        Parent load = FXMLLoader.load(resource);
        contextCashier.getChildren().clear();
        contextCashier.getChildren().add(load);
    }

    public void openManageOrder(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageOrder.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)contextCashier.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void openMakeCustomerOrder(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MakeCustomerOrder.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)contextCashier.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
