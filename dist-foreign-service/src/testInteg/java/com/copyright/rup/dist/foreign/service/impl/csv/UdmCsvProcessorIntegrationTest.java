package com.copyright.rup.dist.foreign.service.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.HeaderValidationException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ThresholdExceededException;
import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UdmUsage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Verifies {@link UdmCsvProcessor}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/30/21
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
public class UdmCsvProcessorIntegrationTest {

    private static final String BASE_PATH = "/com/copyright/rup/dist/foreign/service/impl/usage/acl/";
    private static final String PATH_TO_CSV = "src/testInteg/resources" + BASE_PATH;

    private final ReportTestUtils reportTestUtils = new ReportTestUtils(PATH_TO_CSV);

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(UdmCsvProcessorIntegrationTest.class);
    }

    @Test
    public void testProcessor() throws Exception {
        ProcessingResult<UdmUsage> result = processFile("udm_usage.csv");
        assertNotNull(result);
        List<UdmUsage> actualUsages = result.get();
        List<UdmUsage> expectedUsages = loadExpectedUsages();
        int expectedSize = 2;
        assertEquals(expectedSize, actualUsages.size());
        assertEquals(expectedSize, expectedUsages.size());
        IntStream.range(0, expectedSize).forEach(i ->
            assertUsage(expectedUsages.get(i), actualUsages.get(i))
        );
    }

    @Test
    public void testProcessorExportedUsagesWithInvalidHeader() throws Exception {
        try {
            processFile("invalid_header_exported_udm_usage_file.csv");
            fail();
        } catch (HeaderValidationException e) {
            assertEquals(
                "Columns headers are incorrect. Expected columns headers are:\n" +
                    "<ul>" +
                    "<li>usage_detail_id</li>" +
                    "<li>rs_survey_usage_data_uid</li>" +
                    "<li>name</li>" +
                    "<li>event_dtm</li>" +
                    "<li>wrk_inst</li>" +
                    "<li>std_no</li>" +
                    "<li>main_title</li>" +
                    "<li>article_title</li>" +
                    "<li>pub_type</li>" +
                    "<li>language</li>" +
                    "<li>pub_format</li>" +
                    "<li>tou</li>" +
                    "<li>tou_desc</li>" +
                    "<li>quantity</li>" +
                    "<li>user_id</li>" +
                    "<li>telesales_org_id</li>" +
                    "<li>country_name</li>" +
                    "<li>ip_address</li>" +
                    "<li>location_name</li>" +
                    "<li>survey_start_date</li>" +
                    "<li>survey_end_date</li>" +
                    "</ul>",
                e.getHtmlMessage());
        }
    }

    @Test
    public void testProcessorErrorsExceededThreshold() throws Exception {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        try {
            processFile("udm_usages_with_2000_errors.csv");
            fail();
        } catch (ThresholdExceededException ex) {
            assertEquals("The file could not be uploaded. There are more than 2000 errors", ex.getMessage());
            Executors.newSingleThreadExecutor().execute(() -> ex.getProcessingResult().writeToFile(outputStream));
            reportTestUtils.assertCsvReport("udm_usages_with_2000_errors_report.csv", pipedInputStream);
        }
    }

    @Test
    public void testProcessorForNegativePath() throws Exception {
        ProcessingResult<UdmUsage> result = processFile("udm_usages_with_errors.csv");
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        Executors.newSingleThreadExecutor().execute(() -> result.writeToFile(outputStream));
        reportTestUtils.assertCsvReport("udm_usages_with_errors_report.csv", pipedInputStream);
    }

    private ProcessingResult<UdmUsage> processFile(String file)
        throws IOException {
        ProcessingResult<UdmUsage> result;
        try (InputStream stream = this.getClass()
            .getResourceAsStream(BASE_PATH + "/" + file);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            IOUtils.copy(stream, outputStream);
            UdmCsvProcessor udmCsvProcessor = csvProcessorFactory.getUdmCsvProcessor();
            result = udmCsvProcessor.process(outputStream);
        }
        return result;
    }

    private List<UdmUsage> loadExpectedUsages() throws IOException {
        String content = TestUtils.fileToString(this.getClass(), BASE_PATH + "udm_usages.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, new TypeReference<List<UdmUsage>>() {
        });
    }

    private void assertUsage(UdmUsage expectedUsage, UdmUsage actualUsage) {
        assertNotNull(expectedUsage.getId());
        assertNotNull(actualUsage.getId());
        assertStoredEntity(expectedUsage);
        assertStoredEntity(actualUsage);
        assertEquals(expectedUsage.getOriginalDetailId(), actualUsage.getOriginalDetailId());
        assertEquals(expectedUsage.getArticle(), actualUsage.getArticle());
        assertEquals(expectedUsage.getReportedStandardNumber(), actualUsage.getReportedStandardNumber());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getUsageDate(), actualUsage.getUsageDate());
        assertEquals(expectedUsage.getReportedTitle(), actualUsage.getReportedTitle());
        assertEquals(expectedUsage.getReportedPubType(), actualUsage.getReportedPubType());
        assertEquals(expectedUsage.getReportedTypeOfUse(), actualUsage.getReportedTypeOfUse());
        assertEquals(expectedUsage.getQuantity(), actualUsage.getQuantity());
        assertEquals(expectedUsage.getLanguage(), actualUsage.getLanguage());
        assertEquals(expectedUsage.getSurveyRespondent(), actualUsage.getSurveyRespondent());
        assertEquals(expectedUsage.getCompanyId(), actualUsage.getCompanyId());
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getSurveyCountry(), actualUsage.getSurveyCountry());
        assertEquals(expectedUsage.getIpAddress(), actualUsage.getIpAddress());
        assertEquals(expectedUsage.getSurveyStartDate(), actualUsage.getSurveyStartDate());
        assertEquals(expectedUsage.getSurveyEndDate(), actualUsage.getSurveyEndDate());
    }

    private void assertStoredEntity(StoredEntity entity) {
        assertNotNull(entity.getCreateDate());
        assertNotNull(entity.getUpdateDate());
        assertEquals("SYSTEM", entity.getCreateUser());
    }
}
