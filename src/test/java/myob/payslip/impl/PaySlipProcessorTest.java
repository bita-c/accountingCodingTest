package myob.payslip.impl;

import org.junit.After;
import org.junit.Before;

public class PaySlipProcessorTest {

    // the intention here is to do end to end testing
    // pass an input file , generate output file
    // ensure output file matches expected output file
    // (can read the csv file into a string and compare)

    @Before
    public void setup() {
        // Create the object instance to do testing with.
        PayslipProcessor payslipProcessor = new PayslipProcessor();
    }

    @After
    public void tearDown() {

    }

}
