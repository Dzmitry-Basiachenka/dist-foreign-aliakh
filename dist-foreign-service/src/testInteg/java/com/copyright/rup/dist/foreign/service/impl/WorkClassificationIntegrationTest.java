package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.WorkClassification;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.api.nts.IWorkClassificationService;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Verifies applying works classification to NTS Fund Pools.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/18/2019
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
//TODO: split test data into separate files for each test method
@TestData(fileName = "work-classification-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
@Transactional
public class WorkClassificationIntegrationTest {

    private static final Set<String> BATCHES_IDS = Collections.singleton("e17ebc80-e74e-436d-ba6e-acf3d355b7ff");
    private static final Long WR_WRK_INST_1 = 180382914L;
    private static final Long WR_WRK_INST_2 = 243904752L;
    private static final String STM = "STM";
    private static final String NON_STM = "NON-STM";
    private static final String BELLETRISTIC = "BELLETRISTIC";

    @Autowired
    private IWorkClassificationService workClassificationService;
    @Autowired
    private IFasUsageService usageService;

    @Test
    public void testGetWorkClassificationThreshold() {
        assertEquals(10000, workClassificationService.getWorkClassificationThreshold());
    }

    @Test
    public void testApplyStmClassification() {
        assertAndGetUsages(1, UsageStatusEnum.ELIGIBLE);
        assertAndGetUsages(1, UsageStatusEnum.UNCLASSIFIED);
        workClassificationService.insertOrUpdateClassifications(
            Sets.newHashSet(buildClassification(WR_WRK_INST_1), buildClassification(WR_WRK_INST_2)), STM);
        List<WorkClassification> workClassifications =
            workClassificationService.getClassifications(Sets.newHashSet(BATCHES_IDS), null, null, null);
        assertEquals(2, workClassifications.size());
        assertAndGetUsages(2, UsageStatusEnum.ELIGIBLE);
        workClassifications.forEach(workClassification -> {
            assertEquals(STM, workClassification.getClassification());
            assertEquals("SYSTEM", workClassification.getUpdateUser());
            assertEquals(getConvertedDate(new Date()), getConvertedDate(workClassification.getUpdateDate()));
        });
    }

    @Test
    public void testApplyNonStmClassification() {
        assertAndGetUsages(1, UsageStatusEnum.ELIGIBLE);
        assertAndGetUsages(1, UsageStatusEnum.UNCLASSIFIED);
        workClassificationService.insertOrUpdateClassifications(
            Sets.newHashSet(buildClassification(WR_WRK_INST_1), buildClassification(WR_WRK_INST_2)), NON_STM);
        List<WorkClassification> workClassifications =
            workClassificationService.getClassifications(Sets.newHashSet(BATCHES_IDS), null, null, null);
        assertEquals(2, workClassifications.size());
        assertAndGetUsages(2, UsageStatusEnum.ELIGIBLE);
        assertEquals("SYSTEM", workClassifications.get(0).getUpdateUser());
        workClassifications.forEach(
            workClassification -> {
                assertEquals(NON_STM, workClassification.getClassification());
                assertEquals("SYSTEM", workClassification.getUpdateUser());
                assertEquals(getConvertedDate(new Date()), getConvertedDate(workClassification.getUpdateDate()));
            });
    }

    @Test
    public void testApplyBelletristicClassification() {
        assertAndGetUsages(1, UsageStatusEnum.ELIGIBLE);
        assertAndGetUsages(1, UsageStatusEnum.UNCLASSIFIED);
        workClassificationService.insertOrUpdateClassifications(
            Sets.newHashSet(buildClassification(WR_WRK_INST_1), buildClassification(WR_WRK_INST_2)), BELLETRISTIC);
        assertAndGetUsages(0, UsageStatusEnum.ELIGIBLE);
        assertAndGetUsages(0, UsageStatusEnum.UNCLASSIFIED);
        assertEquals(BELLETRISTIC, workClassificationService.getClassification(WR_WRK_INST_1));
        assertEquals(BELLETRISTIC, workClassificationService.getClassification(WR_WRK_INST_2));
    }

    @Test
    public void testRemoveClassification() {
        assertAndGetUsages(1, UsageStatusEnum.ELIGIBLE);
        assertAndGetUsages(1, UsageStatusEnum.UNCLASSIFIED);
        workClassificationService.deleteClassifications(
            Sets.newHashSet(buildClassification(WR_WRK_INST_1), buildClassification(WR_WRK_INST_2)));
        assertAndGetUsages(1, UsageStatusEnum.ELIGIBLE);
        assertAndGetUsages(1, UsageStatusEnum.UNCLASSIFIED);
        assertNull(workClassificationService.getClassification(WR_WRK_INST_1));
        assertNull(workClassificationService.getClassification(WR_WRK_INST_2));
    }

    private void assertAndGetUsages(int count, UsageStatusEnum status) {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(BATCHES_IDS);
        filter.setUsageStatus(status);
        List<UsageDto> usages = usageService.getUsageDtos(filter, null, null);
        assertEquals(count, usages.size());
    }

    private WorkClassification buildClassification(Long wrWrkInst) {
        WorkClassification classification = new WorkClassification();
        classification.setWrWrkInst(wrWrkInst);
        return classification;
    }

    private String getConvertedDate(Date date) {
        return DateFormatUtils.format(date, RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    }
}
