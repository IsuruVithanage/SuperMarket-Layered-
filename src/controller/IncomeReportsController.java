package controller;

import dao.custom.impl.OrderDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.ItemSells;
import model.MounthlyIncome;
import model.MyComparator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IncomeReportsController {
    public AnchorPane contextAdmin;
    public BarChart tblChart;
    public Label mostItemId;
    public Label mostItemSell;
    public Label leastItemId;
    public Label leastItemSell;
    public BarChart tblIncom;

    public void initialize(){
        try {
            loadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminWindow.fxml");
        Parent load = FXMLLoader.load(resource);
        contextAdmin.getChildren().clear();
        contextAdmin.getChildren().add(load);

    }

    public void loadData() throws SQLException, ClassNotFoundException {
        List<ItemSells> itemSells=new OrderDAOImpl().selectTopItem();
        Collections.sort(itemSells,new MyComparator());
        ArrayList<MounthlyIncome> mounth=new OrderDAOImpl().mounthlyIncome();

        mostItemId.setText(itemSells.get(0).getItemId());
        mostItemSell.setText(String.valueOf(itemSells.get(0).getSell()));
       // leastItemId.setText(String.valueOf(itemSells.get(itemSells.size()-1).getItemId()));
        //leastItemSell.setText(String.valueOf(itemSells.get(itemSells.size()-1).getSell()));

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
}
