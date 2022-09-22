package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

/**
 * Verifies consuming LDMT details from Oracle and storing ACL fund pool details to database.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/12/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class ReceiveLdmtDetailsIntegrationTest {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private ServiceTestHelper testHelper;

    @Before
    public void reset() {
        testHelper.reset();
    }

    @Test
    public void testReceiveLdmtDetailsFromOracle() throws InterruptedException, IOException {
        assertTrue(findAllAclFundPoolDetails().isEmpty());
        testHelper.receiveLdtmDetailsFromOracle("oracle/ldmt_details.json");
        testHelper.assertAclFundPoolDetails(
            testHelper.loadExpectedAclFundPoolDetails("acl/fundpool/acl_fund_pool_details.json"),
            findAllAclFundPoolDetails());
    }

    private List<AclFundPoolDetail> findAllAclFundPoolDetails() {
        return sqlSessionTemplate.selectList("IAclFundPoolMapper.findAllDetails");
    }
}
