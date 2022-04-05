package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Sets;

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
import java.util.Set;

/**
 * Verifies {@link AclUsageRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
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
public class AclUsageRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "acl-usage-repository-integration-test/";
    private static final String FIND_DTOS_BY_FILTER = FOLDER_NAME + "find-dtos-by-filter.groovy";
    private static final String ACL_USAGE_BATCH_NAME = "ACL Usage Batch 2021";
    private static final String ACL_USAGE_UID_1 = "8ff48add-0eea-4fe3-81d0-3264c6779936";
    private static final String ACL_USAGE_UID_2 = "0eeef531-b779-4b3b-827d-b44b2261c6db";
    private static final String USER_NAME = "user@copyright.com";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Autowired
    private IAclUsageRepository aclUsageRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "populate-acl-usages.groovy")
    public void testPopulateAclUsages() {
        String usageBatchId = "1fa04580-af0a-4c57-8470-37ae9c06bea1";
        Set<Integer> periods = Sets.newHashSet(202106, 202112);
        List<String> usageIds = aclUsageRepository.populateAclUsages(usageBatchId, periods, USER_NAME);
        assertEquals(1, usageIds.size());
        List<AclUsageDto> actualUsages = aclUsageRepository.findByIds(Collections.singletonList(usageIds.get(0)));
        assertEquals(1, actualUsages.size());
        AclUsageDto expectedUsage = loadExpectedDtos("json/acl/acl_usage_dto.json").get(0);
        expectedUsage.setId(usageIds.get(0));
        verifyAclUsageDto(expectedUsage, actualUsages.get(0), false);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilter() {
        assertEquals(1, aclUsageRepository.findCountByFilter(buildAclUsageFilter()));
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilter() {
        List<AclUsageDto> values = aclUsageRepository.findDtosByFilter(buildAclUsageFilter(), null, buildSort());
        assertEquals(1, values.size());
        verifyAclUsageDto(loadExpectedDtos("json/acl/acl_usage_dto_0eeef531.json").get(0), values.get(0), false);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testSortingFindDtosByFilter() {
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_1, "detailId");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_1, "period");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_1, "usageOrigin");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_1, "channel");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_2, "usageDetailId");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_2, "wrWrkInst");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_2, "systemTitle");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_2, "detLcId");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_2, "detLcName");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_2, "aggLcId");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_2, "aggLcName");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_2, "surveyCountry");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_1, "publicationType");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_1, "contentUnitPrice");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_2, "typeOfUse");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_1, "annualizedCopies");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_2, "updateUser");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_1, "updateDate");
    }

    private void verifyAclUsageDto(AclUsageDto expectedUsage, AclUsageDto actualUsage, boolean isValidateDates) {
        assertEquals(expectedUsage.getId(), actualUsage.getId());
        assertEquals(expectedUsage.getUsageBatchId(), actualUsage.getUsageBatchId());
        assertEquals(expectedUsage.getUsageOrigin(), actualUsage.getUsageOrigin());
        assertEquals(expectedUsage.getChannel(), actualUsage.getChannel());
        assertEquals(expectedUsage.getPeriod(), actualUsage.getPeriod());
        assertEquals(expectedUsage.getOriginalDetailId(), actualUsage.getOriginalDetailId());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
        assertEquals(expectedUsage.getDetailLicenseeClassId(), actualUsage.getDetailLicenseeClassId());
        assertEquals(expectedUsage.getDetailLicenseeClassName(), actualUsage.getDetailLicenseeClassName());
        assertEquals(expectedUsage.getAggregateLicenseeClassId(), actualUsage.getAggregateLicenseeClassId());
        assertEquals(expectedUsage.getAggregateLicenseeClassName(), actualUsage.getAggregateLicenseeClassName());
        assertEquals(expectedUsage.getSurveyCountry(), actualUsage.getSurveyCountry());
        assertEquals(expectedUsage.getPubTypeName(), actualUsage.getPubTypeName());
        assertEquals(expectedUsage.getContentUnitPrice(), actualUsage.getContentUnitPrice());
        assertEquals(expectedUsage.getTypeOfUse(), actualUsage.getTypeOfUse());
        assertEquals(expectedUsage.getAnnualizedCopies(), actualUsage.getAnnualizedCopies());
        assertEquals(expectedUsage.getCreateUser(), actualUsage.getCreateUser());
        assertEquals(expectedUsage.getUpdateUser(), actualUsage.getUpdateUser());
        if (isValidateDates) {
            assertEquals(expectedUsage.getCreateDate(), actualUsage.getCreateDate());
            assertEquals(expectedUsage.getUpdateDate(), actualUsage.getUpdateDate());
            assertEquals(expectedUsage, actualUsage);
        }
    }

    private List<AclUsageDto> loadExpectedDtos(String fileName) {
        List<AclUsageDto> usages = new ArrayList<>();
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            usages.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<AclUsageDto>>() {
            }));
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return usages;
    }

    private AclUsageFilter buildAclUsageFilter() {
        AclUsageFilter filter = new AclUsageFilter();
        filter.setUsageBatchName(ACL_USAGE_BATCH_NAME);
        return filter;
    }

    private Sort buildSort() {
        return new Sort("updateDate", Sort.Direction.ASC);
    }

    private void assertSortingFindDtosByFilter(String grandtDetailIdAsc, String grantDetailIdDesc,
                                               String sortProperty) {
        AclUsageFilter filter = new AclUsageFilter();
        List<AclUsageDto> grantDetails =
            aclUsageRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Sort.Direction.ASC));
        assertEquals(grandtDetailIdAsc, grantDetails.get(0).getId());
        grantDetails =
            aclUsageRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Sort.Direction.DESC));
        assertEquals(grantDetailIdDesc, grantDetails.get(0).getId());
    }
}
