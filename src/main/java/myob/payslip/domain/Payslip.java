package myob.payslip.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Payslip {
    
    // payslip information with name, 
    //pay period,  gross income, income tax, net income and super.
    
    private Date startDate;
    private BigDecimal grossIncome;
    private BigDecimal incomeTax;
    private BigDecimal netIncome;
    private BigDecimal incomeSuper;

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setGrossIncome(BigDecimal grossIncome) {
        this.grossIncome = grossIncome;
    }

    public void setIncomeTax(BigDecimal incomeTax) {
        this.incomeTax = incomeTax;
    }

    public void setNetIncome(BigDecimal netIncome) {
        this.netIncome = netIncome;
    }

    public void setIncomeSuper(BigDecimal incomeSuper) {
        this.incomeSuper = incomeSuper;
    }

    public Date getStartDate() {
        return startDate;
    }

    public BigDecimal getGrossIncome() {
        return grossIncome;
    }

    public BigDecimal getIncomeTax() {
        return incomeTax;
    }

    public BigDecimal getNetIncome() {
        return netIncome;
    }

    public BigDecimal getIncomeSuper() {
        return incomeSuper;
    }    
}
