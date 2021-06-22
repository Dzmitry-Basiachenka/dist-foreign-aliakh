package com.copyright.rup.dist.foreign.service.impl.matching;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;

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
    private static final String VALISSN = "VALISSN";
    private static final String VALISBN10 = "VALISBN10";
    private static final String WORK_FOUND_REASON = "Wr Wrk Inst 112930820 was found in PI";
    private IPiIntegrationService piIntegrationService;
    private WorkMatchingService workMatchingService;
    private IUsageRepository usageRepository;
    private IUsageAuditService auditService;
    private IUdmUsageAuditService udmAuditService;
    private IUsageService usageService;
    private IUdmUsageService udmUsageService;

    @Before
    public void setUp() {
        workMatchingService = new WorkMatchingService();
        piIntegrationService = createMock(IPiIntegrationService.class);
        usageRepository = createMock(IUsageRepository.class);
        usageService = createMock(IUsageService.class);
        udmUsageService = createMock(IUdmUsageService.class);
        auditService = createMock(IUsageAuditService.class);
        udmAuditService = createMock(IUdmUsageAuditService.class);
        Whitebox.setInternalState(workMatchingService, piIntegrationService);
        Whitebox.setInternalState(workMatchingService, usageRepository);
        Whitebox.setInternalState(workMatchingService, auditService);
        Whitebox.setInternalState(workMatchingService, udmAuditService);
        Whitebox.setInternalState(workMatchingService, usageService);
        Whitebox.setInternalState(workMatchingService, udmUsageService);
    }

    @Test
    public void testMatchByStandardNumber() {
        Usage usage = buildUsage(STANDARD_NUMBER, TITLE);
        Work work = new Work(112930820L, TITLE, STANDARD_NUMBER, VALISBN10);
        expect(piIntegrationService.findWorkByStandardNumber(STANDARD_NUMBER)).andReturn(work).once();
        usageService.updateProcessedUsage(usage);
        expectLastCall().once();
        auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
            "Wr Wrk Inst 112930820 was found by standard number 000043122-1");
        expectLastCall().once();
        replay(piIntegrationService, usageRepository, auditService, usageService);
        workMatchingService.matchByStandardNumber(usage);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(VALISBN10, usage.getStandardNumberType());
        assertEquals(STANDARD_NUMBER, usage.getStandardNumber());
        assertEquals(112930820L, usage.getWrWrkInst(), 0);
        verify(piIntegrationService, usageRepository, auditService, usageService);
    }

    @Test
    public void testMatchStandardNumberByHostIdno() {
        Usage usage = buildUsage(STANDARD_NUMBER, TITLE);
        Work work = new Work(112930820L, TITLE, STANDARD_NUMBER, VALISBN10);
        work.setHostIdnoFlag(true);
        expect(piIntegrationService.findWorkByStandardNumber(STANDARD_NUMBER)).andReturn(work).once();
        usageService.updateProcessedUsage(usage);
        expectLastCall().once();
        auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
            "Wr Wrk Inst 112930820 was found by host IDNO 000043122-1");
        expectLastCall().once();
        replay(piIntegrationService, usageRepository, auditService, usageService);
        workMatchingService.matchByStandardNumber(usage);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(VALISBN10, usage.getStandardNumberType());
        assertEquals(STANDARD_NUMBER, usage.getStandardNumber());
        assertEquals(112930820L, usage.getWrWrkInst(), 0);
        verify(piIntegrationService, usageRepository, auditService, usageService);
    }

    @Test
    public void testMatchByStandardNumberForNts() {
        String batchId = RupPersistUtils.generateUuid();
        Usage usage = buildUsage(STANDARD_NUMBER, TITLE);
        usage.setBatchId(batchId);
        usage.setProductFamily(FdaConstants.FAS_PRODUCT_FAMILY);
        expect(piIntegrationService.findWorkByStandardNumber(STANDARD_NUMBER))
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
        workMatchingService.matchByStandardNumber(usage);
        assertEquals(UsageStatusEnum.NTS_WITHDRAWN, usage.getStatus());
        assertEquals("FAS", usage.getProductFamily());
        verify(piIntegrationService, usageRepository, auditService);
    }

    @Test
    public void testMatchByStandardNumberForUdm() {
        UdmUsage usage = buildUdmUsage(STANDARD_NUMBER, TITLE);
        Work work = new Work(112930820L, TITLE, STANDARD_NUMBER, VALISBN10);
        expect(piIntegrationService.findWorkByStandardNumber(STANDARD_NUMBER)).andReturn(work).once();
        udmUsageService.updateProcessedUsage(usage);
        expectLastCall().once();
        udmAuditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
            "Wr Wrk Inst 112930820 was found by standard number 000043122-1");
        expectLastCall().once();
        replay(piIntegrationService, udmUsageService, udmAuditService);
        workMatchingService.matchByStandardNumber(usage);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(STANDARD_NUMBER, usage.getStandardNumber());
        assertEquals(112930820L, usage.getWrWrkInst(), 0);
        verify(piIntegrationService, udmUsageService, udmAuditService);
    }

    @Test
    public void testMatchByTitle() {
        Usage usage = buildUsage(null, TITLE);
        expect(piIntegrationService.findWorkByTitle(TITLE))
            .andReturn(new Work(112930820L, TITLE, STANDARD_NUMBER, VALISSN)).once();
        usageService.updateProcessedUsage(usage);
        expectLastCall().once();
        auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
            "Wr Wrk Inst 112930820 was found by title \"The theological roots of Pentecostalism\"");
        expectLastCall().once();
        replay(piIntegrationService, usageRepository, auditService);
        workMatchingService.matchByTitle(usage);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(112930820L, usage.getWrWrkInst(), 0);
        assertEquals(VALISSN, usage.getStandardNumberType());
        assertEquals(STANDARD_NUMBER, usage.getStandardNumber());
        verify(piIntegrationService, usageRepository, auditService);
    }

    @Test
    public void testMatchTitleByHostIdno() {
        Usage usage = buildUsage(null, TITLE);
        Work work = new Work(112930820L, TITLE, "00485772", VALISSN);
        work.setHostIdnoFlag(true);
        expect(piIntegrationService.findWorkByTitle(TITLE)).andReturn(work).once();
        usageService.updateProcessedUsage(usage);
        expectLastCall().once();
        auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
            "Wr Wrk Inst 112930820 was found by host IDNO 00485772");
        expectLastCall().once();
        replay(piIntegrationService, usageRepository, auditService);
        workMatchingService.matchByTitle(usage);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(112930820L, usage.getWrWrkInst(), 0);
        assertEquals(VALISSN, usage.getStandardNumberType());
        assertEquals("00485772", usage.getStandardNumber());
        verify(piIntegrationService, usageRepository, auditService);
    }

    @Test
    public void testMatchByTitleForUdm() {
        UdmUsage usage = buildUdmUsage(STANDARD_NUMBER, TITLE);
        Work work = new Work(112930820L, TITLE, STANDARD_NUMBER, VALISBN10);
        expect(piIntegrationService.findWorkByTitle(TITLE)).andReturn(work).once();
        udmUsageService.updateProcessedUsage(usage);
        expectLastCall().once();
        udmAuditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND,
            "Wr Wrk Inst 112930820 was found by title \"The theological roots of Pentecostalism\"");
        expectLastCall().once();
        replay(piIntegrationService, udmUsageService, udmAuditService);
        workMatchingService.matchByTitle(usage);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(STANDARD_NUMBER, usage.getStandardNumber());
        assertEquals(112930820L, usage.getWrWrkInst(), 0);
        verify(piIntegrationService, udmUsageService, udmAuditService);
    }

    @Test
    public void testMatchByWrWrkInst() {
        Usage usage = buildUsage(112930820L);
        expect(piIntegrationService.findWorkByWrWrkInst(112930820L))
            .andReturn(new Work(112930820L, TITLE, STANDARD_NUMBER, VALISSN)).once();
        usageService.updateProcessedUsage(usage);
        expectLastCall().once();
        auditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND, WORK_FOUND_REASON);
        expectLastCall().once();
        replay(piIntegrationService, usageRepository, auditService);
        workMatchingService.matchByWrWrkInst(usage);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(112930820L, usage.getWrWrkInst(), 0);
        assertEquals(TITLE, usage.getSystemTitle());
        assertEquals(STANDARD_NUMBER, usage.getStandardNumber());
        assertEquals(VALISSN, usage.getStandardNumberType());
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

    @Test
    public void testMatchByWrWrkInstForUdm() {
        UdmUsage usage = buildUdmUsage(112930820L);
        Work work = new Work(112930820L, TITLE, STANDARD_NUMBER, VALISBN10);
        expect(piIntegrationService.findWorkByWrWrkInst(112930820L)).andReturn(work).once();
        udmUsageService.updateProcessedUsage(usage);
        expectLastCall().once();
        udmAuditService.logAction(usage.getId(), UsageActionTypeEnum.WORK_FOUND, WORK_FOUND_REASON);
        expectLastCall().once();
        replay(piIntegrationService, udmUsageService, udmAuditService);
        workMatchingService.matchByWrWrkInst(usage);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(STANDARD_NUMBER, usage.getStandardNumber());
        assertEquals(112930820L, usage.getWrWrkInst(), 0);
        verify(piIntegrationService, udmUsageService, udmAuditService);
    }

    private Usage buildUsage(Long wrWrkInst) {
        Usage usage = buildUsage(null, null);
        usage.setWrWrkInst(wrWrkInst);
        return usage;
    }

    private UdmUsage buildUdmUsage(Long wrWrkInst) {
        UdmUsage usage = buildUdmUsage(null, null);
        usage.setWrWrkInst(wrWrkInst);
        return usage;
    }

    private UdmUsage buildUdmUsage(String standardNumber, String title) {
        UdmUsage usage = new UdmUsage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setReportedStandardNumber(standardNumber);
        usage.setReportedTitle(title);
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
