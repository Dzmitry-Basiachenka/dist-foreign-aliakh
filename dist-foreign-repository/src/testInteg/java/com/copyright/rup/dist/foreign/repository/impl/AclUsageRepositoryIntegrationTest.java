package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageRepository;
import com.copyright.rup.dist.foreign.repository.impl.csv.CsvReportsTestHelper;

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
import java.math.BigDecimal;
import java.util.ArrayList;
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
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclUsageRepositoryIntegrationTest extends CsvReportsTestHelper {

    private static final String FOLDER_NAME = "acl-usage-repository-integration-test/";
    private static final String FIND_DTOS_BY_FILTER = FOLDER_NAME + "find-dtos-by-filter.groovy";
    private static final String FIND_PERIODS = FOLDER_NAME + "find-periods.groovy";
    private static final String ACL_USAGE_BATCH_NAME = "ACL Usage Batch 2021";
    private static final String ACL_USAGE_BATCH_UID_1 = "36d8901e-1f3b-4e68-8c95-1f8b02740ed2";
    private static final String ACL_USAGE_BATCH_UID_2 = "c653d257-4180-4fb4-9119-f001063e4a56";
    private static final String ACL_GRANT_SET_UID_1 = "a764abd9-7391-481a-b4fe-755452fd3935";
    private static final String ACL_GRANT_SET_UID_2 = "c5d66bb6-99c5-4fb5-a30b-22555dd685ea";
    private static final String ACL_USAGE_UID_1 = "8ff48add-0eea-4fe3-81d0-3264c6779936";
    private static final String ACL_USAGE_UID_2 = "0eeef531-b779-4b3b-827d-b44b2261c6db";
    private static final String ACL_USAGE_UID_3 = "2ba0fab7-746d-41e0-87b5-c2b3997ce0ae";
    private static final String ACL_USAGE_UID_4 = "dfc0f9f4-2c50-4e1f-ad1a-a29fc2f9f4cd";
    private static final String ACL_USAGE_UID_5 = "1e9ab4dd-8526-4309-9c54-20226c48cd27";
    private static final String PRINT_TOU = "PRINT";
    private static final String DIGITAL_TOU = "DIGITAL";
    private static final UdmUsageOriginEnum USAGE_ORIGIN = UdmUsageOriginEnum.RFA;
    private static final UdmChannelEnum CHANNEL = UdmChannelEnum.CCC;
    private static final Set<Integer> PERIODS = Set.of(202112);
    private static final int DISTRIBUTION_PERIOD = 202212;
    private static final List<Integer> PERIOD_PRIORS = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
    private static final Set<DetailLicenseeClass> DETAIL_LICENSEE_CLASSES =
        Set.of(buildDetailLicenseeClass(2, "Textiles, Apparel, etc."));
    private static final Set<AggregateLicenseeClass> AGGREGATE_LICENSEE_CLASSES =
        Set.of(buildAggregateLicenseeClass(51));
    private static final Set<PublicationType> PUB_TYPES = Set.of(buildPubType());
    private static final Set<String> REPORTED_TYPE_OF_USES = Set.of("PRINT_COPIES");
    private static final String TYPE_OF_USE = "PRINT";
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
        expectedAclUsageDto.setTypeOfUse(DIGITAL_TOU);
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
        filter.setReportedTypeOfUses(REPORTED_TYPE_OF_USES);
        filter.setTypeOfUse(TYPE_OF_USE);
        filter.setUsageDetailIdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID, null));
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST_2, null));
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE, null));
        filter.setSurveyCountryExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY, null));
        filter.setContentUnitPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 10, null));
        filter.setContentUnitPriceFlagExpression(new FilterExpression<>(FilterOperatorEnum.Y));
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
    public void testFindCountByFilterReportedTypeOfUses() {
        assertFilteringFindCountByFilter(filter -> filter.setReportedTypeOfUses(REPORTED_TYPE_OF_USES), 1);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterTypeOfUse() {
        assertFilteringFindCountByFilter(filter -> filter.setTypeOfUse(TYPE_OF_USE), 2);
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
    public void testFindCountByFilterContentUnitPriceFlag() {
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceFlagExpression(
            new FilterExpression<>(FilterOperatorEnum.Y)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceFlagExpression(
            new FilterExpression<>(FilterOperatorEnum.N)), 3);
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
        filter.setReportedTypeOfUses(REPORTED_TYPE_OF_USES);
        filter.setTypeOfUse(TYPE_OF_USE);
        filter.setUsageDetailIdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID, null));
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST_2, null));
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE, null));
        filter.setSurveyCountryExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY, null));
        filter.setContentUnitPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 10, null));
        filter.setContentUnitPriceFlagExpression(new FilterExpression<>(FilterOperatorEnum.Y));
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
    public void testFindDtosByFilterReportedTypeOfUses() {
        assertFilteringFindDtosByFilter(filter -> filter.setReportedTypeOfUses(REPORTED_TYPE_OF_USES), ACL_USAGE_UID_2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterTypeOfUse() {
        assertFilteringFindDtosByFilter(filter -> filter.setTypeOfUse(TYPE_OF_USE), ACL_USAGE_UID_2, ACL_USAGE_UID_3);
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
    public void testFindDtosByFilterContentUnitPriceFlag() {
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceFlagExpression(
            new FilterExpression<>(FilterOperatorEnum.Y)), ACL_USAGE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceFlagExpression(
            new FilterExpression<>(FilterOperatorEnum.N)), ACL_USAGE_UID_3, ACL_USAGE_UID_4, ACL_USAGE_UID_5);
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
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_1, "content");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_1, "price");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_5, ACL_USAGE_UID_1, "contentUnitPrice");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_5, ACL_USAGE_UID_2, "contentUnitPriceFlag");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_5, "reportedTypeOfUse");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_5, ACL_USAGE_UID_2, "typeOfUse");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_5, "annualizedCopies");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_5, ACL_USAGE_UID_2, "mdwmsDeleted");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_1, ACL_USAGE_UID_2, "updateUser");
        assertSortingFindDtosByFilter(ACL_USAGE_UID_2, ACL_USAGE_UID_1, "updateDate");
    }

    @Test
    @TestData(fileName = FIND_PERIODS)
    public void testFindPeriods() {
        List<Integer> expectedPeriods = List.of(202212, 202112);
        List<Integer> actualPeriods = aclUsageRepository.findPeriods();
        assertFalse(actualPeriods.isEmpty());
        assertEquals(expectedPeriods, actualPeriods);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "usage-exist-for-licensee-classes-and-type-of-use.groovy")
    public void testUsageExistForLicenseeClassesAndTypeOfUse() {
        String grantSetId = "5e8da1ba-3b20-4f8e-96ed-f9506da3b87a";
        String batchId = "55adc071-8bbb-46be-a56e-0daba0883a97";
        assertTrue(aclUsageRepository.usageExistForLicenseeClassesAndTypeOfUse(batchId, grantSetId,
            Sets.newHashSet(1, 3), DIGITAL_TOU));
        assertFalse(aclUsageRepository.usageExistForLicenseeClassesAndTypeOfUse(batchId, grantSetId,
            Sets.newHashSet(1, 3), PRINT_TOU));
        assertFalse(aclUsageRepository.usageExistForLicenseeClassesAndTypeOfUse(batchId, grantSetId,
            Set.of(2), DIGITAL_TOU));
        assertTrue(aclUsageRepository.usageExistForLicenseeClassesAndTypeOfUse(batchId, grantSetId,
            Set.of(2), PRINT_TOU));
    }

    @Test
    public void testFindDefaultUsageAgesWeights() {
        List<UsageAge> usageAgeWeights = aclUsageRepository.findDefaultUsageAgesWeights();
        assertEquals(16, usageAgeWeights.size());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-count-non-valid-usages.groovy")
    public void testFindCountInvalidUsages() {
        assertEquals(0, aclUsageRepository.findCountInvalidUsages(ACL_USAGE_BATCH_UID_1, ACL_GRANT_SET_UID_1,
            DISTRIBUTION_PERIOD, PERIOD_PRIORS));
        assertEquals(4, aclUsageRepository.findCountInvalidUsages(ACL_USAGE_BATCH_UID_2, ACL_GRANT_SET_UID_2,
            DISTRIBUTION_PERIOD, PERIOD_PRIORS));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-count-non-valid-usages.groovy")
    public void testWriteInvalidUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(
            outputStream -> aclUsageRepository.writeInvalidUsagesCsvReport(ACL_USAGE_BATCH_UID_2, ACL_GRANT_SET_UID_2,
                DISTRIBUTION_PERIOD, PERIOD_PRIORS, outputStream),
            "acl/detail_ids_missing_values.csv");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-count-non-valid-usages.groovy")
    public void testWriteInvalidUsagesCsvEmptyReport() throws IOException {
        assertFilesWithExecutor(
            outputStream -> aclUsageRepository.writeInvalidUsagesCsvReport(ACL_USAGE_BATCH_UID_1, ACL_GRANT_SET_UID_1,
                DISTRIBUTION_PERIOD, PERIOD_PRIORS, outputStream),
            "acl/detail_ids_missing_values_empty.csv");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "copy-acl-usages.groovy")
    public void testCopyAclUsages() {
        String sourceBatchId = "e0cc3eb3-5c85-4745-b868-e83529864d1a";
        String targetBatchId = "3516305e-d3ad-413b-819e-a390b81d4aa7";
        String userName = "auser@copyright.com";
        List<String> copiedUsagesIds = aclUsageRepository.copyAclUsages(sourceBatchId, targetBatchId, userName);
        assertEquals(2, copiedUsagesIds.size());
        List<AclUsageDto> actualUsages = aclUsageRepository.findByIds(copiedUsagesIds);
        assertEquals(2, actualUsages.size());
        List<AclUsageDto> expectedUsages = loadExpectedDtos("json/acl/acl_usage_dto_for_copy.json");
        assertEquals(expectedUsages.size(), actualUsages.size());
        IntStream.range(0, 2).forEach(i -> {
            AclUsageDto expectedUsage = expectedUsages.get(i);
            expectedUsage.setId(copiedUsagesIds.get(i));
            verifyAclUsageDto(expectedUsage, actualUsages.get(i), false);
        });
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-by-usage-batch-id.groovy")
    public void testDeleteByUsageBatchId() {
        List<String> usageIds = List.of("52c239cf-a09a-4c96-9ec1-986fec4266cf");
        assertEquals(1, aclUsageRepository.findByIds(usageIds).size());
        aclUsageRepository.deleteByUsageBatchId("8b81ce3e-6342-42a7-a3eb-c955d5e7ebba");
        assertEquals(0, aclUsageRepository.findByIds(usageIds).size());
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
        assertEquals(expectedUsage.getPrice(), actualUsage.getPrice());
        assertEquals(expectedUsage.getPriceFlag(), actualUsage.getPriceFlag());
        assertEquals(expectedUsage.getContent(), actualUsage.getContent());
        assertEquals(expectedUsage.getContentFlag(), actualUsage.getContentFlag());
        assertEquals(expectedUsage.getContentUnitPrice(), actualUsage.getContentUnitPrice());
        assertEquals(expectedUsage.getContentUnitPriceFlag(), actualUsage.getContentUnitPriceFlag());
        assertEquals(expectedUsage.getReportedTypeOfUse(), actualUsage.getReportedTypeOfUse());
        assertEquals(expectedUsage.getTypeOfUse(), actualUsage.getTypeOfUse());
        assertEquals(expectedUsage.getAnnualizedCopies(), actualUsage.getAnnualizedCopies());
        assertEquals(expectedUsage.getQuantity(), actualUsage.getQuantity());
        assertEquals(expectedUsage.isEditable(), actualUsage.isEditable());
        assertEquals(expectedUsage.isWorkDeletedFlag(), actualUsage.isWorkDeletedFlag());
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
}
