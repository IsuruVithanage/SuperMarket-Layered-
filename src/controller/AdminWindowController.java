package controller;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import util.LoadFXMLFile;

import java.io.IOException;

public class AdminWindowController {

    public AnchorPane contextAdmin;

    public void openManageItems(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.load("ManageItem", contextAdmin);
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.loadOnTheCurrentPane("LoginWIndow", contextAdmin);
    }

    public void oprnSystemReport(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.load("SystemReports", contextAdmin);
    }

}
