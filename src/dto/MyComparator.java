package dto;

import java.util.Comparator;

public class MyComparator implements Comparator<ItemSellsDTO> {


    @Override
    public int compare(ItemSellsDTO o1, ItemSellsDTO o2) {
        if (o1.getSell() > o2.getSell()) {
            return -1;
        } else if (o1.getSell() < o2.getSell()) {
            return 1;
        }
        return 0;
    }
}
