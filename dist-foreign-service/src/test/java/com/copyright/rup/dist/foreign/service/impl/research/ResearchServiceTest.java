package com.copyright.rup.dist.foreign.service.impl.research;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.powermock.api.easymock.PowerMock.expectLastCall;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAaclReportRepository;
import com.copyright.rup.dist.foreign.repository.api.IFasReportRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Verifies {@link ResearchService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/23/2018
 *
 * @author Nikita Levyankov
 */
public class ResearchServiceTest {

    private IUsageRepository usageRepository;
    private IAaclReportRepository aaclReportRepository;
    private IFasReportRepository fasReportRepository;
    private IUsageAuditService usageAuditService;
    private ResearchService researchService;

    @Before
    public void setUp() {
        usageRepository = createMock(IUsageRepository.class);
        aaclReportRepository = createMock(IAaclReportRepository.class);
        fasReportRepository = createMock(IFasReportRepository.class);
        usageAuditService = createMock(IUsageAuditService.class);
        researchService = new ResearchService();
        Whitebox.setInternalState(researchService, usageRepository);
        Whitebox.setInternalState(researchService, aaclReportRepository);
        Whitebox.setInternalState(researchService, fasReportRepository);
        Whitebox.setInternalState(researchService, usageAuditService);
    }

    @Test
    public void testSendForResearch() {
        UsageFilter filter = new UsageFilter();
        OutputStream outputStream = new ByteArrayOutputStream();
        String usageId1 = RupPersistUtils.generateUuid();
        String usageId2 = RupPersistUtils.generateUuid();
        Set<String> usageIds = new HashSet<>();
        Collections.addAll(usageIds, usageId1, usageId2);
        expect(fasReportRepository.writeUsagesForResearchAndFindIds(filter, outputStream))
            .andReturn(usageIds)
            .once();
        usageRepository.updateStatus(usageIds, UsageStatusEnum.WORK_RESEARCH);
        expectLastCall().once();
        usageAuditService.logAction(usageId1, UsageActionTypeEnum.WORK_RESEARCH, "Usage detail was sent for research");
        expectLastCall().once();
        usageAuditService.logAction(usageId2, UsageActionTypeEnum.WORK_RESEARCH, "Usage detail was sent for research");
        expectLastCall().once();
        replay(usageRepository, fasReportRepository, usageAuditService);
        researchService.sendForResearch(filter, outputStream);
        verify(usageRepository, fasReportRepository, usageAuditService);
    }

    @Test
    public void testSendForResearchNoUsages() {
        UsageFilter filter = new UsageFilter();
        OutputStream outputStream = new ByteArrayOutputStream();
        expect(fasReportRepository.writeUsagesForResearchAndFindIds(filter, outputStream))
            .andReturn(Collections.emptySet())
            .once();
        replay(fasReportRepository, usageAuditService);
        researchService.sendForResearch(filter, outputStream);
        verify(fasReportRepository, usageAuditService);
    }

    @Test
    public void testSendForClassification() {
        UsageFilter filter = new UsageFilter();
        OutputStream outputStream = new ByteArrayOutputStream();
        String usageId1 = RupPersistUtils.generateUuid();
        String usageId2 = RupPersistUtils.generateUuid();
        Set<String> usageIds = new HashSet<>();
        Collections.addAll(usageIds, usageId1, usageId2);
        expect(aaclReportRepository.writeUsagesForClassificationAndFindIds(filter, outputStream))
            .andReturn(usageIds)
            .once();
        usageRepository.updateStatus(usageIds, UsageStatusEnum.WORK_RESEARCH);
        expectLastCall().once();
        usageAuditService.logAction(usageId1, UsageActionTypeEnum.WORK_RESEARCH,
            "Usage detail was sent for classification");
        expectLastCall().once();
        usageAuditService.logAction(usageId2, UsageActionTypeEnum.WORK_RESEARCH,
            "Usage detail was sent for classification");
        expectLastCall().once();
        replay(usageRepository, aaclReportRepository, usageAuditService);
        researchService.sendForClassification(filter, outputStream);
        verify(usageRepository, aaclReportRepository, usageAuditService);
    }

    @Test
    public void testSendForClassificationNoUsages() {
        UsageFilter filter = new UsageFilter();
        OutputStream outputStream = new ByteArrayOutputStream();
        expect(aaclReportRepository.writeUsagesForClassificationAndFindIds(filter, outputStream))
            .andReturn(Collections.emptySet())
            .once();
        replay(aaclReportRepository, usageAuditService);
        researchService.sendForClassification(filter, outputStream);
        verify(aaclReportRepository, usageAuditService);
    }
}
