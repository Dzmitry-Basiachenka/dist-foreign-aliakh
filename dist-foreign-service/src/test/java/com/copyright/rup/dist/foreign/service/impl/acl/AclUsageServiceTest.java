package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

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
}
