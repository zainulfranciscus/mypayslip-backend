DELETE FROM TAX;
INSERT INTO TAX(min_taxable_income,max_taxable_income,base_tax,tax_per_dollar_in_cents,tax_per_dollar_over) values (0,18200,0,0,0);
INSERT INTO TAX(min_taxable_income,max_taxable_income,base_tax,tax_per_dollar_in_cents,tax_per_dollar_over) values (18201,37000,0,19,18200);
INSERT INTO TAX(min_taxable_income,max_taxable_income,base_tax,tax_per_dollar_in_cents,tax_per_dollar_over) values (37001,80000,3572,32.5,37000);
INSERT INTO TAX(min_taxable_income,max_taxable_income,base_tax,tax_per_dollar_in_cents,tax_per_dollar_over) values (80001,180000,17547,37,80000);
INSERT INTO TAX(min_taxable_income,max_taxable_income,base_tax,tax_per_dollar_in_cents,tax_per_dollar_over) values (180001,200000,54547,45,180000);