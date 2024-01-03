package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.RightsholderTypeOfUsePair;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class RightsholderRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "rightsholder-repository-integration-test/";
    private static final String FIND = FOLDER_NAME + "find.groovy";
    private static final String RH_ACCOUNT_NAME = "Rh Account Name";
    private static final String RH_NAME_7000813806 =
        "CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil";
    private static final String RH_NAME_2000017004 = "Access Copyright, The Canadian Copyright Agency";
    private static final String RH_NAME_2000017010 = "JAC, Japan Academic Association for Copyright Clearance, Inc.";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String DIGITAL_TOU = "DIGITAL";
    private static final String PRINT_TOU = "PRINT";
    private static final String SCENARIO_ID_1 = "f933e8cb-3795-44bb-bc6a-f59d5c37781d";
    private static final String USAGE_BATCH_ID = "435a1689-8dfd-4a94-8ef5-72e2c4e6f3fa";

    @Autowired
    private RightsholderRepository rightsholderRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "find-rh-payee-pair-by-scenario-id.groovy")
    public void testFindRhPayeePairByScenarioId() {
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
    @TestData(fileName = "rollback-only.groovy")
    public void testInsertRightsholder() {
        List<Rightsholder> rightsholders = rightsholderRepository.findAll();
        assertEquals(0, rightsholders.size());
        Rightsholder rightsholder = buildRightsholder();
        rightsholderRepository.insert(rightsholder);
        rightsholders = rightsholderRepository.findAll();
        assertEquals(1, rightsholders.size());
    }

    @Test
    @TestData(fileName = FIND)
    public void testFindAccountNumbers() {
        Set<Long> accountNumbers = rightsholderRepository.findAccountNumbers();
        assertEquals(14, accountNumbers.size());
        assertTrue(accountNumbers.containsAll(
            List.of(7000813806L, 2000017004L, 2000017010L, 1000009997L, 1000002859L, 1000005413L, 1000159997L,
                7000800832L, 7001555529L, 2000105646L, 2580011451L, 1000002854L, 1000028511L, 1000009522L)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete.groovy")
    public void testDeleteAll() {
        List<Rightsholder> rightsholders = rightsholderRepository.findAll();
        assertEquals(2, rightsholders.size());
        rightsholderRepository.deleteAll();
        rightsholders = rightsholderRepository.findAll();
        assertEquals(0, rightsholders.size());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete.groovy")
    public void testDeleteByAccountNumber() {
        List<Rightsholder> rightsholders = rightsholderRepository.findAll();
        assertEquals(2, rightsholders.size());
        rightsholderRepository.deleteByAccountNumber(7000813806L);
        rightsholders = rightsholderRepository.findAll();
        assertEquals(1, rightsholders.size());
        assertEquals(0, rightsholders.stream()
            .filter(rightsholder -> Long.valueOf(7000813806L).equals(rightsholder.getAccountNumber()))
            .count());
    }

    @Test
    @TestData(fileName = FIND)
    public void testFindRros() {
        List<Rightsholder> rros = rightsholderRepository.findRros(FAS_PRODUCT_FAMILY);
        assertNotNull(rros);
        assertTrue(CollectionUtils.isNotEmpty(rros));
        assertEquals(4, rros.size());
        assertTrue(rros.stream().map(Rightsholder::getAccountNumber).collect(Collectors.toList())
            .containsAll(List.of(7000813806L, 2000017004L, 2000017010L, 7000800832L)));
        assertTrue(rros.stream().map(Rightsholder::getId).collect(Collectors.toList())
            .containsAll(List.of("05c4714b-291d-4e38-ba4a-35307434acfb",
                "46754660-b627-46b9-a782-3f703b6853c7", "ff8b9ac9-5fca-4d57-b74e-26da209c1040",
                "05c4714b-291d-4e38-ba4a-35307434acfb")));
        assertTrue(rros.stream().map(Rightsholder::getName).collect(Collectors.toList())
            .containsAll(Arrays.asList(RH_NAME_7000813806, RH_NAME_2000017004,
                RH_NAME_2000017010, null)));
    }

    @Test
    @TestData(fileName = FIND)
    public void testFindRightsholdersByAccountNumbers() {
        List<Rightsholder> actualResult = rightsholderRepository.findByAccountNumbers(Set.of(7000813806L, 2000017004L));
        assertTrue(CollectionUtils.isNotEmpty(actualResult));
        assertEquals(2, actualResult.size());
        List<Long> accountNumbers = actualResult.stream()
            .map(Rightsholder::getAccountNumber)
            .collect(Collectors.toList());
        assertTrue(accountNumbers.contains(7000813806L));
        assertTrue(accountNumbers.contains(2000017004L));
    }

    @Test
    @TestData(fileName = FIND)
    public void testFindByAccountNumbersEmptyResult() {
        assertTrue(CollectionUtils.isEmpty(rightsholderRepository.findByAccountNumbers(Set.of(1111111111L))));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-all-with-search.groovy")
    public void testFindAllWithSearch() {
        List<Rightsholder> rightsholders =
            rightsholderRepository.findAllWithSearch(null, null, null);
        assertEquals(7, rightsholders.size());
        assertTrue(rightsholders.stream()
            .map(Rightsholder::getAccountNumber)
            .collect(Collectors.toList())
            .containsAll(Set.of(1000159997L, 1000009997L, 1000002859L, 1000005413L, 2000017004L, 7000813806L,
                2000017010L)));
        rightsholders = rightsholderRepository.findAllWithSearch("9997", null, null);
        assertEquals(2, rightsholders.size());
        assertTrue(rightsholders.stream()
            .map(Rightsholder::getAccountNumber)
            .collect(Collectors.toList())
            .containsAll(Set.of(1000159997L, 1000009997L)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-all-with-search.groovy")
    public void testFindCountWithSearch() {
        assertEquals(7, rightsholderRepository.findCountWithSearch(StringUtils.EMPTY));
        assertEquals(7, rightsholderRepository.findCountWithSearch(null));
        assertEquals(2, rightsholderRepository.findCountWithSearch("9997"));
        assertEquals(1, rightsholderRepository.findCountWithSearch("IEEE"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-scenario-id.groovy")
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

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-acl-grant-set-id.groovy")
    public void testFindByAclGrantSetId() {
        List<RightsholderTypeOfUsePair> rightsholderTypeOfUsePairs =
            rightsholderRepository.findByAclGrantSetId("6d560952-070c-44cb-ba96-710f17a36017");
        assertEquals(3, rightsholderTypeOfUsePairs.size());
        Rightsholder rightsholder1 = rightsholderTypeOfUsePairs.get(0).getRightsholder();
        assertEquals("7b18daf7-679d-4bcf-a0c3-3a3cf8f7ee8c", rightsholder1.getId());
        assertEquals(7001645719L, rightsholder1.getAccountNumber(), 0);
        assertEquals(DIGITAL_TOU, rightsholderTypeOfUsePairs.get(0).getTypeOfUse());
        Rightsholder rightsholder2 = rightsholderTypeOfUsePairs.get(1).getRightsholder();
        assertEquals("7b18daf7-679d-4bcf-a0c3-3a3cf8f7ee8c", rightsholder2.getId());
        assertEquals(7001645719L, rightsholder2.getAccountNumber(), 0);
        assertEquals(PRINT_TOU, rightsholderTypeOfUsePairs.get(1).getTypeOfUse());
        Rightsholder rightsholder3 = rightsholderTypeOfUsePairs.get(2).getRightsholder();
        assertEquals("f2b84227-e24d-4da0-81b8-a6d9635576a2", rightsholder3.getId());
        assertEquals(2000155939L, rightsholder3.getAccountNumber(), 0);
        assertEquals(PRINT_TOU, rightsholderTypeOfUsePairs.get(2).getTypeOfUse());
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
        usage.setProductFamily(FdaConstants.NTS_PRODUCT_FAMILY);
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
