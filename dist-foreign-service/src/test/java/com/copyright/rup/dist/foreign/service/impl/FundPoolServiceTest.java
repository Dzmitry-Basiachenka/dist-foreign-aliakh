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
import com.copyright.rup.dist.foreign.domain.PreServiceFeeFund;
import com.copyright.rup.dist.foreign.repository.api.IFundPoolRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
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
 * Verifies {@link FundPoolService}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/28/2019
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class FundPoolServiceTest {

    private static final String FUND_UID = RupPersistUtils.generateUuid();
    private static final String BATCH_UID = RupPersistUtils.generateUuid();
    private static final String FUND_POOL_NAME = "FAS Q3 2019";
    private static final String USER_NAME = "User Name";

    private FundPoolService fundPoolService;
    private IUsageService usageService;
    private IFundPoolRepository fundPoolRepository;
    private IUsageRepository usageRepository;

    @Before
    public void setUp() {
        fundPoolService = new FundPoolService();
        fundPoolRepository = createMock(IFundPoolRepository.class);
        usageRepository = createMock(IUsageRepository.class);
        usageService = createMock(IUsageService.class);
        Whitebox.setInternalState(fundPoolService, usageService);
        Whitebox.setInternalState(fundPoolService, fundPoolRepository);
        Whitebox.setInternalState(fundPoolService, usageRepository);
    }

    @Test
    public void testCreate() {
        mockStatic(RupContextUtils.class);
        PreServiceFeeFund fund = buildPreServiceFeeFund();
        List<String> batchIds = Collections.singletonList(RupPersistUtils.generateUuid());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        fundPoolRepository.insert(fund);
        expectLastCall().once();
        usageRepository.addWithdrawnUsagesToPreServiceFeeFund(fund.getId(), batchIds, USER_NAME);
        expectLastCall().once();
        replay(RupContextUtils.class, fundPoolRepository, usageRepository);
        fundPoolService.create(fund, batchIds);
        verify(RupContextUtils.class, fundPoolRepository, usageRepository);
    }

    @Test
    public void testFindAll() {
        List<PreServiceFeeFund> funds = Collections.singletonList(buildPreServiceFeeFund());
        expect(fundPoolRepository.findAll()).andReturn(funds).once();
        replay(fundPoolRepository);
        assertEquals(funds, fundPoolService.getPreServiceFeeFunds());
        verify(fundPoolRepository);
    }

    @Test
    public void testDeleteAdditionalFund() {
        usageService.deleteFromPreServiceFeeFund(FUND_UID);
        expectLastCall().once();
        expect(fundPoolRepository.delete(FUND_UID)).andReturn(2).once();
        replay(fundPoolRepository, usageService);
        fundPoolService.deletePreServiceFeeFund(buildPreServiceFeeFund());
        verify(fundPoolRepository, usageService);
    }

    @Test
    public void testGetAdditionalFundNamesByUsageBatchId() {
        List<String> names = Arrays.asList("Test 1", "Test 2");
        expect(fundPoolRepository.findNamesByUsageBatchId(BATCH_UID)).andReturn(names).once();
        replay(fundPoolRepository);
        assertEquals(names, fundPoolService.getPreServiceFeeFundNamesByUsageBatchId(BATCH_UID));
        verify(fundPoolRepository);
    }

    @Test
    public void testFundPoolNameExists() {
        expect(fundPoolRepository.findCountByName(FUND_POOL_NAME)).andReturn(1).once();
        replay(fundPoolRepository);
        assertTrue(fundPoolService.fundPoolNameExists(FUND_POOL_NAME));
        verify(fundPoolRepository);
    }

    private PreServiceFeeFund buildPreServiceFeeFund() {
        PreServiceFeeFund preServiceFeeFund = new PreServiceFeeFund();
        preServiceFeeFund.setId(FUND_UID);
        preServiceFeeFund.setName("Additional Fund");
        return preServiceFeeFund;
    }
}
