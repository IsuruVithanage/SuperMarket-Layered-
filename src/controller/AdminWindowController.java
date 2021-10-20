package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AdminWindowController {


    public AnchorPane contextAdmin;

    public void openManageItems(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageItem.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)contextAdmin.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginWIndow.fxml");
        Parent load = FXMLLoader.load(resource);
        contextAdmin.getChildren().clear();
        contextAdmin.getChildren().add(load);
    }

    public void oprnSystemReport(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SystemReports.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)contextAdmin.getScene().getWindow();
        window.setScene(new Scene(load));
    }

}
