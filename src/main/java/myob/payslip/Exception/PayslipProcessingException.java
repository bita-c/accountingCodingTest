package myob.payslip.Exception;

public class PayslipProcessingException extends RuntimeException {

    private static final long serialVersionUID = -8143972845435353474L;

    private String details;

    public PayslipProcessingException(String passedReason, String passedDetails) {
        super(passedReason);
        this.details = passedDetails;
    }

    public PayslipProcessingException(String passedReason) {
        super(passedReason);
    }

    public void setFaultInfo(String passedDetails) {
        this.details = passedDetails;

    }

    public String getFaultInfo() {
        return this.details;
    }

}
