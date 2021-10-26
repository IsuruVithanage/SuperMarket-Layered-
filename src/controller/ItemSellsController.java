package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ItemTM;
import model.MounthlyIncome;

import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;

public class ItemSellsController {
    public TableView<ItemTM> tblSells;
    public TableColumn<ItemTM, String> colId;
    public TableColumn<ItemTM, String> colSells;
    ObservableList<ItemTM> obList = FXCollections.observableArrayList();

    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colSells.setCellValueFactory(new PropertyValueFactory<>("unitePrice"));
        loadData();
    }

    private void loadData() {
        try {
            ArrayList<MounthlyIncome> itemList = new OrderController().mounthlyIncome();

            itemList.forEach(e -> {
                ItemTM tm = new ItemTM(new DateFormatSymbols().getMonths()[e.getMounth()], null, null, 0, e.getIncome(), 7, null);
                obList.add(tm);

            });
            tblSells.setItems(obList);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
}
