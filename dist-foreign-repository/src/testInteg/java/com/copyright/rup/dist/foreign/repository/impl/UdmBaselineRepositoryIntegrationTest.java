package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UdmValue;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * Integration test for {@link UdmBaselineRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 08/31/21
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class UdmBaselineRepositoryIntegrationTest {

    private static final String TEST_DATA_INIT_FIND_DTOS_BY_FILTER =
        "udm-baseline-repository-test-data-init-find-dtos-by-filter.groovy";
    private static final String USER_NAME = "user@copyright.com";
    private static final String SURVEY_COUNTRY = "Portugal";
    private static final String SURVEY_COUNTRY_FRAGMENT = "Portu";
    private static final String SURVEY_COUNTRY_DIFFERENT_CASE = "PoRtUgaL";
    private static final String SURVEY_COUNTRY_WITH_METASYMBOLS = "Portugal !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final String TYPE_OF_USE_1 = "EMAIL_COPY";
    private static final String TYPE_OF_USE_2 = "PRINT_COPIES";
    private static final String USAGE_DETAIL_ID_1 = "OGN554GHHSG008";
    private static final String USAGE_DETAIL_ID_1_FRAGMENT = "HSG008";
    private static final String USAGE_DETAIL_ID_1_DIFFERENT_CASE = "Ogn554GHhSg008";
    private static final String USAGE_DETAIL_ID_2 = "OGN554GHHSG777";
    private static final String USAGE_DETAIL_ID_3_WITH_METASYMBOLS = "OGN554GHHSG010 !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final String SYSTEM_TITLE_1 = "Colloids and surfaces. B, Biointerfaces";
    private static final String SYSTEM_TITLE_1_FRAGMENT = "Colloids and surfaces. B,";
    private static final String SYSTEM_TITLE_1_DIFFERENT_CASE = "ColLoIds aND SurfaceS. B, BiointerfacES";
    private static final String SYSTEM_TITLE_2 = "Colloids and surfaces. C, Biointerfaces";
    private static final String SYSTEM_TITLE_3_WITH_METASYMBOLS =
        "Colloids and surfaces. B, Biointerfaces !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final String USAGE_ID_1 = "4a1522d3-9dfe-4884-b314-cd6f87922936";
    private static final String USAGE_ID_2 = "1a1522d3-9dfe-4884-58aa-caaf8792112c";
    private static final String USAGE_ID_3 = "771522d3-ccfe-2189-b314-cd6f87ab6689";
    private static final String USAGE_ID_4 = "5c795dfc-94fb-4296-997c-6f36e0a673dc";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private IUdmUsageRepository udmUsageRepository;
    @Autowired
    private IUdmBaselineRepository baselineRepository;

    @Test
    @TestData(fileName = TEST_DATA_INIT_FIND_DTOS_BY_FILTER)
    public void testFindCountByAllFilters() {
        UdmBaselineFilter filter = new UdmBaselineFilter();
        filter.setPeriods(Collections.singleton(202012));
        filter.setChannel(UdmChannelEnum.CCC);
        filter.setUdmUsageOrigin(UdmUsageOriginEnum.SS);
        filter.setDetailLicenseeClasses(Collections.singleton(buildDetailLicenseeClass(22)));
        filter.setAggregateLicenseeClasses(Collections.singleton(buildAggregateLicenseeClass(56)));
        filter.setReportedTypeOfUses(Collections.singleton(TYPE_OF_USE_1));
        filter.setSurveyCountry(SURVEY_COUNTRY);
        filter.setWrWrkInst(20008506L);
        filter.setSystemTitle(SYSTEM_TITLE_1);
        filter.setUsageDetailId(USAGE_DETAIL_ID_1);
        filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 5, null));
        assertEquals(1, baselineRepository.findCountByFilter(filter));
    }

    @Test
    @TestData(fileName = TEST_DATA_INIT_FIND_DTOS_BY_FILTER)
    public void testFindCountByFilter() {
        verifyFilteringFindCountByFilter(filter -> filter.setChannel(UdmChannelEnum.CCC), 2);
        verifyFilteringFindCountByFilter(filter -> filter.setChannel(UdmChannelEnum.Rightsdirect), 0);
        verifyFilteringFindCountByFilter(filter -> filter.setUdmUsageOrigin(UdmUsageOriginEnum.SS), 2);
        verifyFilteringFindCountByFilter(filter -> filter.setUdmUsageOrigin(UdmUsageOriginEnum.RFA), 0);
        verifyFilteringFindCountByFilter(filter -> filter.setReportedTypeOfUses(ImmutableSet.of(TYPE_OF_USE_1)), 1);
        verifyFilteringFindCountByFilter(filter -> filter.setReportedTypeOfUses(ImmutableSet.of(TYPE_OF_USE_2)), 0);
        verifyFilteringFindCountByFilter(filter -> filter.setSurveyCountry(SURVEY_COUNTRY), 1);
        verifyFilteringFindCountByFilter(filter -> filter.setSurveyCountry(SURVEY_COUNTRY_FRAGMENT), 0);
        verifyFilteringFindCountByFilter(filter -> filter.setSurveyCountry(SURVEY_COUNTRY_DIFFERENT_CASE), 1);
        verifyFilteringFindCountByFilter(filter -> filter.setSurveyCountry(SURVEY_COUNTRY_WITH_METASYMBOLS), 1);
        verifyFilteringFindCountByFilter(filter -> filter.setSurveyCountry("USA"), 0);
        verifyFilteringFindCountByFilter(filter -> filter.setUsageDetailId(USAGE_DETAIL_ID_1), 1);
        verifyFilteringFindCountByFilter(filter -> filter.setUsageDetailId(USAGE_DETAIL_ID_1_FRAGMENT), 0);
        verifyFilteringFindCountByFilter(filter -> filter.setUsageDetailId(USAGE_DETAIL_ID_1_DIFFERENT_CASE), 1);
        verifyFilteringFindCountByFilter(filter -> filter.setUsageDetailId(USAGE_DETAIL_ID_3_WITH_METASYMBOLS), 1);
        verifyFilteringFindCountByFilter(filter -> filter.setUsageDetailId(USAGE_DETAIL_ID_2), 0);
        verifyFilteringFindCountByFilter(filter -> filter.setWrWrkInst(20008506L), 2);
        verifyFilteringFindCountByFilter(filter -> filter.setWrWrkInst(20008525L), 0);
        verifyFilteringFindCountByFilter(filter -> filter.setSystemTitle(SYSTEM_TITLE_1), 1);
        verifyFilteringFindCountByFilter(filter -> filter.setSystemTitle(SYSTEM_TITLE_1_FRAGMENT), 0);
        verifyFilteringFindCountByFilter(filter -> filter.setSystemTitle(SYSTEM_TITLE_1_DIFFERENT_CASE), 1);
        verifyFilteringFindCountByFilter(filter -> filter.setSystemTitle(SYSTEM_TITLE_3_WITH_METASYMBOLS), 1);
        verifyFilteringFindCountByFilter(filter -> filter.setSystemTitle(SYSTEM_TITLE_2), 0);
        verifyFilteringFindCountByFilter(filter ->
            filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 5, null)), 2);
        verifyFilteringFindCountByFilter(filter ->
            filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 5, null)), 0);
        verifyFilteringFindCountByFilter(filter ->
            filter.setDetailLicenseeClasses(Collections.singleton(buildDetailLicenseeClass(22))), 1);
        verifyFilteringFindCountByFilter(filter ->
            filter.setDetailLicenseeClasses(Collections.singleton(buildDetailLicenseeClass(24))), 0);
        verifyFilteringFindCountByFilter(filter ->
            filter.setAggregateLicenseeClasses(Collections.singleton(buildAggregateLicenseeClass(56))), 1);
        verifyFilteringFindCountByFilter(filter ->
            filter.setAggregateLicenseeClasses(Collections.singleton(buildAggregateLicenseeClass(82))), 0);
    }

    @Test
    @TestData(fileName = TEST_DATA_INIT_FIND_DTOS_BY_FILTER)
    public void testFindDtosByAllFilters() {
        UdmBaselineFilter filter = new UdmBaselineFilter();
        filter.setPeriods(Collections.singleton(202012));
        filter.setChannel(UdmChannelEnum.CCC);
        filter.setUdmUsageOrigin(UdmUsageOriginEnum.SS);
        filter.setDetailLicenseeClasses(Collections.singleton(buildDetailLicenseeClass(22)));
        filter.setAggregateLicenseeClasses(Collections.singleton(buildAggregateLicenseeClass(56)));
        filter.setReportedTypeOfUses(Collections.singleton(TYPE_OF_USE_1));
        filter.setSurveyCountry(SURVEY_COUNTRY);
        filter.setWrWrkInst(20008506L);
        filter.setSystemTitle(SYSTEM_TITLE_1);
        filter.setUsageDetailId(USAGE_DETAIL_ID_1);
        filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 5, null));
        assertEquals(loadExpectedBaselineDto("json/udm/udm_baseline_dto_4a1522d3.json"),
            baselineRepository.findDtosByFilter(filter, null, null));
    }

    @Test
    @TestData(fileName = TEST_DATA_INIT_FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilter() {
        verifyFilteringFindDtosByFilter(filter -> filter.setChannel(UdmChannelEnum.CCC), USAGE_ID_1, USAGE_ID_4);
        verifyFilteringFindDtosByFilter(filter -> filter.setChannel(UdmChannelEnum.Rightsdirect));
        verifyFilteringFindDtosByFilter(filter -> filter.setUdmUsageOrigin(UdmUsageOriginEnum.SS), USAGE_ID_1,
            USAGE_ID_4);
        verifyFilteringFindDtosByFilter(filter -> filter.setUdmUsageOrigin(UdmUsageOriginEnum.RFA));
        verifyFilteringFindDtosByFilter(filter -> filter.setReportedTypeOfUses(ImmutableSet.of(TYPE_OF_USE_1)),
            USAGE_ID_1);
        verifyFilteringFindDtosByFilter(filter -> filter.setReportedTypeOfUses(ImmutableSet.of(TYPE_OF_USE_2)));
        verifyFilteringFindDtosByFilter(filter -> filter.setSurveyCountry(SURVEY_COUNTRY), USAGE_ID_1);
        verifyFilteringFindDtosByFilter(filter -> filter.setSurveyCountry(SURVEY_COUNTRY_FRAGMENT));
        verifyFilteringFindDtosByFilter(filter -> filter.setSurveyCountry(SURVEY_COUNTRY_DIFFERENT_CASE), USAGE_ID_1);
        verifyFilteringFindDtosByFilter(filter -> filter.setSurveyCountry(SURVEY_COUNTRY_WITH_METASYMBOLS), USAGE_ID_4);
        verifyFilteringFindDtosByFilter(filter -> filter.setSurveyCountry("USA"));
        verifyFilteringFindDtosByFilter(filter -> filter.setUsageDetailId(USAGE_DETAIL_ID_1), USAGE_ID_1);
        verifyFilteringFindDtosByFilter(filter -> filter.setUsageDetailId(USAGE_DETAIL_ID_1_FRAGMENT));
        verifyFilteringFindDtosByFilter(filter -> filter.setUsageDetailId(USAGE_DETAIL_ID_1_DIFFERENT_CASE),
            USAGE_ID_1);
        verifyFilteringFindDtosByFilter(filter -> filter.setUsageDetailId(USAGE_DETAIL_ID_3_WITH_METASYMBOLS),
            USAGE_ID_4);
        verifyFilteringFindDtosByFilter(filter -> filter.setUsageDetailId(USAGE_DETAIL_ID_2));
        verifyFilteringFindDtosByFilter(filter -> filter.setWrWrkInst(20008506L), USAGE_ID_1, USAGE_ID_4);
        verifyFilteringFindDtosByFilter(filter -> filter.setWrWrkInst(20008525L));
        verifyFilteringFindDtosByFilter(filter -> filter.setSystemTitle(SYSTEM_TITLE_1), USAGE_ID_1);
        verifyFilteringFindDtosByFilter(filter -> filter.setSystemTitle(SYSTEM_TITLE_1_FRAGMENT));
        verifyFilteringFindDtosByFilter(filter -> filter.setSystemTitle(SYSTEM_TITLE_1_DIFFERENT_CASE), USAGE_ID_1);
        verifyFilteringFindDtosByFilter(filter -> filter.setSystemTitle(SYSTEM_TITLE_3_WITH_METASYMBOLS), USAGE_ID_4);
        verifyFilteringFindDtosByFilter(filter -> filter.setSystemTitle(SYSTEM_TITLE_2));
        verifyFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 5, null)), USAGE_ID_1, USAGE_ID_4);
        verifyFilteringFindDtosByFilter(filter ->
            filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 5, null)));
        verifyFilteringFindDtosByFilter(filter ->
            filter.setDetailLicenseeClasses(Collections.singleton(buildDetailLicenseeClass(22))), USAGE_ID_1);
        verifyFilteringFindDtosByFilter(filter ->
            filter.setDetailLicenseeClasses(Collections.singleton(buildDetailLicenseeClass(24))));
        verifyFilteringFindDtosByFilter(filter ->
            filter.setAggregateLicenseeClasses(Collections.singleton(buildAggregateLicenseeClass(56))), USAGE_ID_1);
        verifyFilteringFindDtosByFilter(filter ->
            filter.setAggregateLicenseeClasses(Collections.singleton(buildAggregateLicenseeClass(82))));
    }

    @Test
    @TestData(fileName = TEST_DATA_INIT_FIND_DTOS_BY_FILTER)
    public void testSortingFindDtosByFilter() {
        verifySortingFindDtosByFilter(USAGE_ID_2, USAGE_ID_3, "detailId");
        verifySortingFindDtosByFilter(USAGE_ID_3, USAGE_ID_2, "usageOrigin");
        verifySortingFindDtosByFilter(USAGE_ID_2, USAGE_ID_3, "channel");
        verifySortingFindDtosByFilter(USAGE_ID_2, USAGE_ID_3, "usageDetailId");
        verifySortingFindDtosByFilter(USAGE_ID_3, USAGE_ID_2, "wrWrkInst");
        verifySortingFindDtosByFilter(USAGE_ID_3, USAGE_ID_2, "systemTitle");
        verifySortingFindDtosByFilter(USAGE_ID_2, USAGE_ID_3, "detLcId");
        verifySortingFindDtosByFilter(USAGE_ID_3, USAGE_ID_2, "detLcName");
        verifySortingFindDtosByFilter(USAGE_ID_2, USAGE_ID_3, "aggLcId");
        verifySortingFindDtosByFilter(USAGE_ID_3, USAGE_ID_2, "aggLcName");
        verifySortingFindDtosByFilter(USAGE_ID_3, USAGE_ID_2, "surveyCountry");
        verifySortingFindDtosByFilter(USAGE_ID_2, USAGE_ID_3, "reportedTypeOfUse");
        verifySortingFindDtosByFilter(USAGE_ID_2, USAGE_ID_3, "annualizedCopies");
        verifySortingFindDtosByFilter(USAGE_ID_2, USAGE_ID_3, "createDate");
        verifySortingFindDtosByFilter(USAGE_ID_2, USAGE_ID_3, "createUser");
        verifySortingFindDtosByFilter(USAGE_ID_2, USAGE_ID_3, "updateDate");
        verifySortingFindDtosByFilter(USAGE_ID_2, USAGE_ID_3, "updateUser");
    }

    @Test
    @TestData(fileName = "udm-baseline-repository-test-data-init-find-periods.groovy")
    public void testGetPeriods() {
        assertEquals(Arrays.asList(202106, 202012), baselineRepository.findPeriods());
    }

    @Test
    @TestData(fileName = "udm-baseline-repository-test-data-init-find-not-populated-values-from-baseline.groovy")
    public void testFindNotPopulatedValuesFromBaseline() {
        assertEquals(loadExpectedValues("json/udm/udm_values_201512.json"),
            baselineRepository.findNotPopulatedValuesFromBaseline(201512));
        assertEquals(loadExpectedValues("json/udm/udm_values_201412.json"),
            baselineRepository.findNotPopulatedValuesFromBaseline(201412));
        assertTrue(CollectionUtils.isEmpty(baselineRepository.findNotPopulatedValuesFromBaseline(200812)));
    }

    @Test
    @TestData(fileName = "udm-baseline-repository-test-data-init-populate-value-id.groovy")
    public void testPopulateValueId() {
        Map<Long, String> wrWrkInstToValueIdMap = ImmutableMap.of(
            28908508L, "9b2550ff-a80a-41a9-a63c-047216a62241",
            38908778L, "436be73d-82c9-4e53-bda6-b882e528bed7");
        assertEquals(3, baselineRepository.populateValueId(211512, wrWrkInstToValueIdMap, USER_NAME));
        UdmBaselineFilter filter = new UdmBaselineFilter();
        filter.setPeriods(Collections.singleton(211512));
        verifyUdmBaselineDto(loadExpectedBaselineDto("json/udm/udm_baseline_dto_211512.json"),
            baselineRepository.findDtosByFilter(filter, null, null));
        filter.setPeriods(Collections.singleton(211412));
        verifyUdmBaselineDto(loadExpectedBaselineDto("json/udm/udm_baseline_dto_211412.json"),
            baselineRepository.findDtosByFilter(filter, null, null));
    }

    @Test
    @TestData(fileName = "udm-baseline-repository-test-data-init-remove-udm-usage-from-baseline-by-id.groovy")
    public void testRemoveUdmUsageFromBaselineById() {
        List<UdmUsage> udmUsages =
            udmUsageRepository.findByIds(Collections.singletonList("38ac4213-0515-42f5-a1bc-d4794f4eea8f"));
        assertEquals(1, udmUsages.size());
        assertTrue(udmUsages.get(0).isBaselineFlag());
        baselineRepository.removeUdmUsageFromBaselineById("38ac4213-0515-42f5-a1bc-d4794f4eea8f");
        udmUsages = udmUsageRepository.findByIds(Collections.singletonList("38ac4213-0515-42f5-a1bc-d4794f4eea8f"));
        assertEquals(1, udmUsages.size());
        assertFalse(udmUsages.get(0).isBaselineFlag());
    }

    private void verifyUdmBaselineDto(List<UdmBaselineDto> expectedUsages, List<UdmBaselineDto> actualUsages) {
        assertEquals(expectedUsages.size(), actualUsages.size());
        IntStream.range(0, expectedUsages.size()).forEach(index -> {
            UdmBaselineDto expectedUsage = expectedUsages.get(index);
            UdmBaselineDto actualUsage = actualUsages.get(index);
            assertEquals(expectedUsage.getId(), actualUsage.getId());
            assertEquals(expectedUsage.getPeriod(), actualUsage.getPeriod());
            assertEquals(expectedUsage.getUsageOrigin(), actualUsage.getUsageOrigin());
            assertEquals(expectedUsage.getOriginalDetailId(), actualUsage.getOriginalDetailId());
            assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
            assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
            assertEquals(expectedUsage.getDetailLicenseeClassId(), actualUsage.getDetailLicenseeClassId());
            assertEquals(expectedUsage.getDetailLicenseeClassName(), actualUsage.getDetailLicenseeClassName());
            assertEquals(expectedUsage.getAggregateLicenseeClassId(), actualUsage.getAggregateLicenseeClassId());
            assertEquals(expectedUsage.getAggregateLicenseeClassName(), actualUsage.getAggregateLicenseeClassName());
            assertEquals(expectedUsage.getSurveyCountry(), actualUsage.getSurveyCountry());
            assertEquals(expectedUsage.getChannel(), actualUsage.getChannel());
            assertEquals(expectedUsage.getTypeOfUse(), actualUsage.getTypeOfUse());
            assertEquals(expectedUsage.getAnnualizedCopies(), actualUsage.getAnnualizedCopies());
            assertEquals(expectedUsage.getValueId(), actualUsage.getValueId());
            assertEquals(expectedUsage.getCreateUser(), actualUsage.getCreateUser());
            assertEquals(expectedUsage.getUpdateUser(), actualUsage.getUpdateUser());
            assertEquals(expectedUsage.getVersion(), actualUsage.getVersion());
        });
    }

    private void verifyFilteringFindCountByFilter(Consumer<UdmBaselineFilter> consumer, int count) {
        UdmBaselineFilter filter = new UdmBaselineFilter();
        filter.setPeriods(Collections.singleton(202012));
        consumer.accept(filter);
        int usagesCount = baselineRepository.findCountByFilter(filter);
        assertEquals(count, usagesCount);
    }

    private void verifyFilteringFindDtosByFilter(Consumer<UdmBaselineFilter> consumer, String... usageIds) {
        UdmBaselineFilter filter = new UdmBaselineFilter();
        filter.setPeriods(Collections.singleton(202012));
        consumer.accept(filter);
        List<UdmBaselineDto> usages = baselineRepository.findDtosByFilter(filter, null, null);
        assertEquals(usageIds.length, usages.size());
        IntStream.range(0, usageIds.length)
            .forEach(index -> assertEquals(usageIds[index], usages.get(index).getId()));
    }

    private void verifySortingFindDtosByFilter(String detailIdAsc, String detailIdDesc, String sortProperty) {
        UdmBaselineFilter filter = new UdmBaselineFilter();
        filter.setPeriods(Collections.singleton(201906));
        List<UdmBaselineDto> usageDtos =
            baselineRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Sort.Direction.ASC));
        assertEquals(detailIdAsc, usageDtos.get(0).getId());
        usageDtos =
            baselineRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Sort.Direction.DESC));
        assertEquals(detailIdDesc, usageDtos.get(0).getId());
    }

    private DetailLicenseeClass buildDetailLicenseeClass(Integer id) {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(id);
        return detailLicenseeClass;
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass(Integer id) {
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(id);
        return aggregateLicenseeClass;
    }

    private List<UdmBaselineDto> loadExpectedBaselineDto(String fileName) {
        List<UdmBaselineDto> udmBaselineDtos = new ArrayList<>();
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            udmBaselineDtos.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<UdmBaselineDto>>() {
            }));
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return udmBaselineDtos;
    }

    private List<UdmValue> loadExpectedValues(String fileName) {
        List<UdmValue> udmValues = new ArrayList<>();
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            udmValues.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<UdmValue>>() {
            }));
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return udmValues;
    }
}
