package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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

    @Before
    public void setUp() {
        workMatchingService = new WorkMatchingService();
        piIntegrationService = createMock(IPiIntegrationService.class);
        Whitebox.setInternalState(workMatchingService, "piIntegrationService", piIntegrationService);
    }

    @Test
    public void testMatchByTitle() {
        String title1 = "The theological roots of Pentecostalism";
        String title2 = "Theological roots of Pentecostalism";
        List<Usage> usages = Lists.newArrayList(
            buildUsage(null, title1),
            buildUsage(null, title2));
        Map<String, Long> resultMap = ImmutableMap.of(title1, 112930820L, title2, 155941698L);
        expect(piIntegrationService.findWrWrkInstsByTitles(Sets.newHashSet(title1, title2)))
            .andReturn(resultMap).once();
        replay(piIntegrationService);
        List<Usage> result = workMatchingService.matchByTitle(usages);
        result.forEach(usage -> {
            assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
            assertEquals(resultMap.get(usage.getWorkTitle()), usage.getWrWrkInst());
        });
        verify(piIntegrationService);
    }

    @Test
    public void testMatchByIdno() {
        String standardNumber1 = "978-0-918062-08-6";
        String standardNumber2 = "0-918062-08-X";
        List<Usage> usages = Lists.newArrayList(
            buildUsage(standardNumber1, null),
            buildUsage(standardNumber2, null));
        Map<String, Long> resultMap = ImmutableMap.of(standardNumber1, 112930820L, standardNumber2, 155941698L);
        expect(piIntegrationService.findWrWrkInstsByIdnos(Sets.newHashSet(standardNumber1, standardNumber2)))
            .andReturn(resultMap).once();
        replay(piIntegrationService);
        List<Usage> result = workMatchingService.matchByIdno(usages);
        result.forEach(usage -> {
            assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
            assertEquals(resultMap.get(usage.getStandardNumber()), usage.getWrWrkInst());
        });
        verify(piIntegrationService);
    }

    private Usage buildUsage(String standardNumber, String title) {
        Usage usage = new Usage();
        usage.setStandardNumber(standardNumber);
        usage.setWorkTitle(title);
        return usage;
    }
}
