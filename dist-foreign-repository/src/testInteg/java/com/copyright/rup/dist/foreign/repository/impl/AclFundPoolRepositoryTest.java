package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

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
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclFundPoolRepositoryTest {

    private static final String FOLDER_NAME = "acl-fund-pool-repository-integration-test/";
    private static final String ACL_FUND_POOL_ID = "97efb29e-dac7-4dd9-9942-1508853b8625";
    private static final String ACL_FUND_POOL_NAME = "ACL Fund Pool 2021";

    @Autowired
    private AclFundPoolRepository repository;

    @Test
    @TestData(fileName = "rollback-only.groovy")
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
    @TestData(fileName = "rollback-only.groovy")
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

    private AclFundPoolDetail buildAclFundPoolDetail() {
        AclFundPoolDetail aclFundPoolDetail = new AclFundPoolDetail();
        aclFundPoolDetail.setId("438c06a2-f3d1-488e-9f32-d034bc5b2edc");
        aclFundPoolDetail.setFundPoolId(ACL_FUND_POOL_ID);
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(7);
        detailLicenseeClass.setDescription("Fuels");
        aclFundPoolDetail.setDetailLicenseeClass(detailLicenseeClass);
        aclFundPoolDetail.setTypeOfUse("PRINT");
        aclFundPoolDetail.setLicenseType("ACL");
        aclFundPoolDetail.setNetAmount(new BigDecimal("32.00"));
        aclFundPoolDetail.setGrossAmount(new BigDecimal("50.00"));
        aclFundPoolDetail.setLdmtFlag(false);
        return aclFundPoolDetail;
    }

    private AclFundPool buildAclFundPool() {
        AclFundPool aclFundPool = new AclFundPool();
        aclFundPool.setId(ACL_FUND_POOL_ID);
        aclFundPool.setName(ACL_FUND_POOL_NAME);
        aclFundPool.setPeriod(202106);
        aclFundPool.setLicenseType("ACL");
        aclFundPool.setManualUploadFlag(true);
        return aclFundPool;
    }
}
