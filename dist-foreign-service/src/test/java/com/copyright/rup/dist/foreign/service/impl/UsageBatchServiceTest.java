package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UsageBatchService}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/03/2017
 *
 * @author Mikalai Bezmen
 * @author Aliaksandr Radkevich
 */
public class UsageBatchServiceTest {

    private static final Integer FISCAL_YEAR = 2017;
    private static final String BATCH_NAME = "JAACC_11Dec16";
    private static final String USER_NAME = "User Name";
    private static final Long RRO_ACCOUNT_NUMBER = 123456789L;
    private static final String RRO_NAME = "RRO Name";

    private IUsageBatchRepository usageBatchRepository;
    private IUsageService usageService;
    private IRightsholderService rightsholderService;
    private UsageBatchService usageBatchService;

    @Before
    public void setUp() {
        usageBatchRepository = createMock(IUsageBatchRepository.class);
        usageService = createMock(IUsageService.class);
        rightsholderService = createMock(IRightsholderService.class);
        usageBatchService = new UsageBatchService();
        Whitebox.setInternalState(usageBatchService, "usageBatchRepository", usageBatchRepository);
        Whitebox.setInternalState(usageBatchService, "usageService", usageService);
        Whitebox.setInternalState(usageBatchService, "rightsholderService", rightsholderService);
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

    @Test
    public void insertUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        Rightsholder rro = buildRro();
        usageBatch.setRro(rro);
        List<Usage> usages = Lists.newArrayList(new Usage(), new Usage());
        usageBatchRepository.insert(usageBatch);
        expectLastCall().once();
        rightsholderService.updateRightsholder(rro);
        expectLastCall().once();
        expect(usageService.insertUsages(usageBatch, usages, USER_NAME)).andReturn(2).once();
        replay(usageBatchRepository, usageService);
        assertEquals(2, usageBatchService.insertUsageBatch(usageBatch, usages, USER_NAME));
        verify(usageBatchRepository, usageService);
    }

    @Test
    public void testDeleteUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(RupPersistUtils.generateUuid());
        usageService.deleteUsageBatchDetails(usageBatch);
        expectLastCall().once();
        usageBatchRepository.deleteUsageBatch(usageBatch.getId());
        expectLastCall().once();
        replay(usageService, usageBatchRepository);
        usageBatchService.deleteUsageBatch(usageBatch, USER_NAME);
        verify(usageService, usageBatchRepository);
    }

    private Rightsholder buildRro() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(RRO_ACCOUNT_NUMBER);
        rightsholder.setName(RRO_NAME);
        return rightsholder;
    }
}
