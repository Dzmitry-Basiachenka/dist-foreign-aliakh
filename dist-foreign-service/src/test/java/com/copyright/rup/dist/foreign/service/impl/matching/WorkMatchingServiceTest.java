package com.copyright.rup.dist.foreign.service.impl.matching;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;

/**
 * Verifies {@link WorkMatchingService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 2/22/18
 *
 * @author Aliaksandr Radkevich
 */
public class WorkMatchingServiceTest {

    private IPiIntegrationService piIntegrationService;
    private WorkMatchingService workMatchingService;
    private IUsageRepository usageRepository;
    private IUsageAuditService auditService;

    @Before
    public void setUp() {
        workMatchingService = new WorkMatchingService();
        piIntegrationService = createMock(IPiIntegrationService.class);
        usageRepository = createMock(IUsageRepository.class);
        auditService = createMock(IUsageAuditService.class);
        Whitebox.setInternalState(workMatchingService, piIntegrationService);
        Whitebox.setInternalState(workMatchingService, usageRepository);
        Whitebox.setInternalState(workMatchingService, auditService);
    }

    @Test
    public void testMatchByIdno() {
        String standardNumber = "000043122-1";
        String title = "The theological roots of Pentecostalism";
        Usage usage = buildUsage(standardNumber, title);
        expect(piIntegrationService.findWorkByIdnoAndTitle(standardNumber, title))
            .andReturn(new Work(112930820L, title)).once();
        usageRepository.update(Collections.singletonList(usage));
        expectLastCall().once();
        auditService.logAction(anyString(), eq(UsageActionTypeEnum.WORK_FOUND), anyString());
        expectLastCall().once();
        replay(piIntegrationService, usageRepository, auditService);
        workMatchingService.matchByIdno(usage);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(112930820L, usage.getWrWrkInst(), 0);
        verify(piIntegrationService, usageRepository, auditService);
    }

    @Test
    public void testMatchByTitle() {
        String title = "The theological roots of Pentecostalism";
        Usage usage = buildUsage(null, title);
        expect(piIntegrationService.findWrWrkInstByTitle(title)).andReturn(112930820L).once();
        usageRepository.update(Collections.singletonList(usage));
        expectLastCall().once();
        auditService.logAction(anyString(), eq(UsageActionTypeEnum.WORK_FOUND), anyString());
        expectLastCall().once();
        replay(piIntegrationService, usageRepository, auditService);
        workMatchingService.matchByTitle(usage);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(112930820L, usage.getWrWrkInst(), 0);
        verify(piIntegrationService, usageRepository, auditService);
    }

    private Usage buildUsage(String standardNumber, String title) {
        Usage usage = new Usage();
        usage.setStandardNumber(standardNumber);
        usage.setWorkTitle(title);
        return usage;
    }
}
