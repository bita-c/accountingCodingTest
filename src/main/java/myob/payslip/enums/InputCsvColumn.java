package myob.payslip.enums;

public enum InputCsvColumn {

    FIRST_NAME("first name"),
    LAST_NAME("last name"),
    ANNUAL_SALARY("annual salary"),
    SUPER_RATE("super rate"),
    PAYMENT_PERIOD("payment start date");

    private String columnName;

    private InputCsvColumn(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public static String[] getColumnNames() {
        String[] result = new String[values().length];
        int counter = 0;
        for (InputCsvColumn csvColumn : values()) {
            result[counter] = csvColumn.getColumnName();
            counter++;
        }
        return result;
    }


}


