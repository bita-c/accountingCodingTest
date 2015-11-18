package myob.payslip.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    
    //first name, last name, annual salary(positive integer) 
    //and super rate(0% - 50% inclusive), payment start date, 
      
    private String firstName;
    private String lastName;
    private BigDecimal annualSalary;
    private BigDecimal superRate;

    private List<Payslip> payslips = new ArrayList<Payslip>();

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getAnnualSalary() {
        return annualSalary;
    }

    public BigDecimal getSuperRate() {
        return superRate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAnnualSalary(BigDecimal annualSalary) {
        this.annualSalary = annualSalary;
    }

    public void setSuperRate(BigDecimal superRate) {
        this.superRate = superRate;
    }

    public void setPayslips(List<Payslip> payslips) {
        this.payslips = payslips;
    }

    public List<Payslip> getPayslips() {
        return payslips;
    }
}
