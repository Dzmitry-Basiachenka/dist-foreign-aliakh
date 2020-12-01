package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageBatch.NtsFields;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Verifies {@link UsageBatchRepository}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/03/2017
 *
 * @author Mikalai Bezmen
 * @author Aliaksandr Radkevich
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=usage-batch-repository-test-data-init.groovy"})
@Transactional
public class UsageBatchRepositoryIntegrationTest {

    private static final String USAGE_BATCH_NAME_1 = "Test batch";
    private static final String USAGE_BATCH_NAME_2 = "Test batch with fund pool";
    private static final Integer FISCAL_YEAR_2017 = 2017;
    private static final Long RRO_ACCOUNT_NUMBER = 123456789L;
    private static final BigDecimal GROSS_AMOUNT = new BigDecimal("23.53");
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2017, 2, 23);
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String NTS_BATCH_NAME = "NTS Batch without STM usages";
    private static final String NTS_USAGE_BATCH_ID_1 = "7c028d85-58c3-45f8-be2d-33c16b0905b0";
    private static final String NTS_USAGE_BATCH_ID_2 = "334959d7-ad39-4624-a8fa-38c3e82be6eb";
    private static final String NTS_USAGE_BATCH_ID_3 = "00aed73a-4243-440b-aa8a-445185580cb9";
    private static final String NTS_USAGE_BATCH_ID_4 = "3f46981e-e85a-4786-9b60-ab009c4358e7";

    @Autowired
    private UsageBatchRepository usageBatchRepository;
    @Autowired
    private IUsageRepository usageRepository;

    @Test
    public void testFindFiscalYears() {
        List<Integer> fiscalYears = usageBatchRepository.findFiscalYearsByProductFamily(FAS_PRODUCT_FAMILY);
        assertNotNull(fiscalYears);
        assertEquals(4, fiscalYears.size());
        assertEquals(2016, fiscalYears.get(0), 0);
        assertEquals(FISCAL_YEAR_2017, fiscalYears.get(1));
        assertEquals(2018, fiscalYears.get(2), 0);
        assertEquals(2019, fiscalYears.get(3), 0);
    }

    @Test
    public void testFindCountByName() {
        assertEquals(1, usageBatchRepository.findCountByName("JAACC_11Dec16"));
        assertEquals(1, usageBatchRepository.findCountByName("JaAcC_11dec16"));
        assertEquals(0, usageBatchRepository.findCountByName(USAGE_BATCH_NAME_1));
    }

    @Test
    public void testInsertUsageBatch() {
        usageBatchRepository.insert(buildUsageBatch());
        UsageBatch usageBatch = usageBatchRepository.findByName(USAGE_BATCH_NAME_1);
        assertNotNull(usageBatch);
        assertEquals(FAS_PRODUCT_FAMILY, usageBatch.getProductFamily());
        assertEquals(RRO_ACCOUNT_NUMBER, usageBatch.getRro().getAccountNumber());
        assertEquals(PAYMENT_DATE, usageBatch.getPaymentDate());
        assertEquals(FISCAL_YEAR_2017, usageBatch.getFiscalYear());
        assertEquals(GROSS_AMOUNT, usageBatch.getGrossAmount());
        assertNull(usageBatch.getNtsFields());
    }

    @Test
    public void testInsertUsageBatchWithFundPool() {
        usageBatchRepository.insert(buildUsageBatchWithFundPool());
        UsageBatch usageBatch = usageBatchRepository.findByName(USAGE_BATCH_NAME_2);
        assertNotNull(usageBatch);
        assertEquals(NTS_PRODUCT_FAMILY, usageBatch.getProductFamily());
        assertEquals(RRO_ACCOUNT_NUMBER, usageBatch.getRro().getAccountNumber());
        assertEquals(PAYMENT_DATE, usageBatch.getPaymentDate());
        assertEquals(FISCAL_YEAR_2017, usageBatch.getFiscalYear());
        NtsFields ntsFields = usageBatch.getNtsFields();
        assertEquals(2017, ntsFields.getFundPoolPeriodFrom().intValue());
        assertEquals(2018, ntsFields.getFundPoolPeriodTo().intValue());
        assertEquals(new BigDecimal("100"), ntsFields.getStmAmount());
        assertEquals(new BigDecimal("200."), ntsFields.getNonStmAmount());
        assertEquals(new BigDecimal("300.3"), ntsFields.getStmMinimumAmount());
        assertEquals(new BigDecimal("400.44"), ntsFields.getNonStmMinimumAmount());
        assertEquals(ImmutableSet.of("Edu", "Gov"), ntsFields.getMarkets());
        assertTrue(ntsFields.isExcludingStm());
    }

    @Test
    public void testDeleteUsageBatch() {
        String batchId = "56282dbc-2468-48d4-b926-93d3458a656a";
        assertEquals(15, usageBatchRepository.findAll().size());
        usageRepository.deleteByBatchId(batchId);
        usageBatchRepository.deleteUsageBatch(batchId);
        assertEquals(14, usageBatchRepository.findAll().size());
    }

    @Test
    public void testFindAll() {
        List<UsageBatch> usageBatches = usageBatchRepository.findAll();
        assertEquals(15, usageBatches.size());
        assertEquals("abd219c1-caae-4542-84e3-f9f4dba0d03b", usageBatches.get(0).getId());
        assertEquals("55df79f3-7e3f-4d74-9931-9aa513195816", usageBatches.get(1).getId());
        assertEquals("13027b25-2269-3bec-48ea-5126431eedb0", usageBatches.get(2).getId());
        assertEquals(NTS_USAGE_BATCH_ID_3, usageBatches.get(3).getId());
        assertEquals(NTS_USAGE_BATCH_ID_2, usageBatches.get(4).getId());
        assertEquals(NTS_USAGE_BATCH_ID_1, usageBatches.get(5).getId());
        assertEquals(NTS_USAGE_BATCH_ID_4, usageBatches.get(6).getId());
        assertEquals("56282dbc-2468-48d4-b926-94d3458a666a", usageBatches.get(7).getId());
        assertEquals("56282dbc-2468-48d4-b926-93d3458a656a", usageBatches.get(8).getId());
        assertEquals("a5b64c3a-55d2-462e-b169-362dca6a4dd6", usageBatches.get(9).getId());
        assertEquals("66282dbc-2468-48d4-b926-93d3458a656b", usageBatches.get(10).getId());
        assertEquals("071ebf56-eb38-49fc-b26f-cc210a374d3a", usageBatches.get(11).getId());
        assertEquals("033cc3dd-b121-41d5-91e6-cf4ddf71c141", usageBatches.get(12).getId());
        assertEquals("1f332dc2-f3c2-453a-aba0-9baa69b803d4", usageBatches.get(13).getId());
        assertEquals("f0de407a-a615-4171-8e98-9fc28fab5324", usageBatches.get(14).getId());
    }

    @Test
    public void testFindById() {
        UsageBatch usageBatch = usageBatchRepository.findById(NTS_USAGE_BATCH_ID_1);
        assertNotNull(usageBatch);
        assertEquals(NTS_PRODUCT_FAMILY, usageBatch.getProductFamily());
        assertEquals(RRO_ACCOUNT_NUMBER, usageBatch.getRro().getAccountNumber());
        assertEquals(LocalDate.of(2019, 1, 11), usageBatch.getPaymentDate());
        assertEquals(2020, usageBatch.getFiscalYear().intValue());
        NtsFields ntsFields = usageBatch.getNtsFields();
        assertEquals(2013, ntsFields.getFundPoolPeriodFrom().intValue());
        assertEquals(2017, ntsFields.getFundPoolPeriodTo().intValue());
        assertEquals(new BigDecimal("100"), ntsFields.getStmAmount());
        assertEquals(BigDecimal.ZERO, ntsFields.getNonStmAmount());
        assertEquals(new BigDecimal("50"), ntsFields.getStmMinimumAmount());
        assertEquals(new BigDecimal("7"), ntsFields.getNonStmMinimumAmount());
        assertEquals(Collections.singleton("Univ"), ntsFields.getMarkets());
        assertTrue(ntsFields.isExcludingStm());
    }

    @Test
    public void testFindByProductFamily() {
        List<UsageBatch> usageBatches = usageBatchRepository.findByProductFamily(FAS_PRODUCT_FAMILY);
        assertEquals(4, usageBatches.size());
        assertEquals(NTS_USAGE_BATCH_ID_4, usageBatches.get(0).getId());
        assertEquals("56282dbc-2468-48d4-b926-94d3458a666a", usageBatches.get(1).getId());
        assertEquals("56282dbc-2468-48d4-b926-93d3458a656a", usageBatches.get(2).getId());
        assertEquals("a5b64c3a-55d2-462e-b169-362dca6a4dd6", usageBatches.get(3).getId());
    }

    @Test
    public void testFindUsageBatchesForNtsFundPool() {
        List<UsageBatch> usageBatches = usageBatchRepository.findUsageBatchesForNtsFundPool();
        assertEquals(1, usageBatches.size());
        assertEquals("66282dbc-2468-48d4-b926-93d3458a656b", usageBatches.get(0).getId());
    }

    @Test
    public void testFindBatchNamesWithoutUsagesForClassificationStm() {
        List<String> batchNames = usageBatchRepository.findBatchNamesWithoutUsagesForClassification(
            Collections.singleton(NTS_USAGE_BATCH_ID_1), "STM");
        assertTrue(CollectionUtils.isNotEmpty(batchNames));
        assertEquals(1, batchNames.size());
        assertEquals(NTS_BATCH_NAME, batchNames.get(0));
        batchNames = usageBatchRepository.findBatchNamesWithoutUsagesForClassification(
            Sets.newHashSet(NTS_USAGE_BATCH_ID_1, NTS_USAGE_BATCH_ID_2, NTS_USAGE_BATCH_ID_3), "STM");
        assertTrue(CollectionUtils.isNotEmpty(batchNames));
        assertEquals(2, batchNames.size());
        assertTrue(batchNames.contains(NTS_BATCH_NAME));
        assertTrue(batchNames.contains("NTS Batch with Belletristic usages"));
    }

    @Test
    public void testFindBatchNamesWithoutUsagesForClassificationNonStm() {
        List<String> batchNames = usageBatchRepository.findBatchNamesWithoutUsagesForClassification(
            Collections.singleton(NTS_USAGE_BATCH_ID_1), "NON-STM");
        assertTrue(CollectionUtils.isEmpty(batchNames));
        batchNames = usageBatchRepository.findBatchNamesWithoutUsagesForClassification(
            Sets.newHashSet(NTS_USAGE_BATCH_ID_1, NTS_USAGE_BATCH_ID_2, NTS_USAGE_BATCH_ID_3), "NON-STM");
        assertEquals(2, batchNames.size());
        assertTrue(batchNames.contains("NTS Batch with unclassified usages"));
        assertTrue(batchNames.contains("NTS Batch with Belletristic usages"));
    }

    @Test
    public void testFindBatchNamesWithoutUsagesForClassificationUnclassified() {
        List<String> batchNames = usageBatchRepository.findBatchNamesWithoutUsagesForClassification(
            Collections.singleton(NTS_USAGE_BATCH_ID_1), null);
        assertEquals(1, batchNames.size());
        assertEquals(NTS_BATCH_NAME, batchNames.get(0));
        batchNames = usageBatchRepository.findBatchNamesWithoutUsagesForClassification(
            Sets.newHashSet(NTS_USAGE_BATCH_ID_1, NTS_USAGE_BATCH_ID_2, NTS_USAGE_BATCH_ID_3), null);
        assertEquals(2, batchNames.size());
        assertTrue(batchNames.contains("NTS Batch with unclassified usages"));
        assertTrue(batchNames.contains(NTS_BATCH_NAME));
    }

    @Test
    public void testFindIneligibleForScenarioBatchNames() {
        ImmutableSet<String> batchIds =
            ImmutableSet.of(NTS_USAGE_BATCH_ID_1, NTS_USAGE_BATCH_ID_2, NTS_USAGE_BATCH_ID_3,
                "13027b25-2269-3bec-48ea-5126431eedb0", NTS_USAGE_BATCH_ID_4);
        EnumSet<UsageStatusEnum> statuses =
            EnumSet.of(UsageStatusEnum.ELIGIBLE, UsageStatusEnum.UNCLASSIFIED, UsageStatusEnum.LOCKED,
                UsageStatusEnum.SCENARIO_EXCLUDED);
        List<String> batchesNames = usageBatchRepository.findIneligibleForScenarioBatchNames(batchIds, statuses);
        assertNotNull(batchesNames);
        assertEquals(3, batchesNames.size());
        assertEquals("NTS Batch with Belletristic usages", batchesNames.get(0));
        assertEquals("NTS Batch with unclassified usages", batchesNames.get(1));
        assertEquals("NTS Batch without STM usages", batchesNames.get(2));
    }

    @Test
    public void testFindBatchesNamesToScenariosNames() {
        Map<String, String> batchesNamesToScenariosNames = usageBatchRepository.findBatchesNamesToScenariosNames(
            ImmutableSet.of(NTS_USAGE_BATCH_ID_4, "7c028d85-58c3-45f8-be2d-33c16b0905b0"));
        assertNotNull(batchesNamesToScenariosNames);
        assertEquals(1, batchesNamesToScenariosNames.size());
        assertEquals("Scenario name 4", batchesNamesToScenariosNames.get("NEW_26_OCT_2017"));
    }

    @Test
    public void testFindSalNotAttachedToScenario() {
        List<String> actualBatchIds = usageBatchRepository.findByProductFamily("SAL").stream()
            .map(UsageBatch::getId)
            .sorted()
            .collect(Collectors.toList());
        assertEquals(Arrays.asList("1f332dc2-f3c2-453a-aba0-9baa69b803d4", "55df79f3-7e3f-4d74-9931-9aa513195816",
            "abd219c1-caae-4542-84e3-f9f4dba0d03b", "f0de407a-a615-4171-8e98-9fc28fab5324"), actualBatchIds);
        List<String> actualNotAttachedBatchIds = usageBatchRepository.findSalNotAttachedToScenario().stream()
            .map(UsageBatch::getId)
            .sorted()
            .collect(Collectors.toList());
        assertEquals(Arrays.asList("1f332dc2-f3c2-453a-aba0-9baa69b803d4",
            "55df79f3-7e3f-4d74-9931-9aa513195816", "abd219c1-caae-4542-84e3-f9f4dba0d03b"), actualNotAttachedBatchIds);
    }

    @Test
    public void testFindBatchNamesAvailableForRightsAssignment() {
        List<String> expectedBatches =
            Arrays.asList("FAS2 Batch With Eligible and RH Not Found usages", "FAS2 Batch With RH Not Found usages");
        assertEquals(expectedBatches,
            usageBatchRepository.findBatchNamesForRightsAssignment().stream().sorted().collect(Collectors.toList()));
    }

    @Test
    public void testFindSalLicensees() {
        List<SalLicensee> licensees = usageBatchRepository.findSalLicensees();
        assertEquals(2, licensees.size());
        SalLicensee licensee = licensees.get(0);
        assertEquals(1112L, licensee.getAccountNumber().longValue());
        assertEquals("Coors Brewing Company", licensee.getName());
        licensee = licensees.get(1);
        assertEquals(2000017003L, licensee.getAccountNumber().longValue());
        assertEquals("ProLitteris", licensee.getName());
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(RupPersistUtils.generateUuid());
        usageBatch.setProductFamily(FAS_PRODUCT_FAMILY);
        usageBatch.setName(USAGE_BATCH_NAME_1);
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(RRO_ACCOUNT_NUMBER);
        usageBatch.setRro(rightsholder);
        usageBatch.setPaymentDate(PAYMENT_DATE);
        usageBatch.setFiscalYear(FISCAL_YEAR_2017);
        usageBatch.setGrossAmount(GROSS_AMOUNT);
        return usageBatch;
    }

    private UsageBatch buildUsageBatchWithFundPool() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(RupPersistUtils.generateUuid());
        usageBatch.setProductFamily(NTS_PRODUCT_FAMILY);
        usageBatch.setName(USAGE_BATCH_NAME_2);
        usageBatch.setFiscalYear(FISCAL_YEAR_2017);
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(RRO_ACCOUNT_NUMBER);
        usageBatch.setRro(rightsholder);
        usageBatch.setPaymentDate(PAYMENT_DATE);
        usageBatch.setNtsFields(buildNtsFields());
        return usageBatch;
    }

    private NtsFields buildNtsFields() {
        NtsFields ntsFields = new NtsFields();
        ntsFields.setFundPoolPeriodFrom(2017);
        ntsFields.setFundPoolPeriodTo(2018);
        ntsFields.setStmAmount(new BigDecimal("100"));
        ntsFields.setNonStmAmount(new BigDecimal("200."));
        ntsFields.setStmMinimumAmount(new BigDecimal("300.3"));
        ntsFields.setNonStmMinimumAmount(new BigDecimal("400.44"));
        ntsFields.setMarkets(ImmutableSet.of("Edu", "Gov"));
        ntsFields.setExcludingStm(true);
        return ntsFields;
    }
}
