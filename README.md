Employee monthly payslip Exercise

Assumptions:

assuming additional tax is always per each dollar over the base salary rate for tax bracket <br/>
assuming date period is give in this format: dd MMMM – dd MMMM  or dd MMMM y – dd MMMM y <br/>

How to run:

Project builds with maven and packages as a jar with the name payslip-0.0.1-RELEASE.jar <br/>
Run by providing the path to the input csv file as argument: <br/>
java -jar payslip-0.0.1-RELEASE.jar ./input.csv  <br/>
Output is added to the current directory from which it is being run (output.csv)  <br/>


Future Considerations:

I am adding list of payslips for employee but at this stage it doesnt quite apply as we require more than just name to differentiate employees. <br/>
Based on this, currently not assuming that same name means the same person. <br/>
