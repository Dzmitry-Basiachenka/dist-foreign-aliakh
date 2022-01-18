package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmValue;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineValueRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmValueRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

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
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class UdmValueRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "udm-value-repository-integration-test/";
    private static final String FIND_DTOS_BY_FILTER = FOLDER_NAME + "find-dtos-by-filter.groovy";
    private static final String UDM_VALUE_UID_1 = "07026f4a-8a21-4529-97ad-5dab5c92bcce";
    private static final String UDM_VALUE_UID_2 = "2ffbedd7-708c-4cc5-a72b-5d5f962401ab";
    private static final String UDM_VALUE_UID_3 = "833abadc-53cd-47b5-a7d6-8ba08368e636";
    private static final String UDM_VALUE_UID_4 = "dae1b2c9-7266-4b11-8373-eea9504a9819";
    private static final String UDM_VALUE_UID_5 = "e1cbd88f-7b0f-4f92-b6f4-73faadeb4501";
    private static final String UDM_VALUE_UID_6 = "469e81b1-2e16-481d-a336-89725c6237ce";
    private static final String UDM_VALUE_UID_7 = "5add051a-c4be-475b-9542-e31a05be1eb1";
    private static final String ASSIGNEE_1 = "jjohn@copyright.com";
    private static final String ASSIGNEE_2 = "wjohn@copyright.com";
    private static final String ASSIGNEE_3 = "ajohn@copyright.com";
    private static final String UNASSIGNED = "Unassigned";
    private static final String PERIOD_1 = "201406";
    private static final String PERIOD_2 = "202106";
    private static final String USER_NAME = "jjohn@copyright.com";
    private static final Long WR_WRK_INST = 306985899L;
    private static final String SYSTEM_TITLE = "Tenside, surfactants, detergents";
    private static final String SYSTEM_TITLE_DIFFERENT_CASE = "TensIDE, SurfacTants, detErgentS";
    private static final String SYSTEM_TITLE_FRAGMENT = "Tenside";
    private static final String SYSTEM_TITLE_WITH_METASYMBOLS =
        "Colloids and  libero !@#$%^&*()_+-=?/\\'\"}{][<>convallis. B, Biointerfaces";
    private static final String STANDARD_NUMBER = "1873-7773";
    private static final String STANDARD_NUMBER_DIFFERENT_CASE = "1008902112377633Xx";
    private static final String STANDARD_NUMBER_FRAGMENT = "633XX";
    private static final String STANDARD_NUMBER_WITH_METASYMBOLS = "1008902112377654XX !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final Long RH_ACCOUNT_NUMBER = 1000002859L;
    private static final String RH_NAME = "John Wiley & Sons - Books";
    private static final String RH_NAME_DIFFERENT_CASE = "JoHN WileY & Sons - BOOKS";
    private static final String RH_NAME_FRAGMENT = "John Wiley";
    private static final String RH_NAME_WITH_METASYMBOLS = "John Wiley & Sons - Books !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final String PRICE_COMMENT = "price comment 1";
    private static final String PRICE_COMMENT_DIFFERENT_CASE = "priCE COmment 1";
    private static final String PRICE_COMMENT_FRAGMENT = "riCE COmmen";
    private static final String PRICE_COMMENT_WITH_METASYMBOLS = "price comment 2 !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final String LAST_PRICE_COMMENT = "price comment 1";
    private static final String LAST_PRICE_COMMENT_DIFFERENT_CASE = "priCE COmment 1";
    private static final String LAST_PRICE_COMMENT_FRAGMENT = "riCE COmmen";
    private static final String LAST_PRICE_COMMENT_WITH_METASYMBOLS = "price comment 2 !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final String CONTENT_COMMENT = "content comment 1";
    private static final String CONTENT_COMMENT_DIFFERENT_CASE = "content COmment 1";
    private static final String CONTENT_COMMENT_FRAGMENT = "onteNT COmmen";
    private static final String CONTENT_COMMENT_WITH_METASYMBOLS = "content comment 2 !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final String LAST_CONTENT_COMMENT = "content comment 1";
    private static final String LAST_CONTENT_COMMENT_DIFFERENT_CASE = "content COmment 1";
    private static final String LAST_CONTENT_COMMENT_FRAGMENT = "onteNT COmmen";
    private static final String LAST_CONTENT_COMMENT_WITH_METASYMBOLS = "content comment 2 !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final String COMMENT_FRAGMENT = "ommen";
    private static final BigDecimal PRICE_IN_USD = new BigDecimal("2.5000000000");
    private static final BigDecimal PRICE = new BigDecimal("5.0000000000");
    private static final String BOOK = "Book";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Autowired
    private IUdmValueRepository udmValueRepository;
    @Autowired
    private IUdmBaselineValueRepository udmBaselineValueRepository;

    @Test
    @TestData(fileName = "rollback-only.groovy")
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
    @TestData(fileName = FOLDER_NAME + "update.groovy")
    public void testUpdate() {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setPeriods(ImmutableSet.of(211212));
        UdmValueDto originalValue = udmValueRepository.findDtosByFilter(filter, null, null).get(0);
        originalValue.setStatus(UdmValueStatusEnum.NEW);
        originalValue.setPublicationType(buildPublicationType());
        originalValue.setPriceSource("http://commodity.com");
        originalValue.setPrice(new BigDecimal("61.0000000000"));
        originalValue.setCurrency("USD");
        originalValue.setPriceType("Institution");
        originalValue.setPriceAccessType("Online only");
        originalValue.setPriceYear(2111);
        originalValue.setPriceComment("Price Comment");
        originalValue.setPriceInUsd(new BigDecimal("61.0000000000"));
        originalValue.setPriceFlag(true);
        originalValue.setCurrencyExchangeRate(new BigDecimal("1.0000000000"));
        originalValue.setCurrencyExchangeRateDate(LocalDate.of(2021, 10, 21));
        originalValue.setContentSource("http://commodity.com/page/1");
        originalValue.setContent(new BigDecimal("1.0000000000"));
        originalValue.setContentComment("Content Comment");
        originalValue.setContentFlag(true);
        originalValue.setContentUnitPrice(new BigDecimal("61.0000000000"));
        originalValue.setComment("Comment");
        originalValue.setUpdateUser("user@copyright.com");
        udmValueRepository.update(originalValue);
        verifyValueDto(originalValue, udmValueRepository.findDtosByFilter(filter, null, null).get(0),
            false);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-fields.groovy")
    public void testFindPeriods() {
        List<Integer> expectedPeriods = Arrays.asList(202112, 202106, 201512, 201506, 201406, 201106, 201006);
        List<Integer> actualPeriods = udmValueRepository.findPeriods();
        assertFalse(actualPeriods.isEmpty());
        assertEquals(expectedPeriods, actualPeriods);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByAllFilters() {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setPeriods(Collections.singleton(201506));
        filter.setStatus(UdmValueStatusEnum.NEW);
        filter.setAssignees(Collections.singleton(ASSIGNEE_1));
        filter.setLastValuePeriods(Collections.singleton(FilterOperatorEnum.IS_NULL.name()));
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null));
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, STANDARD_NUMBER, null));
        filter.setRhAccountNumberExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_ACCOUNT_NUMBER, null));
        filter.setRhNameExpression(new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_FRAGMENT, null));
        filter.setPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null));
        filter.setPriceInUsdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_IN_USD, null));
        filter.setPriceFlag(true);
        filter.setPriceCommentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_COMMENT, null));
        filter.setLastPriceFlag(null);
        filter.setLastPriceCommentExpression(new FilterExpression<>());
        filter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 50, null));
        filter.setContentFlag(true);
        filter.setContentCommentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_COMMENT, null));
        filter.setLastContentFlag(null);
        filter.setLastContentCommentExpression(new FilterExpression<>());
        filter.setPubType(null);
        filter.setLastPubType(null);
        filter.setComment(COMMENT_FRAGMENT);
        assertEquals(1, udmValueRepository.findCountByFilter(filter));
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByAllFilters() {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setPeriods(Collections.singleton(201506));
        filter.setStatus(UdmValueStatusEnum.NEW);
        filter.setAssignees(Collections.singleton(ASSIGNEE_1));
        filter.setLastValuePeriods(Collections.singleton(FilterOperatorEnum.IS_NULL.name()));
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null));
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, STANDARD_NUMBER, null));
        filter.setRhAccountNumberExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_ACCOUNT_NUMBER, null));
        filter.setRhNameExpression(new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_FRAGMENT, null));
        filter.setPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null));
        filter.setPriceInUsdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_IN_USD, null));
        filter.setPriceFlag(true);
        filter.setPriceCommentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_COMMENT, null));
        filter.setLastPriceFlag(null);
        filter.setLastPriceCommentExpression(new FilterExpression<>());
        filter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 50, null));
        filter.setContentFlag(true);
        filter.setContentCommentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_COMMENT, null));
        filter.setLastContentFlag(null);
        filter.setLastContentCommentExpression(new FilterExpression<>());
        filter.setPubType(null);
        filter.setLastPubType(null);
        filter.setComment(COMMENT_FRAGMENT);
        List<UdmValueDto> values = udmValueRepository.findDtosByFilter(filter, null, buildSort());
        assertEquals(1, values.size());
        verifyValueDto(loadExpectedValueDto("json/udm/udm_value_dto_dae1b2c9.json").get(0), values.get(0), true);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByAdditionalFilter() {
        assertFilteringFindDtosByFilter(filter -> filter.setAssignees(Sets.newHashSet(ASSIGNEE_2, UNASSIGNED)),
            UDM_VALUE_UID_2, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setAssignees(Collections.singleton(ASSIGNEE_2)),
            UDM_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setAssignees(Collections.singleton(ASSIGNEE_3)),
            UDM_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setAssignees(Collections.singleton(UNASSIGNED)),
            UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastValuePeriods(Sets.newHashSet(PERIOD_1, PERIOD_2)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setLastValuePeriods(Collections.singleton(PERIOD_1)),
            UDM_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setLastValuePeriods(Collections.singleton(PERIOD_2)),
            UDM_VALUE_UID_2);
        assertFilteringFindDtosByFilter(
            filter -> filter.setLastValuePeriods(Collections.singleton(FilterOperatorEnum.IS_NULL.name())),
            UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(
            filter -> filter.setLastValuePeriods(Collections.singleton(FilterOperatorEnum.IS_NOT_NULL.name())),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setCurrency(new Currency("USD", "US Dollar")),
            UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceFlag(null),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceFlag(true), UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceFlag(false), UDM_VALUE_UID_1, UDM_VALUE_UID_2,
            UDM_VALUE_UID_3, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastPriceFlag(null),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastPriceFlag(true), UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastPriceFlag(false), UDM_VALUE_UID_1, UDM_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setContentFlag(null),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setContentFlag(true), UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setContentFlag(false), UDM_VALUE_UID_1, UDM_VALUE_UID_2,
            UDM_VALUE_UID_3, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastContentFlag(null),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastContentFlag(true), UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastContentFlag(false), UDM_VALUE_UID_1, UDM_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setPubType(createPubType("BK", BOOK)), UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setLastPubType(createPubType(null, null)), UDM_VALUE_UID_1,
            UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setLastPubType(createPubType("BK", BOOK)),
            UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setComment(COMMENT_FRAGMENT), UDM_VALUE_UID_1, UDM_VALUE_UID_2,
            UDM_VALUE_UID_3, UDM_VALUE_UID_4);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterWrWrkInst() {
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 306985899L, null)),
            UDM_VALUE_UID_2, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, 306985899L, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 123456789L, null)),
            UDM_VALUE_UID_2, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 123456789L, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 306985899L, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 306985899L, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 123456789L, 306985899L)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterSystemTitle() {
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null)),
            UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_WITH_METASYMBOLS, null)),
            UDM_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_FRAGMENT, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_WITH_METASYMBOLS, null)),
            UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE, null)),
            UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_FRAGMENT, null)),
            UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_WITH_METASYMBOLS, null)),
            UDM_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterSystemStandardNumber() {
        assertFilteringFindDtosByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, STANDARD_NUMBER, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, STANDARD_NUMBER_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, STANDARD_NUMBER_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, STANDARD_NUMBER_WITH_METASYMBOLS, null)),
            UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, STANDARD_NUMBER, null)),
            UDM_VALUE_UID_2, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, STANDARD_NUMBER_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, STANDARD_NUMBER_FRAGMENT, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, STANDARD_NUMBER_WITH_METASYMBOLS, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, STANDARD_NUMBER, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, STANDARD_NUMBER_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, STANDARD_NUMBER_FRAGMENT, null)),
            UDM_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, STANDARD_NUMBER_WITH_METASYMBOLS, null)),
            UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterRhAccountNumber() {
        assertFilteringFindDtosByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_ACCOUNT_NUMBER, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_ACCOUNT_NUMBER, null)),
            UDM_VALUE_UID_6, UDM_VALUE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, RH_ACCOUNT_NUMBER, null)),
            UDM_VALUE_UID_6, UDM_VALUE_UID_7);
        assertFilteringFindDtosByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, RH_ACCOUNT_NUMBER, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_6, UDM_VALUE_UID_7, UDM_VALUE_UID_3, UDM_VALUE_UID_4,
            UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, RH_ACCOUNT_NUMBER, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, RH_ACCOUNT_NUMBER, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, RH_ACCOUNT_NUMBER, 1000010077)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_6, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_6, UDM_VALUE_UID_7, UDM_VALUE_UID_3, UDM_VALUE_UID_4,
            UDM_VALUE_UID_5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterRhName() {
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME_WITH_METASYMBOLS, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_NAME, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_NAME_DIFFERENT_CASE, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_NAME_FRAGMENT, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_NAME_WITH_METASYMBOLS, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_FRAGMENT, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_WITH_METASYMBOLS, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterPriceComment() {
        assertFilteringFindDtosByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_COMMENT, null)),
            UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_COMMENT_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_COMMENT_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_COMMENT_WITH_METASYMBOLS, null)),
            UDM_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, PRICE_COMMENT, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, PRICE_COMMENT_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, PRICE_COMMENT_FRAGMENT, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, PRICE_COMMENT_WITH_METASYMBOLS, null)),
            UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, PRICE_COMMENT, null)),
            UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, PRICE_COMMENT_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, PRICE_COMMENT_FRAGMENT, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, PRICE_COMMENT_WITH_METASYMBOLS, null)),
            UDM_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)),
            UDM_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterLastPriceComment() {
        assertFilteringFindDtosByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_PRICE_COMMENT, null)),
            UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_PRICE_COMMENT_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_PRICE_COMMENT_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_PRICE_COMMENT_WITH_METASYMBOLS, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LAST_PRICE_COMMENT, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LAST_PRICE_COMMENT_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LAST_PRICE_COMMENT_FRAGMENT, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LAST_PRICE_COMMENT_WITH_METASYMBOLS, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LAST_PRICE_COMMENT, null)),
            UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LAST_PRICE_COMMENT_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LAST_PRICE_COMMENT_FRAGMENT, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LAST_PRICE_COMMENT_WITH_METASYMBOLS, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)),
            UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterContentComment() {
        assertFilteringFindDtosByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_COMMENT, null)),
            UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_COMMENT_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_COMMENT_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_COMMENT_WITH_METASYMBOLS, null)),
            UDM_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, CONTENT_COMMENT, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, CONTENT_COMMENT_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, CONTENT_COMMENT_FRAGMENT, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, CONTENT_COMMENT_WITH_METASYMBOLS, null)),
            UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, CONTENT_COMMENT, null)),
            UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, CONTENT_COMMENT_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, CONTENT_COMMENT_FRAGMENT, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, CONTENT_COMMENT_WITH_METASYMBOLS, null)),
            UDM_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)),
            UDM_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterLastContentComment() {
        assertFilteringFindDtosByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_CONTENT_COMMENT, null)),
            UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_CONTENT_COMMENT_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_CONTENT_COMMENT_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_CONTENT_COMMENT_WITH_METASYMBOLS, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LAST_CONTENT_COMMENT, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LAST_CONTENT_COMMENT_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LAST_CONTENT_COMMENT_FRAGMENT, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LAST_CONTENT_COMMENT_WITH_METASYMBOLS, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_4, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LAST_CONTENT_COMMENT, null)),
            UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LAST_CONTENT_COMMENT_DIFFERENT_CASE, null)),
            UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LAST_CONTENT_COMMENT_FRAGMENT, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_5);
        assertFilteringFindDtosByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LAST_CONTENT_COMMENT_WITH_METASYMBOLS, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)),
            UDM_VALUE_UID_3, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)),
            UDM_VALUE_UID_1, UDM_VALUE_UID_2, UDM_VALUE_UID_5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByAdditionalFilterWithOperators() {
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
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByBasicFilter() {
        assertFilteringFindDtosByFilter(filter -> filter.setPeriods(new HashSet<>(Arrays.asList(201506, 202112))),
            UDM_VALUE_UID_2, UDM_VALUE_UID_4);
        assertFilteringFindDtosByFilter(filter -> filter.setStatus(UdmValueStatusEnum.PRELIM_RESEARCH_COMPLETE),
            UDM_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setPubType(createPubType(null, null)), UDM_VALUE_UID_1,
            UDM_VALUE_UID_2, UDM_VALUE_UID_3, UDM_VALUE_UID_5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilter() {
        assertFilteringFindCountByFilter(filter -> filter.setPeriods(Sets.newHashSet(201506, 202112)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setStatus(UdmValueStatusEnum.PRELIM_RESEARCH_COMPLETE), 1);
        assertFilteringFindCountByFilter(filter -> filter.setCurrency(new Currency("USD", "US Dollar")), 1);
        assertFilteringFindCountByFilter(filter -> filter.setAssignees(Sets.newHashSet(ASSIGNEE_2, UNASSIGNED)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setAssignees(Collections.singleton(ASSIGNEE_2)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setAssignees(Collections.singleton(ASSIGNEE_3)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setAssignees(Collections.singleton(UNASSIGNED)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setLastValuePeriods(Sets.newHashSet(PERIOD_1, PERIOD_2)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setLastValuePeriods(Collections.singleton(PERIOD_1)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setLastValuePeriods(Collections.singleton(PERIOD_2)), 1);
        assertFilteringFindCountByFilter(
            filter -> filter.setLastValuePeriods(Collections.singleton(FilterOperatorEnum.IS_NULL.name())), 2);
        assertFilteringFindCountByFilter(
            filter -> filter.setLastValuePeriods(Collections.singleton(FilterOperatorEnum.IS_NOT_NULL.name())), 3);
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
        assertFilteringFindCountByFilter(filter -> filter.setPriceFlag(null), 5);
        assertFilteringFindCountByFilter(filter -> filter.setPriceFlag(true), 1);
        assertFilteringFindCountByFilter(filter -> filter.setPriceFlag(false), 4);
        assertFilteringFindCountByFilter(filter -> filter.setLastPriceFlag(null), 5);
        assertFilteringFindCountByFilter(filter -> filter.setLastPriceFlag(true), 1);
        assertFilteringFindCountByFilter(filter -> filter.setLastPriceFlag(false), 2);
        assertFilteringFindCountByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 50, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 20, null)), 1);
        assertFilteringFindCountByFilter(
            filter -> filter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 20, null)), 1);
        assertFilteringFindCountByFilter(
            filter -> filter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.IS_NULL, 20, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentFlag(null), 5);
        assertFilteringFindCountByFilter(filter -> filter.setContentFlag(true), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentFlag(false), 4);
        assertFilteringFindCountByFilter(filter -> filter.setLastContentFlag(null), 5);
        assertFilteringFindCountByFilter(filter -> filter.setLastContentFlag(true), 1);
        assertFilteringFindCountByFilter(filter -> filter.setLastContentFlag(false), 2);
        assertFilteringFindCountByFilter(filter -> filter.setPubType(createPubType(null, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setPubType(createPubType("BK", BOOK)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setLastPubType(createPubType(null, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setLastPubType(createPubType("BK", BOOK)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setComment(COMMENT_FRAGMENT), 4);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterWrWrkInst() {
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, 306985899L, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, 306985899L, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 123456789L, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 123456789L, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 306985899L, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 306985899L, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, 123456789L, 306985899L)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterSystemTitle() {
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_DIFFERENT_CASE, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_FRAGMENT, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_DIFFERENT_CASE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_FRAGMENT, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_WITH_METASYMBOLS, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_DIFFERENT_CASE, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_FRAGMENT, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterSystemStandardNumber() {
        assertFilteringFindCountByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, STANDARD_NUMBER, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, STANDARD_NUMBER_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, STANDARD_NUMBER_FRAGMENT, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, STANDARD_NUMBER_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, STANDARD_NUMBER, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, STANDARD_NUMBER_DIFFERENT_CASE, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, STANDARD_NUMBER_FRAGMENT, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, STANDARD_NUMBER_WITH_METASYMBOLS, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, STANDARD_NUMBER, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, STANDARD_NUMBER_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, STANDARD_NUMBER_FRAGMENT, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, STANDARD_NUMBER_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterRhAccountNumber() {
        assertFilteringFindCountByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_ACCOUNT_NUMBER, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_ACCOUNT_NUMBER, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, RH_ACCOUNT_NUMBER, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, RH_ACCOUNT_NUMBER, null)), 7);
        assertFilteringFindCountByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, RH_ACCOUNT_NUMBER, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, RH_ACCOUNT_NUMBER, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, RH_ACCOUNT_NUMBER, 1000010077)), 6);
        assertFilteringFindCountByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 7);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterRhName() {
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME_DIFFERENT_CASE, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME_FRAGMENT, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME_WITH_METASYMBOLS, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_NAME, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_NAME_DIFFERENT_CASE, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_NAME_FRAGMENT, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, RH_NAME_WITH_METASYMBOLS, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_DIFFERENT_CASE, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_FRAGMENT, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, RH_NAME_WITH_METASYMBOLS, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setRhNameExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 5);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterPriceComment() {
        assertFilteringFindCountByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_COMMENT, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_COMMENT_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_COMMENT_FRAGMENT, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_COMMENT_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, PRICE_COMMENT, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, PRICE_COMMENT_DIFFERENT_CASE, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, PRICE_COMMENT_FRAGMENT, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, PRICE_COMMENT_WITH_METASYMBOLS, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, PRICE_COMMENT, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, PRICE_COMMENT_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, PRICE_COMMENT_FRAGMENT, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, PRICE_COMMENT_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 4);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterLastPriceComment() {
        assertFilteringFindCountByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_PRICE_COMMENT, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_PRICE_COMMENT_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_PRICE_COMMENT_FRAGMENT, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_PRICE_COMMENT_WITH_METASYMBOLS, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LAST_PRICE_COMMENT, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LAST_PRICE_COMMENT_DIFFERENT_CASE, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LAST_PRICE_COMMENT_FRAGMENT, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LAST_PRICE_COMMENT_WITH_METASYMBOLS, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LAST_PRICE_COMMENT, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LAST_PRICE_COMMENT_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LAST_PRICE_COMMENT_FRAGMENT, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LAST_PRICE_COMMENT_WITH_METASYMBOLS, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterContentComment() {
        assertFilteringFindCountByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_COMMENT, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_COMMENT_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_COMMENT_FRAGMENT, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_COMMENT_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, CONTENT_COMMENT, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, CONTENT_COMMENT_DIFFERENT_CASE, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, CONTENT_COMMENT_FRAGMENT, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, CONTENT_COMMENT_WITH_METASYMBOLS, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, CONTENT_COMMENT, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, CONTENT_COMMENT_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, CONTENT_COMMENT_FRAGMENT, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, CONTENT_COMMENT_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 4);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterLastContentComment() {
        assertFilteringFindCountByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_CONTENT_COMMENT, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_CONTENT_COMMENT_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_CONTENT_COMMENT_FRAGMENT, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_CONTENT_COMMENT_WITH_METASYMBOLS, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LAST_CONTENT_COMMENT, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LAST_CONTENT_COMMENT_DIFFERENT_CASE, null)), 4);
        assertFilteringFindCountByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LAST_CONTENT_COMMENT_FRAGMENT, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, LAST_CONTENT_COMMENT_WITH_METASYMBOLS, null)), 5);
        assertFilteringFindCountByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LAST_CONTENT_COMMENT, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LAST_CONTENT_COMMENT_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LAST_CONTENT_COMMENT_FRAGMENT, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, LAST_CONTENT_COMMENT_WITH_METASYMBOLS, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testSortingFindDtosByFilter() {
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "valueId");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_7, UDM_VALUE_UID_6, "valuePeriod");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_7, UDM_VALUE_UID_6, "assignee");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "status");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "rhAccountNumber");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_7, UDM_VALUE_UID_6, "rhName");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "wrWrkInst");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_7, UDM_VALUE_UID_6, "systemTitle");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "systemStandardNumber");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_6, "lastValuePeriod");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_6, "lastPubType");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_7, UDM_VALUE_UID_6, "publicationType");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_6, "lastPriceInUsd");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_6, "lastPriceFlag");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_6, "lastPriceSource");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_6, "lastPriceComment");
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
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_6, "lastContent");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_6, "lastContentFlag");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_6, "lastContentSource");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_6, "lastContentComment");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "content");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "contentComment");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "contentFlag");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_6, "contentUnitPrice");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_6, UDM_VALUE_UID_7, "comment");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_7, UDM_VALUE_UID_6, "updateDate");
        assertSortingFindDtosByFilter(UDM_VALUE_UID_7, UDM_VALUE_UID_6, "updateUser");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-assignee.groovy")
    public void testUpdateAssignee() {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setPeriods(ImmutableSet.of(202106));
        filter.setComment("comment_assignment_");
        UdmValueDto udmValueDto = udmValueRepository.findDtosByFilter(filter, null, null).get(0);
        assertNull(udmValueDto.getAssignee());
        udmValueRepository
            .updateAssignee(Collections.singleton("17c79b33-0949-484f-aea4-1f765cc1c019"), ASSIGNEE_1, USER_NAME);
        udmValueDto = udmValueRepository.findDtosByFilter(filter, null, null).get(0);
        assertEquals(ASSIGNEE_1, udmValueDto.getAssignee());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-assignee.groovy")
    public void testUpdateAssigneeToNull() {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setPeriods(ImmutableSet.of(202112));
        filter.setComment("comment_assignment_3");
        UdmValueDto udmValueDto = udmValueRepository.findDtosByFilter(filter, null, null).get(0);
        assertEquals(ASSIGNEE_1, udmValueDto.getAssignee());
        udmValueRepository
            .updateAssignee(Collections.singleton("b8da90e6-f4f3-4782-860a-2c061f858574"), null, USER_NAME);
        udmValueDto = udmValueRepository.findDtosByFilter(filter, null, null).get(0);
        assertNull(udmValueDto.getAssignee());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-fields.groovy")
    public void testFindAssignees() {
        assertEquals(Arrays.asList(ASSIGNEE_3, "djohn@copyright.com", "ejohn@copyright.com",
            ASSIGNEE_1, ASSIGNEE_2), udmValueRepository.findAssignees());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-fields.groovy")
    public void testFindLastValuePeriods() {
        assertEquals(Arrays.asList("202106", "201506", "201406"),
            udmValueRepository.findLastValuePeriods());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "is-allowed-for-publishing.groovy")
    public void testIsAllowedForPublishing() {
        assertFalse(udmValueRepository.isAllowedForPublishing(209106));
        assertFalse(udmValueRepository.isAllowedForPublishing(209206));
        assertFalse(udmValueRepository.isAllowedForPublishing(209306));
        assertFalse(udmValueRepository.isAllowedForPublishing(209406));
        assertTrue(udmValueRepository.isAllowedForPublishing(209506));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "publish-to-baseline.groovy")
    public void testPublishToBaseline() {
        assertEquals(Arrays.asList("81226f4a-6a21-4529-97ad-5dab5c92bcce", "97226f4a-ca11-4529-bb59-5dab5c92b8ce"),
            udmValueRepository.publishToBaseline(211012, USER_NAME));
        assertEquals(Collections.singletonList("fc3e7747-3d3b-4f07-93fb-f66c97d9f737"),
            udmValueRepository.publishToBaseline(211112, USER_NAME));
        UdmBaselineValueFilter filter = new UdmBaselineValueFilter();
        filter.setPeriods(Collections.singleton(211012));
        verifyValueBaselineDto(
            loadExpectedValueBaselineDto("json/udm/udm_value_baseline_dto_1.json"),
            udmBaselineValueRepository.findDtosByFilter(filter, null, null));
        filter.setPeriods(Collections.singleton(211112));
        verifyValueBaselineDto(
            loadExpectedValueBaselineDto("json/udm/udm_value_baseline_dto_2.json"),
            udmBaselineValueRepository.findDtosByFilter(filter, null, null));
    }

    private void verifyValueBaselineDto(List<UdmValueBaselineDto> expectedValues,
                                        List<UdmValueBaselineDto> actualValues) {
        assertEquals(expectedValues.size(), actualValues.size());
        IntStream.range(0, expectedValues.size()).forEach(index -> {
            UdmValueBaselineDto expectedValue = expectedValues.get(index);
            UdmValueBaselineDto actualValue = actualValues.get(index);
            assertEquals(expectedValue.getId(), actualValue.getId());
            assertEquals(expectedValue.getPeriod(), actualValue.getPeriod());
            assertEquals(expectedValue.getWrWrkInst(), actualValue.getWrWrkInst());
            assertEquals(expectedValue.getSystemTitle(), actualValue.getSystemTitle());
            assertEquals(expectedValue.getPublicationType(), actualValue.getPublicationType());
            assertEquals(expectedValue.getPrice(), actualValue.getPrice());
            assertEquals(expectedValue.getPriceFlag(), actualValue.getPriceFlag());
            assertEquals(expectedValue.getContent(), actualValue.getContent());
            assertEquals(expectedValue.getContentUnitPrice(), actualValue.getContentUnitPrice());
            assertEquals(expectedValue.getContentFlag(), actualValue.getContentFlag());
            assertEquals(expectedValue.getComment(), actualValue.getComment());
            assertEquals(expectedValue.getUpdateUser(), actualValue.getUpdateUser());
            assertEquals(expectedValue.getCreateUser(), actualValue.getCreateUser());
        });
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
        assertEquals(expectedValue.getPrice(), actualValue.getPrice());
        assertEquals(expectedValue.getPriceSource(), actualValue.getPriceSource());
        assertEquals(expectedValue.getPriceInUsd(), actualValue.getPriceInUsd());
        assertEquals(expectedValue.isPriceFlag(), actualValue.isPriceFlag());
        assertEquals(expectedValue.getContent(), actualValue.getContent());
        assertEquals(expectedValue.getCurrency(), actualValue.getCurrency());
        assertEquals(expectedValue.getCurrencyExchangeRate(), actualValue.getCurrencyExchangeRate());
        assertEquals(expectedValue.getCurrencyExchangeRateDate(), actualValue.getCurrencyExchangeRateDate());
        assertEquals(expectedValue.getContentUnitPrice(), actualValue.getContentUnitPrice());
        assertEquals(expectedValue.isContentFlag(), actualValue.isContentFlag());
        assertEquals(expectedValue.getContentComment(), actualValue.getContentComment());
        assertEquals(expectedValue.getContentSource(), actualValue.getContentSource());
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
        filter.setRhAccountNumberExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_ACCOUNT_NUMBER, null));
        consumer.accept(filter);
        List<UdmValueDto> usages = udmValueRepository.findDtosByFilter(filter, null, buildSort());
        assertEquals(usageIds.length, usages.size());
        IntStream.range(0, usageIds.length)
            .forEach(index -> assertEquals(usageIds[index], usages.get(index).getId()));
    }

    private void assertFilteringFindCountByFilter(Consumer<UdmValueFilter> consumer, int count) {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setRhAccountNumberExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_ACCOUNT_NUMBER, null));
        consumer.accept(filter);
        int usagesCount = udmValueRepository.findCountByFilter(filter);
        assertEquals(count, usagesCount);
    }

    private void assertSortingFindDtosByFilter(String valueIdAsc, String valueIdDesc, String sortProperty) {
        UdmValueFilter filter = new UdmValueFilter();
        filter.setAssignees(new HashSet<>(Arrays.asList("djohn@copyright.com", "ejohn@copyright.com")));
        List<UdmValueDto> usageDtos =
            udmValueRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Direction.ASC));
        assertEquals(valueIdAsc, usageDtos.get(0).getId());
        usageDtos = udmValueRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Direction.DESC));
        assertEquals(valueIdDesc, usageDtos.get(0).getId());
    }

    private Sort buildSort() {
        return new Sort("valueId", Sort.Direction.ASC);
    }

    private UdmValue buildUdmValue() {
        UdmValue value = new UdmValue();
        value.setId("94e6d604-e1d6-49a3-bac5-c266793beda8");
        value.setPeriod(209906);
        value.setRhAccountNumber(100008622L);
        value.setStatus(UdmValueStatusEnum.NEW);
        value.setWrWrkInst(327005896L);
        value.setStandardNumber("1245-8969");
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

    private List<UdmValueBaselineDto> loadExpectedValueBaselineDto(String fileName) {
        List<UdmValueBaselineDto> valueBaselineDtos = new ArrayList<>();
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            valueBaselineDtos.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<UdmValueBaselineDto>>() {
            }));
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return valueBaselineDtos;
    }

    private PublicationType createPubType(String name, String description) {
        PublicationType publicationType = new PublicationType();
        publicationType.setName(name);
        publicationType.setDescription(description);
        return publicationType;
    }

    private PublicationType buildPublicationType() {
        PublicationType publicationType = new PublicationType();
        publicationType.setId("ad8df236-5200-4acf-be55-cf82cd342f14");
        publicationType.setName("OT");
        publicationType.setDescription("Other");
        return publicationType;
    }
}
