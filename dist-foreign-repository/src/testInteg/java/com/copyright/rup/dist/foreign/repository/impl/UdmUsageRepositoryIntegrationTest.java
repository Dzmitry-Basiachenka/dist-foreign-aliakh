package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
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
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=udm-usage-repository-test-data-init.groovy"})
@Transactional
public class UdmUsageRepositoryIntegrationTest {

    private static final String UDM_USAGE_ORIGINAL_DETAIL_UID = "OGN674GHHSB0025";
    private static final String UDM_USAGE_UID_1 = "92cf072e-d6d7-4a23-9652-5ee7bca71313";
    private static final String UDM_USAGE_UID_2 = "0dfba00c-6d72-4a0e-8f84-f7c365432f85";
    private static final String UDM_USAGE_UID_3 = "cc3269aa-2f56-21c7-b0d1-34dd0edfcf5a";
    private static final String UDM_USAGE_UID_4 = "847dfefd-3cf8-4853-8b1b-d59b5cd163e9";
    private static final String UDM_USAGE_UID_5 = "9c7f64a7-95c1-4825-ad85-ff0672d252c4";
    private static final String UDM_USAGE_UID_6 = "b989e02b-1f1d-4637-b89e-dc99938a51b9";
    private static final String UDM_USAGE_UID_7 = "c3e3082f-9c3e-4e14-9640-c485a9eae24f";
    private static final String UDM_BATCH_UID_1 = "aa5751aa-2858-38c6-b0d9-51ec0edfcf4f";
    private static final String UDM_BATCH_UID_2 = "bb5751aa-2f56-38c6-b0d9-45ec0edfcf4a";
    private static final String UDM_BATCH_UID_3 = "4b6055be-fc4e-4b49-aeab-28563366c9fd";
    private static final String UDM_BATCH_UID_4 = "80452178-e250-415f-b3e4-71a48ca3e218";
    private static final String SURVEY_RESPONDENT = "fa0276c3-55d6-42cd-8ffe-e9124acae02f";
    private static final String REPORTED_STANDARD_NUMBER = "0927-7765";
    private static final String REPORTED_TYPE_OF_USE = "COPY_FOR_MYSELF";
    private static final String REPORTED_TITLE = "Colloids and surfaces. B, Biointerfaces";
    private static final String PUB_TYPE_JOURNAL = "Journal";
    private static final String PUB_TYPE_NOT_SHARED = "Not Shared";
    private static final String PUBLICATION_FORMAT = "Digital";
    private static final String ARTICLE = "Green chemistry";
    private static final String LANGUAGE = "English";
    private static final String SURVEY_COUNTRY = "United States";
    private static final LocalDate PERIOD_END_DATE = LocalDate.of(2021, 12, 12);
    private static final LocalDate USAGE_DATE = LocalDate.of(2020, 12, 12);
    private static final LocalDate SURVEY_START_DATE = LocalDate.of(2019, 12, 12);
    private static final LocalDate SURVEY_END_DATE = LocalDate.of(2022, 12, 12);
    private static final Long WR_WRK_INST = 122825347L;
    private static final Long COMPANY_ID = 454984566L;
    private static final String COMPANY_NAME_1 = "Skadden, Arps, Slate, Meagher & Flom LLP";
    private static final String COMPANY_NAME_2 = "Albany International Corp.";
    private static final Integer QUANTITY = 10;
    private static final Integer ANNUAL_MULTIPLIER = 1;
    private static final BigDecimal STATISTICAL_MULTIPLIER = new BigDecimal("1.00000");
    private static final BigDecimal ANNUALIZED_COPIES = new BigDecimal("10.00000");
    // Have to use incorrect ip for testing purposes as PMD disallows hardcoded ips
    private static final String IP_ADDRESS = "ip24.12.119.203";
    private static final Long RH_ACCOUNT_NUMBER = 7000813806L;
    private static final String STANDARD_NUMBER = "2192-3558";
    private static final String DIGITAL = "DIGITAL";
    private static final String ASSIGNEE = "wjohn@copyright.com";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Autowired
    private IUdmUsageRepository udmUsageRepository;

