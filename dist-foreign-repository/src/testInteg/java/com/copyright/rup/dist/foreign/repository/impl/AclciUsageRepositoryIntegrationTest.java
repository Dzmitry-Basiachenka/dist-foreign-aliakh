package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclciUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.repository.api.IAclciUsageRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Integration test for {@link AclciUsageRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/08/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclciUsageRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "aclci-usage-repository-integration-test/";
    private static final String USAGE_ID_1 = "5262bc87-e5b4-447b-9294-122e41f01c7e";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Autowired
    private IAclciUsageRepository aclciUsageRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "insert.groovy")
    public void testInsert() {
        Usage expectedUsage =
            loadExpectedUsages(Collections.singletonList("json/aclci/aclci_usage_5262bc87.json")).get(0);
        aclciUsageRepository.insert(expectedUsage);
        List<Usage> actualUsages = aclciUsageRepository.findByIds(Collections.singletonList(USAGE_ID_1));
        assertEquals(1, actualUsages.size());
        verifyUsage(expectedUsage, actualUsages.get(0));
    }

    private void verifyUsage(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getId(), actualUsage.getId());
        verifyUsageIgnoringId(expectedUsage, actualUsage);
    }

    private void verifyUsageIgnoringId(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getBatchId(), actualUsage.getBatchId());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
        assertEquals(expectedUsage.getRightsholder().getName(), actualUsage.getRightsholder().getName());
        assertEquals(expectedUsage.getRightsholder().getId(), actualUsage.getRightsholder().getId());
        assertEquals(expectedUsage.getRightsholder().getAccountNumber(),
            actualUsage.getRightsholder().getAccountNumber());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        verifyAclciUsage(expectedUsage.getAclciUsage(), actualUsage.getAclciUsage());
    }

    private void verifyAclciUsage(AclciUsage expectedUsage, AclciUsage actualUsage)  {
        assertEquals(expectedUsage.getLicenseeAccountNumber(), actualUsage.getLicenseeAccountNumber());
        assertEquals(expectedUsage.getLicenseeName(), actualUsage.getLicenseeName());
        assertEquals(expectedUsage.getCoveragePeriod(), actualUsage.getCoveragePeriod());
        assertEquals(expectedUsage.getLicenseType(), actualUsage.getLicenseType());
        assertEquals(expectedUsage.getReportedMediaType(), actualUsage.getReportedMediaType());
        assertEquals(expectedUsage.getMediaTypeWeight(), actualUsage.getMediaTypeWeight());
        assertEquals(expectedUsage.getReportedArticle(), actualUsage.getReportedArticle());
        assertEquals(expectedUsage.getReportedStandardNumber(), actualUsage.getReportedStandardNumber());
        assertEquals(expectedUsage.getReportedAuthor(), actualUsage.getReportedAuthor());
        assertEquals(expectedUsage.getReportedPublisher(), actualUsage.getReportedPublisher());
        assertEquals(expectedUsage.getReportedPublicationDate(), actualUsage.getReportedPublicationDate());
        assertEquals(expectedUsage.getReportedPageRange(), actualUsage.getReportedPageRange());
        assertEquals(expectedUsage.getReportedVolNumberSeries(), actualUsage.getReportedVolNumberSeries());
        assertEquals(expectedUsage.getReportedGrade(), actualUsage.getReportedGrade());
        assertEquals(expectedUsage.getGradeGroup(), actualUsage.getGradeGroup());
        assertEquals(expectedUsage.getBatchPeriodEndDate(), actualUsage.getBatchPeriodEndDate());
    }

    private List<Usage> loadExpectedUsages(List<String> fileNames) {
        List<Usage> usages = new ArrayList<>();
        fileNames.forEach(fileName -> {
            try {
                String content = TestUtils.fileToString(this.getClass(), fileName);
                usages.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<Usage>>() {
                }));
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        });
        return usages;
    }
}
