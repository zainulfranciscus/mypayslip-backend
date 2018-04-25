package org.accounting.controller.validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;

@Component
public class EmployeeRequestValidator extends RequestValidator{


    public void validatePayDate(String year, String month, String day) {

        if(!NumberUtils.isParsable(year) || !NumberUtils.isParsable(month) || !NumberUtils.isParsable(day)){
            builder.append(errorMessageForInvalidDates(year,month,day));
            return;
        }

        try {
            LocalDate.of(parseInt(year), parseInt(month), parseInt(day));
        }catch(DateTimeException ex){
            builder.append(errorMessageForInvalidDates(year,month,day));
            return;
        }

    }


    public String errorMessageForInvalidDates(String year, String month, String date) {
        return format(IS_NOT_VALID_DATE,year,month,date);
    }
}
