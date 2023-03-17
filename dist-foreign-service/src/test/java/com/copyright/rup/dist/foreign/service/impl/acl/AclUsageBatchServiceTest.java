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

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiDeletedWorkIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageBatchRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.Set;

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
@PrepareForTest({RupContextUtils.class, RupPersistUtils.class})
public class AclUsageBatchServiceTest {

    private static final String ACL_USAGE_BATCH_NAME = "ACL Usage Batch 2021";
    private static final String USER_NAME = "user@copyright.com";
    private static final String ACL_USAGE_BATCH_ID = "cbfc9884-ad28-4760-8b52-794a7e9884a8";

    private IAclUsageBatchService aclUsageBatchService;
    private IAclUsageBatchRepository aclUsageBatchRepository;
    private IAclUsageService aclUsageService;
    private IPiDeletedWorkIntegrationService piIntegrationService;
    private IUdmUsageService udmUsageService;

    @Before
    public void setUp() {
        aclUsageBatchService = new AclUsageBatchService();
        udmUsageService = createMock(IUdmUsageService.class);
        piIntegrationService = createMock(IPiDeletedWorkIntegrationService.class);
        aclUsageBatchRepository = createMock(IAclUsageBatchRepository.class);
        aclUsageService = createMock(IAclUsageService.class);
        Whitebox.setInternalState(aclUsageBatchService, aclUsageBatchRepository);
        Whitebox.setInternalState(aclUsageBatchService, aclUsageService);
        Whitebox.setInternalState(aclUsageBatchService, udmUsageService);
        Whitebox.setInternalState(aclUsageBatchService, piIntegrationService);
    }

    @Test
    public void testIsUsageBatchExist() {
        expect(aclUsageBatchRepository.isAclUsageBatchExist(ACL_USAGE_BATCH_NAME)).andReturn(true).once();
        replay(aclUsageBatchRepository);
        assertTrue(aclUsageBatchService.isAclUsageBatchExist(ACL_USAGE_BATCH_NAME));
        verify(aclUsageBatchRepository);
    }

    @Test
    public void testInsertWithSoftDeletedWork() {
        mockStatic(RupContextUtils.class);
        mockStatic(RupPersistUtils.class);
        AclUsageBatch usageBatch = buildAclUsageBatch();
        Set<Long> wrWrkInsts = Set.of(137133077L);
        expect(RupPersistUtils.generateUuid()).andReturn(ACL_USAGE_BATCH_ID).once();
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(udmUsageService.getWrWrkInstPublishedToBaselineUdmUsages(usageBatch.getPeriods()))
            .andReturn(wrWrkInsts).once();
        expect(piIntegrationService.isDeletedWork(137133077L)).andReturn(true).once();
        aclUsageBatchRepository.insert(usageBatch);
        expectLastCall().once();
        expect(aclUsageService.populateAclUsages(ACL_USAGE_BATCH_ID, usageBatch.getPeriods(), USER_NAME, wrWrkInsts))
            .andReturn(1).once();
        replay(RupContextUtils.class, RupPersistUtils.class, aclUsageBatchRepository, aclUsageService, udmUsageService,
            piIntegrationService);
        assertEquals(1, aclUsageBatchService.insert(usageBatch));
        assertEquals(usageBatch.getId(), ACL_USAGE_BATCH_ID);
        assertEquals(usageBatch.getCreateUser(), USER_NAME);
        assertEquals(usageBatch.getUpdateUser(), USER_NAME);
        verify(RupContextUtils.class, RupPersistUtils.class, aclUsageBatchRepository, aclUsageService, udmUsageService,
            piIntegrationService);
    }

