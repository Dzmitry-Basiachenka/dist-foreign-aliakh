package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
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
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class UsageBatchRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "usage-batch-repository-integration-test/";
    private static final String USAGE_BATCH_NAME_1 = "Test batch";
    private static final String USAGE_BATCH_NAME_2 = "Test batch with fund pool";
    private static final Integer FISCAL_YEAR_2017 = 2017;
    private static final Long RRO_ACCOUNT_NUMBER = 123456789L;
    private static final BigDecimal GROSS_AMOUNT = new BigDecimal("23.53");
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2017, 2, 23);
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String NTS_BATCH_NAME = "NTS Batch without STM usages";
    private static final String NTS_USAGE_BATCH_ID_1 = "e92f9fcd-9eec-4170-960c-f9f8d6cabd2d";
    private static final String NTS_USAGE_BATCH_ID_2 = "99725b90-c4da-414d-9159-f1c8c83c5e19";
    private static final String NTS_USAGE_BATCH_ID_3 = "3da5944b-dab1-47a4-a29d-2dbecc6737e0";

    @Autowired
    private UsageBatchRepository usageBatchRepository;
    @Autowired
    private IUsageRepository usageRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "find-fiscal-years-by-product-family.groovy")
    public void testFindFiscalYearsByProductFamily() {
        List<Integer> fiscalYears = usageBatchRepository.findFiscalYearsByProductFamily(FAS_PRODUCT_FAMILY);
        assertNotNull(fiscalYears);
        assertEquals(4, fiscalYears.size());
        assertEquals(2016, fiscalYears.get(0), 0);
        assertEquals(FISCAL_YEAR_2017, fiscalYears.get(1));
        assertEquals(2018, fiscalYears.get(2), 0);
        assertEquals(2019, fiscalYears.get(3), 0);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-count-by-name.groovy")
    public void testFindCountByName() {
        assertEquals(1, usageBatchRepository.findCountByName("JAACC_11Dec16"));
        assertEquals(1, usageBatchRepository.findCountByName("JaAcC_11dec16"));
        assertEquals(0, usageBatchRepository.findCountByName(USAGE_BATCH_NAME_1));
    }

    @Test
    @TestData(fileName = "rollback-only.groovy")
    public void testInsertUsageBatch() {
        usageBatchRepository.insert(buildUsageBatch());
        UsageBatch usageBatch = usageBatchRepository.findByName(USAGE_BATCH_NAME_1);
        assertNotNull(usageBatch);
        assertEquals(FAS_PRODUCT_FAMILY, usageBatch.getProductFamily());
        assertEquals(RRO_ACCOUNT_NUMBER, usageBatch.getRro().getAccountNumber());
        assertEquals(PAYMENT_DATE, usageBatch.getPaymentDate());
        assertEquals(FISCAL_YEAR_2017, usageBatch.getFiscalYear());
        assertEquals(GROSS_AMOUNT, usageBatch.getGrossAmount());
        assertEquals(1000, usageBatch.getInitialUsagesCount());
        assertNull(usageBatch.getNtsFields());
    }

    @Test
    @TestData(fileName = "rollback-only.groovy")
    public void testInsertUsageBatchWithFundPool() {
        usageBatchRepository.insert(buildUsageBatchWithFundPool());
        UsageBatch usageBatch = usageBatchRepository.findByName(USAGE_BATCH_NAME_2);
        assertNotNull(usageBatch);
        assertEquals(NTS_PRODUCT_FAMILY, usageBatch.getProductFamily());
        assertEquals(RRO_ACCOUNT_NUMBER, usageBatch.getRro().getAccountNumber());
        assertEquals(PAYMENT_DATE, usageBatch.getPaymentDate());
        assertEquals(FISCAL_YEAR_2017, usageBatch.getFiscalYear());
        assertEquals(1000, usageBatch.getInitialUsagesCount());
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
    @TestData(fileName = "rollback-only.groovy")
    public void testUpdateInitialUsagesCount() {
        UsageBatch batch = buildUsageBatchWithFundPool();
        batch.setInitialUsagesCount(0);
        usageBatchRepository.insert(batch);
        UsageBatch usageBatch = usageBatchRepository.findByName(USAGE_BATCH_NAME_2);
        assertNotNull(usageBatch);
        assertEquals(0, usageBatch.getInitialUsagesCount());
        usageBatchRepository.updateInitialUsagesCount(1000, usageBatch.getId(), "user@copyright.com");
        usageBatch = usageBatchRepository.findByName(USAGE_BATCH_NAME_2);
        assertNotNull(usageBatch);
        assertEquals(1000, usageBatch.getInitialUsagesCount());
        assertEquals("user@copyright.com", usageBatch.getUpdateUser());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-usage-batch.groovy")
    public void testDeleteUsageBatch() {
        String batchId = "10f425fa-8c2b-4a2f-9527-61533b79de39";
        assertEquals(1, usageBatchRepository.findAll().size());
        usageRepository.deleteByBatchId(batchId);
        usageBatchRepository.deleteUsageBatch(batchId);
        assertEquals(0, usageBatchRepository.findAll().size());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-all.groovy")
    public void testFindAll() {
        List<UsageBatch> usageBatches = usageBatchRepository.findAll();
        assertEquals(4, usageBatches.size());
        assertEquals("2cd32412-dd17-4ab0-9b1b-296d48ad7db7", usageBatches.get(0).getId());
        assertEquals("b74ec8a7-8c0d-437f-aa1f-77a32931e7c7", usageBatches.get(1).getId());
        assertEquals("949a3b84-b559-459a-9de9-315447c6d1f7", usageBatches.get(2).getId());
        assertEquals("e6210618-a5af-4557-81e5-5754e3aa2fba", usageBatches.get(3).getId());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-id.groovy")
    public void testFindById() {
        UsageBatch usageBatch = usageBatchRepository.findById("6b27ad49-ec1c-4c63-9ff7-c79317087180");
        assertNotNull(usageBatch);
        assertEquals(NTS_PRODUCT_FAMILY, usageBatch.getProductFamily());
        assertEquals(RRO_ACCOUNT_NUMBER, usageBatch.getRro().getAccountNumber());
        assertEquals(LocalDate.of(2019, 1, 11), usageBatch.getPaymentDate());
        assertEquals(2020, usageBatch.getFiscalYear().intValue());
        assertEquals(2, usageBatch.getInitialUsagesCount());
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
    @TestData(fileName = FOLDER_NAME + "find-by-product-family.groovy")
    public void testFindByProductFamily() {
        List<UsageBatch> usageBatches = usageBatchRepository.findByProductFamily(FAS_PRODUCT_FAMILY);
        assertEquals(4, usageBatches.size());
        assertEquals("59209cce-6334-4056-ad37-7ec6fe4f001a", usageBatches.get(0).getId());
        assertEquals("14d39224-ec9e-4d89-b3ed-82a3ddf01d46", usageBatches.get(1).getId());
        assertEquals("9f7342a7-014a-42a9-b02f-3a31fbb71088", usageBatches.get(2).getId());
        assertEquals("fe8103c0-91a2-4502-b33c-dbdc93f99dcf", usageBatches.get(3).getId());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-usage-batches-for-nts-fund-pool.groovy")
    public void testFindUsageBatchesForNtsFundPool() {
        List<UsageBatch> usageBatches = usageBatchRepository.findUsageBatchesForNtsFundPool();
        assertEquals(1, usageBatches.size());
        assertEquals("e0bfbab2-6627-419f-98af-a08e648c571f", usageBatches.get(0).getId());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-batch-names-without-usages-for-classification.groovy")
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
    @TestData(fileName = FOLDER_NAME + "find-batch-names-without-usages-for-classification.groovy")
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
    @TestData(fileName = FOLDER_NAME + "find-batch-names-without-usages-for-classification.groovy")
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
    @TestData(fileName = FOLDER_NAME + "find-ineligible-for-scenario-batch-names.groovy")
    public void testFindIneligibleForScenarioBatchNames() {
        ImmutableSet<String> batchIds =
            ImmutableSet.of("272ebe5c-8c77-442f-83cd-c4075bda38c2", "c8fb56a0-2ea9-4dd4-a731-e92a3587e7ee",
                "32409673-9d9a-4f4f-92c7-676307d39518", "b691c756-d027-49da-a1f4-b0ddfabaa4fd",
                "321129ec-d280-4349-8ed5-fdbfe28da51f");
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
    @TestData(fileName = FOLDER_NAME + "find-batches-names-to-scenarios-names.groovy")
    public void testFindBatchesNamesToScenariosNames() {
        Map<String, String> batchesNamesToScenariosNames = usageBatchRepository.findBatchesNamesToScenariosNames(
            ImmutableSet.of("8d6ac3b1-8704-43a8-acec-d86ac263996f", "e40065de-4854-457b-86a1-59b8b3d33b9a"));
        assertNotNull(batchesNamesToScenariosNames);
        assertEquals(1, batchesNamesToScenariosNames.size());
        assertEquals("Scenario name 4", batchesNamesToScenariosNames.get("NEW_26_OCT_2017"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-sal-not-attached-to-scenario.groovy")
    public void testFindSalNotAttachedToScenario() {
        List<String> actualBatchIds = usageBatchRepository.findByProductFamily("SAL").stream()
            .map(UsageBatch::getId)
            .sorted()
            .collect(Collectors.toList());
        assertEquals(Arrays.asList("4f0a6d1a-4df5-4d29-a1b4-9fc32f031ba5", "930caf5f-839f-4926-bebe-bde38d90b0e1"),
            actualBatchIds);
        List<UsageBatch> notAttachedBatches = usageBatchRepository.findSalNotAttachedToScenario();
        assertEquals(1, notAttachedBatches.size());
        assertEquals(2, notAttachedBatches.get(0).getInitialUsagesCount());
        List<String> actualNotAttachedBatchIds = notAttachedBatches.stream()
            .map(UsageBatch::getId)
            .sorted()
            .collect(Collectors.toList());
        assertEquals(Collections.singletonList("930caf5f-839f-4926-bebe-bde38d90b0e1"), actualNotAttachedBatchIds);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-batch-names-available-for-rights-assignment.groovy")
    public void testFindBatchNamesAvailableForRightsAssignment() {
        List<String> expectedBatches =
            Arrays.asList("FAS2 Batch With Eligible and RH Not Found usages", "FAS2 Batch With RH Not Found usages");
        assertEquals(expectedBatches,
            usageBatchRepository.findBatchNamesForRightsAssignment().stream().sorted().collect(Collectors.toList()));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-sal-licensees.groovy")
    public void testFindSalLicensees() {
        List<SalLicensee> licensees = usageBatchRepository.findSalLicensees();
        assertEquals(2, licensees.size());
        SalLicensee licensee = licensees.get(0);
        assertEquals(5588L, licensee.getAccountNumber().longValue());
        assertEquals("RGS Energy Group", licensee.getName());
        licensee = licensees.get(1);
        assertEquals(7001293454L, licensee.getAccountNumber().longValue());
        assertEquals("Synergy Publishers", licensee.getName());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-sal-usage-periods.groovy")
    public void testFindSalUsagePeriods() {
        assertEquals(Collections.singletonList(2015), usageBatchRepository.findSalUsagePeriods());
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
        usageBatch.setInitialUsagesCount(1000);
        return usageBatch;
    }

    private UsageBatch buildUsageBatchWithFundPool() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(RupPersistUtils.generateUuid());
        usageBatch.setProductFamily(NTS_PRODUCT_FAMILY);
        usageBatch.setName(USAGE_BATCH_NAME_2);
        usageBatch.setFiscalYear(FISCAL_YEAR_2017);
        usageBatch.setInitialUsagesCount(1000);
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
