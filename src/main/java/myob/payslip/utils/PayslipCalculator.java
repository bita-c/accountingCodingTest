package myob.payslip.utils;

import myob.payslip.enums.IncomeTaxBracket;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

public class PayslipCalculator {

    private static final Logger LOGGER = Logger.getLogger(PayslipCalculator.class);
    private static final BigDecimal MONTHS_IN_YEAR = new BigDecimal(12);

    private PayslipCalculator() {
    }

    /**
     * Calculates and return grossIncome given an annualSalary.
     *
     * @param annualSalary
     * @return GrossIncome
     */
    public static BigDecimal calculateGrossIncome(BigDecimal annualSalary) {

        BigDecimal result = BigDecimal.ZERO;
        if (annualSalary != null) {
            result = annualSalary.divide(MONTHS_IN_YEAR, BigDecimal.ROUND_HALF_UP);
        } else {
            LOGGER.warn("Invalid AnnualSalary set for Employee: NULL");
        }
        return result.setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Calculates and returns an IncomeTax given an annualSalary.
     *
     * @param annualSalary
     * @return IncomeTax
     */
    public static BigDecimal calculateIncomeTax(BigDecimal annualSalary) {

        BigDecimal result = BigDecimal.ZERO;
        if (annualSalary != null) {
            IncomeTaxBracket taxBracket = IncomeTaxBracket.getTaxBracket(annualSalary);

            BigDecimal amountOver = annualSalary.subtract(taxBracket.getLowerThreshold());
            BigDecimal additionalTax =  amountOver.multiply(taxBracket.getAdditionalTax());

            result = taxBracket.getBaseTaxAmount().add(additionalTax) ;
            result = result.divide(MONTHS_IN_YEAR , BigDecimal.ROUND_HALF_UP);

        } else {
            LOGGER.warn("Invalid AnnualSalary set for Employee: NULL");
        }
        return result.setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Calculates and returns a netIncomeTax given grossIncome and incomeTax values.
     *
     * @param grossIncome
     * @param incomeTax
     * @return NetIncome
     */
    public static BigDecimal calculateNetIncome(BigDecimal grossIncome, BigDecimal incomeTax) {

        BigDecimal result = BigDecimal.ZERO;

        if (grossIncome != null && incomeTax != null) {
            result =  grossIncome.subtract(incomeTax);

        } else {
            LOGGER.warn("Invalid grossIncome or incomeTax set for Employee: NULL");
        }
        return result.setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Calculates and returns super on income given the superRate and grossIncome values.
     *
     * @param superRate
     * @param grossIncome
     * @return IncomeSuper
     */
    public static BigDecimal calculateSuper(BigDecimal superRate, BigDecimal grossIncome) {

        BigDecimal result = BigDecimal.ZERO;

        if (grossIncome != null) {

            if (superRate != null) {

                result = grossIncome.multiply(superRate);

            } else {
                LOGGER.warn("Invalid superRate set for Employee: NULL");
            }

        } else {
            LOGGER.warn("Invalid grossIncome set for Employee: NULL");
        }

        return result.setScale(0, BigDecimal.ROUND_HALF_UP);
    }
}
