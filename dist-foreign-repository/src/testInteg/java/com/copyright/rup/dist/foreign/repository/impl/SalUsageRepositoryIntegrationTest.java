package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.ISalUsageRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private static final String USAGE_ID = "5ab5e80b-89c0-4d78-9675-54c7ab284450";

    @Autowired
    private ISalUsageRepository salUsageRepository;

    @Test
    public void testFindCountByFilter() {
        assertEquals(1, salUsageRepository.findCountByFilter(buildUsageFilter(
            Collections.singleton(USAGE_BATCH_ID), UsageStatusEnum.NEW, SAL_PRODUCT_FAMILY, SalDetailTypeEnum.IB)));
    }

    @Test
    public void testFindDtosByFilter() {
        verifyUsageDtos(salUsageRepository.findDtosByFilter(buildUsageFilter(
            Collections.singleton(USAGE_BATCH_ID), UsageStatusEnum.NEW, SAL_PRODUCT_FAMILY, SalDetailTypeEnum.IB),
            null, new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), USAGE_ID);
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

    private void verifyUsageDtos(List<UsageDto> usageDtos, String... usageIds) {
        assertNotNull(usageDtos);
        usageDtos.sort(Comparator.comparing(UsageDto::getId));
        Arrays.sort(usageIds);
        verifyUsageDtosInExactOrder(usageDtos, usageIds);
    }

    private void verifyUsageDtosInExactOrder(List<UsageDto> usageDtos, String... expectedIds) {
        assertNotNull(usageDtos);
        List<String> actualIds = usageDtos.stream()
            .map(UsageDto::getId)
            .collect(Collectors.toList());
        assertEquals(Arrays.asList(expectedIds), actualIds);
    }
}
