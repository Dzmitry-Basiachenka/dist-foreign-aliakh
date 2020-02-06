package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.junit.Before;
import org.junit.Test;

/**
 * Validates {@link ClassifiedUsageValidator}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 01/24/2020
 *
 * @author Anton Azarenka
 */
public class ClassifiedUsageValidatorTest {

    private static final String DETAIL_ID = "c0623017-61de-4b56-839e-d6927851da67";
    private IUsageService usageService;
    private ClassifiedUsageValidator validator;

    @Before
    public void setUp() {
        usageService = createMock(IUsageService.class);
        validator = new ClassifiedUsageValidator(usageService);
    }

    @Test
    public void testIsValid() {
        AaclClassifiedUsage usage = buildClassifiedAaclUsage(DETAIL_ID);
        expect(usageService.isUsageIdExists(DETAIL_ID, UsageStatusEnum.WORK_RESEARCH)).andReturn(true).once();
        replay(usageService);
        assertTrue(validator.isValid(usage));
        verify(usageService);
    }

    @Test
    public void testIsValidUsageIdNull() {
        AaclClassifiedUsage usage = buildClassifiedAaclUsage(null);
        expect(usageService.isUsageIdExists(null, UsageStatusEnum.WORK_RESEARCH)).andReturn(false).once();
        replay(usageService);
        assertFalse(validator.isValid(usage));
        verify(usageService);
    }

    @Test
    public void testGetErrorMessage() {
        assertEquals("Detail with such ID doesn't exist in the system", validator.getErrorMessage());
    }

    private AaclClassifiedUsage buildClassifiedAaclUsage(String detailId) {
        AaclClassifiedUsage classifiedAaclUsage = new AaclClassifiedUsage();
        classifiedAaclUsage.setDetailId(detailId);
        return classifiedAaclUsage;
    }
}
