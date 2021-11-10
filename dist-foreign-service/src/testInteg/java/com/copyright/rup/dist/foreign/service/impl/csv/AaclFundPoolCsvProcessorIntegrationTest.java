package com.copyright.rup.dist.foreign.service.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.HeaderValidationException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;

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
 * Verifies {@link AaclFundPoolCsvProcessor}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/02/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
public class AaclFundPoolCsvProcessorIntegrationTest {

    private static final String BASE_PATH = "/com/copyright/rup/dist/foreign/service/impl/aacl/fundpool/";
    private static final String PATH_TO_CSV = "src/testInteg/resources" + BASE_PATH;

    private final ReportTestUtils reportTestUtils = new ReportTestUtils(PATH_TO_CSV);

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(AaclFundPoolCsvProcessorIntegrationTest.class);
    }

    @Test
    public void testProcessor() throws Exception {
        verifyProcessorResult("aacl_fund_pool.csv");
    }

    @Test
    public void testProcessorMixedHeaders() throws Exception {
        verifyProcessorResult("aacl_fund_pool_mixed_headers.csv");
    }

    @Test
    public void testProcessorWithErrors() throws Exception {
        ProcessingResult<FundPoolDetail> result = processFile("aacl_fund_pool_with_errors.csv");
        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream(pos);
        Executors.newSingleThreadExecutor().execute(() -> result.writeToFile(pos));
        reportTestUtils.assertCsvReport("aacl_fund_pool_with_errors_report.csv", pis);
    }

    @Test
    public void testProcessorWithInvalidHeader() throws Exception {
        try {
            processFile("aacl_fund_pool_invalid_headers.csv");
            fail();
        } catch (HeaderValidationException e) {
            assertEquals(
                "Columns headers are incorrect. Expected columns headers are:\n" +
                    "<ul>" +
                    "<li>Agg LC ID</li>" +
                    "<li>Gross Amount</li>" +
                    "</ul>",
                e.getHtmlMessage());
        }
    }

    private void verifyProcessorResult(String fileName) throws IOException {
        ProcessingResult<FundPoolDetail> result = processFile(fileName);
        assertNotNull(result);
        List<FundPoolDetail> actualDetails = result.get();
        List<FundPoolDetail> expectedDetails = loadExpectedDetails();
        int expectedSize = 7;
        assertEquals(expectedSize, actualDetails.size());
        assertEquals(expectedSize, expectedDetails.size());
        IntStream.range(0, expectedSize).forEach(i ->
            assertDetail(expectedDetails.get(i), actualDetails.get(i))
        );
    }

    private ProcessingResult<FundPoolDetail> processFile(String file) throws IOException {
        ProcessingResult<FundPoolDetail> result;
        try (InputStream is = this.getClass().getResourceAsStream(BASE_PATH + "/" + file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            IOUtils.copy(is, baos);
            AaclFundPoolCsvProcessor processor = csvProcessorFactory.getAaclFundPoolCsvProcessor();
            result = processor.process(baos);
        }
        return result;
    }

    private void assertDetail(FundPoolDetail expectedDetail, FundPoolDetail actualDetail) {
        assertEquals(expectedDetail.getAggregateLicenseeClass().getId(),
            actualDetail.getAggregateLicenseeClass().getId());
        assertEquals(expectedDetail.getGrossAmount(), actualDetail.getGrossAmount());
    }

    private List<FundPoolDetail> loadExpectedDetails() throws IOException {
        String content = TestUtils.fileToString(this.getClass(), BASE_PATH + "aacl_fund_pool.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, new TypeReference<List<FundPoolDetail>>() {
        });
    }
}
