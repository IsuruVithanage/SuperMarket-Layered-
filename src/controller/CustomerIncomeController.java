package controller;

import bo.custom.CustomerIncomeBO;
import bo.custom.impl.CustomerIncomeBOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CustIncomeDTO;
import view.tm.CustomerIncomeTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerIncomeController {
    private final ObservableList<CustomerIncomeTM> obList = FXCollections.observableArrayList();
    private final CustomerIncomeBO cutomerIncome = new CustomerIncomeBOImpl();
    public TableView<CustomerIncomeTM> tblCust;
    public TableColumn<CustomerIncomeTM, String> colCustID;
    public TableColumn<CustomerIncomeTM, String> colIncome;

    public void initialize() {

        colCustID.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colIncome.setCellValueFactory(new PropertyValueFactory<>("unitePrice"));
        loadData();
    }

    private void loadData() {
        try {
            ArrayList<CustIncomeDTO> custList = cutomerIncome.getCustomerIncome();

            custList.forEach(e -> {
                CustomerIncomeTM tm = new CustomerIncomeTM(e.getCustId(), e.getIncome());
                obList.add(tm);

            });
            tblCust.setItems(obList);


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
}
