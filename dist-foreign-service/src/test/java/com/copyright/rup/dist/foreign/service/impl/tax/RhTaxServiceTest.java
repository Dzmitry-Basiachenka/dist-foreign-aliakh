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
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

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
    private IUsageRepository usageRepository;
    private IUsageAuditService usageAuditService;
    private IOracleIntegrationService oracleIntegrationServiceMock;

    @Before
    public void setUp() {
        rhTaxService = new RhTaxService();
        usageRepository = createMock(IUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        oracleIntegrationServiceMock = createMock(IOracleIntegrationService.class);
        rhTaxService.setOracleIntegrationService(oracleIntegrationServiceMock);
        rhTaxService.setUsageAuditService(usageAuditService);
        rhTaxService.setUsageRepository(usageRepository);
    }

    @Test
    public void testProcessTaxCountryCode() {
        Usage usage = buildUsage();
        String usageId = usage.getId();
        expect(oracleIntegrationServiceMock.isUsCountryCode(1000001534L)).andReturn(true).once();
        usageRepository.updateStatus(Collections.singleton(usageId), UsageStatusEnum.US_TAX_COUNTRY);
        expectLastCall().once();
        usageAuditService.logAction(usageId, UsageActionTypeEnum.US_TAX_COUNTRY, "Rightsholder tax country is US");
        expectLastCall().once();
        replay(oracleIntegrationServiceMock, usageRepository, usageAuditService);
        rhTaxService.processTaxCountryCode(usage);
        assertEquals(UsageStatusEnum.US_TAX_COUNTRY, usage.getStatus());
        verify(oracleIntegrationServiceMock, usageRepository, usageAuditService);
    }

    @Test
    public void testProcessTaxCountryCodeNonUs() {
        Usage usage = buildUsage();
        expect(oracleIntegrationServiceMock.isUsCountryCode(1000001534L)).andReturn(false).once();
        replay(oracleIntegrationServiceMock, usageRepository, usageAuditService);
        rhTaxService.processTaxCountryCode(usage);
        assertNull(usage.getStatus());
        verify(oracleIntegrationServiceMock, usageRepository, usageAuditService);
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
