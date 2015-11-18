package myob.payslip.utils;

import myob.payslip.domain.Employee;

import java.util.List;

public class EmployeeFactory {

    // creates employee objects from the csv rows
    public static List<Employee> createEmployees() {

      /*  // break up the date string 01 March – 31 March, get the start and end dates
                String[] paymentDates = paymentPeriod.split("-");
                String paymentStartDate = paymentDates[0];
                String paymentEndDate = paymentDates[1];

                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date paymentStartDateConverted;
                try {
                    paymentStartDateConverted = dateFormat.parse(paymentPeriod);
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Invalid input: paymentPeriod format not matching: dfajhksdfhkasdhfkasdfkadkfhakdsfhkasdfaskfd");
                }


                BigDecimal superRateDecimal = new BigDecimal(superRate.trim().replace("%", "")).divide(BigDecimal.valueOf(100));

                // validate the superRate range is correct
                if (superRateDecimal.floatValue() < 0 || superRateDecimal.floatValue() > 0.5) {
                    throw new IllegalArgumentException("Invalid superRate value: " + superRateDecimal);
                }

                Employee employee = new Employee();
                employee.setFirstName(firstName);
                employee.setLastName(lastName);
                employee.setSuperRate(superRateDecimal);

                Payslip payslip = new Payslip();
                payslip.setStartDate(paymentStartDateConverted);

                employee.getPayslips().add(payslip);    */



        return null;
    }
}
