package controller;

import bo.BoFactory;
import bo.custom.IncomeReportsBO;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import dto.ItemSellsDTO;
import dto.MounthlyIncomeDTO;
import dto.MyComparator;
import util.LoadFXMLFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SystemReportsController {
    private final IncomeReportsBO incomeReportsBO = (IncomeReportsBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.INCOME_REPORT);
    public AnchorPane contextSys;
    public BarChart tblChart;
    public Label mostItemId;
    public Label mostItemSell;
    public Label leastItem;
    public Label leastSell;
    public BarChart tblIncom;
    public Label lblYearIncome;
    public JFXComboBox<Integer> cmbYear;
    public Label lblMonthIncome;
    public JFXComboBox<String> cmbMonth;

    public void initialize() {
        loadYers();
        loadMonth();

        try {
            loadData();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        cmbYear.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    if (newValue.equals(2021)) {
                        try {
                            lblYearIncome.setText(String.valueOf(annualIncome()));
                        } catch (SQLException | ClassNotFoundException throwables) {
                            throwables.printStackTrace();
                        }
                    } else {
                        lblYearIncome.setText("0");
                    }
                });

        cmbMonth.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        lblMonthIncome.setText(String.valueOf(mounthlyIncome(newValue)));
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                });
    }

    private void loadData() throws SQLException, ClassNotFoundException {
        List<ItemSellsDTO> itemSells = incomeReportsBO.selectAllItemSell();
        itemSells.sort(new MyComparator());
        ArrayList<MounthlyIncomeDTO> mounth = incomeReportsBO.mounthlyIncome();

        mostItemId.setText(itemSells.get(0).getItemId());
        mostItemSell.setText(String.valueOf(itemSells.get(0).getSell()));
        leastItem.setText(String.valueOf(itemSells.get(itemSells.size() - 1).getItemId()));
        leastSell.setText(String.valueOf(itemSells.get(itemSells.size() - 1).getSell()));

        XYChart.Series set1 = new XYChart.Series<>();
        for (ItemSellsDTO item : itemSells) {
            set1.getData().add(new XYChart.Data(item.getItemId(), item.getSell()));

        }
        tblChart.getData().addAll(set1);

        XYChart.Series set2 = new XYChart.Series<>();
        for (MounthlyIncomeDTO m : mounth) {
            set2.getData().add(new XYChart.Data(m.getMounth(), m.getIncome()));

        }
        tblIncom.getData().addAll(set2);

    }

    public void back(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.load("AdminWindow", contextSys);
    }

    public void customerwiseIncome(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.loadOn("CustomerIncome");
    }

    public void loadYers() {
        List<Integer> years = new ArrayList<Integer>();
        for (int i = 2021; i < 2026; i++) {
            years.add(i);
        }
        cmbYear.getItems().addAll(years);
    }

    public void loadMonth() {
        String[] months = {"January", "February", "March", "April",
                "May", "June", "July", "August", "September",
                "October", "November", "December"};

        cmbMonth.getItems().addAll(months);
    }

    public double annualIncome() throws SQLException, ClassNotFoundException {
        ArrayList<MounthlyIncomeDTO> mounth = incomeReportsBO.mounthlyIncome();
        double yearTotal = 0;

        for (MounthlyIncomeDTO m : mounth) {
            yearTotal += m.getIncome();
        }

        return yearTotal;

    }

    public double mounthlyIncome(String month) throws SQLException, ClassNotFoundException {
        ArrayList<MounthlyIncomeDTO> incomes = incomeReportsBO.mounthlyIncome();
        double mounthTotal = 0;

        for (MounthlyIncomeDTO m : incomes) {
            if (month.equals(m.getMounth())) {
                mounthTotal = m.getIncome();
            }
        }

        return mounthTotal;
    }

}
