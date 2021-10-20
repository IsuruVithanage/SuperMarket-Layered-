package model;


import javafx.scene.control.Button;

public class CartTM {
    private String itemId;
    private String description;
    private int qty;
    private double unitPrice;
    private double discount;
    private double total;
    private Button deletebtn;

    public CartTM() {
    }

    public CartTM(String itemId, String description, int qty, double unitPrice, double discount, double total, Button deletebtn) {
        this.itemId = itemId;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.total = total;
        this.deletebtn = deletebtn;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Button getDeletebtn() {
        return deletebtn;
    }

    public void setDeletebtn(Button deletebtn) {
        this.deletebtn = deletebtn;
    }
}
