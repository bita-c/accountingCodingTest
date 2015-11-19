package myob.payslip.utils;

import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.List;

public class EmployeeFactoryTest {

    //TODO
    private String csvFilePath = "/Users/bcrosby/workspace/myobExercise/src/test/resources/csv/invalid-input.csv";

    private List<CSVRecord> csvRecords;

    private static final int EMPTY_VALUE_ROW_INDEX = 0;
    private static final int INVALID_SUPER_VALUE_ROW_INDEX = 1;
    private static final int INVALID_PAYMENT_PERIOD_FORMAT_ROW_INDEX = 2;
    private static final int INVALID_PAYMENT_DATE_FORMAT_ROW_INDEX = 3;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws IOException{
        csvRecords = CsvMunger.readCsv(csvFilePath);
    }

    public void testCreateEmployeeSuccess() {


    }

    @Test
    public void testCreateEmployeeInvalidCSVRecordEmptyColumns() throws IOException {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid CSVRecord: lastName is NULL or empty");

        EmployeeFactory.createEmployee(csvRecords.get(EMPTY_VALUE_ROW_INDEX));
    }

    @Test
    public void testCreateEmployeeInvalidSuperRate() throws IOException  {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid superRate value: 0.7");

        EmployeeFactory.createEmployee(csvRecords.get(INVALID_SUPER_VALUE_ROW_INDEX));
    }

    @Test
    public void testCreateEmployeeInvalidPaymentPeriodFormat() {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid CSVRecord: paymentPeriod format not like: dd MMMM - dd MMMM");

        EmployeeFactory.createEmployee(csvRecords.get(INVALID_PAYMENT_PERIOD_FORMAT_ROW_INDEX));
    }

    @Test
    public void testCreateEmployeeInvalidPaymentDateFormat() {

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid CSVRecord: paymentPeriod format not like: dd MMMM - dd MMMM");

        EmployeeFactory.createEmployee(csvRecords.get(INVALID_PAYMENT_DATE_FORMAT_ROW_INDEX));

    }

    public void testCreateEmployeeHandlesDateWithNoYear() {

        // check that the created dates are set to current year

    }

    public void testCreateEmployeeHandlesDateWithYear() {

        // check that the created dates are set to the provided date with year

    }

}
