package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.UdmUsage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

/**
 * Verifies {@link QuantityValidator}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/03/21
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(Parameterized.class)
public class QuantityValidatorTest {

    private final Long quantity;
    private final String reportedTitle;
    private final boolean expectedResult;

    /**
     * Constructor.
     *
     * @param quantity       quantity
     * @param reportedTitle  reported title
     * @param expectedResult expected result
     */
    public QuantityValidatorTest(Long quantity, String reportedTitle, boolean expectedResult) {
        this.quantity = quantity;
        this.reportedTitle = reportedTitle;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {0L, "NONE", true},
            {0L, "None", true},
            {0L, "none", true},
            {0L, "Brain surgery", false},
            {0L, "", false},
            {0L, null, false},
            {1L, "NONE", false},
            {1L, "None", false},
            {1L, "none", false},
            {1L, "Brain surgery", true},
            {1L, "", true},
            {1L, null, true},
        });
    }

    @Test
    public void testIsValid() {
        QuantityValidator validator = new QuantityValidator();
        assertEquals(expectedResult, validator.isValid(buildUdmUsage(quantity, reportedTitle)));
        assertEquals(
            "Quantity should be zero if Reported Title equals to 'None', otherwise it should be greater than zero",
            validator.getErrorMessage());
    }

    private UdmUsage buildUdmUsage(Long quantityToSet, String reportedTitleToSet) {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setQuantity(quantityToSet);
        udmUsage.setReportedTitle(reportedTitleToSet);
        return udmUsage;
    }
}
