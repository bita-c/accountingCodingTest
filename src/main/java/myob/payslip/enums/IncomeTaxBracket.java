package myob.payslip.enums;

import java.math.BigDecimal;

public enum IncomeTaxBracket {
   
    BRACKET_0(0, 0, 0),
    BRACKET_1(18201, 0, 0.19),
    BRACKET_2(37001, 3572, 0.325),
    BRACKET_3(80001, 17547, 0.37),
    BRACKET_4(180001, 54547, 0.45);

    private float lowerThreshold;
    private float baseTaxAmount;
    private double additionalTax;

    private IncomeTaxBracket(float lowerThreshold, float baseTaxAmount, double additionalTax){
        this.lowerThreshold = lowerThreshold;
        this.baseTaxAmount = baseTaxAmount;
        this.additionalTax = additionalTax;
    }

    public static IncomeTaxBracket getTaxBracket(float income) {
        IncomeTaxBracket correctTaxBracket = BRACKET_1;
        for (IncomeTaxBracket taxBracket : values()) {
            if (income >= taxBracket.lowerThreshold) {
                correctTaxBracket = taxBracket;
            }
        }
        return correctTaxBracket;
    } 
}
