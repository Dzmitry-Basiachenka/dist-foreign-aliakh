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
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class ReceivePaidUsagesFromLmTest {

    private static final String FOLDER_NAME = "recieve-paid-usages-from-lm-test/";

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
    @TestData(fileName = FOLDER_NAME + "test-receive-paid-fas-usages-from-lm.groovy")
    public void testReceivePaidFasUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_usages_fas.json");
        testHelper.assertPaidUsages(testHelper.loadExpectedPaidUsages("usage/paid_usages_fas.json"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-receive-paid-nts-usages-from-lm.groovy")
    public void testReceivePaidNtsUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_usages_nts_receive_paid_from_lm_test.json");
        testHelper.assertPaidUsages(testHelper.loadExpectedPaidUsages("usage/paid_usages_nts.json"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-receive-paid-aacl-usages-from-lm.groovy")
    public void testReceivePaidAaclUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_usages_aacl.json");
        testHelper.assertPaidAaclUsages(testHelper.loadExpectedPaidUsages("usage/aacl/aacl_paid_usages.json"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-receive-paid-split-aacl-usages-from-lm.groovy")
    public void testReceivePaidSplitAaclUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_split_usages_aacl.json");
        testHelper.assertPaidAaclUsages(testHelper.loadExpectedPaidUsages("usage/aacl/aacl_paid_split_usages.json"));
        testHelper.assertScenarioAudit("de1d65f6-10c6-462c-bd97-44fcfc976934", List.of(
            Pair.of(ScenarioActionTypeEnum.UPDATED_AFTER_SPLIT, "Scenario has been updated after Split process")));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-receive-paid-split-usages-from-lm.groovy")
    public void testReceivePaidSplitUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_split_usages_fas.json");
        testHelper.assertPaidUsages(testHelper.loadExpectedPaidUsages("usage/paid_split_usages_fas.json"));
        testHelper.assertScenarioAudit("4924da00-ee87-41b3-9aed-caa5c5ba94f1", List.of(
            Pair.of(ScenarioActionTypeEnum.UPDATED_AFTER_SPLIT, "Scenario has been updated after Split process")));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-receive-post-distribution-usage-from-lm.groovy")
    public void testReceivePostDistributionUsageFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/post_distribution_paid_usages_fas.json");
        testHelper.assertPaidUsages(testHelper.loadExpectedPaidUsages("usage/post_distribution_paid_usages_fas.json"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-receive-post-distribution-aacl-usage-from-lm.groovy")
    public void testReceivePostDistributionAaclUsageFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/post_distribution_paid_usages_aacl.json");
        testHelper.assertPaidAaclUsages(
            testHelper.loadExpectedPaidUsages("usage/aacl/aacl_post_distribution_paid_usages.json"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "test-receive-post-distribution-split-usage-from-lm.groovy")
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
    @TestData(fileName = FOLDER_NAME + "test-receive-paid-information-from-lm.groovy")
    public void testReceivePaidInformationFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_usages.json");
        testHelper.assertPaidUsages(testHelper.loadExpectedPaidUsages("usage/paid_usages.json"));
    }
}