    @Test
    public void testInsert() {
        UdmUsage usageToInsert = buildUdmUsage();
        udmUsageRepository.insert(usageToInsert);
        List<UdmUsage> udmUsages = udmUsageRepository.findByIds(Collections.singletonList(UDM_USAGE_UID_1));
        assertEquals(1, udmUsages.size());
        UdmUsage udmUsage = udmUsages.get(0);
        assertNotNull(udmUsage);
        verifyUsage(usageToInsert, udmUsage);
    }

    @Test
    public void testFindIdsByStatus() {
        List<String> udmUsageIds = udmUsageRepository.findIdsByStatus(UsageStatusEnum.WORK_FOUND);
        assertEquals(2, udmUsageIds.size());
        assertTrue(udmUsageIds.contains("587dfefd-3cf8-4853-8b1b-d59b5cd163e9"));
        assertTrue(udmUsageIds.contains("987dfefd-3c24-4853-8b1c-aaab5cd163e1"));
    }

    @Test
    public void testFindByIds() {
        UdmUsage usageToInsert = loadExpectedUsage("json/udm/udm_usage_587dfefd.json").get(0);
        List<UdmUsage> actualUsages =
            udmUsageRepository.findByIds(Collections.singletonList("587dfefd-3cf8-4853-8b1b-d59b5cd163e9"));
        assertEquals(1, actualUsages.size());
        verifyUsage(usageToInsert, actualUsages.get(0));
    }

