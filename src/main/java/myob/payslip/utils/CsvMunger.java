
package myob.payslip.utils;

import myob.payslip.domain.Employee;
import myob.payslip.domain.Payslip;
import myob.payslip.enums.InputCsvColumn;
import myob.payslip.enums.OutputCsvColumn;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public final class CsvMunger {

    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final Logger LOGGER = Logger.getLogger(CsvMunger.class);


    // dont allow class to be instantiated, as its a util class
    private CsvMunger() {

    }

    //Input (first name, last name, annual salary, super rate (%), payment start date)
    //David,Rudd,60050,9%,01 March – 31 March

    public static List<Employee> readCsv(String filePath) throws IOException {

        List<Employee> employeeList = new ArrayList<Employee>();

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader( InputCsvColumn.getColumnNames());
        FileReader fileReader = null;
        CSVParser csvFileParser = null;

        try {

            fileReader = new FileReader(filePath);
            csvFileParser = new CSVParser(fileReader, csvFileFormat);
            List<CSVRecord> csvRecords = csvFileParser.getRecords();
            for (CSVRecord record : csvRecords) {
                employeeList.add( EmployeeFactory.createEmployee(record));
            }

        } catch (FileNotFoundException e) {
            LOGGER.error("Unable to find input csv file");
            throw(e);

        } catch (IOException e) {
            LOGGER.error("Unable to read from the input csv file");
            throw(e);
        } finally {
            try {
                fileReader.close();
                csvFileParser.close();
            } catch (IOException e) {
                System.err.print("cant close fileReader");
                //TODO: handle this properly
            }
        }

        return employeeList;
    }


    // Ryan Chen,01 March – 31 March,10000,2696,7304,1000
    //Output (name, pay period, gross income, income tax, net income, super)
    public static void writeCsv(List<Employee> employees, String filePath) throws IOException {

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        Writer fileWriter = null;
        CSVPrinter csvFilePrinter = null;

        try {
           fileWriter = new FileWriter(filePath);
           csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

           //Create CSV file header
           csvFilePrinter.printRecord(OutputCsvColumn.getColumnHeaders());


           for (Employee employee  : employees) {
               List payslipsRecord = new ArrayList();

               payslipsRecord.add(employee.getFirstName() + " " + employee.getLastName());
               // TODO: format start date and end dates to match expected

               // for each employee we want to add all the payslips we have been asked
               for (Payslip payslip : employee.getPayslips()) {

                   // convert the dates to the date format expected
                   SimpleDateFormat dt = new SimpleDateFormat("dd MMMM");
                   String formattedStartDate = dt.format(payslip.getStartDate());
                   String formattedEndDate = dt.format(payslip.getEndDate());

                   payslipsRecord.add(formattedStartDate + " - " + formattedEndDate);

                   // round the values as expected

                  ;


                   payslipsRecord.add( payslip.getGrossIncome().setScale(0, BigDecimal.ROUND_HALF_UP));
                   payslipsRecord.add(payslip.getIncomeTax().setScale(0, BigDecimal.ROUND_HALF_UP));
                   payslipsRecord.add(payslip.getNetIncome().setScale(0, BigDecimal.ROUND_HALF_UP));
                   payslipsRecord.add(payslip.getIncomeSuper().setScale(0, BigDecimal.ROUND_HALF_UP));
               }

               csvFilePrinter.printRecord(payslipsRecord);
           }

       } catch (IOException e) {

           System.err.print("cant write csv");
           LOGGER.error("Unable to write to the output csv file");
           throw(e);
       } finally {
           try {
               fileWriter.flush();
               fileWriter.close();
               csvFilePrinter.close();

           } catch (IOException e) {

               System.err.print("cant flush buffer");
               //TODO: handle this properly

           }
       }

    }



}
