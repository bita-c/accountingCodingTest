package myob.payslip.domain;

public class Employee {
    
    //first name, last name, annual salary(positive integer) 
    //and super rate(0% - 50% inclusive), payment start date, 
      
    private String firstName;
    private String lastName;
    private float annualSalary;
    private float superRate;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public float getAnnualSalary() {
        return annualSalary;
    }

    public float getSuperRate() {
        return superRate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAnnualSalary(float annualSalary) {
        this.annualSalary = annualSalary;
    }

    public void setSuperRate(float superRate) {
        this.superRate = superRate;
    }    
}