    @Test
    public void testFindDtosByAllFilters() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(ImmutableSet.of(UDM_BATCH_UID_4));
        filter.setAssignees(ImmutableSet.of(ASSIGNEE));
        filter.setReportedPubTypes(ImmutableSet.of(PUB_TYPE_NOT_SHARED));
        filter.setPubFormats(ImmutableSet.of(PUBLICATION_FORMAT));
        filter.setDetailLicenseeClasses(ImmutableSet.of(buildDetailLicenseeClass(22)));
        filter.setReportedTypeOfUses(ImmutableSet.of(REPORTED_TYPE_OF_USE));
        filter.setChannel(UdmChannelEnum.CCC);
        filter.setUsageDateFrom(LocalDate.of(2020, 4, 12));
        filter.setUsageDateTo(LocalDate.of(2020, 6, 20));
        filter.setSurveyStartDateFrom(LocalDate.of(2020, 3, 12));
        filter.setSurveyStartDateTo(LocalDate.of(2020, 5, 20));
        filter.setSurveyCountry(SURVEY_COUNTRY);
        filter.setLanguage(LANGUAGE);
        filter.setCompanyName(COMPANY_NAME_2);
        filter.setCompanyId(1136L);
        filter.setWrWrkInst(254327612L);
        filter.setAnnualMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 25, null));
        filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 425, null));
        filter.setStatisticalMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 1, null));
        filter.setQuantityExpression(new FilterExpression<>(FilterOperatorEnum.BETWEEN, 2, 400));
        List<UdmUsageDto> usages = udmUsageRepository.findDtosByFilter(filter, null, buildSort());
        assertEquals(1, usages.size());
        verifyUsageDto(loadExpectedUsageDto("json/udm/udm_usage_b989e02b.json").get(0), usages.get(0));
    }

    @Test
    public void testFindDtosByFilter() {
        assertFilteringFindDtosByFilter(filter -> filter.setPeriod(202106), UDM_USAGE_UID_5, UDM_USAGE_UID_6,
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageStatus(UsageStatusEnum.RH_FOUND), UDM_USAGE_UID_5,
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setAssignees(ImmutableSet.of(ASSIGNEE)), UDM_USAGE_UID_5,
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setReportedPubTypes(ImmutableSet.of(PUB_TYPE_NOT_SHARED)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setPubFormats(ImmutableSet.of(PUBLICATION_FORMAT)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter ->
            filter.setDetailLicenseeClasses(ImmutableSet.of(buildDetailLicenseeClass(2))), UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter ->
                filter.setReportedTypeOfUses(ImmutableSet.of(REPORTED_TYPE_OF_USE)), UDM_USAGE_UID_5,
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setChannel(UdmChannelEnum.CCC), UDM_USAGE_UID_5,
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDateFrom(LocalDate.of(2020, 5, 11)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setUsageDateTo(LocalDate.of(2020, 5, 10)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> {
            filter.setUsageDateFrom(LocalDate.of(2020, 4, 12));
            filter.setUsageDateTo(LocalDate.of(2020, 6, 20));
        }, UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyStartDateFrom(LocalDate.of(2020, 5, 20)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyStartDateTo(LocalDate.of(2020, 5, 20)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> {
            filter.setSurveyStartDateFrom(LocalDate.of(2020, 3, 12));
            filter.setSurveyStartDateTo(LocalDate.of(2020, 5, 20));
        }, UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setSurveyCountry(SURVEY_COUNTRY), UDM_USAGE_UID_5,
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setLanguage(LANGUAGE), UDM_USAGE_UID_5, UDM_USAGE_UID_6,
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyName(COMPANY_NAME_2), UDM_USAGE_UID_5,
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setCompanyId(1136L), UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInst(254327612L), UDM_USAGE_UID_5, UDM_USAGE_UID_6,
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter ->
                filter.setAnnualMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 25, null)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter ->
                filter.setAnnualMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 25, null)),
            UDM_USAGE_UID_5);
        assertFilteringFindDtosByFilter(filter ->
                filter.setAnnualMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 20, null)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter ->
                filter.setAnnualMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.BETWEEN, 20, 25)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter ->
                filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 425, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter ->
                filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 425, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter ->
                filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 5, null)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter ->
                filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.BETWEEN, 100, 400)),
            UDM_USAGE_UID_6);
        assertFilteringFindDtosByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 1, null)), UDM_USAGE_UID_5, UDM_USAGE_UID_6,
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 2, null)), UDM_USAGE_UID_5, UDM_USAGE_UID_6,
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 0, null)), UDM_USAGE_UID_5, UDM_USAGE_UID_6,
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 7, 100)));
        assertFilteringFindDtosByFilter(filter ->
                filter.setQuantityExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 17, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter ->
                filter.setQuantityExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 25, null)),
            UDM_USAGE_UID_5, UDM_USAGE_UID_6, UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter ->
                filter.setQuantityExpression(new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 5, null)),
            UDM_USAGE_UID_7);
        assertFilteringFindDtosByFilter(filter ->
                filter.setQuantityExpression(new FilterExpression<>(FilterOperatorEnum.BETWEEN, 2, 400)),
            UDM_USAGE_UID_6, UDM_USAGE_UID_7);
    }

    @Test
    public void testFindCountByFilter() {
        assertFilteringFindCountByFilter(filter -> filter.setPeriod(202106), 3);
        assertFilteringFindCountByFilter(filter -> filter.setUsageStatus(UsageStatusEnum.RH_FOUND), 3);
        assertFilteringFindCountByFilter(filter -> filter.setAssignees(ImmutableSet.of(ASSIGNEE)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setReportedPubTypes(ImmutableSet.of(PUB_TYPE_NOT_SHARED)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setPubFormats(ImmutableSet.of(PUBLICATION_FORMAT)), 3);
        assertFilteringFindCountByFilter(filter ->
            filter.setDetailLicenseeClasses(ImmutableSet.of(buildDetailLicenseeClass(2))), 1);
        assertFilteringFindCountByFilter(filter ->
            filter.setReportedTypeOfUses(ImmutableSet.of(REPORTED_TYPE_OF_USE)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setChannel(UdmChannelEnum.CCC), 3);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDateFrom(LocalDate.of(2020, 5, 20)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setUsageDateTo(LocalDate.of(2020, 5, 20)), 2);
        assertFilteringFindCountByFilter(filter -> {
            filter.setUsageDateFrom(LocalDate.of(2020, 4, 12));
            filter.setUsageDateTo(LocalDate.of(2020, 6, 20));
        }, 2);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyStartDateFrom(LocalDate.of(2020, 5, 20)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyStartDateTo(LocalDate.of(2020, 5, 20)), 2);
        assertFilteringFindCountByFilter(filter -> {
            filter.setSurveyStartDateFrom(LocalDate.of(2020, 3, 12));
            filter.setSurveyStartDateTo(LocalDate.of(2020, 5, 20));
        }, 2);
        assertFilteringFindCountByFilter(filter -> filter.setSurveyCountry(SURVEY_COUNTRY), 2);
        assertFilteringFindCountByFilter(filter -> filter.setLanguage(LANGUAGE), 3);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyName(COMPANY_NAME_2), 2);
        assertFilteringFindCountByFilter(filter -> filter.setCompanyId(1136L), 2);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInst(254327612L), 3);
        assertFilteringFindCountByFilter(filter ->
            filter.setAnnualMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 25, null)), 2);
        assertFilteringFindCountByFilter(filter ->
            filter.setAnnualMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 25, null)), 1);
        assertFilteringFindCountByFilter(filter ->
            filter.setAnnualMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 20, null)), 2);
        assertFilteringFindCountByFilter(filter ->
            filter.setAnnualMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.BETWEEN, 20, 25)), 2);
        assertFilteringFindCountByFilter(filter ->
            filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 425, null)), 1);
        assertFilteringFindCountByFilter(filter ->
            filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 425, null)), 2);
        assertFilteringFindCountByFilter(filter ->
            filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 5, null)), 2);
        assertFilteringFindCountByFilter(filter ->
            filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.BETWEEN, 100, 400)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 1, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 2, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 0, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 7, 100)), 0);
        assertFilteringFindCountByFilter(filter ->
            filter.setQuantityExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 17, null)), 1);
        assertFilteringFindCountByFilter(filter ->
            filter.setQuantityExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 25, null)), 3);
        assertFilteringFindCountByFilter(filter ->
            filter.setQuantityExpression(new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 5, null)), 1);
        assertFilteringFindCountByFilter(filter ->
            filter.setQuantityExpression(new FilterExpression<>(FilterOperatorEnum.BETWEEN, 2, 400)), 2);
    }

    @Test
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
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "quantity");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "annualizedCopies");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "ineligibleReason");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "createDate");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_3, UDM_USAGE_UID_4, "updateDate");
        assertSortingFindDtosByFilter(UDM_USAGE_UID_4, UDM_USAGE_UID_3, "updateUser");
    }

    @Test
    public void testFindCountByAllFilters() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(ImmutableSet.of(UDM_BATCH_UID_4));
        filter.setAssignees(ImmutableSet.of(ASSIGNEE));
        filter.setReportedPubTypes(ImmutableSet.of(PUB_TYPE_NOT_SHARED));
        filter.setPubFormats(ImmutableSet.of(PUBLICATION_FORMAT));
        filter.setDetailLicenseeClasses(ImmutableSet.of(buildDetailLicenseeClass(22)));
        filter.setReportedTypeOfUses(ImmutableSet.of(REPORTED_TYPE_OF_USE));
        filter.setChannel(UdmChannelEnum.CCC);
        filter.setUsageDateFrom(LocalDate.of(2020, 4, 12));
        filter.setUsageDateTo(LocalDate.of(2020, 6, 20));
        filter.setSurveyStartDateFrom(LocalDate.of(2020, 3, 12));
        filter.setSurveyStartDateTo(LocalDate.of(2020, 5, 20));
        filter.setSurveyCountry(SURVEY_COUNTRY);
        filter.setLanguage(LANGUAGE);
        filter.setCompanyName(COMPANY_NAME_2);
        filter.setCompanyId(1136L);
        filter.setWrWrkInst(254327612L);
        filter.setAnnualMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 25, null));
        filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 425, null));
        filter.setStatisticalMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 1, null));
        filter.setQuantityExpression(new FilterExpression<>(FilterOperatorEnum.BETWEEN, 2, 400));
        assertEquals(1, udmUsageRepository.findCountByFilter(filter));
    }

    @Test
    public void testIsOriginalDetailIdExist() {
        assertTrue(udmUsageRepository.isOriginalDetailIdExist("OGN674GHHSB001"));
        assertFalse(udmUsageRepository.isOriginalDetailIdExist("OGN674GHHSB101"));
    }

    @Test
    public void testUpdateProcessedUsage() {
        List<UdmUsage> udmUsages = udmUsageRepository.findByIds(Collections.singletonList(UDM_USAGE_UID_2));
        assertEquals(1, CollectionUtils.size(udmUsages));
        UdmUsage udmUsage = udmUsages.get(0);
        udmUsage.setStatus(UsageStatusEnum.RH_FOUND);
        udmUsage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        udmUsage.setWrWrkInst(5697789789L);
        udmUsage.setSystemTitle("Wissenschaft & Forschung France");
        udmUsage.setStandardNumber(STANDARD_NUMBER);
        assertNotNull(udmUsageRepository.updateProcessedUsage(udmUsage));
        List<UdmUsage> updatedUdmUsages = udmUsageRepository.findByIds(Collections.singletonList(UDM_USAGE_UID_2));
        assertEquals(1, CollectionUtils.size(updatedUdmUsages));
        UdmUsage updatedUdmUsage = updatedUdmUsages.get(0);
        assertEquals(RH_ACCOUNT_NUMBER, updatedUdmUsage.getRightsholder().getAccountNumber());
        assertEquals(UsageStatusEnum.RH_FOUND, updatedUdmUsage.getStatus());
        assertEquals(STANDARD_NUMBER, updatedUdmUsage.getStandardNumber());
        assertEquals("Wissenschaft & Forschung France", updatedUdmUsage.getSystemTitle());
    }

    @Test
    public void testFindAssignees() {
        assertEquals(ImmutableList.of("jjohn@copyright.com", "wjohn@copyright.com"),
            udmUsageRepository.findAssignees());
    }

    @Test
    public void testFindPublicationTypes() {
        assertEquals(ImmutableList.of("Book", PUB_TYPE_NOT_SHARED), udmUsageRepository.findPublicationTypes());
    }

    @Test
    public void testFindPublicationFormats() {
        assertEquals(ImmutableList.of("Digital", "Not Specified"), udmUsageRepository.findPublicationFormats());
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
        filter.setUdmBatchesIds(ImmutableSet.of(UDM_BATCH_UID_4));
        consumer.accept(filter);
        List<UdmUsageDto> usages = udmUsageRepository.findDtosByFilter(filter, null, buildSort());
        assertEquals(usageIds.length, usages.size());
        IntStream.range(0, usageIds.length)
            .forEach(index -> assertEquals(usageIds[index], usages.get(index).getId()));
    }

    private void assertFilteringFindCountByFilter(Consumer<UdmUsageFilter> consumer, int count) {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(ImmutableSet.of(UDM_BATCH_UID_4));
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
        assertEquals(expectedUsage.getDetailLicenseeClassId(), actualUsage.getDetailLicenseeClassId());
        assertEquals(expectedUsage.getDetailLicenseeClassName(), actualUsage.getDetailLicenseeClassName());
        assertEquals(expectedUsage.getSurveyRespondent(), actualUsage.getSurveyRespondent());
        assertEquals(expectedUsage.getIpAddress(), actualUsage.getIpAddress());
        assertEquals(expectedUsage.getSurveyCountry(), actualUsage.getSurveyCountry());
        assertEquals(expectedUsage.getSurveyStartDate(), actualUsage.getSurveyStartDate());
        assertEquals(expectedUsage.getStatisticalMultiplier(), actualUsage.getStatisticalMultiplier());
        assertEquals(expectedUsage.getAnnualMultiplier(), actualUsage.getAnnualMultiplier());
        assertEquals(expectedUsage.getReportedTypeOfUse(), actualUsage.getReportedTypeOfUse());
        assertEquals(expectedUsage.getAnnualizedCopies(), actualUsage.getAnnualizedCopies());
        assertEquals(expectedUsage.getQuantity(), actualUsage.getQuantity());
        assertNull(actualUsage.getIneligibleReason());
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
        assertNull(actualUsage.getIneligibleReason());
    }

    private UdmUsage buildUdmUsage() {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setId(UDM_USAGE_UID_1);
        udmUsage.setBatchId(UDM_BATCH_UID_1);
        udmUsage.setOriginalDetailId(UDM_USAGE_ORIGINAL_DETAIL_UID);
        udmUsage.setStatus(UsageStatusEnum.NEW);
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