    @Test
    public void testInsert() {
        mockStatic(RupContextUtils.class);
        mockStatic(RupPersistUtils.class);
        AclUsageBatch usageBatch = buildAclUsageBatch();
        Set<Long> wrWrkInsts = Set.of(122581113L);
        expect(RupPersistUtils.generateUuid()).andReturn(ACL_USAGE_BATCH_ID).once();
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(udmUsageService.getWrWrkInstPublishedToBaselineUdmUsages(usageBatch.getPeriods()))
            .andReturn(wrWrkInsts).once();
        expect(piIntegrationService.isDeletedWork(122581113L)).andReturn(false).once();
        aclUsageBatchRepository.insert(usageBatch);
        expectLastCall().once();
        expect(aclUsageService.populateAclUsages(ACL_USAGE_BATCH_ID, usageBatch.getPeriods(), USER_NAME, Set.of()))
            .andReturn(1).once();
        replay(RupContextUtils.class, RupPersistUtils.class, aclUsageBatchRepository, aclUsageService, udmUsageService,
            piIntegrationService);
        assertEquals(1, aclUsageBatchService.insert(usageBatch));
        assertEquals(usageBatch.getId(), ACL_USAGE_BATCH_ID);
        assertEquals(usageBatch.getCreateUser(), USER_NAME);
        assertEquals(usageBatch.getUpdateUser(), USER_NAME);
        verify(RupContextUtils.class, RupPersistUtils.class, aclUsageBatchRepository, aclUsageService, udmUsageService,
            piIntegrationService);
    }

    @Test
    public void testGetAll() {
        List<AclUsageBatch> usageBatches = List.of(buildAclUsageBatch());
        expect(aclUsageBatchRepository.findAll()).andReturn(usageBatches).once();
        replay(aclUsageBatchRepository);
        assertSame(usageBatches, aclUsageBatchService.getAll());
        verify(aclUsageBatchRepository);
    }

    @Test
    public void testGetUsageBatchesByPeriod() {
        List<AclUsageBatch> usageBatches = List.of(buildAclUsageBatch());
        expect(aclUsageBatchRepository.findUsageBatchesByPeriod(202212, true)).andReturn(usageBatches).once();
        replay(aclUsageBatchRepository);
        assertSame(usageBatches, aclUsageBatchService.getUsageBatchesByPeriod(202212, true));
        verify(aclUsageBatchRepository);
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = List.of(202006);
        expect(aclUsageBatchRepository.findPeriods()).andReturn(periods).once();
        replay(aclUsageBatchRepository);
        assertSame(periods, aclUsageBatchService.getPeriods());
        verify(aclUsageBatchRepository);
    }

    @Test
    public void testCopyUsageBatch() {
        mockStatic(RupContextUtils.class);
        AclUsageBatch usageBatch = buildAclUsageBatch();
        Capture<String> usageBatchIdCapture = newCapture();
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        aclUsageBatchRepository.insert(usageBatch);
        expectLastCall().once();
        expect(aclUsageService.copyAclUsages(eq(ACL_USAGE_BATCH_ID), capture(usageBatchIdCapture),
            eq(USER_NAME))).andReturn(1).once();
        replay(RupContextUtils.class, aclUsageBatchRepository, aclUsageService);
        assertEquals(1, aclUsageBatchService.copyUsageBatch(ACL_USAGE_BATCH_ID, usageBatch));
        assertEquals(usageBatch.getId(), usageBatchIdCapture.getValue());
        assertEquals(usageBatch.getCreateUser(), USER_NAME);
        assertEquals(usageBatch.getUpdateUser(), USER_NAME);
        verify(RupContextUtils.class, aclUsageBatchRepository, aclUsageService);
    }

    @Test
    public void testDeleteAclUsageBatch() {
        mockStatic(RupContextUtils.class);
        AclUsageBatch usageBatch = buildAclUsageBatch();
        usageBatch.setId("e58a1a83-5766-4c62-8fe6-44ba5d1045b7");
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        aclUsageService.deleteUsages(usageBatch.getId());
        expectLastCall().once();
        aclUsageBatchRepository.deleteById(usageBatch.getId());
        expectLastCall().once();
        replay(RupContextUtils.class, aclUsageService, aclUsageBatchRepository);
        aclUsageBatchService.deleteAclUsageBatch(usageBatch);
        verify(RupContextUtils.class, aclUsageService, aclUsageBatchRepository);
    }

    private AclUsageBatch buildAclUsageBatch() {
        AclUsageBatch usageBatch = new AclUsageBatch();
        usageBatch.setName(ACL_USAGE_BATCH_NAME);
        usageBatch.setDistributionPeriod(202112);
        usageBatch.setPeriods(Set.of(202106, 202112));
        usageBatch.setEditable(true);
        return usageBatch;
    }
}
