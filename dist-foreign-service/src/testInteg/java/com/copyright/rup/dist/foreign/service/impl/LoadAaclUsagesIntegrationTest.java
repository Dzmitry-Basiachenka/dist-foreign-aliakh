package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.AaclUsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;

import com.google.common.collect.ImmutableMap;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Verifies functionality for loading AACL usages.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/27/19
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
public class LoadAaclUsagesIntegrationTest {

    private static final String USAGE_ID_1 = "3e9566e6-5cb9-4013-88ef-d4f8b66bda5d";
    private static final String USAGE_ID_2 = "599c5313-ed3b-4874-87e9-972425eb734f";
    private static final String USAGE_ID_3 = "78272304-1a43-48f7-a83b-530a90ed27e6";
    private static final String USAGE_ID_4 = "f634ec19-8ed1-4cc8-88bc-54461c62e670";
    private static final List<String> IDS = Arrays.asList(USAGE_ID_1, USAGE_ID_2, USAGE_ID_3, USAGE_ID_4);
    private static final String UPLOADED_REASON = "Uploaded in 'AACL test batch' Batch";
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2019, 6, 30);

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private ServiceTestHelper testHelper;

    @Test
    public void testLoadUsages() throws Exception {
        loadUsageBatch();
        UsageFilter filter = new UsageFilter();
        filter.setProductFamily(AACL_PRODUCT_FAMILY);
        filter.setPaymentDate(PAYMENT_DATE);
        List<UsageDto> usages = usageService.getUsageDtos(filter, null, null);
        assertUsage(usages, USAGE_ID_1, 16245866L, "CORNELL UNIVERSITY", "Feb 2019 TUR", 10, 14,
            "AACL usage Comment 1");
        assertUsage(usages, USAGE_ID_2, 16912502L, "WESTERN MICHIGAN UNIVERSITY", "Aug 2018 TUR", 30, 12,
            "AACL usage Comment 2");
        assertUsage(usages, USAGE_ID_3, 16565457L, "UNIV. OF MICHIGAN", "Aug 2019 TUR", 2, 7, "AACL usage Comment 3");
        assertUsage(usages, USAGE_ID_4, 16504064L, "University of Chicago", "Feb 2018 TUR", 3, 17,
            "AACL usage Comment 4");
        IDS.forEach(id -> testHelper.assertAudit(id,
            buildUsageAuditItems(id, ImmutableMap.of(UsageActionTypeEnum.LOADED, UPLOADED_REASON))));
    }

    private void loadUsageBatch() throws IOException {
        UsageBatch batch = buildUsageBatch();
        AaclUsageCsvProcessor csvProcessor = csvProcessorFactory.getAaclUsageCsvProcessor();
        ProcessingResult<Usage> result = csvProcessor.process(getCsvOutputStream());
        assertTrue(result.isSuccessful());
        List<Usage> usages = result.get();
        setPredefinedUsageIds(usages);
        int usagesInsertedCount = usageBatchService.insertAaclBatch(batch, usages);
        assertEquals(4, usagesInsertedCount);
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch batch = new UsageBatch();
        batch.setName("AACL test batch");
        batch.setProductFamily(AACL_PRODUCT_FAMILY);
        batch.setPaymentDate(PAYMENT_DATE);
        return batch;
    }

    // predefined usage ids are used, otherwise during every test run the usage ids will be random
    private void setPredefinedUsageIds(List<Usage> usages) {
        AtomicInteger usageId = new AtomicInteger(0);
        usages.forEach(usage -> usage.setId(IDS.get(usageId.getAndIncrement())));
    }

    private ByteArrayOutputStream getCsvOutputStream() throws IOException {
        String csvText = TestUtils.fileToString(this.getClass(), "usage/aacl/aacl_usages_for_upload.csv");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.write(csvText, out, StandardCharsets.UTF_8);
        return out;
    }

    private void assertUsage(List<UsageDto> usages, String usageId, Long wrWrkInst, String institution,
                             String usageSource, Integer numberOfCopies, Integer numberOfPages, String comment) {
        UsageDto usage = usages.stream().filter(usageDto -> usageDto.getId().equals(usageId)).findFirst().orElse(null);
        assertNotNull(usage);
        assertEquals(UsageStatusEnum.NEW, usage.getStatus());
        assertEquals(wrWrkInst, usage.getWrWrkInst());
        assertEquals(AACL_PRODUCT_FAMILY, usage.getProductFamily());
        assertEquals(numberOfCopies, usage.getNumberOfCopies());
        assertEquals(comment, usage.getComment());
        AaclUsage aaclUsage = usage.getAaclUsage();
        assertEquals(institution, aaclUsage.getInstitution());
        assertEquals(usageSource, aaclUsage.getUsageSource());
        assertEquals(numberOfPages, aaclUsage.getNumberOfPages());
        assertEquals(201906, aaclUsage.getUsagePeriod());
    }

    private List<UsageAuditItem> buildUsageAuditItems(String usageId, Map<UsageActionTypeEnum, String> map) {
        List<UsageAuditItem> usageAuditItems = new ArrayList<>();
        map.forEach((actionTypeEnum, detail) -> {
            UsageAuditItem usageAuditItem = new UsageAuditItem();
            usageAuditItem.setUsageId(usageId);
            usageAuditItem.setActionType(actionTypeEnum);
            usageAuditItem.setActionReason(detail);
            usageAuditItems.add(usageAuditItem);
        });
        return usageAuditItems;
    }
}
