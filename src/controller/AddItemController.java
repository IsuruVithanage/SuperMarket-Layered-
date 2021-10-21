package controller;

import com.jfoenix.controls.JFXTextField;
import dao.ItemDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Item;
import model.ItemTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class AddItemController {
    public JFXTextField txtDesc;
    public JFXTextField txtqtyOnHand;
    public JFXTextField txtpackSize;
    public JFXTextField txtunitPrice;
    public Label itemCode;
    public JFXTextField txtDiscount;
    public AnchorPane contextaddItem;

    public void initialize(){
        setItemCode();
    }

    public void addItem(ActionEvent actionEvent) {
        Item item = new Item(itemCode.getText(), txtDesc.getText(), txtpackSize.getText(), Integer.parseInt(txtqtyOnHand.getText()), Double.parseDouble(txtunitPrice.getText()), Double.parseDouble(txtDiscount.getText()));

        try {
            if (new ItemDAOImpl().searchItem(itemCode.getText())) {
                new ItemDAOImpl().deleteItem(itemCode.getText());
                new ItemDAOImpl().saveItem(item);
            } else {
                try {
                    if (new ItemDAOImpl().saveItem(item)) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();

                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try Again..").show();
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                setItemCode();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setItemCode();
    }

        public void done(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageItem.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)contextaddItem.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    //Set generated Item ID
    private void setItemCode() {
        try {
            itemCode.setText(new ItemDAOImpl().creatItemId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadData(ItemTM tm){
        itemCode.setText(tm.getItemCode());
        txtDesc.setText(tm.getDescription());
        txtpackSize.setText(tm.getPackSize());
        txtqtyOnHand.setText(String.valueOf(tm.getQtyOnHand()));
        txtunitPrice.setText(String.valueOf(tm.getUnitePrice()));
        txtDiscount.setText(String.valueOf(tm.getItemdiscount()));

    }

    public void clear(ActionEvent actionEvent) {
        txtDesc.clear();
        txtpackSize.clear();
        txtqtyOnHand.clear();
        txtunitPrice.clear();
        txtDiscount.clear();
        setItemCode();
    }
}
