package controller;

import dao.OrderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ItemTM;
import model.MounthlyIncome;

import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;

public class ItemSellsController {
    public TableView tblSells;
    public TableColumn colId;
    public TableColumn colSells;

    public void initialize(){

        colId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colSells.setCellValueFactory(new PropertyValueFactory<>("unitePrice"));
        loadData();
    }

    ObservableList<ItemTM> obList = FXCollections.observableArrayList();
    private void loadData() {
        try {
            ArrayList<MounthlyIncome> itemList=new OrderController().mounthlyIncome();

            itemList.forEach(e->{
                ItemTM tm = new ItemTM(new DateFormatSymbols().getMonths()[e.getMounth()].toString(),null,null,0,e.getIncome(),7,null);
                obList.add(tm);

                });
            tblSells.setItems(obList);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
