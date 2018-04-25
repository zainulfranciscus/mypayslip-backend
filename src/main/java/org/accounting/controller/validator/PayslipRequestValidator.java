package org.accounting.controller.validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class PayslipRequestValidator extends RequestValidator {

    protected static final String GROSS_INCOME = "Gross income";
    public static final String NET_INCOME = "Net income";
    public static final String PAY = "Pay";
    public static final String INCOME_TAX = "Income tax";

    @Override
    public String validate() {
        return super.validate();
    }

    public void validateGrossIncome(String gross) {

        gross = StringUtils.trim(gross);

        if (StringUtils.isBlank(gross)) {
            builder.append(mustNotBeBlank(GROSS_INCOME));
            return;
        }

        if (!NumberUtils.isParsable(gross)) {
            builder.append(mustBeAnumber(GROSS_INCOME));
            return;
        }

        if (isLessThan0(gross)) {
            builder.append(cannotBeLessThan0(GROSS_INCOME));
        }
    }


    public void validateNetIncome(String net) {

        net = StringUtils.trim(net);

        if (StringUtils.isBlank(net)) {
            builder.append(mustNotBeBlank(NET_INCOME));
            return;
        }

        if (!NumberUtils.isParsable(net)) {
            builder.append(mustBeAnumber(NET_INCOME));
            return;
        }


        if (isLessThan0(net)) {
            builder.append(cannotBeLessThan0(NET_INCOME));
        }
    }

    public void validatePay(String pay) {

        pay = StringUtils.trim(pay);
        if (StringUtils.isBlank(pay)) {
            builder.append(mustNotBeBlank(PAY));
            return;
        }

        if (!NumberUtils.isParsable(pay)) {
            builder.append(mustBeAnumber(PAY));
            return;
        }

        if (isLessThan0(pay)) {
            builder.append(cannotBeLessThan0(PAY));
        }
    }


    public void validateIncomeTax(String incomeTax) {
        incomeTax = StringUtils.trim(incomeTax);
        if (StringUtils.isBlank(incomeTax)) {
            builder.append(mustNotBeBlank(INCOME_TAX));
            return;
        }

        if (!NumberUtils.isParsable(incomeTax)) {
            builder.append(mustBeAnumber(INCOME_TAX));
            return;
        }

        if (isLessThan0(incomeTax)) {
            builder.append(cannotBeLessThan0(INCOME_TAX));
        }
    }


}
