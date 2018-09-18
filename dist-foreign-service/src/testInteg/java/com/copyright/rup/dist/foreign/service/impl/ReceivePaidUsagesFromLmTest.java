package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.impl.mock.PaidUsageConsumerMock;

import com.google.common.collect.ImmutableList;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Verifies counsuming paid information from LM and storing paid data to database.
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
@Transactional
public class ReceivePaidUsagesFromLmTest {

    @Produce
    private ProducerTemplate template;
    @Autowired
    @Qualifier("df.service.paidUsageConsumer")
    private PaidUsageConsumerMock paidUsageConsumer;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;

    @Test
    public void testReceivePaidUsagesFromLm() throws InterruptedException {
        int paidUsagesCount = usageArchiveRepository.findPaidIds().size();
        expectReceivePaidUsages("lm/paid_usages_fas.json");
        assertEquals(1 + paidUsagesCount, usageArchiveRepository.findPaidIds().size());
        assertPaidUsageByLmDetailId("a1bd3d85-f130-45ad-be9b-dd4344668b16");
    }

    @Test
    public void testReceivePostDistributionUsageFromLm() throws InterruptedException {
        int paidUsagesCount = usageArchiveRepository.findPaidIds().size();
        String predistributionPaidUsageId = "ef058a3f-b60e-429b-b6e3-14d386eb86ba";
        expectReceivePaidUsages("lm/post_distribution_paid_usages_fas.json");
        assertEquals(1 + paidUsagesCount, usageArchiveRepository.findPaidIds().size());
        List<String> paidUsageIds = usageArchiveRepository.findPaidIds();
        assertFalse(paidUsageIds.contains(predistributionPaidUsageId));
        assertPaidUsageByLmDetailId("c3a24455-a92b-4572-b6b5-628a66572104");
    }

    @Test
    /**
     * Test case to verify consuming logic when received paid information for SENT_TO_LM, ARCHIVED and not existing
     * usages in one message.
     */
    public void testReceivePaidInformationFromLm() throws InterruptedException {
        String predistributionPaidUsageId = "ef058a3f-b60e-429b-b6e3-14d386eb86ba";
        int paidUsagesCount = usageArchiveRepository.findPaidIds().size();
        expectReceivePaidUsages("lm/paid_usages.json");
        assertEquals(2 + paidUsagesCount, usageArchiveRepository.findPaidIds().size());
        assertPaidUsageByLmDetailId("c3a24455-a92b-4572-b6b5-628a66572104");
        assertPaidUsageByLmDetailId("a1bd3d85-f130-45ad-be9b-dd4344668b16");
        assertFalse(usageArchiveRepository.findPaidIds().contains(predistributionPaidUsageId));
    }

    private void assertPaidUsageByLmDetailId(String lmDetailId) {
        List<PaidUsage> paidUsages =
            usageArchiveRepository.findByIdAndStatus(usageArchiveRepository.findPaidIds(), UsageStatusEnum.PAID);
        PaidUsage actualPaidUsage =
            paidUsages.stream()
                .filter(paidUsage -> lmDetailId.equals(paidUsage.getLmDetailId()))
                .findFirst()
                .orElse(null);
        assertNotNull(actualPaidUsage);
        assertPaidInformationNotEmpty(actualPaidUsage.getId());
    }

    private void assertPaidInformationNotEmpty(String expectedPaidUsageId) {
        List<PaidUsage> paidUsages =
            usageArchiveRepository.findByIdAndStatus(ImmutableList.of(expectedPaidUsageId), UsageStatusEnum.PAID);
        PaidUsage paidUsage = paidUsages.get(0);
        assertNotNull(paidUsage.getLmDetailId());
        assertNotNull(paidUsage.getPayee());
        assertNotNull(paidUsage.getCheckDate());
        assertNotNull(paidUsage.getCheckNumber());
        assertNotNull(paidUsage.getPeriodEndDate());
        assertNotNull(paidUsage.getDistributionName());
        assertNotNull(paidUsage.getDistributionDate());
        assertNotNull(paidUsage.getCccEventId());
    }

    private void expectReceivePaidUsages(String messageFilepath) throws InterruptedException {
        template.setDefaultEndpointUri("direct:queue:Consumer.df.VirtualTopic.sf.processor.detail.paid");
        CountDownLatch latch = new CountDownLatch(1);
        paidUsageConsumer.setLatch(latch);
        template.sendBodyAndHeader(TestUtils.fileToString(this.getClass(), messageFilepath), "source", "FDA");
        assertTrue(latch.await(10, TimeUnit.SECONDS));
    }
}
