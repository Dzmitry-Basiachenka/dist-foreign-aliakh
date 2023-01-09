package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineValueRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * Integration test for {@link UdmBaselineValueRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/29/2021
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class UdmBaselineValueRepositoryIntegrationTest {

    private static final String FIND_DTOS_BY_FILTER =
        "udm-baseline-value-repository-integration-test/find-dtos-by-filter.groovy";
    private static final String UDM_BASELINE_VALUE_UID_1 = "5cd47c05-4539-4bc6-a71d-f33c67896ce5";
    private static final String UDM_BASELINE_VALUE_UID_2 = "123c31cc-4be5-41b6-9ab3-c36b0ff85d8d";
    private static final String UDM_BASELINE_VALUE_UID_3 = "3e48b712-739d-409a-b077-895e420d89e6";
    private static final Long WR_WRK_INST = 569856369L;
    private static final String SYSTEM_TITLE_1 = "Malcolm Gruel Jr";
    private static final String SYSTEM_TITLE_1_FRAGMENT = "ruel";
    private static final String SYSTEM_TITLE_1_DIFFERENT_CASE = "MalcoLM GRueL JR";
    private static final String SYSTEM_TITLE_2 = "Co and Travor";
    private static final String SYSTEM_TITLE_3_WITH_METASYMBOLS =
        "Colloids and  libero !@#$%^&*()_+-=?/\\'\"}{][<>convallis. B, Biointerfaces";
    private static final BigDecimal PRICE = new BigDecimal("50.0000000000");
    private static final BigDecimal CONTENT = new BigDecimal("2.0000000000");
    private static final BigDecimal CONTENT_UNIT_PRICE = new BigDecimal("25.0000000000");
    private static final String NP = "NP";
    private static final String NEWSPAPER = "Newspaper";
    private static final String COMMENT_1 = "Comment";
    private static final String COMMENT_1_FRAGMENT = "ommen";
    private static final String COMMENT_1_DIFFERENT_CASE = "ComMENT";
    private static final String COMMENT_2 = "UDM value b6ded5b5";
    private static final String COMMENT_3_WITH_METASYMBOLS = "Comment !@#$%^&*()_+-=?/\\'\"}{][<>";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Autowired
    private IUdmBaselineValueRepository udmBaselineValueRepository;

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindPeriods() {
        List<Integer> expectedPeriods = Arrays.asList(211212, 211112, 211012);
        List<Integer> actualPeriods = udmBaselineValueRepository.findPeriods();
        assertFalse(actualPeriods.isEmpty());
        assertEquals(expectedPeriods, actualPeriods);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByAllFilters() {
        UdmBaselineValueFilter filter = new UdmBaselineValueFilter();
        filter.setPeriods(Set.of(211112));
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null));
        filter.setPubTypes(Set.of(createPubType(NP, NEWSPAPER)));
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_1, null));
        filter.setPriceFlagExpression(new FilterExpression<>(FilterOperatorEnum.N));
        filter.setContentFlagExpression(new FilterExpression<>(FilterOperatorEnum.Y));
        filter.setContentUnitPriceFlagExpression(new FilterExpression<>(FilterOperatorEnum.N));
        filter.setPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null));
        filter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT, null));
        filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_UNIT_PRICE, null));
        filter.setCommentExpression(new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMMENT_1_FRAGMENT, null));
        assertEquals(1, udmBaselineValueRepository.findCountByFilter(filter));
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByAllFilters() {
        UdmBaselineValueFilter filter = new UdmBaselineValueFilter();
        filter.setPeriods(Set.of(211112));
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null));
        filter.setPubTypes(Set.of(createPubType(NP, NEWSPAPER)));
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_1, null));
        filter.setPriceFlagExpression(new FilterExpression<>(FilterOperatorEnum.N));
        filter.setContentFlagExpression(new FilterExpression<>(FilterOperatorEnum.Y));
        filter.setContentUnitPriceFlagExpression(new FilterExpression<>(FilterOperatorEnum.N));
        filter.setPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null));
        filter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT, null));
        filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_UNIT_PRICE, null));
        filter.setCommentExpression(new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMMENT_1_FRAGMENT, null));
        List<UdmValueBaselineDto> baselineValues =
            udmBaselineValueRepository.findDtosByFilter(filter, null, null);
        assertEquals(1, baselineValues.size());
        verifyBaselineValueDto(loadExpectedBaselineValueDto("json/udm/udm_baseline_value_dto_123c31cc.json").get(0),
            baselineValues.get(0));
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByBasicFilter() {
        assertFilteringFindDtosByFilter(filter -> filter.setPeriods(ImmutableSet.of(211012, 211212)),
            UDM_BASELINE_VALUE_UID_3, UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setPubTypes(Set.of(createPubType(NP, NEWSPAPER))),
            UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_3, UDM_BASELINE_VALUE_UID_1);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterWrWrkInst() {
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null)), UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, WR_WRK_INST, null)), UDM_BASELINE_VALUE_UID_3,
            UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, WR_WRK_INST, null)), UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, WR_WRK_INST, null)),
            UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, WR_WRK_INST, null)), UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, WR_WRK_INST, null)),
            UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, WR_WRK_INST, 823333789L)), UDM_BASELINE_VALUE_UID_2,
            UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), UDM_BASELINE_VALUE_UID_2,
            UDM_BASELINE_VALUE_UID_3, UDM_BASELINE_VALUE_UID_1);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterSystemTitle() {
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_1, null)), UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_1_DIFFERENT_CASE, null)),
            UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_3_WITH_METASYMBOLS, null)),
            UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_2, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_1_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_1, null)), UDM_BASELINE_VALUE_UID_3,
            UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_1_DIFFERENT_CASE, null)),
            UDM_BASELINE_VALUE_UID_3, UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_3_WITH_METASYMBOLS, null)),
            UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_2, null)), UDM_BASELINE_VALUE_UID_2,
            UDM_BASELINE_VALUE_UID_3, UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_1_FRAGMENT, null)),
            UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_3, UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_1, null)), UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_1_DIFFERENT_CASE, null)),
            UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_3_WITH_METASYMBOLS, null)),
            UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_2, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_1_FRAGMENT, null)),
            UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), UDM_BASELINE_VALUE_UID_2,
            UDM_BASELINE_VALUE_UID_3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterPriceFlag() {
        assertFilteringFindDtosByFilter(filter -> filter.setPriceFlagExpression(
            new FilterExpression<>(FilterOperatorEnum.Y)), UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceFlagExpression(
            new FilterExpression<>(FilterOperatorEnum.N)), UDM_BASELINE_VALUE_UID_2,
            UDM_BASELINE_VALUE_UID_1);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterContentFlag() {
        assertFilteringFindDtosByFilter(filter -> filter.setContentFlagExpression(
            new FilterExpression<>(FilterOperatorEnum.Y)), UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setContentFlagExpression(
            new FilterExpression<>(FilterOperatorEnum.N)), UDM_BASELINE_VALUE_UID_1);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterContentUnitPriceFlag() {
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceFlagExpression(
            new FilterExpression<>(FilterOperatorEnum.Y)), UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceFlagExpression(
            new FilterExpression<>(FilterOperatorEnum.N)), UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_1);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterPrice() {
        assertFilteringFindDtosByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null)), UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, PRICE, null)), UDM_BASELINE_VALUE_UID_3,
            UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, PRICE, null)), UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, PRICE, null)),
            UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, new BigDecimal("55.0000000000"), null)),
            UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, PRICE, null)), UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, new BigDecimal("45.0000000000"),
                new BigDecimal("60.0000000000"))), UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), UDM_BASELINE_VALUE_UID_2,
            UDM_BASELINE_VALUE_UID_3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterContent() {
        assertFilteringFindDtosByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT, null)), UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, CONTENT, null)), UDM_BASELINE_VALUE_UID_3,
            UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, CONTENT, null)), UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, CONTENT, null)),
            UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, new BigDecimal("4.0000000000"), null)),
            UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, CONTENT, null)),
            UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, new BigDecimal("1.0000000000"),
                new BigDecimal("3.0000000000"))), UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), UDM_BASELINE_VALUE_UID_2,
            UDM_BASELINE_VALUE_UID_3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterContentUnitPrice() {
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_UNIT_PRICE, null)), UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, CONTENT_UNIT_PRICE, null)),
            UDM_BASELINE_VALUE_UID_3, UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, new BigDecimal("13.0000000000"), null)),
            UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, new BigDecimal("12.0000000000"),
                null)), UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_3, UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, CONTENT_UNIT_PRICE, null)), UDM_BASELINE_VALUE_UID_3,
            UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, CONTENT_UNIT_PRICE, null)),
            UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_3, UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN,  new BigDecimal("22.0000000000"),
                new BigDecimal("41.0000000000"))), UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), UDM_BASELINE_VALUE_UID_2,
            UDM_BASELINE_VALUE_UID_3, UDM_BASELINE_VALUE_UID_1);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilterComment() {
        assertFilteringFindDtosByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMMENT_1, null)), UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMMENT_1_DIFFERENT_CASE, null)),
            UDM_BASELINE_VALUE_UID_2);
        assertFilteringFindDtosByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMMENT_3_WITH_METASYMBOLS, null)),
            UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMMENT_2, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMMENT_1_FRAGMENT, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMMENT_1, null)), UDM_BASELINE_VALUE_UID_3,
            UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMMENT_1_DIFFERENT_CASE, null)),
            UDM_BASELINE_VALUE_UID_3, UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMMENT_3_WITH_METASYMBOLS, null)),
            UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMMENT_2, null)), UDM_BASELINE_VALUE_UID_2,
            UDM_BASELINE_VALUE_UID_3, UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMMENT_1_FRAGMENT, null)),
            UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_3, UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMMENT_1, null)), UDM_BASELINE_VALUE_UID_2,
            UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMMENT_1_DIFFERENT_CASE, null)),
            UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMMENT_3_WITH_METASYMBOLS, null)),
            UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMMENT_2, null)));
        assertFilteringFindDtosByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMMENT_1_FRAGMENT, null)), UDM_BASELINE_VALUE_UID_2,
            UDM_BASELINE_VALUE_UID_3);
        assertFilteringFindDtosByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), UDM_BASELINE_VALUE_UID_1);
        assertFilteringFindDtosByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), UDM_BASELINE_VALUE_UID_2,
            UDM_BASELINE_VALUE_UID_3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByBasicFilter() {
        assertFilteringFindCountByFilter(filter -> filter.setPeriods(ImmutableSet.of(211012, 211212)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setPubTypes(Set.of(createPubType(NP, NEWSPAPER))), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterWrWrkInst() {
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, WR_WRK_INST, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, WR_WRK_INST, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, WR_WRK_INST, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, WR_WRK_INST, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, WR_WRK_INST, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, WR_WRK_INST, 823333789L)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterSystemTitle() {
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_1, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_1_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_3_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_2, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE_1_FRAGMENT, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_1, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_1_DIFFERENT_CASE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_3_WITH_METASYMBOLS, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_2, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, SYSTEM_TITLE_1_FRAGMENT, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_1, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_1_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_3_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_2, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, SYSTEM_TITLE_1_FRAGMENT, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterPriceFlag() {
        assertFilteringFindCountByFilter(filter -> filter.setPriceFlagExpression(
            new FilterExpression<>(FilterOperatorEnum.Y)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setPriceFlagExpression(
            new FilterExpression<>(FilterOperatorEnum.N)), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterContentFlag() {
        assertFilteringFindCountByFilter(filter -> filter.setContentFlagExpression(
            new FilterExpression<>(FilterOperatorEnum.Y)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setContentFlagExpression(
            new FilterExpression<>(FilterOperatorEnum.N)), 1);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterContentUnitPriceFlag() {
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceFlagExpression(
            new FilterExpression<>(FilterOperatorEnum.Y)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceFlagExpression(
            new FilterExpression<>(FilterOperatorEnum.N)), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterPrice() {
        assertFilteringFindCountByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, PRICE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, PRICE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, PRICE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, new BigDecimal("55.0000000000"), null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, PRICE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, new BigDecimal("45.0000000000"),
                new BigDecimal("60.0000000000"))), 1);
        assertFilteringFindCountByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterContent() {
        assertFilteringFindCountByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, CONTENT, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, CONTENT, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, CONTENT, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, new BigDecimal("4.0000000000"), null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, CONTENT, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN, new BigDecimal("1.0000000000"),
                new BigDecimal("3.0000000000"))), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterContentUnitPrice() {
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_UNIT_PRICE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, CONTENT_UNIT_PRICE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, new BigDecimal("13.0000000000"), null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, new BigDecimal("12.0000000000"),
                null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN, CONTENT_UNIT_PRICE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, CONTENT_UNIT_PRICE, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.BETWEEN,  new BigDecimal("22.0000000000"),
                new BigDecimal("41.0000000000"))), 1);
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 3);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testFindCountByFilterComment() {
        assertFilteringFindCountByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMMENT_1, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMMENT_1_DIFFERENT_CASE, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMMENT_3_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMMENT_2, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, COMMENT_1_FRAGMENT, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMMENT_1, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMMENT_1_DIFFERENT_CASE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMMENT_3_WITH_METASYMBOLS, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMMENT_2, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.DOES_NOT_EQUAL, COMMENT_1_FRAGMENT, null)), 3);
        assertFilteringFindCountByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMMENT_1, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMMENT_1_DIFFERENT_CASE, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMMENT_3_WITH_METASYMBOLS, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMMENT_2, null)), 0);
        assertFilteringFindCountByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.CONTAINS, COMMENT_1_FRAGMENT, null)), 2);
        assertFilteringFindCountByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NULL, null, null)), 1);
        assertFilteringFindCountByFilter(filter -> filter.setCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.IS_NOT_NULL, null, null)), 2);
    }

    @Test
    @TestData(fileName = FIND_DTOS_BY_FILTER)
    public void testSortingFindDtosByFilter() {
        assertSortingFindDtosByFilter(UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_1, "valueId");
        assertSortingFindDtosByFilter(UDM_BASELINE_VALUE_UID_1, UDM_BASELINE_VALUE_UID_3, "period");
        assertSortingFindDtosByFilter(UDM_BASELINE_VALUE_UID_1, UDM_BASELINE_VALUE_UID_3, "wrWrkInst");
        assertSortingFindDtosByFilter(UDM_BASELINE_VALUE_UID_3, UDM_BASELINE_VALUE_UID_1, "systemTitle");
        assertSortingFindDtosByFilter(UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_2, "pubType");
        assertSortingFindDtosByFilter(UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_1, "price");
        assertSortingFindDtosByFilter(UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_3, "priceFlag");
        assertSortingFindDtosByFilter(UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_1, "content");
        assertSortingFindDtosByFilter(UDM_BASELINE_VALUE_UID_1, UDM_BASELINE_VALUE_UID_2, "contentFlag");
        assertSortingFindDtosByFilter(UDM_BASELINE_VALUE_UID_1, UDM_BASELINE_VALUE_UID_2, "contentUnitPrice");
        assertSortingFindDtosByFilter(UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_3, "contentUnitPriceFlag");
        assertSortingFindDtosByFilter(UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_1, "comment");
        assertSortingFindDtosByFilter(UDM_BASELINE_VALUE_UID_2, UDM_BASELINE_VALUE_UID_3, "updateDate");
        assertSortingFindDtosByFilter(UDM_BASELINE_VALUE_UID_3, UDM_BASELINE_VALUE_UID_2, "updateUser");
    }

    private void assertFilteringFindDtosByFilter(Consumer<UdmBaselineValueFilter> consumer,
                                                 String... baselineValueIds) {
        UdmBaselineValueFilter filter = new UdmBaselineValueFilter();
        consumer.accept(filter);
        List<UdmValueBaselineDto> baselineValues =
            udmBaselineValueRepository.findDtosByFilter(filter, null, buildSort());
        assertEquals(baselineValueIds.length, baselineValues.size());
        IntStream.range(0, baselineValueIds.length)
            .forEach(index -> assertEquals(baselineValueIds[index], baselineValues.get(index).getId()));
    }

    private void assertFilteringFindCountByFilter(Consumer<UdmBaselineValueFilter> consumer, int count) {
        UdmBaselineValueFilter filter = new UdmBaselineValueFilter();
        consumer.accept(filter);
        int usagesCount = udmBaselineValueRepository.findCountByFilter(filter);
        assertEquals(count, usagesCount);
    }

    private void verifyBaselineValueDto(UdmValueBaselineDto expectedValue, UdmValueBaselineDto actualValue) {
        assertEquals(expectedValue.getId(), actualValue.getId());
        assertEquals(expectedValue.getPeriod(), actualValue.getPeriod());
        assertEquals(expectedValue.getWrWrkInst(), actualValue.getWrWrkInst());
        assertEquals(expectedValue.getSystemTitle(), actualValue.getSystemTitle());
        assertEquals(expectedValue.getPublicationType(), actualValue.getPublicationType());
        assertEquals(expectedValue.getPrice(), actualValue.getPrice());
        assertEquals(expectedValue.getPriceFlag(), actualValue.getPriceFlag());
        assertEquals(expectedValue.getContent(), actualValue.getContent());
        assertEquals(expectedValue.getContentFlag(), actualValue.getContentFlag());
        assertEquals(expectedValue.getContentUnitPrice(), actualValue.getContentUnitPrice());
        assertEquals(expectedValue.getContentUnitPriceFlag(), actualValue.getContentUnitPriceFlag());
        assertEquals(expectedValue.getComment(), actualValue.getComment());
    }

    private void assertSortingFindDtosByFilter(String detailIdAsc, String detailIdDesc, String sortProperty) {
        UdmBaselineValueFilter filter = new UdmBaselineValueFilter();
        List<UdmValueBaselineDto> usageDtos =
            udmBaselineValueRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Direction.ASC));
        assertEquals(detailIdAsc, usageDtos.get(0).getId());
        usageDtos =
            udmBaselineValueRepository.findDtosByFilter(filter, null, new Sort(sortProperty, Direction.DESC));
        assertEquals(detailIdDesc, usageDtos.get(0).getId());
    }

    private List<UdmValueBaselineDto> loadExpectedBaselineValueDto(String fileName) {
        List<UdmValueBaselineDto> udmUsageDtos = new ArrayList<>();
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            udmUsageDtos.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<UdmValueBaselineDto>>() {
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

    private Sort buildSort() {
        return new Sort("valueId", Sort.Direction.ASC);
    }
}
