package myob.payslip.utils;

import myob.payslip.domain.Employee;
import myob.payslip.domain.Payslip;
import myob.payslip.enums.IncomeTaxBracket;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

public class PayslipCalculator {

    private static final Logger LOGGER = Logger.getLogger(PayslipCalculator.class);
    private static final BigDecimal MONTHS_IN_YEAR = new BigDecimal(12);

    private PayslipCalculator() {

    }

    //income tax,net income,

    public static BigDecimal calculateGrossIncome(Employee employee) {

        BigDecimal result = BigDecimal.ZERO;
        if (employee.getAnnualSalary() != null) {
            result = employee.getAnnualSalary().divide(MONTHS_IN_YEAR, BigDecimal.ROUND_HALF_UP);
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

            BigDecimal amountOver = annualSalary.subtract(taxBracket.getLowerThreshold());
            BigDecimal additionalTax =  amountOver.multiply(taxBracket.getAdditionalTax());

            result = taxBracket.getBaseTaxAmount().add(additionalTax) ;
            result = result.divide(MONTHS_IN_YEAR , BigDecimal.ROUND_HALF_UP);

        } else {
            LOGGER.warn("Invalid AnnualSalary set for Employee: NULL");
        }
        return result;
    }

    public static BigDecimal calculateNetIncome(Payslip payslip) {

        BigDecimal result = BigDecimal.ZERO;
        BigDecimal grossIncome =  payslip.getGrossIncome();
        BigDecimal incomeTax =  payslip.getIncomeTax();

        if (grossIncome != null && incomeTax != null) {
            result =  grossIncome.subtract(incomeTax);

        } else {
            LOGGER.warn("Invalid GrossIncome set for Employee: NULL");
        }
        return result;
    }

    public static BigDecimal calculateSuper(Employee employee, Payslip payslip ) {

        BigDecimal result = BigDecimal.ZERO;
        BigDecimal grossIncome = payslip.getGrossIncome();
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
