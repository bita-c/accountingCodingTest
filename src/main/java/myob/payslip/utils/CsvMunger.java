
package myob.payslip.utils;

import myob.payslip.enums.InputCsvColumn;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CsvMunger {

    //Input (first name, last name, annual salary, super rate (%), payment start date)
    //David,Rudd,60050,9%,01 March – 31 March

    private static final Logger LOGGER = Logger.getLogger(CsvMunger.class);

    public static List<Map<InputCsvColumn, String>> readCsv() throws IOException {

        List<Map<InputCsvColumn, String>> employeeList = new ArrayList<Map<InputCsvColumn, String>>();

        try {
            Reader fileReader = new FileReader("path/to/file.csv");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(fileReader);

            for (CSVRecord record : records) {

                Map<InputCsvColumn, String> ColumnNamesValuesMap = new HashMap<InputCsvColumn, String>();

                String firstName = record.get(InputCsvColumn.FIRST_NAME);
                String lastName = record.get(InputCsvColumn.LAST_NAME);
                String annualSalary = record.get(InputCsvColumn.ANNUAL_SALARY);
                String superRate = record.get(InputCsvColumn.SUPER_RATE);
                String paymentPeriod = record.get(InputCsvColumn.PAYMENT_PERIOD);

                // Check input.
                if (firstName == null || firstName.trim().isEmpty()) {
                    throw new IllegalArgumentException("Invalid input: firstName is NULL or empty");
                }
                if (lastName == null || lastName.trim().isEmpty()) {
                    throw new IllegalArgumentException("Invalid input: lastName is NULL or empty");
                }
                if (annualSalary == null || annualSalary.trim().isEmpty()) {
                    throw new IllegalArgumentException("Invalid input: annualSalary is NULL or empty");
                }
                if (superRate == null || superRate.trim().isEmpty()) {
                    throw new IllegalArgumentException("Invalid input: superRate is NULL or empty");
                }
                if (paymentPeriod == null || paymentPeriod.trim().isEmpty()) {
                    throw new IllegalArgumentException("Invalid input: paymentPeriod is NULL or empty");
                }

                ColumnNamesValuesMap.put(InputCsvColumn.FIRST_NAME, firstName);
                ColumnNamesValuesMap.put(InputCsvColumn.LAST_NAME, lastName);
                ColumnNamesValuesMap.put(InputCsvColumn.ANNUAL_SALARY, annualSalary);
                ColumnNamesValuesMap.put(InputCsvColumn.SUPER_RATE, superRate);
                ColumnNamesValuesMap.put(InputCsvColumn.PAYMENT_PERIOD, paymentPeriod);
                employeeList.add(ColumnNamesValuesMap);
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


    // Ryan,Chen,120000,10%,01 March – 31 March
    public static void writeCsv() {

        //final Appendable out = "";
        //final CSVPrinter printer = CSVFormat.DEFAULT.withHeader("H1", "H2").print(out);

    }



}
