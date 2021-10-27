package dto;

public class ItemSellsDTO {
    private String itemId;
    private int sell;

    public ItemSellsDTO() {
    }

    public ItemSellsDTO(String itemId, int sell) {
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
