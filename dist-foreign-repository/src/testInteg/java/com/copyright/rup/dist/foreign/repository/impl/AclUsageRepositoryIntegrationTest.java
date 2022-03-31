package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
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

    private void verifyAclUsageDto(AclUsageDto expectedUsage, AclUsageDto actualUsage, boolean isValidateDates) {
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
}
