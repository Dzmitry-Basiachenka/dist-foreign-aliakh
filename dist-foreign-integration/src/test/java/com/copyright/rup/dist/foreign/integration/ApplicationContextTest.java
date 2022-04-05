package com.copyright.rup.dist.foreign.integration;

import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.integration.lm.impl.producer.ExternalUsageProducer;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.client.RestTemplate;

/**
 * Verifies spring application context.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/21/2017
 *
 * @author Mikalai Bezmen
 */
public class ApplicationContextTest {

    private static final String CONFIG_LOCATION =
        "/com/copyright/rup/dist/foreign/integration/dist-foreign-integration-context-test.xml";

    @Test
    public void testContext() {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext(CONFIG_LOCATION);
            assertNotNull(context);
            assertNotNull(context.getBean(RestTemplate.class));
            assertNotNull(context.getBean(IPrmIntegrationService.class));
            assertNotNull(context.getBean("df.integration.oracleRhTaxCountryCacheService"));
            assertNotNull(context.getBean("df.integration.oracleRhTaxCountryService"));
            assertNotNull(context.getBean("df.integration.oracleRhTaxInformationService"));
            assertNotNull(context.getBean("df.integration.piIntegrationCacheService"));
            assertNotNull(context.getBean("df.integration.piIntegrationService"));
            assertNotNull(context.getBean("df.integration.rmsIntegrationService"));
            assertNotNull(context.getBean("df.integration.telesalesService"));
            assertNotNull(context.getBean("df.integration.telesalesCacheService"));
            assertNotNull(context.getBean("df.integration.prmIneligibleRightsholderService"));
            assertNotNull(context.getBean("dist.common.integration.rest.prmCountryService"));
            assertNotNull(context.getBean("dist.common.integration.rest.prmRightsholderAsyncService"));
            assertNotNull(context.getBean("dist.common.integration.rest.prmRightsholderService"));
            assertNotNull(context.getBean("dist.common.integration.rest.prmRollUpAsyncService"));
            assertNotNull(context.getBean("dist.common.integration.rest.prmRollUpService"));
            assertNotNull(context.getBean("dist.common.integration.rmsRightsService"));
            assertNotNull(context.getBean("dist.common.integration.rmsRightsAssignmentService"));
            assertNotNull(context.getBean(ExternalUsageProducer.class));
        } catch (Exception e) {
            throw new AssertionError("Context is not valid: ", e);
        }
    }
}
