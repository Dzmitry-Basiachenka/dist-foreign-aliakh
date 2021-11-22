package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.UdmProxyValueDto;
import com.copyright.rup.dist.foreign.repository.api.IUdmProxyValueRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    private static final int PERIOD = 211012;
    private static final String USER_NAME = "user@copyright.com";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private IUdmProxyValueRepository udmProxyValueRepository;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Test
    @TestData(fileName = "udm-proxy-value-repository-test-data-init-delete-proxy-values.groovy")
    public void testDeleteProxyValues() {
        assertEquals(1, findAllProxyValueDtos().size());
        udmProxyValueRepository.deleteProxyValues(PERIOD);
        assertEquals(0, findAllProxyValueDtos().size());
    }

    @Test
    @TestData(fileName = "udm-proxy-value-repository-test-data-init-insert-proxy-values.groovy")
    public void testInsertProxyValues() {
        udmProxyValueRepository.insertProxyValues(PERIOD, USER_NAME);
        verifyProxyValueDto(
            loadExpectedProxyValueDto("json/udm/udm_proxy_value_dto.json"),
            findAllProxyValueDtos());
    }

    private void verifyProxyValueDto(List<UdmProxyValueDto> expectedValues,
                                     List<UdmProxyValueDto> actualValues) {
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

    private List<UdmProxyValueDto> loadExpectedProxyValueDto(String fileName) {
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

    private List<UdmProxyValueDto> findAllProxyValueDtos() {
        return sqlSessionTemplate.selectList("IUdmProxyValueMapper.findAllDtos");
    }
}
