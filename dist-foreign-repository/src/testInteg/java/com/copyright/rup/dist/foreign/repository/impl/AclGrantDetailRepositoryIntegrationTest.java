package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
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
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.IntStream;

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
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclGrantDetailRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "acl-grant-detail-repository-integration-test/";
    private static final String FIND_DTOS_BY_FILTER = FOLDER_NAME + "find-dtos-by-filter.groovy";
    private static final String ACL_GRANT_SET_UID = "384a380e-c6ef-4af2-a282-96f4b1570fdd";
    private static final String ACL_GRANT_SET_NAME = "ACL Grant Set 2021";
    private static final String ACL_GRANT_DETAIL_UID_1 = "8676271a-9298-4e8b-ad46-3a864f6c655c";
    private static final String ACL_GRANT_DETAIL_UID_2 = "dfa139d5-a821-4744-bf89-affeb5411395";
    private static final String ACL_GRANT_DETAIL_UID_3 = "bc696696-e46f-4fbd-85c7-4c509b370deb";
    private static final String ACL_GRANT_DETAIL_UID_4 = "ce59e8f3-de93-4fef-9cfb-47eb8c0175cc";
    private static final String GRANT_STATUS = "GRANT";
    private static final String DIGITAL_TOU = "DIGITAL";
    private static final String PRINT_TOU = "PRINT";
    private static final String TYPE_OF_USE_STATUS = "Digital Only";
    private static final Long WR_WRK_INST = 122820638L;
    private static final String SYSTEM_TITLE = "Wall Street journal";
    private static final Long RH_ACCOUNT_NUMBER = 1000028511L;
    private static final String RH_NAME = "Greenleaf Publishing";
    private static final String RH_NAME_DIFFERENT_CASE = "GrEeNlEaF PuBlIshInG";
    private static final String RH_NAME_FRAGMENT = "Greenleaf Pu";
    private static final String RH_NAME_WITH_METASYMBOLS = "Alexander Stille !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final Boolean ELIGIBLE = true;
    private static final String ACL_LICENSE_TYPE = "ACL";
    private static final String USER_NAME = "user@copyright.com";
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
        List<AclGrantDetail> actualGrantDetails = aclGrantDetailRepository.findByIds(List.of(ACL_GRANT_DETAIL_UID_1));
        assertEquals(1, actualGrantDetails.size());
        AclGrantDetail actualGrantDetail = actualGrantDetails.get(0);
        assertNotNull(actualGrantDetail);
        verifyAclGrantDetail(grantDetail, actualGrantDetail, false);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByAllFilter() {
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        filter.setGrantSetNames(Set.of(ACL_GRANT_SET_NAME));
        filter.setGrantSetPeriod(202106);
        filter.setGrantStatuses(Set.of(GRANT_STATUS));
        filter.setLicenseTypes(Set.of("ACL"));
        filter.setTypeOfUses(Set.of(PRINT_TOU));
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null));
        filter.setRhAccountNumberExpression(new FilterExpression<>(FilterOperatorEnum.CONTAINS, 28511, null));
        filter.setRhNameExpression(new FilterExpression<>(FilterOperatorEnum.CONTAINS, "Greenleaf", null));
        filter.setEditableExpression(new FilterExpression<>(FilterOperatorEnum.Y));
        filter.setEligibleExpression(new FilterExpression<>(FilterOperatorEnum.Y));
        assertEquals(1, aclGrantDetailRepository.findCountByFilter(filter));
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByBasicFilters() {
        assertFilteringFindCountByFilter(filter -> filter.setGrantSetNames(Set.of(ACL_GRANT_SET_NAME)), 1);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByAdditionalFilter() {
        assertFilteringFindCountByFilter(filter -> filter.setLicenseTypes(Set.of(ACL_LICENSE_TYPE)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setGrantStatuses(Set.of(GRANT_STATUS)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setTypeOfUses(Set.of(PRINT_TOU)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setGrantSetPeriod(202206), 1);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterWrWrkInst() {
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, WR_WRK_INST, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 122820628L, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 122820628L, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, WR_WRK_INST, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, WR_WRK_INST, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 122820625L, 122820640L)), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterRhAccountNumber() {
        assertFilteringFindCountByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_ACCOUNT_NUMBER, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_ACCOUNT_NUMBER, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, RH_ACCOUNT_NUMBER, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, RH_ACCOUNT_NUMBER, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, RH_ACCOUNT_NUMBER, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, RH_ACCOUNT_NUMBER, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 1000010077L, RH_ACCOUNT_NUMBER)), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterRhName() {
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME_DIFFERENT_CASE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME_FRAGMENT, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_NAME, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_NAME_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_NAME_FRAGMENT, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_NAME_WITH_METASYMBOLS, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_DIFFERENT_CASE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_FRAGMENT, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_WITH_METASYMBOLS, null)), 1);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterEligible() {
        assertFilteringFindCountByFilter(filter -> filter.setEligibleExpression(
            new FilterExpression<>(FilterOperatorEnum.Y)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setEligibleExpression(
            new FilterExpression<>(FilterOperatorEnum.N)), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterEditable() {
        assertFilteringFindCountByFilter(filter -> filter.setEditableExpression(
            new FilterExpression<>(FilterOperatorEnum.Y)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setEditableExpression(
            new FilterExpression<>(FilterOperatorEnum.N)), 1);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByAllFilters() {
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        filter.setGrantSetNames(Set.of(ACL_GRANT_SET_NAME));
        filter.setGrantSetPeriod(202106);
        filter.setGrantStatuses(Set.of(GRANT_STATUS));
        filter.setLicenseTypes(Set.of(ACL_LICENSE_TYPE));
        filter.setTypeOfUses(Set.of(PRINT_TOU));
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null));
        filter.setRhAccountNumberExpression(new FilterExpression<>(FilterOperatorEnum.CONTAINS, 28511, null));
        filter.setRhNameExpression(new FilterExpression<>(FilterOperatorEnum.CONTAINS, "Greenleaf", null));
        filter.setEditableExpression(new FilterExpression<>(FilterOperatorEnum.Y));
        List<AclGrantDetailDto> values = aclGrantDetailRepository.findDtosByFilter(filter, null, buildSort());
        assertEquals(1, values.size());
        verifyAclGrantDetailDto(loadExpectedDtos("json/acl/acl_grant_detail_dto.json").get(0), values.get(0), true);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByBasicFilters() {
        assertFilteringFindDtosByFilter(filter -> filter.setGrantSetNames(Set.of(ACL_GRANT_SET_NAME)),
            ACL_GRANT_DETAIL_UID_3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByAdditionalFilter() {
        assertFilteringFindDtosByFilter(filter -> filter.setLicenseTypes(Set.of(ACL_LICENSE_TYPE)),
            ACL_GRANT_DETAIL_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setGrantStatuses(Set.of(GRANT_STATUS)),
            ACL_GRANT_DETAIL_UID_4, ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setTypeOfUses(Set.of(PRINT_TOU)),
            ACL_GRANT_DETAIL_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setGrantSetPeriod(202206), ACL_GRANT_DETAIL_UID_2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterWrWrkInst() {
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null)),
            ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, WR_WRK_INST, null)),
            ACL_GRANT_DETAIL_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 122820628L, null)),
            ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 122820628L, null)),
            ACL_GRANT_DETAIL_UID_4, ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, WR_WRK_INST, null)),
            ACL_GRANT_DETAIL_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, WR_WRK_INST, null)),
            ACL_GRANT_DETAIL_UID_4, ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 122820625L, 122820640L)),
            ACL_GRANT_DETAIL_UID_4, ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterRhAccountNumber() {
        assertFilteringFindDtosByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_ACCOUNT_NUMBER, null)),
            ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_ACCOUNT_NUMBER, null)),
            ACL_GRANT_DETAIL_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, RH_ACCOUNT_NUMBER, null)),
            ACL_GRANT_DETAIL_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, RH_ACCOUNT_NUMBER, null)),
            ACL_GRANT_DETAIL_UID_4, ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, RH_ACCOUNT_NUMBER, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, RH_ACCOUNT_NUMBER, null)),
            ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 1000010077L, RH_ACCOUNT_NUMBER)),
            ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterRhName() {
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME, null)),
            ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME_DIFFERENT_CASE, null)),
            ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME_WITH_METASYMBOLS, null)), ACL_GRANT_DETAIL_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_NAME, null)), ACL_GRANT_DETAIL_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_NAME_DIFFERENT_CASE, null)),
            ACL_GRANT_DETAIL_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_NAME_FRAGMENT, null)),
            ACL_GRANT_DETAIL_UID_4, ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_NAME_WITH_METASYMBOLS, null)),
            ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME, null)),
            ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_DIFFERENT_CASE, null)),
            ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_FRAGMENT, null)),
            ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_WITH_METASYMBOLS, null)),
            ACL_GRANT_DETAIL_UID_4);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterEligible() {
        assertFilteringFindDtosByFilter(filter -> filter.setEligibleExpression(
            new FilterExpression<>(FilterOperatorEnum.Y)), ACL_GRANT_DETAIL_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setEligibleExpression(
            new FilterExpression<>(FilterOperatorEnum.N)), ACL_GRANT_DETAIL_UID_4, ACL_GRANT_DETAIL_UID_2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterEditable() {
        assertFilteringFindDtosByFilter(filter -> filter.setEditableExpression(
            new FilterExpression<>(FilterOperatorEnum.Y)), ACL_GRANT_DETAIL_UID_4, ACL_GRANT_DETAIL_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setEditableExpression(
            new FilterExpression<>(FilterOperatorEnum.N)), ACL_GRANT_DETAIL_UID_2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testSortingFindDtosByFilter() {
        assertSortingFindDtosByFilter(ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_2, "licenseType");
        assertSortingFindDtosByFilter(ACL_GRANT_DETAIL_UID_2, ACL_GRANT_DETAIL_UID_3, "typeOfUseStatus");
        assertSortingFindDtosByFilter(ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_3, "grantStatus");
        assertSortingFindDtosByFilter(ACL_GRANT_DETAIL_UID_4, ACL_GRANT_DETAIL_UID_3, "eligible");
        assertSortingFindDtosByFilter(ACL_GRANT_DETAIL_UID_4, ACL_GRANT_DETAIL_UID_3, "wrWrkInst");
        assertSortingFindDtosByFilter(ACL_GRANT_DETAIL_UID_4, ACL_GRANT_DETAIL_UID_3, "systemTitle");
        assertSortingFindDtosByFilter(ACL_GRANT_DETAIL_UID_3, ACL_GRANT_DETAIL_UID_4, "rhAccountNumber");
        assertSortingFindDtosByFilter(ACL_GRANT_DETAIL_UID_4, ACL_GRANT_DETAIL_UID_3, "rhName");
        assertSortingFindDtosByFilter(ACL_GRANT_DETAIL_UID_4, ACL_GRANT_DETAIL_UID_3, "typeOfUse");
        assertSortingFindDtosByFilter(ACL_GRANT_DETAIL_UID_4, ACL_GRANT_DETAIL_UID_2, "grantPeriod");
        assertSortingFindDtosByFilter(ACL_GRANT_DETAIL_UID_4, ACL_GRANT_DETAIL_UID_3, "createDate");
        assertSortingFindDtosByFilter(ACL_GRANT_DETAIL_UID_4, ACL_GRANT_DETAIL_UID_3, "updateDate");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-grant-details.groovy")
    public void testUpdateGrantsDetails() {
        AclGrantDetailDto expectedGrantDetailDto = loadExpectedDtos("json/acl/acl_grant_detail_for_update.json").get(0);
        aclGrantDetailRepository.updateGrant(expectedGrantDetailDto);
        AclGrantDetailFilter aclGrantDetailFilter = new AclGrantDetailFilter();
        aclGrantDetailFilter.setGrantSetNames(Set.of("ACL Grant Set 2022"));
        aclGrantDetailFilter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null));
        aclGrantDetailFilter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 1000019896L, null));
        AclGrantDetailDto actualGrantDetailDto =
            aclGrantDetailRepository.findDtosByFilter(aclGrantDetailFilter, null, null).get(0);
        verifyAclGrantDetailDto(expectedGrantDetailDto, actualGrantDetailDto, false);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-grant-details.groovy")
    public void testFindPairForGrant() {
        AclGrantDetailDto expectedGrantDetail = loadExpectedDtos("json/acl/acl_pair_grant_detail_dto.json").get(0);
        AclGrantDetailDto actualGrantDetail =
            aclGrantDetailRepository.findPairForGrantById("883c61f6-3e78-418a-ba97-2890a2ffa46b");
        assertEquals("882f4daf-87ea-4ae7-b63e-740e760e5973", actualGrantDetail.getId());
        assertEquals(expectedGrantDetail.getTypeOfUseStatus(), actualGrantDetail.getTypeOfUseStatus());
        assertEquals(expectedGrantDetail.getGrantStatus(), actualGrantDetail.getGrantStatus());
        assertEquals(expectedGrantDetail.getEligible(), actualGrantDetail.getEligible());
        assertEquals(expectedGrantDetail.getWrWrkInst(), actualGrantDetail.getWrWrkInst());
        assertEquals(expectedGrantDetail.getRhAccountNumber(), actualGrantDetail.getRhAccountNumber());
        assertEquals(expectedGrantDetail.getTypeOfUse(), actualGrantDetail.getTypeOfUse());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-by-grant-set-id.groovy")
    public void testDeleteByGrantSetId() {
        List<String> grantDetailIds = List.of("a532c96d-895a-40ba-b123-5d775ff67397");
        assertEquals(1, aclGrantDetailRepository.findByIds(grantDetailIds).size());
        aclGrantDetailRepository.deleteByGrantSetId("0d651beb-e536-4854-aed7-50f068c369ba");
        assertEquals(0, aclGrantDetailRepository.findByIds(grantDetailIds).size());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "is-grant-detail-exist.groovy")
    public void isGrantDetailExist() {
        String fundPoolId = "d8b01f8f-b8e0-4127-9997-4ffe79f201b2";
        assertTrue(aclGrantDetailRepository.isGrantDetailExist(fundPoolId, 122820638L, DIGITAL_TOU));
        assertFalse(aclGrantDetailRepository.isGrantDetailExist(fundPoolId, 122820638L, PRINT_TOU));
        assertTrue(aclGrantDetailRepository.isGrantDetailExist(fundPoolId, 269040891L, PRINT_TOU));
        assertFalse(
            aclGrantDetailRepository.isGrantDetailExist("0d651beb-e536-4854-aed7-50f068c369ba", 269040891L, PRINT_TOU));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "copy-grant-details-by-grant-set-id.groovy")
    public void testCopyGrantDetailsByGrantSetId() {
        String sourceGrantSetId = "ba787989-32a5-425c-b0b8-03392554bf2d";
        String targetGrantSetId = "ce1462d6-c573-4f95-94e5-c295fa6e1525";
        String userName = "user@copyright.com";
        List<String> copiedGrantDetailsIds =
            aclGrantDetailRepository.copyGrantDetailsByGrantSetId(sourceGrantSetId, targetGrantSetId, userName);
        assertEquals(1, copiedGrantDetailsIds.size());
        List<AclGrantDetail> actualGrantDetails = aclGrantDetailRepository.findByIds(copiedGrantDetailsIds);
        assertEquals(1, actualGrantDetails.size());
        List<AclGrantDetail> expectedGrantDetails = loadExpectedDetails("json/acl/acl_grant_details_for_copy.json");
        expectedGrantDetails.get(0).setId(copiedGrantDetailsIds.get(0));
        expectedGrantDetails.get(0).setGrantSetId(targetGrantSetId);
        verifyAclGrantDetail(expectedGrantDetails.get(0), actualGrantDetails.get(0), true);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-payee-account-number.groovy")
    public void testUpdatePayeeAccountNumber() {
        String grantSetId = "ac16e4fd-ab85-4f6e-9ffe-f87c3b7f0467";
        List<String> grantDetailIds = List.of("018c6593-7eb4-4dc7-bc5e-f9a4b5fd7df8");
        List<AclGrantDetail> actualGrantDetails = aclGrantDetailRepository.findByIds(grantDetailIds);
        assertEquals(1, actualGrantDetails.size());
        AclGrantDetail actualGrantDetail = actualGrantDetails.get(0);
        assertNull(actualGrantDetail.getPayeeAccountNumber());
        aclGrantDetailRepository.updatePayeeAccountNumber(grantSetId, 1000028511L, "DIGITAL", 1000019896L, USER_NAME);
        actualGrantDetails = aclGrantDetailRepository.findByIds(grantDetailIds);
        assertEquals(1, actualGrantDetails.size());
        actualGrantDetail = actualGrantDetails.get(0);
        assertEquals(1000019896L, actualGrantDetail.getPayeeAccountNumber(), 0);
        assertEquals(USER_NAME, actualGrantDetail.getUpdateUser());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-payee-account-number.groovy")
    public void testUpdatePayeeAccountNumberById() {
        String grantDetailId = "018c6593-7eb4-4dc7-bc5e-f9a4b5fd7df8";
        List<AclGrantDetail> actualGrantDetails = aclGrantDetailRepository.findByIds(List.of(grantDetailId));
        assertEquals(1, actualGrantDetails.size());
        AclGrantDetail actualGrantDetail = actualGrantDetails.get(0);
        assertNull(actualGrantDetail.getPayeeAccountNumber());
        aclGrantDetailRepository.updatePayeeAccountNumberById(grantDetailId, 1000019896L, USER_NAME);
        actualGrantDetails = aclGrantDetailRepository.findByIds(List.of(grantDetailId));
        assertEquals(1, actualGrantDetails.size());
        actualGrantDetail = actualGrantDetails.get(0);
        assertEquals(1000019896L, actualGrantDetail.getPayeeAccountNumber(), 0);
        assertEquals(USER_NAME, actualGrantDetail.getUpdateUser());
    }

    private void verifyAclGrantDetail(AclGrantDetail expectedGrantDetail, AclGrantDetail actualGrantDetail,
                                      boolean isValidateAdditionalFields) {
        assertEquals(expectedGrantDetail.getId(), actualGrantDetail.getId());
        assertEquals(expectedGrantDetail.getGrantSetId(), actualGrantDetail.getGrantSetId());
        assertEquals(expectedGrantDetail.getGrantStatus(), actualGrantDetail.getGrantStatus());
        assertEquals(expectedGrantDetail.getTypeOfUse(), actualGrantDetail.getTypeOfUse());
        assertEquals(expectedGrantDetail.getTypeOfUseStatus(), actualGrantDetail.getTypeOfUseStatus());
        assertEquals(expectedGrantDetail.getWrWrkInst(), actualGrantDetail.getWrWrkInst());
        assertEquals(expectedGrantDetail.getSystemTitle(), actualGrantDetail.getSystemTitle());
        assertEquals(expectedGrantDetail.getRhAccountNumber(), actualGrantDetail.getRhAccountNumber());
        assertEquals(expectedGrantDetail.getEligible(), actualGrantDetail.getEligible());
        assertEquals(expectedGrantDetail.getPayeeAccountNumber(), actualGrantDetail.getPayeeAccountNumber());
        if (isValidateAdditionalFields) {
            assertEquals(expectedGrantDetail.getVersion(), actualGrantDetail.getVersion());
            assertEquals(expectedGrantDetail.getCreateUser(), actualGrantDetail.getCreateUser());
            assertEquals(expectedGrantDetail.getUpdateUser(), actualGrantDetail.getUpdateUser());
            assertNotNull(actualGrantDetail.getCreateDate());
            assertNotNull(actualGrantDetail.getUpdateDate());
        }
        assertEquals(expectedGrantDetail, actualGrantDetail);
    }

    private void assertFilteringFindCountByFilter(Consumer<AclGrantDetailFilter> consumer, int count) {
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        consumer.accept(filter);
        int usagesCount = aclGrantDetailRepository.findCountByFilter(filter);
        assertEquals(count, usagesCount);
    }

    private void verifyAclGrantDetailDto(AclGrantDetailDto expectedGrantDetail, AclGrantDetailDto actualGrantDetail,
                                         boolean isValidateDate) {
        assertEquals(expectedGrantDetail.getLicenseType(), actualGrantDetail.getLicenseType());
        assertEquals(expectedGrantDetail.getTypeOfUseStatus(), actualGrantDetail.getTypeOfUseStatus());
        assertEquals(expectedGrantDetail.getGrantSetName(), actualGrantDetail.getGrantSetName());
        assertEquals(expectedGrantDetail.getGrantStatus(), actualGrantDetail.getGrantStatus());
        assertEquals(expectedGrantDetail.getEligible(), actualGrantDetail.getEligible());
        assertEquals(expectedGrantDetail.getWrWrkInst(), actualGrantDetail.getWrWrkInst());
        assertEquals(expectedGrantDetail.getSystemTitle(), actualGrantDetail.getSystemTitle());
        assertEquals(expectedGrantDetail.getRhAccountNumber(), actualGrantDetail.getRhAccountNumber());
        assertEquals(expectedGrantDetail.getRhName(), actualGrantDetail.getRhName());
        assertEquals(expectedGrantDetail.getTypeOfUse(), actualGrantDetail.getTypeOfUse());
        assertEquals(expectedGrantDetail.getGrantPeriod(), actualGrantDetail.getGrantPeriod());
        assertEquals(expectedGrantDetail.getEditable(), actualGrantDetail.getEditable());
        assertEquals(expectedGrantDetail.getManualUploadFlag(), actualGrantDetail.getManualUploadFlag());
        assertEquals(expectedGrantDetail.getPayeeAccountNumber(), actualGrantDetail.getPayeeAccountNumber());
        assertEquals(expectedGrantDetail.getVersion(), actualGrantDetail.getVersion());
        assertEquals(expectedGrantDetail.getCreateUser(), actualGrantDetail.getCreateUser());
        assertEquals(expectedGrantDetail.getUpdateUser(), actualGrantDetail.getUpdateUser());
        assertEquals(expectedGrantDetail.getCreateDate(), actualGrantDetail.getCreateDate());
        if (isValidateDate) {
            assertEquals(expectedGrantDetail.getUpdateDate(), actualGrantDetail.getUpdateDate());
        }
    }

    private void assertFilteringFindDtosByFilter(Consumer<AclGrantDetailFilter> consumer, String... detailIds) {
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        consumer.accept(filter);
        List<AclGrantDetailDto> usages = aclGrantDetailRepository.findDtosByFilter(filter, null, buildSort());
        assertEquals(detailIds.length, usages.size());
        IntStream.range(0, detailIds.length)
            .forEach(index -> assertEquals(detailIds[index], usages.get(index).getId()));
    }

    private AclGrantDetail buildAclGrantDetail() {
        AclGrantDetail grantDetail = new AclGrantDetail();
        grantDetail.setId(ACL_GRANT_DETAIL_UID_1);
        grantDetail.setGrantSetId(ACL_GRANT_SET_UID);
        grantDetail.setGrantStatus(GRANT_STATUS);
        grantDetail.setTypeOfUse(DIGITAL_TOU);
        grantDetail.setTypeOfUseStatus(TYPE_OF_USE_STATUS);
        grantDetail.setWrWrkInst(122825347L);
        grantDetail.setSystemTitle(SYSTEM_TITLE);
        grantDetail.setRhAccountNumber(7000813806L);
        grantDetail.setEligible(ELIGIBLE);
        grantDetail.setManualUploadFlag(false);
        grantDetail.setPayeeAccountNumber(2000024750L);
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

    private List<AclGrantDetail> loadExpectedDetails(String fileName) {
        List<AclGrantDetail> grantDetails = new ArrayList<>();
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            grantDetails.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<AclGrantDetail>>() {
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
