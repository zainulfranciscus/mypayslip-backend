package org.accounting.controller;

import org.accounting.controller.response.PayslipResponse;
import org.accounting.controller.response.TaxResponse;
import org.accounting.controller.response.TaxResponseBuilder;
import org.accounting.controller.validator.EmployeeRequestValidator;
import org.accounting.controller.validator.PayslipRequestValidator;
import org.accounting.model.employee.EmployeeBuilder;
import org.accounting.model.payslip.Payslip;
import org.accounting.model.payslip.PayslipBuilder;
import org.accounting.model.payslip.impl.PayslipEntity;
import org.accounting.model.tax.Tax;
import org.accounting.service.impl.PayslipServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.accounting.model.payslip.Payslip.MONTHLY_PAY_FREQUENCY;
import static org.apache.commons.lang3.StringUtils.trim;

@CrossOrigin(maxAge = 18000)
@RestController
@RequestMapping("/payslip")
public class PayslipController {
    public static final String SUCCESS_REQUEST_MESSAGE = "Success";


    @Autowired
    private PayslipServiceImpl payslipService;

    @Autowired
    private EmployeeRequestValidator employeeRequestValidator;

    @Autowired
    private PayslipRequestValidator payslipRequestValidator;

    public void setPayslipService(PayslipServiceImpl service) {
        this.payslipService = service;
    }


    @GetMapping(value = "/tax", produces = "application/json")
    public TaxResponse getTaxForEmployee(@RequestParam("annualSalary") String salary) {


        employeeRequestValidator.validateSalary(salary);


        TaxResponseBuilder taxResponseBuilder = new TaxResponseBuilder();

        if (employeeRequestValidator.hasErrors()) {

            taxResponseBuilder.withMessage(employeeRequestValidator.validate());
            taxResponseBuilder.withBadRequestStatus();
            return taxResponseBuilder.build();
        }

        Tax t = payslipService.findTaxBracket(new BigDecimal(trim(salary)));

        taxResponseBuilder.withMessage(SUCCESS_REQUEST_MESSAGE);
        taxResponseBuilder.withOKHttpStatus();
        taxResponseBuilder.withTax(t);

        return taxResponseBuilder.build();
    }

    @PostMapping(value = "/savePayslip", produces = "application/json")
    public PayslipResponse savePayslip(@RequestParam("annualSalary") String salary,
                                       @RequestParam("grossIncome") String grossIncome,
                                       @RequestParam("netIncome") String netIncome,
                                       @RequestParam("superRate") String superRate,
                                       @RequestParam("pay") String pay,
                                       @RequestParam("firstName") String firstName,
                                       @RequestParam("lastName") String lastName,
                                       @RequestParam("incomeTax") String incomeTax) {

        payslipRequestValidator.validateSalary(salary);
        payslipRequestValidator.validateGrossIncome(grossIncome);
        payslipRequestValidator.validateNetIncome(netIncome);
        payslipRequestValidator.validateSuperRate(superRate);
        payslipRequestValidator.validatePay(pay);
        payslipRequestValidator.validateFirstName(firstName);
        payslipRequestValidator.validateLastName(lastName);
        payslipRequestValidator.validateIncomeTax(incomeTax);

        if (payslipRequestValidator.hasErrors()) {
            return new PayslipResponse(HttpStatus.BAD_REQUEST.value(), payslipRequestValidator.validate(), new PayslipBuilder().build());
        }
        EmployeeBuilder employeeBuilder = new EmployeeBuilder();
        employeeBuilder.withSuperRate(new BigDecimal(superRate))
                .withPayDate(LocalDate.now())
                .withLastName(trim(lastName))
                .withFirstName(trim(firstName));

        Payslip payslip = payslipService.findPayslip(employeeBuilder.build());
        if (payslip != null) {
            PayslipResponse payslipResponse = new PayslipResponse(HttpStatus.BAD_REQUEST.value(), payslip);
            return payslipResponse;
        }

        PayslipBuilder payslipBuilder = new PayslipBuilder();
        payslipBuilder.withAnnualIncome(new BigDecimal(trim(salary)))
                .withGrossIncome(new BigDecimal(trim(grossIncome)))
                .withNetIncome(new BigDecimal(trim(netIncome)))
                .withSuperAmount(new BigDecimal(trim(superRate)))
                .withPay(new BigDecimal(trim(pay)))
                .withLastName(trim(lastName))
                .withPayFrequency(MONTHLY_PAY_FREQUENCY)
                .withFirstName(trim(firstName))
                .withPayDate(LocalDate.now())
                .withIncomeTax(new BigDecimal(trim(incomeTax)));

        PayslipEntity p = payslipBuilder.build();

        payslipService.save(p);

        return new PayslipResponse(HttpStatus.OK.value(), SUCCESS_REQUEST_MESSAGE, p);

    }


    public void setEmployeeValidator(EmployeeRequestValidator validator) {
        this.employeeRequestValidator = validator;
    }

    public void setPayslipValidator(PayslipRequestValidator validator) {
        this.payslipRequestValidator = validator;
    }
}
