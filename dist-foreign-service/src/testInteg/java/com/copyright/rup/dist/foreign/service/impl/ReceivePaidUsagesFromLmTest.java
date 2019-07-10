package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.mock.aws.SqsClientMock;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.impl.mock.PaidUsageConsumerMock;
import com.copyright.rup.dist.foreign.service.impl.mock.SnsMock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public void testReceivePaidFasUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        expectReceivePaidUsages("lm/paid_usages_fas.json");
        assertPaidUsages("usage/paid_usages_fas.json");
    }

    @Test
    public void testReceivePaidNtsUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        expectReceivePaidUsages("lm/paid_usages_nts_receive_paid_from_lm_test.json");
        assertPaidUsages("usage/paid_usages_nts.json");
    }

    @Test
    public void testReceivePaidSplitUsagesFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        expectReceivePaidUsages("lm/paid_split_usages_fas.json");
        assertPaidUsages("usage/paid_split_usages_fas.json");
    }

    @Test
    public void testReceivePostDistributionUsageFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        expectReceivePaidUsages("lm/post_distribution_paid_usages_fas.json");
        assertPaidUsages("usage/post_distribution_paid_usages_fas.json");
    }

    @Test
    public void testReceivePostDistributionSplitUsageFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        expectReceivePaidUsages("lm/post_distribution_split_paid_usages_fas.json");
        assertPaidUsages("usage/post_distribution_split_paid_usages_fas.json");
    }

    /**
     * Test case to verify consuming logic when received paid information for SENT_TO_LM, ARCHIVED and not existing
     * usages in one message.
     */
    @Test
    public void testReceivePaidInformationFromLm() throws InterruptedException, IOException {
        assertTrue(CollectionUtils.isEmpty(usageArchiveRepository.findPaidIds()));
        expectReceivePaidUsages("lm/paid_usages.json");
        assertPaidUsages("usage/paid_usages.json");
    }

    private void assertPaidUsages(String expectedPaidUsageJsonFile) throws IOException {
        List<PaidUsage> expectedPaidUsages = loadExpectedPaidUsages(expectedPaidUsageJsonFile).stream()
            .sorted(Comparator.comparing(PaidUsage::getLmDetailId))
            .collect(Collectors.toList());
        List<PaidUsage> actualPaidUsages =
            usageArchiveRepository.findByIdAndStatus(usageArchiveRepository.findPaidIds(), UsageStatusEnum.PAID)
                .stream()
                .sorted(Comparator.comparing(PaidUsage::getLmDetailId))
                .collect(Collectors.toList());
        assertEquals(expectedPaidUsages.size(), actualPaidUsages.size());
        IntStream.range(0, expectedPaidUsages.size())
            .forEach(i -> assertPaidUsage(expectedPaidUsages.get(i), actualPaidUsages.get(i)));
    }

    private List<PaidUsage> loadExpectedPaidUsages(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<PaidUsage>>() {
        });
    }

    private void assertPaidUsage(PaidUsage expectedPaidUsage, PaidUsage actualPaidUsage) {
        assertNotNull(actualPaidUsage.getId());
        assertEquals(expectedPaidUsage.getStatus(), actualPaidUsage.getStatus());
        assertEquals(expectedPaidUsage.getWrWrkInst(), actualPaidUsage.getWrWrkInst());
        assertEquals(expectedPaidUsage.getWorkTitle(), actualPaidUsage.getWorkTitle());
        assertEquals(expectedPaidUsage.getArticle(), actualPaidUsage.getArticle());
        assertEquals(expectedPaidUsage.getStandardNumber(), actualPaidUsage.getStandardNumber());
        assertEquals(expectedPaidUsage.getStandardNumberType(), actualPaidUsage.getStandardNumberType());
        assertEquals(expectedPaidUsage.getPublisher(), actualPaidUsage.getPublisher());
        assertEquals(expectedPaidUsage.getPublicationDate(), actualPaidUsage.getPublicationDate());
        assertEquals(expectedPaidUsage.getMarket(), actualPaidUsage.getMarket());
        assertEquals(expectedPaidUsage.getMarketPeriodFrom(), actualPaidUsage.getMarketPeriodFrom());
        assertEquals(expectedPaidUsage.getMarketPeriodTo(), actualPaidUsage.getMarketPeriodTo());
        assertEquals(expectedPaidUsage.getAuthor(), actualPaidUsage.getAuthor());
        assertEquals(expectedPaidUsage.getNumberOfCopies(), actualPaidUsage.getNumberOfCopies());
        assertEquals(expectedPaidUsage.getReportedValue(), actualPaidUsage.getReportedValue());
        assertEquals(expectedPaidUsage.isRhParticipating(), actualPaidUsage.isRhParticipating());
        assertEquals(expectedPaidUsage.getProductFamily(), actualPaidUsage.getProductFamily());
        assertEquals(expectedPaidUsage.getSystemTitle(), actualPaidUsage.getSystemTitle());
        assertEquals(expectedPaidUsage.getServiceFee(), actualPaidUsage.getServiceFee());
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

    private void expectReceivePaidUsages(String messageFilepath) throws InterruptedException {
        paidUsageConsumer.setLatch(new CountDownLatch(1));
        sqsClientMock.sendMessage("fda-test-df-consumer-sf-detail-paid",
            SnsMock.wrapBody(TestUtils.fileToString(this.getClass(), messageFilepath)), Collections.EMPTY_MAP);
        assertTrue(paidUsageConsumer.getLatch().await(2, TimeUnit.SECONDS));
        sqsClientMock.assertQueueMessagesReceived("fda-test-df-consumer-sf-detail-paid");
    }
}
