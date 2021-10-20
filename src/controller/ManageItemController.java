package controller;

import dao.ItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Item;
import model.ItemTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ManageItemController {
    public TableView tblItem;
    public TableColumn colID;
    public TableColumn colDesc;
    public TableColumn colSize;
    public TableColumn colPrice;
    public TableColumn colQTY;
    public TableColumn colDiscount;
    public TableColumn colDelete;
    public Label itemCount;
    public AnchorPane contextManage;

    public void initialize(){

        colID.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitePrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("itemdiscount"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));


        try {
            setItemToTable(new ItemController().getAllItem());
            itemCount.setText(String.valueOf(new ItemController().getAllItem().size()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblItem.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            try {
                updatItem((Integer) newValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    ObservableList<ItemTM> obList = FXCollections.observableArrayList();
    private void setItemToTable(ArrayList<Item> items) {

        items.forEach(e->{
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
                        new ItemController().deleteItem(tm.getItemCode());
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

    public void back(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminWindow.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)contextManage.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void updatItem(int index) throws IOException {
        if (index==-1){
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        }else{
            ItemTM tm = obList.get(index);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AddItem.fxml"));
            Parent parent = loader.load();
            AddItemController controller = (AddItemController) loader.getController();
            controller.loadData(tm);
            Stage window = (Stage)contextManage.getScene().getWindow();
            window.close();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        }
    }

    public void openAddItem(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddItem.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)contextManage.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
