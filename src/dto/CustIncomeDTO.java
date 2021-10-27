package dto;

public class CustIncomeDTO {
    private String custId;
    private double income;

    public CustIncomeDTO() {
    }

    public CustIncomeDTO(String custId, double income) {
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
