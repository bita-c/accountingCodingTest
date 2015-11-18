package myob.payslip.impl;

import myob.payslip.domain.Employee;
import myob.payslip.utils.CsvMunger;
import myob.payslip.utils.PayslipCalculator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PayslipProcessor {

    public static void processPaySlips() {

        // temporarily pass the path in the file system so we can get things running
        String inputCsvPath = "/Users/bcrosby/workspace/myobExercise/src/main/resources/csv/input.csv";
        String outputCsvPath = "/Users/bcrosby/workspace/myobExercise/src/main/resources/csv/output.csv";


        // use spring to wire things up
        // read the location of the input and output csv files

        // call the csvmunger and get the Employees created
        List<Employee>  employees = new ArrayList<Employee>();
        try {
            employees = CsvMunger.readCsv(inputCsvPath);

        } catch(IOException e ) {

            System.err.print("enable to read input");
          // create own exception class and throw appropriately

        }

        // process each employee and calculate payslip values
        for (Employee employee : employees) {

            employee.getPayslip().setGrossIncome(PayslipCalculator.calculateGrossIncome(employee));
            employee.getPayslip().setIncomeTax(PayslipCalculator.calculateIncomeTax(employee));
            employee.getPayslip().setNetIncome(PayslipCalculator.calculateNetIncome(employee));
            employee.getPayslip().setIncomeSuper(PayslipCalculator.calculateSuper(employee));
        }

        // call the csvmunger and print the employees
        try {
            CsvMunger.writeCsv(employees, outputCsvPath);

        } catch (IOException e) {
            System.err.print("enable to write output");
        }
    }


    public static void main(String[] args){
        processPaySlips();
    }
}
