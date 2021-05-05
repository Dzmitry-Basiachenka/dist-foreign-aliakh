package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link OriginalDetailIdValidator}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/03/21
 *
 * @author Uladzislau Shalamitski
 */
public class OriginalDetailIdValidatorTest {

    private static final String ORIGINAL_DETAIL_ID = "32b247dc-999a-48eb-b575-59e97114c553";

    private IUdmUsageService udmUsageService;
    private OriginalDetailIdValidator validator;

    @Before
    public void setUp() {
        udmUsageService = createMock(IUdmUsageService.class);
        validator = new OriginalDetailIdValidator(udmUsageService);
    }

    @Test
    public void testIsValidOriginalDetailIdExists() {
        expect(udmUsageService.isOriginalDetailIdExist(ORIGINAL_DETAIL_ID)).andReturn(true).once();
        replay(udmUsageService);
        assertFalse(validator.isValid(buildUdmUsage()));
        verify(udmUsageService);
    }

    @Test
    public void testIsValidOriginalDetailIdDoesNotExist() {
        expect(udmUsageService.isOriginalDetailIdExist(ORIGINAL_DETAIL_ID)).andReturn(false).once();
        replay(udmUsageService);
        assertTrue(validator.isValid(buildUdmUsage()));
        verify(udmUsageService);
    }

    @Test
    public void testGetErrorMessage() {
        assertEquals("Original Detail ID is already present in the system", validator.getErrorMessage());
    }

    private UdmUsage buildUdmUsage() {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setOriginalDetailId(ORIGINAL_DETAIL_ID);
        return udmUsage;
    }
}
