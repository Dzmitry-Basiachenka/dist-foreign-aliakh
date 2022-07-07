package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetail;
import com.copyright.rup.dist.foreign.domain.AclScenarioShareDetail;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.IntStream;

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
    private static final String FIND_PERIODS = FOLDER_NAME + "find-periods.groovy";
    private static final String FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID =
        FOLDER_NAME + "find-acl-rh-totals-holders-by-scenario-id.groovy";
    private static final String ACL_USAGE_BATCH_NAME = "ACL Usage Batch 2021";
    private static final String ACL_USAGE_BATCH_UID_1 = "36d8901e-1f3b-4e68-8c95-1f8b02740ed2";
    private static final String ACL_USAGE_BATCH_UID_2 = "c653d257-4180-4fb4-9119-f001063e4a56";
    private static final String ACL_USAGE_UID_1 = "8ff48add-0eea-4fe3-81d0-3264c6779936";
    private static final String ACL_USAGE_UID_2 = "0eeef531-b779-4b3b-827d-b44b2261c6db";
    private static final String ACL_USAGE_UID_3 = "2ba0fab7-746d-41e0-87b5-c2b3997ce0ae";
    private static final String ACL_USAGE_UID_4 = "dfc0f9f4-2c50-4e1f-ad1a-a29fc2f9f4cd";
    private static final String ACL_USAGE_UID_5 = "1e9ab4dd-8526-4309-9c54-20226c48cd27";
    private static final UdmUsageOriginEnum USAGE_ORIGIN = UdmUsageOriginEnum.RFA;
    private static final UdmChannelEnum CHANNEL = UdmChannelEnum.CCC;
    private static final Set<Integer> PERIODS = Collections.singleton(202112);
    private static final Set<DetailLicenseeClass> DETAIL_LICENSEE_CLASSES =
        Collections.singleton(buildDetailLicenseeClass(2, "Textiles, Apparel, etc."));
    private static final Set<AggregateLicenseeClass> AGGREGATE_LICENSEE_CLASSES =
        Collections.singleton(buildAggregateLicenseeClass(51));
    private static final Set<PublicationType> PUB_TYPES = Collections.singleton(buildPubType());
    private static final Set<String> TYPE_OF_USES = Collections.singleton("PRINT");
    private static final String USAGE_DETAIL_ID = "OGN674GHHHB0111";
    private static final String USAGE_DETAIL_ID_DIFFERENT_CASE = "ogn674ghhhb0111";
    private static final String USAGE_DETAIL_ID_FRAGMENT = "OGN674";
    private static final String USAGE_DETAIL_ID_WITH_METASYMBOLS = "OGN554GHHSG005 !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final Long WR_WRK_INST_1 = 123822477L;
    private static final Long WR_WRK_INST_2 = 306985867L;
    private static final String SYSTEM_TITLE = "The Wall Street journal";
    private static final String SYSTEM_TITLE_DIFFERENT_CASE = "THe WAll STReet JOURnal";
    private static final String SYSTEM_TITLE_FRAGMENT = "JOURnal";
    private static final String SYSTEM_TITLE_WITH_METASYMBOLS = "The New York times !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final String SURVEY_COUNTRY = "Portugal";
    private static final String SURVEY_COUNTRY_DIFFERENT_CASE = "PORTugal";
    private static final String SURVEY_COUNTRY_FRAGMENT = "Port";
    private static final String SURVEY_COUNTRY_WITH_METASYMBOLS = "Spain !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final int CONTENT_UNIT_PRICE_1 = 7;
    private static final int CONTENT_UNIT_PRICE_2 = 9;
    private static final int ANNUALIZED_COPIES_1 = 1;
    private static final int ANNUALIZED_COPIES_2 = 3;
    private static final String USER_NAME = "user@copyright.com";
    private static final String ACL_SCENARIO_UID = "0d0041a3-833e-463e-8ad4-f28461dc961d";
    private static final String RH_NAME = "John Wiley & Sons - Books";
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
        assertEquals(2, usageIds.size());
        List<AclUsageDto> actualUsages = aclUsageRepository.findByIds(usageIds);
        assertEquals(2, actualUsages.size());
        List<AclUsageDto> expectedUsages = loadExpectedDtos("json/acl/acl_usage_dto.json");
        IntStream.range(0, 2).forEach(i -> {
            AclUsageDto expectedUsage = expectedUsages.get(i);
            expectedUsage.setId(usageIds.get(i));
            verifyAclUsageDto(expectedUsage, actualUsages.get(i), false);
        });
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update.groovy")
    public void testUpdate() {
        AclUsageFilter filter = new AclUsageFilter();
        filter.setUsageBatchName("Update ACL Usage 2023");
        AclUsageDto expectedAclUsageDto = aclUsageRepository.findDtosByFilter(filter, null, null).get(0);
        expectedAclUsageDto.setPeriod(202412);
        expectedAclUsageDto.setWrWrkInst(WR_WRK_INST_2);
        expectedAclUsageDto.setDetailLicenseeClass(buildDetailLicenseeClass(10, "Primary Metals"));
        expectedAclUsageDto.setPublicationType(buildPubType());
        expectedAclUsageDto.setContentUnitPrice(new BigDecimal("1.0000000000"));
        expectedAclUsageDto.setTypeOfUse("DIGITAL");
        expectedAclUsageDto.setAnnualizedCopies(new BigDecimal("10.00000"));
        expectedAclUsageDto.setAggregateLicenseeClassId(53);
        expectedAclUsageDto.setAggregateLicenseeClassName("Metals");
        aclUsageRepository.update(expectedAclUsageDto);
        verifyAclUsageDto(expectedAclUsageDto, aclUsageRepository.findDtosByFilter(filter, null, null).get(0), false);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByAllFilters() {
        AclUsageFilter filter = new AclUsageFilter();
        filter.setUsageBatchName(ACL_USAGE_BATCH_NAME);
        filter.setUsageOrigin(USAGE_ORIGIN);
        filter.setChannel(CHANNEL);
        filter.setPeriods(PERIODS);
        filter.setDetailLicenseeClasses(DETAIL_LICENSEE_CLASSES);
        filter.setAggregateLicenseeClasses(AGGREGATE_LICENSEE_CLASSES);
        filter.setPubTypes(PUB_TYPES);
        filter.setTypeOfUses(TYPE_OF_USES);
        filter.setUsageDetailIdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID, null));
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST_2, null));
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE, null));
        filter.setSurveyCountryExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY, null));
        filter.setContentUnitPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 10, null));
        filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 1, null));
        assertEquals(1, aclUsageRepository.findCountByFilter(filter));
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterUsageOrigin() {
        assertFilteringFindCountByFilter(filter -> filter.setUsageOrigin(USAGE_ORIGIN), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterChannel() {
        assertFilteringFindCountByFilter(filter -> filter.setChannel(CHANNEL), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterPeriods() {
        assertFilteringFindCountByFilter(filter -> filter.setPeriods(PERIODS), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterDetailLicenseeClasses() {
        assertFilteringFindCountByFilter(filter -> filter.setDetailLicenseeClasses(DETAIL_LICENSEE_CLASSES), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterAggregateLicenseeClasses() {
        assertFilteringFindCountByFilter(filter -> filter.setAggregateLicenseeClasses(AGGREGATE_LICENSEE_CLASSES), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterPubTypes() {
        assertFilteringFindCountByFilter(filter -> filter.setPubTypes(PUB_TYPES), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterTypeOfUses() {
        assertFilteringFindCountByFilter(filter -> filter.setTypeOfUses(TYPE_OF_USES), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterUsageDetailId() {
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID_FRAGMENT, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, USAGE_DETAIL_ID, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, USAGE_DETAIL_ID_DIFFERENT_CASE, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, USAGE_DETAIL_ID_FRAGMENT, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, USAGE_DETAIL_ID_WITH_METASYMBOLS, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, USAGE_DETAIL_ID, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, USAGE_DETAIL_ID_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, USAGE_DETAIL_ID_FRAGMENT, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, USAGE_DETAIL_ID_WITH_METASYMBOLS, null)), 1);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterWrWrkInst() {
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST_1, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, WR_WRK_INST_1, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, WR_WRK_INST_1, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, WR_WRK_INST_1, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, WR_WRK_INST_2, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, WR_WRK_INST_2, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, WR_WRK_INST_1, WR_WRK_INST_2)), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterSystemTitle() {
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_FRAGMENT, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_DIFFERENT_CASE, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_FRAGMENT, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_WITH_METASYMBOLS, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_FRAGMENT, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_WITH_METASYMBOLS, null)), 1);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterSurveyCountry() {
        assertFilteringFindCountByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY_DIFFERENT_CASE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY_FRAGMENT, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_COUNTRY, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_COUNTRY_DIFFERENT_CASE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_COUNTRY_FRAGMENT, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_COUNTRY_WITH_METASYMBOLS, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_COUNTRY, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_COUNTRY_DIFFERENT_CASE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_COUNTRY_FRAGMENT, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_COUNTRY_WITH_METASYMBOLS, null)), 1);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterContentUnitPrice() {
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_UNIT_PRICE_1, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, CONTENT_UNIT_PRICE_1, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, CONTENT_UNIT_PRICE_1, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, CONTENT_UNIT_PRICE_1, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, CONTENT_UNIT_PRICE_2, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, CONTENT_UNIT_PRICE_2, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, CONTENT_UNIT_PRICE_1, CONTENT_UNIT_PRICE_2)), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterAnnualizedCopies() {
        assertFilteringFindCountByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, ANNUALIZED_COPIES_1, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, ANNUALIZED_COPIES_1, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, ANNUALIZED_COPIES_1, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, ANNUALIZED_COPIES_1, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, ANNUALIZED_COPIES_2, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, ANNUALIZED_COPIES_2, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, ANNUALIZED_COPIES_1, ANNUALIZED_COPIES_2)), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByAllFilters() {
        AclUsageFilter filter = new AclUsageFilter();
        filter.setUsageBatchName(ACL_USAGE_BATCH_NAME);
        filter.setUsageOrigin(USAGE_ORIGIN);
        filter.setChannel(CHANNEL);
        filter.setPeriods(PERIODS);
        filter.setDetailLicenseeClasses(DETAIL_LICENSEE_CLASSES);
        filter.setAggregateLicenseeClasses(AGGREGATE_LICENSEE_CLASSES);
        filter.setPubTypes(PUB_TYPES);
        filter.setTypeOfUses(TYPE_OF_USES);
        filter.setUsageDetailIdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID, null));
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST_2, null));
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE, null));
        filter.setSurveyCountryExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY, null));
        filter.setContentUnitPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 10, null));
        filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 1, null));
        List<AclUsageDto> values = aclUsageRepository.findDtosByFilter(filter, null, buildSort());
        assertEquals(1, values.size());
        verifyAclUsageDto(loadExpectedDtos("json/acl/acl_usage_dto_0eeef531.json").get(0), values.get(0), true);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterUsageOrigin() {
        assertFilteringFindDtosByFilter(filter -> filter.setUsageOrigin(USAGE_ORIGIN),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterChannel() {
        assertFilteringFindDtosByFilter(filter -> filter.setChannel(CHANNEL),
            ACL_USAGE_UID_2, ACL_USAGE_UID_5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterPeriods() {
        assertFilteringFindDtosByFilter(filter -> filter.setPeriods(PERIODS),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterDetailLicenseeClasses() {
        assertFilteringFindDtosByFilter(filter -> filter.setDetailLicenseeClasses(DETAIL_LICENSEE_CLASSES),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterAggregateLicenseeClasses() {
        assertFilteringFindDtosByFilter(filter -> filter.setAggregateLicenseeClasses(AGGREGATE_LICENSEE_CLASSES),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterPubTypes() {
        assertFilteringFindDtosByFilter(filter -> filter.setPubTypes(PUB_TYPES),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterTypeOfUses() {
        assertFilteringFindDtosByFilter(filter -> filter.setTypeOfUses(TYPE_OF_USES),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterUsageDetailId() {
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID, null)),
            ACL_USAGE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID_DIFFERENT_CASE, null)),
            ACL_USAGE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID_WITH_METASYMBOLS, null)),
            ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, USAGE_DETAIL_ID, null)),
            ACL_USAGE_UID_3, ACL_USAGE_UID_4, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, USAGE_DETAIL_ID_DIFFERENT_CASE, null)),
            ACL_USAGE_UID_3, ACL_USAGE_UID_4, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, USAGE_DETAIL_ID_FRAGMENT, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3, ACL_USAGE_UID_4, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, USAGE_DETAIL_ID_WITH_METASYMBOLS, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3, ACL_USAGE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, USAGE_DETAIL_ID, null)),
            ACL_USAGE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, USAGE_DETAIL_ID_DIFFERENT_CASE, null)),
            ACL_USAGE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, USAGE_DETAIL_ID_FRAGMENT, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, USAGE_DETAIL_ID_WITH_METASYMBOLS, null)),
            ACL_USAGE_UID_5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterWrWrkInst() {
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST_1, null)),
            ACL_USAGE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, WR_WRK_INST_1, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_4, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, WR_WRK_INST_1, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_4, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, WR_WRK_INST_1, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3, ACL_USAGE_UID_4, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, WR_WRK_INST_2, null)),
            ACL_USAGE_UID_3, ACL_USAGE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, WR_WRK_INST_2, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3, ACL_USAGE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, WR_WRK_INST_1, WR_WRK_INST_2)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3, ACL_USAGE_UID_4);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterSystemTitle() {
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null)),
            ACL_USAGE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_DIFFERENT_CASE, null)),
            ACL_USAGE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_WITH_METASYMBOLS, null)),
            ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_DIFFERENT_CASE, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_FRAGMENT, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3, ACL_USAGE_UID_4, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_WITH_METASYMBOLS, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3, ACL_USAGE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE, null)),
            ACL_USAGE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_DIFFERENT_CASE, null)),
            ACL_USAGE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_FRAGMENT, null)),
            ACL_USAGE_UID_3, ACL_USAGE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_WITH_METASYMBOLS, null)),
            ACL_USAGE_UID_5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterSurveyCountry() {
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY_DIFFERENT_CASE, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY_WITH_METASYMBOLS, null)),
            ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_COUNTRY, null)),
            ACL_USAGE_UID_4, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_COUNTRY_DIFFERENT_CASE, null)),
            ACL_USAGE_UID_4, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_COUNTRY_FRAGMENT, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3, ACL_USAGE_UID_4, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_COUNTRY_WITH_METASYMBOLS, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3, ACL_USAGE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_COUNTRY, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_COUNTRY_DIFFERENT_CASE, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_COUNTRY_FRAGMENT, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_COUNTRY_WITH_METASYMBOLS, null)),
            ACL_USAGE_UID_5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterContentUnitPrice() {
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_UNIT_PRICE_1, null)),
            ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, CONTENT_UNIT_PRICE_1, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3, ACL_USAGE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, CONTENT_UNIT_PRICE_1, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3, ACL_USAGE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, CONTENT_UNIT_PRICE_1, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3, ACL_USAGE_UID_4, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, CONTENT_UNIT_PRICE_2, null)),
            ACL_USAGE_UID_4, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, CONTENT_UNIT_PRICE_2, null)),
            ACL_USAGE_UID_3, ACL_USAGE_UID_4, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, CONTENT_UNIT_PRICE_1, CONTENT_UNIT_PRICE_2)),
            ACL_USAGE_UID_3, ACL_USAGE_UID_4, ACL_USAGE_UID_5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterAnnualizedCopies() {
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, ANNUALIZED_COPIES_1, null)),
            ACL_USAGE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, ANNUALIZED_COPIES_1, null)),
            ACL_USAGE_UID_3, ACL_USAGE_UID_4, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, ANNUALIZED_COPIES_1, null)),
            ACL_USAGE_UID_3, ACL_USAGE_UID_4, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, ANNUALIZED_COPIES_1, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3, ACL_USAGE_UID_4, ACL_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, ANNUALIZED_COPIES_2, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, ANNUALIZED_COPIES_2, null)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3, ACL_USAGE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, ANNUALIZED_COPIES_1, ANNUALIZED_COPIES_2)),
            ACL_USAGE_UID_2, ACL_USAGE_UID_3, ACL_USAGE_UID_4);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testSortingFindDtosByFilter() {
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_4, "detailId");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_5, ACL_USAGE_UID_1, "period");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_5, "usageOrigin");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_3, "channel");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_5, ACL_USAGE_UID_4, "usageDetailId");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_5, "wrWrkInst");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_4, "systemTitle");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_5, "detLcId");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_2, "detLcName");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_4, "aggLcId");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_4, ACL_USAGE_UID_5, "aggLcName");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_4, ACL_USAGE_UID_5, "surveyCountry");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_1, "publicationType");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_5, ACL_USAGE_UID_1, "contentUnitPrice");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_5, ACL_USAGE_UID_2, "typeOfUse");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_5, "annualizedCopies");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_2, "updateUser");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_1, "updateDate");
    }

    @Test
    @TestData(fileName = FIND_PERIODS)
    public void testFindPeriods() {
        List<Integer> expectedPeriods = Arrays.asList(202212, 202112);
        List<Integer> actualPeriods = aclUsageRepository.findPeriods();
        assertFalse(actualPeriods.isEmpty());
        assertEquals(expectedPeriods, actualPeriods);
    }

    @Test
    public void testFindDefaultUsageAgesWeights() {
        List<UsageAge> usageAgeWeights = aclUsageRepository.findDefaultUsageAgesWeights();
        assertEquals(16, usageAgeWeights.size());
    }

    @Test
    @TestData(fileName = FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID)
    public void testFindAclRightsholderTotalsHoldersByScenarioIdEmptySearchValue() {
        List<AclRightsholderTotalsHolder> holders = aclUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(
            ACL_SCENARIO_UID, StringUtils.EMPTY, null, null);
        assertEquals(2, holders.size());
        verifyAclRightsholderTotalsHolder(buildAclRightsholderTotalsHolder(1000002859L, RH_NAME, 100.00,
            16.00, 84.00, 220.00, 35.00, 178.00, 2, 2), holders.get(0));
        verifyAclRightsholderTotalsHolder(buildAclRightsholderTotalsHolder(1000000026L, null, 20.00, 3.00, 10.00,
            0.00, 0.00, 0.00, 1, 1), holders.get(1));
    }

    @Test
    @TestData(fileName = FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID)
    public void testFindAclRightsholderTotalsHoldersByScenarioIdNotEmptySearchValue() {
        List<AclRightsholderTotalsHolder> holders = aclUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(
            ACL_SCENARIO_UID, "JohN", null, null);
        assertEquals(1, holders.size());
        verifyAclRightsholderTotalsHolder(buildAclRightsholderTotalsHolder(1000002859L, RH_NAME, 100.00,
            16.00, 84.00, 220.00, 35.00, 178.00, 2, 2), holders.get(0));
    }

    @Test
    @TestData(fileName = FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID)
    public void testFindAclRightsholderTotalsHolderCountByScenarioIdEmptySearchValue() {
        assertEquals(2, aclUsageRepository.findAclRightsholderTotalsHolderCountByScenarioId(ACL_SCENARIO_UID,
            StringUtils.EMPTY));
    }

    @Test
    @TestData(fileName = FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID)
    public void testFindAclRightsholderTotalsHolderCountByScenarioIdNullSearchValue() {
        assertEquals(2, aclUsageRepository.findAclRightsholderTotalsHolderCountByScenarioId(ACL_SCENARIO_UID, null));
    }

    @Test
    @TestData(fileName = FIND_ACL_RH_TOTALS_HOLDERS_BY_SCENARIO_ID)
    public void testSortingFindAclRightsholderTotalsHoldersByScenarioId() {
        AclRightsholderTotalsHolder holder1 = buildAclRightsholderTotalsHolder(1000002859L, RH_NAME, 100.00,
            16.00, 84.00, 220.00, 35.00, 178.00, 2, 2);
        AclRightsholderTotalsHolder holder2 = buildAclRightsholderTotalsHolder(1000000026L, null, 20.00, 3.00, 10.00,
            0.00, 0.00, 0.00, 1, 1);
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "rightsholder.accountNumber");
        assertSortingAclRightsholderTotalsHolder(holder1, holder2, "rightsholder.name");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "grossTotalPrint");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "serviceFeeTotalPrint");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "netTotalPrint");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "grossTotalDigital");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "serviceFeeTotalDigital");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "netTotalDigital");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "numberOfTitles");
        assertSortingAclRightsholderTotalsHolder(holder2, holder1, "numberOfAggLcClasses");
        assertSortingAclRightsholderTotalsHolder(holder1, holder1, "licenseType");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "add-to-scenario.groovy")
    public void testAddToAclScenario() {
        AclScenario scenario = buildAclScenario("dec62df4-6a8f-4c59-ad65-2a5e06b3924d",
            "11c6590a-cea4-4cb6-a3ce-0f23a6f2e81c", "0f65b9b0-308f-4f73-b232-773a98baba2e",
            "17970e6b-c020-4c84-9282-045ca465a8af", "ACL Scenario 201512", "Description",
            ScenarioStatusEnum.IN_PROGRESS, true, 202212, "ACL", USER_NAME, "2022-02-14T12:00:00+00:00");
        aclUsageRepository.addToAclScenario(scenario, "SYSTEM");
        List<AclScenarioDetail> scenarioDetails =
            aclUsageRepository.findScenarioDetailsByScenarioId("dec62df4-6a8f-4c59-ad65-2a5e06b3924d");
        assertEquals(1, scenarioDetails.size());
        AclScenarioDetail expectedScenarioDetail = buildAclScenarioDetail();
        verifyAclScenarioDetail(expectedScenarioDetail, scenarioDetails.get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-count-with-null-pub-type-or-content-unit-price-by-batch-id.groovy")
    public void testFindCountWithNullPubTypeOrContentUnitPriceByBatchId() {
        assertEquals(0, aclUsageRepository.findCountWithNullPubTypeOrContentUnitPriceByBatchId(ACL_USAGE_BATCH_UID_1));
        assertEquals(3, aclUsageRepository.findCountWithNullPubTypeOrContentUnitPriceByBatchId(ACL_USAGE_BATCH_UID_2));
    }

    private AclScenarioDetail buildAclScenarioDetail() {
        AclScenarioDetail scenarioDetail = new AclScenarioDetail();
        scenarioDetail.setScenarioId("dec62df4-6a8f-4c59-ad65-2a5e06b3924d");
        scenarioDetail.setPeriod(202112);
        scenarioDetail.setOriginalDetailId("OGN674GHHHB0110");
        scenarioDetail.setWrWrkInst(122820638L);
        scenarioDetail.setSystemTitle("Technology review");
        scenarioDetail.setDetailLicenseeClass(buildDetailLicenseeClass(43, "Other - Govt"));
        scenarioDetail.setAggregateLicenseeClassId(1);
        scenarioDetail.setAggregateLicenseeClassName("Food and Tobacco");
        scenarioDetail.setPublicationType(buildPubType(new BigDecimal("1.00")));
        scenarioDetail.setContentUnitPrice(new BigDecimal("11.0000000000"));
        scenarioDetail.setQuantity(10L);
        scenarioDetail.setUsageAgeWeight(new BigDecimal("0.50000"));
        scenarioDetail.setWeightedCopies(new BigDecimal("5.0000000000"));
        scenarioDetail.setSurveyCountry("Germany");
        scenarioDetail.setScenarioShareDetails(buildAclScenarioShareDetails());
        return scenarioDetail;
    }

    private List<AclScenarioShareDetail> buildAclScenarioShareDetails() {
        return Arrays.asList(
            buildAclScenarioShareDetail(1000028511L, "DIGITAL"),
            buildAclScenarioShareDetail(1000028511L, "PRINT"));
    }

    private AclScenarioShareDetail buildAclScenarioShareDetail(Long rhAccountNumber, String typeOfUse) {
        AclScenarioShareDetail aclScenarioShareDetail = new AclScenarioShareDetail();
        aclScenarioShareDetail.setRhAccountNumber(rhAccountNumber);
        aclScenarioShareDetail.setValueWeight(new BigDecimal("55.0000000000"));
        aclScenarioShareDetail.setVolumeWeight(new BigDecimal("5.0000000000"));
        aclScenarioShareDetail.setTypeOfUse(typeOfUse);
        return aclScenarioShareDetail;
    }

    private void verifyAclScenarioDetail(AclScenarioDetail expectedScenarioDetail,
                                         AclScenarioDetail actualScenarioDetail) {
        assertEquals(expectedScenarioDetail.getScenarioId(), actualScenarioDetail.getScenarioId());
        assertEquals(expectedScenarioDetail.getPeriod(), actualScenarioDetail.getPeriod());
        assertEquals(expectedScenarioDetail.getOriginalDetailId(), actualScenarioDetail.getOriginalDetailId());
        assertEquals(expectedScenarioDetail.getWrWrkInst(), actualScenarioDetail.getWrWrkInst());
        assertEquals(expectedScenarioDetail.getSystemTitle(), actualScenarioDetail.getSystemTitle());
        assertEquals(expectedScenarioDetail.getDetailLicenseeClass().getId(),
            actualScenarioDetail.getDetailLicenseeClass().getId());
        assertEquals(expectedScenarioDetail.getDetailLicenseeClass().getDescription(),
            actualScenarioDetail.getDetailLicenseeClass().getDescription());
        assertEquals(expectedScenarioDetail.getAggregateLicenseeClassId(),
            actualScenarioDetail.getAggregateLicenseeClassId());
        assertEquals(expectedScenarioDetail.getAggregateLicenseeClassName(),
            actualScenarioDetail.getAggregateLicenseeClassName());
        assertEquals(expectedScenarioDetail.getPublicationType().getId(),
            actualScenarioDetail.getPublicationType().getId());
        assertEquals(expectedScenarioDetail.getPublicationType().getWeight(),
            actualScenarioDetail.getPublicationType().getWeight());
        assertEquals(expectedScenarioDetail.getContentUnitPrice(), actualScenarioDetail.getContentUnitPrice());
        assertEquals(expectedScenarioDetail.getQuantity(), actualScenarioDetail.getQuantity());
        assertEquals(expectedScenarioDetail.getUsageAgeWeight(), actualScenarioDetail.getUsageAgeWeight());
        assertEquals(expectedScenarioDetail.getWeightedCopies(), actualScenarioDetail.getWeightedCopies());
        assertEquals(expectedScenarioDetail.getSurveyCountry(), actualScenarioDetail.getSurveyCountry());
        List<AclScenarioShareDetail> expectedShareDetails = expectedScenarioDetail.getScenarioShareDetails();
        List<AclScenarioShareDetail> actualShareDetails = actualScenarioDetail.getScenarioShareDetails();
        assertEquals(expectedShareDetails.size(), actualShareDetails.size());
        IntStream.range(0, actualScenarioDetail.getScenarioShareDetails().size()).forEach(i -> {
            verifyAclScenarioShareDetails(expectedShareDetails.get(i), actualShareDetails.get(i));
        });
    }

    private void verifyAclScenarioShareDetails(AclScenarioShareDetail expectedDetail,
                                               AclScenarioShareDetail actualDetail) {
        assertEquals(expectedDetail.getRhAccountNumber(), actualDetail.getRhAccountNumber());
        assertEquals(expectedDetail.getTypeOfUse(), actualDetail.getTypeOfUse());
        assertEquals(expectedDetail.getVolumeWeight(), actualDetail.getVolumeWeight());
        assertEquals(expectedDetail.getValueWeight(), actualDetail.getValueWeight());
    }

    private AclScenario buildAclScenario(String id, String fundPoolId, String usageBatchId, String grantSetId,
                                         String name, String description, ScenarioStatusEnum status, boolean editable,
                                         Integer periodEndDate, String licenseType, String user, String date) {
        AclScenario scenario = new AclScenario();
        scenario.setId(id);
        scenario.setFundPoolId(fundPoolId);
        scenario.setUsageBatchId(usageBatchId);
        scenario.setGrantSetId(grantSetId);
        scenario.setName(name);
        scenario.setDescription(description);
        scenario.setStatus(status);
        scenario.setEditableFlag(editable);
        scenario.setPeriodEndDate(periodEndDate);
        scenario.setLicenseType(licenseType);
        scenario.setCreateUser(user);
        scenario.setUpdateUser(user);
        scenario.setCreateDate(Date.from(OffsetDateTime.parse(date).toInstant()));
        scenario.setUpdateDate(Date.from(OffsetDateTime.parse(date).toInstant()));
        return scenario;
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
        assertEquals(expectedUsage.getDetailLicenseeClass().getId(), actualUsage.getDetailLicenseeClass().getId());
        assertEquals(expectedUsage.getDetailLicenseeClass().getDescription(),
            actualUsage.getDetailLicenseeClass().getDescription());
        assertEquals(expectedUsage.getAggregateLicenseeClassId(), actualUsage.getAggregateLicenseeClassId());
        assertEquals(expectedUsage.getAggregateLicenseeClassName(), actualUsage.getAggregateLicenseeClassName());
        assertEquals(expectedUsage.getSurveyCountry(), actualUsage.getSurveyCountry());
        assertEquals(expectedUsage.getPublicationType(), actualUsage.getPublicationType());
        assertEquals(expectedUsage.getContentUnitPrice(), actualUsage.getContentUnitPrice());
        assertEquals(expectedUsage.getTypeOfUse(), actualUsage.getTypeOfUse());
        assertEquals(expectedUsage.getAnnualizedCopies(), actualUsage.getAnnualizedCopies());
        assertEquals(expectedUsage.getQuantity(), actualUsage.getQuantity());
        assertEquals(expectedUsage.isEditable(), actualUsage.isEditable());
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

    private static DetailLicenseeClass buildDetailLicenseeClass(int id, String description) {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(id);
        detailLicenseeClass.setDescription(description);
        return detailLicenseeClass;
    }

    private static AggregateLicenseeClass buildAggregateLicenseeClass(int id) {
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(id);
        return aggregateLicenseeClass;
    }

    private static PublicationType buildPubType() {
        PublicationType publicationType = new PublicationType();
        publicationType.setId("73876e58-2e87-485e-b6f3-7e23792dd214");
        publicationType.setName("BK");
        publicationType.setDescription("Book");
        return publicationType;
    }

    private static PublicationType buildPubType(BigDecimal weight) {
        PublicationType publicationType = new PublicationType();
        publicationType.setId("73876e58-2e87-485e-b6f3-7e23792dd214");
        publicationType.setName("BK");
        publicationType.setDescription("Book");
        publicationType.setWeight(weight);
        return publicationType;
    }

    private Sort buildSort() {
        return new Sort("updateDate", Sort.Direction.ASC);
    }

    private void assertFilteringFindCountByFilter(Consumer<AclUsageFilter> consumer, int count) {
        AclUsageFilter filter = new AclUsageFilter();
        filter.setUsageBatchName(ACL_USAGE_BATCH_NAME);
        consumer.accept(filter);
        int usagesCount = aclUsageRepository.findCountByFilter(filter);
        assertEquals(count, usagesCount);
    }

    private void assertFilteringFindDtosByFilter(Consumer<AclUsageFilter> consumer, String... usageIds) {
        AclUsageFilter filter = new AclUsageFilter();
        filter.setUsageBatchName(ACL_USAGE_BATCH_NAME);
        consumer.accept(filter);
        List<AclUsageDto> usages = aclUsageRepository.findDtosByFilter(filter, null, buildSort());
        assertEquals(usageIds.length, usages.size());
        IntStream.range(0, usageIds.length)
            .forEach(index -> assertEquals(usageIds[index], usages.get(index).getId()));
    }

    private void assertSortingFindDtosByFilter(String usageIdAsc, String usageIdDesc, String sortProperty) {
        AclUsageFilter filter = new AclUsageFilter();
        List<AclUsageDto> usages =
            aclUsageRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Sort.Direction.ASC));
        assertEquals(usageIdAsc, usages.get(0).getId());
        usages =
            aclUsageRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Sort.Direction.DESC));
        assertEquals(usageIdDesc, usages.get(0).getId());
    }

    private AclRightsholderTotalsHolder buildAclRightsholderTotalsHolder(Long rhAccountNumber, String rhName,
                                                                         Double grossTotalPrint,
                                                                         Double serviceFeeTotalPrint,
                                                                         Double netTotalPrint, Double grossTotalDigital,
                                                                         Double serviceFeeTotalDigital,
                                                                         Double netTotalDigital,
                                                                         int numberOfTitles, int numberOfAggLcClasses) {
        AclRightsholderTotalsHolder holder = new AclRightsholderTotalsHolder();
        holder.getRightsholder().setAccountNumber(rhAccountNumber);
        holder.getRightsholder().setName(rhName);
        holder.setGrossTotalPrint(BigDecimal.valueOf(grossTotalPrint).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setServiceFeeTotalPrint(
            BigDecimal.valueOf(serviceFeeTotalPrint).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setNetTotalPrint(BigDecimal.valueOf(netTotalPrint).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setGrossTotalDigital(
            BigDecimal.valueOf(grossTotalDigital).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setServiceFeeTotalDigital(
            BigDecimal.valueOf(serviceFeeTotalDigital).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setNetTotalDigital(BigDecimal.valueOf(netTotalDigital).setScale(10, BigDecimal.ROUND_HALF_UP));
        holder.setNumberOfTitles(numberOfTitles);
        holder.setNumberOfAggLcClasses(numberOfAggLcClasses);
        holder.setLicenseType("ACL");
        return holder;
    }

    private void verifyAclRightsholderTotalsHolder(AclRightsholderTotalsHolder expectedHolder,
                                                   AclRightsholderTotalsHolder actualHolder) {
        assertEquals(
            expectedHolder.getRightsholder().getAccountNumber(), actualHolder.getRightsholder().getAccountNumber());
        assertEquals(expectedHolder.getRightsholder().getName(), actualHolder.getRightsholder().getName());
        assertEquals(expectedHolder.getGrossTotalPrint(),
            actualHolder.getGrossTotalPrint().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getServiceFeeTotalPrint(),
            actualHolder.getServiceFeeTotalPrint().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getNetTotalPrint(),
            actualHolder.getNetTotalPrint().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getGrossTotalDigital(),
            actualHolder.getGrossTotalDigital().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getServiceFeeTotalDigital(),
            actualHolder.getServiceFeeTotalDigital().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getNetTotalDigital(),
            actualHolder.getNetTotalDigital().setScale(10, BigDecimal.ROUND_HALF_UP));
        assertEquals(expectedHolder.getNumberOfTitles(), actualHolder.getNumberOfTitles());
        assertEquals(expectedHolder.getNumberOfAggLcClasses(), actualHolder.getNumberOfAggLcClasses());
        assertEquals(expectedHolder.getLicenseType(), actualHolder.getLicenseType());
    }

    private void assertSortingAclRightsholderTotalsHolder(AclRightsholderTotalsHolder holderAsc,
                                                          AclRightsholderTotalsHolder holderDesc, String sortProperty) {
        List<AclRightsholderTotalsHolder> holders = aclUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(
            ACL_SCENARIO_UID, StringUtils.EMPTY, null, new Sort(sortProperty, Sort.Direction.ASC));
        verifyAclRightsholderTotalsHolder(holderAsc, holders.get(0));
        holders = aclUsageRepository.findAclRightsholderTotalsHoldersByScenarioId(
            ACL_SCENARIO_UID, StringUtils.EMPTY, null, new Sort(sortProperty, Sort.Direction.DESC));
        verifyAclRightsholderTotalsHolder(holderDesc, holders.get(0));
    }
}
