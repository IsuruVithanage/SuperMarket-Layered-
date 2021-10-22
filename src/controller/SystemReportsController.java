package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ItemSells;
import model.MounthlyIncome;
import model.MyComparator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.*;

public class SystemReportsController {
    public AnchorPane contextSys;
    public BarChart tblChart;
    public Label mostItemId;
    public Label mostItemSell;
    public Label leastItem;
    public Label leastSell;
    public BarChart tblIncom;
    public Label lblYearIncome;
    public JFXComboBox cmbYear;
    public Label lblMonthIncome;
    public JFXComboBox cmbMonth;


    public void initialize(){
        loadYers();
        loadMonth();

        try {
            loadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbYear.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    if (newValue.equals(2021)){
                        try {
                            lblYearIncome.setText(String.valueOf(annualIncome()));
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }else {
                        lblYearIncome.setText("0");
                    }
                });

        cmbMonth.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        lblMonthIncome.setText(String.valueOf(mounthlyIncome(newValue.toString())));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void loadData() throws SQLException, ClassNotFoundException {
        List<ItemSells> itemSells=new OrderController().selectTopItem();
        Collections.sort(itemSells,new MyComparator());
        ArrayList<MounthlyIncome> mounth=new OrderController().mounthlyIncome();

        mostItemId.setText(itemSells.get(0).getItemId());
        mostItemSell.setText(String.valueOf(itemSells.get(0).getSell()));
        leastItem.setText(String.valueOf(itemSells.get(itemSells.size()-1).getItemId()));
        leastSell.setText(String.valueOf(itemSells.get(itemSells.size()-1).getSell()));

        XYChart.Series set1=new XYChart.Series<>();
        for (ItemSells item:itemSells) {
            set1.getData().add(new XYChart.Data(item.getItemId(),item.getSell()));

        }
        tblChart.getData().addAll(set1);

        XYChart.Series set2=new XYChart.Series<>();
        for (MounthlyIncome m:mounth) {
            set2.getData().add(new XYChart.Data(new DateFormatSymbols().getMonths()[m.getMounth()],m.getIncome()));

        }
        tblIncom.getData().addAll(set2);

    }

    public void back(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminWindow.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)contextSys.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void customerwiseIncome(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/CustomerIncome.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void loadYers(){
        List<Integer> years=new ArrayList<Integer>();
        for (int i = 2021; i < 2026; i++) {
            years.add(i);
        }
        cmbYear.getItems().addAll(years);
    }

    public void loadMonth(){
        String months[] = {"January", "February", "March", "April",
                "May", "June", "July", "August", "September",
                "October", "November", "December"};

        cmbMonth.getItems().addAll(months);


    }

    public double annualIncome() throws SQLException, ClassNotFoundException {
        ArrayList<MounthlyIncome> mounth=new OrderController().mounthlyIncome();
        double yearTotal=0;

        for (MounthlyIncome m:mounth) {
            yearTotal+=m.getIncome();
        }

        return yearTotal;

    }

    public double mounthlyIncome(String mounthIndex) throws SQLException, ClassNotFoundException {
        ArrayList<MounthlyIncome> mounth=new OrderController().mounthlyIncome();
        double mounthTotal=0;
        int index= index(mounthIndex);

        for (MounthlyIncome m:mounth) {
            if (index-1==m.getMounth()){
                mounthTotal=m.getIncome();
            }
        }

        return mounthTotal;
    }

    public int index(String month){
        int monthString;

        switch (month) {
            case "January":  monthString = 1;       break;
            case "February":  monthString = 2;      break;
            case "March":  monthString = 3;         break;
            case "April":  monthString = 4;         break;
            case "May":  monthString = 5;           break;
            case "June":  monthString = 6;          break;
            case "July":  monthString = 7;          break;
            case "August":  monthString = 8;        break;
            case "September":  monthString = 9;     break;
            case "October": monthString = 10;       break;
            case "November": monthString = 11;      break;
            case "December": monthString = 12;      break;
            default: monthString = 0; break;
        }

        return monthString;
    }



}
