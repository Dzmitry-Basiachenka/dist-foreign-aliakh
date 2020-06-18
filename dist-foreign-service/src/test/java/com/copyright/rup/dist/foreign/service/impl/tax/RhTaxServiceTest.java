package com.copyright.rup.dist.foreign.service.impl.tax;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxCountryChunkService;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxCountryService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

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

    private static final Set<Long> ACCOUNT_NUMBERS = Sets.newHashSet(7001413934L, 1000009522L);

    private RhTaxService rhTaxService;
    private IUsageService usageService;
    private IOracleRhTaxCountryService oracleRhTaxCountryService;
    private IOracleRhTaxCountryChunkService oracleRhTaxCountryChunkService;

    @Before
    public void setUp() {
        rhTaxService = new RhTaxService();
        usageService = createMock(IUsageService.class);
        oracleRhTaxCountryService = createMock(IOracleRhTaxCountryService.class);
        oracleRhTaxCountryChunkService = createMock(IOracleRhTaxCountryChunkService.class);
        Whitebox.setInternalState(rhTaxService, "usageService", usageService);
        Whitebox.setInternalState(rhTaxService, "oracleRhTaxCountryService", oracleRhTaxCountryService);
        Whitebox.setInternalState(rhTaxService, "oracleRhTaxCountryChunkService", oracleRhTaxCountryChunkService);
    }

    @Test
    public void testProcessTaxCountryCode() {
        Usage usage = buildUsage(1000001534L);
        expect(oracleRhTaxCountryService.isUsTaxCountry(1000001534L)).andReturn(true).once();
        usageService.updateProcessedUsage(usage);
        expectLastCall().once();
        replay(oracleRhTaxCountryService, usageService);
        rhTaxService.processTaxCountryCode(usage);
        assertEquals(UsageStatusEnum.US_TAX_COUNTRY, usage.getStatus());
        verify(oracleRhTaxCountryService, usageService);
    }

    @Test
    public void testProcessTaxCountryCodeNonUs() {
        Usage usage = buildUsage(1000001534L);
        expect(oracleRhTaxCountryService.isUsTaxCountry(1000001534L)).andReturn(false).once();
        replay(oracleRhTaxCountryService, usageService);
        rhTaxService.processTaxCountryCode(usage);
        assertNull(usage.getStatus());
        verify(oracleRhTaxCountryService, usageService);
    }

    @Test(expected = NullPointerException.class)
    public void testProcessTaxCountryCodeNullUsage() {
        Usage usage = new Usage();
        rhTaxService.processTaxCountryCode(usage);
    }

    @Test
    public void testProcessTaxCountryCodeByChunks() {
        Usage usTaxUsage = buildUsage(7001413934L);
        Usage notUsTaxUsage = buildUsage(1000009522L);
        List<Usage> usages = Arrays.asList(usTaxUsage, notUsTaxUsage);
        expect(oracleRhTaxCountryChunkService.isUsTaxCountry(ACCOUNT_NUMBERS))
            .andReturn(ImmutableMap.of(7001413934L, Boolean.TRUE, 1000009522L, Boolean.FALSE))
            .once();
        usageService.updateProcessedUsage(usTaxUsage);
        expectLastCall().once();
        replay(oracleRhTaxCountryChunkService, usageService);
        rhTaxService.processTaxCountryCode(usages);
        assertEquals(UsageStatusEnum.US_TAX_COUNTRY, usTaxUsage.getStatus());
        assertNotEquals(UsageStatusEnum.US_TAX_COUNTRY, notUsTaxUsage.getStatus());
        verify(oracleRhTaxCountryChunkService, usageService);
    }

    private Usage buildUsage(Long accountNumber) {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.getRightsholder().setAccountNumber(accountNumber);
        return usage;
    }
}
