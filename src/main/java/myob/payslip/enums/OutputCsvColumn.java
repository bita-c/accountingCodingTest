package myob.payslip.enums;

import java.util.ArrayList;
import java.util.List;

public enum OutputCsvColumn {

    //Output (name, pay period, gross income, income tax, net income, super)
    NAME("name"),
    PAY_PERIOD("pay preiod"),
    GROSS_INCOME("gross income"),
    INCOME_TAX("income tax"),
    NET_INCOME("net income"),
    SUPER("super");

    private String columnName;

    private OutputCsvColumn(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public static List<String> getColumnHeaders() {

        List<String> result = new ArrayList<String>();

        for (OutputCsvColumn header : OutputCsvColumn.values()) {
            result.add(header.columnName);
        }
        return result;
    }


}
