package org.accounting.controller.response;

import org.accounting.model.payslip.Payslip;

public class PayslipResponse extends Response<Payslip>{

    private Payslip payslip;
    private static  final String PAYSLIP_ALREADY_EXIST = "Payslip for %s %s already exist";


    public PayslipResponse(int status,String message, Payslip p) {
        super(status,message);
        this.payslip = p;
    }
    public PayslipResponse(int status, Payslip p) {
        super(status,String.format(PAYSLIP_ALREADY_EXIST,p.getEmployeeFirstName(),p.getEmployeeLastName()));
        this.payslip=p;
    }

    @Override
    public Payslip getResponseObject() {
        return payslip;
    }
}
