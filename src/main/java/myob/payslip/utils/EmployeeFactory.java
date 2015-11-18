package myob.payslip.utils;

import myob.payslip.domain.Employee;
import myob.payslip.domain.Payslip;
import myob.payslip.enums.InputCsvColumn;
import org.apache.commons.csv.CSVRecord;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeFactory {

    // creates employee objects from the csv rows
    public static Employee createEmployee(CSVRecord csvRecord) {

        Employee employee = new Employee();

        String firstName = csvRecord.get(InputCsvColumn.FIRST_NAME);
        String lastName = csvRecord.get(InputCsvColumn.LAST_NAME);
        String annualSalary = csvRecord.get(InputCsvColumn.ANNUAL_SALARY);
        String superRate = csvRecord.get(InputCsvColumn.SUPER_RATE);
        String paymentPeriod = csvRecord.get(InputCsvColumn.PAYMENT_PERIOD);

        // Check input.
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid input: firstName is NULL or empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid input: lastName is NULL or empty");
        }
        if (annualSalary == null || annualSalary.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid input: annualSalary is NULL or empty");
        }
        if (superRate == null || superRate.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid input: superRate is NULL or empty");
        }
        if (paymentPeriod == null || paymentPeriod.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid input: paymentPeriod is NULL or empty");
        }

        // break up the date string 01 March – 31 March, get the start and end dates
        String[] paymentDates = paymentPeriod.split("-");
        String paymentStartDate = paymentDates[0];
        String paymentEndDate = paymentDates[1];

        DateFormat dateFormat = new SimpleDateFormat("dd MMMM");
        Date paymentStartDateConverted;
        Date paymentEndDateConverted ;
        try {
            paymentStartDateConverted = dateFormat.parse(paymentStartDate);
            paymentEndDateConverted = dateFormat.parse(paymentEndDate);

        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid input: paymentPeriod format not matching: dd MMMM");
        }

        BigDecimal superRateDecimal = new BigDecimal(superRate.trim().replace("%", "")).divide(BigDecimal.valueOf(100));

        // validate the superRate range is correct
        if (superRateDecimal.floatValue() < 0 || superRateDecimal.floatValue() > 0.5) {
            throw new IllegalArgumentException("Invalid superRate value: " + superRateDecimal);
        }

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setSuperRate(superRateDecimal);

        Payslip payslip = new Payslip();
        payslip.setStartDate(paymentStartDateConverted);
        payslip.setEndDate(paymentEndDateConverted);

        employee.setPayslip(payslip);

        return employee;
    }
}
