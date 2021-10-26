package controller;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import util.LoadFXMLFile;

import java.io.IOException;

public class CashierWindowController {
    public AnchorPane contextCashier;

    public void logout(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.loadOnTheCurrentPane("LoginWIndow", contextCashier);
    }

    public void openManageOrder(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.load("ManageOrder", contextCashier);
    }

    public void openMakeCustomerOrder(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.load("MakeCustomerOrder", contextCashier);
    }
}
