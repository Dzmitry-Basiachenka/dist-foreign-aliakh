package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.common.test.JsonEndpointMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;

import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
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

    @EndpointInject(context = "df.integration.camelContext", uri = "mock:sf.processor.detail")
    private MockEndpoint mockLmEndPoint;

    @Autowired
    private IScenarioService scenarioService;

    @Test
    public void testSendToLm() throws Exception {
        String expectedJson = TestUtils.fileToString(this.getClass(), "details/details_to_lm.json");
        mockLmEndPoint.expectedMessageCount(1);
        mockLmEndPoint.expectedHeaderReceived("source", "FDA");
        mockLmEndPoint.expects(new JsonEndpointMatcher(mockLmEndPoint, Collections.singletonList(expectedJson)));
        Scenario scenario = new Scenario();
        scenario.setId("4c014547-06f3-4840-94ff-6249730d537d");
        scenarioService.sendToLm(scenario);
        mockLmEndPoint.assertIsSatisfied();
    }
}
