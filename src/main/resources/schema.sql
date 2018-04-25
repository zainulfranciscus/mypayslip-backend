CREATE TABLE IF NOT EXISTS payslip (
    employee_first_name varchar(255) not null,
    employee_last_name varchar(255) not null,
    pay_date date not null,
    annual_income decimal(19,2) not null,
    gross_income decimal(19,2) not null,
    income_tax decimal(19,2) not null,
    net_income decimal(19,2) not null,
    pay decimal(19,2) not null,
    pay_frequency varchar(255) not null,
    super_amount decimal(19,2) not null,
    primary key (employee_first_name, employee_last_name, pay_date),
    check(annual_income >= 0),
    check(gross_income >= 0),
    check(income_tax >= 0),
    check(net_income >= 0),
    check(pay >= 0),
    check(super_amount >= 0)
);

CREATE TABLE IF NOT EXISTS tax (
    max_taxable_income decimal(19,2) not null,
    min_taxable_income decimal(19,2) not null,
    base_tax decimal(19,2)  not null,
    tax_per_dollar_in_cents decimal(19,2)  not null,
    tax_per_dollar_over decimal(19,2)  not null,
    primary key (max_taxable_income, min_taxable_income),
    check(min_taxable_income >= 0),
    check(base_tax >= 0),
    check(tax_per_dollar_in_cents >= 0),
    check(tax_per_dollar_over >= 0)
);