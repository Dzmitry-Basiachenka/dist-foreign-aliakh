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
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.repository.api.IFundPoolRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String FUND_POOL_NAME = "FAS Q3 2019";
    private static final String USER_NAME = "User Name";

    private FundPoolService fundPoolService;
    private IUsageService usageService;
    private INtsUsageService ntsUsageService;
    private IFundPoolRepository fundPoolRepository;

    @Before
    public void setUp() {
        fundPoolService = new FundPoolService();
        fundPoolRepository = createMock(IFundPoolRepository.class);
        usageService = createMock(IUsageService.class);
        ntsUsageService = createMock(INtsUsageService.class);
        Whitebox.setInternalState(fundPoolService, usageService);
        Whitebox.setInternalState(fundPoolService, ntsUsageService);
        Whitebox.setInternalState(fundPoolService, fundPoolRepository);
    }

    @Test
    public void testCreate() {
        mockStatic(RupContextUtils.class);
        FundPool fund = buildPreServiceFeeFund();
        Set<String> batchIds = Collections.singleton(RupPersistUtils.generateUuid());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        fundPoolRepository.insert(fund);
        expectLastCall().once();
        usageService.addWithdrawnUsagesToPreServiceFeeFund(fund.getId(), batchIds, USER_NAME);
        expectLastCall().once();
        replay(RupContextUtils.class, fundPoolRepository, usageService);
        fundPoolService.create(fund, batchIds);
        verify(RupContextUtils.class, fundPoolRepository, usageService);
    }

    @Test
    public void testGetPreServiceFeeFunds() {
        List<FundPool> funds = Collections.singletonList(buildPreServiceFeeFund());
        expect(fundPoolRepository.findByProductFamily("NTS")).andReturn(funds).once();
        replay(fundPoolRepository);
        assertEquals(funds, fundPoolService.getPreServiceFeeFunds("NTS"));
        verify(fundPoolRepository);
    }

    @Test
    public void testGetPreServiceFeeFundsNotAttachedToScenario() {
        List<FundPool> funds = Collections.singletonList(buildPreServiceFeeFund());
        expect(fundPoolRepository.findNotAttachedToScenario()).andReturn(funds).once();
        replay(fundPoolRepository);
        assertEquals(funds, fundPoolService.getPreServiceFeeFundsNotAttachedToScenario());
        verify(fundPoolRepository);
    }

    @Test
    public void testDeleteAdditionalFund() {
        ntsUsageService.deleteFromPreServiceFeeFund(FUND_UID);
        expectLastCall().once();
        expect(fundPoolRepository.delete(FUND_UID)).andReturn(2).once();
        replay(fundPoolRepository, ntsUsageService);
        fundPoolService.deletePreServiceFeeFund(buildPreServiceFeeFund());
        verify(fundPoolRepository, ntsUsageService);
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
    public void testFundPoolExists() {
        expect(fundPoolRepository.fundPoolExists(FAS_PRODUCT_FAMILY, FUND_POOL_NAME)).andReturn(true).once();
        replay(fundPoolRepository);
        assertTrue(fundPoolService.fundPoolExists(FAS_PRODUCT_FAMILY, FUND_POOL_NAME));
        verify(fundPoolRepository);
    }

    private FundPool buildPreServiceFeeFund() {
        FundPool preServiceFeeFund = new FundPool();
        preServiceFeeFund.setId(FUND_UID);
        preServiceFeeFund.setName("Additional Fund");
        return preServiceFeeFund;
    }
}
