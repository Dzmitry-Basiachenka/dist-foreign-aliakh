package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.WithdrawnFundPool;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IWithdrawnFundPoolRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link WithdrawnFundPoolService}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/28/2019
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class WithdrawnFundPoolServiceTest {

    private static final String FUND_UID = RupPersistUtils.generateUuid();
    private static final String BATCH_UID = RupPersistUtils.generateUuid();
    private static final String FUND_POOL_NAME = "FAS Q3 2019";
    private static final String USER_NAME = "User Name";

    private WithdrawnFundPoolService withdrawnFundPoolService;
    private IUsageService usageService;
    private IWithdrawnFundPoolRepository withdrawnFundPoolRepository;
    private IUsageRepository usageRepository;

    @Before
    public void setUp() {
        withdrawnFundPoolService = new WithdrawnFundPoolService();
        withdrawnFundPoolRepository = createMock(IWithdrawnFundPoolRepository.class);
        usageRepository = createMock(IUsageRepository.class);
        usageService = createMock(IUsageService.class);
        Whitebox.setInternalState(withdrawnFundPoolService, usageService);
        Whitebox.setInternalState(withdrawnFundPoolService, withdrawnFundPoolRepository);
        Whitebox.setInternalState(withdrawnFundPoolService, usageRepository);
    }

    @Test
    public void testCreate() {
        mockStatic(RupContextUtils.class);
        WithdrawnFundPool fund = buildWithdrawnFundPool();
        List<String> batchIds = Collections.singletonList(RupPersistUtils.generateUuid());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        withdrawnFundPoolRepository.insert(fund);
        expectLastCall().once();
        usageRepository.addWithdrawnUsagesToFundPool(fund.getId(), batchIds, USER_NAME);
        expectLastCall().once();
        replay(RupContextUtils.class, withdrawnFundPoolRepository, usageRepository);
        withdrawnFundPoolService.create(fund, batchIds);
        verify(RupContextUtils.class, withdrawnFundPoolRepository, usageRepository);
    }

    @Test
    public void testFindAll() {
        List<WithdrawnFundPool> funds = Collections.singletonList(buildWithdrawnFundPool());
        expect(withdrawnFundPoolRepository.findAll()).andReturn(funds).once();
        replay(withdrawnFundPoolRepository);
        assertEquals(funds, withdrawnFundPoolService.getAdditionalFunds());
        verify(withdrawnFundPoolRepository);
    }

    @Test
    public void testDeleteAdditionalFund() {
        usageService.deleteFromAdditionalFund(FUND_UID);
        expectLastCall().once();
        expect(withdrawnFundPoolRepository.delete(FUND_UID)).andReturn(2).once();
        replay(withdrawnFundPoolRepository, usageService);
        withdrawnFundPoolService.deleteAdditionalFund(buildWithdrawnFundPool());
        verify(withdrawnFundPoolRepository, usageService);
    }

    @Test
    public void testGetAdditionalFundNamesByUsageBatchId() {
        List<String> names = Arrays.asList("Test 1", "Test 2");
        expect(withdrawnFundPoolRepository.findNamesByUsageBatchId(BATCH_UID)).andReturn(names).once();
        replay(withdrawnFundPoolRepository);
        assertEquals(names, withdrawnFundPoolService.getAdditionalFundNamesByUsageBatchId(BATCH_UID));
        verify(withdrawnFundPoolRepository);
    }

    @Test
    public void testFundPoolNameExists() {
        expect(withdrawnFundPoolRepository.findCountByName(FUND_POOL_NAME)).andReturn(1).once();
        replay(withdrawnFundPoolRepository);
        assertTrue(withdrawnFundPoolService.fundPoolNameExists(FUND_POOL_NAME));
        verify(withdrawnFundPoolRepository);
    }

    private WithdrawnFundPool buildWithdrawnFundPool() {
        WithdrawnFundPool withdrawnFundPool = new WithdrawnFundPool();
        withdrawnFundPool.setId(FUND_UID);
        withdrawnFundPool.setName("Additional Fund");
        return withdrawnFundPool;
    }
}
