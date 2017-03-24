package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link DetailIdValidator}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/20/17
 *
 * @author Aliaksei Pchelnikau
 */
public class DetailIdValidatorTest {

    private static final long DETAIL_ID = 1L;
    private DetailIdValidator validator;
    private IUsageService usageService;

    @Before
    public void setUp() {
        usageService = createMock(IUsageService.class);
        validator = new DetailIdValidator();
        Whitebox.setInternalState(validator, "usageService", usageService);
    }

    @Test
    public void testIsValid() {
        expect(usageService.detailIdExists(DETAIL_ID)).andReturn(false).once();
        replay(usageService);
        Usage usage = new Usage();
        usage.setDetailId(DETAIL_ID);
        assertTrue(validator.isValid(usage));
        verify(usageService);
    }

    @Test
    public void testIsNotValid() {
        expect(usageService.detailIdExists(DETAIL_ID)).andReturn(true).once();
        replay(usageService);
        Usage usage = new Usage();
        usage.setDetailId(DETAIL_ID);
        assertFalse(validator.isValid(usage));
        verify(usageService);
    }

    @Test
    public void getErrorMessage() {
        assertEquals("Detail ID: Detail with such ID already exists", validator.getErrorMessage());
    }
}
