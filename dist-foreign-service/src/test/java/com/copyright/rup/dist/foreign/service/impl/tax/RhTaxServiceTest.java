package com.copyright.rup.dist.foreign.service.impl.tax;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleIntegrationService;

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
    private IOracleIntegrationService oracleIntegrationServiceMock;

    @Before
    public void setUp() {
        rhTaxService = new RhTaxService();
        oracleIntegrationServiceMock = createMock(IOracleIntegrationService.class);
        rhTaxService.setOracleIntegrationService(oracleIntegrationServiceMock);
    }

    @Test
    public void testIsUsCountryCodeUsage() {
        Usage usage = buildUsage();
        expect(oracleIntegrationServiceMock.isUsCountryCode(1000001534L)).andReturn(true).once();
        replay(oracleIntegrationServiceMock);
        assertTrue(rhTaxService.isUsCountryCodeUsage(usage));
        verify(oracleIntegrationServiceMock);
    }

    @Test
    public void testIsUsCountryCodeUsageWithFrCode() {
        Usage usage = buildUsage();
        expect(oracleIntegrationServiceMock.isUsCountryCode(1000001534L)).andReturn(false).once();
        replay(oracleIntegrationServiceMock);
        assertFalse(rhTaxService.isUsCountryCodeUsage(usage));
        verify(oracleIntegrationServiceMock);
    }

    @Test(expected = NullPointerException.class)
    public void testIsUsCountryCodeUsageWithNull() {
        Usage usage = new Usage();
        rhTaxService.isUsCountryCodeUsage(usage);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.getRightsholder().setAccountNumber(1000001534L);
        return usage;
    }
}
