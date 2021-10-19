package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.repository.api.IUdmPriceTypeRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Verifies {@link UdmPriceTypeRepository}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 10/13/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@Transactional
public class UdmPriceTypeRepositoryIntegrationTest {

    @Autowired
    private IUdmPriceTypeRepository udmPriceTypeRepository;

    @Test
    public void testFindAllPriceTypes() {
        List<String> expectedPriceTypes =
            Arrays.asList("Individual", "Institution", "Corporate Tiered pricing", "List Price", "Open Access",
                "Free to Qualified", "Cost of Membership", "Per-Article price", "Other (Add to Price Note)");
        List<String> actualPriceTypes = udmPriceTypeRepository.findAllPriceTypes();
        assertEquals(expectedPriceTypes.size(), actualPriceTypes.size());
        IntStream.range(0, expectedPriceTypes.size())
            .forEach(index -> assertEquals(expectedPriceTypes.get(index), actualPriceTypes.get(index)));
    }

    @Test
    public void testFindAllAccessPriceTypes() {
        List<String> expectedPriceAccessTypes = Arrays.asList("Print", "Digital", "Combined - only option");
        List<String> actualPriceAccessTypes = udmPriceTypeRepository.findAllPriceAccessTypes();
        assertEquals(expectedPriceAccessTypes.size(), actualPriceAccessTypes.size());
        IntStream.range(0, expectedPriceAccessTypes.size())
            .forEach(index -> assertEquals(expectedPriceAccessTypes.get(index), actualPriceAccessTypes.get(index)));
    }
}
