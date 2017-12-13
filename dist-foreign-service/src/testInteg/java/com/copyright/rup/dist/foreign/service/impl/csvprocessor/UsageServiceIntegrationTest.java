package com.copyright.rup.dist.foreign.service.impl.csvprocessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.ReportMatcher;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.exception.ThresholdExceededException;

import com.google.common.collect.Lists;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Verifies {@link CsvErrorResultWriter}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/22/17
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=usage-service-data-init.groovy"})
@TransactionConfiguration
@Transactional
public class UsageServiceIntegrationTest {

    private static final String PATH_TO_ACTUAL_REPORT = "build/temp";
    private static final String FILE_NAME = "errors_report.csv";
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private CsvProcessingResult<Usage> result;

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUsageAuditRepository auditRepository;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        FileUtils.deleteQuietly(Paths.get(PATH_TO_ACTUAL_REPORT).toFile());
        Files.createDirectory(Paths.get(PATH_TO_ACTUAL_REPORT));
    }

    @Test
    public void testWriteErrorsToFile() throws Exception {
        List<String> headers = buildHeaders();
        result = new CsvProcessingResult<>(headers, "usages.csv");
        logErrors();
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        executorService.execute(() -> usageService.writeErrorsToFile(result, outputStream));
        FileUtils.copyInputStreamToFile(pipedInputStream, new File(PATH_TO_ACTUAL_REPORT, FILE_NAME));
        verifyReport();
    }

    @Test
    public void testDeleteFromScenario() {
        Scenario scenario = new Scenario();
        scenario.setId("12ec845f-0e76-4d1c-85cd-bb3fb7ca260e");
        scenario.setName("Test Scenario for exclude");
        usageService.deleteFromScenario(scenario, 2000017001L, Lists.newArrayList(1000000003L, 1000000004L),
            "Exclude reason");
        verifyExcludedUsages(scenario.getId(), true, 1000000003L, 1000000004L);
        verifyExcludedUsages(scenario.getId(), false, 1000000001L, 1000000002L, 1000000006L);
        verifyAuditItems(auditRepository.findByUsageId("2641e7fe-2a5a-4cdf-8879-48816d705169"));
        verifyAuditItems(auditRepository.findByUsageId("405491b1-49a9-4b70-9cdb-d082be6a802d"));
        assertTrue(auditRepository.findByUsageId("9f96760c-0de9-4cee-abf2-65521277281b").isEmpty());
        assertTrue(auditRepository.findByUsageId("e4a81fad-7b0e-4c67-8df2-112c8913e45e").isEmpty());
        assertTrue(auditRepository.findByUsageId("4ddfcb74-cb72-48f6-9ee4-8b4e05afce75").isEmpty());
    }

    private void verifyExcludedUsages(String scenarioId, boolean excluded,  Long... accountNumbers) {
        Pageable pageable = new Pageable(0, 10);
        Arrays.stream(accountNumbers)
            .forEach(accountNumber -> assertEquals(excluded,
                usageService.getByScenarioIdAndRhAccountNumber(accountNumber, scenarioId,
                    StringUtils.EMPTY, pageable, null).isEmpty()));
    }

    private void verifyAuditItems(List<UsageAuditItem> auditItems) {
        assertEquals(1, auditItems.size());
        UsageAuditItem auditItem = auditItems.get(0);
        assertEquals("12ec845f-0e76-4d1c-85cd-bb3fb7ca260e", auditItem.getScenarioId());
        assertEquals(UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, auditItem.getActionType());
        assertEquals("Exclude reason", auditItem.getActionReason());
        assertEquals("Test Scenario for exclude", auditItem.getScenarioName());
    }

    private void logErrors() throws ThresholdExceededException{
        result.logError(2, buildOriginalLineWithErrors(Lists.newArrayList(0), Lists.newArrayList(StringUtils.EMPTY)),
            "Detail ID: Field is required and cannot be null or empty");
        result.logError(5, buildOriginalLineWithErrors(Lists.newArrayList(5), Lists.newArrayList("text")),
            "RH Acct Number: Field value should be numeric");
        List<String> lines =
            buildOriginalLineWithErrors(Lists.newArrayList(0), Lists.newArrayList("text more then 10 symbols"));
        result.logError(3, lines, "Detail ID: Field value should be numeric");
        result.logError(3, lines, "Detail ID: Field max length is 10 characters");
        lines = buildOriginalLineWithErrors(Lists.newArrayList(8, 4), Lists.newArrayList("44.123", "text"));
        result.logError(7, lines, "Reported Value: Field value must be Numeric with decimals to 2 places");
        result.logError(7, lines, "Wr Wrk Inst: Field value should be numeric");
    }

    private List<String> buildOriginalLineWithErrors(List<Integer> indexesForReplace, List<String> values) {
        List<String> columns =
            Lists.newArrayList("234", "1984", "Appendix: The Principles of Newspeak", "9.78015E+12", "123456789",
                "1000009522", "Publisher", "12/22/3000", "65", "30.86", "Univ,Bus,Doc,S", "2015", "2016",
                "Aarseth, Espen J.");
        IntStream.range(0, indexesForReplace.size()).forEach(i -> columns.set(indexesForReplace.get(i), values.get(i)));
        return columns;
    }

    private List<String> buildHeaders() {
        return Lists.newArrayList("Detail ID", "Title", "Article", "Standard Number", "Wr Wrk Inst", "RH Acct Number",
            "Publisher", "Pub Date", "Number of Copies", "Reported Value", "Market", "Market Period From",
            "Market Period To", "Author");
    }

    private void verifyReport() {
        assertTrue(new ReportMatcher(
            new File("src/testInteg/resources/com/copyright/rup/dist/foreign/service/csv", FILE_NAME)).matches(
            new File(PATH_TO_ACTUAL_REPORT, FILE_NAME)));
    }
}
