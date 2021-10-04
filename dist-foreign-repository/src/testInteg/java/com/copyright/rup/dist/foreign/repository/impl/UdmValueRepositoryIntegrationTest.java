package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmValue;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmValueRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * Verifies {@link UdmValueRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/21
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=udm-value-repository-test-data-init.groovy"})
@Transactional
public class UdmValueRepositoryIntegrationTest {

    private static final String UDM_VALUE_UID_1 = "07026f4a-8a21-4529-97ad-5dab5c92bcce";
    private static final String UDM_VALUE_UID_2 = "2ffbedd7-708c-4cc5-a72b-5d5f962401ab";
    private static final String UDM_VALUE_UID_3 = "833abadc-53cd-47b5-a7d6-8ba08368e636";
    private static final String UDM_VALUE_UID_4 = "dae1b2c9-7266-4b11-8373-eea9504a9819";
    private static final String UDM_VALUE_UID_5 = "e1cbd88f-7b0f-4f92-b6f4-73faadeb4501";
    private static final String UDM_VALUE_UID_6 = "469e81b1-2e16-481d-a336-89725c6237ce";
    private static final String UDM_VALUE_UID_7 = "5add051a-c4be-475b-9542-e31a05be1eb1";
    private static final String PERIOD_1 = "201406";
    private static final String PERIOD_2 = "202106";
    private static final String ASSIGNEE = "jjohn@copyright.com";
    private static final String USER_NAME = "jjohn@copyright.com";
    private static final String SYSTEM_TITLE = "Tenside, surfactants, detergents";
    private static final String SYSTEM_TITLE_WITH_METASYMBOLS =
        "Colloids and  libero !@#$%^&*()_+-=?/'\"}{][<>convallis. B, Biointerfaces";
    private static final String STANDARD_NUMBER = "1873-7773";
    private static final String RH_NAME_PART = "John Wiley";
    private static final String COMMENT = "Comment";
    private static final BigDecimal PRICE_IN_USD = new BigDecimal("2.5000000000");
    private static final BigDecimal PRICE = new BigDecimal("5.0000000000");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private IUdmValueRepository udmValueRepository;

    @Test
    public void testInsert() {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setPeriods(Collections.singleton(209906));
        assertEquals(0, udmValueRepository.findCountByFilter(filter));
        udmValueRepository.insert(buildUdmValue());
        List<UdmValueDto> values = udmValueRepository.findDtosByFilter(filter, null, null);
        assertEquals(1, values.size());
        verifyValueDto(loadExpectedValueDto("json/udm/udm_value_dto_94e6d604.json").get(0), values.get(0), false);
    }

    @Test
    public void testFindPeriods() {
        List<Integer> expectedPeriods = Arrays.asList(202112, 202106, 201912, 201506, 201406);
        List<Integer> actualPeriods = udmValueRepository.findPeriods();
        assertFalse(actualPeriods.isEmpty());
        assertEquals(expectedPeriods, actualPeriods);
    }

    @Test
    public void testFindCountByAllFilters() {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setPeriods(Collections.singleton(201506));
        filter.setStatus(UdmValueStatusEnum.NEW);
        filter.setAssignees(Collections.singleton(ASSIGNEE));
        filter.setLastValuePeriods(Collections.singleton(FilterOperatorEnum.IS_NULL.name()));
        filter.setWrWrkInst(306985899L);
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, STANDARD_NUMBER, null));
        filter.setRhAccountNumber(1000002859L);
        filter.setRhNameExpression(new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_PART, null));
        filter.setPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null));
        filter.setPriceInUsdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_IN_USD, null));
        filter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 50, null));
        filter.setPubType(null);
        filter.setComment(COMMENT);
        assertEquals(1, udmValueRepository.findCountByFilter(filter));
    }

    @Test
    public void testFindDtosByAllFilters() {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setPeriods(Collections.singleton(201506));
        filter.setStatus(UdmValueStatusEnum.NEW);
        filter.setAssignees(Collections.singleton(ASSIGNEE));
        filter.setLastValuePeriods(Collections.singleton(FilterOperatorEnum.IS_NULL.name()));
        filter.setWrWrkInst(306985899L);
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, STANDARD_NUMBER, null));
        filter.setRhAccountNumber(1000002859L);
        filter.setRhNameExpression(new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_PART, null));
        filter.setPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null));
        filter.setPriceInUsdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_IN_USD, null));
        filter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 50, null));
        filter.setPubType(null);
        filter.setComment(COMMENT);
        List<UdmValueDto> values = udmValueRepository.findDtosByFilter(filter, null, buildSort());
        assertEquals(1, values.size());
        verifyValueDto(loadExpectedValueDto("json/udm/udm_value_dto_dae1b2c9.json").get(0), values.get(0), true);
    }

    @Test
    public void testFindDtosByFilter() {
        assertFilteringFindDtosByFilter(filter -> filter.setPeriods(new HashSet<>(Arrays.asList(201506, 202112))),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setStatus(UdmValueStatusEnum.PRELIM_RESEARCH_COMPLETE),
            UDM_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setCurrency(new Currency("USD", "US Dollar")),
            UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(
            filter -> filter.setAssignees(new HashSet<>(Arrays.asList("wjohn@copyright.com", "ajohn@copyright.com"))),
            UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInst(306985899L), UDM_VALUE_UID_2, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null)), UDM_VALUE_UID_2, UDM_VALUE_UID_3,
            UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_WITH_METASYMBOLS, null)), UDM_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, "Tenside", null)), UDM_VALUE_UID_2, UDM_VALUE_UID_3,
            UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setRhAccountNumber(1000002859L), UDM_VALUE_UID_1,
            UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, STANDARD_NUMBER, null)), UDM_VALUE_UID_1, UDM_VALUE_UID_3,
            UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, "5555", null)), UDM_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_PART, null)), UDM_VALUE_UID_1, UDM_VALUE_UID_2,
            UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, new BigDecimal("4.0000000000"), null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, new BigDecimal("6.0000000000"), null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, PRICE, null)), UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceInUsdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_IN_USD, null)), UDM_VALUE_UID_1,
            UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceInUsdExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, PRICE_IN_USD, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceInUsdExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, PRICE_IN_USD, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceInUsdExpression(new FilterExpression<>(
            FilterOperatorEnum.IS_NULL, PRICE_IN_USD, null)), UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 50, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 20, null)), UDM_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 20, null)), UDM_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, 20, null)), UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setPubType(createPubType(null, null)), UDM_VALUE_UID_1,
            UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setPubType(createPubType("BK", "BOOK")), UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setComment(COMMENT), UDM_VALUE_UID_1, UDM_VALUE_UID_2,
            UDM_VALUE_UID_3, UDM_VALUE_UID_4);
    }

    @Test
    public void testFindDtosByFilterLastValuePeriods() {
        assertFilteringFindDtosByFilter(filter -> filter.setLastValuePeriods(Sets.newHashSet(PERIOD_1, PERIOD_2)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setLastValuePeriods(Collections.singleton(PERIOD_1)),
            UDM_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setLastValuePeriods(Collections.singleton(PERIOD_2)),
            UDM_VALUE_UID_2);
        assertFilteringFindDtosByFilter(
            filter -> filter.setLastValuePeriods(Collections.singleton(FilterOperatorEnum.IS_NULL.name())),
            UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(
            filter -> filter.setLastValuePeriods(Collections.singleton(FilterOperatorEnum.IS_NOT_NULL.name())),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2);
    }

    @Test
    public void testFindCountByFilter() {
        assertFilteringFindCountByFilter(filter -> filter.setPeriods(new HashSet<>(Arrays.asList(201506, 202112))), 3);
        assertFilteringFindCountByFilter(filter -> filter.setStatus(UdmValueStatusEnum.PRELIM_RESEARCH_COMPLETE), 1);
        assertFilteringFindCountByFilter(filter -> filter.setCurrency(new Currency("USD", "US Dollar")), 1);
        assertFilteringFindCountByFilter(filter -> filter.setAssignees(new HashSet<>(
            Arrays.asList("wjohn@copyright.com", "ajohn@copyright.com"))), 3);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInst(306985899L), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, "Tenside", null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setRhAccountNumber(1000002859L), 5);
        assertFilteringFindCountByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, STANDARD_NUMBER, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, "5555", null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_PART, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setPriceExpression(new FilterExpression<>(
            FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, new BigDecimal("4.0000000000"), null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setPriceExpression(new FilterExpression<>(
            FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, new BigDecimal("6.0000000000"), null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, PRICE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setPriceInUsdExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_IN_USD, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setPriceInUsdExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, PRICE_IN_USD, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setPriceInUsdExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, PRICE_IN_USD, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setPriceInUsdExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, PRICE_IN_USD, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 50, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 20, null)), 1);
        assertFilteringFindCountByFilter(
            filter -> filter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 20, null)), 1);
        assertFilteringFindCountByFilter(
            filter -> filter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.IS_NULL, 20, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setPubType(createPubType(null, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setPubType(createPubType("BK", "BOOK")), 1);
        assertFilteringFindCountByFilter(filter -> filter.setComment(COMMENT), 4);
    }

    @Test
    public void testFindCountByFilterLastValuePeriods() {
        assertFilteringFindCountByFilter(
            filter -> filter.setLastValuePeriods(Sets.newHashSet(PERIOD_1, PERIOD_2)), 2);
        assertFilteringFindCountByFilter(
            filter -> filter.setLastValuePeriods(Collections.singleton(PERIOD_1)), 1);
        assertFilteringFindCountByFilter(
            filter -> filter.setLastValuePeriods(Collections.singleton(PERIOD_2)), 1);
        assertFilteringFindCountByFilter(
            filter -> filter.setLastValuePeriods(Collections.singleton(FilterOperatorEnum.IS_NULL.name())), 3);
        assertFilteringFindCountByFilter(
            filter -> filter.setLastValuePeriods(Collections.singleton(FilterOperatorEnum.IS_NOT_NULL.name())), 2);
    }

    @Test
    public void testSortingFindDtosByFilter() {
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "detailId");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_7, UDM_VALUE_UID_6, "valuePeriod");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_7, UDM_VALUE_UID_6, "assignee");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "status");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "rhAccountNumber");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_7, UDM_VALUE_UID_6, "rhName");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_7, UDM_VALUE_UID_6, "wrWrkInst");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_7, UDM_VALUE_UID_6, "systemTitle");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "systemStandardNumber");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_7, UDM_VALUE_UID_6, "standardNumberType");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_7, UDM_VALUE_UID_6, "publicationType");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "price");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_7, UDM_VALUE_UID_6, "currency");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "priceType");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "priceAccessType");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "priceYear");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "priceComment");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "priceFlag");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "priceInUsd");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "currencyExchangeRate");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "currencyExchangeRateDate");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "content");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "contentComment");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "contentFlag");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "comment");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_7, UDM_VALUE_UID_6, "updateDate");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_7, UDM_VALUE_UID_6, "updateUser");
    }

    @Test
    public void testUpdateAssignee() {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setPeriods(ImmutableSet.of(202106));
        filter.setComment("comment_assignment_");
        UdmValueDto udmValueDto = udmValueRepository.findDtosByFilter(filter, null, null).get(0);
        assertNull(udmValueDto.getAssignee());
        udmValueRepository
            .updateAssignee(Collections.singleton("17c79b33-0949-484f-aea4-1f765cc1c019"), ASSIGNEE, USER_NAME);
        udmValueDto = udmValueRepository.findDtosByFilter(filter, null, null).get(0);
        assertEquals(ASSIGNEE, udmValueDto.getAssignee());
    }

    @Test
    public void testUpdateAssigneeToNull() {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setPeriods(ImmutableSet.of(202112));
        filter.setComment("comment_assignment_3");
        UdmValueDto udmValueDto = udmValueRepository.findDtosByFilter(filter, null, null).get(0);
        assertEquals(ASSIGNEE, udmValueDto.getAssignee());
        udmValueRepository
            .updateAssignee(Collections.singleton("b8da90e6-f4f3-4782-860a-2c061f858574"), null, USER_NAME);
        udmValueDto = udmValueRepository.findDtosByFilter(filter, null, null).get(0);
        assertNull(udmValueDto.getAssignee());
    }

    @Test
    public void testFindAssignees() {
        assertEquals(Arrays.asList("ajohn@copyright.com", "djohn@copyright.com", "ejohn@copyright.com",
            "jjohn@copyright.com", "wjohn@copyright.com"), udmValueRepository.findAssignees());
    }

    private void verifyValueDto(UdmValueDto expectedValue, UdmValueDto actualValue, boolean isValidateDates) {
        assertEquals(expectedValue.getId(), actualValue.getId());
        assertEquals(expectedValue.getValuePeriod(), actualValue.getValuePeriod());
        assertEquals(expectedValue.getStatus(), actualValue.getStatus());
        assertEquals(expectedValue.getAssignee(), actualValue.getAssignee());
        assertEquals(expectedValue.getRhAccountNumber(), actualValue.getRhAccountNumber());
        assertEquals(expectedValue.getPublicationType(), actualValue.getPublicationType());
        assertEquals(expectedValue.getRhName(), actualValue.getRhName());
        assertEquals(expectedValue.getWrWrkInst(), actualValue.getWrWrkInst());
        assertEquals(expectedValue.getSystemTitle(), actualValue.getSystemTitle());
        assertEquals(expectedValue.getSystemStandardNumber(), actualValue.getSystemStandardNumber());
        assertEquals(expectedValue.getStandardNumberType(), actualValue.getStandardNumberType());
        assertEquals(expectedValue.getPrice(), actualValue.getPrice());
        assertEquals(expectedValue.getPriceInUsd(), actualValue.getPriceInUsd());
        assertEquals(expectedValue.getContent(), actualValue.getContent());
        assertEquals(expectedValue.getCurrency(), actualValue.getCurrency());
        assertEquals(expectedValue.getCurrencyExchangeRate(), actualValue.getCurrencyExchangeRate());
        assertEquals(expectedValue.getContentUnitPrice(), actualValue.getContentUnitPrice());
        assertEquals(expectedValue.isContentFlag(), actualValue.isContentFlag());
        assertEquals(expectedValue.getContentComment(), actualValue.getContentComment());
        assertEquals(expectedValue.getComment(), actualValue.getComment());
        assertEquals(expectedValue.getUpdateUser(), actualValue.getUpdateUser());
        assertEquals(expectedValue.getCreateUser(), actualValue.getCreateUser());
        if (isValidateDates) {
            assertEquals(expectedValue.getUpdateDate(), actualValue.getUpdateDate());
            assertEquals(expectedValue.getCreateDate(), actualValue.getCreateDate());
        }
    }

    private void assertFilteringFindDtosByFilter(Consumer<UdmValueFilter> consumer, String... usageIds) {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setRhAccountNumber(1000002859L);
        consumer.accept(filter);
        List<UdmValueDto> usages = udmValueRepository.findDtosByFilter(filter, null, buildSort());
        assertEquals(usageIds.length, usages.size());
        IntStream.range(0, usageIds.length)
            .forEach(index -> assertEquals(usageIds[index], usages.get(index).getId()));
    }

    private void assertFilteringFindCountByFilter(Consumer<UdmValueFilter> consumer, int count) {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setRhAccountNumber(1000002859L);
        consumer.accept(filter);
        int usagesCount = udmValueRepository.findCountByFilter(filter);
        assertEquals(count, usagesCount);
    }

    private void assertSortingFindDtosByFilter(String detailIdAsc, String detailIdDesc, String sortProperty) {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setAssignees(new HashSet<>(Arrays.asList("djohn@copyright.com", "ejohn@copyright.com")));
        List<UdmValueDto> usageDtos =
            udmValueRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Direction.ASC));
        assertEquals(detailIdAsc, usageDtos.get(0).getId());
        usageDtos =
            udmValueRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Direction.DESC));
        assertEquals(detailIdDesc, usageDtos.get(0).getId());
    }

    private Sort buildSort() {
        return new Sort("detailId", Sort.Direction.ASC);
    }

    private UdmValue buildUdmValue() {
        UdmValue value = new UdmValue();
        value.setId("94e6d604-e1d6-49a3-bac5-c266793beda8");
        value.setPeriod(209906);
        value.setRhAccountNumber(100008622L);
        value.setStatus(UdmValueStatusEnum.NEW);
        value.setWrWrkInst(327005896L);
        value.setStandardNumber("1245-8969");
        value.setStandardNumberType("ISBN13");
        value.setSystemTitle("Pink dolphins in real life");
        value.setUpdateUser(USER_NAME);
        value.setCreateUser(USER_NAME);
        return value;
    }

    private List<UdmValueDto> loadExpectedValueDto(String fileName) {
        List<UdmValueDto> udmUsageDtos = new ArrayList<>();
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            udmUsageDtos.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<UdmValueDto>>() {
            }));
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return udmUsageDtos;
    }

    private PublicationType createPubType(String name, String description) {
        PublicationType publicationType = new PublicationType();
        publicationType.setName(name);
        publicationType.setDescription(description);
        return publicationType;
    }
}
