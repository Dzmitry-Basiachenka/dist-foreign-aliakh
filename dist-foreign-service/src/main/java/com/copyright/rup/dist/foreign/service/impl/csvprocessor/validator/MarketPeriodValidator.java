package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import com.copyright.rup.dist.foreign.domain.Usage;

/**
 * The validator to check that market period to greater than market period from.
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
        if (null == usage || null == usage.getMarketPeriodTo() || null == usage.getMarketPeriodFrom()) {
            return true;
        }
        return usage.getMarketPeriodFrom() <= usage.getMarketPeriodTo();
    }

    @Override
    public String getErrorMessage() {
        return "Market Period To: Field value should not be less than Market Period From";
    }
}
