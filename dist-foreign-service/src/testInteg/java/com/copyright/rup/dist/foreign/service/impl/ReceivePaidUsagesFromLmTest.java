package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.mock.aws.SqsClientMock;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.impl.mock.PaidUsageConsumerMock;
import com.copyright.rup.dist.foreign.service.impl.mock.SnsMock;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

    private static final BigDecimal AMOUNT_2000 = new BigDecimal("2000.0000000000");
    private static final BigDecimal AMOUNT_1000 = new BigDecimal("1000.0000000000");
    private static final BigDecimal AMOUNT_1680 = new BigDecimal("1680.0000000000");
    private static final BigDecimal AMOUNT_840 = new BigDecimal("840.0000000000");
    private static final BigDecimal AMOUNT_500 = new BigDecimal("500.0000000000");
    private static final BigDecimal AMOUNT_420 = new BigDecimal("420.0000000000");
    private static final BigDecimal AMOUNT_320 = new BigDecimal("320.0000000000");
    private static final BigDecimal AMOUNT_160 = new BigDecimal("160.0000000000");
    private static final BigDecimal AMOUNT_80 = new BigDecimal("80.0000000000");

    @Autowired
    @Qualifier("df.service.paidUsageConsumer")
    private PaidUsageConsumerMock paidUsageConsumer;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private SqsClientMock sqsClientMock;

    @Before
    public void reset() {
        sqsClientMock.reset();
    }

    @Test
    public void testReceivePaidUsagesFromLm() throws InterruptedException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        expectReceivePaidUsages("lm/paid_usages_fas.json");
        List<String> paidUsageIds = usageArchiveRepository.findPaidIds();
        assertEquals(1, CollectionUtils.size(paidUsageIds));
        assertPaidUsage(
            buildPaidUsage("a1bd3d85-f130-45ad-be9b-dd4344668b16", 1000009372L, AMOUNT_840, AMOUNT_160, AMOUNT_1000));
    }

    @Test
    public void testReceivePaidSplitUsagesFromLm() throws InterruptedException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        expectReceivePaidUsages("lm/paid_split_usages_fas.json");
        List<String> paidUsageIds = usageArchiveRepository.findPaidIds();
        assertEquals(2, CollectionUtils.size(paidUsageIds));
        assertPaidUsage(
            buildPaidUsage("a1bd3d85-f130-45ad-be9b-dd4344668b16", 1000009372L, AMOUNT_420, AMOUNT_80, AMOUNT_500));
        assertPaidUsage(
            buildPaidUsage("fbf146ca-3cab-4868-b31b-16e9daa4a7d0", 1000009522L, AMOUNT_420, AMOUNT_80, AMOUNT_500));
    }

    @Test
    public void testReceivePostDistributionUsageFromLm() throws InterruptedException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        expectReceivePaidUsages("lm/post_distribution_paid_usages_fas.json");
        List<String> paidUsageIds = usageArchiveRepository.findPaidIds();
        assertEquals(1, CollectionUtils.size(paidUsageIds));
        assertFalse(paidUsageIds.contains("ef058a3f-b60e-429b-b6e3-14d386eb86ba"));
        assertPaidUsage(
            buildPaidUsage("c3a24455-a92b-4572-b6b5-628a66572104", 1000002859L, AMOUNT_1680, AMOUNT_320, AMOUNT_2000));
    }

    @Test
    public void testReceivePostDistributionSplitUsageFromLm() throws InterruptedException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        expectReceivePaidUsages("lm/post_distribution_split_paid_usages_fas.json");
        List<String> paidUsageIds = usageArchiveRepository.findPaidIds();
        assertEquals(2, CollectionUtils.size(paidUsageIds));
        assertFalse(paidUsageIds.contains("ef058a3f-b60e-429b-b6e3-14d386eb86ba"));
        assertPaidUsage(
            buildPaidUsage("c3a24455-a92b-4572-b6b5-628a66572104", 1000002859L, AMOUNT_840, AMOUNT_160, AMOUNT_1000));
        assertPaidUsage(
            buildPaidUsage("fbb61f5e-35df-4081-b975-3d88e2a0af78", 1000009522L, AMOUNT_840, AMOUNT_160, AMOUNT_1000));
    }

    /**
     * Test case to verify consuming logic when received paid information for SENT_TO_LM, ARCHIVED and not existing
     * usages in one message.
     */
    @Test
    public void testReceivePaidInformationFromLm() throws InterruptedException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        expectReceivePaidUsages("lm/paid_usages.json");
        List<String> paidUsageIds = usageArchiveRepository.findPaidIds();
        assertEquals(2, CollectionUtils.size(paidUsageIds));
        assertPaidUsage(
            buildPaidUsage("a1bd3d85-f130-45ad-be9b-dd4344668b16", 1000002859L, AMOUNT_840, AMOUNT_160, AMOUNT_1000));
        assertPaidUsage(
            buildPaidUsage("c3a24455-a92b-4572-b6b5-628a66572104", 1000002859L, AMOUNT_1680, AMOUNT_320, AMOUNT_2000));
        assertFalse(paidUsageIds.contains("ef058a3f-b60e-429b-b6e3-14d386eb86ba"));
    }

    private void assertPaidUsage(PaidUsage expectedPaidUsage) {
        List<PaidUsage> paidUsages =
            usageArchiveRepository.findByIdAndStatus(usageArchiveRepository.findPaidIds(), UsageStatusEnum.PAID);
        PaidUsage actualPaidUsage =
            paidUsages.stream()
                .filter(paidUsage -> expectedPaidUsage.getLmDetailId().equals(paidUsage.getLmDetailId()))
                .findFirst()
                .orElse(null);
        assertNotNull(actualPaidUsage);
        assertPaidInformation(expectedPaidUsage, actualPaidUsage);
    }

    private void assertPaidInformation(PaidUsage expectedPaidUsage, PaidUsage actualPaidUsage) {
        assertEquals(expectedPaidUsage.getLmDetailId(), actualPaidUsage.getLmDetailId());
        assertEquals(expectedPaidUsage.getRightsholder().getAccountNumber(),
            actualPaidUsage.getRightsholder().getAccountNumber());
        assertEquals(expectedPaidUsage.getPayee().getAccountNumber(), actualPaidUsage.getPayee().getAccountNumber());
        assertEquals(expectedPaidUsage.getDistributionName(), actualPaidUsage.getDistributionName());
        assertEquals(expectedPaidUsage.getDistributionDate(), actualPaidUsage.getDistributionDate());
        assertEquals(expectedPaidUsage.getCccEventId(), actualPaidUsage.getCccEventId());
        assertEquals(expectedPaidUsage.getCheckNumber(), actualPaidUsage.getCheckNumber());
        assertEquals(expectedPaidUsage.getCheckDate(), actualPaidUsage.getCheckDate());
        assertEquals(expectedPaidUsage.getPeriodEndDate(), actualPaidUsage.getPeriodEndDate());
        assertEquals(expectedPaidUsage.getNetAmount(), actualPaidUsage.getNetAmount());
        assertEquals(expectedPaidUsage.getGrossAmount(), actualPaidUsage.getGrossAmount());
        assertEquals(expectedPaidUsage.getServiceFeeAmount(), actualPaidUsage.getServiceFeeAmount());
        assertEquals(expectedPaidUsage.getComment(), actualPaidUsage.getComment());
    }

    private PaidUsage buildPaidUsage(String lmDetailId, Long rhAccountNumber, BigDecimal netAmount,
                                     BigDecimal serviceFeeAmount, BigDecimal collectedAmount) {
        PaidUsage paidUsage = new PaidUsage();
        paidUsage.getRightsholder().setAccountNumber(rhAccountNumber);
        paidUsage.getPayee().setAccountNumber(1000010022L);
        paidUsage.setDistributionName("FDA March 17");
        paidUsage.setDistributionDate(OffsetDateTime.parse("2017-01-14T00:00-05:00"));
        paidUsage.setCccEventId("53256");
        paidUsage.setCheckDate(OffsetDateTime.parse("2017-01-15T00:00-05:00"));
        paidUsage.setCheckNumber("578945");
        paidUsage.setPeriodEndDate(OffsetDateTime.parse("2017-01-16T00:00-05:00"));
        paidUsage.setLmDetailId(lmDetailId);
        paidUsage.setNetAmount(netAmount);
        paidUsage.setServiceFeeAmount(serviceFeeAmount);
        paidUsage.setGrossAmount(collectedAmount);
        paidUsage.setComment("usage from usages.csv");
        return paidUsage;
    }

    private void expectReceivePaidUsages(String messageFilepath) throws InterruptedException {
        paidUsageConsumer.setLatch(new CountDownLatch(1));
        sqsClientMock.sendMessage("fda-test-df-consumer-sf-detail-paid",
            SnsMock.wrapBody(TestUtils.fileToString(this.getClass(), messageFilepath)), Collections.EMPTY_MAP);
        assertTrue(paidUsageConsumer.getLatch().await(2, TimeUnit.SECONDS));
        sqsClientMock.assertQueueMessagesReceived("fda-test-df-consumer-sf-detail-paid");
    }
}
