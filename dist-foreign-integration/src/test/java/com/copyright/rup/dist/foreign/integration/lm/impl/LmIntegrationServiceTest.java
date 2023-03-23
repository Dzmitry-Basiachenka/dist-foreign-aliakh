package com.copyright.rup.dist.foreign.integration.lm.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsageMessage;
import com.copyright.rup.dist.foreign.integration.lm.impl.producer.ExternalUsageProducer;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Verifies {@link LmIntegrationService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Ihar Suvorau
 */
public class LmIntegrationServiceTest {

    private static final String SCENARIO_ID = "390eb422-255b-4eb3-bce5-71ae408e2de9";
    private static final String SCENARIO_NAME = "Test Scenario";
    private static final String ACL_PRODUCT_FAMILY = "ACL";
    private static final String FAS_PRODUCT_FAMILY = "FAS";

    private ILmIntegrationService lmIntegrationService;
    private ExternalUsageProducer externalUsageProducer;
    private ExternalUsage externalUsage;

    @Before
    public void setUp() {
        lmIntegrationService = new LmIntegrationService();
        externalUsageProducer = createMock(ExternalUsageProducer.class);
        Whitebox.setInternalState(lmIntegrationService, "batchSize", 2);
        Whitebox.setInternalState(lmIntegrationService, "externalUsageProducer", externalUsageProducer);
        externalUsage = new ExternalUsage(new Usage());
    }

    @Test
    public void testSendToLmAclSingleMessage() {
        Capture<ExternalUsageMessage> externalUsageMessageCapture = newCapture();
        externalUsageProducer.send(capture(externalUsageMessageCapture));
        expectLastCall().once();
        replay(externalUsageProducer);
        lmIntegrationService.sendToLm(List.of(externalUsage, externalUsage),
            SCENARIO_ID, SCENARIO_NAME, ACL_PRODUCT_FAMILY, 2);
        ExternalUsageMessage externalUsageMessage = externalUsageMessageCapture.getValue();
        assertHeaders(externalUsageMessage.getHeaders(), ACL_PRODUCT_FAMILY, 2);
        assertEquals(List.of(externalUsage, externalUsage), externalUsageMessage.getExternalUsages());
        verify(externalUsageProducer);
    }

    @Test
    public void testSendToLmAclNotSingleMessages() {
        Capture<ExternalUsageMessage> externalUsageMessageCapture1 = newCapture();
        externalUsageProducer.send(capture(externalUsageMessageCapture1));
        expectLastCall().once();
        Capture<ExternalUsageMessage> externalUsageMessageCapture2 = newCapture();
        externalUsageProducer.send(capture(externalUsageMessageCapture2));
        expectLastCall().once();
        replay(externalUsageProducer);
        lmIntegrationService.sendToLm(List.of(externalUsage, externalUsage, externalUsage),
            SCENARIO_ID, SCENARIO_NAME, ACL_PRODUCT_FAMILY, 3);
        ExternalUsageMessage externalUsageMessage1 = externalUsageMessageCapture1.getValue();
        assertHeaders(externalUsageMessage1.getHeaders(), ACL_PRODUCT_FAMILY, 3);
        assertEquals(List.of(externalUsage, externalUsage), externalUsageMessage1.getExternalUsages());
        ExternalUsageMessage externalUsageMessage2 = externalUsageMessageCapture2.getValue();
        assertHeaders(externalUsageMessage2.getHeaders(), ACL_PRODUCT_FAMILY, 3);
        assertEquals(List.of(externalUsage), externalUsageMessage2.getExternalUsages());
        verify(externalUsageProducer);
    }

    @Test
    public void testSendToLmFasSingleMessage() {
        Capture<ExternalUsageMessage> externalUsageMessageCapture = newCapture();
        externalUsageProducer.send(capture(externalUsageMessageCapture));
        expectLastCall().once();
        replay(externalUsageProducer);
        lmIntegrationService.sendToLm(List.of(externalUsage, externalUsage), buildScenario(), 2);
        ExternalUsageMessage externalUsageMessage = externalUsageMessageCapture.getValue();
        assertHeaders(externalUsageMessage.getHeaders(), FAS_PRODUCT_FAMILY, 2);
        assertEquals(List.of(externalUsage, externalUsage), externalUsageMessage.getExternalUsages());
        verify(externalUsageProducer);
    }

    @Test
    public void testSendToLmFasNotSingleMessages() {
        Capture<ExternalUsageMessage> externalUsageMessageCapture1 = newCapture();
        externalUsageProducer.send(capture(externalUsageMessageCapture1));
        expectLastCall().once();
        Capture<ExternalUsageMessage> externalUsageMessageCapture2 = newCapture();
        externalUsageProducer.send(capture(externalUsageMessageCapture2));
        expectLastCall().once();
        replay(externalUsageProducer);
        lmIntegrationService.sendToLm(List.of(externalUsage, externalUsage, externalUsage), buildScenario(), 3);
        ExternalUsageMessage externalUsageMessage1 = externalUsageMessageCapture1.getValue();
        assertHeaders(externalUsageMessage1.getHeaders(), FAS_PRODUCT_FAMILY, 3);
        assertEquals(List.of(externalUsage, externalUsage), externalUsageMessage1.getExternalUsages());
        ExternalUsageMessage externalUsageMessage2 = externalUsageMessageCapture2.getValue();
        assertHeaders(externalUsageMessage2.getHeaders(), FAS_PRODUCT_FAMILY, 3);
        assertEquals(List.of(externalUsage), externalUsageMessage2.getExternalUsages());
        verify(externalUsageProducer);
    }

    private static Scenario buildScenario() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName(SCENARIO_NAME);
        scenario.setProductFamily(FAS_PRODUCT_FAMILY);
        return scenario;
    }

    private void assertHeaders(Map<String, Object> headers, String productFamily, int numberOfMessages) {
        assertEquals("FDA", headers.get("source"));
        assertEquals(SCENARIO_ID, headers.get("scenarioId"));
        assertEquals(SCENARIO_NAME, headers.get("scenarioName"));
        assertEquals(productFamily, headers.get("productFamily"));
        assertEquals(numberOfMessages, headers.get("numberOfMessages"));
        Object sendDate = headers.get("sendDate");
        assertNotNull(sendDate);
        assertNotNull(OffsetDateTime.parse((String) sendDate,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS Z")));
    }
}
