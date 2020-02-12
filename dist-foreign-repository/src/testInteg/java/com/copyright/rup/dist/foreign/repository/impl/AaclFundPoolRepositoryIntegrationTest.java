package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.repository.api.IAaclFundPoolRepository;

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
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Integration test for {@link AaclFundPoolRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/02/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=aacl-fund-pool-repository-test-data-init.groovy"})
@Transactional
public class AaclFundPoolRepositoryIntegrationTest {

    private static final String USER_NAME = "user@copyright.com";
    private static final String FUND_POOL_ID = "ce9c1258-6d29-4224-a4e6-6f03b6aeef53";

    @Autowired
    private IAaclFundPoolRepository aaclFundPoolRepository;

    @Test
    public void testFindAggregateLicenseeClassIds() {
        Set<Integer> aggregateLicenseeClassIds = aaclFundPoolRepository.findAggregateLicenseeClassIds();
        assertEquals(36, aggregateLicenseeClassIds.size());
        aggregateLicenseeClassIds.forEach(aggregateLicenseeClassId -> assertTrue(aggregateLicenseeClassId > 0));
    }

    @Test
    public void testFindAll() throws IOException {
        List<FundPool> expectedFundPools = loadExpectedFundPools("expected_aacl_fund_pool.json");
        List<FundPool> actualFundPools = aaclFundPoolRepository.findAll();
        assertEquals(expectedFundPools.size(), actualFundPools.size());
        IntStream.range(0, expectedFundPools.size())
            .forEach(i -> verifyFundPool(expectedFundPools.get(i), actualFundPools.get(i)));
    }

    @Test
    public void testFindDetailsByFundPoolId() throws IOException {
        List<FundPoolDetail> expectedDetails = loadExpectedFundPoolDetails("expected_aacl_fund_pool_details.json");
        List<FundPoolDetail> actualDetails =
            aaclFundPoolRepository.findDetailsByFundPoolId(FUND_POOL_ID);
        assertEquals(expectedDetails.size(), actualDetails.size());
        IntStream.range(0, expectedDetails.size())
            .forEach(i -> verifyFundPoolDetail(expectedDetails.get(i), actualDetails.get(i)));
    }

    @Test
    public void testDeleteById() {
        List<FundPool> actualFundPools = aaclFundPoolRepository.findAll();
        assertEquals(3, actualFundPools.size());
        assertTrue(actualFundPools.stream().anyMatch(fundPool -> Objects.equals(FUND_POOL_ID, fundPool.getId())));
        aaclFundPoolRepository.deleteDetailsByFundPoolId(FUND_POOL_ID);
        aaclFundPoolRepository.deleteById(FUND_POOL_ID);
        actualFundPools = aaclFundPoolRepository.findAll();
        assertEquals(2, actualFundPools.size());
        assertFalse(actualFundPools.stream().anyMatch(fundPool -> Objects.equals(FUND_POOL_ID, fundPool.getId())));
    }

    @Test
    public void testDeleteDetailsByFundPoolId() {
        assertEquals(2, aaclFundPoolRepository.findDetailsByFundPoolId(FUND_POOL_ID).size());
        aaclFundPoolRepository.deleteDetailsByFundPoolId(FUND_POOL_ID);
        assertEquals(0, aaclFundPoolRepository.findDetailsByFundPoolId(FUND_POOL_ID).size());
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
        aaclFundPoolRepository.insertDetail(detail);
        List<FundPoolDetail> details = aaclFundPoolRepository.findDetailsByFundPoolId(fundPoolId);
        assertEquals(1, details.size());
        FundPoolDetail actualDetail = details.get(0);
        assertEquals(detail.getId(), actualDetail.getId());
        assertEquals(detail.getAggregateLicenseeClass().getId(), actualDetail.getAggregateLicenseeClass().getId());
        assertEquals(new BigDecimal("1.00"), actualDetail.getGrossAmount());
        assertEquals(USER_NAME, actualDetail.getCreateUser());
        assertEquals(USER_NAME, actualDetail.getUpdateUser());
    }

    private List<FundPool> loadExpectedFundPools(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), "json/aacl/" + fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<FundPool>>() {});
    }

    private List<FundPoolDetail> loadExpectedFundPoolDetails(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), "json/aacl/" + fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<FundPoolDetail>>() {});
    }

    private void verifyFundPool(FundPool expected, FundPool actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getTotalAmount(), actual.getTotalAmount());
        assertEquals(expected.getCreateDate(), actual.getCreateDate());
        assertEquals(expected.getCreateUser(), actual.getCreateUser());
    }

    private void verifyFundPoolDetail(FundPoolDetail expected, FundPoolDetail actual) {
        assertEquals(expected.getId(), actual.getId());
        assertNotNull(actual.getAggregateLicenseeClass());
        assertEquals(expected.getAggregateLicenseeClass().getId(), actual.getAggregateLicenseeClass().getId());
        assertEquals(expected.getAggregateLicenseeClass().getName(), actual.getAggregateLicenseeClass().getName());
        assertEquals(expected.getGrossAmount(), actual.getGrossAmount());
    }
}
