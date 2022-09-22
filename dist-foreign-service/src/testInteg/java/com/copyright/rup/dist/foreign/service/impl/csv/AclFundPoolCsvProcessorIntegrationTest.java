package com.copyright.rup.dist.foreign.service.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.HeaderValidationException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
 * Verifies {@link AclFundPoolCsvProcessor}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/27/2022
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclFundPoolCsvProcessorIntegrationTest {

    private static final String BASE_PATH = "/com/copyright/rup/dist/foreign/service/impl/acl/fundpool/";
    private static final String PATH_TO_CSV = "src/testInteg/resources" + BASE_PATH;

    private final ReportTestUtils reportTestUtils = new ReportTestUtils(PATH_TO_CSV);

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(AclFundPoolCsvProcessorIntegrationTest.class);
    }

    @Test
    public void testProcessor() throws Exception {
        verifyProcessorResult("acl_fund_pool.csv");
    }

    @Test
    public void testProcessorWithErrors() throws Exception {
        ProcessingResult<AclFundPoolDetail> result = processFile("acl_fund_pool_with_errors.csv");
        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream(pos);
        Executors.newSingleThreadExecutor().execute(() -> result.writeToFile(pos));
        reportTestUtils.assertCsvReport("acl_fund_pool_with_errors_report.csv", pis);
    }

    @Test
    public void testProcessorWithInvalidHeader() throws Exception {
        try {
            processFile("acl_fund_pool_invalid_headers.csv");
            fail();
        } catch (HeaderValidationException e) {
            assertEquals(
                "Columns headers are incorrect. Expected columns headers are:\n" +
                    "<ul>" +
                    "<li>DET_LC_ID</li>" +
                    "<li>FUND_POOL_TYPE</li>" +
                    "<li>NET_AMOUNT</li>" +
                    "<li>GROSS_AMOUNT</li>" +
                    "</ul>",
                e.getHtmlMessage());
        }
    }

    private void verifyProcessorResult(String fileName) throws IOException {
        ProcessingResult<AclFundPoolDetail> result = processFile(fileName);
        assertNotNull(result);
        List<AclFundPoolDetail> actualDetails = result.get();
        List<AclFundPoolDetail> expectedDetails = loadExpectedDetails();
        int expectedSize = 9;
        assertEquals(expectedSize, actualDetails.size());
        assertEquals(expectedSize, expectedDetails.size());
        IntStream.range(0, expectedSize).forEach(i ->
            assertDetail(expectedDetails.get(i), actualDetails.get(i))
        );
    }

    private ProcessingResult<AclFundPoolDetail> processFile(String file) throws IOException {
        ProcessingResult<AclFundPoolDetail> result;
        try (InputStream is = this.getClass().getResourceAsStream(BASE_PATH + "/" + file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            IOUtils.copy(is, baos);
            AclFundPoolCsvProcessor processor = csvProcessorFactory.getAclFundPoolCvsProcessor();
            result = processor.process(baos);
        }
        return result;
    }

    private void assertDetail(AclFundPoolDetail expectedDetail, AclFundPoolDetail actualDetail) {
        assertEquals(expectedDetail.getDetailLicenseeClass().getId(),
            actualDetail.getDetailLicenseeClass().getId());
        assertEquals(expectedDetail.getGrossAmount(), actualDetail.getGrossAmount());
        assertEquals(expectedDetail.getNetAmount(), actualDetail.getNetAmount());
        assertEquals(expectedDetail.getTypeOfUse(), actualDetail.getTypeOfUse());
        assertEquals(expectedDetail.getLicenseType(), actualDetail.getLicenseType());
        assertEquals(expectedDetail.isLdmtFlag(), actualDetail.isLdmtFlag());
    }

    private List<AclFundPoolDetail> loadExpectedDetails() throws IOException {
        String content = TestUtils.fileToString(this.getClass(), BASE_PATH + "acl_fund_pool.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, new TypeReference<List<AclFundPoolDetail>>() {
        });
    }
}
