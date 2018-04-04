package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

/**
 * Verifies {@link DuplicateDetailIdValidator}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 04/01/18
 *
 * @author Pavel Liakh
 */
public class DuplicateDetailIdValidatorTest {

    private IUsageService usageService;
    private DuplicateDetailIdValidator duplicateDetailIdValidator;

    @Before
    public void setUp() {
        usageService = createMock(IUsageService.class);
        duplicateDetailIdValidator = new DuplicateDetailIdValidator(usageService);
    }

    @Test
    public void testIsNotValid() {
        Usage usage = new Usage();
        usage.setDetailId(1L);
        expect(usageService.isDetailIdExists(usage.getDetailId(), Optional.empty())).andReturn(true).once();
        replay(usageService);
        assertFalse(duplicateDetailIdValidator.isValid(usage));
        verify(usageService);
    }

    @Test
    public void testIsValid() {
        Usage usage = new Usage();
        usage.setDetailId(1L);
        expect(usageService.isDetailIdExists(usage.getDetailId(), Optional.empty())).andReturn(false).once();
        replay(usageService);
        assertTrue(duplicateDetailIdValidator.isValid(usage));
        verify(usageService);
    }

    @Test
    public void testGetErrorMessage() {
        assertEquals("Detail ID: Detail with such ID already exists", duplicateDetailIdValidator.getErrorMessage());
    }
}
