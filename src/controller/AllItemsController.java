package controller;

import dao.custom.ItemDAO;
import dao.custom.impl.ItemDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Item;
import model.ItemTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class AllItemsController {
    public AnchorPane contextItem;
    public TableView tblItem;
    public TableColumn colID;
    public TableColumn colDesc;
    public TableColumn colSize;
    public TableColumn colQTY;
    public TableColumn colPrice;
    public TableColumn colDiscount;
    public TableColumn colDelete;
    private ItemDAO itemDAO=new ItemDAOImpl();

    public void initialize(){

        colID.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitePrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("itemdiscount"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));

        try {
            setItemToTable(itemDAO.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setItemToTable(ArrayList<Item> customers) {
        ObservableList<ItemTM> obList = FXCollections.observableArrayList();
        customers.forEach(e->{
            Button btn = new Button("Delete");
            ItemTM tm = new ItemTM(e.getItemCode(),e.getDescription(),e.getPackSize(),e.getQtyOnHand(),e.getUnitePrice(),e.getItemdiscount(),btn);
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
                        itemDAO.delete(tm.getItemCode());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    obList.remove(tm);
                    tblItem.refresh();

                }else if(result.get() == ButtonType.CANCEL) {
                    // cancel button is pressed
                }

            });
        });
        tblItem.setItems(obList);
    }
}
