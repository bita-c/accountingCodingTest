package myob.payslip.utils;

import myob.payslip.domain.Employee;
import myob.payslip.enums.IncomeTaxBracket;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

public class PayslipCalculator {

    private static final Logger LOGGER = Logger.getLogger(PayslipCalculator.class);

    private PayslipCalculator() {

    }

    public static BigDecimal calculateGrossIncome(Employee employee) {

        BigDecimal result = BigDecimal.ZERO;
        if (employee.getAnnualSalary() != null) {
            result = employee.getAnnualSalary().divide(new BigDecimal(12), BigDecimal.ROUND_HALF_UP);
        } else {
            LOGGER.warn("Invalid AnnualSalary set for Employee: NULL");
        }
        return result;
    }

    public static BigDecimal calculateIncomeTax(Employee employee) {

        BigDecimal result = BigDecimal.ZERO;
        BigDecimal annualSalary = employee.getAnnualSalary();
        if (annualSalary != null) {

            // base + additional for each dollar over
            // get tax bracket for income
            IncomeTaxBracket taxBracket = IncomeTaxBracket.getTaxBracket(annualSalary);

            result = taxBracket.getBaseTaxAmount()
                    .add(annualSalary.subtract( taxBracket.getLowerThreshold())
                            .multiply(taxBracket.getAdditionalTax())) ;

        } else {
            LOGGER.warn("Invalid AnnualSalary set for Employee: NULL");
        }
        return result;
    }

    public static BigDecimal calculateNetIncome(Employee employee) {

        BigDecimal result = BigDecimal.ZERO;
        BigDecimal grossIncome =  employee.getPayslip().getGrossIncome();
        BigDecimal incomeTax =  employee.getPayslip().getIncomeTax();

        if (grossIncome != null && incomeTax != null) {
            result =  grossIncome.subtract(incomeTax);

        } else {
            LOGGER.warn("Invalid GrossIncome set for Employee: NULL");
        }
        return result;
    }

    public static BigDecimal calculateSuper(Employee employee) {

        BigDecimal result = BigDecimal.ZERO;
        BigDecimal grossIncome =  employee.getPayslip().getGrossIncome();
        BigDecimal superRate = employee.getSuperRate();

        if (grossIncome != null) {

            if (superRate != null) {

                result = grossIncome.multiply(superRate);

            } else {
                LOGGER.warn("Invalid SuperRate set for Employee: NULL");
            }

        } else {
            LOGGER.warn("Invalid GrossIncome set for Employee: NULL");
        }

        return result;
    }
}
