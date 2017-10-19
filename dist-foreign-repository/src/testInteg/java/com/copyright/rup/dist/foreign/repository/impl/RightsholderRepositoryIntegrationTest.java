package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-sql-test-context.xml"})
@TransactionConfiguration
@Transactional
public class RightsholderRepositoryIntegrationTest {

    private static final Long RH_ACCOUNT_NUMBER = 12345678L;
    private static final Long RH_ACCOUNT_NUMBER_7000813806 = 7000813806L;
    private static final Long RH_ACCOUNT_NUMBER_2000017004 = 2000017004L;
    private static final Long RH_ACCOUNT_NUMBER_7001440663 = 7001440663L;
    private static final Long RH_ACCOUNT_NUMBER_1000009997 = 1000009997L;
    private static final Long RH_ACCOUNT_NUMBER_1000002859 = 1000002859L;
    private static final Long RH_ACCOUNT_NUMBER_1000008666 = 1000008666L;
    private static final Long RH_ACCOUNT_NUMBER_1000005413 = 1000005413L;
    private static final Long RH_ACCOUNT_NUMBER_1000159997 = 1000159997L;
    private static final Long RH_ACCOUNT_NUMBER_7000800832 = 7000800832L;
    private static final String RH_ACCOUNT_NAME = "Rh Account Name";
    private static final String RH_NAME_7000813806 =
        "CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil";
    private static final String RH_NAME_2000017004 = "Access Copyright, The Canadian Copyright Agency";
    private static final String RH_NAME_7001440663 = "JAACC, Japan Academic Association for Copyright Clearance [T]";

    @Autowired
    private RightsholderRepository rightsholderRepository;

    @Test
    public void testInsertRightsholder() {
        List<Rightsholder> rightsholders = rightsholderRepository.findAll();
        assertEquals(8, rightsholders.size());
        Rightsholder rightsholder = buildRightsholder();
        rightsholderRepository.insert(rightsholder);
        rightsholders = rightsholderRepository.findAll();
        assertEquals(9, rightsholders.size());
    }

    @Test
    public void testFindAccountNumbers() {
        Set<Long> accountNumbers = rightsholderRepository.findAccountNumbers();
        assertEquals(9, accountNumbers.size());
        assertTrue(accountNumbers.containsAll(Lists.newArrayList(RH_ACCOUNT_NUMBER_7000813806,
            RH_ACCOUNT_NUMBER_2000017004, RH_ACCOUNT_NUMBER_7001440663, RH_ACCOUNT_NUMBER_1000009997,
            RH_ACCOUNT_NUMBER_1000002859, RH_ACCOUNT_NUMBER_1000008666, RH_ACCOUNT_NUMBER_1000005413,
            RH_ACCOUNT_NUMBER_1000159997, RH_ACCOUNT_NUMBER_7000800832)));
    }

    @Test
    public void testDeleteAll() {
        List<Rightsholder> rightsholders = rightsholderRepository.findAll();
        assertEquals(8, rightsholders.size());
        rightsholderRepository.deleteAll();
        rightsholders = rightsholderRepository.findAll();
        assertEquals(0, rightsholders.size());
    }

    @Test
    public void testDeleteByAccountNumber() {
        List<Rightsholder> rightsholders = rightsholderRepository.findAll();
        assertEquals(8, rightsholders.size());
        rightsholderRepository.deleteByAccountNumber(RH_ACCOUNT_NUMBER_7000813806);
        rightsholders = rightsholderRepository.findAll();
        assertEquals(7, rightsholders.size());
        assertTrue(rightsholders.stream()
            .filter(rightsholder -> RH_ACCOUNT_NUMBER_7000813806.equals(rightsholder.getAccountNumber()))
            .collect(Collectors.toList()).isEmpty());
    }

    @Test
    public void testFindRros() {
        List<Rightsholder> rros = rightsholderRepository.findRros();
        assertNotNull(rros);
        assertTrue(CollectionUtils.isNotEmpty(rros));
        assertEquals(4, rros.size());
        assertTrue(rros.stream().map(Rightsholder::getAccountNumber).collect(Collectors.toList())
            .containsAll(Lists.newArrayList(RH_ACCOUNT_NUMBER_7000813806, RH_ACCOUNT_NUMBER_2000017004,
                RH_ACCOUNT_NUMBER_7001440663, RH_ACCOUNT_NUMBER_7000800832)));
        assertTrue(rros.stream().map(Rightsholder::getId).collect(Collectors.toList())
            .containsAll(Lists.newArrayList("05c4714b-291d-4e38-ba4a-35307434acfb",
                "46754660-b627-46b9-a782-3f703b6853c7", "ff8b9ac9-5fca-4d57-b74e-26da209c1040",
                "05c4714b-291d-4e38-ba4a-35307434acfb")));
        assertTrue(rros.stream().map(Rightsholder::getName).collect(Collectors.toList())
            .containsAll(Lists.newArrayList(RH_NAME_7000813806, RH_NAME_2000017004,
                RH_NAME_7001440663, null)));
    }

    @Test
    public void testFindRightsholdersByAccountNumbers() {
        List<Rightsholder> actualResult = rightsholderRepository.findRightsholdersByAccountNumbers(
            Sets.newHashSet(RH_ACCOUNT_NUMBER_7000813806, RH_ACCOUNT_NUMBER_2000017004));
        assertTrue(CollectionUtils.isNotEmpty(actualResult));
        assertEquals(2, actualResult.size());
        List<Long> accountNumbers = actualResult.stream()
            .map(Rightsholder::getAccountNumber)
            .collect(Collectors.toList());
        assertTrue(accountNumbers.contains(RH_ACCOUNT_NUMBER_7000813806));
        assertTrue(accountNumbers.contains(RH_ACCOUNT_NUMBER_2000017004));
    }

    @Test
    public void testFindRightsholdersByAccountNumbersEmptyResult() {
        assertTrue(CollectionUtils.isEmpty(
            rightsholderRepository.findRightsholdersByAccountNumbers(Sets.newHashSet(1111111111L))));
    }

    private Rightsholder buildRightsholder() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(RH_ACCOUNT_NUMBER);
        rightsholder.setName(RH_ACCOUNT_NAME);
        rightsholder.setId(RupPersistUtils.generateUuid());
        return rightsholder;
    }
}
