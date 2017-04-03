package com.copyright.rup.dist.foreign.ui.component.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator.ReportedValueValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link GrossAmountValidator}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/24/17
 *
 * @author Darya Baraukova
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({GrossAmountValidator.class})
public class GrossAmountValidatorTest {

    @Test
    public void testIsValidValue() throws Exception {
        GrossAmountValidator grossAmountValidator = new GrossAmountValidator();
        ReportedValueValidator reportedValueValidator = createMock(ReportedValueValidator.class);
        expectNew(ReportedValueValidator.class).andReturn(reportedValueValidator).once();
        expect(reportedValueValidator.isValid("1.00")).andReturn(true).once();
        replay(reportedValueValidator, ReportedValueValidator.class);
        assertTrue(grossAmountValidator.isValid("1.00"));
        verify(reportedValueValidator, ReportedValueValidator.class);
    }
}
