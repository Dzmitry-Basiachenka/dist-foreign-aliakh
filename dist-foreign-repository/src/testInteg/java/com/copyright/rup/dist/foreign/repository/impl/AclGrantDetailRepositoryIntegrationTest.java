package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantDetailRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclGrantDetailRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/27/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclGrantDetailRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "acl-grant-detail-repository-integration-test/";
    private static final String UDM_GRANT_DETAIL_UID = "8676271a-9298-4e8b-ad46-3a864f6c655c";
    private static final String UDM_GRANT_SET_UID = "384a380e-c6ef-4af2-a282-96f4b1570fdd";
    private static final String TYPE_OF_USE = "Digital";
    private static final String TYPE_OF_USE_STATUS = "Digital Only";
    private static final Long WR_WRK_INST = 122825347L;
    private static final String SYSTEM_TITLE = "Wall Street journal";
    private static final Long RH_ACCOUNT_NUMBER = 7000813806L;
    private static final Boolean ELIGIBLE = true;
    private static final String COMMENT = "comment";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Autowired
    private IAclGrantDetailRepository aclGrantDetailRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "insert.groovy")
    public void testInsert() {
        AclGrantDetail grantDetail = buildAclGrantDetail();
        aclGrantDetailRepository.insert(grantDetail);
        List<AclGrantDetail> actualGrantDetails =
            aclGrantDetailRepository.findByIds(Collections.singletonList(UDM_GRANT_DETAIL_UID));
        assertEquals(1, actualGrantDetails.size());
        AclGrantDetail actualGrantDetail = actualGrantDetails.get(0);
        assertNotNull(actualGrantDetail);
        verifyAclGrantDetail(grantDetail, actualGrantDetail);
    }

    private void verifyAclGrantDetail(AclGrantDetail expectedGrantDetail, AclGrantDetail actualGrantDetail) {
        assertEquals(expectedGrantDetail.getId(), actualGrantDetail.getId());
        assertEquals(expectedGrantDetail.getGrantSetId(), actualGrantDetail.getGrantSetId());
        assertEquals(expectedGrantDetail.getTypeOfUse(), actualGrantDetail.getTypeOfUse());
        assertEquals(expectedGrantDetail.getTypeOfUseStatus(), actualGrantDetail.getTypeOfUseStatus());
        assertEquals(expectedGrantDetail.getWrWrkInst(), actualGrantDetail.getWrWrkInst());
        assertEquals(expectedGrantDetail.getSystemTitle(), actualGrantDetail.getSystemTitle());
        assertEquals(expectedGrantDetail.getRhAccountNumber(), actualGrantDetail.getRhAccountNumber());
        assertEquals(expectedGrantDetail.getEligible(), actualGrantDetail.getEligible());
        assertEquals(expectedGrantDetail.getComment(), actualGrantDetail.getComment());
        assertEquals(expectedGrantDetail, actualGrantDetail);
    }

    private AclGrantDetail buildAclGrantDetail() {
        AclGrantDetail grantDetail = new AclGrantDetail();
        grantDetail.setId(UDM_GRANT_DETAIL_UID);
        grantDetail.setGrantSetId(UDM_GRANT_SET_UID);
        grantDetail.setTypeOfUse(TYPE_OF_USE);
        grantDetail.setTypeOfUseStatus(TYPE_OF_USE_STATUS);
        grantDetail.setWrWrkInst(WR_WRK_INST);
        grantDetail.setSystemTitle(SYSTEM_TITLE);
        grantDetail.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        grantDetail.setEligible(ELIGIBLE);
        grantDetail.setComment(COMMENT);
        return grantDetail;
    }
}
