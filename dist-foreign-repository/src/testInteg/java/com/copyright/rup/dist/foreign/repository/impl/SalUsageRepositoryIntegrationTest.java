package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.ISalUsageRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Integration test for {@link SalUsageRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=sal-usage-repository-test-data-init.groovy"})
@Transactional
public class SalUsageRepositoryIntegrationTest {

    private static final String USAGE_BATCH_ID = "6aa46f9f-a0c2-4b61-97bc-aa35b7ce6e64";
    private static final String SAL_PRODUCT_FAMILY = "SAL";
    private static final String DETAIL_ID_KEY = "detailId";

    @Autowired
    private ISalUsageRepository salUsageRepository;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Test
    public void testFindCountByFilter() {
        assertEquals(1, salUsageRepository.findCountByFilter(buildUsageFilter(
            Collections.singleton(USAGE_BATCH_ID), UsageStatusEnum.NEW, SAL_PRODUCT_FAMILY, SalDetailTypeEnum.IB)));
    }

    @Test

    public void testFindDtosByFilter() throws IOException {
        verifyUsageDtos(loadExpectedUsageDtos("json/sal_usage_dto.json"),
            salUsageRepository.findDtosByFilter(buildUsageFilter(
                Collections.singleton(USAGE_BATCH_ID), UsageStatusEnum.NEW, SAL_PRODUCT_FAMILY, SalDetailTypeEnum.IB),
                null, new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)));
    }

    private UsageFilter buildUsageFilter(Set<String> usageBatchIds, UsageStatusEnum status, String productFamily,
                                         SalDetailTypeEnum salDetailType) {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(usageBatchIds);
        usageFilter.setUsageStatus(status);
        usageFilter.setProductFamily(productFamily);
        usageFilter.setSalDetailType(salDetailType);
        return usageFilter;
    }

    private List<UsageDto> loadExpectedUsageDtos(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        return OBJECT_MAPPER.readValue(content, new TypeReference<List<UsageDto>>() {
        });
    }

    private void verifyUsageDtos(List<UsageDto> expectedUsages, List<UsageDto> actualUsages) {
        assertEquals(CollectionUtils.size(expectedUsages), CollectionUtils.size(actualUsages));
        IntStream.range(0, expectedUsages.size())
            .forEach(i -> {
                UsageDto expectedUsage = expectedUsages.get(i);
                UsageDto actualUsage = actualUsages.get(i);
                assertNotNull(expectedUsage);
                assertNotNull(actualUsage);
                assertEquals(expectedUsage.toString(), actualUsage.toString());
            });
    }
}
