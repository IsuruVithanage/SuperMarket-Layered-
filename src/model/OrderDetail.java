package model;

public class OrderDetail {
    private String itemCode;
    private String orderID;
    private int orderqty;
    private double price;
    private double discount;

    public OrderDetail() {
    }

    public OrderDetail(String itemCode, String orderID, int orderqty, double price, double discount) {
        this.itemCode = itemCode;
        this.orderID = orderID;
        this.orderqty = orderqty;
        this.price = price;
        this.discount = discount;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getOrderqty() {
        return orderqty;
    }

    public void setOrderqty(int orderqty) {
        this.orderqty = orderqty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "itemCode='" + itemCode + '\'' +
                ", orderID='" + orderID + '\'' +
                ", orderqty=" + orderqty +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}
