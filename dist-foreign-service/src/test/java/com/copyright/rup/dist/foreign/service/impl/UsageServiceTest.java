package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UsageService}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/03/17
 *
 * @author Aliaksei Pchelnikau
 * @author Mikalai Bezmen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class, UsageService.class})
public class UsageServiceTest {

    private static final String USAGE_ID_1 = "Usage id 1";
    private static final String USAGE_ID_2 = "Usage id 2";
    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();
    private static final String USER_NAME = "User name";
    private IUsageRepository usageRepository;
    private IUsageService usageService;

    @Before
    public void setUp() {
        usageRepository = createMock(IUsageRepository.class);
        usageService = new UsageService();
        Whitebox.setInternalState(usageService, "usageRepository", usageRepository);
    }

    @Test
    public void testGetUsagesCount() {
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2017);
        expect(usageRepository.getUsagesCount(filter)).andReturn(1).once();
        replay(usageRepository);
        usageService.getUsagesCount(filter);
        verify(usageRepository);
    }

    @Test
    public void testGetUsageCountsEmptyFilter() {
        assertEquals(0, usageService.getUsagesCount(new UsageFilter()));
    }

    @Test
    public void testGetUsages() {
        List<UsageDto> usagesWithBatch = Lists.newArrayList(new UsageDto());
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("detailId", Sort.Direction.ASC);
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2017);
        expect(usageRepository.findByFilter(filter, pageable, sort)).andReturn(usagesWithBatch).once();
        replay(usageRepository);
        List<UsageDto> result = usageService.getUsages(filter, pageable, sort);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(usageRepository);
    }

    @Test
    public void testGetUsagesEmptyFilter() {
        List<UsageDto> result = usageService.getUsages(new UsageFilter(), null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testWriteUsageCsvReport() {
        PipedOutputStream outputStream = new PipedOutputStream();
        UsageFilter usageFilter = new UsageFilter();
        usageRepository.writeUsagesCsvReport(usageFilter, outputStream);
        expectLastCall().once();
        replay(usageRepository);
        usageService.writeUsageCsvReport(usageFilter, outputStream);
        verify(usageRepository);
    }

    @Test
    public void testInsertUsages() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setGrossAmount(new BigDecimal("12.00"));
        Usage usage1 = new Usage();
        usage1.setReportedValue(BigDecimal.TEN);
        Usage usage2 = new Usage();
        usage2.setReportedValue(BigDecimal.ONE);
        List<Usage> usages = Lists.newArrayList(usage1, usage2);
        usageRepository.insertUsage(usages.get(0));
        expectLastCall().once();
        usageRepository.insertUsage(usages.get(1));
        expectLastCall().once();
        replay(usageRepository);
        assertEquals(2, usageService.insertUsages(usageBatch, usages));
        assertEquals(new BigDecimal("10.9090909090"), usage1.getGrossAmount());
        assertEquals(new BigDecimal("1.0909090909"), usage2.getGrossAmount());
        verify(usageRepository);
    }

    @Test
    public void testDeleteUsageBatchDetails() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(RupPersistUtils.generateUuid());
        usageRepository.deleteUsageBatchDetails(usageBatch.getId());
        expectLastCall().once();
        replay(usageRepository);
        usageService.deleteUsageBatchDetails(usageBatch);
        verify(usageRepository);
    }

    @Test
    public void testGetUsagesWithAmounts() {
        UsageFilter usageFilter = new UsageFilter();
        expect(usageRepository.findUsagesWithAmounts(usageFilter)).andReturn(Collections.emptyList()).once();
        replay(usageRepository);
        assertTrue(CollectionUtils.isEmpty(usageService.getUsagesWithAmounts(usageFilter)));
        verify(usageRepository);
    }

    @Test
    public void testAddUsagesToScenario() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setCreateUser(USER_NAME);
        List<String> usagesIds = Lists.newArrayList(USAGE_ID_1, USAGE_ID_2);
        usageRepository.addUsagesToScenario(usagesIds, SCENARIO_ID, USER_NAME);
        expectLastCall().once();
        replay(usageRepository);
        usageService.addUsagesToScenario(usagesIds, scenario);
        verify(usageRepository);
    }

    @Test
    public void testDeleteUsagesFromScenario() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        usageRepository.deleteUsagesFromScenario(SCENARIO_ID, USER_NAME);
        expectLastCall().once();
        replay(usageRepository, RupContextUtils.class);
        usageService.deleteUsagesFromScenario(SCENARIO_ID);
        verify(usageRepository, RupContextUtils.class);
    }
}
