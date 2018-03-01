package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Integration test to verify data is updated correctly while getting works and determining NTS usages.
 * {@link com.copyright.rup.dist.foreign.service.impl.mock.PiIntegrationServiceMock} is used to avoid calls to real
 * environment.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 2/27/18
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=works-matching-data-init.groovy"})
public class WorksMatchingIntegrationTest {

    private static final String NTS_BY_STANDARD_NUMBER_MESSAGE =
        "Detail was made eligible for NTS because sum of gross amounts, grouped by standard number, is less than $100";
    private static final String NTS_BY_WORK_TITLE_MESSAGE =
        "Detail was made eligible for NTS because sum of gross amounts, grouped by work title, is less than $100";
    private static final String NTS_BY_SELF_MESSAGE =
        "Detail was made eligible for NTS because gross amount is less than $100";

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageAuditRepository auditRepository;

    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String FAS_PRODUCT_FAMILY = "FAS";

    @Test
    public void testExecuteInternal() throws JobExecutionException {
        usageService.findWorksAndUpdateStatuses();
        verifyUsage(5487125469L, 123059057L, UsageStatusEnum.WORK_FOUND, FAS_PRODUCT_FAMILY,
            "Wr Wrk Inst 123059057 was found by standard number 978-0-271-01750-1");
        verifyUsage(5236985478L, 123059057L, UsageStatusEnum.WORK_FOUND, FAS_PRODUCT_FAMILY,
            "Wr Wrk Inst 123059057 was found by title \"Forbidden rites\"");
        verifyUsage(5544213254L, 123059057L, UsageStatusEnum.WORK_FOUND, FAS_PRODUCT_FAMILY,
            "Wr Wrk Inst 123059057 was found by standard number 978-0-271-01750-1");
        verifyUsage(5896325874L, null, UsageStatusEnum.NEW, FAS_PRODUCT_FAMILY);
        verifyUsage(5425874236L, null, UsageStatusEnum.NEW, FAS_PRODUCT_FAMILY);
        verifyUsage(2254475587L, null, UsageStatusEnum.NEW, FAS_PRODUCT_FAMILY);
        verifyUsage(3652124587L, null, UsageStatusEnum.NEW, FAS_PRODUCT_FAMILY);
        verifyUsage(3654214587L, null, UsageStatusEnum.NEW, FAS_PRODUCT_FAMILY);
        verifyUsage(2301002001L, null, UsageStatusEnum.NEW, FAS_PRODUCT_FAMILY);
        verifyUsage(5420136521L, null, UsageStatusEnum.NEW, FAS_PRODUCT_FAMILY);
        verifyUsage(5487414477L, null, UsageStatusEnum.NEW, FAS_PRODUCT_FAMILY);
        verifyUsage(3200110141L, null, UsageStatusEnum.NEW, FAS_PRODUCT_FAMILY);
        verifyUsage(2154874521L, null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY, NTS_BY_STANDARD_NUMBER_MESSAGE);
        verifyUsage(1254874596L, null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY, NTS_BY_STANDARD_NUMBER_MESSAGE);
        verifyUsage(4125487962L, null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY, NTS_BY_STANDARD_NUMBER_MESSAGE);
        verifyUsage(2155544477L, null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY, NTS_BY_WORK_TITLE_MESSAGE);
        verifyUsage(6235212401L, null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY, NTS_BY_WORK_TITLE_MESSAGE);
        verifyUsage(4512458741L, null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY, NTS_BY_WORK_TITLE_MESSAGE);
        verifyUsage(2365985441L, null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY, NTS_BY_WORK_TITLE_MESSAGE);
        verifyUsage(8745236587L, null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY, NTS_BY_SELF_MESSAGE);
        verifyUsage(7859654123L, null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY, NTS_BY_SELF_MESSAGE);
        verifyUsage(2011442101L, null, UsageStatusEnum.ELIGIBLE, NTS_PRODUCT_FAMILY, NTS_BY_WORK_TITLE_MESSAGE);
    }

    private void verifyUsage(Long detailId, Long wrWrkInst, UsageStatusEnum status, String productFamily,
                             String... auditMessages) {
        Usage usage = usageRepository.findByDetailId(detailId);
        assertEquals(status, usage.getStatus());
        assertEquals(productFamily, usage.getProductFamily());
        assertEquals(wrWrkInst, usage.getWrWrkInst());
        List<UsageAuditItem> auditItems = auditRepository.findByUsageId(usage.getId());
        assertEquals(auditMessages.length, auditItems.size());
        assertArrayEquals(auditMessages, auditItems.stream().map(UsageAuditItem::getActionReason).toArray());
    }
}
