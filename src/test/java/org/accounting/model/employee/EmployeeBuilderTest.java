package org.accounting.model.employee;



import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class EmployeeBuilderTest {

    private EmployeeBuilder employeeBuilder;
    private Employee employee;
    private BigDecimal salary;
    private String firstName;
    private String lastName;
    private BigDecimal superRate;
    private LocalDate now;

    @Before
    public void setup(){
        employeeBuilder = new EmployeeBuilder();

        salary = new BigDecimal(23700);
        employeeBuilder.withAnnualSalary(salary);

        firstName = "John";
        employeeBuilder.withFirstName(firstName);

        lastName = "Doe";
        employeeBuilder.withLastName(lastName);

        now = LocalDate.now();
        employeeBuilder.withPayDate(now);

        superRate = new BigDecimal(9);
        employeeBuilder.withSuperRate(superRate);

        employee = employeeBuilder.build();
    }

    @Test
    public void testSalaryIsEqualTo_salary(){
        assertEquals(employee.getAnnualSalary(),salary);
    }

    @Test
    public void testFirstNameIsEqualTo_firstName(){
        assertEquals(employee.getFirstName(),firstName);
    }

    @Test
    public void testLastNameIsEqualTo_lastName(){
        assertEquals(employee.getLastName(),lastName);
    }

    @Test
    public void testSuperRateIsEqualTo_superRate(){
        assertEquals(employee.getSuperRate(),superRate);
    }

    @Test
    public void twoEmployeesAreEqual_whenFirstAndLastName_areEqual(){

        EmployeeBuilder employeeBuilder = new EmployeeBuilder();
        String firstName = "Joe";
        String lastName = "Doe";
        Employee emp1 = employeeBuilder.withFirstName(firstName).withLastName(lastName).build();
        Employee emp2 = employeeBuilder.withFirstName(firstName).withLastName(lastName).build();

        assertEquals(emp1,emp2);

    }

    @Test
    public void twoEmployeesAreEqual_whenFirstAndLastName_areDifferent(){

        EmployeeBuilder employeeBuilder = new EmployeeBuilder();
        String firstName = "Joe";

        Employee emp1 = employeeBuilder.withFirstName(firstName).withLastName("zoe").build();
        Employee emp2 = employeeBuilder.withFirstName(firstName).withLastName("moe").build();

        assertNotSame(emp1,emp2);

    }

    @Test
    public void twoEmployeeWithTheSameName_HasEqualHasCode(){
        assertEquals(employeeBuilder.build().hashCode(),employeeBuilder.build().hashCode());
    }

    @Test
    public void twoEmployeeWithDifferentName_HasDifferentqualHasCode(){
        assertNotSame(employeeBuilder.build().hashCode(),employeeBuilder.withLastName("Zoe").build().hashCode());

    }
}
