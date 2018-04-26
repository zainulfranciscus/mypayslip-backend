package org.accounting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.accounting.controller.response.PayslipResponse;
import org.accounting.controller.response.TaxResponse;
import org.accounting.model.employee.Employee;
import org.accounting.model.employee.EmployeeBuilder;
import org.accounting.model.payslip.Payslip;
import org.accounting.model.payslip.PayslipBuilder;
import org.accounting.model.payslip.impl.PayslipEntity;
import org.accounting.model.tax.Tax;
import org.accounting.service.impl.PayslipServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static junit.framework.TestCase.assertNull;
import static org.accounting.controller.PayslipController.SUCCESS_REQUEST_MESSAGE;
import static org.accounting.controller.validator.PayslipRequestValidatorTest.NEGATIVE_NUMBER;
import static org.accounting.controller.validator.RequestValidator.SALARY;
import static org.accounting.controller.validator.RequestValidator.cannotBeLessThan0;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PayslipControllerIT extends ControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private PayslipServiceImpl payslipService;

    private JacksonTester<TaxResponse> taxResponseJsonTester;

    private JacksonTester<PayslipResponse> payslipResponseJsonTester;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());

    }

    @Test
    public void shouldReturnATaxThatMatchesTheEmployeesSalary() throws IOException {

        Tax taxBracketForThisEmployee = payslipService.findTaxBracket(new BigDecimal(annualSalary));
        TaxResponse taxResponse = new TaxResponse(taxBracketForThisEmployee, HttpStatus.OK.value(), SUCCESS_REQUEST_MESSAGE);
        String validTaxRequest = getTaxRequest();
        this.webClient.get().uri(validTaxRequest).exchange().expectStatus().isOk()
                .expectBody(String.class).isEqualTo(taxResponseJsonTester.write(taxResponse).getJson());

    }

    @Test
    public void shouldReturnATaxResponse_withBadRequest_whenSalaryIsLesssThan0() throws IOException {
        String salaryLessThan0 = "-1";
        String requestWithInvalidSalaryParameter = String.format(GET_TAX_REQUEST_PARAMETERS, salaryLessThan0);

        TaxResponse taxResponse = new TaxResponse(null, HttpStatus.BAD_REQUEST.value(), cannotBeLessThan0(SALARY));

        this.webClient.get().uri(requestWithInvalidSalaryParameter).exchange().expectStatus().isOk()
                .expectBody(String.class).isEqualTo(taxResponseJsonTester.write(taxResponse).getJson());
    }

    @Test
    public void shouldSavePayslip_whenPayslipHasNotBeenGeneratedForThisEmployee() throws IOException {

        Payslip payslip = getPayslip();
        PayslipResponse response = new PayslipResponse(HttpStatus.OK.value(), SUCCESS_REQUEST_MESSAGE, payslip);

        this.webClient.post().uri(getPayslipRequest(firstName, lastName))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo(payslipResponseJsonTester.write(response).getJson());

        assertEquals(payslip, payslipService.findPayslip(employee(payslip)));
    }

    @Test
    public void shouldHaveNotSavedThePayslip_whenThisEmployeeHasBeenPaid() throws IOException {

        String firstName = "Joe";
        String lastName = "Clint";

        PayslipBuilder payslipBuilder = getPayslipBuilder();
        payslipBuilder.withFirstName(firstName).withLastName(lastName).withPayDate(LocalDate.now());
        PayslipEntity payslip = payslipBuilder.build();
        payslipService.save(payslip);

        PayslipResponse response = new PayslipResponse(HttpStatus.BAD_REQUEST.value(), payslip);
        this.webClient.post().uri(getPayslipRequest(firstName, lastName))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo(payslipResponseJsonTester.write(response).getJson());

    }

    @Test
    public void shouldReturnABadHTTPResponse_whenAmountsInPayslipAreNegative() {


        BigDecimal negativeNumber = new BigDecimal(NEGATIVE_NUMBER);
        String firstName = "Employee with Negative Payslip";
        String lastName = "Test Employee";

        Payslip payslip = getPayslipBuilder().withAnnualIncome(negativeNumber)
                .withGrossIncome(negativeNumber)
                .withIncomeTax(negativeNumber)
                .withNetIncome(negativeNumber)
                .withSuperAmount(negativeNumber)
                .withPay(negativeNumber)
                .withFirstName(firstName)
                .withLastName(lastName).build();

        String payslipRequest = String.format(SAVE_PAYSLIP_REQUEST_PARAMETERS,
                NEGATIVE_NUMBER,
                NEGATIVE_NUMBER,
                NEGATIVE_NUMBER,
                NEGATIVE_NUMBER,
                NEGATIVE_NUMBER,
                NEGATIVE_NUMBER,
                firstName,
                lastName);


        this.webClient.post().uri(payslipRequest)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .returnResult().getResponseBody().contains("300");

        assertNull(payslipService.findPayslip(employee(payslip)));
    }




    private Employee employee(Payslip payslip){

        EmployeeBuilder employeeBuilder = new EmployeeBuilder();
        employeeBuilder.withFirstName(payslip.getEmployeeFirstName());
        employeeBuilder.withLastName(payslip.getEmployeeLastName());
        employeeBuilder.withPayDate(payslip.getPayDate());

        return employeeBuilder.build();
    }

}
