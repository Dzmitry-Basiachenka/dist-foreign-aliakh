package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPool.AclciFields;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Verifies {@link FundPoolRepository}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/26/2019
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class FundPoolRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "fund-pool-repository-integration-test/";
    private static final String NTS_FUND_POOL_ID_1 = "b5b64c3a-55d2-462e-b169-362dca6a4dd7";
    private static final String NTS_FUND_POOL_ID_2 = "76282dbc-2468-48d4-b926-93d3458a656b";
    private static final String NTS_FUND_POOL_ID_3 = "6fe5044d-15a3-47fe-913e-69f3bf353bef";
    private static final String AACL_FUND_POOL_ID = "ce9c1258-6d29-4224-a4e6-6f03b6aeef53";
    private static final String SAL_FUND_POOL_ID = "8b1ba84d-4f51-4072-bb6f-55ab6a65a1e1";
    private static final String ACLCI_FUND_POOL_ID = "8e8319da-ea31-401a-8b13-16a8d1ef9d28";
    private static final String NAME_1 = "Q1 2019 100%";
    private static final String NAME_2 = "NTS Q2 2019";
    private static final String ACLCI_FUND_POOL_NAME = "ACLCIC Fund Pool";
    private static final String COMMENT_1 = "some comment";
    private static final String COMMENT_2 = "other comment";
    private static final String SAL_PRODUCT_FAMILY = "SAL";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final String ACLCI_PRODUCT_FAMILY = "ACLCI";
    private static final String USER_NAME = "user@copyright.com";

    @Autowired
    private FundPoolRepository fundPoolRepository;

    @Test
    @TestData(fileName = "rollback-only.groovy")
    public void testInsert() {
        assertNull(fundPoolRepository.findById(NTS_FUND_POOL_ID_2));
        FundPool fundPool = new FundPool();
        fundPool.setId(NTS_FUND_POOL_ID_2);
        fundPool.setProductFamily(NTS_PRODUCT_FAMILY);
        fundPool.setName(NAME_2);
        fundPool.setComment(COMMENT_2);
        fundPoolRepository.insert(fundPool);
        FundPool actualFundPool = fundPoolRepository.findById(NTS_FUND_POOL_ID_2);
        assertNotNull(actualFundPool);
        assertEquals(NTS_FUND_POOL_ID_2, actualFundPool.getId());
        assertEquals(NTS_PRODUCT_FAMILY, actualFundPool.getProductFamily());
        assertEquals(NAME_2, actualFundPool.getName());
        assertEquals(COMMENT_2, actualFundPool.getComment());
    }

    @Test
    @TestData(fileName = "rollback-only.groovy")
    public void testInsertSalFundPool() {
        assertNull(fundPoolRepository.findById(SAL_FUND_POOL_ID));
        FundPool.SalFields salFields = buildSalFields();
        BigDecimal totalAmount = new BigDecimal("200.00");
        FundPool fundPool = new FundPool();
        fundPool.setId(SAL_FUND_POOL_ID);
        fundPool.setProductFamily(SAL_PRODUCT_FAMILY);
        fundPool.setName(NAME_2);
        fundPool.setComment(COMMENT_2);
        fundPool.setTotalAmount(totalAmount);
        fundPool.setSalFields(salFields);
        fundPoolRepository.insert(fundPool);
        FundPool insertedFundPool = fundPoolRepository.findById(SAL_FUND_POOL_ID);
        assertNotNull(insertedFundPool);
        assertEquals(SAL_FUND_POOL_ID, insertedFundPool.getId());
        assertEquals(SAL_PRODUCT_FAMILY, insertedFundPool.getProductFamily());
        assertEquals(NAME_2, insertedFundPool.getName());
        assertEquals(COMMENT_2, insertedFundPool.getComment());
        assertEquals(totalAmount, insertedFundPool.getTotalAmount());
        assertEquals(salFields, insertedFundPool.getSalFields());
    }

    @Test
    @TestData(fileName = "rollback-only.groovy")
    public void testInsertAclciFundPool() {
        assertNull(fundPoolRepository.findById(ACLCI_FUND_POOL_ID));
        FundPool.AclciFields aclciFields = buildAclciFields();
        BigDecimal totalAmount = new BigDecimal("2000.00");
        FundPool fundPool = new FundPool();
        fundPool.setId(ACLCI_FUND_POOL_ID);
        fundPool.setProductFamily(ACLCI_PRODUCT_FAMILY);
        fundPool.setName(ACLCI_FUND_POOL_NAME);
        fundPool.setComment(COMMENT_2);
        fundPool.setTotalAmount(totalAmount);
        fundPool.setAclciFields(aclciFields);
        fundPoolRepository.insert(fundPool);
        FundPool insertedFundPool = fundPoolRepository.findById(ACLCI_FUND_POOL_ID);
        assertNotNull(insertedFundPool);
        assertEquals(ACLCI_FUND_POOL_ID, insertedFundPool.getId());
        assertEquals(ACLCI_PRODUCT_FAMILY, insertedFundPool.getProductFamily());
        assertEquals(ACLCI_FUND_POOL_NAME, insertedFundPool.getName());
        assertEquals(COMMENT_2, insertedFundPool.getComment());
        assertEquals(totalAmount, insertedFundPool.getTotalAmount());
        assertEquals(aclciFields, insertedFundPool.getAclciFields());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-id.groovy")
    public void testFindByIdForNts() {
        FundPool fundPool = fundPoolRepository.findById(NTS_FUND_POOL_ID_1);
        assertNotNull(fundPool);
        assertEquals(NTS_FUND_POOL_ID_1, fundPool.getId());
        assertEquals(NAME_1, fundPool.getName());
        assertEquals(new BigDecimal("50.00"), fundPool.getTotalAmount());
        assertEquals(COMMENT_1, fundPool.getComment());
        assertEquals(NTS_PRODUCT_FAMILY, fundPool.getProductFamily());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-details-by-fund-pool-id.groovy")
    public void testFindByIdForAacl() {
        FundPool fundPool = fundPoolRepository.findById(AACL_FUND_POOL_ID);
        assertNotNull(fundPool);
        assertEquals(AACL_FUND_POOL_ID, fundPool.getId());
        assertEquals("AACL Fund Pool 1", fundPool.getName());
        assertEquals(new BigDecimal("31.20"), fundPool.getTotalAmount());
        assertNull(fundPool.getComment());
        assertEquals(AACL_PRODUCT_FAMILY, fundPool.getProductFamily());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-product-family.groovy")
    public void testFindByProductFamily() {
        List<FundPool> fundPools = fundPoolRepository.findByProductFamily(NTS_PRODUCT_FAMILY);
        assertEquals(3, fundPools.size());
        assertFundPool(fundPools.get(0), NTS_FUND_POOL_ID_1, NTS_PRODUCT_FAMILY, NAME_1, new BigDecimal("50.00"),
            "some comment");
        assertFundPool(fundPools.get(1), "49060c9b-9cc2-4b93-b701-fffc82eb28b0", NTS_PRODUCT_FAMILY, "Test fund",
            new BigDecimal("10.00"), "test comment");
        assertFundPool(fundPools.get(2), "a40132c0-d724-4450-81d2-456e67ff6f64", NTS_PRODUCT_FAMILY,
            "Archived Pre-Service fee fund", new BigDecimal("99.00"), null);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-nts-not-attached-to-scenario.groovy")
    public void testFindNtsNotAttachedToScenario() {
        List<FundPool> fundPools = fundPoolRepository.findNtsNotAttachedToScenario();
        assertEquals(1, fundPools.size());
        assertEquals(fundPools.get(0).getId(), "49060c9b-9cc2-4b93-b701-fffc82eb28b0");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-aacl-not-attached-to-scenario.groovy")
    public void testFindAaclNotAttachedToScenario() {
        List<String> actualIds = fundPoolRepository.findAaclNotAttachedToScenario().stream()
            .map(FundPool::getId)
            .collect(Collectors.toList());
        List<String> expectedIds =
            List.of("6d38454b-ce71-4b0e-8ecf-436d23dc6c3e", "ce9c1258-6d29-4224-a4e6-6f03b6aeef53",
                "100ce91c-49c1-4197-9f7a-23a8210d5706");
        assertEquals(expectedIds, actualIds);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-sal-not-attached-to-scenario.groovy")
    public void testFindSalNotAttachedToScenario() {
        List<String> actualIds = fundPoolRepository.findSalNotAttachedToScenario().stream()
            .map(FundPool::getId)
            .collect(Collectors.toList());
        List<String> expectedIds =
            List.of("1ea65e2a-22c1-4a96-b55b-b6b4fd7d51ed", "15c3023d-1e68-4b7d-bfe3-18e85806b167");
        assertEquals(expectedIds, actualIds);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-names-by-usage-batch-id.groovy")
    public void testFindNamesByUsageBatchId() {
        List<String> names = fundPoolRepository.findNamesByUsageBatchId("63b45167-a6ce-4cd5-84c6-5167916aee98");
        assertEquals(0, names.size());
        names = fundPoolRepository.findNamesByUsageBatchId("a163cca7-8eeb-449c-8a3c-29ff3ec82e58");
        assertEquals(1, names.size());
        assertEquals("Test fund", names.get(0));
        names = fundPoolRepository.findNamesByUsageBatchId("1a615c47-531a-4a27-a4f3-a5bd3d5a4b1c");
        assertEquals(1, names.size());
        assertEquals("Archived Pre-Service fee fund", names.get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-id.groovy")
    public void testDelete() {
        assertNotNull(fundPoolRepository.findById(NTS_FUND_POOL_ID_1));
        fundPoolRepository.delete(NTS_FUND_POOL_ID_1);
        assertNull(fundPoolRepository.findById(NTS_FUND_POOL_ID_1));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "fund-pool-exists.groovy")
    public void testFundPoolExists() {
        assertTrue(fundPoolRepository.fundPoolExists(NTS_PRODUCT_FAMILY, NAME_1));
        assertTrue(fundPoolRepository.fundPoolExists(NTS_PRODUCT_FAMILY, "q1 2019 100%"));
        assertFalse(fundPoolRepository.fundPoolExists(NTS_PRODUCT_FAMILY, "Q1 2019"));
        assertFalse(fundPoolRepository.fundPoolExists(AACL_PRODUCT_FAMILY, NAME_1));
        FundPool fundPool = new FundPool();
        fundPool.setId(NTS_FUND_POOL_ID_3);
        fundPool.setProductFamily(AACL_PRODUCT_FAMILY);
        fundPool.setName(NAME_1);
        fundPoolRepository.insert(fundPool);
        assertTrue(fundPoolRepository.fundPoolExists(AACL_PRODUCT_FAMILY, NAME_1));
        assertTrue(fundPoolRepository.fundPoolExists(AACL_PRODUCT_FAMILY, "q1 2019 100%"));
        assertFalse(fundPoolRepository.fundPoolExists(AACL_PRODUCT_FAMILY, "Q1 2019"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "insert-detail.groovy")
    public void testInsertDetail() {
        String fundPoolId = "6d38454b-ce71-4b0e-8ecf-436d23dc6c3e";
        FundPoolDetail detail = new FundPoolDetail();
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(111);
        detail.setId("5f2da414-5ac1-44ce-9060-30219a6aca93");
        detail.setFundPoolId(fundPoolId);
        detail.setAggregateLicenseeClass(aggregateLicenseeClass);
        detail.setGrossAmount(BigDecimal.ONE);
        detail.setCreateUser(USER_NAME);
        detail.setUpdateUser(USER_NAME);
        fundPoolRepository.insertDetail(detail);
        List<FundPoolDetail> details = fundPoolRepository.findDetailsByFundPoolId(fundPoolId);
        assertEquals(1, details.size());
        FundPoolDetail actualDetail = details.get(0);
        assertEquals(detail.getId(), actualDetail.getId());
        assertEquals(detail.getAggregateLicenseeClass().getId(), actualDetail.getAggregateLicenseeClass().getId());
        assertEquals(new BigDecimal("1.00"), actualDetail.getGrossAmount());
        assertEquals(USER_NAME, actualDetail.getCreateUser());
        assertEquals(USER_NAME, actualDetail.getUpdateUser());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-details-by-fund-pool-id.groovy")
    public void testFindDetailsByFundPoolId() throws IOException {
        List<FundPoolDetail> expectedDetails = loadExpectedFundPoolDetails("expected_aacl_fund_pool_details.json");
        List<FundPoolDetail> actualDetails = fundPoolRepository.findDetailsByFundPoolId(AACL_FUND_POOL_ID);
        assertEquals(expectedDetails.size(), actualDetails.size());
        IntStream.range(0, expectedDetails.size())
            .forEach(i -> assertFundPoolDetail(expectedDetails.get(i), actualDetails.get(i)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-details-by-fund-pool-id.groovy")
    public void testDeleteDetailsByFundPoolId() {
        assertEquals(2, fundPoolRepository.findDetailsByFundPoolId(AACL_FUND_POOL_ID).size());
        fundPoolRepository.deleteDetailsByFundPoolId(AACL_FUND_POOL_ID);
        assertEquals(0, fundPoolRepository.findDetailsByFundPoolId(AACL_FUND_POOL_ID).size());
    }

    private List<FundPoolDetail> loadExpectedFundPoolDetails(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), "json/aacl/" + fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<FundPoolDetail>>() {
        });
    }

    private void assertFundPool(FundPool fundPool, String id, String productFamily, String name, BigDecimal amount,
                                String comment) {
        assertEquals(id, fundPool.getId());
        assertEquals(productFamily, fundPool.getProductFamily());
        assertEquals(name, fundPool.getName());
        assertEquals(amount, fundPool.getTotalAmount());
        assertEquals(comment, fundPool.getComment());
    }

    private void assertFundPoolDetail(FundPoolDetail expected, FundPoolDetail actual) {
        assertEquals(expected.getId(), actual.getId());
        assertNotNull(actual.getAggregateLicenseeClass());
        AggregateLicenseeClass expectedAggregate = expected.getAggregateLicenseeClass();
        AggregateLicenseeClass actualAggregate = actual.getAggregateLicenseeClass();
        assertEquals(expectedAggregate.getId(), actualAggregate.getId());
        assertEquals(expectedAggregate.getEnrollmentProfile(), actualAggregate.getEnrollmentProfile());
        assertEquals(expectedAggregate.getDiscipline(), actualAggregate.getDiscipline());
        assertEquals(expected.getGrossAmount(), actual.getGrossAmount());
    }

    private FundPool.SalFields buildSalFields() {
        FundPool.SalFields salFields = new FundPool.SalFields();
        salFields.setDateReceived(LocalDate.of(2020, 12, 24));
        salFields.setAssessmentName("FY2020 COG");
        salFields.setLicenseeAccountNumber(1000008985L);
        salFields.setLicenseeName("FarmField Inc.");
        salFields.setGradeKto5NumberOfStudents(10);
        salFields.setGrade6to8NumberOfStudents(5);
        salFields.setGrossAmount(new BigDecimal("1000.0"));
        salFields.setItemBankGrossAmount(new BigDecimal("20.01"));
        salFields.setGradeKto5GrossAmount(new BigDecimal("653.33"));
        salFields.setGrade6to8GrossAmount(new BigDecimal("326.66"));
        salFields.setGrade9to12GrossAmount(new BigDecimal("0.0"));
        salFields.setItemBankSplitPercent(new BigDecimal("0.02"));
        salFields.setServiceFee(new BigDecimal("0.25"));
        return salFields;
    }

    private FundPool.AclciFields buildAclciFields() {
        FundPool.AclciFields aclciFields = new AclciFields();
        aclciFields.setCoverageYears("2022-2023");
        aclciFields.setGradeKto2NumberOfStudents(2);
        aclciFields.setGrade3to5NumberOfStudents(5);
        aclciFields.setGrade6to8NumberOfStudents(8);
        aclciFields.setGrade9to12NumberOfStudents(12);
        aclciFields.setGradeHeNumberOfStudents(20);
        aclciFields.setGrossAmount(new BigDecimal("1000.0"));
        aclciFields.setCurriculumDbGrossAmount(new BigDecimal("200.0"));
        aclciFields.setGradeKto2GrossAmount(new BigDecimal("34.04"));
        aclciFields.setGrade3to5GrossAmount(new BigDecimal("85.11"));
        aclciFields.setGrade6to8GrossAmount(new BigDecimal("136.17"));
        aclciFields.setGrade9to12GrossAmount(new BigDecimal("204.26"));
        aclciFields.setGradeHeGrossAmount(new BigDecimal("340.43"));
        aclciFields.setCurriculumDbSplitPercent(new BigDecimal("0.2"));
        return aclciFields;
    }
}
