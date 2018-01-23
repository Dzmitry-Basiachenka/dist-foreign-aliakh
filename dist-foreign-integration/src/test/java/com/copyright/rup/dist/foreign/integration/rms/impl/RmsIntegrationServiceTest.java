package com.copyright.rup.dist.foreign.integration.rms.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.integration.rest.rms.IRmsService;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.time.DateUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Verifies {@link RmsIntegrationService}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 01/16/2018
 *
 * @author Aliaksandr Liakh
 */
public class RmsIntegrationServiceTest {

    private static final Long WR_WRK_INST_1 = 154L;
    private static final Long WR_WRK_INST_2 = 258L;
    private static final Date CURRENT_DATE = new Date();
    private RmsIntegrationService rmsIntegrationService;
    private IRmsService rmsService;

    @Before
    public void setUp() {
        rmsService = createMock(IRmsService.class);
        rmsIntegrationService = new RmsIntegrationService();
        rmsIntegrationService.setRmsService(rmsService);
    }

    @Test
    public void testGetAllRmsGrants() {
        List<Long> wrWrkInsts = Lists.newArrayList(WR_WRK_INST_1, WR_WRK_INST_2);
        Capture<Date> dateCapture = new Capture<>();
        expect(rmsService.getAllRmsGrants(eq(wrWrkInsts), capture(dateCapture)))
            .andReturn(Collections.emptySet()).once();
        replay(rmsService);
        assertTrue(rmsIntegrationService.getAllRmsGrants(wrWrkInsts).isEmpty());
        assertTrue(DateUtils.isSameDay(CURRENT_DATE, dateCapture.getValue()));
        verify(rmsService);
    }
}
