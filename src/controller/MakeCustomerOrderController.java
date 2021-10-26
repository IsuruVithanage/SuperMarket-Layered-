package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import dao.custom.impl.CustomerDAOImpl;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailDAOImpl;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.*;
import util.LoadFXMLFile;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MakeCustomerOrderController {
    static String custvalue = null;
    private final OrderDAO orderDAO = new OrderDAOImpl();
    private final ItemDAO itemDAO = new ItemDAOImpl();
    private final OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
    public AnchorPane context;
    public Label lbTime;
    public Label lbDate;
    public JFXComboBox<String> cmbCustID;
    public TextField txtName;
    public TextField txtAddress;
    public JFXComboBox<String> cmbItem;
    public TextField txtItemDesc;
    public TextField txtPackSize;
    public TextField txtQTYOnHand;
    public TextField txtUniteprice;
    public Label lbDiscount;
    public TextField txtqty;
    public TableColumn<CartTM, String> colItemID;
    public TableColumn<CartTM, String> colItemDesc;
    public TableColumn<CartTM, String> colQTY;
    public TableColumn<CartTM, String> colUnitPrice;
    public TableColumn<CartTM, String> colDiscount;
    public TableColumn<CartTM, String> colTotal;
    public TableColumn<CartTM, String> colDelete;
    public TableView<CartTM> tblCart;
    public Label lbTotal;
    public Label lbOrderId;
    public JFXButton btnPLaceOrder;
    public Label lbError;
    //Add the item in to the Table
    ObservableList<CartTM> obList = FXCollections.observableArrayList();

    public void initialize() {

        colItemID.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deletebtn"));

        loadDateAndTime();//Display Date and Time

        setorderId();

        btnPLaceOrder.setDisable(true);


        try {
            loadCustomerIds();//Load custIds to comboBox
            loadItemIds();//Load ItemIds to comboBox
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        //Select Customer
        cmbCustID.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    custvalue = newValue;
                    try {
                        tblCart.getItems().clear();
                        setCustomerData(newValue);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                });

        //Selecet Item
        cmbItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData(newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

        });
    }

    //Back to Cashier
    public void back(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.load("CashierWindow", context);

    }

    //Open Add customer Window
    public void addCustomer(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.load("AddCustomer", context);

    }

    //Open Customer Table window
    public void openCustomerTable(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.loadOn("AllCustomers");

    }

    //Open Item Table window
    public void openItemTable(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.loadOn("AllItems");
    }

    public void addToCart(ActionEvent actionEvent) {
        if (txtqty.getText().trim().isEmpty()) {
            lbError.setText("Empty text Fields");
            return;
        } else {
            lbError.setText("");
            btnPLaceOrder.setDisable(false);
            int qtyOnHand = Integer.parseInt(txtQTYOnHand.getText());
            double unitPrice = Double.parseDouble(txtUniteprice.getText());
            double discount = Double.parseDouble(lbDiscount.getText());
            int qtyForCustomer = Integer.parseInt(txtqty.getText());
            double discountPrice = (qtyForCustomer * unitPrice * discount) / 100;
            double total = (qtyForCustomer * unitPrice) - discountPrice;

            //Check the Qtyfror customer is greater than the qtyOnHand
            if (qtyOnHand < qtyForCustomer) {
                new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
                return;
            }

            //Make a new CartTM
            Button del = new Button("Delete");
            CartTM tm = new CartTM(
                    cmbItem.getValue(),
                    txtItemDesc.getText(),
                    qtyForCustomer,
                    unitPrice,
                    discount,
                    total,
                    del

            );

            txtQTYOnHand.setText(String.valueOf(qtyOnHand - qtyForCustomer));
            del.setOnAction((e) -> {
                obList.remove(tm);
                tblCart.refresh();
                calculateCost(obList, lbTotal);
            });

            //THis will check this item is already in the obList or not
            int rowNumber = isExists(tm, obList);

            if (rowNumber == -1) {// new Add
                obList.add(tm);
            } else {
                // update
                CartTM temp = obList.get(rowNumber);
                CartTM newTm = new CartTM(
                        temp.getItemId(),
                        temp.getDescription(),
                        temp.getQty() + qtyForCustomer,
                        unitPrice,
                        discount,
                        total + temp.getTotal(),
                        del
                );

                if (qtyOnHand < temp.getQty()) {
                    new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
                    return;
                }

                obList.remove(rowNumber);
                obList.add(newTm);
            }
            tblCart.setItems(obList);
            calculateCost(obList, lbTotal);
        }
    }

    //Calculate the Total
    public void calculateCost(ObservableList<CartTM> obList, Label lbTotal) {
        double total = 0;
        for (CartTM tm : obList
        ) {
            total += tm.getTotal();
        }
        lbTotal.setText(String.valueOf(total));
    }

    //Check whether their is equal item in th obList
    public int isExists(CartTM tm, ObservableList<CartTM> obList) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getItemId().equals(obList.get(i).getItemId())) {
                return i;
            }
        }
        return -1;
    }

    //Place the Order
    public void placeOrder(ActionEvent actionEvent) {
        ArrayList<OrderDetail> items = new ArrayList<>();
        for (CartTM tempTm : obList
        ) {
            items.add(new OrderDetail(tempTm.getItemId(), lbOrderId.getText(), tempTm.getQty(), tempTm.getDiscount(), tempTm.getUnitPrice()));
        }

        OrderDTO order = new OrderDTO(lbOrderId.getText(), cmbCustID.getValue(), lbDate.getText(), lbTime.getText(), Double.parseDouble(lbTotal.getText()), items);

        try {
            if (orderDAO.add(order) /*&& orderDetailDAO.add(order.getItems())*/) {
                new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
                setorderId();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        obList.clear();
        txtqty.clear();

    }

    //Display time and date
    public void loadDateAndTime() {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lbDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            String state = "AM";
            if (currentTime.getHour() >= 12) {
                state = "PM";
            }
            lbTime.setText(
                    String.format("%02d", currentTime.getHour()) + " : " + String.format("%02d", currentTime.getMinute()) +
                            " : " + String.format("%02d", currentTime.getSecond()) + " " + state
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    //Load all customer ids to the comboBox
    public void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds = new CustomerDAOImpl().getCustomerIds();
        cmbCustID.getItems().addAll(customerIds);

    }

    //Set customer data in to the text fields
    private void setCustomerData(String customerId) throws SQLException, ClassNotFoundException {
        Customer c1 = new CustomerDAOImpl().search(customerId);
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtName.setText(c1.getCustomerName());
            txtAddress.setText(c1.getCustomerAddress());
        }
    }

    //Set Item data in to the text fields
    public void setItemData(String itemCode) throws SQLException, ClassNotFoundException {
        Item i1 = itemDAO.search(itemCode);
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtItemDesc.setText(i1.getDescription());
            txtPackSize.setText(String.valueOf(i1.getPackSize()));
            txtQTYOnHand.setText(String.valueOf(i1.getQtyOnHand()));
            txtUniteprice.setText(String.valueOf(i1.getUnitePrice()));
            lbDiscount.setText(String.valueOf(i1.getItemdiscount()));
        }

    }

    public void addOrderDiscount(ActionEvent actionEvent) {
    }

    public void loadItemIds() throws SQLException, ClassNotFoundException {
        List<String> itemIds = new ItemDAOImpl().getAllItemIds();
        cmbItem.getItems().addAll(itemIds);

    }

    //Set generated Order ID
    private void setorderId() {
        try {
            lbOrderId.setText(orderDAO.generateNewOrderId());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
