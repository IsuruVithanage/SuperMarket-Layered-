package view.tm;

public class MonthlyIncomeTM {
    private String mounth;
    private double income;

    public MonthlyIncomeTM() {
    }

    public MonthlyIncomeTM(String mounth, double income) {
        this.mounth = mounth;
        this.income = income;
    }

    public String getMounth() {
        return mounth;
    }

    public void setMounth(String mounth) {
        this.mounth = mounth;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
