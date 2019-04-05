package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.domain.PreServiceFeeFund;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Verifies {@link FundPoolRepository}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/26/2019
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=fund-pool-repository-test-data-init.groovy"})
@TransactionConfiguration
@Transactional
public class FundPoolRepositoryIntegrationTest {

    private static final String ID_1 = "b5b64c3a-55d2-462e-b169-362dca6a4dd7";
    private static final String ID_2 = "76282dbc-2468-48d4-b926-93d3458a656b";
    private static final String NAME_1 = "FAS Q1 2019";
    private static final String NAME_2 = "FAS Q2 2019";
    private static final String COMMENT_1 = "some comment";
    private static final String COMMENT_2 = "other comment";

    @Autowired
    private FundPoolRepository fundPoolRepository;

    @Test
    public void testInsert() {
        assertNull(fundPoolRepository.findById(ID_2));
        PreServiceFeeFund fundPool = new PreServiceFeeFund();
        fundPool.setId(ID_2);
        fundPool.setName(NAME_2);
        fundPool.setComment(COMMENT_2);
        fundPoolRepository.insert(fundPool);
        PreServiceFeeFund actualFundPool = fundPoolRepository.findById(ID_2);
        assertNotNull(actualFundPool);
        assertEquals(ID_2, actualFundPool.getId());
        assertEquals(NAME_2, actualFundPool.getName());
        assertEquals(COMMENT_2, actualFundPool.getComment());
    }

    @Test
    public void testFindById() {
        PreServiceFeeFund fundPool = fundPoolRepository.findById(ID_1);
        assertNotNull(fundPool);
        assertEquals(ID_1, fundPool.getId());
        assertEquals(NAME_1, fundPool.getName());
        assertEquals(COMMENT_1, fundPool.getComment());
    }

    @Test
    public void testFindAll() {
        List<PreServiceFeeFund> fundPools = fundPoolRepository.findAll();
        assertEquals(2, fundPools.size());
        assertFundPool(fundPools.get(0), ID_1, "FAS Q1 2019", new BigDecimal("50.00"), "some comment");
        assertFundPool(fundPools.get(1), "49060c9b-9cc2-4b93-b701-fffc82eb28b0", "Test fund", new BigDecimal("10.00"),
            "test comment");
    }

    @Test
    public void testFindNamesByUsageBatchId() {
        List<String> names =
            fundPoolRepository.findNamesByUsageBatchId("a163cca7-8eeb-449c-8a3c-29ff3ec82e58");
        assertEquals(1, names.size());
        assertEquals("Test fund", names.get(0));
    }

    @Test
    public void testDelete() {
        assertNotNull(fundPoolRepository.findById(ID_1));
        fundPoolRepository.delete(ID_1);
        assertNull(fundPoolRepository.findById(ID_1));
    }

    @Test
    public void testFindCountByName() {
        assertEquals(1, fundPoolRepository.findCountByName(NAME_1));
        assertEquals(0, fundPoolRepository.findCountByName("missing fund pool name"));
    }

    private void assertFundPool(PreServiceFeeFund fundPool, String id, String name, BigDecimal amount, String comment) {
        assertEquals(fundPool.getId(), id);
        assertEquals(fundPool.getName(), name);
        assertEquals(fundPool.getAmount(), amount);
        assertEquals(fundPool.getComment(), comment);
    }
}
