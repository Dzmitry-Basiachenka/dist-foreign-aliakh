package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.SalUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;

import org.junit.Before;
import org.junit.Test;

/**
 * Validates {@link UsageDataWorkPortionIdValidator}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 9/22/20
 *
 * @author Stanislau Rudak
 */
public class UsageDataWorkPortionIdValidatorTest {

    private static final String WORK_PORTION_ID = "1101001IB2361";
    private static final String BATCH_ID = "5a323c09-e5d5-4d15-9ca7-fc3c4b0ef992";

    private UsageDataWorkPortionIdValidator validator;
    private ISalUsageService salUsageService;

    @Before
    public void setUp() {
        salUsageService = createMock(ISalUsageService.class);
        validator = new UsageDataWorkPortionIdValidator(salUsageService, BATCH_ID);
    }

    @Test
    public void testIsValidPasses() {
        expect(salUsageService.workPortionIdExists(WORK_PORTION_ID, BATCH_ID)).andReturn(true).once();
        replay(salUsageService);
        assertTrue(validator.isValid(buildUsage()));
        verify(salUsageService);
    }

    @Test
    public void testIsValidFails() {
        expect(salUsageService.workPortionIdExists(WORK_PORTION_ID, BATCH_ID)).andReturn(false).once();
        replay(salUsageService);
        assertFalse(validator.isValid(buildUsage()));
        verify(salUsageService);
    }

    @Test
    public void testGetErrorMessage() {
        assertEquals("Work Portion Id is not present in the Item Bank", validator.getErrorMessage());
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setSalUsage(new SalUsage());
        usage.getSalUsage().setReportedWorkPortionId(WORK_PORTION_ID);
        return usage;
    }
}
