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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    public void testMatchByTitle() {
        String title1 = "The theological roots of Pentecostalism";
        String title2 = "Theological roots of Pentecostalism";
        Usage usage1 = buildUsage(null, title1);
        Usage usage2 = buildUsage(null, title2);
        List<Usage> usages = Lists.newArrayList(usage1, usage2);
        Map<String, Long> resultMap = ImmutableMap.of(title1, 112930820L, title2, 155941698L);
        expect(piIntegrationService.findWrWrkInstsByTitles(Sets.newHashSet(title1, title2)))
            .andReturn(resultMap).once();
        usageRepository.updateStatusAndWrWrkInstByTitle(usages);
        expectLastCall().once();
        expect(usageRepository.findByTitleAndStatus(title1, UsageStatusEnum.WORK_FOUND))
            .andReturn(Collections.singletonList(usage1)).once();
        expect(usageRepository.findByTitleAndStatus(title2, UsageStatusEnum.WORK_FOUND))
            .andReturn(Collections.singletonList(usage2)).once();
        auditService.logAction(anyString(), eq(UsageActionTypeEnum.WORK_FOUND), anyString());
        expectLastCall().times(2);
        replay(piIntegrationService, usageRepository, auditService);
        List<Usage> result = workMatchingService.matchByTitle(usages);
        result.forEach(usage -> {
            assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
            assertEquals(resultMap.get(usage.getWorkTitle()), usage.getWrWrkInst());
        });
        verify(piIntegrationService, usageRepository, auditService);
    }

    @Test
    public void testMatchByIdno() {
        String standardNumber1 = "978-0-918062-08-6";
        String standardNumber2 = "0-918062-08-X";
        Usage usage1 = buildUsage(standardNumber1, StringUtils.EMPTY);
        Usage usage2 = buildUsage(standardNumber2, StringUtils.EMPTY);
        List<Usage> usages = Lists.newArrayList(usage1, usage2);
        String mainTitle1 = "Main title 1";
        String mainTitle2 = "Main title 2";
        Map<String, Work> idnoToWorkMap = ImmutableMap.of(standardNumber1, new Work(112930820L, mainTitle1),
            standardNumber2, new Work(155941698L, mainTitle2));
        expect(piIntegrationService.findWorksByIdnos(
            ImmutableMap.of(standardNumber1, StringUtils.EMPTY, standardNumber2, StringUtils.EMPTY)))
            .andReturn(idnoToWorkMap).once();
        usageRepository.updateStatusAndWrWrkInstByStandardNumber(usages);
        expectLastCall().once();
        expect(usageRepository.findByStandardNumberAndStatus(standardNumber1, UsageStatusEnum.WORK_FOUND))
            .andReturn(Collections.singletonList(usage1)).once();
        expect(usageRepository.findByStandardNumberAndStatus(standardNumber2, UsageStatusEnum.WORK_FOUND))
            .andReturn(Collections.singletonList(usage2)).once();
        auditService.logAction(anyString(), eq(UsageActionTypeEnum.WORK_FOUND), anyString());
        expectLastCall().times(2);
        replay(piIntegrationService, usageRepository, auditService);
        List<Usage> result = workMatchingService.matchByIdno(usages);
        assertEquals(2, result.size());
        assertEquals(UsageStatusEnum.WORK_FOUND, usages.get(0).getStatus());
        Work work1 = idnoToWorkMap.get(usages.get(0).getStandardNumber());
        assertEquals(work1.getWrWrkInst(), usages.get(0).getWrWrkInst());
        assertEquals(work1.getMainTitle(), mainTitle1);
        assertEquals(UsageStatusEnum.WORK_FOUND, usages.get(1).getStatus());
        Work work2 = idnoToWorkMap.get(usages.get(1).getStandardNumber());
        assertEquals(work2.getWrWrkInst(), usages.get(1).getWrWrkInst());
        assertEquals(work2.getMainTitle(), mainTitle2);
        verify(piIntegrationService, usageRepository, auditService);
    }

    private Usage buildUsage(String standardNumber, String title) {
        Usage usage = new Usage();
        usage.setStandardNumber(standardNumber);
        usage.setWorkTitle(title);
        return usage;
    }
}
