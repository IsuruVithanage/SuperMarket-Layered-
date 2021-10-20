package model;

import java.util.Comparator;

public class MyComparator implements Comparator<ItemSells> {


    @Override
    public int compare(ItemSells o1, ItemSells o2) {
        if (o1.getSell() > o2.getSell()) {
            return -1;
        } else if (o1.getSell() < o2.getSell()) {
            return 1;
        }
        return 0;
    }
}
