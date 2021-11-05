package com.copyright.rup.dist.foreign.repository.impl;

import static com.copyright.rup.dist.foreign.domain.FdaConstants.NTS_PRODUCT_FAMILY;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Verifies {@link RightsholderRepository}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/23/2017
 *
 * @author Mikalai Bezmen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
//TODO: split test data into separate files for each test method
@TestData(fileName = "rightsholder-repository-test-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
@Transactional
public class RightsholderRepositoryIntegrationTest {

    private static final String RH_ACCOUNT_NAME = "Rh Account Name";
    private static final String RH_NAME_7000813806 =
        "CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil";
    private static final String RH_NAME_2000017004 = "Access Copyright, The Canadian Copyright Agency";
    private static final String RH_NAME_2000017010 = "JAC, Japan Academic Association for Copyright Clearance, Inc.";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String SCENARIO_ID_1 = "f933e8cb-3795-44bb-bc6a-f59d5c37781d";
    private static final String USAGE_BATCH_ID = "435a1689-8dfd-4a94-8ef5-72e2c4e6f3fa";

    @Autowired
    private RightsholderRepository rightsholderRepository;

    @Test
    public void testFindRhPayeeByScenarioId() {
        Usage expectedUsage1 = buildUsage();
        Usage expectedUsage2 = buildUsage();
        Usage expectedUsage3 = buildUsage();
        expectedUsage1.setRightsholder(buildRightsholder(2000017004L,
            "Access Copyright, The Canadian Copyright Agency"));
        expectedUsage2.setRightsholder(buildRightsholder(2000017010L,
            "JAC, Japan Academic Association for Copyright Clearance, Inc."));
        List<RightsholderPayeePair> result =
            rightsholderRepository.findRhPayeePairByScenarioId(SCENARIO_ID_1);
        assertEquals(3, result.size());
        result.sort(Comparator.comparing(RightsholderPayeePair::getRightsholder));
        RightsholderPayeePair pair1 = result.get(0);
        assertEquals(expectedUsage1.getRightsholder(), pair1.getRightsholder());
        assertEquals(expectedUsage1.getPayee(), pair1.getPayee());
        RightsholderPayeePair pair2 = result.get(2);
        assertEquals(expectedUsage2.getRightsholder(), pair2.getRightsholder());
        assertEquals(expectedUsage2.getPayee(), pair2.getPayee());
        RightsholderPayeePair pair3 = result.get(1);
        assertEquals(expectedUsage3.getRightsholder(), pair3.getRightsholder());
        assertEquals(expectedUsage3.getPayee(), pair3.getPayee());
    }

    @Test
    public void testInsertRightsholder() {
        List<Rightsholder> rightsholders = rightsholderRepository.findAll();
        assertEquals(7, rightsholders.size());
        Rightsholder rightsholder = buildRightsholder();
        rightsholderRepository.insert(rightsholder);
        rightsholders = rightsholderRepository.findAll();
        assertEquals(8, rightsholders.size());
    }

    @Test
    public void testFindAccountNumbers() {
        Set<Long> accountNumbers = rightsholderRepository.findAccountNumbers();
        assertEquals(11, accountNumbers.size());
        assertTrue(accountNumbers.containsAll(Arrays.asList(7000813806L, 2000017004L, 2000017010L, 1000009997L,
            1000002859L, 1000005413L, 1000159997L, 7000800832L, 7001555529L, 2000105646L)));
    }

    @Test
    public void testDeleteAll() {
        List<Rightsholder> rightsholders = rightsholderRepository.findAll();
        assertEquals(7, rightsholders.size());
        rightsholderRepository.deleteAll();
        rightsholders = rightsholderRepository.findAll();
        assertEquals(0, rightsholders.size());
    }

    @Test
    public void testDeleteByAccountNumber() {
        List<Rightsholder> rightsholders = rightsholderRepository.findAll();
        assertEquals(7, rightsholders.size());
        rightsholderRepository.deleteByAccountNumber(7000813806L);
        rightsholders = rightsholderRepository.findAll();
        assertEquals(6, rightsholders.size());
        assertTrue(rightsholders.stream()
            .filter(rightsholder -> Long.valueOf(7000813806L).equals(rightsholder.getAccountNumber()))
            .collect(Collectors.toList()).isEmpty());
    }

    @Test
    public void testFindRros() {
        List<Rightsholder> rros = rightsholderRepository.findRros(FAS_PRODUCT_FAMILY);
        assertNotNull(rros);
        assertTrue(CollectionUtils.isNotEmpty(rros));
        assertEquals(4, rros.size());
        assertTrue(rros.stream().map(Rightsholder::getAccountNumber).collect(Collectors.toList())
            .containsAll(Arrays.asList(7000813806L, 2000017004L, 2000017010L, 7000800832L)));
        assertTrue(rros.stream().map(Rightsholder::getId).collect(Collectors.toList())
            .containsAll(Arrays.asList("05c4714b-291d-4e38-ba4a-35307434acfb",
                "46754660-b627-46b9-a782-3f703b6853c7", "ff8b9ac9-5fca-4d57-b74e-26da209c1040",
                "05c4714b-291d-4e38-ba4a-35307434acfb")));
        assertTrue(rros.stream().map(Rightsholder::getName).collect(Collectors.toList())
            .containsAll(Arrays.asList(RH_NAME_7000813806, RH_NAME_2000017004,
                RH_NAME_2000017010, null)));
    }

    @Test
    public void testFindRightsholdersByAccountNumbers() {
        List<Rightsholder> actualResult = rightsholderRepository.findByAccountNumbers(
            Sets.newHashSet(7000813806L, 2000017004L));
        assertTrue(CollectionUtils.isNotEmpty(actualResult));
        assertEquals(2, actualResult.size());
        List<Long> accountNumbers = actualResult.stream()
            .map(Rightsholder::getAccountNumber)
            .collect(Collectors.toList());
        assertTrue(accountNumbers.contains(7000813806L));
        assertTrue(accountNumbers.contains(2000017004L));
    }

    @Test
    public void testFindRightsholdersByAccountNumbersEmptyResult() {
        assertTrue(CollectionUtils.isEmpty(rightsholderRepository.findByAccountNumbers(Sets.newHashSet(1111111111L))));
    }

    @Test
    public void testFindFromUsages() {
        List<Rightsholder> rightsholders =
            rightsholderRepository.findFromUsages(FAS_PRODUCT_FAMILY, null, null, null);
        assertEquals(4, rightsholders.size());
        assertTrue(rightsholders.stream()
            .map(Rightsholder::getAccountNumber)
            .collect(Collectors.toList())
            .containsAll(Sets.newHashSet(1000159997L, 1000009997L, 1000002859L, 1000005413L)));
        rightsholders = rightsholderRepository.findFromUsages(FAS_PRODUCT_FAMILY, "9997", null, null);
        assertEquals(2, rightsholders.size());
        assertTrue(rightsholders.stream()
            .map(Rightsholder::getAccountNumber)
            .collect(Collectors.toList())
            .containsAll(Sets.newHashSet(1000159997L, 1000009997L)));
    }

    @Test
    public void testFindCountFromUsages() {
        assertEquals(4, rightsholderRepository.findCountFromUsages(FAS_PRODUCT_FAMILY, StringUtils.EMPTY));
        assertEquals(4, rightsholderRepository.findCountFromUsages(FAS_PRODUCT_FAMILY, null));
        assertEquals(2, rightsholderRepository.findCountFromUsages(FAS_PRODUCT_FAMILY, "9997"));
        assertEquals(1, rightsholderRepository.findCountFromUsages(FAS_PRODUCT_FAMILY, "IEEE"));
    }

    @Test
    public void testFindByScenarioId() {
        List<Rightsholder> rightsholders =
            rightsholderRepository.findByScenarioId("d7e9bae8-6b10-4675-9668-8e3605a47dad");
        assertEquals(2, CollectionUtils.size(rightsholders));
        Rightsholder rightsholder1 = rightsholders.get(0);
        assertEquals("5bcf2c37-2f32-48e9-90fe-c9d75298eeed", rightsholder1.getId());
        assertEquals(1000002859L, rightsholder1.getAccountNumber(), 0);
        Rightsholder rightsholder2 = rightsholders.get(1);
        assertEquals("8a0dbf78-d9c9-49d9-a895-05f55cfc8329", rightsholder2.getId());
        assertEquals(1000005413L, rightsholder2.getAccountNumber(), 0);
    }

    private Rightsholder buildRightsholder() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(12345678L);
        rightsholder.setName(RH_ACCOUNT_NAME);
        rightsholder.setId(RupPersistUtils.generateUuid());
        return rightsholder;
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setBatchId(USAGE_BATCH_ID);
        usage.setScenarioId(SCENARIO_ID_1);
        usage.setWrWrkInst(123456783L);
        usage.setWorkTitle("WorkTitle");
        usage.setRightsholder(buildRightsholder(7000813806L,
            "CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil"));
        usage.setPayee(usage.getRightsholder());
        usage.setStatus(UsageStatusEnum.LOCKED);
        usage.setProductFamily(NTS_PRODUCT_FAMILY);
        usage.setArticle("Article");
        usage.setStandardNumber("StandardNumber");
        usage.setPublisher("Publisher");
        usage.setPublicationDate(LocalDate.of(2016, 11, 3));
        usage.setMarket("Market");
        usage.setMarketPeriodFrom(2015);
        usage.setMarketPeriodTo(2017);
        usage.setAuthor("Author");
        usage.setNumberOfCopies(1);
        usage.setReportedValue(new BigDecimal("11.25"));
        usage.setGrossAmount(new BigDecimal("54.4400000000"));
        return usage;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
