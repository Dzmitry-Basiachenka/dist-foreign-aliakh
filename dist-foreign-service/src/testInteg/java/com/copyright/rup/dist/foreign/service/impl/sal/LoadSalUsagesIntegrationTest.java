package com.copyright.rup.dist.foreign.service.impl.sal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageBatch.SalFields;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;
import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.SalItemBankCsvProcessor;

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Verifies functionality for loading SAL usages.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/04/2020
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestData(fileName = "empty-change-set-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class LoadSalUsagesIntegrationTest {

    private static final String UPLOADED_REASON = "Uploaded in 'SAL test batch' Batch";
    private static final String SAL_PRODUCT_FAMILY = "SAL";
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2019, 6, 30);

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private ISalUsageService salUsageService;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    private Map<String, String> commentToUsageIdMap;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    public void testLoadUsages() throws Exception {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/sal/rms_grants_122769471_request.json",
            "rights/sal/rms_grants_122769471_response.json");
        testHelper.expectGetRmsRights("rights/sal/rms_grants_request_2.json", "rights/sal/rms_grants_response_2.json");
        testHelper.expectPrmCall("prm/rightsholder_1000000322_response.json", 1000000322L);
        loadUsageBatch();
        assertUsageBatch();
        assertUsages();
        assertAudit();
    }

    private void loadUsageBatch() throws IOException {
        UsageBatch batch = buildUsageBatch();
        SalItemBankCsvProcessor csvProcessor = csvProcessorFactory.getSalItemBankCsvProcessor();
        ProcessingResult<Usage> result =
            csvProcessor.process(testHelper.getCsvOutputStream("usage/sal/sal_item_bank_usages_for_upload.csv"));
        assertTrue(result.isSuccessful());
        List<Usage> usages = result.get();
        List<String> insertedUsageIds = usageBatchService.insertSalBatch(batch, usages);
        List<Usage> actualUsages = salUsageService.getUsagesByIds(insertedUsageIds);
        commentToUsageIdMap = actualUsages.stream()
            .collect(Collectors.toMap(Usage::getComment, Usage::getId));
        List<String> orderedIds = actualUsages.stream()
            .sorted(Comparator.comparing(Usage::getComment))
            .map(Usage::getId)
            .collect(Collectors.toList());
        salUsageService.sendForMatching(orderedIds, batch.getName());
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch batch = new UsageBatch();
        batch.setName("SAL test batch");
        batch.setProductFamily(SAL_PRODUCT_FAMILY);
        batch.setPaymentDate(PAYMENT_DATE);
        SalFields salFields = new SalFields();
        salFields.setLicenseeAccountNumber(4444L);
        salFields.setLicenseeName("Truman State University");
        batch.setSalFields(salFields);
        return batch;
    }

    private void assertUsageBatch() {
        UsageBatch batch = usageBatchService.getUsageBatches("SAL")
            .stream()
            .filter(b ->"SAL test batch".equals(b.getName()))
            .findFirst()
            .orElse(null);
        assertNotNull(batch);
        assertEquals(4, batch.getInitialUsagesCount());
    }

    private void assertUsages() throws IOException {
        Map<String, UsageDto> actualUsageCommentsToUsages =
            getActualUsages().stream().collect(Collectors.toMap(UsageDto::getComment, usageDto -> usageDto));
        List<UsageDto> expectedUsages =
            testHelper.loadExpectedUsageDtos("usage/sal/sal_expected_item_bank_usages_for_upload.json");
        assertEquals(expectedUsages.size(), actualUsageCommentsToUsages.size());
        expectedUsages.forEach(expectedUsage -> {
            testHelper.assertUsageDto(expectedUsage, actualUsageCommentsToUsages.get(expectedUsage.getComment()));
            testHelper.assertSalUsage(expectedUsage.getSalUsage(),
                actualUsageCommentsToUsages.get(expectedUsage.getComment()).getSalUsage());
        });
    }

    private void assertAudit() {
        testHelper.assertAudit(commentToUsageIdMap.get("usage1"), buildUsageAuditItems(ImmutableMap.of(
            UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible",
            UsageActionTypeEnum.RH_FOUND, "Rightsholder account 1000000322 was found in RMS",
            UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 122769471 was found in PI",
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        testHelper.assertAudit(commentToUsageIdMap.get("usage2"), buildUsageAuditItems(ImmutableMap.of(
            UsageActionTypeEnum.WORK_NOT_FOUND, "Wr Wrk Inst 963852741 was not found in PI",
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        testHelper.assertAudit(commentToUsageIdMap.get("usage3"), buildUsageAuditItems(ImmutableMap.of(
            UsageActionTypeEnum.WORK_NOT_GRANTED, "Right for 243618757 is denied for rightsholder account 1000000322",
            UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 243618757 was found in PI",
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        testHelper.assertAudit(commentToUsageIdMap.get("usage4"), buildUsageAuditItems(ImmutableMap.of(
            UsageActionTypeEnum.RH_NOT_FOUND, "Rightsholder account for 140160102 was not found in RMS",
            UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 140160102 was found in PI",
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
    }

    private List<UsageAuditItem> buildUsageAuditItems(Map<UsageActionTypeEnum, String> map) {
        List<UsageAuditItem> usageAuditItems = new ArrayList<>();
        map.forEach((actionTypeEnum, detail) -> {
            UsageAuditItem usageAuditItem = new UsageAuditItem();
            usageAuditItem.setActionType(actionTypeEnum);
            usageAuditItem.setActionReason(detail);
            usageAuditItems.add(usageAuditItem);
        });
        return usageAuditItems;
    }

    private List<UsageDto> getActualUsages() {
        UsageFilter filter = new UsageFilter();
        filter.setProductFamily(SAL_PRODUCT_FAMILY);
        filter.setSalDetailType(SalDetailTypeEnum.IB);
        return salUsageService.getUsageDtos(filter, null, null);
    }
}
