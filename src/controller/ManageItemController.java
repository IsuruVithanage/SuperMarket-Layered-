package controller;

import bo.BoFactory;
import bo.custom.ItemBO;
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
import dto.ItemDTO;
import util.LoadFXMLFile;
import view.tm.ItemTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ManageItemController {
    private final ObservableList<ItemTM> obList = FXCollections.observableArrayList();
    private final ItemBO itemBO = (ItemBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);
    public TableView<ItemTM> tblItem;
    public TableColumn<ItemTM, String> colID;
    public TableColumn<ItemTM, String> colDesc;
    public TableColumn<ItemTM, String> colSize;
    public TableColumn<ItemTM, String> colPrice;
    public TableColumn<ItemTM, String> colQTY;
    public TableColumn<ItemTM, String> colDiscount;
    public TableColumn<ItemTM, String> colDelete;
    public Label itemCount;
    public AnchorPane contextManage;

    public void initialize() {

        colID.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitePrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("itemdiscount"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));


        try {
            setItemToTable(itemBO.getAllItems());
            itemCount.setText(String.valueOf(itemBO.getAllItems().size()));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        tblItem.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            try {
                updatItem((Integer) newValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setItemToTable(ArrayList<ItemDTO> items) {

        items.forEach(e -> {
            Button btn = new Button("Delete");
            ItemTM tm = new ItemTM(e.getItemCode(), e.getDescription(), e.getPackSize(), e.getQtyOnHand(), e.getUnitePrice(), e.getItemdiscount(), btn);
            obList.add(tm);
            btn.setOnAction((ei) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("Are you sure");
                alert.setContentText("Select okay or cancel this alert.");
                Optional<ButtonType> result = alert.showAndWait();
                if (!result.isPresent()) {
                    // alert is exited, no button has been pressed.
                } else if (result.get() == ButtonType.OK) {

                    try {
                        itemBO.deleteItem(tm.getItemCode());
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    obList.remove(tm);
                    tblItem.refresh();

                } else if (result.get() == ButtonType.CANCEL) {
                    // cancel button is pressed
                }

            });
        });
        tblItem.setItems(obList);
    }

    public void back(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.load("AdminWindow", contextManage);
    }

    public void updatItem(int index) throws IOException {
        if (index == -1) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            ItemTM tm = obList.get(index);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AddItem.fxml"));
            Parent parent = loader.load();
            AddItemController controller = loader.getController();
            controller.loadData(tm);
            Stage window = (Stage) contextManage.getScene().getWindow();
            window.close();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        }
    }

    public void openAddItem(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.load("AddItem", contextManage);

    }
}
