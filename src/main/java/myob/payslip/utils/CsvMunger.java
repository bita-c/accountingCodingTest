
package myob.payslip.utils;

import myob.payslip.domain.Employee;
import myob.payslip.enums.OutputCsvColumn;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public final class CsvMunger {

    private static final String CSV_COMMA_SEPARATOR = ",";
    private static final Logger LOGGER = Logger.getLogger(CsvMunger.class);


    // dont allow class to be instantiated, as its a util class
    private CsvMunger() {

    }

    //Input (first name, last name, annual salary, super rate (%), payment start date)
    //David,Rudd,60050,9%,01 March – 31 March

    public static List<Employee> readCsv(String filePath) throws IOException {

        List<Employee> employeeList = new ArrayList<Employee>();

        try {
            Reader fileReader = new FileReader(filePath);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(fileReader);

            for (CSVRecord record : records) {
                employeeList.add( EmployeeFactory.createEmployee(record));
            }

        } catch (FileNotFoundException e) {
            LOGGER.error("Unable to find input csv file");
            throw(e);

        } catch (IOException e) {
            LOGGER.error("Unable to read from the input csv file");
            throw(e);
        }

        return employeeList;
    }


    // Ryan Chen,01 March – 31 March,10000,2696,7304,1000
    //Output (name, pay period, gross income, income tax, net income, super)
    public static void writeCsv(List<Employee> employees, String filePath) throws IOException {

       CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(CSV_COMMA_SEPARATOR);

       try {
           Writer fileWriter = new FileWriter(filePath);
           CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

           //Create CSV file header
           csvFilePrinter.printRecord(OutputCsvColumn.getColumnHeaders());


           for (Employee employee  : employees) {
               List payslipsRecord = new ArrayList();

               payslipsRecord.add(employee.getFirstName() + " " + employee.getLastName());
               // TODO: format start date and end dates to match expected
               payslipsRecord.add(employee.getPayslip().getStartDate() + " - " + employee.getPayslip().getEndDate());
               payslipsRecord.add(employee.getPayslip().getGrossIncome().floatValue());
               payslipsRecord.add(employee.getPayslip().getIncomeTax().floatValue());
               payslipsRecord.add(employee.getPayslip().getNetIncome().floatValue());
               payslipsRecord.add(employee.getPayslip().getIncomeSuper().floatValue());

               System.err.print(payslipsRecord);

               csvFilePrinter.printRecord(payslipsRecord);
           }

       } catch (IOException e) {

           System.err.print("cant write csv");
           LOGGER.error("Unable to write to the output csv file");
           throw(e);
       }

    }



}
