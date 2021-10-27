package controller;

import bo.BoFactory;
import bo.custom.ManageOrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import entity.OrderDetail;
import util.LoadFXMLFile;
import view.tm.CartTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageOrderController extends MakeCustomerOrderController {
    //Load Items for specific order in the table
    private final ObservableList<CartTM> obList = FXCollections.observableArrayList();
    private final ObservableList<CartTM> detetedItem = FXCollections.observableArrayList();
    private final ManageOrderBO manageOrderBO = (ManageOrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.MANAGE_ORDER);
    public AnchorPane contextMO;
    public JFXComboBox<String> cmbItem;
    public TextField txtPackSize;
    public TextField txtQTYOnHand;
    public TextField txtUniteprice;
    public TextField txtqty;
    public Label lbDiscount;
    public JFXComboBox<String> cmbCustID;
    public JFXComboBox<String> cmbOrderID;
    public TableView<CartTM> tblCart;
    public TableColumn<Object, Object> colItemId;
    public TableColumn<Object, Object> colQTY;
    public TableColumn<Object, Object> colUnitPrice;
    public TableColumn<Object, Object> colDiscount;
    public TableColumn<Object, Object> colTotal;
    public TableColumn<Object, Object> colRemove;
    public Label lbTotal;
    public TextField tctName;
    public JFXButton btnEditeOrder;

    public void initialize() {

        loadDateAndTime();
        btnEditeOrder.setDisable(true);

        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("deletebtn"));

        try {
            loadCustomerIds();//Load custIds to comboBox inherit from MakeCustomerOrderController
            loadItemIds();//Load ItemIds to comboBox inherit from MakeCustomerOrderController
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        cmbCustID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                cmbOrderID.getItems().clear();
                tblCart.getItems().clear();
                setCustomerData(newValue);
                loadOrderIds(newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });

        cmbOrderID.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        obList.clear();
                        loadItemTable(manageOrderBO.selectOrder(newValue));
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                });

        cmbItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData(newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });

        tblCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            updatItem((Integer) newValue);
        });
    }

    //Load Order Ids for specific Customer
    private void loadOrderIds(String id) throws SQLException, ClassNotFoundException {
        List<String> customerIds = manageOrderBO.getOrderIdsbyCustomer(id);
        cmbOrderID.getItems().addAll(customerIds);
    }

    //Set customer data in to the text fields
    private void setCustomerData(String customerId) throws SQLException, ClassNotFoundException {
        CustomerDTO c1 = manageOrderBO.searchCustomer(customerId);
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            tctName.setText(c1.getCustomerName());
            txtAddress.setText(c1.getCustomerAddress());
        }

    }

    //Set Item data in to the text fields
    public void setItemData(String itemCode) throws SQLException, ClassNotFoundException {
        ItemDTO i1 = manageOrderBO.searchItem(itemCode);
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtPackSize.setText(String.valueOf(i1.getPackSize()));
            txtQTYOnHand.setText(String.valueOf(i1.getQtyOnHand()));
            txtUniteprice.setText(String.valueOf(i1.getUnitePrice()));
            lbDiscount.setText(String.valueOf(i1.getItemdiscount()));
        }
    }

    private void loadItemTable(ArrayList<OrderDetail> itemList) {
        itemList.forEach(e -> {
            double discountPrice = (e.getOrderqty() * e.getPrice() * e.getDiscount()) / 100;
            System.out.println(e.getOrderqty());
            System.out.println(e.getPrice());
            System.out.println(e.getDiscount());
            double total = (e.getOrderqty() * e.getPrice()) - discountPrice;
            Button rem = new Button("Remove");
            CartTM tm = new CartTM(e.getItemCode(), null, e.getOrderqty(), e.getPrice(), e.getDiscount(), total, rem);
            obList.add(tm);
            rem.setOnAction((ei) -> {
                //txtQTYOnHand.setText(String.valueOf(Integer.parseInt(txtQTYOnHand.getText())+tm.getQty()));
                detetedItem.add(tm);
                obList.remove(tm);
                tblCart.refresh();
                calculateCost(obList, lbTotal);
            });
        });

        tblCart.setItems(obList);
        calculateCost(obList, lbTotal);
    }


    public void updatItem(int index) {
        if (index == -1) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            CartTM tm = obList.get(index);
            cmbItem.setValue(tm.getItemId());
            try {
                setItemData(tm.getItemId());
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            txtqty.setText(String.valueOf(tm.getQty()));

        }
    }


    public void back(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.load("CashierWindow", contextMO);
    }

    public void openCustomerTable(ActionEvent actionEvent) throws IOException {
        LoadFXMLFile.loadOn("AllCustomers");
    }

    public void openItemTable(ActionEvent actionEvent) {
    }

    public void cancelOrder(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("Are you sure");
        alert.setContentText("Select okay or cancel this alert.");
        Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent()) {
            // alert is exited, no button has been pressed.
        } else if (result.get() == ButtonType.OK) {
            try {
                manageOrderBO.deleteOrder(cmbOrderID.getSelectionModel().getSelectedItem());
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else if (result.get() == ButtonType.CANCEL) {
            // cancel button is pressed
        }

    }


    public void editeOrder(ActionEvent actionEvent) {
        ArrayList<OrderDetail> items = new ArrayList<>();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("Are you sure");
        alert.setContentText("Select okay or cancel this alert.");
        Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent()) {
            // alert is exited, no button has been pressed.
        } else if (result.get() == ButtonType.OK) {
            for (CartTM tempTm : obList
            ) {
                items.add(new OrderDetail(tempTm.getItemId(), cmbOrderID.getSelectionModel().getSelectedItem(), tempTm.getQty(), tempTm.getDiscount(), tempTm.getUnitPrice()));
            }

            OrderDTO order = new OrderDTO(cmbOrderID.getSelectionModel().getSelectedItem(), cmbCustID.getValue(), lbDate.getText(), lbTime.getText(), Double.parseDouble(lbTotal.getText()), items);

            try {
                if (manageOrderBO.updateOrder(order.getOrderId(), order.getItems(), detetedItem)) {
                    manageOrderBO.placeOrder(order);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again").show();
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else if (result.get() == ButtonType.CANCEL) {
            // cancel button is pressed
        }

    }


    public void addToCart(ActionEvent actionEvent) {
        btnEditeOrder.setDisable(false);
        int qtyOnHand = Integer.parseInt(txtQTYOnHand.getText());
        double unitPrice = Double.parseDouble(txtUniteprice.getText());
        double discount = Double.parseDouble(lbDiscount.getText());
        int qtyForCustomer = Integer.parseInt(txtqty.getText());
        double discountPrice = (qtyForCustomer * unitPrice * discount) / 100;
        double total = (qtyForCustomer * unitPrice) - discountPrice;

        if (qtyOnHand < qtyForCustomer) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }

        Button del = new Button("Remove");
        CartTM tm = new CartTM(
                cmbItem.getValue(),
                null,
                qtyForCustomer,
                unitPrice,
                discount,
                total,
                del

        );

        txtQTYOnHand.setText(String.valueOf(qtyOnHand - qtyForCustomer));
        del.setOnAction((e) -> {
            //txtQTYOnHand.setText(String.valueOf(Integer.parseInt(txtQTYOnHand.getText())+tm.getQty()));
            obList.remove(tm);
            tblCart.refresh();
            calculateCost(obList, lbTotal);
        });
        int rowNumber = isExists(tm, obList);

        if (rowNumber == -1) {// new Add
            obList.add(tm);
        } else {
            // update
            CartTM temp = obList.get(rowNumber);
            CartTM newTm = new CartTM(
                    temp.getItemId(),
                    temp.getDescription(),
                    qtyForCustomer,
                    unitPrice,
                    discount,
                    total,
                    del
            );


            if (qtyOnHand < temp.getQty()) {
                new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
                return;
            }

            obList.remove(rowNumber);
            obList.add(newTm);
            newTm.getDeletebtn().setOnAction((e) -> {
                //txtQTYOnHand.setText(String.valueOf(Integer.parseInt(txtQTYOnHand.getText())+tm.getQty()));
                obList.remove(newTm);
                tblCart.refresh();
                calculateCost(obList, lbTotal);
            });
        }
        tblCart.setItems(obList);
        calculateCost(obList, lbTotal);
    }


}
