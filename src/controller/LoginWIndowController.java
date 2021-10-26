package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import util.LoadFXMLFile;

import java.io.IOException;

public class LoginWIndowController {
    public JFXPasswordField password;
    public JFXTextField txtusername;
    public AnchorPane contextLog;

    public void login(ActionEvent actionEvent) throws IOException {

        //If the password 123 open the admin window else open the cashier window
        if ("123".equals(password.getText())) {
            LoadFXMLFile.loadOnTheCurrentPane("AdminWindow", contextLog);
        } else {
            LoadFXMLFile.loadOnTheCurrentPane("CashierWindow", contextLog);
        }

    }
}
