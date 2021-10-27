package dto;

public class ItemDTO {private String itemCode;
    private String description;
    private String packSize;
    private int qtyOnHand;
    private double unitePrice;
    private double itemdiscount;

    public ItemDTO() {
    }

    public ItemDTO(String itemCode, String description, String packSize, int qtyOnHand, double unitePrice, double itemdiscount) {
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.qtyOnHand = qtyOnHand;
        this.unitePrice = unitePrice;
        this.itemdiscount = itemdiscount;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitePrice() {
        return unitePrice;
    }

    public void setUnitePrice(double unitePrice) {
        this.unitePrice = unitePrice;
    }

    public double getItemdiscount() {
        return itemdiscount;
    }

    public void setItemdiscount(double itemdiscount) {
        this.itemdiscount = itemdiscount;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", packSize='" + packSize + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", unitePrice=" + unitePrice +
                ", itemdiscount=" + itemdiscount +
                '}';
    }

}
