package org.accounting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.accounting.controller.response.PayslipResponse;
import org.accounting.controller.response.TaxResponse;
import org.accounting.controller.response.TaxResponseBuilder;
import org.accounting.controller.validator.EmployeeRequestValidator;
import org.accounting.controller.validator.PayslipRequestValidator;
import org.accounting.model.employee.Employee;
import org.accounting.model.employee.EmployeeBuilder;
import org.accounting.model.payslip.PayslipBuilder;
import org.accounting.service.impl.PayslipServiceImpl;
import org.junit.Before;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.accounting.controller.validator.EmployeeRequestValidator.CANNOT_BE_LESS_THAN_0;
import static org.accounting.controller.PayslipController.SUCCESS_REQUEST_MESSAGE;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class PayslipControllerTest extends ControllerTest {

    private MockMvc mockMvc;
    private PayslipServiceImpl mockService;
    private JacksonTester<TaxResponse> taxResponseJsonTester;
    private JacksonTester<PayslipResponse> payslipResponseJsonTester;
    private PayslipController controller;
    private EmployeeRequestValidator mockEmployeeRequestValidator;
    private PayslipRequestValidator mockPayslipRequestValidator;



    private String getTaxRequest;
    private String postPayslipRequest;


    @Before
    public void setup() {

        mockService = mock(PayslipServiceImpl.class);
        mockEmployeeRequestValidator = mock(EmployeeRequestValidator.class);
        mockPayslipRequestValidator = mock(PayslipRequestValidator.class);

        controller = new PayslipController();
        controller.setPayslipService(mockService);
        controller.setEmployeeValidator(mockEmployeeRequestValidator);
        controller.setPayslipValidator(mockPayslipRequestValidator);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        JacksonTester.initFields(this, new ObjectMapper());


        getTaxRequest = getTaxRequest();
        postPayslipRequest = getPayslipRequest(firstName,lastName );

    }

    @Test
    public void shouldReturnATax() throws Exception {

        when(mockService.findTaxBracket(Mockito.any(BigDecimal.class))).thenReturn(tax);

        TaxResponseBuilder taxResponseBuilder = new TaxResponseBuilder();
        taxResponseBuilder.withTax(tax);
        taxResponseBuilder.withMessage(SUCCESS_REQUEST_MESSAGE);
        taxResponseBuilder.withOKHttpStatus();

        MockHttpServletResponse response = mockMvc.perform(
                get(getTaxRequest)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(response.getContentAsString(), taxResponseJsonTester.write(taxResponseBuilder.build()).getJson());

    }


    @Test
    public void shouldReturnABadRequestResponse_whenRequestParametersHasInvalidValue() throws Exception {

        String invalidSalaryValue = "-1";
        mockEmployeeRequestValidator.validateSalary(invalidSalaryValue);
        when(mockEmployeeRequestValidator.hasErrors()).thenReturn(true);
        when(mockEmployeeRequestValidator.validate()).thenReturn(CANNOT_BE_LESS_THAN_0);

        MockHttpServletResponse response = mockMvc.perform(
                get(getTaxRequest)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        TaxResponseBuilder taxResponseBuilder = new TaxResponseBuilder();
        taxResponseBuilder.withMessage(CANNOT_BE_LESS_THAN_0);
        taxResponseBuilder.withBadRequestStatus();


        assertEquals(response.getContentAsString(), taxResponseJsonTester.write(taxResponseBuilder.build()).getJson());


    }

    @Test
    public void shouldSavePayslip_whenNoPayslipExist() throws Exception {


        when(mockService.findPayslip(Mockito.any(Employee.class))).thenReturn(null);

        MockHttpServletResponse response = mockMvc.perform(
                post(postPayslipRequest)).andDo(print()).andReturn()
                .getResponse();

        verify(mockService, times(1)).findPayslip(Mockito.any(Employee.class));

        verify(mockService, times(1)).save(getPayslip());

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void shouldHavePayslipResponse_withAnError_BecausePayslipExist() throws Exception {

        PayslipBuilder payslipBuilder = new PayslipBuilder();
        payslipBuilder.withFirstName(firstName).withLastName(lastName);

        EmployeeBuilder employeeBuilder = new EmployeeBuilder();
        employeeBuilder.withFirstName(firstName).withLastName(lastName).withAnnualSalary(new BigDecimal(annualSalary));
        Employee employee = employeeBuilder.build();

        when(mockService.findPayslip(employee)).thenReturn(payslipBuilder.build());
        MockHttpServletResponse response = mockMvc.perform(
                post(postPayslipRequest)).andDo(print()).andReturn()
                .getResponse();


        verify(mockService, times(1)).findPayslip(employee);

        PayslipBuilder builder = new PayslipBuilder();
        builder.withFirstName(firstName);
        builder.withLastName(lastName);

        PayslipResponse payslipResponse = new PayslipResponse(HttpStatus.BAD_REQUEST.value(), builder.build());
        String responseFromSavePayslip = payslipResponseJsonTester.write(payslipResponse).getJson();


        assertEquals(responseFromSavePayslip,response.getContentAsString());

    }

    @Test
    public void shouldReturnAPayslipResponse_WithBadRequest_WhenAParameterIsInvalid() throws Exception {
        String invalidNetIncome = "-1";

        mockPayslipRequestValidator.validateSalary(annualSalary);
        mockPayslipRequestValidator.validateGrossIncome(grossIncome);
        mockPayslipRequestValidator.validateNetIncome(invalidNetIncome);
        mockPayslipRequestValidator.validateSuperRate(superRate);
        mockPayslipRequestValidator.validatePay(pay);
        mockPayslipRequestValidator.validateFirstName(firstName);
        mockPayslipRequestValidator.validateLastName(lastName);

        when(mockPayslipRequestValidator.hasErrors()).thenReturn(true);
        when(mockPayslipRequestValidator.validate()).thenReturn(CANNOT_BE_LESS_THAN_0);

        MockHttpServletResponse response = mockMvc.perform(
                post(postPayslipRequest)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn()
                .getResponse();

        PayslipBuilder payslipBuilder = new PayslipBuilder();

        PayslipResponse payslipResponse = new PayslipResponse(HttpStatus.BAD_REQUEST.value(),CANNOT_BE_LESS_THAN_0,payslipBuilder.build());


        assertEquals(response.getContentAsString(), payslipResponseJsonTester.write(payslipResponse).getJson());


        verify(mockPayslipRequestValidator,times(1)).hasErrors();
        verify(mockPayslipRequestValidator,times(1)).validate();
    }



}
