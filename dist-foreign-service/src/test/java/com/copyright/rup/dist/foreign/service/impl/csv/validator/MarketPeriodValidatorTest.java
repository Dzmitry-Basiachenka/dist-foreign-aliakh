package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.Usage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;
import java.util.List;

/**
 * Verifies {@link MarketPeriodValidator}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
@RunWith(Parameterized.class)
public class MarketPeriodValidatorTest {

    private final Integer marketPeriodFrom;
    private final Integer marketPeriodTo;
    private final boolean expectedResult;

    /**
     * Constructor.
     *
     * @param marketPeriodFrom market period from
     * @param marketPeriodTo   market period to
     * @param expectedResult   expected result
     */
    public MarketPeriodValidatorTest(Integer marketPeriodFrom, Integer marketPeriodTo, boolean expectedResult) {
        this.marketPeriodFrom = marketPeriodFrom;
        this.marketPeriodTo = marketPeriodTo;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][]{
            {2000, 2005, true},
            {2000, 2000, true},
            {2000, 1999, false}
        });
    }

    @Test
    public void testGetErrorMessage() {
        MarketPeriodValidator validator = new MarketPeriodValidator();
        assertEquals(expectedResult, validator.isValid(buildUsage(marketPeriodFrom, marketPeriodTo)));
        assertEquals("Market Period To: Field value should not be less than Market Period From",
            validator.getErrorMessage());
    }

    private Usage buildUsage(Integer maskedPeriodFrom, Integer markedPeriodTo) {
        Usage usage = new Usage();
        usage.setMarketPeriodFrom(maskedPeriodFrom);
        usage.setMarketPeriodTo(markedPeriodTo);
        return usage;
    }
}
