package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageBatchRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;

import com.google.common.collect.Sets;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclUsageBatchService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/01/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class AclUsageBatchServiceTest {

    private static final String ACL_USAGE_BATCH_NAME = "ACL Usage Batch 2021";
    private static final String USER_NAME = "user@copyright.com";

    private IAclUsageBatchService aclUsageBatchService;
    private IAclUsageBatchRepository aclUsageBatchRepository;
    private IAclUsageService aclUsageService;

    @Before
    public void setUp() {
        aclUsageBatchService = new AclUsageBatchService();
        aclUsageBatchRepository = createMock(IAclUsageBatchRepository.class);
        aclUsageService = createMock(IAclUsageService.class);
        Whitebox.setInternalState(aclUsageBatchService, aclUsageBatchRepository);
        Whitebox.setInternalState(aclUsageBatchService, aclUsageService);
    }

    @Test
    public void testIsUsageBatchExist() {
        expect(aclUsageBatchRepository.isAclUsageBatchExist(ACL_USAGE_BATCH_NAME)).andReturn(true).once();
        replay(aclUsageBatchRepository);
        assertTrue(aclUsageBatchService.isAclUsageBatchExist(ACL_USAGE_BATCH_NAME));
        verify(aclUsageBatchRepository);
    }

    @Test
    public void testInsert() {
        mockStatic(RupContextUtils.class);
        AclUsageBatch usageBatch = buildAclUsageBatch();
        Capture<String> usageBatchIdCapture = newCapture();
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        aclUsageBatchRepository.insert(usageBatch);
        expectLastCall().once();
        expect(aclUsageService.populateAclUsages(capture(usageBatchIdCapture), eq(usageBatch.getPeriods()),
            eq(USER_NAME))).andReturn(1).once();
        replay(RupContextUtils.class, aclUsageBatchRepository, aclUsageService);
        assertEquals(1, aclUsageBatchService.insert(usageBatch));
        assertEquals(usageBatch.getId(), usageBatchIdCapture.getValue());
        assertEquals(usageBatch.getCreateUser(), USER_NAME);
        assertEquals(usageBatch.getUpdateUser(), USER_NAME);
        verify(RupContextUtils.class, aclUsageBatchRepository, aclUsageService);
    }

    @Test
    public void testGetAll() {
        List<AclUsageBatch> usageBatches = Collections.singletonList(buildAclUsageBatch());
        expect(aclUsageBatchRepository.findAll()).andReturn(usageBatches).once();
        replay(aclUsageBatchRepository);
        assertSame(usageBatches, aclUsageBatchService.getAll());
        verify(aclUsageBatchRepository);
    }

    private AclUsageBatch buildAclUsageBatch() {
        AclUsageBatch usageBatch = new AclUsageBatch();
        usageBatch.setName("ACL Usage Batch 2021");
        usageBatch.setDistributionPeriod(202112);
        usageBatch.setPeriods(Sets.newHashSet(202106, 202112));
        usageBatch.setEditable(true);
        return usageBatch;
    }
}
