package com.copyright.rup.dist.foreign.service.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.HeaderValidationException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
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
 * Verifies {@link AclGrantDetailCsvProcessor}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/30/2022
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclGrantDetailCsvProcessorIntegrationTest {

    private static final String BASE_PATH = "/com/copyright/rup/dist/foreign/service/impl/acl/grants/";
    private static final String PATH_TO_CSV = "src/testInteg/resources" + BASE_PATH;

    private final ReportTestUtils reportTestUtils = new ReportTestUtils(PATH_TO_CSV);

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(AclGrantDetailCsvProcessorIntegrationTest.class);
    }

    @Test
    public void testProcessor() throws Exception {
        verifyProcessorResult("acl_grant_detail.csv");
    }

    @Test
    @TestData(fileName = "acl-grant-detail-csv-processor-integration-test/test-processor.groovy")
    public void testProcessorWithErrors() throws Exception {
        ProcessingResult<AclGrantDetailDto> result = processFile("acl_grant_detail_with_errors.csv");
        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream(pos);
        Executors.newSingleThreadExecutor().execute(() -> result.writeToFile(pos));
        reportTestUtils.assertCsvReport("acl_grant_detail_with_errors_report.csv", pis);
    }

    @Test
    public void testProcessorWithInvalidHeader() throws Exception {
        try {
            processFile("acl_grant_detail_invalid_headers.csv");
            fail();
        } catch (HeaderValidationException e) {
            assertEquals(
                "Columns headers are incorrect. Expected columns headers are:\n" +
                    "<ul>" +
                    "<li>WR_WRK_INST</li>" +
                    "<li>TYPE_OF_USE</li>" +
                    "<li>RH_ACCOUNT_NUMBER</li>" +
                    "</ul>",
                e.getHtmlMessage());
        }
    }

    private void verifyProcessorResult(String fileName) throws IOException {
        ProcessingResult<AclGrantDetailDto> result = processFile(fileName);
        assertNotNull(result);
        List<AclGrantDetailDto> actualDetails = result.get();
        List<AclGrantDetailDto> expectedDetails = loadExpectedDetails();
        int expectedSize = 3;
        assertEquals(expectedSize, actualDetails.size());
        assertEquals(expectedSize, expectedDetails.size());
        IntStream.range(0, expectedSize).forEach(i ->
            assertDetail(expectedDetails.get(i), actualDetails.get(i))
        );
    }

    private ProcessingResult<AclGrantDetailDto> processFile(String file) throws IOException {
        ProcessingResult<AclGrantDetailDto> result;
        try (InputStream is = this.getClass().getResourceAsStream(BASE_PATH + "/" + file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            IOUtils.copy(is, baos);
            AclGrantDetailCsvProcessor processor =
                csvProcessorFactory.getAclGrantDetailCvsProcessor("e8d4ac06-e8e5-40b7-ade1-9a5dae1f1311");
            result = processor.process(baos);
        }
        return result;
    }

    private void assertDetail(AclGrantDetailDto expectedDetail, AclGrantDetailDto actualDetail) {
        assertNotNull(actualDetail.getId());
        assertEquals(expectedDetail.getWrWrkInst(), actualDetail.getWrWrkInst());
        assertEquals(expectedDetail.getTypeOfUse(), actualDetail.getTypeOfUse());
        assertEquals(expectedDetail.getRhAccountNumber(), actualDetail.getRhAccountNumber());
        assertEquals(expectedDetail.getGrantStatus(), actualDetail.getGrantStatus());
        assertTrue(actualDetail.getManualUploadFlag());
        assertTrue(actualDetail.getEligible());
    }

    private List<AclGrantDetailDto> loadExpectedDetails() throws IOException {
        String content = TestUtils.fileToString(this.getClass(), BASE_PATH + "acl_grant_detail.json");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<List<AclGrantDetailDto>>() {
        });
    }
}
