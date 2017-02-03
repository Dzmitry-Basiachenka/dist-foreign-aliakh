package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.CurrencyEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
    private static final Integer FISCAL_YEAR_2015 = 2015;
    private static final Long RH_ACCOUNT_NUMBER = 12345678L;
    private static final String ACCOUNT_NAME = "Account Name";
    private static final BigDecimal CONVERSION_RATE = BigDecimal.ZERO;
    private static final BigDecimal APPLIED_CONVERSION_RATE = CONVERSION_RATE;
    private static final String USAGE_BATCH_NAME = "Usage Batch Name";

    @Autowired
    private IUsageBatchRepository usageBatchRepository;

    @Test
    public void testFindFiscalYears() {
        usageBatchRepository.insert(buildUsageBatch(FISCAL_YEAR_2017));
        usageBatchRepository.insert(buildUsageBatch(FISCAL_YEAR_2017));
        usageBatchRepository.insert(buildUsageBatch(FISCAL_YEAR_2015));
        usageBatchRepository.insert(buildUsageBatch(FISCAL_YEAR_2016));
        List<Integer> fiscalYears = usageBatchRepository.findFiscalYears();
        assertNotNull(fiscalYears);
        assertEquals(3, fiscalYears.size());
        assertEquals(FISCAL_YEAR_2015, fiscalYears.get(0));
        assertEquals(FISCAL_YEAR_2016, fiscalYears.get(1));
        assertEquals(FISCAL_YEAR_2017, fiscalYears.get(2));
    }

    private UsageBatch buildUsageBatch(Integer fiscalYear) {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(UUID.randomUUID().toString());
        usageBatch.setName(USAGE_BATCH_NAME);
        usageBatch.setRro(buildRightsholder());
        usageBatch.setPaymentDate(LocalDate.of(2016, 11, 8));
        usageBatch.setFiscalYear(fiscalYear);
        usageBatch.setCurrency(CurrencyEnum.USD);
        usageBatch.setConversionRate(CONVERSION_RATE);
        usageBatch.setAppliedConversionRate(APPLIED_CONVERSION_RATE);
        return usageBatch;
    }

    private Rightsholder buildRightsholder() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId(UUID.randomUUID().toString());
        rightsholder.setName(ACCOUNT_NAME);
        rightsholder.setAccountNumber(RH_ACCOUNT_NUMBER);
        return rightsholder;
    }
}
