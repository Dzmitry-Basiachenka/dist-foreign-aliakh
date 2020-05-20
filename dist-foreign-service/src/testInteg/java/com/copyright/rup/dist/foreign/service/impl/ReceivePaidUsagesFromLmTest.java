package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

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
@TestPropertySource(properties = {"test.liquibase.changelog=receive-paid-usages-from-lm-data-init.groovy"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ReceivePaidUsagesFromLmTest {

    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IAaclUsageService aaclUsageService;

    @Before
    public void reset() {
        testHelper.reset();
    }

    @Test
    public void testReceivePaidFasUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_usages_fas.json");
        assertPaidUsages("usage/paid_usages_fas.json", usageDtos ->
            usageService.getForAudit(new AuditFilter(), null, null));
    }

    @Test
    public void testReceivePaidNtsUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_usages_nts_receive_paid_from_lm_test.json");
        assertPaidUsages("usage/paid_usages_nts.json", usageDtos ->
            usageService.getForAudit(new AuditFilter(), null, null));
    }

    @Test
    public void testReceivePaidAaclUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_usages_aacl.json");
        assertPaidUsages("usage/aacl/aacl_paid_usages.json", usageDtos ->
            aaclUsageService.getForAudit(new AuditFilter(), null, null));
    }

    @Test
    public void testReceivePaidSplitAaclUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_split_usages_aacl.json");
        assertPaidUsages("usage/aacl/aacl_paid_split_usages.json", usageDtos ->
            aaclUsageService.getForAudit(new AuditFilter(), null, null));
    }

    @Test
    public void testReceivePaidSplitUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_split_usages_fas.json");
        assertPaidUsages("usage/paid_split_usages_fas.json", usageDtos ->
            usageService.getForAudit(new AuditFilter(), null, null));
    }

    @Test
    public void testReceivePostDistributionUsageFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/post_distribution_paid_usages_fas.json");
        assertPaidUsages("usage/post_distribution_paid_usages_fas.json", usageDtos ->
            usageService.getForAudit(new AuditFilter(), null, null));
    }

    @Test
    public void testReceivePostDistributionAaclUsageFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/post_distribution_paid_usages_aacl.json");
        assertPaidUsages("usage/aacl/aacl_post_distribution_paid_usages.json", usageDtos ->
            aaclUsageService.getForAudit(new AuditFilter(), null, null));
    }

    @Test
    public void testReceivePostDistributionSplitUsageFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/post_distribution_split_paid_usages_fas.json");
        assertPaidUsages("usage/post_distribution_split_paid_usages_fas.json", usageDtos ->
            usageService.getForAudit(new AuditFilter(), null, null));
    }

    /**
     * Test case to verify consuming logic when received paid information for SENT_TO_LM, ARCHIVED and not existing
     * usages in one message.
     */
    @Test
    public void testReceivePaidInformationFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        testHelper.receivePaidUsagesFromLm("lm/paid_usages.json");
        Function<AuditFilter, List<UsageDto>> function = usageDtos ->
            usageService.getForAudit(new AuditFilter(), null, null);
        assertPaidUsages("usage/paid_usages.json", function);
    }

    private void assertPaidUsages(String expectedPaidUsageJsonFile, Function<AuditFilter, List<UsageDto>> function)
        throws IOException {
        List<PaidUsage> expectedPaidUsages = loadExpectedPaidUsages(expectedPaidUsageJsonFile);
        testHelper.assertPaidUsages(expectedPaidUsages, function);
    }

    private List<PaidUsage> loadExpectedPaidUsages(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<PaidUsage>>() {
        });
    }
}
