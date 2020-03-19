package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
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
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=fund-pool-repository-test-data-init.groovy"})
@Transactional
public class FundPoolRepositoryIntegrationTest {

    private static final String NTS_FUND_POOL_ID_1 = "b5b64c3a-55d2-462e-b169-362dca6a4dd7";
    private static final String NTS_FUND_POOL_ID_2 = "76282dbc-2468-48d4-b926-93d3458a656b";
    private static final String NTS_FUND_POOL_ID_3 = "6fe5044d-15a3-47fe-913e-69f3bf353bef";
    private static final String AACL_FUND_POOL_ID = "ce9c1258-6d29-4224-a4e6-6f03b6aeef53";
    private static final String NAME_1 = "Q1 2019 100%";
    private static final String NAME_2 = "NTS Q2 2019";
    private static final String COMMENT_1 = "some comment";
    private static final String COMMENT_2 = "other comment";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final String USER_NAME = "user@copyright.com";

    @Autowired
    private FundPoolRepository fundPoolRepository;

    @Test
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
    public void testFindNtsNotAttachedToScenario() {
        List<FundPool> fundPools = fundPoolRepository.findNtsNotAttachedToScenario();
        assertEquals(1, fundPools.size());
        assertEquals(fundPools.get(0).getId(), "49060c9b-9cc2-4b93-b701-fffc82eb28b0");
    }

    @Test
    public void testFindNamesByUsageBatchId() {
        List<String> names =
            fundPoolRepository.findNamesByUsageBatchId("63b45167-a6ce-4cd5-84c6-5167916aee98");
        assertEquals(0, names.size());
        names =
            fundPoolRepository.findNamesByUsageBatchId("a163cca7-8eeb-449c-8a3c-29ff3ec82e58");
        assertEquals(1, names.size());
        assertEquals("Test fund", names.get(0));
        names =
            fundPoolRepository.findNamesByUsageBatchId("1a615c47-531a-4a27-a4f3-a5bd3d5a4b1c");
        assertEquals(1, names.size());
        assertEquals("Archived Pre-Service fee fund", names.get(0));
    }

    @Test
    public void testDelete() {
        assertNotNull(fundPoolRepository.findById(NTS_FUND_POOL_ID_1));
        fundPoolRepository.delete(NTS_FUND_POOL_ID_1);
        assertNull(fundPoolRepository.findById(NTS_FUND_POOL_ID_1));
    }

    @Test
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
    public void testFindDetailsByFundPoolId() throws IOException {
        List<FundPoolDetail> expectedDetails = loadExpectedFundPoolDetails("expected_aacl_fund_pool_details.json");
        List<FundPoolDetail> actualDetails = fundPoolRepository.findDetailsByFundPoolId(AACL_FUND_POOL_ID);
        assertEquals(expectedDetails.size(), actualDetails.size());
        IntStream.range(0, expectedDetails.size())
            .forEach(i -> assertFundPoolDetail(expectedDetails.get(i), actualDetails.get(i)));
    }

    @Test
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
        return mapper.readValue(content, new TypeReference<List<FundPoolDetail>>() {});
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
}
