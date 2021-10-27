package view.tm;

public class CustomerIncomeTM {
    private String custId;
    private double income;

    public CustomerIncomeTM() {
    }

    public CustomerIncomeTM(String custId, double income) {
        this.custId = custId;
        this.income = income;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
