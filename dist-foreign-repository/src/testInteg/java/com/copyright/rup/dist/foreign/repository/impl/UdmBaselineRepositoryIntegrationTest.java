package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private IUdmUsageRepository udmUsageRepository;
    @Autowired
    private IUdmBaselineRepository baselineRepository;

    @Test //TODO {aazarenka} replace UdmUsageDto to UdmBaselineDto after implementation
    public void testRemoveUdmUsageFromBaseline() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUdmBatchesIds(Collections.singleton("201f42dc-7f13-4449-97b0-725aa5a339e0"));
        List<UdmUsageDto> usageDtos = udmUsageRepository.findDtosByFilter(filter, null, null);
        assertEquals(3, usageDtos.size());
        usageDtos.forEach(usageDto -> assertTrue(usageDto.isBaselineFlag()));
        Set<String> removedUsageIds = baselineRepository.removeUmdUsagesFromBaseline(202106, USER_NAME);
        assertEquals(2, removedUsageIds.size());
        filter.setPeriod(202106);
        assertEquals(2, udmUsageRepository.findDtosByFilter(filter, null, null)
            .stream()
            .filter(usageDto -> !usageDto.isBaselineFlag())
            .count());
    }
}
