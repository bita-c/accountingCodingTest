package myob.payslip.helper;

import myob.payslip.domain.Employee;
import myob.payslip.domain.Payslip;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class TestHelper {

    private TestHelper() {

    }

    public static URI getFilePath(String fileName) {

        URI result;
        try {
            URL url = TestHelper.class.getResource(fileName);
            result = url.toURI();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read file ");
        }
        return result;
    }

    public static List<Employee> createEmployeeList() {

        Employee employeeA = new Employee();

        List<Employee> employees = new ArrayList<Employee>();

        employeeA.setFirstName("firstNameA");
        employeeA.setLastName("lastNameA");
        employeeA.setSuperRate(new BigDecimal(0.9));
        employeeA.setAnnualSalary(new BigDecimal(60050));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 01);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        Date startDate  = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, 31);

        Date endDate  = cal.getTime();

        Payslip payslipA1 = new Payslip();
        payslipA1.setStartDate(startDate);
        payslipA1.setEndDate(endDate);
        payslipA1.setGrossIncome(new BigDecimal(40000));
        payslipA1.setIncomeSuper(new BigDecimal(50));
        payslipA1.setNetIncome(new BigDecimal(30000));
        payslipA1.setIncomeTax(new BigDecimal(100));

        employeeA.getPayslips().add(payslipA1);
        employees.add(employeeA);

        return employees;
    }
}
