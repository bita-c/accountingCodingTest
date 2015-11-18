package myob.payslip.enums;

import java.math.BigDecimal;

public enum IncomeTaxBracket {
   
    BRACKET_0(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO),
    BRACKET_1(new BigDecimal(18201), BigDecimal.ZERO, new BigDecimal(0.19)),
    BRACKET_2(new BigDecimal(37001), new BigDecimal(3572), new BigDecimal(0.325)),
    BRACKET_3(new BigDecimal(80001), new BigDecimal(17547), new BigDecimal(0.37)),
    BRACKET_4(new BigDecimal(180001), new BigDecimal(54547), new BigDecimal(0.45));

    private BigDecimal lowerThreshold;
    private BigDecimal baseTaxAmount;
    private BigDecimal additionalTax;

    private IncomeTaxBracket(BigDecimal lowerThreshold, BigDecimal baseTaxAmount, BigDecimal additionalTax){
        this.lowerThreshold = lowerThreshold;
        this.baseTaxAmount = baseTaxAmount;
        this.additionalTax = additionalTax;
    }

    public BigDecimal getBaseTaxAmount() {
        return this.baseTaxAmount;
    }

    public BigDecimal getLowerThreshold() {
        return this.lowerThreshold;
    }

    public BigDecimal getAdditionalTax() {
        return this.additionalTax;
    }

    public static IncomeTaxBracket getTaxBracket(BigDecimal income) {
        IncomeTaxBracket correctTaxBracket = BRACKET_1;
        for (IncomeTaxBracket taxBracket : values()) {
           // if (income.floatValue() >= taxBracket.lowerThreshold.floatValue()) {
            if (income.compareTo(taxBracket.lowerThreshold) > 1 ) {
                correctTaxBracket = taxBracket;
            }
        }
        return correctTaxBracket;
    } 
}
