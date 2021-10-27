package dto;

public class MounthlyIncomeDTO {
    private String mounth;
    private double income;

    public MounthlyIncomeDTO() {
    }

    public MounthlyIncomeDTO(String mounth, double income) {
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
