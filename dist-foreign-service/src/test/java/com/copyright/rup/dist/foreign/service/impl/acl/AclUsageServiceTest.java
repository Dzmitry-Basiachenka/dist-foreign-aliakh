package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link AclUsageService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/01/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclUsageServiceTest {

    private IAclUsageService aclUsageService;
    private IAclUsageRepository aclUsageRepository;

    @Before
    public void setUp() {
        aclUsageService = new AclUsageService();
        aclUsageRepository = createMock(IAclUsageRepository.class);
        Whitebox.setInternalState(aclUsageService, aclUsageRepository);
    }

    @Test
    public void testPopulateAclUsages() {
        String usageBatchId = "3e85d243-63a6-4145-9df6-91f6b2cada53";
        Set<Integer> periods = Collections.singleton(202106);
        String userName = "user@copyright.com";
        List<String> usageIds = Collections.singletonList("8b705e49-85fe-4851-a051-08109d159c7d");
        expect(aclUsageRepository.populateAclUsages(usageBatchId, periods, userName)).andReturn(usageIds).once();
        replay(aclUsageRepository);
        assertEquals(usageIds.size(), aclUsageService.populateAclUsages(usageBatchId, periods, userName));
        verify(aclUsageRepository);
    }

    @Test
    public void testGetCount() {
        AclUsageFilter aclUsageFilter = buildAclUsageFilter();
        expect(aclUsageRepository.findCountByFilter(aclUsageFilter)).andReturn(10).once();
        replay(aclUsageRepository);
        assertEquals(10, aclUsageService.getCount(aclUsageFilter));
        verify(aclUsageRepository);
    }

    @Test
    public void testGetCountEmptyFilter() {
        assertEquals(0, aclUsageService.getCount(new AclUsageFilter()));
    }

    @Test
    public void testGetDtos() {
        List<AclUsageDto> aclUsageDtos = Collections.singletonList(new AclUsageDto());
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("detailId", Sort.Direction.ASC);
        AclUsageFilter filter = buildAclUsageFilter();
        expect(aclUsageRepository.findDtosByFilter(filter, pageable, sort)).andReturn(aclUsageDtos).once();
        replay(aclUsageRepository);
        assertSame(aclUsageDtos, aclUsageService.getDtos(filter, pageable, sort));
        verify(aclUsageRepository);
    }

    @Test
    public void testGetDtosEmptyFilter() {
        List<AclUsageDto> result = aclUsageService.getDtos(new AclUsageFilter(), null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    private AclUsageFilter buildAclUsageFilter() {
        AclUsageFilter aclUsageFilter = new AclUsageFilter();
        aclUsageFilter.setUsageBatchName("ACL Usage Batch 2021");
        return aclUsageFilter;
    }
}
