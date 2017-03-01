package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
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
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
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
    public void testFindRightsholdersAccountNumbers() {
        Set<Long> accountNumbers = rightsholderRepository.findRightsholdersAccountNumbers();
        assertEquals(9, accountNumbers.size());
        assertTrue(accountNumbers.contains(RH_ACCOUNT_NUMBER_7000813806));
        assertTrue(accountNumbers.contains(RH_ACCOUNT_NUMBER_2000017004));
        assertTrue(accountNumbers.contains(RH_ACCOUNT_NUMBER_7001440663));
        assertTrue(accountNumbers.contains(RH_ACCOUNT_NUMBER_1000009997));
        assertTrue(accountNumbers.contains(RH_ACCOUNT_NUMBER_1000002859));
        assertTrue(accountNumbers.contains(RH_ACCOUNT_NUMBER_1000008666));
        assertTrue(accountNumbers.contains(RH_ACCOUNT_NUMBER_1000005413));
        assertTrue(accountNumbers.contains(RH_ACCOUNT_NUMBER_1000159997));
        assertTrue(accountNumbers.contains(RH_ACCOUNT_NUMBER_7000800832));
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
    public void testDeleteRightsholderByAccountNumber() {
        List<Rightsholder> rightsholders = rightsholderRepository.findAll();
        assertEquals(8, rightsholders.size());
        rightsholderRepository.deleteRightsholderByAccountNumber(RH_ACCOUNT_NUMBER_7000813806);
        rightsholders = rightsholderRepository.findAll();
        assertEquals(7, rightsholders.size());
        assertTrue(rightsholders.stream()
            .filter(rightsholder -> RH_ACCOUNT_NUMBER_7000813806.equals(rightsholder.getAccountNumber()))
            .collect(Collectors.toList()).isEmpty());
    }

    private Rightsholder buildRightsholder() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(RH_ACCOUNT_NUMBER);
        rightsholder.setName(RH_ACCOUNT_NAME);
        return rightsholder;
    }
}
