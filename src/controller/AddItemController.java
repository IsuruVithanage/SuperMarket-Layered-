package controller;

import bo.custom.ItemBO;
import bo.custom.impl.ItemBOImpl;
import com.jfoenix.controls.JFXTextField;
import dao.custom.ItemDAO;
import dao.custom.impl.ItemDAOImpl;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.ItemDTO;
import view.tm.ItemTM;
import util.LoadFXMLFile;

import java.io.IOException;
import java.sql.SQLException;

public class AddItemController {
    private final ItemDAO itemDAO = new ItemDAOImpl();
    private final ItemBO itemBO = new ItemBOImpl();
    public JFXTextField txtDesc;
    public JFXTextField txtqtyOnHand;
    public JFXTextField txtpackSize;
    public JFXTextField txtunitPrice;
    public Label itemCode;
    public JFXTextField txtDiscount;
    public AnchorPane contextaddItem;

    public void initialize() {
        setItemCode();
    }

    public void addItem(ActionEvent actionEvent) {
        ItemDTO item = new ItemDTO(itemCode.getText(), txtDesc.getText(), txtpackSize.getText(), Integer.parseInt(txtqtyOnHand.getText()), Double.parseDouble(txtunitPrice.getText()), Double.parseDouble(txtDiscount.getText()));

        try {
            if (itemBO.ifItemExist(itemCode.getText())) {
                itemBO.deleteItem(itemCode.getText());
                itemBO.addItem(item);
            } else {
                try {
                    if (itemBO.addItem(item)) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();

                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try Again..").show();
                    }

                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                setItemCode();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        setItemCode();
    }

    public void done(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.load("ManageItem", contextaddItem);
    }

    //Set generated Item ID
    private void setItemCode() {
        try {
            itemCode.setText(itemBO.generateNewID());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void loadData(ItemTM tm) {
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
