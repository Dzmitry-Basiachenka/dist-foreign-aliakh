package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.impl.mock.SqsClientMock;

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

/**
 * Verifies Send To LM functionality.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=send-scenario-to-lm-data-init.groovy"})
public class SendScenarioToLmTest {

    @Autowired
    private IScenarioService scenarioService;

    @Autowired
    private SqsClientMock sqsClientMock;

    @Before
    public void setUp() {
        sqsClientMock.reset();
    }

    @Test
    public void testSendToLm() {
        Scenario scenario = new Scenario();
        scenario.setId("4c014547-06f3-4840-94ff-6249730d537d");
        sqsClientMock.prepareSendMessageExpectations("sf-detail.fifo",
            TestUtils.fileToString(this.getClass(), "details/details_to_lm.json"), Collections.EMPTY_LIST,
            ImmutableMap.of("source", "FDA"));
        scenarioService.sendToLm(scenario);
        sqsClientMock.assertSendMessage();
    }
}
