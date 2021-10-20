package model;

public class ItemSells {
    private String itemId;
    private int sell;

    public ItemSells() {
    }

    public ItemSells(String itemId, int sell) {
        this.itemId = itemId;
        this.sell = sell;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getSell() {
        return sell;
    }

    public void setSell(int sell) {
        this.sell = sell;
    }
}
