package myob.payslip.utils;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PaySlipCalculatorTest {

    @Test
    public void testCalculateGrossIncome() {

        BigDecimal expectedGrossIncome = new BigDecimal(5004);
        BigDecimal inputSalary = new BigDecimal(60050);

        BigDecimal grossIncome = PayslipCalculator.calculateGrossIncome(inputSalary);

        assertNotNull("Invalid grossIncome returned: NULL", grossIncome);
        assertEquals("Incorrect grossIncome returned", 0, expectedGrossIncome.compareTo(grossIncome));
    }

    @Test
    public void testCalculateIncomeTax() {

        BigDecimal expectedIncomeTax = new BigDecimal(922);
        BigDecimal inputSalary = new BigDecimal(60050);

        BigDecimal incomeTax = PayslipCalculator.calculateIncomeTax(inputSalary);

        assertNotNull("Invalid incomeTax returned: NULL", incomeTax);
        assertEquals("Incorrect incomeTax returned", 0, expectedIncomeTax.compareTo(incomeTax));
    }

    @Test
    public void testCalculateNetIncome() {

        BigDecimal expectedNetIncome = new BigDecimal(4082);
        BigDecimal inputGrossIncome = new BigDecimal(5004);
        BigDecimal inputIncomeTax = new BigDecimal(922);

        BigDecimal netIncome = PayslipCalculator.calculateNetIncome(inputGrossIncome, inputIncomeTax);

        assertNotNull("Invalid netIncome returned: NULL", netIncome);
        assertEquals("Incorrect netIncome returned", 0, expectedNetIncome.compareTo(netIncome));
    }

    @Test
    public void testCalculateSuper() {

        BigDecimal expectedSuper = new BigDecimal(450);
        BigDecimal inputGrossIncome = new BigDecimal(5004);
        BigDecimal inputSuperRate = new BigDecimal(0.09);

        BigDecimal incomeSuper = PayslipCalculator.calculateSuper(inputSuperRate, inputGrossIncome);

        assertNotNull("Invalid super returned: NULL", incomeSuper);
        assertEquals("Incorrect netIncome returned", 0, expectedSuper.compareTo(incomeSuper));

    }

}
