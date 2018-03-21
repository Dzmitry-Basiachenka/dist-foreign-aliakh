package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.foreign.domain.Usage;

import java.util.Objects;


/**
 * The validator to check that Wr Wrk Inst field value cannot be null if RH Acct Number field value is presented.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/24/18
 *
 * @author Uladzislau Shalamitski
 */
public class RightsholderWrWrkInstValidator implements IValidator<Usage> {

    @Override
    public boolean isValid(Usage usage) {
        Objects.requireNonNull(usage);
        return !(Objects.nonNull(usage.getRightsholder().getAccountNumber()) && Objects.isNull(usage.getWrWrkInst()));
    }

    @Override
    public String getErrorMessage() {
        return "Wr Wrk Inst: Field value cannot be null or empty if RH Account # field value is presented";
    }
}
