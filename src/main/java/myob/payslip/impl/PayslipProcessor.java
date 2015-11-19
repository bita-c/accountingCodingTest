package myob.payslip.impl;

import myob.payslip.domain.Employee;
import myob.payslip.domain.Payslip;
import myob.payslip.utils.CsvMunger;
import myob.payslip.utils.EmployeeFactory;
import myob.payslip.utils.PayslipCalculator;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PayslipProcessor {

    private static final Logger LOGGER = Logger.getLogger(PayslipProcessor.class);

    public static void processPaySlips() {

        // temporarily pass the path in the file system so we can get things running
        String inputCsvPath = "/Users/bcrosby/workspace/myobExercise/src/main/resources/csv/input.csv";
        String outputCsvPath = "/Users/bcrosby/workspace/myobExercise/src/main/resources/csv/output.csv";


        // use spring to wire things up
        // read the location of the input and output csv files

        // call the csvmunger and get the Employees created
        List<Employee>  employees = new ArrayList<Employee>();
        List<CSVRecord> csvRecords = new ArrayList<CSVRecord>();
        try {
            csvRecords = CsvMunger.readCsv(inputCsvPath);

        } catch(IOException e ) {

            System.err.print("enable to read input");
          // create own exception class and throw appropriately

        }

        for (CSVRecord record : csvRecords) {
            employees.add(EmployeeFactory.createEmployee(record));
        }

        // process each employee and calculate payslip values
        for (Employee employee : employees) {

            for (Payslip payslip : employee.getPayslips()) {

                payslip.setGrossIncome(PayslipCalculator.calculateGrossIncome(employee));
                payslip.setIncomeTax(PayslipCalculator.calculateIncomeTax(employee));
                payslip.setNetIncome(PayslipCalculator.calculateNetIncome(payslip));
                payslip.setIncomeSuper(PayslipCalculator.calculateSuper(employee, payslip));
            }
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
