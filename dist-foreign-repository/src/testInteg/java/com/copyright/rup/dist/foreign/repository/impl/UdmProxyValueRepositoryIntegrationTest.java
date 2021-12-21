package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.UdmProxyValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmProxyValueRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
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
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Verifies {@link IUdmProxyValueRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/24/2021
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
public class UdmProxyValueRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "udm-proxy-value-repository-integration-test/";
    private static final int PERIOD = 211012;
    private static final String USER_NAME = "user@copyright.com";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Autowired
    private IUdmProxyValueRepository udmProxyValueRepository;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-proxy-values.groovy")
    public void testDeleteProxyValues() {
        UdmProxyValueFilter filter = new UdmProxyValueFilter();
        filter.setPeriods(ImmutableSet.of(PERIOD));
        assertEquals(1, udmProxyValueRepository.findDtosByFilter(filter).size());
        udmProxyValueRepository.deleteProxyValues(PERIOD);
        assertEquals(0, udmProxyValueRepository.findDtosByFilter(filter).size());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "clear-proxy-values.groovy")
    public void testClearProxyValues() {
        findAllValueDtos().forEach(udmValueDto -> assertNotNull(udmValueDto.getContentUnitPrice()));
        udmProxyValueRepository.clearProxyValues(PERIOD, USER_NAME);
        assertEquals(Sets.newHashSet("e6b6588f-df06-4c63-b9c0-4b4f1e711945", "57f0198d-0366-4a8e-a833-e40986a37981"),
            findAllValueDtos().stream()
                .filter(udmValueDto -> Objects.isNull(udmValueDto.getContentUnitPrice()))
                .map(UdmValueDto::getId)
                .collect(Collectors.toSet()));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "insert-proxy-values.groovy")
    public void testInsertProxyValues() {
        UdmProxyValueFilter filter = new UdmProxyValueFilter();
        filter.setPeriods(ImmutableSet.of(PERIOD));
        udmProxyValueRepository.insertProxyValues(PERIOD, USER_NAME);
        verifyProxyValueDtos(loadExpectedProxyValueDtos("json/udm/udm_proxy_value_dto.json"),
            udmProxyValueRepository.findDtosByFilter(filter));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "apply-proxy-values.groovy")
    public void testApplyProxyValues() {
        assertEquals(Arrays.asList("43699543-3287-40e1-a4b8-553e7547deb9", "cf03b058-6189-4885-b2a6-a6de04b2de88"),
            udmProxyValueRepository.applyProxyValues(PERIOD, USER_NAME));
        verifyValueDtos(loadExpectedValueDtos("json/udm/udm_value_dto_43699543.json"),
            findAllValueDtos());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-periods.groovy")
    public void testFindPeriods() {
        assertEquals(Arrays.asList(211012, 211006), udmProxyValueRepository.findPeriods());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-dtos-by-filter.groovy")
    public void testFindDtosByFilter() {
        UdmProxyValueFilter filter = new UdmProxyValueFilter();
        filter.setPeriods(ImmutableSet.of(211012, 211006));
        verifyProxyValueDtos(loadExpectedProxyValueDtos("json/udm/udm_proxy_value_dto_find_by_filter_1.json"),
            udmProxyValueRepository.findDtosByFilter(filter));
        filter.setPubTypeNames(ImmutableSet.of("NP", "BK"));
        verifyProxyValueDtos(loadExpectedProxyValueDtos("json/udm/udm_proxy_value_dto_find_by_filter_1.json"),
            udmProxyValueRepository.findDtosByFilter(filter));
        filter.setPeriods(Collections.emptySet());
        verifyProxyValueDtos(loadExpectedProxyValueDtos("json/udm/udm_proxy_value_dto_find_by_filter_2.json"),
            udmProxyValueRepository.findDtosByFilter(filter));
        filter.setPubTypeNames(Collections.emptySet());
    }

    private void verifyProxyValueDtos(List<UdmProxyValueDto> expectedValues, List<UdmProxyValueDto> actualValues) {
        assertEquals(expectedValues.size(), actualValues.size());
        IntStream.range(0, expectedValues.size()).forEach(index -> {
            UdmProxyValueDto expectedValue = expectedValues.get(index);
            UdmProxyValueDto actualValue = actualValues.get(index);
            assertEquals(expectedValue.getPubTypeName(), actualValue.getPubTypeName());
            assertEquals(expectedValue.getPeriod(), actualValue.getPeriod());
            assertEquals(expectedValue.getContentUnitPrice(), actualValue.getContentUnitPrice());
            assertEquals(expectedValue.getContentUnitPriceCount(), actualValue.getContentUnitPriceCount());
        });
    }

    private List<UdmProxyValueDto> loadExpectedProxyValueDtos(String fileName) {
        List<UdmProxyValueDto> proxyValues = new ArrayList<>();
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            proxyValues.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<UdmProxyValueDto>>() {
            }));
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return proxyValues;
    }

    private void verifyValueDtos(List<UdmValueDto> expectedValues, List<UdmValueDto> actualValues) {
        assertEquals(expectedValues.size(), actualValues.size());
        IntStream.range(0, expectedValues.size()).forEach(index -> {
            UdmValueDto expectedValue = expectedValues.get(index);
            UdmValueDto actualValue = actualValues.get(index);
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
            assertEquals(expectedValue.getVersion(), actualValue.getVersion());
        });
    }

    private List<UdmValueDto> loadExpectedValueDtos(String fileName) {
        List<UdmValueDto> values = new ArrayList<>();
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            values.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<UdmValueDto>>() {
            }));
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return values;
    }

    private List<UdmValueDto> findAllValueDtos() {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(1);
        parameters.put("filter", new UdmValueFilter());
        return sqlSessionTemplate.selectList("IUdmValueMapper.findDtosByFilter", parameters);
    }
}
