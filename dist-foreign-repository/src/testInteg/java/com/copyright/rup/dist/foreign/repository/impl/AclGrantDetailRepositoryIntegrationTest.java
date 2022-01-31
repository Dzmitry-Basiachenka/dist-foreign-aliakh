package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantDetailRepository;
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
import java.util.ArrayList;
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
    private static final String FIND_DTOS_BY_FILTER = FOLDER_NAME + "find-dtos-by-filter.groovy";    
    private static final String UDM_GRANT_SET_UID = "384a380e-c6ef-4af2-a282-96f4b1570fdd";
    private static final String ACL_GRANT_SET_NAME = "ACL Grant Set 2021";
    private static final String UDM_GRANT_DETAIL_UID_1 = "8676271a-9298-4e8b-ad46-3a864f6c655c";
    private static final String UDM_GRANT_DETAIL_UID_2 = "bc696696-e46f-4fbd-85c7-4c509b370deb";
    private static final String UDM_GRANT_DETAIL_UID_3 = "ce59e8f3-de93-4fef-9cfb-47eb8c0175cc";
    private static final String GRANT_STATUS = "Grant";
    private static final String TYPE_OF_USE = "Digital";
    private static final String TYPE_OF_USE_STATUS = "Digital Only";
    private static final Long WR_WRK_INST = 122825347L;
    private static final String SYSTEM_TITLE = "Wall Street journal";
    private static final Long RH_ACCOUNT_NUMBER = 7000813806L;
    private static final Boolean ELIGIBLE = true;
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
            aclGrantDetailRepository.findByIds(Collections.singletonList(UDM_GRANT_DETAIL_UID_1));
        assertEquals(1, actualGrantDetails.size());
        AclGrantDetail actualGrantDetail = actualGrantDetails.get(0);
        assertNotNull(actualGrantDetail);
        verifyAclGrantDetail(grantDetail, actualGrantDetail);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilter() {
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        filter.setGrantSetNames(Collections.singleton(ACL_GRANT_SET_NAME));
        assertEquals(1, aclGrantDetailRepository.findCountByFilter(filter));
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilter() {
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        filter.setGrantSetNames(Collections.singleton(ACL_GRANT_SET_NAME));
        List<AclGrantDetailDto> values = aclGrantDetailRepository.findDtosByFilter(filter, null, buildSort());
        assertEquals(1, values.size());
        verifyAclGrantDetailDto(loadExpectedDtos("json/acl/acl_grant_detail_dto.json").get(0), values.get(0));
    }
    
    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testSortingFindDtosByFilter() {
        assertSortingFindDtosByFilter(UDM_GRANT_DETAIL_UID_2, UDM_GRANT_DETAIL_UID_3, "licenseType");
        assertSortingFindDtosByFilter(UDM_GRANT_DETAIL_UID_2, UDM_GRANT_DETAIL_UID_3, "typeOfUseStatus");
        assertSortingFindDtosByFilter(UDM_GRANT_DETAIL_UID_3, UDM_GRANT_DETAIL_UID_2, "grantStatus");
        assertSortingFindDtosByFilter(UDM_GRANT_DETAIL_UID_3, UDM_GRANT_DETAIL_UID_2, "eligible");
        assertSortingFindDtosByFilter(UDM_GRANT_DETAIL_UID_3, UDM_GRANT_DETAIL_UID_2, "wrWrkInst");
        assertSortingFindDtosByFilter(UDM_GRANT_DETAIL_UID_3, UDM_GRANT_DETAIL_UID_2, "systemTitle");
        assertSortingFindDtosByFilter(UDM_GRANT_DETAIL_UID_2, UDM_GRANT_DETAIL_UID_3, "rhAccountNumber");
        assertSortingFindDtosByFilter(UDM_GRANT_DETAIL_UID_3, UDM_GRANT_DETAIL_UID_2, "rhName");
        assertSortingFindDtosByFilter(UDM_GRANT_DETAIL_UID_3, UDM_GRANT_DETAIL_UID_2, "typeOfUse");
        assertSortingFindDtosByFilter(UDM_GRANT_DETAIL_UID_3, UDM_GRANT_DETAIL_UID_2, "grantPeriod");
        assertSortingFindDtosByFilter(UDM_GRANT_DETAIL_UID_3, UDM_GRANT_DETAIL_UID_2, "createDate");
        assertSortingFindDtosByFilter(UDM_GRANT_DETAIL_UID_3, UDM_GRANT_DETAIL_UID_2, "updateDate");
    }
    
    private void verifyAclGrantDetail(AclGrantDetail expectedGrantDetail, AclGrantDetail actualGrantDetail) {
        assertEquals(expectedGrantDetail.getId(), actualGrantDetail.getId());
        assertEquals(expectedGrantDetail.getGrantSetId(), actualGrantDetail.getGrantSetId());
        assertEquals(expectedGrantDetail.getGrantStatus(), actualGrantDetail.getGrantStatus());
        assertEquals(expectedGrantDetail.getTypeOfUse(), actualGrantDetail.getTypeOfUse());
        assertEquals(expectedGrantDetail.getTypeOfUseStatus(), actualGrantDetail.getTypeOfUseStatus());
        assertEquals(expectedGrantDetail.getWrWrkInst(), actualGrantDetail.getWrWrkInst());
        assertEquals(expectedGrantDetail.getSystemTitle(), actualGrantDetail.getSystemTitle());
        assertEquals(expectedGrantDetail.getRhAccountNumber(), actualGrantDetail.getRhAccountNumber());
        assertEquals(expectedGrantDetail.getEligible(), actualGrantDetail.getEligible());
        assertEquals(expectedGrantDetail, actualGrantDetail);
    }
    
    private void verifyAclGrantDetailDto(AclGrantDetailDto expectedGrantDetail, AclGrantDetailDto actualGrantDetail) {
        assertEquals(expectedGrantDetail.getLicenseType(), actualGrantDetail.getLicenseType());
        assertEquals(expectedGrantDetail.getTypeOfUseStatus(), actualGrantDetail.getTypeOfUseStatus());
        assertEquals(expectedGrantDetail.getGrantStatus(), actualGrantDetail.getGrantStatus());
        assertEquals(expectedGrantDetail.getEligible(), actualGrantDetail.getEligible());
        assertEquals(expectedGrantDetail.getWrWrkInst(), actualGrantDetail.getWrWrkInst());
        assertEquals(expectedGrantDetail.getSystemTitle(), actualGrantDetail.getSystemTitle());
        assertEquals(expectedGrantDetail.getRhAccountNumber(), actualGrantDetail.getRhAccountNumber());
        assertEquals(expectedGrantDetail.getRhName(), actualGrantDetail.getRhName());
        assertEquals(expectedGrantDetail.getTypeOfUse(), actualGrantDetail.getTypeOfUse());
        assertEquals(expectedGrantDetail.getGrantPeriod(), actualGrantDetail.getGrantPeriod());
        assertEquals(expectedGrantDetail.getCreateDate(), actualGrantDetail.getCreateDate());
        assertEquals(expectedGrantDetail.getUpdateDate(), actualGrantDetail.getUpdateDate());
        assertEquals(expectedGrantDetail, actualGrantDetail);
    }

    private AclGrantDetail buildAclGrantDetail() {
        AclGrantDetail grantDetail = new AclGrantDetail();
        grantDetail.setId(UDM_GRANT_DETAIL_UID_1);
        grantDetail.setGrantSetId(UDM_GRANT_SET_UID);
        grantDetail.setGrantStatus(GRANT_STATUS);
        grantDetail.setTypeOfUse(TYPE_OF_USE);
        grantDetail.setTypeOfUseStatus(TYPE_OF_USE_STATUS);
        grantDetail.setWrWrkInst(WR_WRK_INST);
        grantDetail.setSystemTitle(SYSTEM_TITLE);
        grantDetail.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        grantDetail.setEligible(ELIGIBLE);
        return grantDetail;
    }

    private Sort buildSort() {
        return new Sort("updateDate", Sort.Direction.ASC);
    }
    
    private List<AclGrantDetailDto> loadExpectedDtos(String fileName) {
        List<AclGrantDetailDto> grantDetails = new ArrayList<>();
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            grantDetails.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<AclGrantDetailDto>>() {
            }));
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return grantDetails;
    }
    
    private void assertSortingFindDtosByFilter(String grandtDetailIdAsc, String grantDetailIdDesc,
                                               String sortProperty) {
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        List<AclGrantDetailDto> grantDetails =
            aclGrantDetailRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Sort.Direction.ASC));
        assertEquals(grandtDetailIdAsc, grantDetails.get(0).getId());
        grantDetails =
            aclGrantDetailRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Sort.Direction.DESC));
        assertEquals(grantDetailIdDesc, grantDetails.get(0).getId());
    }    
}
