package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AaclFundPool;
import com.copyright.rup.dist.foreign.domain.AaclFundPoolDetail;
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
import java.util.List;
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

    @Autowired
    private IAaclFundPoolRepository aaclFundPoolRepository;

    @Test
    public void testAaclFundPoolExists() {
        assertTrue(aaclFundPoolRepository.aaclFundPoolExists("fund_pool_name 100%"));
        assertTrue(aaclFundPoolRepository.aaclFundPoolExists("FUND_POOL_NAME 100%"));
        assertFalse(aaclFundPoolRepository.aaclFundPoolExists("fund_pool_name"));
    }

    @Test
    public void testFindAggregateLicenseeClassIds() {
        Set<Integer> aggregateLicenseeClassIds = aaclFundPoolRepository.findAggregateLicenseeClassIds();
        assertEquals(36, aggregateLicenseeClassIds.size());
        aggregateLicenseeClassIds.forEach(aggregateLicenseeClassId -> assertTrue(aggregateLicenseeClassId > 0));
    }

    @Test
    public void testFindAll() throws IOException {
        List<AaclFundPool> expectedFundPools = loadExpectedFundPools("expected_aacl_fund_pool.json");
        List<AaclFundPool> actualFundPools = aaclFundPoolRepository.findAll();
        assertEquals(expectedFundPools.size(), actualFundPools.size());
        IntStream.range(0, expectedFundPools.size())
            .forEach(i -> verifyFundPool(expectedFundPools.get(i), actualFundPools.get(i)));
    }

    @Test
    public void testFindDetailsByFundPoolId() throws IOException {
        List<AaclFundPoolDetail> expectedDetails = loadExpectedFundPoolDetails("expected_aacl_fund_pool_details.json");
        List<AaclFundPoolDetail> actualDetails =
            aaclFundPoolRepository.findDetailsByFundPoolId("ce9c1258-6d29-4224-a4e6-6f03b6aeef53");
        assertEquals(expectedDetails.size(), actualDetails.size());
        IntStream.range(0, expectedDetails.size())
            .forEach(i -> verifyFundPoolDetail(expectedDetails.get(i), actualDetails.get(i)));
    }

    private List<AaclFundPool> loadExpectedFundPools(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), "json/aacl/" + fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<AaclFundPool>>() {});
    }

    private List<AaclFundPoolDetail> loadExpectedFundPoolDetails(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), "json/aacl/" + fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<AaclFundPoolDetail>>() {});
    }

    private void verifyFundPool(AaclFundPool expected, AaclFundPool actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getTotalGrossAmount(), actual.getTotalGrossAmount());
        assertEquals(expected.getCreateDate(), actual.getCreateDate());
        assertEquals(expected.getCreateUser(), actual.getCreateUser());
    }

    private void verifyFundPoolDetail(AaclFundPoolDetail expected, AaclFundPoolDetail actual) {
        assertEquals(expected.getId(), actual.getId());
        assertNotNull(actual.getAggregateLicenseeClass());
        assertEquals(expected.getAggregateLicenseeClass().getId(), actual.getAggregateLicenseeClass().getId());
        assertEquals(expected.getAggregateLicenseeClass().getName(), actual.getAggregateLicenseeClass().getName());
        assertEquals(expected.getGrossAmount(), actual.getGrossAmount());
    }
}
