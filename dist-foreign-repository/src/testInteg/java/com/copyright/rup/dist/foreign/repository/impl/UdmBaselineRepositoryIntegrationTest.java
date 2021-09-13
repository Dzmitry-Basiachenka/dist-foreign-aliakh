package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
@TestPropertySource(properties = {"test.liquibase.changelog=udm-baseline-repository-test-data-init.groovy"})
@Transactional
public class UdmBaselineRepositoryIntegrationTest {

    private static final String USER_NAME = "user@copyright.com";
    private static final String SURVEY_COUNTRY = "Portugal";
    private static final String TYPE_OF_USE_1 = "EMAIL_COPY";
    private static final String TYPE_OF_USE_2 = "PRINT_COPIES";
    private static final String USAGE_DETAIL_ID_1 = "OGN554GHHSG008";
    private static final String USAGE_DETAIL_ID_2 = "OGN554GHHSG777";
    private static final String SYSTEM_TITLE_1 = "Colloids and surfaces. B, Biointerfaces";
    private static final String SYSTEM_TITLE_2 = "Colloids and surfaces. C, Biointerfaces";
    private static final String USAGE_ID_1 = "4a1522d3-9dfe-4884-b314-cd6f87922936";
    private static final String USAGE_ID_2 = "1a1522d3-9dfe-4884-58aa-caaf8792112c";
    private static final String USAGE_ID_3 = "771522d3-ccfe-2189-b314-cd6f87ab6689";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private IUdmUsageRepository udmUsageRepository;
    @Autowired
    private IUdmBaselineRepository baselineRepository;

