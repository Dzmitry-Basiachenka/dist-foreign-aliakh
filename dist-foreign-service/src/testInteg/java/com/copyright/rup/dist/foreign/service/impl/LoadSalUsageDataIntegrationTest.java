package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.SalUsageDataCsvProcessor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Verifies functionality for loading SAL usage data.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/25/2020
 *
 * @author Stanislau Rudak
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestData(fileName = "load-sal-usage-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class LoadSalUsageDataIntegrationTest {

    private static final String BATCH_ID = "a04ad469-03ad-40dc-abaa-5770386c9367";
    private static final String UPLOADED_REASON = "Uploaded in 'SAL usage batch' Batch";
    private static final String SAL_PRODUCT_FAMILY = "SAL";

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private ISalUsageService salUsageService;
    @Autowired
    private ServiceTestHelper testHelper;

    private Map<String, String> commentToUsageIdMap;

    @Test
    public void testLoadUsageData() throws Exception {
        loadUsageData();
        assertUsageBatch();
        assertUsages();
        assertAudit();
    }

    private void loadUsageData() throws IOException {
        UsageBatch batch = usageBatchService.getUsageBatchById(BATCH_ID);
        SalUsageDataCsvProcessor csvProcessor = csvProcessorFactory.getSalUsageDataCsvProcessor(BATCH_ID);
        ProcessingResult<Usage> result =
            csvProcessor.process(testHelper.getCsvOutputStream("usage/sal/sal_usage_data_for_upload.csv"));
        assertTrue(result.isSuccessful());
        List<Usage> usages = result.get();
        usageBatchService.addUsageDataToSalBatch(batch, usages);
        commentToUsageIdMap = usages.stream()
            .collect(Collectors.toMap(Usage::getComment, Usage::getId));
    }

    private void assertUsageBatch() {
        UsageBatch batch = usageBatchService.getUsageBatchById("a04ad469-03ad-40dc-abaa-5770386c9367");
        assertNotNull(batch);
        assertEquals(5, batch.getInitialUsagesCount());
    }

    private void assertUsages() throws IOException {
        Map<String, UsageDto> actualUsageCommentsToUsages =
            getActualUsages().stream().collect(Collectors.toMap(UsageDto::getComment, usageDto -> usageDto));
        List<UsageDto> expectedUsages =
            testHelper.loadExpectedUsageDtos("usage/sal/sal_expected_usage_data_for_upload.json");
        assertEquals(expectedUsages.size(), actualUsageCommentsToUsages.size());
        expectedUsages.forEach(expectedUsage -> {
            testHelper.assertUsageDto(expectedUsage, actualUsageCommentsToUsages.get(expectedUsage.getComment()));
            testHelper.assertSalUsage(expectedUsage.getSalUsage(),
                actualUsageCommentsToUsages.get(expectedUsage.getComment()).getSalUsage());
        });
    }

    private void assertAudit() {
        List<UsageAuditItem> expectedAudit =
            Collections.singletonList(buildUsageAuditItem(UsageActionTypeEnum.LOADED, UPLOADED_REASON));
        testHelper.assertAudit(commentToUsageIdMap.get("SAL usage data comment 1"), expectedAudit);
        testHelper.assertAudit(commentToUsageIdMap.get("SAL usage data comment 2"), expectedAudit);
        testHelper.assertAudit(commentToUsageIdMap.get("SAL usage data comment 3"), expectedAudit);
    }

    private UsageAuditItem buildUsageAuditItem(UsageActionTypeEnum type, String reason) {
        UsageAuditItem usageAuditItem = new UsageAuditItem();
        usageAuditItem.setActionType(type);
        usageAuditItem.setActionReason(reason);
        return usageAuditItem;
    }

    private List<UsageDto> getActualUsages() {
        UsageFilter filter = new UsageFilter();
        filter.setProductFamily(SAL_PRODUCT_FAMILY);
        filter.setSalDetailType(SalDetailTypeEnum.UD);
        return salUsageService.getUsageDtos(filter, null, null);
    }
}
