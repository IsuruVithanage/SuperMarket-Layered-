package model;

public class MounthlyIncome {
    private int mounth;
    private double income;

    public MounthlyIncome() {
    }

    public MounthlyIncome(int mounth, double income) {
        this.mounth = mounth;
        this.income = income;
    }

    public int getMounth() {
        return mounth;
    }

    public void setMounth(int mounth) {
        this.mounth = mounth;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
