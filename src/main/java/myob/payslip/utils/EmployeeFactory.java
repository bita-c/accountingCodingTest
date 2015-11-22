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

    /**
     * Given a valid CSVRecord, creates an Employee object based on the
     * information in the CSVRecord.
     *
     * @param csvRecord
     * @return Created employee
     */
    public static Employee createEmployee(CSVRecord csvRecord) {

        if (csvRecord == null) {
            throw new IllegalArgumentException("Invalid input: CSVRecord is NULL");
        }

        Employee employee = new Employee();

        String firstName = csvRecord.get(InputCsvColumn.FIRST_NAME.getColumnName());
        String lastName = csvRecord.get(InputCsvColumn.LAST_NAME.getColumnName());
        String annualSalary = csvRecord.get(InputCsvColumn.ANNUAL_SALARY.getColumnName());
        String superRate = csvRecord.get(InputCsvColumn.SUPER_RATE.getColumnName());
        String paymentPeriod = csvRecord.get(InputCsvColumn.PAYMENT_PERIOD.getColumnName());

        // Check input.
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid CSVRecord: firstName is NULL or empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid CSVRecord: lastName is NULL or empty");
        }
        if (annualSalary == null || annualSalary.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid CSVRecord: annualSalary is NULL or empty");
        }
        if (superRate == null || superRate.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid CSVRecord: superRate is NULL or empty");
        }
        if (paymentPeriod == null || paymentPeriod.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid CSVRecord: paymentPeriod is NULL or empty");
        }

        BigDecimal superRateDecimal = new BigDecimal(superRate.trim().replace("%", "")).divide(BigDecimal.valueOf(100));

        // validate the superRate range is correct
        if (superRateDecimal.floatValue() < 0 || superRateDecimal.floatValue() > 0.5) {
            throw new IllegalArgumentException("Invalid superRate value: " + superRateDecimal);
        }

        // break up the date string dd MMMM – dd MMMM, get the start and end dates
        String[] paymentDates = paymentPeriod.split("-");
        String paymentStartDate;
        String paymentEndDate;

        if (paymentDates.length > 1) {
            paymentStartDate = paymentDates[0];
            paymentEndDate = paymentDates[1];
        } else {
            throw new IllegalArgumentException("Invalid CSVRecord: paymentPeriod format not like: dd MMMM - dd MMMM");
        }

        Date paymentStartDateConverted = convertToAcceptedFormat(paymentStartDate);
        Date paymentEndDateConverted = convertToAcceptedFormat(paymentEndDate);

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

    /**
     * converts the string date passed, which may or may not contain year
     * to a valid Date object.
     *
     * @param date
     * @return Converted Date
     */
    private static Date convertToAcceptedFormat(String date) {

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String yearInString = String.valueOf(year);

        DateFormat dateFormat = new SimpleDateFormat("dd MMMM y");
        Date convertedDate;
        try {
            convertedDate = dateFormat.parse(date);

        } catch (ParseException e) {

            // if year is not provided then add the current year and parse again
            date += " " + yearInString;
            try {
                convertedDate = dateFormat.parse(date);
            } catch (ParseException e2) {
                throw new IllegalArgumentException("Invalid date format: Not matching 'dd MMMM' or 'dd MMMM y'");
            }
        }
        return convertedDate;
    }
}
