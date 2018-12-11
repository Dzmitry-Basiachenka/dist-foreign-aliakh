package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;

import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Verifies correctness of inserting NTS batch.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/05/2018
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=insert-nts-batch-data-init.groovy"})
@Transactional
public class InsertNtsBatchIntegrationTest {

    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageBatchRepository usageBatchRepository;
    @Autowired
    private IUsageAuditService usageAuditService;

    @Test
    public void testInsertNtsBatch() {
        UsageBatch usageBatch = buildNtsBatch();
        usageBatchService.insertNtsBatch(usageBatch);
        assertNotNull(usageBatchRepository.findAll()
            .stream()
            .filter(batch -> Objects.equals("Test NTS batch", batch.getName()))
            .findFirst());
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton(usageBatch.getId()));
        List<UsageDto> usages =
            usageRepository.findByFilter(usageFilter, null, new Sort("market", Sort.Direction.ASC));
        assertEquals(3, usages.size());
        verifyUsage(usages.get(0), 2011, 2013, "Bus,Edu");
        verifyUsage(usages.get(1), 2013, 2017, "Sch");
        verifyUsage(usages.get(2), 2000, 2011, "Univ,Bus");
    }

    private void verifyUsage(UsageDto usage, Integer periodFrom, Integer periodTo, String market) {
        assertNull(usage.getRhAccountNumber());
        assertNull(usage.getArticle());
        assertNull(usage.getPublisher());
        assertNull(usage.getPublicationDate());
        assertNull(usage.getNumberOfCopies());
        assertNotNull(usage.getWrWrkInst());
        assertNotNull(usage.getWorkTitle());
        assertNotNull(usage.getSystemTitle());
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals("NTS", usage.getProductFamily());
        assertEquals(5431L, usage.getRroAccountNumber(), 0);
        assertEquals(new BigDecimal("0.00"), usage.getReportedValue());
        assertEquals(new BigDecimal("50.0000000000"), usage.getGrossAmount());
        assertEquals(new BigDecimal("0.00"), usage.getBatchGrossAmount());
        assertEquals(market, usage.getMarket());
        assertEquals(periodFrom, usage.getMarketPeriodFrom());
        assertEquals(periodTo, usage.getMarketPeriodTo());
        verifyAudit(usage.getId());
    }

    private void verifyAudit(String usageId) {
        List<UsageAuditItem> auditItems = usageAuditService.getUsageAudit(usageId);
        assertEquals(1, auditItems.size());
        assertEquals(UsageActionTypeEnum.CREATED, auditItems.get(0).getActionType());
        String actionReason = auditItems.get(0).getActionReason();
        assertTrue(actionReason.matches(
            "Usage was created based on Market\\(s\\): '\\w+', '\\w+', '\\w+', Fund Pool Period: 2011-2013"));
        assertTrue(actionReason.contains("Univ"));
        assertTrue(actionReason.contains("Edu"));
        assertTrue(actionReason.contains("Sch"));
    }

    private UsageBatch buildNtsBatch() {
        FundPool fundPool = new FundPool();
        fundPool.setFundPoolPeriodFrom(2011);
        fundPool.setFundPoolPeriodTo(2013);
        fundPool.setMarkets(Sets.newHashSet("Univ", "Edu", "Sch"));
        fundPool.setStmAmount(new BigDecimal("200000.00"));
        fundPool.setNonStmAmount(new BigDecimal("50000.00"));
        fundPool.setStmMinimumAmount(new BigDecimal("50.00"));
        fundPool.setNonStmMinimumAmount(new BigDecimal("7.00"));
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setFiscalYear(2014);
        usageBatch.setId(RupPersistUtils.generateUuid());
        usageBatch.setName("Test NTS batch");
        usageBatch.setPaymentDate(LocalDate.now());
        Rightsholder rro = new Rightsholder();
        rro.setAccountNumber(5431L);
        usageBatch.setRro(rro);
        usageBatch.setFundPool(fundPool);
        return usageBatch;
    }
}
