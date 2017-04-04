package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.foreign.domain.Usage;

/**
 * The validator to check that 'Market Period To' greater than or equal to 'Market Period From'.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
public class MarketPeriodValidator implements IValidator<Usage> {

    @Override
    public boolean isValid(Usage usage) {
        checkNotNull(usage);
        return usage.getMarketPeriodFrom() <= usage.getMarketPeriodTo();
    }

    @Override
    public String getErrorMessage() {
        return "Market Period To: Field value should not be less than Market Period From";
    }
}
