package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageAuditService;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link UdmBaselineService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/03/21
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class UdmBaselineServiceTest {

    private final UdmBaselineService udmBaselineService = new UdmBaselineService();
    private IUdmBaselineRepository baselineRepository;
    private IUdmUsageAuditService udmUsageAuditService;
    private int udmRecordsThreshold;

    @Before
    public void setUp() {
        udmRecordsThreshold = 1000;
        baselineRepository = createMock(IUdmBaselineRepository.class);
        udmUsageAuditService = createMock(IUdmUsageAuditService.class);
        Whitebox.setInternalState(udmBaselineService, baselineRepository);
        Whitebox.setInternalState(udmBaselineService, udmUsageAuditService);
        Whitebox.setInternalState(udmBaselineService, udmRecordsThreshold);
    }

    @Test
    public void testGetBaselineUsageDtos() {
        UdmBaselineFilter filter = new UdmBaselineFilter();
        filter.setPeriods(Collections.singleton(202012));
        List<UdmBaselineDto> udmBaselineDtos = Collections.singletonList(new UdmBaselineDto());
        expect(baselineRepository.findDtosByFilter(filter, null, null)).andReturn(udmBaselineDtos).once();
        replay(baselineRepository);
        assertEquals(udmBaselineDtos, udmBaselineService.getBaselineUsageDtos(filter, null, null));
        verify(baselineRepository);
    }

    @Test
    public void testGetBaselineUsageDtosEmptyFilter() {
        replay(baselineRepository);
        assertEquals(Collections.EMPTY_LIST,
            udmBaselineService.getBaselineUsageDtos(new UdmBaselineFilter(), null, null));
        verify(baselineRepository);
    }

    @Test
    public void testGetBaselineUsagesCount() {
        UdmBaselineFilter filter = new UdmBaselineFilter();
        filter.setPeriods(Collections.singleton(202012));
        expect(baselineRepository.findCountByFilter(filter)).andReturn(10).once();
        replay(baselineRepository);
        assertEquals(10, udmBaselineService.getBaselineUsagesCount(filter));
        verify(baselineRepository);
    }

    @Test
    public void testGetBaselineUsagesCountEmptyFilter() {
        replay(baselineRepository);
        assertEquals(0, udmBaselineService.getBaselineUsagesCount(new UdmBaselineFilter()));
        verify(baselineRepository);
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = Arrays.asList(202012, 201906);
        expect(baselineRepository.findPeriods()).andReturn(periods).once();
        replay(baselineRepository);
        assertEquals(periods, udmBaselineService.getPeriods());
        verify(baselineRepository);
    }

    @Test
    public void testGetUdmRecordThreshold() {
        assertEquals(1000, udmRecordsThreshold);
    }

    @Test
    public void testGetWrWrkInstToSystemTitles() {
        Set<Integer> periods = Sets.newHashSet(202012, 201906);
        ImmutableMap<Long, String> workToTitleMap = ImmutableMap.of(159526526L, "Speculum");
        expect(baselineRepository.findWrWrkInstToSystemTitles(periods)).andReturn(workToTitleMap).once();
        replay(baselineRepository);
        assertSame(workToTitleMap, udmBaselineService.getWrWrkInstToSystemTitles(periods));
        verify(baselineRepository);
    }

    @Test
    public void testDeleteFromBaseline() {
        String usageId = "f6e201bf-7b34-470f-937c-7e3bdeac0efe";
        baselineRepository.removeUdmUsageFromBaselineById(usageId);
        expectLastCall().once();
        udmUsageAuditService.logAction(usageId, UsageActionTypeEnum.REMOVE_FROM_BASELINE, "Reason to delete");
        expectLastCall().once();
        replay(baselineRepository, udmUsageAuditService);
        udmBaselineService.deleteFromBaseline(ImmutableSet.of(usageId), "Reason to delete", "user@copyright.com");
        verify(baselineRepository, udmUsageAuditService);
    }
}