    @Test
    public void testRemoveUdmUsageFromBaseline() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(Collections.singleton("201f42dc-7f13-4449-97b0-725aa5a339e0"));
        List<UdmUsageDto> usageDtos = udmUsageRepository.findDtosByFilter(filter, null, null);
        assertEquals(3, usageDtos.size());
        usageDtos.forEach(usageDto -> assertTrue(usageDto.isBaselineFlag()));
        Set<String> removedUsageIds = baselineRepository.removeUdmUsagesFromBaseline(202106, USER_NAME);
        assertEquals(2, removedUsageIds.size());
        filter.setPeriod(202106);
        assertEquals(2, udmUsageRepository.findDtosByFilter(filter, null, null)
            .stream()
            .filter(usageDto -> !usageDto.isBaselineFlag())
            .count());
    }

    @Test
    public void testFindCountByAllFilters() {
        UdmBaselineFilter filter = new UdmBaselineFilter();
        filter.setPeriod(202012);
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
    public void testFindCountByFilter() {
        verifyFilteringFindCountByFilter(filter -> filter.setChannel(UdmChannelEnum.CCC), 1);
        verifyFilteringFindCountByFilter(filter -> filter.setChannel(UdmChannelEnum.Rightsdirect), 0);
        verifyFilteringFindCountByFilter(filter -> filter.setUdmUsageOrigin(UdmUsageOriginEnum.SS), 1);
        verifyFilteringFindCountByFilter(filter -> filter.setUdmUsageOrigin(UdmUsageOriginEnum.RFA), 0);
        verifyFilteringFindCountByFilter(filter -> filter.setReportedTypeOfUses(ImmutableSet.of(TYPE_OF_USE_1)), 1);
        verifyFilteringFindCountByFilter(filter -> filter.setReportedTypeOfUses(ImmutableSet.of(TYPE_OF_USE_2)), 0);
        verifyFilteringFindCountByFilter(filter -> filter.setSurveyCountry(SURVEY_COUNTRY), 1);
        verifyFilteringFindCountByFilter(filter -> filter.setSurveyCountry("USA"), 0);
        verifyFilteringFindCountByFilter(filter -> filter.setUsageDetailId(USAGE_DETAIL_ID_1), 1);
        verifyFilteringFindCountByFilter(filter -> filter.setUsageDetailId(USAGE_DETAIL_ID_2), 0);
        verifyFilteringFindCountByFilter(filter -> filter.setWrWrkInst(20008506L), 1);
        verifyFilteringFindCountByFilter(filter -> filter.setWrWrkInst(20008525L), 0);
        verifyFilteringFindCountByFilter(filter -> filter.setSystemTitle(SYSTEM_TITLE_1), 1);
        verifyFilteringFindCountByFilter(filter -> filter.setSystemTitle(SYSTEM_TITLE_2), 0);
        verifyFilteringFindCountByFilter(filter ->
            filter.setAnnualizedCopiesExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 5, null)), 1);
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
    public void testFindDtosByAllFilters() {
        UdmBaselineFilter filter = new UdmBaselineFilter();
        filter.setPeriod(202012);
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
    public void testFindDtosByFilter() {
        verifyFilteringFindDtosByFilter(filter -> filter.setChannel(UdmChannelEnum.CCC), USAGE_ID_1);
        verifyFilteringFindDtosByFilter(filter -> filter.setChannel(UdmChannelEnum.Rightsdirect));
        verifyFilteringFindDtosByFilter(filter -> filter.setUdmUsageOrigin(UdmUsageOriginEnum.SS), USAGE_ID_1);
        verifyFilteringFindDtosByFilter(filter -> filter.setUdmUsageOrigin(UdmUsageOriginEnum.RFA));
        verifyFilteringFindDtosByFilter(filter -> filter.setReportedTypeOfUses(ImmutableSet.of(TYPE_OF_USE_1)),
            USAGE_ID_1);
        verifyFilteringFindDtosByFilter(filter -> filter.setReportedTypeOfUses(ImmutableSet.of(TYPE_OF_USE_2)));
        verifyFilteringFindDtosByFilter(filter -> filter.setSurveyCountry(SURVEY_COUNTRY), USAGE_ID_1);
        verifyFilteringFindDtosByFilter(filter -> filter.setSurveyCountry("USA"));
        verifyFilteringFindDtosByFilter(filter -> filter.setUsageDetailId(USAGE_DETAIL_ID_1), USAGE_ID_1);
        verifyFilteringFindDtosByFilter(filter -> filter.setUsageDetailId(USAGE_DETAIL_ID_2));
        verifyFilteringFindDtosByFilter(filter -> filter.setWrWrkInst(20008506L), USAGE_ID_1);
        verifyFilteringFindDtosByFilter(filter -> filter.setWrWrkInst(20008525L));
        verifyFilteringFindDtosByFilter(filter -> filter.setSystemTitle(SYSTEM_TITLE_1), USAGE_ID_1);
        verifyFilteringFindDtosByFilter(filter -> filter.setSystemTitle(SYSTEM_TITLE_2));
        verifyFilteringFindDtosByFilter(filter -> filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 5, null)), USAGE_ID_1);
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
    public void testGetPeriods() {
        assertEquals(Arrays.asList(202106, 202012, 201906), baselineRepository.findPeriods());
    }

    private void verifyFilteringFindCountByFilter(Consumer<UdmBaselineFilter> consumer, int count) {
        UdmBaselineFilter filter = new UdmBaselineFilter();
        filter.setPeriod(202012);
        consumer.accept(filter);
        int usagesCount = baselineRepository.findCountByFilter(filter);
        assertEquals(count, usagesCount);
    }

    private void verifyFilteringFindDtosByFilter(Consumer<UdmBaselineFilter> consumer, String... usageIds) {
        UdmBaselineFilter filter = new UdmBaselineFilter();
        filter.setPeriod(202012);
        consumer.accept(filter);
        List<UdmBaselineDto> usages = baselineRepository.findDtosByFilter(filter, null, null);
        assertEquals(usageIds.length, usages.size());
        IntStream.range(0, usageIds.length)
            .forEach(index -> assertEquals(usageIds[index], usages.get(index).getId()));
    }

    private void verifySortingFindDtosByFilter(String detailIdAsc, String detailIdDesc, String sortProperty) {
        UdmBaselineFilter filter = new UdmBaselineFilter();
        filter.setPeriod(201906);
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
}
