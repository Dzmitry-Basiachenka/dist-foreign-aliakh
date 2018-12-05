package com.copyright.rup.dist.foreign.service.impl.tax;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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

    private IUsageRepository usageRepository;
    private IUsageAuditService usageAuditService;
    private RhTaxService rhTaxService;
    private IOracleIntegrationService oracleIntegrationService;

    @Before
    public void setUp() {
        rhTaxService = new RhTaxService();
        oracleIntegrationService = createMock(IOracleIntegrationService.class);
        usageRepository = createMock(IUsageRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        Whitebox.setInternalState(rhTaxService, "usageRepository", usageRepository);
        Whitebox.setInternalState(rhTaxService, "usageAuditService", usageAuditService);
        Whitebox.setInternalState(rhTaxService, "oracleIntegrationService", oracleIntegrationService);
    }

    @Test
    public void testApplyRhTaxCountryFr() {
        Usage usage = buildUsage(RupPersistUtils.generateUuid());
        expect(oracleIntegrationService.isUsCountryCode(1000001534L)).andReturn(false).once();
        usageRepository.deleteById(usage.getId());
        expectLastCall().once();
        usageAuditService.deleteActions(usage.getId());
        expectLastCall().once();
        replay(usageRepository, usageAuditService, oracleIntegrationService);
        rhTaxService.applyRhTaxCountry(usage);
        verify(usageRepository, usageAuditService, oracleIntegrationService);
    }

    @Test
    public void testApplyRhTaxCountryUs() {
        Usage usage = buildUsage(RupPersistUtils.generateUuid());
        expect(oracleIntegrationService.isUsCountryCode(1000001534L)).andReturn(true).once();
        usageRepository.updateStatus(Collections.singleton(usage.getId()), UsageStatusEnum.ELIGIBLE);
        expectLastCall().once();
        replay(usageRepository, usageAuditService, oracleIntegrationService);
        rhTaxService.applyRhTaxCountry(usage);
        verify(usageRepository, usageAuditService, oracleIntegrationService);
    }

    private Usage buildUsage(String usageId) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.getRightsholder().setAccountNumber(1000001534L);
        return usage;
    }
}
