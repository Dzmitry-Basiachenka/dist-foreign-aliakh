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
 * Verifies {@link UsageDataGradeValidator}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 9/23/20
 *
 * @author Stanislau Rudak
 */
public class UsageDataGradeValidatorTest {

    private static final String WORK_PORTION_ID = "1101001IB2361";
    private static final String GRADE_K = "K";

    private UsageDataGradeValidator validator;
    private ISalUsageService salUsageService;

    @Before
    public void setUp() {
        salUsageService = createMock(ISalUsageService.class);
        validator = new UsageDataGradeValidator(salUsageService);
    }

    @Test
    public void testIsValidWithGradeInUsageData() {
        replay(salUsageService);
        assertTrue(validator.isValid(buildUsage(GRADE_K)));
        verify(salUsageService);
    }

    @Test
    public void testIsValidWithGradeInItemBank() {
        expect(salUsageService.getItemBankDetailGradeByWorkPortionId(WORK_PORTION_ID)).andReturn(GRADE_K).once();
        replay(salUsageService);
        assertTrue(validator.isValid(buildUsage(null)));
        verify(salUsageService);
    }

    @Test
    public void testIsValidWithGradeInNeither() {
        expect(salUsageService.getItemBankDetailGradeByWorkPortionId(WORK_PORTION_ID)).andReturn(null).once();
        replay(salUsageService);
        assertFalse(validator.isValid(buildUsage(null)));
        verify(salUsageService);
    }

    @Test
    public void testGetErrorMessage() {
        assertEquals("Grade should be specified in either Item Bank or Usage Data", validator.getErrorMessage());
    }

    private Usage buildUsage(String grade) {
        Usage usage = new Usage();
        usage.setSalUsage(new SalUsage());
        usage.getSalUsage().setReportedWorkPortionId(WORK_PORTION_ID);
        usage.getSalUsage().setGrade(grade);
        return usage;
    }
}
