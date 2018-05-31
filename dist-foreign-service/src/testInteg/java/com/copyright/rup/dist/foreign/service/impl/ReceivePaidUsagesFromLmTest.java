package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.mock.PaidUsageConsumerMock;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
public class ReceivePaidUsagesFromLmTest {

    @Produce
    private ProducerTemplate template;
    @Autowired
    @Qualifier("df.service.paidUsageConsumer")
    private PaidUsageConsumerMock paidUsageConsumer;
    @Autowired
    private IUsageService usageService;

    @Test
    public void testReceivePaidUsagesFromLm() throws InterruptedException {
        expectReceivePaidUsages();
        List<UsageDto> usages =
            usageService.getByScenarioAndRhAccountNumber(1000002859L, buildExpectedScenario(), null, null, null);
        assertTrue(CollectionUtils.isNotEmpty(usages));
        assertEquals(1, usages.size());
    }

    private void expectReceivePaidUsages() throws InterruptedException {
        template.setDefaultEndpointUri("direct:queue:Consumer.df.VirtualTopic.sf.processor.detail.paid");
        CountDownLatch latch = new CountDownLatch(1);
        paidUsageConsumer.setLatch(latch);
        template.sendBodyAndHeader(TestUtils.fileToString(this.getClass(), "lm/paid_usages_fas.json"), "source", "FDA");
        assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    private Scenario buildExpectedScenario() {
        Scenario scenario = new Scenario();
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        scenario.setId("5b9ab240-17e3-11e8-b566-0800200c9a66");
        return scenario;
    }
}
