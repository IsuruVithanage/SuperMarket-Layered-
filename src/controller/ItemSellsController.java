package controller;

import bo.BoFactory;
import bo.custom.IncomeReportsBO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.MounthlyIncomeDTO;
import view.tm.MonthlyIncomeTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemSellsController {
    private final ObservableList<MonthlyIncomeTM> obList = FXCollections.observableArrayList();
    private final IncomeReportsBO incomeReportsBO = (IncomeReportsBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.INCOME_REPORT);
    public TableView<MonthlyIncomeTM> tblSells;
    public TableColumn<MonthlyIncomeTM, String> colId;
    public TableColumn<MonthlyIncomeTM, String> colSells;

    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colSells.setCellValueFactory(new PropertyValueFactory<>("unitePrice"));
        loadData();
    }

    private void loadData() {
        try {
            ArrayList<MounthlyIncomeDTO> itemList = incomeReportsBO.mounthlyIncome();

            itemList.forEach(e -> {
                MonthlyIncomeTM tm = new MonthlyIncomeTM(e.getMounth(), e.getIncome());
                obList.add(tm);

            });
            tblSells.setItems(obList);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
}
