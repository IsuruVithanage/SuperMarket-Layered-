package controller;

import bo.BoFactory;
import bo.custom.IncomeReportsBO;
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

public class IncomeReportsController {
    private final IncomeReportsBO incomeReportsBO = (IncomeReportsBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.INCOME_REPORT);
    public AnchorPane contextAdmin;
    public BarChart tblChart;
    public Label mostItemId;
    public Label mostItemSell;
    public Label leastItemId;
    public Label leastItemSell;
    public BarChart tblIncom;

    public void initialize() {
        try {
            loadData();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.loadOnTheCurrentPane("AdminWindow", contextAdmin);

    }

    public void loadData() throws SQLException, ClassNotFoundException {
        List<ItemSellsDTO> itemSells = incomeReportsBO.selectAllItemSell();
        itemSells.sort(new MyComparator());
        ArrayList<MounthlyIncomeDTO> mounth = incomeReportsBO.mounthlyIncome();

        mostItemId.setText(itemSells.get(0).getItemId());
        mostItemSell.setText(String.valueOf(itemSells.get(0).getSell()));
        // leastItemId.setText(String.valueOf(itemSells.get(itemSells.size()-1).getItemId()));
        //leastItemSell.setText(String.valueOf(itemSells.get(itemSells.size()-1).getSell()));

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
}
