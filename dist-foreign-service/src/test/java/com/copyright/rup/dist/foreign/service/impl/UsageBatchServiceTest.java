package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UsageBatchService}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/03/2017
 *
 * @author Mikalai Bezmen
 */
public class UsageBatchServiceTest {

    private static final Integer FISCAL_YEAR = 2017;
    private static final String BATCH_NAME = "JAACC_11Dec16";

    private IUsageBatchRepository usageBatchRepository;
    private UsageBatchService usageBatchService;

    @Before
    public void setUp() {
        usageBatchRepository = createMock(IUsageBatchRepository.class);
        usageBatchService = new UsageBatchService();
        Whitebox.setInternalState(usageBatchService, "usageBatchRepository", usageBatchRepository);
    }

    @Test
    public void testGetFiscalYears() {
        expect(usageBatchRepository.findFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        replay(usageBatchRepository);
        List<Integer> result = usageBatchService.getFiscalYears();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(FISCAL_YEAR));
        verify(usageBatchRepository);
    }

    @Test
    public void testGetUsageBatches() {
        List<UsageBatch> usageBatches = Collections.singletonList(new UsageBatch());
        expect(usageBatchRepository.findUsageBatches()).andReturn(usageBatches).once();
        replay(usageBatchRepository);
        assertEquals(usageBatches, usageBatchService.getUsageBatches());
        verify(usageBatchRepository);
    }

    @Test
    public void testUsageBatchExists() {
        expect(usageBatchRepository.getUsageBatchesCountByName(BATCH_NAME)).andReturn(1).once();
        replay(usageBatchRepository);
        assertTrue(usageBatchService.usageBatchExists(BATCH_NAME));
        verify(usageBatchRepository);
    }

    @Test
    public void testUsageBatchDoesNotExist() {
        expect(usageBatchRepository.getUsageBatchesCountByName(BATCH_NAME)).andReturn(0).once();
        replay(usageBatchRepository);
        assertFalse(usageBatchService.usageBatchExists(BATCH_NAME));
        verify(usageBatchRepository);
    }
}
