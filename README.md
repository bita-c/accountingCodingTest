Employee monthly payslip Exercise

Assumptions:

assuming additional tax is always per each dollar over the base salary rate for tax bracket
assuming date period is give in this format: dd MMMM – dd MMMM  or dd MMMM y – dd MMMM y

How to run:

Project builds with maven and packages as a jar with the name payslip-0.0.1-RELEASE.jar
Run by providing the path to the input csv file as argument:
java -jar payslip-0.0.1-RELEASE.jar ./input.csv
Output is added to the current directory from which it is being run (output.csv)


Future Considerations:

I am adding list of payslips for employee but at this stage it doesnt quite apply as we require more than just name to differentiate employees
Based on this, currently not assuming that same name means the same person