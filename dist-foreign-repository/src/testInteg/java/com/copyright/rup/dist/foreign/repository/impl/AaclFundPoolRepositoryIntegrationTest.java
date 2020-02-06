package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.repository.api.IAaclFundPoolRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test for {@link AaclFundPoolRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/02/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=aacl-fund-pool-repository-test-data-init.groovy"})
@Transactional
public class AaclFundPoolRepositoryIntegrationTest {

    @Autowired
    private IAaclFundPoolRepository aaclFundPoolRepository;

    @Test
    public void testAaclFundPoolExists() {
        assertTrue(aaclFundPoolRepository.aaclFundPoolExists("fund_pool_name 100%"));
        assertTrue(aaclFundPoolRepository.aaclFundPoolExists("FUND_POOL_NAME 100%"));
        assertFalse(aaclFundPoolRepository.aaclFundPoolExists("fund_pool_name"));
    }
}
