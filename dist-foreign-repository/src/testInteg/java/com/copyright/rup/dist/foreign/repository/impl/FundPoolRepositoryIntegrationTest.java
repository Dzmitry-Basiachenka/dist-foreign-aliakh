package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.FundPool;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
@Transactional
public class FundPoolRepositoryIntegrationTest {

    private static final String ID_1 = "b5b64c3a-55d2-462e-b169-362dca6a4dd7";
    private static final String ID_2 = "76282dbc-2468-48d4-b926-93d3458a656b";
    private static final String ID_3 = "6fe5044d-15a3-47fe-913e-69f3bf353bef";
    private static final String NAME_1 = "Q1 2019 100%";
    private static final String NAME_2 = "NTS Q2 2019";
    private static final String COMMENT_1 = "some comment";
    private static final String COMMENT_2 = "other comment";
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final String NTS_PRODUCT_FAMILY = "NTS";

    @Autowired
    private FundPoolRepository fundPoolRepository;

    @Test
    public void testInsert() {
        assertNull(fundPoolRepository.findById(ID_2));
        FundPool fundPool = new FundPool();
        fundPool.setId(ID_2);
        fundPool.setProductFamily(NTS_PRODUCT_FAMILY);
        fundPool.setName(NAME_2);
        fundPool.setComment(COMMENT_2);
        fundPoolRepository.insert(fundPool);
        FundPool actualFundPool = fundPoolRepository.findById(ID_2);
        assertNotNull(actualFundPool);
        assertEquals(ID_2, actualFundPool.getId());
        assertEquals(NTS_PRODUCT_FAMILY, actualFundPool.getProductFamily());
        assertEquals(NAME_2, actualFundPool.getName());
        assertEquals(COMMENT_2, actualFundPool.getComment());
    }

    @Test
    public void testFindById() {
        FundPool fundPool = fundPoolRepository.findById(ID_1);
        assertNotNull(fundPool);
        assertEquals(ID_1, fundPool.getId());
        assertEquals(NAME_1, fundPool.getName());
        assertEquals(COMMENT_1, fundPool.getComment());
    }

    @Test
    public void testFindByProductFamily() {
        List<FundPool> fundPools = fundPoolRepository.findByProductFamily("NTS");
        assertEquals(3, fundPools.size());
        assertFundPool(fundPools.get(0), ID_1, NTS_PRODUCT_FAMILY, NAME_1, new BigDecimal("50.00"),
            "some comment");
        assertFundPool(fundPools.get(1), "49060c9b-9cc2-4b93-b701-fffc82eb28b0", NTS_PRODUCT_FAMILY, "Test fund",
            new BigDecimal("10.00"), "test comment");
        assertFundPool(fundPools.get(2), "a40132c0-d724-4450-81d2-456e67ff6f64", NTS_PRODUCT_FAMILY,
            "Archived Pre-Service fee fund", new BigDecimal("99.00"), null);
    }

    @Test
    public void testFindNtsNotAttachedToScenario() {
        List<FundPool> fundPools = fundPoolRepository.findNtsNotAttachedToScenario();
        assertEquals(1, fundPools.size());
        assertEquals(fundPools.get(0).getId(), "49060c9b-9cc2-4b93-b701-fffc82eb28b0");
    }

    @Test
    public void testFindNamesByUsageBatchId() {
        List<String> names =
            fundPoolRepository.findNamesByUsageBatchId("63b45167-a6ce-4cd5-84c6-5167916aee98");
        assertEquals(0, names.size());
        names =
            fundPoolRepository.findNamesByUsageBatchId("a163cca7-8eeb-449c-8a3c-29ff3ec82e58");
        assertEquals(1, names.size());
        assertEquals("Test fund", names.get(0));
        names =
            fundPoolRepository.findNamesByUsageBatchId("1a615c47-531a-4a27-a4f3-a5bd3d5a4b1c");
        assertEquals(1, names.size());
        assertEquals("Archived Pre-Service fee fund", names.get(0));
    }

    @Test
    public void testDelete() {
        assertNotNull(fundPoolRepository.findById(ID_1));
        fundPoolRepository.delete(ID_1);
        assertNull(fundPoolRepository.findById(ID_1));
    }

    @Test
    public void testFundPoolExists() {
        assertTrue(fundPoolRepository.fundPoolExists(NTS_PRODUCT_FAMILY, NAME_1));
        assertTrue(fundPoolRepository.fundPoolExists(NTS_PRODUCT_FAMILY, "q1 2019 100%"));
        assertFalse(fundPoolRepository.fundPoolExists(NTS_PRODUCT_FAMILY, "Q1 2019"));
        assertFalse(fundPoolRepository.fundPoolExists(AACL_PRODUCT_FAMILY, NAME_1));
        FundPool fundPool = new FundPool();
        fundPool.setId(ID_3);
        fundPool.setProductFamily(AACL_PRODUCT_FAMILY);
        fundPool.setName(NAME_1);
        fundPoolRepository.insert(fundPool);
        assertTrue(fundPoolRepository.fundPoolExists(AACL_PRODUCT_FAMILY, NAME_1));
        assertTrue(fundPoolRepository.fundPoolExists(AACL_PRODUCT_FAMILY, "q1 2019 100%"));
        assertFalse(fundPoolRepository.fundPoolExists(AACL_PRODUCT_FAMILY, "Q1 2019"));
    }

    private void assertFundPool(FundPool fundPool, String id, String productFamily, String name, BigDecimal amount,
                                String comment) {
        assertEquals(id, fundPool.getId());
        assertEquals(productFamily, fundPool.getProductFamily());
        assertEquals(name, fundPool.getName());
        assertEquals(amount, fundPool.getTotalAmount());
        assertEquals(comment, fundPool.getComment());
    }
}
