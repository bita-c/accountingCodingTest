package myob.payslip.enums;

public enum InputCsvColumn {

    FIRST_NAME(0),
    LAST_NAME(1),
    ANNUAL_SALARY(2),
    SUPER_RATE(3),
    PAYMENT_PERIOD(4);

    private int columnIndex;

    private InputCsvColumn(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }


}
