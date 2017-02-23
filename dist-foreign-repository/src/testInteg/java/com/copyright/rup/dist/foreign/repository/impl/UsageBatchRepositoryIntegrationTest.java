package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;

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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Verifies {@link UsageBatchRepository}.
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

    private static final String USAGE_BATCH_NAME = "Batch name";
    private static final String CURRENCY = "USD";
    private static final Integer FISCAL_YEAR_2017 = 2017;
    private static final Integer FISCAL_YEAR_2016 = 2016;
    private static final Integer FISCAL_YEAR_2019 = 2019;
    private static final Long RRO_ACCOUNT_NUMBER = 123456789L;
    private static final BigDecimal GROSS_AMOUNT = new BigDecimal("23.53");
    private static final BigDecimal APPLIED_CONVERSION_RATE = new BigDecimal("1.2500000000");
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2017,2,23);

    @Autowired
    private UsageBatchRepository usageBatchRepository;

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
        assertEquals(0, usageBatchRepository.getUsageBatchesCountByName(USAGE_BATCH_NAME));
    }

    @Test
    public void testInsertUsageBatch() {
        usageBatchRepository.insert(buildUsageBatch());
        UsageBatch usageBatch = usageBatchRepository.findUsageBatchByName(USAGE_BATCH_NAME);
        assertNotNull(usageBatch);
        assertEquals(RRO_ACCOUNT_NUMBER, usageBatch.getRro().getAccountNumber());
        assertEquals(PAYMENT_DATE, usageBatch.getPaymentDate());
        assertEquals(FISCAL_YEAR_2017, usageBatch.getFiscalYear());
        assertEquals(CURRENCY, usageBatch.getCurrency());
        assertEquals(GROSS_AMOUNT, usageBatch.getGrossAmount());
        assertEquals(APPLIED_CONVERSION_RATE, usageBatch.getAppliedConversionRate());
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(UUID.randomUUID().toString());
        usageBatch.setName(USAGE_BATCH_NAME);
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(RRO_ACCOUNT_NUMBER);
        usageBatch.setRro(rightsholder);
        usageBatch.setPaymentDate(PAYMENT_DATE);
        usageBatch.setFiscalYear(FISCAL_YEAR_2017);
        usageBatch.setCurrency(CURRENCY);
        usageBatch.setGrossAmount(GROSS_AMOUNT);
        usageBatch.setAppliedConversionRate(APPLIED_CONVERSION_RATE);
        return usageBatch;
    }
}
