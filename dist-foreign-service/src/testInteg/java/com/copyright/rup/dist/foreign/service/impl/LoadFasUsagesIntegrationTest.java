package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;

import com.google.common.collect.ImmutableMap;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Verifies functionality for loading researched usages.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/32/18
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class LoadFasUsagesIntegrationTest {

    private static final String USAGE_ID_1 = "0f263081-1a5d-4c76-a1ff-d9a6e1e0b694";
    private static final String USAGE_ID_2 = "5ae4880e-0955-4518-8681-2aeeda667474";
    private static final String USAGE_ID_3 = "afef95d3-d525-49ec-91fe-79fdced6830f";
    private static final String USAGE_ID_4 = "ddc1672e-ef63-4965-a6bc-1d299272c953";
    private static final List<String> IDS = Arrays.asList(USAGE_ID_1, USAGE_ID_2, USAGE_ID_3, USAGE_ID_4);
    private static final String UPLOADED_REASON = "Uploaded in 'Test_Batch' Batch";
    private static final String FAS2_PRODUCT_FAMILY = "FAS2";

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
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/rms_grants_123059057_request.json",
            "rights/rms_grants_empty_response.json");
        testHelper.expectGetRmsRights("rights/rms_grants_100011725_request.json",
            "rights/rms_grants_100011725_response.json");
        testHelper.expectPrmCall("prm/rightsholder_1000024950_response.json", 1000024950L);
        loadUsageBatch();
        testHelper.assertUsages(Arrays.asList(
            buildUsage(USAGE_ID_1, UsageStatusEnum.NTS_WITHDRAWN, null, null, FAS2_PRODUCT_FAMILY,
                new BigDecimal("55.0000000000"), new BigDecimal("50.00")),
            buildUsage(USAGE_ID_2, UsageStatusEnum.WORK_NOT_FOUND,
                null, null, FAS2_PRODUCT_FAMILY, new BigDecimal("165.0000000000"), new BigDecimal("150.00")),
            buildUsage(USAGE_ID_3, UsageStatusEnum.RH_NOT_FOUND, 123059057L,
                null, FAS2_PRODUCT_FAMILY, new BigDecimal("220.0000000000"), new BigDecimal("200.00")),
            buildUsage(USAGE_ID_4, UsageStatusEnum.ELIGIBLE, 100011725L,
                1000024950L, FAS2_PRODUCT_FAMILY, new BigDecimal("110.0000000000"), new BigDecimal("100.00"))
        ));
        testHelper.assertAudit(USAGE_ID_1, buildUsageAuditItems(USAGE_ID_1, ImmutableMap.of(
            UsageActionTypeEnum.ELIGIBLE_FOR_NTS,
            "Detail was made eligible for NTS because sum of gross amounts, grouped by standard number, " +
                "is less than $100",
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        testHelper.assertAudit(USAGE_ID_2, buildUsageAuditItems(USAGE_ID_2, ImmutableMap.of(
            UsageActionTypeEnum.WORK_NOT_FOUND, "Wr Wrk Inst was not found by standard number 12345XX-123117",
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        testHelper.assertAudit(USAGE_ID_3, buildUsageAuditItems(USAGE_ID_3, ImmutableMap.of(
            UsageActionTypeEnum.RH_NOT_FOUND, "Rightsholder account for 123059057 was not found in RMS",
            UsageActionTypeEnum.WORK_FOUND,
            "Wr Wrk Inst 123059057 was found by title \"True directions : living your sacred instructions\"",
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        testHelper.assertAudit(USAGE_ID_4, buildUsageAuditItems(USAGE_ID_4, ImmutableMap.of(
            UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible",
            UsageActionTypeEnum.RH_FOUND, "Rightsholder account 1000024950 was found in RMS",
            UsageActionTypeEnum.WORK_FOUND, "Usage was uploaded with Wr Wrk Inst",
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        testHelper.verifyRestServer();
    }

    private void loadUsageBatch() throws IOException {
        UsageBatch batch = buildUsageBatch();
        UsageCsvProcessor csvProcessor = csvProcessorFactory.getUsageCsvProcessor("FAS2");
        ProcessingResult<Usage> result = csvProcessor.process(getCsvOutputStream());
        assertTrue(result.isSuccessful());
        List<Usage> usages = result.get();
        setPredefinedUsageIds(usages);
        int usagesInsertedCount = usageBatchService.insertFasBatch(batch, usages);
        assertEquals(4, usagesInsertedCount);
        List<UsageBatch> batches = usageBatchService.getUsageBatches(FAS2_PRODUCT_FAMILY);
        UsageBatch uploadedBatch =
            batches.stream().filter(b -> "Test_Batch".equals(b.getName())).findFirst().orElse(null);
        assertNotNull(uploadedBatch);
        assertEquals(4, uploadedBatch.getInitialUsagesCount());
        usageService.sendForMatching(usages);
        usageService.sendForGettingRights(usages, batch.getName());
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch batch = new UsageBatch();
        batch.setName("Test_Batch");
        batch.setRro(buildRro());
        batch.setProductFamily(FAS2_PRODUCT_FAMILY);
        batch.setPaymentDate(LocalDate.now());
        batch.setFiscalYear(2018);
        batch.setGrossAmount(BigDecimal.valueOf(550));
        return batch;
    }

    private Rightsholder buildRro() {
        Rightsholder rro = new Rightsholder();
        rro.setId("77b111d3-9eea-49af-b815-100b9716c1b3");
        rro.setAccountNumber(2000017000L);
        rro.setName("CLA, The Copyright Licensing Agency Ltd.");
        return rro;
    }

    // predefined usage ids are used, otherwise during every test run the usage ids will be random
    private void setPredefinedUsageIds(List<Usage> usages) {
        AtomicInteger usageId = new AtomicInteger(0);
        usages.forEach(usage -> usage.setId(IDS.get(usageId.getAndIncrement())));
    }

    private ByteArrayOutputStream getCsvOutputStream() throws IOException {
        String csvText = TestUtils.fileToString(this.getClass(), "usage/usages_for_upload.csv");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.write(csvText, out, StandardCharsets.UTF_8);
        return out;
    }

    private Usage buildUsage(String usageId, UsageStatusEnum status, Long wrWrkInst, Long rhAccounNumber,
                             String productFamily, BigDecimal grossAmount, BigDecimal reportedValue) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(rhAccounNumber);
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setStatus(status);
        usage.setWrWrkInst(wrWrkInst);
        usage.setRightsholder(rightsholder);
        usage.setProductFamily(productFamily);
        usage.setReportedValue(reportedValue);
        usage.setGrossAmount(grossAmount);
        usage.setNetAmount(new BigDecimal("0.0000000000"));
        usage.setServiceFeeAmount(new BigDecimal("0.0000000000"));
        return usage;
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
