package bo;

import bo.custom.impl.*;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {
    }

    public static BoFactory getBOFactory() {
        if (boFactory == null) {
            boFactory = new BoFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BoTypes types) {
        switch (types) {
            case ITEM:
                return new ItemBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case CUSTOMER_INCOME:
                return new CustomerIncomeBOImpl();
            case INCOME_REPORT:
                return new IncomeReportsBOImpl();
            case MANAGE_ORDER:
                return new ManageOrderBOImpl();
            default:
                return null;
        }
    }

    public enum BoTypes {
        CUSTOMER, ITEM, CUSTOMER_INCOME, INCOME_REPORT, MANAGE_ORDER
    }
}
