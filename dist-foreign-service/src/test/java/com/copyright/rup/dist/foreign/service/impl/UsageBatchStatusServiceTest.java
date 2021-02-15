package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchStatusRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchStatusService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link UsageBatchStatusService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/12/2021
 *
 * @author Ihar Suvorau
 */
public class UsageBatchStatusServiceTest {

    private static final long NUMBER_OF_DAYS = 7;
    private final List<UsageBatchStatus> batchStatuses = new ArrayList<>();
    private IUsageBatchStatusService usageBatchStatusService;
    private IUsageBatchStatusRepository usageBatchStatusRepository;

    @Before
    public void setUp() {
        usageBatchStatusService = new UsageBatchStatusService();
        usageBatchStatusRepository = createMock(IUsageBatchStatusRepository.class);
        batchStatuses.add(new UsageBatchStatus());
        Whitebox.setInternalState(usageBatchStatusService, usageBatchStatusRepository);
        Whitebox.setInternalState(usageBatchStatusService, "numberOfDays", NUMBER_OF_DAYS);
    }

    @Test
    public void testGetUsageBatchStatusesFas() {
        Set<String> batchIds = Collections.singleton("364401b8-18cd-4b1d-baa1-998aaf39a386");
        expect(usageBatchStatusRepository.findUsageBatchIdsByProductFamilyAndStartDateFrom("FAS",
            LocalDate.now().minusDays(NUMBER_OF_DAYS))).andReturn(batchIds).once();
        expect(usageBatchStatusRepository.findUsageBatchStatusesFas(batchIds)).andReturn(batchStatuses).once();
        replay(usageBatchStatusRepository);
        assertSame(batchStatuses, usageBatchStatusService.getUsageBatchStatusesFas());
        verify(usageBatchStatusRepository);
    }

    @Test
    public void testGetUsageBatchStatusesFas2() {
        expect(usageBatchStatusRepository.findUsageBatchStatusesFas2()).andReturn(batchStatuses).once();
        replay(usageBatchStatusRepository);
        assertSame(batchStatuses, usageBatchStatusService.getUsageBatchStatusesFas2());
        verify(usageBatchStatusRepository);
    }

    @Test
    public void testGetUsageBatchStatusesNts() {
        expect(usageBatchStatusRepository.findUsageBatchStatusesNts()).andReturn(batchStatuses).once();
        replay(usageBatchStatusRepository);
        assertSame(batchStatuses, usageBatchStatusService.getUsageBatchStatusesNts());
        verify(usageBatchStatusRepository);
    }

    @Test
    public void testGetUsageBatchStatusesAacl() {
        expect(usageBatchStatusRepository.findUsageBatchStatusesAacl()).andReturn(batchStatuses).once();
        replay(usageBatchStatusRepository);
        assertSame(batchStatuses, usageBatchStatusService.getUsageBatchStatusesAacl());
        verify(usageBatchStatusRepository);
    }

    @Test
    public void testGetUsageBatchStatusesSal() {
        expect(usageBatchStatusRepository.findUsageBatchStatusesSal()).andReturn(batchStatuses).once();
        replay(usageBatchStatusRepository);
        assertSame(batchStatuses, usageBatchStatusService.getUsageBatchStatusesSal());
        verify(usageBatchStatusRepository);
    }
}
