package myob.payslip.utils;

import myob.payslip.domain.Employee;
import myob.payslip.domain.Payslip;
import myob.payslip.enums.InputCsvColumn;
import org.apache.commons.csv.CSVRecord;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EmployeeFactory {

    // creates employee objects from the csv rows
    public static Employee createEmployee(CSVRecord csvRecord) {

        Employee employee = new Employee();

        String firstName = csvRecord.get(InputCsvColumn.FIRST_NAME.getColumnName());
        String lastName = csvRecord.get(InputCsvColumn.LAST_NAME.getColumnName());
        String annualSalary = csvRecord.get(InputCsvColumn.ANNUAL_SALARY.getColumnName());
        String superRate = csvRecord.get(InputCsvColumn.SUPER_RATE.getColumnName());
        String paymentPeriod = csvRecord.get(InputCsvColumn.PAYMENT_PERIOD.getColumnName());

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
        String[] paymentDates = paymentPeriod.split("–");
        String paymentStartDate = null;
        String paymentEndDate = null;

        // we might only have the start date provided, so check to make sure
        if (paymentDates.length > 0) {
             paymentStartDate = paymentDates[0];
            if (paymentDates.length > 1) {
                paymentEndDate = paymentDates[1];
            }
        } else {
            throw new IllegalArgumentException("Invalid input: paymentPeriod format not matching: dd MMMM - dd MMMM");
        }

       // manipulate the dates to add current year
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String yearInString = String.valueOf(year);


        DateFormat dateFormat = new SimpleDateFormat("dd MMMM y");
        Date paymentStartDateConverted;
        Date paymentEndDateConverted ;
        try {
            paymentStartDateConverted = dateFormat.parse(paymentStartDate);
            paymentEndDateConverted = dateFormat.parse(paymentEndDate);

        } catch (ParseException e) {

            // if year is not provided then add the current year and parse again
            paymentStartDate += " " + yearInString;
            paymentEndDate += " " + yearInString;

            try {
                paymentStartDateConverted = dateFormat.parse(paymentStartDate);
                paymentEndDateConverted = dateFormat.parse(paymentEndDate);

            } catch (ParseException e2) {
                throw new IllegalArgumentException("Invalid input: date format invalid: Not matching 'dd MMMM' or 'dd MMMM y'");
            }
        }

        BigDecimal superRateDecimal = new BigDecimal(superRate.trim().replace("%", "")).divide(BigDecimal.valueOf(100));

        // validate the superRate range is correct
        if (superRateDecimal.floatValue() < 0 || superRateDecimal.floatValue() > 0.5) {
            throw new IllegalArgumentException("Invalid superRate value: " + superRateDecimal);
        }

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setAnnualSalary(new BigDecimal(annualSalary));
        employee.setSuperRate(superRateDecimal);


        Payslip payslip = new Payslip();
        payslip.setStartDate(paymentStartDateConverted);
        payslip.setEndDate(paymentEndDateConverted);

        employee.getPayslips().add(payslip);

        return employee;
    }
}
