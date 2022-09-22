package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Integration test for {@link AclFundPoolRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/21/2022
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclFundPoolRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "acl-fund-pool-repository-integration-test/";
    private static final String ACL_FUND_POOL_ID = "97efb29e-dac7-4dd9-9942-1508853b8625";
    private static final String ACL_FUND_POOL_NAME = "ACL Fund Pool 2021";
    private static final String USER_NAME = "user@copyright.com";
    private static final String LICENSE_TYPE = "ACL";
    private static final String ROLLBACK_ONLY = "rollback-only.groovy";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private AclFundPoolRepository repository;

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Test
    @TestData(fileName = ROLLBACK_ONLY)
    public void testInsert() {
        AclFundPool fundPool = buildAclFundPool();
        repository.insert(fundPool);
        AclFundPool actualFundPool = repository.findById(ACL_FUND_POOL_ID);
        assertNotNull(actualFundPool);
        assertEquals(fundPool.getId(), actualFundPool.getId());
        assertEquals(fundPool.getName(), actualFundPool.getName());
        assertEquals(fundPool.getPeriod(), actualFundPool.getPeriod());
        assertEquals(fundPool.getLicenseType(), actualFundPool.getLicenseType());
        assertEquals(fundPool.isManualUploadFlag(), actualFundPool.isManualUploadFlag());
        assertEquals(fundPool, actualFundPool);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "is-fund-pool-exist.groovy")
    public void testIsFundPoolExist() {
        assertTrue(repository.isFundPoolExists(ACL_FUND_POOL_NAME));
        assertFalse(repository.isFundPoolExists("ACL Fund Pool 2022"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "is-ldmt-detail-exist.groovy")
    public void testIsLdmtDetailExist() {
        assertTrue(repository.isLdmtDetailExist("ACL"));
        assertFalse(repository.isLdmtDetailExist("VGW"));
    }

    @Test
    @TestData(fileName = ROLLBACK_ONLY)
    public void testInsertDetails() {
        repository.insert(buildAclFundPool());
        AclFundPoolDetail fundPoolDetail = buildAclFundPoolDetail();
        repository.insertDetail(fundPoolDetail);
        List<AclFundPoolDetail> aclFundPoolDetails = repository.findDetailsByFundPoolId(ACL_FUND_POOL_ID);
        assertEquals(1, aclFundPoolDetails.size());
        AclFundPoolDetail aclFundPoolDetail = aclFundPoolDetails.get(0);
        assertNotNull(aclFundPoolDetail);
        assertEquals(fundPoolDetail.getId(), aclFundPoolDetail.getId());
        assertEquals(fundPoolDetail.getFundPoolId(), aclFundPoolDetail.getFundPoolId());
        assertEquals(fundPoolDetail.getDetailLicenseeClass().getId(),
            aclFundPoolDetail.getDetailLicenseeClass().getId());
        assertEquals(fundPoolDetail.getDetailLicenseeClass().getDescription(),
            aclFundPoolDetail.getDetailLicenseeClass().getDescription());
        assertEquals(fundPoolDetail.getGrossAmount(), aclFundPoolDetail.getGrossAmount());
        assertEquals(fundPoolDetail.getNetAmount(), aclFundPoolDetail.getNetAmount());
        assertEquals(fundPoolDetail.getLicenseType(), aclFundPoolDetail.getLicenseType());
        assertEquals(fundPoolDetail.getTypeOfUse(), aclFundPoolDetail.getTypeOfUse());
        assertEquals(fundPoolDetail.isLdmtFlag(), aclFundPoolDetail.isLdmtFlag());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "add-ldmt-details-to-fund-pool.groovy")
    public void testAddLdmtDetailsToFundPool() {
        String fundPoolId = "39edbdfd-d732-4a78-9ecd-6a756c9f5b93";
        assertEquals(1, repository.addLdmtDetailsToFundPool(fundPoolId, LICENSE_TYPE, USER_NAME));
        List<AclFundPoolDetail> fundPoolDetails = repository.findDetailsByFundPoolId(fundPoolId);
        assertEquals(1, fundPoolDetails.size());
        AclFundPoolDetail fundPoolDetail = fundPoolDetails.get(0);
        assertNotNull(fundPoolDetail);
        assertEquals("b2f01b15-2193-4d91-ae5b-0834452e4788", fundPoolDetail.getId());
        assertEquals(fundPoolId, fundPoolDetail.getFundPoolId());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-all.groovy")
    public void testFindAll() {
        AclFundPool aclFundPool1 = buildAclFundPool("2d46b574-c1f3-4322-8b45-e322574bf057",
            "ACL Fund Pool 202212", 202212, LICENSE_TYPE, true, new BigDecimal("2400.52"), new BigDecimal("2711.00"));
        AclFundPool aclFundPool2 = buildAclFundPool("037a9464-fec3-43a2-a71e-711c620100d7",
            "ACL Fund Pool 202206", 202206, "MACL", true, new BigDecimal("1000.00"), new BigDecimal("1180.24"));
        List<AclFundPool> fundPools = repository.findAll();
        assertEquals(2, fundPools.size());
        verifyAclFundPool(aclFundPool1, fundPools.get(1));
        verifyAclFundPool(aclFundPool2, fundPools.get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-filter.groovy")
    //TODO cover all filters criteria
    public void testFindDtosByFilter() {
        AclFundPoolDetailFilter filter = new AclFundPoolDetailFilter();
        filter.setLicenseType("ACL");
        filter.setAggregateLicenseeClasses(Collections.singleton(buildAggregateLicenseeClass(51)));
        List<AclFundPoolDetailDto> details = repository.findDtosByFilter(filter);
        assertEquals(1, details.size());
        verifyFundPoolDetail(
            loadExpectedDetails(Collections.singletonList("json/acl/acl_fund_pool_detail_find_by_filter.json")).get(0),
            details.get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-detail-dtos-by-fund-pool-id.groovy")
    public void testFindDetailDtosByFundPoolId() {
        List<AclFundPoolDetailDto> actualDetails =
            repository.findDetailDtosByFundPoolId("8c2e21f0-c1f8-4bd8-8676-968e98617480");
        assertEquals(3, actualDetails.size());
        List<AclFundPoolDetailDto> expectedDetails = loadExpectedDetails(
            Collections.singletonList("json/acl/acl_fund_pool_detail_find_by_grant_set_id.json"));
        IntStream.range(0, expectedDetails.size())
            .forEach(index -> verifyFundPoolDetail(expectedDetails.get(index), actualDetails.get(index)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-fund-pools-by-period-and-license-type.groovy")
    public void testFindFundPoolsByLicenseTypeAndPeriod() {
        AclFundPool expectedFundPool = buildAclFundPool("7c3b7ccf-593a-43a5-86d6-83471a073bdc",
            "ACL Fund Pool 202212", 202212, LICENSE_TYPE, true, new BigDecimal("0.00"), new BigDecimal("0.00"));
        List<AclFundPool> fundPools = repository.findFundPoolsByLicenseTypeAndPeriod(LICENSE_TYPE, 202212);
        assertEquals(1, fundPools.size());
        verifyAclFundPool(expectedFundPool, fundPools.get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-periods.groovy")
    public void testFindPeriods() {
        List<Integer> expectedPeriods = Arrays.asList(202212, 202206);
        List<Integer> actualPeriods = repository.findPeriods();
        assertFalse(actualPeriods.isEmpty());
        assertEquals(expectedPeriods, actualPeriods);
    }

    @Test
    @TestData(fileName = ROLLBACK_ONLY)
    public void testDeleteByFundPoolId() {
        repository.insert(buildAclFundPool());
        AclFundPoolDetail fundPoolDetail = buildAclFundPoolDetail();
        repository.insertDetail(fundPoolDetail);
        assertEquals(1, repository.findDetailsByFundPoolId(ACL_FUND_POOL_ID).size());
        repository.deleteDetailsByFundPoolId(ACL_FUND_POOL_ID);
        assertEquals(0, repository.findDetailsByFundPoolId(ACL_FUND_POOL_ID).size());
    }

    @Test
    @TestData(fileName = ROLLBACK_ONLY)
    public void testDeleteById() {
        AclFundPool fundPool = buildAclFundPool();
        repository.insert(fundPool);
        AclFundPoolDetail fundPoolDetail = buildAclFundPoolDetail();
        repository.insertDetail(fundPoolDetail);
        assertEquals(1, repository.findAll().size());
        repository.deleteById(fundPool.getId());
        assertEquals(0, repository.findAll().size());
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass(int id) {
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(id);
        return aggregateLicenseeClass;
    }

    private AclFundPoolDetail buildAclFundPoolDetail() {
        AclFundPoolDetail aclFundPoolDetail = new AclFundPoolDetail();
        aclFundPoolDetail.setId("438c06a2-f3d1-488e-9f32-d034bc5b2edc");
        aclFundPoolDetail.setFundPoolId(ACL_FUND_POOL_ID);
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(7);
        detailLicenseeClass.setDescription("Fuels");
        aclFundPoolDetail.setDetailLicenseeClass(detailLicenseeClass);
        aclFundPoolDetail.setTypeOfUse("PRINT");
        aclFundPoolDetail.setLicenseType(LICENSE_TYPE);
        aclFundPoolDetail.setNetAmount(new BigDecimal("32.00"));
        aclFundPoolDetail.setGrossAmount(new BigDecimal("50.00"));
        aclFundPoolDetail.setLdmtFlag(false);
        return aclFundPoolDetail;
    }

    private AclFundPool buildAclFundPool(String id, String name, Integer period, String licenseType,
                                         boolean manualUploadFlag, BigDecimal netAmount, BigDecimal totalAmount) {
        AclFundPool aclFundPool = new AclFundPool();
        aclFundPool.setId(id);
        aclFundPool.setName(name);
        aclFundPool.setPeriod(period);
        aclFundPool.setLicenseType(licenseType);
        aclFundPool.setManualUploadFlag(manualUploadFlag);
        aclFundPool.setTotalAmount(totalAmount);
        aclFundPool.setNetAmount(netAmount);
        return aclFundPool;
    }

    private AclFundPool buildAclFundPool() {
        AclFundPool aclFundPool = new AclFundPool();
        aclFundPool.setId(ACL_FUND_POOL_ID);
        aclFundPool.setName(ACL_FUND_POOL_NAME);
        aclFundPool.setPeriod(202106);
        aclFundPool.setLicenseType(LICENSE_TYPE);
        aclFundPool.setManualUploadFlag(true);
        return aclFundPool;
    }

    private void verifyAclFundPool(AclFundPool expectedFundPool, AclFundPool actualFundPool) {
        assertNotNull(actualFundPool);
        assertEquals(expectedFundPool.getId(), actualFundPool.getId());
        assertEquals(expectedFundPool.getName(), actualFundPool.getName());
        assertEquals(expectedFundPool.getPeriod(), actualFundPool.getPeriod());
        assertEquals(expectedFundPool.getLicenseType(), actualFundPool.getLicenseType());
        assertEquals(expectedFundPool.isManualUploadFlag(), actualFundPool.isManualUploadFlag());
        assertEquals(expectedFundPool.getNetAmount(), actualFundPool.getNetAmount());
        assertEquals(expectedFundPool.getTotalAmount(), actualFundPool.getTotalAmount());
        assertEquals(expectedFundPool, actualFundPool);
    }

    private void verifyFundPoolDetail(AclFundPoolDetailDto expectedDetail, AclFundPoolDetailDto actualDetail) {
        assertNotNull(actualDetail);
        assertEquals(expectedDetail.getId(), actualDetail.getId());
        assertEquals(expectedDetail.getFundPoolName(), actualDetail.getFundPoolName());
        assertEquals(expectedDetail.getFundPoolId(), actualDetail.getFundPoolId());
        assertEquals(expectedDetail.getPeriod(), actualDetail.getPeriod());
        assertEquals(expectedDetail.getLicenseType(), actualDetail.getLicenseType());
        assertEquals(expectedDetail.getTypeOfUse(), actualDetail.getTypeOfUse());
        assertEquals(expectedDetail.getNetAmount(), actualDetail.getNetAmount());
        assertEquals(expectedDetail.getGrossAmount(), actualDetail.getGrossAmount());
        assertEquals(expectedDetail.getAggregateLicenseeClass(), actualDetail.getAggregateLicenseeClass());
        assertEquals(expectedDetail.getDetailLicenseeClass(), actualDetail.getDetailLicenseeClass());
    }

    private List<AclFundPoolDetailDto> loadExpectedDetails(List<String> fileNames) {
        List<AclFundPoolDetailDto> details = new ArrayList<>();
        fileNames.forEach(fileName -> {
            try {
                String content = TestUtils.fileToString(this.getClass(), fileName);
                details.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<AclFundPoolDetailDto>>() {
                }));
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        });
        return details;
    }
}
