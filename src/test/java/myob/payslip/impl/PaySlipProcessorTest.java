package myob.payslip.impl;

import myob.payslip.utils.CsvMunger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PayslipProcessorTest {

    //TODO: fix this
    private String csvInputFilePath = "/Users/bcrosby/workspace/myobExercise/src/test/resources/csv/valid-input.csv";
    private String csvOutputFilePath = "/Users/bcrosby/workspace/myobExercise/src/test/resources/csv/valid-output.csv";

    @Test
    public void testPayslipProcessing() throws IOException {

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");

        String expectedRow1 = "name,pay period,gross income,income tax,net income,super";
        String expectedRow2 = "David Rudd,01 March - 31 March,5004,922,4082,450";
        String expectedRow3 = "David Rudd,01 March - 31 March,4167,650,3517,208";
        String expectedRow4 = "Ryan Chen,01 March - 31 March,10000,2696,7304,1000";

        StringBuilder outputRow1 = new StringBuilder();
        StringBuilder outputRow2 = new StringBuilder();
        StringBuilder outputRow3 = new StringBuilder();
        StringBuilder outputRow4 = new StringBuilder();

        PayslipProcessor.processPaySlips(csvInputFilePath, csvOutputFilePath);

        List<CSVRecord> records = CsvMunger.readCsv(csvOutputFilePath);

        CSVPrinter csvFilePrinter = new CSVPrinter(outputRow1, csvFileFormat);
        csvFilePrinter.printRecord(records.get(0));

        csvFilePrinter = new CSVPrinter(outputRow2, csvFileFormat);
        csvFilePrinter.printRecord(records.get(1));

        csvFilePrinter = new CSVPrinter(outputRow3, csvFileFormat);
        csvFilePrinter.printRecord(records.get(2));

        csvFilePrinter = new CSVPrinter(outputRow4, csvFileFormat);
        csvFilePrinter.printRecord(records.get(3));

        assertEquals("First row is incorrect", expectedRow1, outputRow1.toString().trim());
        assertEquals("First second is incorrect", expectedRow2, outputRow2.toString().trim());
        assertEquals("First third is incorrect", expectedRow3, outputRow3.toString().trim());
        assertEquals("First fourth is incorrect", expectedRow4, outputRow4.toString().trim());

    }
}
