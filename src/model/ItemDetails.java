package model;

public class ItemDetails {
    private String itemID;
    private int qty;
    private double discount;
    private double unitPrice;

    public ItemDetails() {
    }

    public ItemDetails(String itemID, int qty, double discount, double unitPrice) {
        this.itemID = itemID;
        this.qty = qty;
        this.discount = discount;
        this.unitPrice = unitPrice;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "ItemDetails{" +
                "itemID='" + itemID + '\'' +
                ", qty=" + qty +
                ", discount=" + discount +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
