
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public final class CsvMunger {

    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final Logger LOGGER = Logger.getLogger(CsvMunger.class);

    // don't allow class to be instantiated, as its a util class
    private CsvMunger() {
    }

    /**
     * Reads rows from csv file path passed as input and returns a List of CSVRecord
     * associated with each row.
     *
     * @param filePath
     * @return List containing CSVRecords
     * @throws IOException
     */
    public static List<CSVRecord> readCsv(String filePath) throws IOException {

        List<CSVRecord> csvRecords = new ArrayList<CSVRecord>();
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(InputCsvColumn.getColumnNames());
        FileReader fileReader = null;
        CSVParser csvFileParser = null;

        try {
            fileReader = new FileReader(filePath);
            csvFileParser = new CSVParser(fileReader, csvFileFormat);
            csvRecords = csvFileParser.getRecords();

        } catch (FileNotFoundException e) {
            LOGGER.error("Unable to find input csv file");
            throw(e);

        } catch (IOException e) {
            LOGGER.error("Unable to read from the input csv file");
            throw(e);

        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
                if (csvFileParser != null) {
                    csvFileParser.close();
                }
            } catch (IOException e) {
                // Log this, we dont want to be too loud here
                LOGGER.warn("Unable to close FileReader or CSVParser");
            }
        }
        return csvRecords;
    }

    /**
     * Writes to a csv filePath, a set of records containing payslip related information
     * for each employee, in the list of employees passed.
     *
     * @param employees
     * @param filePath
     * @throws IOException
     */
    public static void writeCsv(List<Employee> employees, String filePath) throws IOException {

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        Writer fileWriter = null;
        CSVPrinter csvFilePrinter = null;

        try {
           fileWriter = new FileWriter(filePath);
           csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
           csvFilePrinter.printRecord(OutputCsvColumn.getColumnHeaders());

           for (Employee employee  : employees) {
               List payslipsRecord = new ArrayList();

               payslipsRecord.add(employee.getFirstName() + " " + employee.getLastName());

               // for each employee we want to add all the payslips we have been asked
               for (Payslip payslip : employee.getPayslips()) {

                   // convert the dates to the date format expected
                   SimpleDateFormat dt = new SimpleDateFormat("dd MMMM");
                   String formattedStartDate = dt.format(payslip.getStartDate());
                   String formattedEndDate = dt.format(payslip.getEndDate());

                   payslipsRecord.add(formattedStartDate + " - " + formattedEndDate);

                   payslipsRecord.add( payslip.getGrossIncome());
                   payslipsRecord.add(payslip.getIncomeTax());
                   payslipsRecord.add(payslip.getNetIncome());
                   payslipsRecord.add(payslip.getIncomeSuper());
               }
               csvFilePrinter.printRecord(payslipsRecord);
           }

       } catch (IOException e) {
           LOGGER.error("Unable to write to the output csv file");
           throw(e);

       } finally {
           try {
               fileWriter.flush();
               fileWriter.close();
               csvFilePrinter.close();

           } catch (IOException e) {
               LOGGER.warn("Unable to flush FileWriter Buffer or close FileWriter");
           }
       }
    }
}
