package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import com.google.common.collect.Lists;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
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
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
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
        expect(usageBatchRepository.findAll()).andReturn(usageBatches).once();
        replay(usageBatchRepository);
        assertEquals(usageBatches, usageBatchService.getUsageBatches());
        verify(usageBatchRepository);
    }

    @Test
    public void testUsageBatchExists() {
        expect(usageBatchRepository.getCountByName(BATCH_NAME)).andReturn(1).once();
        replay(usageBatchRepository);
        assertTrue(usageBatchService.usageBatchExists(BATCH_NAME));
        verify(usageBatchRepository);
    }

    @Test
    public void testUsageBatchDoesNotExist() {
        expect(usageBatchRepository.getCountByName(BATCH_NAME)).andReturn(0).once();
        replay(usageBatchRepository);
        assertFalse(usageBatchService.usageBatchExists(BATCH_NAME));
        verify(usageBatchRepository);
    }

    @Test
    public void insertUsageBatch() {
        mockStatic(RupContextUtils.class);
        Capture<UsageBatch> captureUsageBatch = new Capture<>();
        UsageBatch usageBatch = new UsageBatch();
        Rightsholder rro = buildRro();
        usageBatch.setRro(rro);
        List<Usage> usages = Lists.newArrayList(new Usage(), new Usage());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        usageBatchRepository.insert(capture(captureUsageBatch));
        expectLastCall().once();
        rightsholderService.updateRightsholder(rro);
        expectLastCall().once();
        expect(usageService.insertUsages(usageBatch, usages)).andReturn(2).once();
        replay(usageBatchRepository, usageService, RupContextUtils.class);
        assertEquals(2, usageBatchService.insertUsageBatch(usageBatch, usages));
        UsageBatch insertedUsageBatch = captureUsageBatch.getValue();
        assertNotNull(insertedUsageBatch);
        assertEquals(USER_NAME, insertedUsageBatch.getUpdateUser());
        assertEquals(USER_NAME, insertedUsageBatch.getCreateUser());
        verify(usageBatchRepository, usageService, RupContextUtils.class);
    }

    @Test
    public void testDeleteUsageBatch() {
        mockStatic(RupContextUtils.class);
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(RupPersistUtils.generateUuid());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        usageService.deleteUsageBatchDetails(usageBatch);
        expectLastCall().once();
        usageBatchRepository.deleteUsageBatch(usageBatch.getId());
        expectLastCall().once();
        replay(usageService, usageBatchRepository, RupContextUtils.class);
        usageBatchService.deleteUsageBatch(usageBatch);
        verify(usageService, usageBatchRepository, RupContextUtils.class);
    }

    private Rightsholder buildRro() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(RRO_ACCOUNT_NUMBER);
        rightsholder.setName(RRO_NAME);
        return rightsholder;
    }
}
