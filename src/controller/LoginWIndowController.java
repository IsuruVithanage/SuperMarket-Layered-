package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class LoginWIndowController {
    public JFXPasswordField password;
    public JFXTextField txtusername;
    public AnchorPane contextLog;

    public void login(ActionEvent actionEvent) throws IOException {

        //If the password 123 open the admin window else open the cashier window
        if ("123".equals(password.getText().toString())) {
            URL resource = getClass().getResource("../view/AdminWindow.fxml");
            Parent load = FXMLLoader.load(resource);
            contextLog.getChildren().clear();
            contextLog.getChildren().add(load);
        }else {
            URL resource = getClass().getResource("../view/CashierWindow.fxml");
            Parent load = FXMLLoader.load(resource);
            contextLog.getChildren().clear();
            contextLog.getChildren().add(load);
        }

    }
}
