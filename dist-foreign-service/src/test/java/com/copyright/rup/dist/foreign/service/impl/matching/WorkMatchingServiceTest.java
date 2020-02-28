package com.copyright.rup.dist.foreign.service.impl.matching;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;

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

    private static final String STANDARD_NUMBER = "000043122-1";
    private static final String TITLE = "The theological roots of Pentecostalism";
    private IPiIntegrationService piIntegrationService;
    private WorkMatchingService workMatchingService;
    private IUsageRepository usageRepository;
    private IUsageAuditService auditService;
    private IUsageService usageService;

    @Before
    public void setUp() {
        workMatchingService = new WorkMatchingService();
        piIntegrationService = createMock(IPiIntegrationService.class);
        usageRepository = createMock(IUsageRepository.class);
        usageService = createMock(IUsageService.class);
        auditService = createMock(IUsageAuditService.class);
        Whitebox.setInternalState(workMatchingService, piIntegrationService);
        Whitebox.setInternalState(workMatchingService, usageRepository);
        Whitebox.setInternalState(workMatchingService, auditService);
        Whitebox.setInternalState(workMatchingService, usageService);
    }

    @Test
    public void testMatchByIdno() {
        Usage usage = buildUsage(STANDARD_NUMBER, TITLE);
        Work work = new Work(112930820L, TITLE, STANDARD_NUMBER, "valisbn10");
        work.setHostIdnoFlag(true);
        expect(piIntegrationService.findWorkByIdnoAndTitle(STANDARD_NUMBER, TITLE)).andReturn(work).once();
        usageService.updateProcessedUsage(usage);
        expectLastCall().once();
        auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
            "Wr Wrk Inst 112930820 was found by host IDNO 000043122-1");
        expectLastCall().once();
        replay(piIntegrationService, usageRepository, auditService, usageService);
        workMatchingService.matchByIdno(usage);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals("VALISBN10", usage.getStandardNumberType());
        assertEquals(STANDARD_NUMBER, usage.getStandardNumber());
        assertEquals(112930820L, usage.getWrWrkInst(), 0);
        verify(piIntegrationService, usageRepository, auditService, usageService);
    }

    @Test
    public void testMatchByIdnoForNts() {
        String batchId = RupPersistUtils.generateUuid();
        Usage usage = buildUsage(STANDARD_NUMBER, TITLE);
        usage.setBatchId(batchId);
        expect(piIntegrationService.findWorkByIdnoAndTitle(STANDARD_NUMBER, TITLE))
            .andReturn(new Work()).once();
        expect(usageRepository.getTotalAmountByStandardNumberAndBatchId(STANDARD_NUMBER, batchId))
            .andReturn(new BigDecimal("99.00")).once();
        usageService.updateProcessedUsage(usage);
        expectLastCall().once();
        auditService.logAction(usage.getId(), UsageActionTypeEnum.ELIGIBLE_FOR_NTS,
            "Detail was made eligible for NTS because sum of gross amounts, grouped by standard number, " +
                "is less than $100");
        expectLastCall().once();
        replay(piIntegrationService, usageRepository, auditService);
        workMatchingService.matchByIdno(usage);
        assertEquals(UsageStatusEnum.NTS_WITHDRAWN, usage.getStatus());
        assertEquals("NTS", usage.getProductFamily());
        verify(piIntegrationService, usageRepository, auditService);
    }

    @Test
    public void testMatchByTitle() {
        Usage usage = buildUsage(null, TITLE);
        expect(piIntegrationService.findWorkByTitle(TITLE))
            .andReturn(new Work(112930820L, TITLE, STANDARD_NUMBER, "valissn")).once();
        usageService.updateProcessedUsage(usage);
        expectLastCall().once();
        auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
            "Wr Wrk Inst 112930820 was found by title \"The theological roots of Pentecostalism\"");
        expectLastCall().once();
        replay(piIntegrationService, usageRepository, auditService);
        workMatchingService.matchByTitle(usage);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(112930820L, usage.getWrWrkInst(), 0);
        assertEquals("VALISSN", usage.getStandardNumberType());
        assertEquals(STANDARD_NUMBER, usage.getStandardNumber());
        verify(piIntegrationService, usageRepository, auditService);
    }

    @Test
    public void testMatchByWrWrkInst() {
        Usage usage = buildUsage(112930820L);
        expect(piIntegrationService.findWorkByWrWrkInst(112930820L))
            .andReturn(new Work(112930820L, TITLE, STANDARD_NUMBER, "valissn")).once();
        usageService.updateProcessedUsage(usage);
        expectLastCall().once();
        auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 112930820 was found in PI");
        expectLastCall().once();
        replay(piIntegrationService, usageRepository, auditService);
        workMatchingService.matchByWrWrkInst(usage);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(112930820L, usage.getWrWrkInst(), 0);
        assertEquals(TITLE, usage.getSystemTitle());
        assertEquals(STANDARD_NUMBER, usage.getStandardNumber());
        assertEquals("VALISSN", usage.getStandardNumberType());
        verify(piIntegrationService, usageRepository, auditService);
    }

    @Test
    public void testMatchByWrWrkInstWithWorkNotFound() {
        Usage usage = buildUsage(112930820L);
        expect(piIntegrationService.findWorkByWrWrkInst(112930820L)).andReturn(new Work()).once();
        usageService.updateProcessedUsage(usage);
        expectLastCall().once();
        auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_NOT_FOUND,
            "Wr Wrk Inst 112930820 was not found in PI");
        expectLastCall().once();
        replay(piIntegrationService, usageRepository, auditService);
        workMatchingService.matchByWrWrkInst(usage);
        assertEquals(UsageStatusEnum.WORK_NOT_FOUND, usage.getStatus());
        assertEquals(112930820L, usage.getWrWrkInst(), 0);
        assertNull(usage.getSystemTitle());
        assertNull(usage.getStandardNumber());
        assertNull(usage.getStandardNumberType());
        verify(piIntegrationService, usageRepository, auditService);
    }

    private Usage buildUsage(Long wrWrkInst) {
        Usage usage = buildUsage(null, null);
        usage.setWrWrkInst(wrWrkInst);
        return usage;
    }

    private Usage buildUsage(String standardNumber, String title) {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setStandardNumber(standardNumber);
        usage.setWorkTitle(title);
        return usage;
    }
}
