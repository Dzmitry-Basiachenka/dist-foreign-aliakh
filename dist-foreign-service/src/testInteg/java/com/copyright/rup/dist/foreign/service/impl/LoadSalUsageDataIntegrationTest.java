package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.SalUsage;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=load-sal-usage-data-init.groovy"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
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
        ProcessingResult<Usage> result = csvProcessor.process(getCsvOutputStream());
        assertTrue(result.isSuccessful());
        List<Usage> usages = result.get();
        usageBatchService.addUsageDataToSalBatch(batch, usages);
        commentToUsageIdMap = usages.stream()
            .collect(Collectors.toMap(Usage::getComment, Usage::getId));
    }

    private ByteArrayOutputStream getCsvOutputStream() throws IOException {
        String csvText = TestUtils.fileToString(this.getClass(), "usage/sal/sal_usage_data_for_upload.csv");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.write(csvText, out, StandardCharsets.UTF_8);
        return out;
    }

    private void assertUsageBatch() {
        UsageBatch batch = usageBatchService.getUsageBatchById("a04ad469-03ad-40dc-abaa-5770386c9367");
        assertNotNull(batch);
        assertEquals(5, batch.getInitialUsagesCount());
    }

    private void assertUsages() throws IOException {
        Map<String, UsageDto> actualUsageCommentsToUsages =
            getActualUsages().stream().collect(Collectors.toMap(UsageDto::getComment, usageDto -> usageDto));
        List<UsageDto> expectedUsages = loadExpectedUsages("usage/sal/sal_expected_usage_data_for_upload.json");
        assertEquals(expectedUsages.size(), actualUsageCommentsToUsages.size());
        expectedUsages.forEach(
            expectedUsage -> assertUsage(expectedUsage, actualUsageCommentsToUsages.get(expectedUsage.getComment())));
    }

    private List<UsageDto> loadExpectedUsages(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<UsageDto>>() {
        });
    }

    // TODO {srudak} move to ServiceTestHelper
    private void assertUsage(UsageDto expectedUsage, UsageDto actualUsage) {
        assertNotNull(actualUsage);
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
        assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
        assertEquals(expectedUsage.getRhAccountNumber(), actualUsage.getRhAccountNumber());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        assertSalUsage(expectedUsage.getSalUsage(), actualUsage.getSalUsage());
    }

    private void assertSalUsage(SalUsage expectedUsage, SalUsage actualUsage) {
        assertEquals(expectedUsage.getAssessmentName(), actualUsage.getAssessmentName());
        assertEquals(expectedUsage.getCoverageYear(), actualUsage.getCoverageYear());
        assertEquals(expectedUsage.getGrade(), actualUsage.getGrade());
        assertEquals(expectedUsage.getGradeGroup(), actualUsage.getGradeGroup());
        assertEquals(expectedUsage.getDetailType(), actualUsage.getDetailType());
        assertEquals(expectedUsage.getReportedWorkPortionId(), actualUsage.getReportedWorkPortionId());
        assertEquals(expectedUsage.getReportedStandardNumber(), actualUsage.getReportedStandardNumber());
        assertEquals(expectedUsage.getReportedMediaType(), actualUsage.getReportedMediaType());
        assertEquals(expectedUsage.getMediaTypeWeight(), actualUsage.getMediaTypeWeight());
        assertEquals(expectedUsage.getReportedArticle(), actualUsage.getReportedArticle());
        assertEquals(expectedUsage.getReportedAuthor(), actualUsage.getReportedAuthor());
        assertEquals(expectedUsage.getReportedPublisher(), actualUsage.getReportedPublisher());
        assertEquals(expectedUsage.getReportedPublicationDate(), actualUsage.getReportedPublicationDate());
        assertEquals(expectedUsage.getReportedPageRange(), actualUsage.getReportedPageRange());
        assertEquals(expectedUsage.getReportedVolNumberSeries(), actualUsage.getReportedVolNumberSeries());
        assertEquals(expectedUsage.getAssessmentType(), actualUsage.getAssessmentType());
        assertEquals(expectedUsage.getStates(), actualUsage.getStates());
        assertEquals(expectedUsage.getNumberOfViews(), actualUsage.getNumberOfViews());
        assertEquals(expectedUsage.getScoredAssessmentDate(), actualUsage.getScoredAssessmentDate());
        assertEquals(expectedUsage.getQuestionIdentifier(), actualUsage.getQuestionIdentifier());
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
