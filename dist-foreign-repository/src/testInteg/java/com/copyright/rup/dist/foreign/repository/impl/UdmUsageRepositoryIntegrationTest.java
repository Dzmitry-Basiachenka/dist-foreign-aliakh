package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Verifies {@link UdmUsageRepository}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 04/28/2021
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class UdmUsageRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "udm-usage-repository-integration-test/";
    private static final String FIND_DTOS_BY_FILTER = FOLDER_NAME + "find-dtos-by-filter.groovy";
    private static final String FIND_FIELDS = FOLDER_NAME + "find-fields.groovy";
    private static final String FIND_BY_SEARCH_VALUE = FOLDER_NAME + "find-by-search-value.groovy";
    private static final String UDM_USAGE_ORIGINAL_DETAIL_UID = "OGN674GHHSB0025";
    private static final String UDM_USAGE_UID_1 = "92cf072e-d6d7-4a23-9652-5ee7bca71313";
    private static final String UDM_USAGE_UID_2 = "0dfba00c-6d72-4a0e-8f84-f7c365432f85";
    private static final String UDM_USAGE_UID_3 = "cc3269aa-2f56-21c7-b0d1-34dd0edfcf5a";
    private static final String UDM_USAGE_UID_4 = "847dfefd-3cf8-4853-8b1b-d59b5cd163e9";
    private static final String UDM_USAGE_UID_5 = "9c7f64a7-95c1-4825-ad85-ff0672d252c4";
    private static final String UDM_USAGE_UID_6 = "b989e02b-1f1d-4637-b89e-dc99938a51b9";
    private static final String UDM_USAGE_UID_7 = "c3e3082f-9c3e-4e14-9640-c485a9eae24f";
    private static final String UDM_USAGE_UID_8 = "4a39452b-1319-4bf9-9a66-f79e6c7063be";
    private static final String UDM_USAGE_UID_9 = "1d856581-09aa-4f70-9e0c-cf7fe121135f";
    private static final String UDM_USAGE_UID_10 = "d147235f-3595-4b74-b091-f6f91fba2e7d";
    private static final String UDM_USAGE_UID_11 = "cb241298-5c9e-4222-8fc1-5ee80c0e48f1";
    private static final String UDM_USAGE_UID_12 = "f2776c57-f744-4fb0-9445-f01bde48730f";
    private static final String UDM_BATCH_UID_1 = "aa5751aa-2858-38c6-b0d9-51ec0edfcf4f";
    private static final String UDM_BATCH_UID_2 = "bb5751aa-2f56-38c6-b0d9-45ec0edfcf4a";
    private static final String UDM_BATCH_UID_3 = "4b6055be-fc4e-4b49-aeab-28563366c9fd";
    private static final String UDM_BATCH_UID_4 = "80452178-e250-415f-b3e4-71a48ca3e218";
    private static final String REPORTED_STANDARD_NUMBER = "0927-7765";
    private static final String REPORTED_TYPE_OF_USE = "PRINT_COPIES";
    private static final String TYPE_OF_USE = "PRINT";
    private static final String PUB_TYPE_JOURNAL = "Journal";
    private static final String PUB_TYPE_NOT_SHARED = "Not Shared";
    private static final String PUBLICATION_FORMAT = "Digital";
    private static final String ARTICLE = "Green chemistry";
    private static final String LANGUAGE = "English";
    private static final String LANGUAGE_DIFFERENT_CASE = "EngLISH";
    private static final String LANGUAGE_FRAGMENT = "EnG";
    private static final String LANGUAGE_WITH_METASYMBOLS = "GerMAN !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final String SURVEY_RESPONDENT = "c6615155-f82b-402c-8f22-77e2722ae448";
    private static final String SURVEY_RESPONDENT_DIFFERENT_CASE = "C6615155-F82B-402C-8F22-77E2722AE448";
    private static final String SURVEY_RESPONDENT_FRAGMENT = "8F22";
    private static final String SURVEY_COUNTRY = "United States";
    private static final String SURVEY_COUNTRY_DIFFERENT_CASE = "UnITed StaTES";
    private static final String SURVEY_COUNTRY_FRAGMENT = "Ted StaT";
    private static final String SURVEY_COUNTRY_WITH_METASYMBOLS = "PortuGAL !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final LocalDate PERIOD_END_DATE = LocalDate.of(2021, 12, 31);
    private static final LocalDate USAGE_DATE = LocalDate.of(2020, 12, 12);
    private static final LocalDate SURVEY_START_DATE = LocalDate.of(2019, 12, 12);
    private static final LocalDate SURVEY_END_DATE = LocalDate.of(2022, 12, 12);
    private static final Long WR_WRK_INST = 122825347L;
    private static final String REPORTED_TITLE = "The Wall Street journal";
    private static final String REPORTED_TITLE_DIFFERENT_CASE = "THe WAll STReet JOURnal";
    private static final String REPORTED_TITLE_FRAGMENT = "JOURnal";
    private static final String REPORTED_TITLE_WITH_METASYMBOLS = "The New York times !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final String SYSTEM_TITLE = "Wall Street journal";
    private static final String SYSTEM_TITLE_DIFFERENT_CASE = "WAll STReet JOURnal";
    private static final String SYSTEM_TITLE_FRAGMENT = "JOURnal";
    private static final String SYSTEM_TITLE_WITH_METASYMBOLS = "New York times !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final String USAGE_DETAIL_ID = "OGN674GHHSB0108";
    private static final String USAGE_DETAIL_ID_DIFFERENT_CASE = "ogn674ghhsb0108";
    private static final String USAGE_DETAIL_ID_FRAGMENT = "HSB0108";
    private static final Long COMPANY_ID = 454984566L;
    private static final String COMPANY_NAME_1 = "Skadden, Arps, Slate, Meagher & Flom LLP";
    private static final String COMPANY_NAME_2 = "Albany International Corp.";
    private static final String COMPANY_NAME_2_DIFFERENT_CASE = "AlbanY IntErNationAL CoRP.";
    private static final String COMPANY_NAME_2_FRAGMENT = "AlbanY IntEr";
    private static final String COMPANY_NAME_WITH_METASYMBOLS = "AlcoN LabORAtorIEs, Inc. !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final Long QUANTITY = 10L;
    private static final Integer ANNUAL_MULTIPLIER = 1;
    private static final BigDecimal STATISTICAL_MULTIPLIER = new BigDecimal("1.00000");
    private static final BigDecimal ANNUALIZED_COPIES = new BigDecimal("10.00000");
    // Have to use incorrect ip for testing purposes as PMD disallows hardcoded ips
    private static final String IP_ADDRESS = "ip24.12.119.203";
    private static final Long RH_ACCOUNT_NUMBER = 7000813806L;
    private static final String STANDARD_NUMBER = "2192-3558";
    private static final String DIGITAL = "DIGITAL";
    private static final String ASSIGNEE_1 = "wjohn@copyright.com";
    private static final String ASSIGNEE_2 = "jjohn@copyright.com";
    private static final String ASSIGNEE_3 = "ajohn@copyright.com";
    private static final String UNASSIGNED = "Unassigned";
    private static final String USER_NAME = "user@copyright.com";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String INVALID_VALUE = "Invalid value";

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Autowired
    private IUdmUsageRepository udmUsageRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "insert.groovy")
    public void testInsert() {
        UdmUsage usageToInsert = buildUdmUsage();
        udmUsageRepository.insert(usageToInsert);
        List<UdmUsage> udmUsages = udmUsageRepository.findByIds(List.of(UDM_USAGE_UID_1));
        assertEquals(1, udmUsages.size());
        UdmUsage udmUsage = udmUsages.get(0);
        assertNotNull(udmUsage);
        verifyUsage(usageToInsert, udmUsage);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update.groovy")
    public void testUpdate() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(Collections.singleton("2e92041d-42d1-44f2-b6bd-2e6e8a131831"));
        UdmUsageDto originalUsage = udmUsageRepository.findDtosByFilter(filter, null, null).get(0);
        originalUsage.setStatus(UsageStatusEnum.INELIGIBLE);
        originalUsage.setPeriod(202512);
        originalUsage.setPeriodEndDate(LocalDate.of(2025, 12, 31));
        originalUsage.setAssignee(ASSIGNEE_3);
        originalUsage.setWrWrkInst(WR_WRK_INST);
        originalUsage.setReportedTitle(REPORTED_TITLE);
        originalUsage.setReportedStandardNumber(STANDARD_NUMBER);
        originalUsage.setReportedPubType(PUB_TYPE_JOURNAL);
        originalUsage.setActionReason(
            new UdmActionReason("1c8f6e43-2ca8-468d-8700-ce855e6cd8c0", "Aggregated Content"));
        originalUsage.setComment("Specialist should review ineligible usage");
        originalUsage.setResearchUrl("google.com");
        DetailLicenseeClass detailLicenseeClass = buildDetailLicenseeClass(2);
        detailLicenseeClass.setDescription("Textiles, Apparel, etc.");
        originalUsage.setDetailLicenseeClass(detailLicenseeClass);
        originalUsage.setCompanyId(COMPANY_ID);
        originalUsage.setCompanyName(COMPANY_NAME_1);
        originalUsage.setAnnualMultiplier(1);
        originalUsage.setStatisticalMultiplier(new BigDecimal("2.00000"));
        originalUsage.setQuantity(3L);
        originalUsage.setAnnualizedCopies(new BigDecimal("6.00000"));
        originalUsage.setIneligibleReason(
            new UdmIneligibleReason("b60a726a-39e8-4303-abe1-6816da05b858", "Invalid survey"));
        udmUsageRepository.update(originalUsage);
        verifyUsageDto(originalUsage, udmUsageRepository.findDtosByFilter(filter, null, null).get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-ids-by-status.groovy")
    public void testFindIdsByStatus() {
        List<String> udmUsageIds = udmUsageRepository.findIdsByStatus(UsageStatusEnum.WORK_FOUND);
        assertEquals(2, udmUsageIds.size());
        assertTrue(udmUsageIds.contains("587dfefd-3cf8-4853-8b1b-d59b5cd163e9"));
        assertTrue(udmUsageIds.contains("987dfefd-3c24-4853-8b1c-aaab5cd163e1"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-ids.groovy")
    public void testFindByIds() {
        UdmUsage usageToInsert = loadExpectedUsage("json/udm/udm_usage_587dfefd.json").get(0);
        List<UdmUsage> actualUsages =
            udmUsageRepository.findByIds(List.of("587dfefd-3cf8-4853-8b1b-d59b5cd163e9"));
        assertEquals(1, actualUsages.size());
        verifyUsage(usageToInsert, actualUsages.get(0));
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByAllFilters() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(Collections.singleton(UDM_BATCH_UID_4));
        filter.setAssignees(Collections.singleton(ASSIGNEE_1));
        filter.setDetailLicenseeClasses(Collections.singleton(buildDetailLicenseeClass(22)));
        filter.setReportedPubTypes(Collections.singleton(PUB_TYPE_NOT_SHARED));
        filter.setReportedTypeOfUses(Collections.singleton(REPORTED_TYPE_OF_USE));
        filter.setTypeOfUse(TYPE_OF_USE);
        filter.setPubFormats(Collections.singleton(PUBLICATION_FORMAT));
        filter.setUsageDateFrom(LocalDate.of(2020, 4, 12));
        filter.setUsageDateTo(LocalDate.of(2020, 6, 20));
        filter.setSurveyStartDateFrom(LocalDate.of(2020, 3, 12));
        filter.setSurveyStartDateTo(LocalDate.of(2020, 5, 20));
        filter.setChannel(UdmChannelEnum.CCC);
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 227738245L, null));
        filter.setReportedTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, REPORTED_TITLE, null));
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        filter.setUsageDetailIdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID, null));
        filter.setCompanyIdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 1136L, null));
        filter.setCompanyNameExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, COMPANY_NAME_2, null));
        filter.setSurveyRespondentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_RESPONDENT,
            null));
        filter.setSurveyCountryExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY, null));
        filter.setLanguageExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, LANGUAGE, null));
        filter.setAnnualMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 25, null));
        filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 425, null));
        filter.setStatisticalMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 1, null));
        filter.setQuantityExpression(new FilterExpression<>(FilterOperatorEnum.BETWEEN, 2, 400));
        List<UdmUsageDto> usages = udmUsageRepository.findDtosByFilter(filter, null, buildSort());
        assertEquals(1, usages.size());
        verifyUsageDto(loadExpectedUsageDto("json/udm/udm_usage_dto_b989e02b.json").get(0), usages.get(0));
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterPeriods() {
        assertFilteringFindDtosByFilter(filter -> filter.setPeriods(Collections.singleton(202106)), UDM_USAGE_UID_5,
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterUsageStatus() {
        assertFilteringFindDtosByFilter(filter -> filter.setUsageStatus(UsageStatusEnum.RH_FOUND), UDM_USAGE_UID_5,
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterAssignees() {
        assertFilteringFindDtosByFilter(filter -> filter.setAssignees(Sets.newHashSet(ASSIGNEE_1, UNASSIGNED)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setAssignees(Collections.singleton(ASSIGNEE_1)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setAssignees(Collections.singleton(ASSIGNEE_2)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setAssignees(Collections.singleton(UNASSIGNED)),
            UDM_USAGE_UID_5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterReportedPubTypes() {
        assertFilteringFindDtosByFilter(
            filter -> filter.setReportedPubTypes(Collections.singleton(PUB_TYPE_NOT_SHARED)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterPubFormats() {
        assertFilteringFindDtosByFilter(filter -> filter.setPubFormats(Collections.singleton(PUBLICATION_FORMAT)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterDetailLicenseeClasses() {
        assertFilteringFindDtosByFilter(filter ->
            filter.setDetailLicenseeClasses(Collections.singleton(buildDetailLicenseeClass(2))), UDM_USAGE_UID_7);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterReportedTypeOfUses() {
        assertFilteringFindDtosByFilter(filter ->
                filter.setReportedTypeOfUses(Collections.singleton(REPORTED_TYPE_OF_USE)), UDM_USAGE_UID_5,
            UDM_USAGE_UID_6);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterTypeOfUses() {
        assertFilteringFindDtosByFilter(filter -> filter.setTypeOfUse(TYPE_OF_USE), UDM_USAGE_UID_5, UDM_USAGE_UID_6);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterChannel() {
        assertFilteringFindDtosByFilter(filter -> filter.setChannel(UdmChannelEnum.CCC), UDM_USAGE_UID_5,
            UDM_USAGE_UID_6, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterUsageDate() {
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDateFrom(LocalDate.of(2020, 5, 11)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDateTo(LocalDate.of(2020, 5, 10)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> {
            filter.setUsageDateFrom(LocalDate.of(2020, 4, 12));
            filter.setUsageDateTo(LocalDate.of(2020, 6, 20));
        }, UDM_USAGE_UID_5, UDM_USAGE_UID_6);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterSurveyStartDates() {
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyStartDateFrom(LocalDate.of(2020, 5, 20)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyStartDateTo(LocalDate.of(2020, 5, 20)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> {
            filter.setSurveyStartDateFrom(LocalDate.of(2020, 3, 12));
            filter.setSurveyStartDateTo(LocalDate.of(2020, 5, 20));
        }, UDM_USAGE_UID_5, UDM_USAGE_UID_6);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterWrWrkInst() {
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 321208892L, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, 321208892L, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 227738245L, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 227738245L, null)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 227738245L, null)),
            UDM_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 227738245L, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 227738245L, 321208892L)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)),
            UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterUsageDetailId() {
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, USAGE_DETAIL_ID, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, USAGE_DETAIL_ID_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, USAGE_DETAIL_ID_FRAGMENT, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, USAGE_DETAIL_ID, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, USAGE_DETAIL_ID_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, USAGE_DETAIL_ID_FRAGMENT, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterCompanyId() {
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 5374L, null)),
            UDM_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, 5374L, null)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 1138L, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 1138L, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 1138L, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 1138L, null)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 1138L, 5374L)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterCompanyName() {
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMPANY_NAME_2, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMPANY_NAME_2_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMPANY_NAME_2_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMPANY_NAME_WITH_METASYMBOLS, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMPANY_NAME_2, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMPANY_NAME_2_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMPANY_NAME_2_FRAGMENT, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMPANY_NAME_WITH_METASYMBOLS, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMPANY_NAME_2, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMPANY_NAME_2_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMPANY_NAME_2_FRAGMENT, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMPANY_NAME_WITH_METASYMBOLS, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)),
            UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterSurveyRespondentExpression() {
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_RESPONDENT, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_RESPONDENT_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_RESPONDENT_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_RESPONDENT, null)),
            UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_RESPONDENT_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_RESPONDENT_FRAGMENT, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_RESPONDENT, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_RESPONDENT_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_RESPONDENT_FRAGMENT, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)),
            UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterSurveyCountry() {
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY_WITH_METASYMBOLS, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_COUNTRY, null)),
            UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_COUNTRY_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_COUNTRY_FRAGMENT, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_COUNTRY_WITH_METASYMBOLS, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_COUNTRY, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_COUNTRY_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_COUNTRY_FRAGMENT, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_COUNTRY_WITH_METASYMBOLS, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterReportedTitle() {
        assertFilteringFindDtosByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, REPORTED_TITLE, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, REPORTED_TITLE_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, REPORTED_TITLE_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, REPORTED_TITLE_WITH_METASYMBOLS, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, REPORTED_TITLE, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, REPORTED_TITLE_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, REPORTED_TITLE_FRAGMENT, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, REPORTED_TITLE_WITH_METASYMBOLS, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, REPORTED_TITLE, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, REPORTED_TITLE_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, REPORTED_TITLE_FRAGMENT, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, REPORTED_TITLE_WITH_METASYMBOLS, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)),
            UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterSystemTitle() {
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_WITH_METASYMBOLS, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_FRAGMENT, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_WITH_METASYMBOLS, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_FRAGMENT, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_WITH_METASYMBOLS, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)),
            UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterLanguage() {
        assertFilteringFindDtosByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LANGUAGE, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LANGUAGE_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LANGUAGE_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LANGUAGE_WITH_METASYMBOLS, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LANGUAGE, null)),
            UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LANGUAGE_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LANGUAGE_FRAGMENT, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LANGUAGE_WITH_METASYMBOLS, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LANGUAGE, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LANGUAGE_DIFFERENT_CASE, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LANGUAGE_FRAGMENT, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LANGUAGE_WITH_METASYMBOLS, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)),
            UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterAnnualMultiplier() {
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 25, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, 25, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 12, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 12, null)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 12, null)),
            UDM_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 12, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 12, 25)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)),
            UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterAnnualizedCopies() {
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 102, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, 102, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 75, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 75, null)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 75, null)),
            UDM_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 75, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 75, 102)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)),
            UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterStatisticalMultiplier() {
        assertFilteringFindDtosByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 1, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, 1, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 0.5, null)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 0.5, null)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 0.5, null)),
            UDM_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 0.5, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 0.5, 1)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)),
            UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterQuantity() {
        assertFilteringFindDtosByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 17, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, 17, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 5, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 5, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 5, null)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 5, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_12);
        assertFilteringFindDtosByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 5, 17)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7, UDM_USAGE_UID_12);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterPeriods() {
        assertFilteringFindCountByFilter(filter -> filter.setPeriods(Collections.singleton(202106)), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterUsageStatus() {
        assertFilteringFindCountByFilter(filter -> filter.setUsageStatus(UsageStatusEnum.RH_FOUND), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterAssignees() {
        assertFilteringFindCountByFilter(filter -> filter.setAssignees(Sets.newHashSet(ASSIGNEE_1, UNASSIGNED)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setAssignees(Collections.singleton(ASSIGNEE_1)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setAssignees(Collections.singleton(ASSIGNEE_2)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setAssignees(Collections.singleton(UNASSIGNED)), 1);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterReportedPubTypes() {
        assertFilteringFindCountByFilter(
            filter -> filter.setReportedPubTypes(Collections.singleton(PUB_TYPE_NOT_SHARED)), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterPubFormats() {
        assertFilteringFindCountByFilter(filter -> filter.setPubFormats(Collections.singleton(PUBLICATION_FORMAT)), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterDetailLicenseeClasses() {
        assertFilteringFindCountByFilter(filter ->
            filter.setDetailLicenseeClasses(Collections.singleton(buildDetailLicenseeClass(2))), 1);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterReportedTypeOfUses() {
        assertFilteringFindCountByFilter(filter ->
            filter.setReportedTypeOfUses(Collections.singleton(REPORTED_TYPE_OF_USE)), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterTypeOfUses() {
        assertFilteringFindCountByFilter(filter -> filter.setTypeOfUse(TYPE_OF_USE), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterChannel() {
        assertFilteringFindCountByFilter(filter -> filter.setChannel(UdmChannelEnum.CCC), 4);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterUsageDates() {
        assertFilteringFindCountByFilter(filter -> filter.setUsageDateFrom(LocalDate.of(2020, 5, 20)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDateTo(LocalDate.of(2020, 5, 20)), 3);
        assertFilteringFindCountByFilter(filter -> {
            filter.setUsageDateFrom(LocalDate.of(2020, 4, 12));
            filter.setUsageDateTo(LocalDate.of(2020, 6, 20));
        }, 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterSurveyStartDates() {
        assertFilteringFindCountByFilter(filter -> filter.setSurveyStartDateFrom(LocalDate.of(2020, 5, 20)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyStartDateTo(LocalDate.of(2020, 5, 20)), 3);
        assertFilteringFindCountByFilter(filter -> {
            filter.setSurveyStartDateFrom(LocalDate.of(2020, 3, 12));
            filter.setSurveyStartDateTo(LocalDate.of(2020, 5, 20));
        }, 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterWrWrkInst() {
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 321208892L, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, 321208892L, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 227738245L, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 227738245L, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 227738245L, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 227738245L, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 227738245L, 321208892L)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterReportedTitle() {
        assertFilteringFindCountByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, REPORTED_TITLE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, REPORTED_TITLE_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, REPORTED_TITLE_FRAGMENT, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, REPORTED_TITLE_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, REPORTED_TITLE, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, REPORTED_TITLE_DIFFERENT_CASE, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, REPORTED_TITLE_FRAGMENT, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, REPORTED_TITLE_WITH_METASYMBOLS, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, REPORTED_TITLE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, REPORTED_TITLE_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, REPORTED_TITLE_FRAGMENT, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, REPORTED_TITLE_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setReportedTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 3);
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
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 3);
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
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, USAGE_DETAIL_ID, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, USAGE_DETAIL_ID_DIFFERENT_CASE, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, USAGE_DETAIL_ID_FRAGMENT, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, USAGE_DETAIL_ID, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, USAGE_DETAIL_ID_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, USAGE_DETAIL_ID_FRAGMENT, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDetailIdExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 4);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterCompanyId() {
        assertFilteringFindCountByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 5374L, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, 5374L, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 1138L, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 1138L, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 1138L, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 1138L, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 1138L, 5374L)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyIdExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 4);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterCompanyName() {
        assertFilteringFindCountByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMPANY_NAME_2, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMPANY_NAME_2_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMPANY_NAME_2_FRAGMENT, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMPANY_NAME_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMPANY_NAME_2, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMPANY_NAME_2_DIFFERENT_CASE, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMPANY_NAME_2_FRAGMENT, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMPANY_NAME_WITH_METASYMBOLS, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMPANY_NAME_2, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMPANY_NAME_2_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMPANY_NAME_2_FRAGMENT, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMPANY_NAME_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyNameExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterSurverRespondent() {
        assertFilteringFindCountByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_RESPONDENT, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_RESPONDENT_DIFFERENT_CASE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_RESPONDENT_FRAGMENT, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_RESPONDENT, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_RESPONDENT_DIFFERENT_CASE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SURVEY_RESPONDENT_FRAGMENT, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_RESPONDENT, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_RESPONDENT_DIFFERENT_CASE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SURVEY_RESPONDENT_FRAGMENT, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyRespondentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 3);
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
        assertFilteringFindCountByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyCountryExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 4);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterLanguage() {
        assertFilteringFindCountByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LANGUAGE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LANGUAGE_DIFFERENT_CASE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LANGUAGE_FRAGMENT, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LANGUAGE_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LANGUAGE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LANGUAGE_DIFFERENT_CASE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LANGUAGE_FRAGMENT, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LANGUAGE_WITH_METASYMBOLS, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LANGUAGE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LANGUAGE_DIFFERENT_CASE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LANGUAGE_FRAGMENT, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LANGUAGE_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setLanguageExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterAnnualMultiplier() {
        assertFilteringFindCountByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 25, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, 25, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 12, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 12, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 12, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 12, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 12, 25)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterAnnualizedCopies() {
        assertFilteringFindCountByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 102, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, 102, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 75, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 75, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 75, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 75, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 75, 102)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterStatisticalMultiplier() {
        assertFilteringFindCountByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 1, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, 1, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 0.5, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 0.5, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 0.5, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 0.5, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 0.5, 1)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterQuantity() {
        assertFilteringFindCountByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 17, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, 17, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 5, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 5, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 5, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 5, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 5, 17)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setQuantityExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 4);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "is-original-detail-id-exist.groovy")
    public void testSortingFindDtosByFilter() {
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "detailId");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "period");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "usageOrigin");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "usageDetailId");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "status");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "assignee");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "rhAccountNumber");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "rhName");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "wrWrkInst");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "reportedTitle");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "systemTitle");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "reportedStandardNumber");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "standardNumber");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "reportedPubType");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "publicationFormat");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "article");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "language");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "actionReason");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "comment");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "researchUrl");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "companyId");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "companyName");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "detLcId");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "detLcName");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "surveyRespondent");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "ipAddress");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "surveyCountry");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "channel");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "usageDate");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "surveyStartDate");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "surveyEndDate");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "annualMultiplier");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "statisticalMultiplier");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "reportedTypeOfUse");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "typeOfUse");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "quantity");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "annualizedCopies");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "ineligibleReason");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "createDate");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "updateDate");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "updateUser");
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByAllFilters() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(Collections.singleton(UDM_BATCH_UID_4));
        filter.setAssignees(Collections.singleton(ASSIGNEE_1));
        filter.setDetailLicenseeClasses(Collections.singleton(buildDetailLicenseeClass(22)));
        filter.setReportedPubTypes(Collections.singleton(PUB_TYPE_NOT_SHARED));
        filter.setReportedTypeOfUses(Collections.singleton(REPORTED_TYPE_OF_USE));
        filter.setTypeOfUse(TYPE_OF_USE);
        filter.setPubFormats(Collections.singleton(PUBLICATION_FORMAT));
        filter.setUsageDateFrom(LocalDate.of(2020, 4, 12));
        filter.setUsageDateTo(LocalDate.of(2020, 6, 20));
        filter.setSurveyStartDateFrom(LocalDate.of(2020, 3, 12));
        filter.setSurveyStartDateTo(LocalDate.of(2020, 5, 20));
        filter.setChannel(UdmChannelEnum.CCC);
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 227738245L, null));
        filter.setReportedTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, REPORTED_TITLE, null));
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        filter.setUsageDetailIdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID, null));
        filter.setCompanyIdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 1136L, null));
        filter.setCompanyNameExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, COMPANY_NAME_2, null));
        filter.setSurveyRespondentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_RESPONDENT,
            null));
        filter.setSurveyCountryExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY, null));
        filter.setLanguageExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, LANGUAGE, null));
        filter.setAnnualMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 25, null));
        filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 425, null));
        filter.setStatisticalMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 1, null));
        filter.setQuantityExpression(new FilterExpression<>(FilterOperatorEnum.BETWEEN, 2, 400));
        assertEquals(1, udmUsageRepository.findCountByFilter(filter));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "is-original-detail-id-exist.groovy")
    public void testIsOriginalDetailIdExist() {
        assertTrue(udmUsageRepository.isOriginalDetailIdExist("OGN674GHHSB001"));
        assertFalse(udmUsageRepository.isOriginalDetailIdExist("OGN674GHHSB101"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-processed-usage.groovy")
    public void testUpdateProcessedUsage() {
        List<UdmUsage> udmUsages = udmUsageRepository.findByIds(List.of(UDM_USAGE_UID_2));
        assertEquals(1, CollectionUtils.size(udmUsages));
        UdmUsage udmUsage = udmUsages.get(0);
        udmUsage.setStatus(UsageStatusEnum.RH_FOUND);
        udmUsage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        udmUsage.setWrWrkInst(5697789789L);
        udmUsage.setSystemTitle("Wissenschaft & Forschung France");
        udmUsage.setStandardNumber(STANDARD_NUMBER);
        assertNotNull(udmUsageRepository.updateProcessedUsage(udmUsage));
        List<UdmUsage> updatedUdmUsages = udmUsageRepository.findByIds(List.of(UDM_USAGE_UID_2));
        assertEquals(1, CollectionUtils.size(updatedUdmUsages));
        UdmUsage updatedUdmUsage = updatedUdmUsages.get(0);
        assertEquals(RH_ACCOUNT_NUMBER, updatedUdmUsage.getRightsholder().getAccountNumber());
        assertEquals(UsageStatusEnum.RH_FOUND, updatedUdmUsage.getStatus());
        assertEquals(STANDARD_NUMBER, updatedUdmUsage.getStandardNumber());
        assertEquals("Wissenschaft & Forschung France", updatedUdmUsage.getSystemTitle());
    }

    @Test
    @TestData(fileName = FIND_FIELDS)
    public void testFindUserNames() {
        List<String> expectedUserNames = Arrays.asList(ASSIGNEE_3, ASSIGNEE_2, ASSIGNEE_1);
        List<String> actualUserNames = udmUsageRepository.findUserNames();
        assertFalse(actualUserNames.isEmpty());
        assertEquals(expectedUserNames, actualUserNames);
    }

    @Test
    @TestData(fileName = FIND_FIELDS)
    public void testFindPeriods() {
        List<Integer> expectedPeriods = Arrays.asList(202106, 202012, 202006);
        List<Integer> actualPeriods = udmUsageRepository.findPeriods();
        assertFalse(actualPeriods.isEmpty());
        assertEquals(expectedPeriods, actualPeriods);
    }

    @Test
    @TestData(fileName = FIND_FIELDS)
    public void testFindAssignees() {
        assertEquals(Arrays.asList("jjohn@copyright.com", "wjohn@copyright.com"),
            udmUsageRepository.findAssignees());
    }

    @Test
    @TestData(fileName = FIND_FIELDS)
    public void testFindPublicationTypes() {
        assertEquals(Arrays.asList("Book", PUB_TYPE_NOT_SHARED), udmUsageRepository.findPublicationTypes());
    }

    @Test
    @TestData(fileName = FIND_FIELDS)
    public void testFindPublicationFormats() {
        assertEquals(Arrays.asList("Digital", "Not Specified"), udmUsageRepository.findPublicationFormats());
    }

    @Test
    @TestData(fileName = FIND_BY_SEARCH_VALUE)
    public void testSearchByReportedTitle() {
        verifyFindBySearchValue("Brain surgery", UDM_USAGE_UID_8);
        verifyFindBySearchValue("ain surg", UDM_USAGE_UID_8);
        verifyFindBySearchValue("Bra in");
        verifyFindBySearchValue(INVALID_VALUE);
        verifyFindBySearchValue("ADVANCES", UDM_USAGE_UID_9);
        verifyFindBySearchValue("advances", UDM_USAGE_UID_9);
        verifyFindBySearchValue("aDvaNCes", UDM_USAGE_UID_9);
    }

    @Test
    @TestData(fileName = FIND_BY_SEARCH_VALUE)
    public void testSearchBySystemTitle() {
        verifyFindBySearchValue("Castanea and surgery. C, Biointerfaces", UDM_USAGE_UID_8);
        verifyFindBySearchValue("Castanea an", UDM_USAGE_UID_8);
        verifyFindBySearchValue("Cast anea");
        verifyFindBySearchValue(INVALID_VALUE);
        verifyFindBySearchValue("TRANSACTION", UDM_USAGE_UID_9);
        verifyFindBySearchValue("transaction", UDM_USAGE_UID_9);
        verifyFindBySearchValue("TrAnSaCtiON", UDM_USAGE_UID_9);
    }

    @Test
    @TestData(fileName = FIND_BY_SEARCH_VALUE)
    public void testSearchByUsageDetailId() {
        verifyFindBySearchValue("OGN674GHHSB0110", UDM_USAGE_UID_8);
        verifyFindBySearchValue("OGN674GHHSB011", UDM_USAGE_UID_8, UDM_USAGE_UID_9);
        verifyFindBySearchValue("OGN674G HHSB0110");
        verifyFindBySearchValue(INVALID_VALUE);
        verifyFindBySearchValue("ogn674ghhsb011", UDM_USAGE_UID_8, UDM_USAGE_UID_9);
        verifyFindBySearchValue("Ogn674gHHsb011", UDM_USAGE_UID_8, UDM_USAGE_UID_9);
    }

    @Test
    @TestData(fileName = FIND_BY_SEARCH_VALUE)
    public void testSearchByStandardNumber() {
        verifyFindBySearchValue("100891123776UXX", UDM_USAGE_UID_8);
        verifyFindBySearchValue("23776UXX", UDM_USAGE_UID_8);
        verifyFindBySearchValue("1008911 23776UXX");
        verifyFindBySearchValue(INVALID_VALUE);
        verifyFindBySearchValue("100891123776YXX", UDM_USAGE_UID_9);
        verifyFindBySearchValue("100891123776yxx", UDM_USAGE_UID_9);
        verifyFindBySearchValue("100891123776yXx", UDM_USAGE_UID_9);
    }

    @Test
    @TestData(fileName = FIND_BY_SEARCH_VALUE)
    public void testSearchByArticle() {
        verifyFindBySearchValue("Appendix: The Principles of Newspeak", UDM_USAGE_UID_8, UDM_USAGE_UID_9);
        verifyFindBySearchValue("Appendix: The", UDM_USAGE_UID_8, UDM_USAGE_UID_9);
        verifyFindBySearchValue("Appe ndix");
        verifyFindBySearchValue(INVALID_VALUE);
        verifyFindBySearchValue("NEWSPEAK", UDM_USAGE_UID_8, UDM_USAGE_UID_9);
        verifyFindBySearchValue("newspeak", UDM_USAGE_UID_8, UDM_USAGE_UID_9);
        verifyFindBySearchValue("NeWsPeAK", UDM_USAGE_UID_8, UDM_USAGE_UID_9);
    }

    @Test
    @TestData(fileName = FIND_BY_SEARCH_VALUE)
    public void testSearchBySurveyRespondent() {
        verifyFindBySearchValue("c986xxxx-19c3-4530-8ffc-zzzzzz000000", UDM_USAGE_UID_8);
        verifyFindBySearchValue("c986xxxx-19c3-4530-8ffc", UDM_USAGE_UID_8, UDM_USAGE_UID_9);
        verifyFindBySearchValue("c986xxxx-19 c3-4530-8ffc");
        verifyFindBySearchValue(INVALID_VALUE);
        verifyFindBySearchValue("C986XXXX-19C3-4530-8FFC-ZZZZZZ111111", UDM_USAGE_UID_9);
        verifyFindBySearchValue("c986xxxx-19c3-4530-8ffc-", UDM_USAGE_UID_8, UDM_USAGE_UID_9);
        verifyFindBySearchValue("C986XXxx-19C3-4530-8fFc-", UDM_USAGE_UID_8, UDM_USAGE_UID_9);
    }

    @Test
    @TestData(fileName = FIND_BY_SEARCH_VALUE)
    public void testSearchByComment() {
        verifyFindBySearchValue("UDM search comment 1", UDM_USAGE_UID_8);
        verifyFindBySearchValue("UDM searc", UDM_USAGE_UID_8, UDM_USAGE_UID_9);
        verifyFindBySearchValue("com ment");
        verifyFindBySearchValue(INVALID_VALUE);
        verifyFindBySearchValue("UDM SEARCH", UDM_USAGE_UID_8, UDM_USAGE_UID_9);
        verifyFindBySearchValue("udm search", UDM_USAGE_UID_8, UDM_USAGE_UID_9);
        verifyFindBySearchValue("UdM sEaRcH", UDM_USAGE_UID_8, UDM_USAGE_UID_9);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-status-by-ids.groovy")
    public void testUpdateStatusByIds() {
        List<UdmUsage> udmUsages = udmUsageRepository.findByIds(Arrays.asList(UDM_USAGE_UID_10, UDM_USAGE_UID_11));
        assertEquals(2, udmUsages.size());
        UdmUsage udmUsage1 = udmUsages.get(0);
        assertEquals(UsageStatusEnum.NEW, udmUsage1.getStatus());
        assertEquals(USER_NAME, udmUsage1.getUpdateUser());
        UdmUsage udmUsage2 = udmUsages.get(1);
        assertEquals(UsageStatusEnum.NEW, udmUsage2.getStatus());
        assertEquals(USER_NAME, udmUsage2.getUpdateUser());
        udmUsageRepository.updateStatusByIds(Sets.newHashSet(udmUsage1.getId(), udmUsage2.getId()),
            UsageStatusEnum.WORK_FOUND);
        udmUsages = udmUsageRepository.findByIds(Arrays.asList(UDM_USAGE_UID_10, UDM_USAGE_UID_11));
        assertEquals(2, udmUsages.size());
        udmUsage1 = udmUsages.get(0);
        assertEquals(UsageStatusEnum.WORK_FOUND, udmUsage1.getStatus());
        assertEquals(StoredEntity.DEFAULT_USER, udmUsage1.getUpdateUser());
        udmUsage2 = udmUsages.get(1);
        assertEquals(UsageStatusEnum.WORK_FOUND, udmUsage2.getStatus());
        assertEquals(StoredEntity.DEFAULT_USER, udmUsage2.getUpdateUser());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-by-batch-id.groovy")
    public void testDeleteByBatchId() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(Collections.singleton("2e92041d-42d1-44f2-b6bd-2e6e8a131831"));
        assertEquals(1, udmUsageRepository.findCountByFilter(filter));
        udmUsageRepository.deleteByBatchId("2e92041d-42d1-44f2-b6bd-2e6e8a131831");
        assertEquals(0, udmUsageRepository.findCountByFilter(filter));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-assignee.groovy")
    public void testUpdateAssignee() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setSearchValue("OGN674GHHHB0117");
        UdmUsageDto udmUsagedto = udmUsageRepository.findDtosByFilter(filter, null, null).get(0);
        assertNull(udmUsagedto.getAssignee());
        udmUsageRepository
            .updateAssignee(Collections.singleton("22241298-5c9e-4222-8fc1-5ee80c0e48f1"), ASSIGNEE_3, USER_NAME);
        udmUsagedto = udmUsageRepository.findDtosByFilter(filter, null, null).get(0);
        assertEquals(ASSIGNEE_3, udmUsagedto.getAssignee());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-assignee.groovy")
    public void testUpdateAssigneeToNull() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setSearchValue("OGN674GHHHB0116");
        UdmUsageDto udmUsagedto = udmUsageRepository.findDtosByFilter(filter, null, null).get(0);
        assertEquals(ASSIGNEE_1, udmUsagedto.getAssignee());
        udmUsageRepository
            .updateAssignee(Collections.singleton("d247235f-3595-4b74-b091-f6f91fba2e7d"), null, USER_NAME);
        udmUsagedto = udmUsageRepository.findDtosByFilter(filter, null, null).get(0);
        assertNull(udmUsagedto.getAssignee());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "publish-udm-usages-to-baseline.groovy")
    public void testPublishUdmUsageToBaseline() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(Collections.singleton("9608ee69-ea5d-4a80-b31d-399514a4f51e"));
        List<UdmUsageDto> usageDtos = udmUsageRepository.findDtosByFilter(filter, null, null);
        assertEquals(3, usageDtos.size());
        usageDtos.forEach(usageDto -> assertFalse(usageDto.isBaselineFlag()));
        Set<String> udmUsageIds = udmUsageRepository.publishUdmUsagesToBaseline(202106, USER_NAME);
        assertEquals(1, udmUsageIds.size());
        filter.setPeriods(Collections.singleton(202106));
        assertEquals(1, udmUsageRepository.findDtosByFilter(filter, null, null)
            .stream()
            .filter(UdmUsageDto::isBaselineFlag)
            .count());
    }

    private void verifyFindBySearchValue(String searchValue, String... udmUsageIds) {
        List<String> expectedUdmUsageIds = Arrays.stream(udmUsageIds)
            .sorted()
            .collect(Collectors.toList());
        int expectedSize = expectedUdmUsageIds.size();
        UdmUsageFilter udmUsageFilter = new UdmUsageFilter();
        udmUsageFilter.setSearchValue(searchValue);
        assertEquals(expectedSize, udmUsageRepository.findCountByFilter(udmUsageFilter));
        List<UdmUsageDto> udmUsageDtos = udmUsageRepository.findDtosByFilter(udmUsageFilter, null, null);
        assertNotNull(udmUsageDtos);
        assertEquals(expectedSize, udmUsageDtos.size());
        List<String> actualIds = udmUsageDtos.stream()
            .map(UdmUsageDto::getId)
            .sorted()
            .collect(Collectors.toList());
        assertEquals(expectedUdmUsageIds, actualIds);
    }

    private void assertSortingFindDtosByFilter(String detailIdAsc, String detailIdDesc, String sortProperty) {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(Sets.newHashSet(UDM_BATCH_UID_2, UDM_BATCH_UID_3));
        List<UdmUsageDto> usageDtos =
            udmUsageRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Direction.ASC));
        assertEquals(detailIdAsc, usageDtos.get(0).getId());
        usageDtos =
            udmUsageRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Direction.DESC));
        assertEquals(detailIdDesc, usageDtos.get(0).getId());
    }

    private void assertFilteringFindDtosByFilter(Consumer<UdmUsageFilter> consumer, String... usageIds) {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(Collections.singleton(UDM_BATCH_UID_4));
        consumer.accept(filter);
        List<UdmUsageDto> usages = udmUsageRepository.findDtosByFilter(filter, null, buildSort());
        assertEquals(usageIds.length, usages.size());
        IntStream.range(0, usageIds.length)
            .forEach(index -> assertEquals(usageIds[index], usages.get(index).getId()));
    }

    private void assertFilteringFindCountByFilter(Consumer<UdmUsageFilter> consumer, int count) {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(Collections.singleton(UDM_BATCH_UID_4));
        consumer.accept(filter);
        int usagesCount = udmUsageRepository.findCountByFilter(filter);
        assertEquals(count, usagesCount);
    }

    private void verifyUsageDto(UdmUsageDto expectedUsage, UdmUsageDto actualUsage) {
        assertEquals(expectedUsage.getId(), actualUsage.getId());
        assertEquals(expectedUsage.getPeriod(), actualUsage.getPeriod());
        assertEquals(expectedUsage.getUsageOrigin(), actualUsage.getUsageOrigin());
        assertEquals(expectedUsage.getChannel(), actualUsage.getChannel());
        assertEquals(expectedUsage.getOriginalDetailId(), actualUsage.getOriginalDetailId());
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getUsageDate(), actualUsage.getUsageDate());
        assertEquals(expectedUsage.getAssignee(), actualUsage.getAssignee());
        assertEquals(expectedUsage.getRhAccountNumber(), actualUsage.getRhAccountNumber());
        assertEquals(expectedUsage.getRhName(), actualUsage.getRhName());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getReportedStandardNumber(), actualUsage.getReportedStandardNumber());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getReportedTitle(), actualUsage.getReportedTitle());
        assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
        assertEquals(expectedUsage.getReportedPubType(), actualUsage.getReportedPubType());
        assertEquals(expectedUsage.getPubFormat(), actualUsage.getPubFormat());
        assertEquals(expectedUsage.getArticle(), actualUsage.getArticle());
        assertEquals(expectedUsage.getLanguage(), actualUsage.getLanguage());
        assertEquals(expectedUsage.getCompanyId(), actualUsage.getCompanyId());
        assertEquals(expectedUsage.getCompanyName(), actualUsage.getCompanyName());
        assertEquals(expectedUsage.getDetailLicenseeClass().getId(), actualUsage.getDetailLicenseeClass().getId());
        assertEquals(expectedUsage.getDetailLicenseeClass().getDescription(),
            actualUsage.getDetailLicenseeClass().getDescription());
        assertEquals(expectedUsage.getSurveyRespondent(), actualUsage.getSurveyRespondent());
        assertEquals(expectedUsage.getIpAddress(), actualUsage.getIpAddress());
        assertEquals(expectedUsage.getSurveyCountry(), actualUsage.getSurveyCountry());
        assertEquals(expectedUsage.getSurveyStartDate(), actualUsage.getSurveyStartDate());
        assertEquals(expectedUsage.getStatisticalMultiplier(), actualUsage.getStatisticalMultiplier());
        assertEquals(expectedUsage.getAnnualMultiplier(), actualUsage.getAnnualMultiplier());
        assertEquals(expectedUsage.getReportedTypeOfUse(), actualUsage.getReportedTypeOfUse());
        assertEquals(expectedUsage.getTypeOfUse(), actualUsage.getTypeOfUse());
        assertEquals(expectedUsage.getAnnualizedCopies(), actualUsage.getAnnualizedCopies());
        assertEquals(expectedUsage.getQuantity(), actualUsage.getQuantity());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        assertEquals(expectedUsage.getResearchUrl(), actualUsage.getResearchUrl());
        assertEquals(expectedUsage.getIneligibleReason(), actualUsage.getIneligibleReason());
    }

    private void verifyUsage(UdmUsage expectedUsage, UdmUsage actualUsage) {
        assertEquals(expectedUsage.getId(), actualUsage.getId());
        assertEquals(expectedUsage.getBatchId(), actualUsage.getBatchId());
        assertEquals(expectedUsage.getOriginalDetailId(), actualUsage.getOriginalDetailId());
        assertEquals(expectedUsage.getPeriodEndDate(), actualUsage.getPeriodEndDate());
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getRightsholder().getAccountNumber(),
            actualUsage.getRightsholder().getAccountNumber());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getReportedStandardNumber(), actualUsage.getReportedStandardNumber());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getReportedTitle(), actualUsage.getReportedTitle());
        assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
        assertEquals(expectedUsage.getReportedPubType(), actualUsage.getReportedPubType());
        assertEquals(expectedUsage.getPubFormat(), actualUsage.getPubFormat());
        assertEquals(expectedUsage.getArticle(), actualUsage.getArticle());
        assertEquals(expectedUsage.getLanguage(), actualUsage.getLanguage());
        assertEquals(expectedUsage.getCompanyId(), actualUsage.getCompanyId());
        assertEquals(expectedUsage.getCompanyName(), actualUsage.getCompanyName());
        assertEquals(expectedUsage.getDetailLicenseeClassId(), actualUsage.getDetailLicenseeClassId());
        assertEquals(expectedUsage.getSurveyRespondent(), actualUsage.getSurveyRespondent());
        assertEquals(expectedUsage.getIpAddress(), actualUsage.getIpAddress());
        assertEquals(expectedUsage.getSurveyCountry(), actualUsage.getSurveyCountry());
        assertEquals(expectedUsage.getUsageDate(), actualUsage.getUsageDate());
        assertEquals(expectedUsage.getSurveyStartDate(), actualUsage.getSurveyStartDate());
        assertEquals(expectedUsage.getStatisticalMultiplier(), actualUsage.getStatisticalMultiplier());
        assertEquals(expectedUsage.getAnnualMultiplier(), actualUsage.getAnnualMultiplier());
        assertEquals(expectedUsage.getReportedTypeOfUse(), actualUsage.getReportedTypeOfUse());
        assertEquals(expectedUsage.getTypeOfUse(), actualUsage.getTypeOfUse());
        assertEquals(expectedUsage.getAnnualizedCopies(), actualUsage.getAnnualizedCopies());
        assertEquals(expectedUsage.getQuantity(), actualUsage.getQuantity());
        assertEquals(expectedUsage.getVersion(), actualUsage.getVersion());
        assertNull(actualUsage.getIneligibleReasonId());
    }

    private UdmUsage buildUdmUsage() {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setId(UDM_USAGE_UID_1);
        udmUsage.setBatchId(UDM_BATCH_UID_1);
        udmUsage.setOriginalDetailId(UDM_USAGE_ORIGINAL_DETAIL_UID);
        udmUsage.setStatus(UsageStatusEnum.NEW);
        udmUsage.setPeriod(202112);
        udmUsage.setPeriodEndDate(PERIOD_END_DATE);
        udmUsage.setUsageDate(USAGE_DATE);
        udmUsage.setWrWrkInst(WR_WRK_INST);
        udmUsage.setReportedStandardNumber(REPORTED_STANDARD_NUMBER);
        udmUsage.setReportedTitle(REPORTED_TITLE);
        udmUsage.setReportedPubType(PUB_TYPE_JOURNAL);
        udmUsage.setPubFormat(PUBLICATION_FORMAT);
        udmUsage.setArticle(ARTICLE);
        udmUsage.setLanguage(LANGUAGE);
        udmUsage.setCompanyId(COMPANY_ID);
        udmUsage.setCompanyName(COMPANY_NAME_1);
        udmUsage.setDetailLicenseeClassId(1);
        udmUsage.setSurveyRespondent(SURVEY_RESPONDENT);
        udmUsage.setIpAddress(IP_ADDRESS);
        udmUsage.setSurveyCountry(SURVEY_COUNTRY);
        udmUsage.setSurveyStartDate(SURVEY_START_DATE);
        udmUsage.setSurveyEndDate(SURVEY_END_DATE);
        udmUsage.setAnnualMultiplier(ANNUAL_MULTIPLIER);
        udmUsage.setStatisticalMultiplier(STATISTICAL_MULTIPLIER);
        udmUsage.setAnnualizedCopies(ANNUALIZED_COPIES);
        udmUsage.setTypeOfUse(DIGITAL);
        udmUsage.setReportedTypeOfUse(REPORTED_TYPE_OF_USE);
        udmUsage.setQuantity(QUANTITY);
        return udmUsage;
    }

    private Sort buildSort() {
        return new Sort("detailId", Sort.Direction.ASC);
    }

    private DetailLicenseeClass buildDetailLicenseeClass(Integer id) {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(id);
        return detailLicenseeClass;
    }

    private List<UdmUsage> loadExpectedUsage(String fileName) {
        List<UdmUsage> usages = new ArrayList<>();
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            usages.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<UdmUsage>>() {
            }));
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return usages;
    }

    private List<UdmUsageDto> loadExpectedUsageDto(String fileName) {
        List<UdmUsageDto> udmUsageDtos = new ArrayList<>();
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            udmUsageDtos.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<UdmUsageDto>>() {
            }));
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return udmUsageDtos;
    }
}
