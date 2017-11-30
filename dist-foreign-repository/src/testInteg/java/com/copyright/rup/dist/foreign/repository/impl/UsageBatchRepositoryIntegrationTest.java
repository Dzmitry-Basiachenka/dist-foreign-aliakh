package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Verifies {@link UsageBatchRepository}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/03/2017
 *
 * @author Mikalai Bezmen
 * @author Aliaksandr Radkevich
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-sql-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=usage-batch-repository-test-data-init.groovy"})
@TransactionConfiguration
@Transactional
public class UsageBatchRepositoryIntegrationTest {

    private static final String USAGE_BATCH_NAME = "Batch name";
    private static final Integer FISCAL_YEAR_2017 = 2017;
    private static final Long RRO_ACCOUNT_NUMBER = 123456789L;
    private static final BigDecimal GROSS_AMOUNT = new BigDecimal("23.53");
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2017, 2, 23);

    @Autowired
    private UsageBatchRepository usageBatchRepository;
    @Autowired
    private IUsageRepository usageRepository;

    @Test
    public void testFindFiscalYears() {
        List<Integer> fiscalYears = usageBatchRepository.findFiscalYears();
        assertNotNull(fiscalYears);
        assertEquals(4, fiscalYears.size());
        assertEquals(2016, fiscalYears.get(0), 0);
        assertEquals(FISCAL_YEAR_2017, fiscalYears.get(1));
        assertEquals(2018, fiscalYears.get(2), 0);
        assertEquals(2019, fiscalYears.get(3), 0);
    }

    @Test
    public void testFindCountByName() {
        assertEquals(1, usageBatchRepository.findCountByName("JAACC_11Dec16"));
        assertEquals(1, usageBatchRepository.findCountByName("JaAcC_11dec16"));
        assertEquals(0, usageBatchRepository.findCountByName(USAGE_BATCH_NAME));
    }

    @Test
    public void testInsertUsageBatch() {
        usageBatchRepository.insert(buildUsageBatch());
        UsageBatch usageBatch = usageBatchRepository.findByName(USAGE_BATCH_NAME);
        assertNotNull(usageBatch);
        assertEquals(RRO_ACCOUNT_NUMBER, usageBatch.getRro().getAccountNumber());
        assertEquals(PAYMENT_DATE, usageBatch.getPaymentDate());
        assertEquals(FISCAL_YEAR_2017, usageBatch.getFiscalYear());
        assertEquals(GROSS_AMOUNT, usageBatch.getGrossAmount());
    }

    @Test
    public void testDeleteUsageBatch() {
        String batchId = "56282dbc-2468-48d4-b926-93d3458a656a";
        assertEquals(4, usageBatchRepository.findAll().size());
        usageRepository.deleteUsages(batchId);
        usageBatchRepository.deleteUsageBatch(batchId);
        assertEquals(3, usageBatchRepository.findAll().size());
    }

    @Test
    public void testFindUsageBatch() {
        List<UsageBatch> usageBatches = usageBatchRepository.findAll();
        assertEquals(4, usageBatches.size());
        assertEquals("3f46981e-e85a-4786-9b60-ab009c4358e7", usageBatches.get(0).getId());
        assertEquals("56282dbc-2468-48d4-b926-94d3458a666a", usageBatches.get(1).getId());
        assertEquals("56282dbc-2468-48d4-b926-93d3458a656a", usageBatches.get(2).getId());
        assertEquals("a5b64c3a-55d2-462e-b169-362dca6a4dd6", usageBatches.get(3).getId());
    }

    @Test
    public void testFindForFilterUsageBatch() {
        List<UsageBatch> usageBatches = usageBatchRepository.findForFilter();
        assertEquals(3, usageBatches.size());
        verifyUsageBatch(usageBatches.get(0), "56282dbc-2468-48d4-b926-94d3458a666a", "AccessCopyright_11Dec16");
        verifyUsageBatch(usageBatches.get(1), "56282dbc-2468-48d4-b926-93d3458a656a", "CADRA_11Dec16");
        verifyUsageBatch(usageBatches.get(2), "a5b64c3a-55d2-462e-b169-362dca6a4dd6", "JAACC_11Dec16");
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(RupPersistUtils.generateUuid());
        usageBatch.setName(USAGE_BATCH_NAME);
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(RRO_ACCOUNT_NUMBER);
        usageBatch.setRro(rightsholder);
        usageBatch.setPaymentDate(PAYMENT_DATE);
        usageBatch.setFiscalYear(FISCAL_YEAR_2017);
        usageBatch.setGrossAmount(GROSS_AMOUNT);
        return usageBatch;
    }

    private void verifyUsageBatch(UsageBatch usageBatch, String id, String name) {
        assertEquals(id, usageBatch.getId());
        assertEquals(name, usageBatch.getName());
    }
}
