package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.copyright.rup.dist.foreign.repository.api.IUdmValueRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

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

    @Autowired
    private IUdmValueRepository udmValueRepository;

    @Test
    public void testFindPeriods() {
        List<Integer> expectedPeriods = Arrays.asList(202106, 201912);
        List<Integer> actualPeriods = udmValueRepository.findPeriods();
        assertFalse(actualPeriods.isEmpty());
        assertEquals(expectedPeriods, actualPeriods);
    }
}
