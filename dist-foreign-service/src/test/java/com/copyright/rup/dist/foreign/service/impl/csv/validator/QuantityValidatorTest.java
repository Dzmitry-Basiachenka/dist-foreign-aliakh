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

    private final Integer quantity;
    private final String reportedTitle;
    private final boolean expectedResult;

    /**
     * Constructor.
     *
     * @param quantity       quantity
     * @param reportedTitle  reported title
     * @param expectedResult expected result
     */
    public QuantityValidatorTest(Integer quantity, String reportedTitle, boolean expectedResult) {
        this.quantity = quantity;
        this.reportedTitle = reportedTitle;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {0, "NONE", true},
            {0, "None", true},
            {0, "none", true},
            {0, "Brain surgery", false},
            {0, "", false},
            {0, null, false},
            {1, "NONE", false},
            {1, "None", false},
            {1, "none", false},
            {1, "Brain surgery", true},
            {1, "", true},
            {1, null, true},
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

    private UdmUsage buildUdmUsage(Integer quantityToSet, String reportedTitleToSet) {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setQuantity(quantityToSet);
        udmUsage.setReportedTitle(reportedTitleToSet);
        return udmUsage;
    }
}
