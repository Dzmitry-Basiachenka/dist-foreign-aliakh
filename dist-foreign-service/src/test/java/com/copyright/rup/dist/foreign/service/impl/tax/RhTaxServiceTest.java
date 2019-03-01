package com.copyright.rup.dist.foreign.service.impl.tax;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import com.copyright.rup.dist.foreign.service.api.IUsageService;
import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link RhTaxService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/05/18
 *
 * @author Uladzislau Shalamitski
 */
public class RhTaxServiceTest {

    private RhTaxService rhTaxService;
    private IUsageService usageService;
    private IUsageAuditService usageAuditService;
    private IOracleIntegrationService oracleIntegrationServiceMock;

    @Before
    public void setUp() {
        rhTaxService = new RhTaxService();
        usageService = createMock(IUsageService.class);
        usageAuditService = createMock(IUsageAuditService.class);
        oracleIntegrationServiceMock = createMock(IOracleIntegrationService.class);
        rhTaxService.setOracleIntegrationService(oracleIntegrationServiceMock);
        rhTaxService.setUsageAuditService(usageAuditService);
        rhTaxService.setUsageService(usageService);
    }

    @Test
    public void testProcessTaxCountryCode() {
        Usage usage = buildUsage();
        String usageId = usage.getId();
        expect(oracleIntegrationServiceMock.isUsCountryCode(1000001534L)).andReturn(true).once();
        usageService.updateProcessedUsage(usage);
        expectLastCall().once();
        usageAuditService.logAction(usageId, UsageActionTypeEnum.US_TAX_COUNTRY, "Rightsholder tax country is US");
        expectLastCall().once();
        replay(oracleIntegrationServiceMock, usageService, usageAuditService);
        rhTaxService.processTaxCountryCode(usage);
        assertEquals(UsageStatusEnum.US_TAX_COUNTRY, usage.getStatus());
        verify(oracleIntegrationServiceMock, usageService, usageAuditService);
    }

    @Test
    public void testProcessTaxCountryCodeNonUs() {
        Usage usage = buildUsage();
        expect(oracleIntegrationServiceMock.isUsCountryCode(1000001534L)).andReturn(false).once();
        replay(oracleIntegrationServiceMock, usageService, usageAuditService);
        rhTaxService.processTaxCountryCode(usage);
        assertNull(usage.getStatus());
        verify(oracleIntegrationServiceMock, usageService, usageAuditService);
    }

    @Test(expected = NullPointerException.class)
    public void testProcessTaxCountryCodeNullUsage() {
        Usage usage = new Usage();
        rhTaxService.processTaxCountryCode(usage);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.getRightsholder().setAccountNumber(1000001534L);
        return usage;
    }
}
