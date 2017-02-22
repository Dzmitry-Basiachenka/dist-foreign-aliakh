package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;

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
 * Verifies {@link IUsageBatchRepository}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/03/2017
 *
 * @author Mikalai Bezmen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-sql-test-context.xml"})
@TransactionConfiguration
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
public class UsageBatchRepositoryIntegrationTest {

    private static final Integer FISCAL_YEAR_2017 = 2017;
    private static final Integer FISCAL_YEAR_2016 = 2016;
    private static final Integer FISCAL_YEAR_2019 = 2019;

    @Autowired
    private IUsageBatchRepository usageBatchRepository;

    @Test
    public void testFindFiscalYears() {
        List<Integer> fiscalYears = usageBatchRepository.findFiscalYears();
        assertNotNull(fiscalYears);
        assertEquals(3, fiscalYears.size());
        assertEquals(FISCAL_YEAR_2016, fiscalYears.get(0));
        assertEquals(FISCAL_YEAR_2017, fiscalYears.get(1));
        assertEquals(FISCAL_YEAR_2019, fiscalYears.get(2));
    }

    @Test
    public void testGetUsageBatchesCountByName() {
        assertEquals(1, usageBatchRepository.getUsageBatchesCountByName("JAACC_11Dec16"));
        assertEquals(1, usageBatchRepository.getUsageBatchesCountByName("JaAcC_11dec16"));
        assertEquals(0, usageBatchRepository.getUsageBatchesCountByName("Batch name"));
    }
}
