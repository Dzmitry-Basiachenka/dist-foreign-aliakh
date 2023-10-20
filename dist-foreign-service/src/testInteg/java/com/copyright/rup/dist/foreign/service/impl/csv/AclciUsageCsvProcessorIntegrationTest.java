package com.copyright.rup.dist.foreign.service.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.HeaderValidationException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ThresholdExceededException;
import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.foreign.domain.AclciUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;

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
 * Verifies {@link AclciUsageCsvProcessor}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/12/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
public class AclciUsageCsvProcessorIntegrationTest {

    private static final String BASE_PATH = "/com/copyright/rup/dist/foreign/service/impl/usage/aclci/";
    private static final String PATH_TO_CSV = "src/testInteg/resources" + BASE_PATH;

    private final ReportTestUtils reportTestUtils = new ReportTestUtils(PATH_TO_CSV);

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private ServiceTestHelper testHelper;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(AclciUsageCsvProcessorIntegrationTest.class);
    }

    @Test
    public void testProcessor() throws Exception {
        verifyProcessorResult("aclci_usages.csv");
    }

    @Test
    public void testProcessorMixedHeaders() throws Exception {
        verifyProcessorResult("aclci_usages_mixed_headers.csv");
    }

    @Test
    public void testProcessorErrors() throws Exception {
        ProcessingResult<Usage> result = processFile("aclci_usages_with_errors.csv");
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        Executors.newSingleThreadExecutor().execute(() -> result.writeToFile(outputStream));
        reportTestUtils.assertCsvReport("aclci_usages_with_errors_report.csv", inputStream);
    }

    @Test
    public void testProcessorErrorsExceededThreshold() throws Exception {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        try {
            processFile("aclci_usages_with_2000_errors.csv");
            fail();
        } catch (ThresholdExceededException e) {
            assertEquals("The file could not be uploaded. There are more than 2000 errors", e.getMessage());
            Executors.newSingleThreadExecutor().execute(() -> e.getProcessingResult().writeToFile(outputStream));
            reportTestUtils.assertCsvReport("aclci_usages_with_2000_errors_report.csv", inputStream);
        }
    }

    @Test
    public void testProcessorWithInvalidHeader() throws Exception {
        try {
            processFile("aclci_usages_invalid_header.csv");
            fail();
        } catch (HeaderValidationException e) {
            assertEquals(
                "Columns headers are incorrect. Expected columns headers are:\n" +
                    "<ul>" +
                    "<li>Coverage Period</li>" +
                    "<li>License Type</li>" +
                    "<li>Number of Students</li>" +
                    "<li>Reported Grade</li>" +
                    "<li>Wr Wrk Inst</li>" +
                    "<li>Reported Work Title</li>" +
                    "<li>Reported Standard Number or Image ID Number</li>" +
                    "<li>Reported Article or Chapter Title</li>" +
                    "<li>Reported Author</li>" +
                    "<li>Reported Publisher</li>" +
                    "<li>Reported Publication Date</li>" +
                    "<li>Reported Media Type</li>" +
                    "<li>Comment</li>" +
                    "</ul>",
                e.getHtmlMessage());
        }
    }

    private void verifyProcessorResult(String fileName) throws IOException {
        ProcessingResult<Usage> result = processFile(fileName);
        assertNotNull(result);
        List<Usage> actualUsages = result.get();
        List<Usage> expectedUsages = testHelper.loadExpectedUsages(BASE_PATH + "aclci_usages.json");
        assertEquals(expectedUsages.size(), actualUsages.size());
        IntStream.range(0, expectedUsages.size()).forEach(i ->
            assertUsage(expectedUsages.get(i), actualUsages.get(i))
        );
    }

    private ProcessingResult<Usage> processFile(String file) throws IOException {
        ProcessingResult<Usage> result;
        try (InputStream stream = this.getClass().getResourceAsStream(BASE_PATH + "/" + file);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            IOUtils.copy(stream, outputStream);
            AclciUsageCsvProcessor processor = csvProcessorFactory.getAclciUsageCsvProcessor();
            result = processor.process(outputStream);
        }
        return result;
    }

    private void assertUsage(Usage expectedUsage, Usage actualUsage) {
        assertNotNull(expectedUsage.getId());
        assertNotNull(actualUsage.getId());
        assertStoredEntity(expectedUsage);
        assertStoredEntity(actualUsage);
        assertUsageParsedFields(expectedUsage, actualUsage);
    }

    private void assertStoredEntity(StoredEntity<?> entity) {
        assertNotNull(entity.getCreateDate());
        assertNotNull(entity.getUpdateDate());
        assertEquals("SYSTEM", entity.getCreateUser());
    }

    private void assertUsageParsedFields(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getNumberOfCopies(), actualUsage.getNumberOfCopies());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        assertAclciUsage(expectedUsage.getAclciUsage(), actualUsage.getAclciUsage());
    }

    private void assertAclciUsage(AclciUsage expectedUsage, AclciUsage actualUsage)  {
        assertEquals(expectedUsage.getLicenseeAccountNumber(), actualUsage.getLicenseeAccountNumber());
        assertEquals(expectedUsage.getLicenseeName(), actualUsage.getLicenseeName());
        assertEquals(expectedUsage.getReportedNumberOfStudents(), actualUsage.getReportedNumberOfStudents());
        assertEquals(expectedUsage.getCoveragePeriod(), actualUsage.getCoveragePeriod());
        assertEquals(expectedUsage.getLicenseType(), actualUsage.getLicenseType());
        assertEquals(expectedUsage.getReportedMediaType(), actualUsage.getReportedMediaType());
        assertEquals(expectedUsage.getMediaTypeWeight(), actualUsage.getMediaTypeWeight());
        assertEquals(expectedUsage.getReportedArticle(), actualUsage.getReportedArticle());
        assertEquals(expectedUsage.getReportedStandardNumber(), actualUsage.getReportedStandardNumber());
        assertEquals(expectedUsage.getReportedAuthor(), actualUsage.getReportedAuthor());
        assertEquals(expectedUsage.getReportedPublisher(), actualUsage.getReportedPublisher());
        assertEquals(expectedUsage.getReportedPublicationDate(), actualUsage.getReportedPublicationDate());
        assertEquals(expectedUsage.getReportedGrade(), actualUsage.getReportedGrade());
        assertEquals(expectedUsage.getGradeGroup(), actualUsage.getGradeGroup());
        assertEquals(expectedUsage.getBatchPeriodEndDate(), actualUsage.getBatchPeriodEndDate());
    }
}

