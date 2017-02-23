package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

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
    private static final String RH_ACCOUNT_NAME = "Rh Account Name";
    
    @Autowired
    private RightsholderRepository rightsholderRepository;

    @Test
    public void testInsertRightsholder() {
        Rightsholder rightsholder = buildRightsholder();
        rightsholderRepository.insert(rightsholder);
        List<Rightsholder> rightsholders = rightsholderRepository.findAll();
        assertEquals(1, rightsholders.size());
        assertEquals(rightsholder, rightsholders.get(0));
    }

    private Rightsholder buildRightsholder() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(RH_ACCOUNT_NUMBER);
        rightsholder.setName(RH_ACCOUNT_NAME);
        return rightsholder;
    }
}
