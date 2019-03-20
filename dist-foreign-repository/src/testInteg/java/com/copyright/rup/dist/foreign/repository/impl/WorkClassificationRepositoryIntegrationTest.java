package com.copyright.rup.dist.foreign.repository.impl;

import static junit.framework.TestCase.assertNull;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verifies {@link WorkClassificationRepository}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/06/2019
 *
 * @author Pavel Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=work-classification-repository-test-data-init.groovy"})
@TransactionConfiguration
@Transactional
public class WorkClassificationRepositoryIntegrationTest {

    @Autowired
    private WorkClassificationRepository workClassificationRepository;

    @Test
    public void testFindClassificationByWrWrkInst() {
        assertEquals("NON-STM", workClassificationRepository.findClassificationByWrWrkInst(11111111111L));
    }

    @Test
    public void testFindClassificationByWrWrkInstWithNoClassification() {
        assertNull(workClassificationRepository.findClassificationByWrWrkInst(22222222222L));
    }
}
