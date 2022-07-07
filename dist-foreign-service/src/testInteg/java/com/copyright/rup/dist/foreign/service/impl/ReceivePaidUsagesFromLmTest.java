package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Verifies consuming paid information from LM and storing paid data to database.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 02/22/18
 *
 * @author Darya Baraukova
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class ReceivePaidUsagesFromLmTest {

    private static final String TEST_DATA = "receive-paid-usages-from-lm-data-init.groovy";

    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void reset() {
        testHelper.reset();
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    //TODO: split test data into separate files for each test method
    @TestData(fileName = TEST_DATA)
    public void testReceivePaidFasUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_usages_fas.json");
        testHelper.assertPaidUsages(testHelper.loadExpectedPaidUsages("usage/paid_usages_fas.json"));
    }

    @Test
    //TODO: split test data into separate files for each test method
    @TestData(fileName = TEST_DATA)
    public void testReceivePaidNtsUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_usages_nts_receive_paid_from_lm_test.json");
        testHelper.assertPaidUsages(testHelper.loadExpectedPaidUsages("usage/paid_usages_nts.json"));
    }

    @Test
    //TODO: split test data into separate files for each test method
    @TestData(fileName = TEST_DATA)
    public void testReceivePaidAaclUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_usages_aacl.json");
        testHelper.assertPaidAaclUsages(testHelper.loadExpectedPaidUsages("usage/aacl/aacl_paid_usages.json"));
    }

    @Test
    //TODO: split test data into separate files for each test method
    @TestData(fileName = TEST_DATA)
    public void testReceivePaidSplitAaclUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_split_usages_aacl.json");
        testHelper.assertPaidAaclUsages(testHelper.loadExpectedPaidUsages("usage/aacl/aacl_paid_split_usages.json"));
        testHelper.assertScenarioAudit("de1d65f6-10c6-462c-bd97-44fcfc976934", Collections.singletonList(
            Pair.of(ScenarioActionTypeEnum.UPDATED_AFTER_SPLIT, "Scenario has been updated after Split process")));
    }

    @Test
    //TODO: split test data into separate files for each test method
    @TestData(fileName = TEST_DATA)
    public void testReceivePaidSplitUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_split_usages_fas.json");
        testHelper.assertPaidUsages(testHelper.loadExpectedPaidUsages("usage/paid_split_usages_fas.json"));
        testHelper.assertScenarioAudit("4924da00-ee87-41b3-9aed-caa5c5ba94f1", Collections.singletonList(
            Pair.of(ScenarioActionTypeEnum.UPDATED_AFTER_SPLIT, "Scenario has been updated after Split process")));
    }

    @Test
    //TODO: split test data into separate files for each test method
    @TestData(fileName = TEST_DATA)
    public void testReceivePostDistributionUsageFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/post_distribution_paid_usages_fas.json");
        testHelper.assertPaidUsages(testHelper.loadExpectedPaidUsages("usage/post_distribution_paid_usages_fas.json"));
    }

    @Test
    //TODO: split test data into separate files for each test method
    @TestData(fileName = TEST_DATA)
    public void testReceivePostDistributionAaclUsageFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/post_distribution_paid_usages_aacl.json");
        testHelper.assertPaidAaclUsages(
            testHelper.loadExpectedPaidUsages("usage/aacl/aacl_post_distribution_paid_usages.json"));
    }

    @Test
    //TODO: split test data into separate files for each test method
    @TestData(fileName = TEST_DATA)
    public void testReceivePostDistributionSplitUsageFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/post_distribution_split_paid_usages_fas.json");
        testHelper.assertPaidUsages(
            testHelper.loadExpectedPaidUsages("usage/post_distribution_split_paid_usages_fas.json"));
    }

    /**
     * Test case to verify consuming logic when received paid information for SENT_TO_LM, ARCHIVED and not existing
     * usages in one message.
     */
    @Test
    //TODO: split test data into separate files for each test method
    @TestData(fileName = TEST_DATA)
    public void testReceivePaidInformationFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_usages.json");
        testHelper.assertPaidUsages(testHelper.loadExpectedPaidUsages("usage/paid_usages.json"));
    }
}
