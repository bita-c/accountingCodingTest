package myob.payslip.utils;

import myob.payslip.domain.Employee;
import myob.payslip.healper.TestHelper;
import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class EmployeeFactoryTest {

    private String csvFile = "/csv/valid-and-invalid-input.csv";

    private String csvFilePath = TestHelper.getFilePath(csvFile).getPath();

    private List<CSVRecord> csvRecords;

    private static final int EMPTY_VALUE_ROW_INDEX = 0;
    private static final int INVALID_SUPER_VALUE_ROW_INDEX = 1;
    private static final int INVALID_PAYMENT_PERIOD_FORMAT_ROW_INDEX = 2;
    private static final int INVALID_PAYMENT_DATE_FORMAT_ROW_INDEX = 3;
    private static final int PERIOD_DATES_NO_YEAR_ROW_INDEX = 4;
    private static final int PERIOD_DATES_WITH_YEAR_ROW_INDEX = 5;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws IOException{
        csvRecords = CsvMunger.readCsv(csvFilePath);
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
        expectedException.expectMessage("Invalid date format: Not matching 'dd MMMM' or 'dd MMMM y'");

        EmployeeFactory.createEmployee(csvRecords.get(INVALID_PAYMENT_DATE_FORMAT_ROW_INDEX));
    }

    @Test
    public void testCreateEmployeeHandlesDateWithNoYear() {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 01);
        cal.set(Calendar.MONTH, 02);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        String expectedStartDate = cal.getTime().toString();

        cal.set(Calendar.DAY_OF_MONTH, 31);

        String expectedEndDate = cal.getTime().toString();

        // check that the created dates are set to current year
        Employee employee = EmployeeFactory.createEmployee(csvRecords.get(PERIOD_DATES_NO_YEAR_ROW_INDEX));
        assertNotNull("Employee is NULL", employee);
        assertNotNull("Employee Payslips is NULL", employee.getPayslips());
        assertFalse("Employee has no Payslips set", employee.getPayslips().isEmpty());
        // here we check that if no year is provided, year is set to current year and also verify the whole date is set correctly
        assertEquals("Incorrect payment StartDate set", expectedStartDate, employee.getPayslips().get(0).getStartDate().toString());
        assertEquals("Incorrect payment EndDate set", expectedEndDate, employee.getPayslips().get(0).getEndDate().toString());

    }

    @Test
    public void testCreateEmployeeHandlesDateWithYear() {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 01);
        cal.set(Calendar.MONTH, 02);
        cal.set(Calendar.YEAR, 2014);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        String expectedStartDate = cal.getTime().toString();

        cal.set(Calendar.DAY_OF_MONTH, 31);

        String expectedEndDate = cal.getTime().toString();

        Employee employee = EmployeeFactory.createEmployee(csvRecords.get(PERIOD_DATES_WITH_YEAR_ROW_INDEX));
        assertNotNull("Employee is NULL", employee);
        assertNotNull("Employee Payslips is NULL", employee.getPayslips());
        assertFalse("Employee has no Payslips set", employee.getPayslips().isEmpty());
        // here we check that if no year is provided, year is set to current year and also verify the whole date is set correctly
        assertEquals("Incorrect payment StartDate set", expectedStartDate, employee.getPayslips().get(0).getStartDate().toString());
        assertEquals("Incorrect payment EndDate set", expectedEndDate, employee.getPayslips().get(0).getEndDate().toString());

    }

}
