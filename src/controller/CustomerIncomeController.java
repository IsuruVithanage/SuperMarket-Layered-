package controller;

import dao.custom.impl.OrderDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CustIncome;
import model.ItemTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerIncomeController {
    public TableView tblCust;
    public TableColumn colCustID;
    public TableColumn colIncome;


    public void initialize(){

        colCustID.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colIncome.setCellValueFactory(new PropertyValueFactory<>("unitePrice"));
        loadData();
    }

    ObservableList<ItemTM> obList = FXCollections.observableArrayList();
    private void loadData() {
        try {
            ArrayList<CustIncome> custList=new OrderDAOImpl().cutomerIncome();

            custList.forEach(e->{
                ItemTM tm = new ItemTM(e.getCustId(),null,null,0,e.getIncome(),7,null);
                obList.add(tm);

            });
            tblCust.setItems(obList);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
