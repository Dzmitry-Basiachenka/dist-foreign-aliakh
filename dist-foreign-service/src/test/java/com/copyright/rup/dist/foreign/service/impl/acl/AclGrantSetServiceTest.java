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
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantSetRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantSetService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;

import com.google.common.collect.ImmutableMap;

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
 * Verifies {@link AclGrantSetService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/27/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class AclGrantSetServiceTest {

    private static final String USER_NAME = "user@copyright.com";
    private static final String ACL_GRANT_SET_NAME = "ACL Grant Set 2021";
    private static final Long WR_WRK_INST = 122825347L;
    private static final String SYSTEM_TITLE = "Wall Street journal";

    private IAclGrantSetService aclGrantSetService;
    private IUdmBaselineService udmBaselineService;
    private IAclGrantService aclGrantService;
    private IAclGrantSetRepository aclGrantSetRepository;
    private IAclGrantDetailService aclGrantDetailService;
    private IRightsholderService rightsholderService;

    @Before
    public void setUp() {
        aclGrantSetService = new AclGrantSetService();
        udmBaselineService = createMock(IUdmBaselineService.class);
        aclGrantService = createMock(IAclGrantService.class);
        aclGrantSetRepository = createMock(IAclGrantSetRepository.class);
        aclGrantDetailService = createMock(IAclGrantDetailService.class);
        rightsholderService = createMock(IRightsholderService.class);
        Whitebox.setInternalState(aclGrantSetService, udmBaselineService);
        Whitebox.setInternalState(aclGrantSetService, aclGrantService);
        Whitebox.setInternalState(aclGrantSetService, aclGrantSetRepository);
        Whitebox.setInternalState(aclGrantSetService, aclGrantDetailService);
        Whitebox.setInternalState(aclGrantSetService, rightsholderService);
    }

    @Test
    public void testInsert() {
        mockStatic(RupContextUtils.class);
        AclGrantSet grantSet = buildAclGrantSet();
        AclGrantDetail grantDetail = new AclGrantDetail();
        grantDetail.setWrWrkInst(WR_WRK_INST);
        grantDetail.setRhAccountNumber(7000813806L);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(udmBaselineService.getWrWrkInstToSystemTitles(grantSet.getPeriods()))
            .andReturn(ImmutableMap.of(WR_WRK_INST, SYSTEM_TITLE)).once();
        expect(aclGrantService.createAclGrantDetails(grantSet, ImmutableMap.of(WR_WRK_INST, SYSTEM_TITLE), USER_NAME))
            .andReturn(List.of(grantDetail)).once();
        aclGrantSetRepository.insert(grantSet);
        expectLastCall().once();
        grantDetail.setSystemTitle(SYSTEM_TITLE);
        aclGrantDetailService.insert(eq(List.of(grantDetail)));
        expectLastCall().once();
        expect(rightsholderService.updateRightsholders(Set.of(7000813806L)))
            .andReturn(List.of(new Rightsholder()));
        Capture<String> grantSetIdCapture = newCapture();
        aclGrantDetailService.populatePayeesAsync(capture(grantSetIdCapture));
        expectLastCall().once();
        replay(RupContextUtils.class, udmBaselineService, aclGrantService, aclGrantSetRepository,
            aclGrantDetailService);
        assertEquals(1, aclGrantSetService.insert(grantSet));
        assertEquals(grantSet.getId(), grantSetIdCapture.getValue());
        verify(RupContextUtils.class, udmBaselineService, aclGrantService, aclGrantSetRepository,
            aclGrantDetailService);
    }

    @Test
    public void testIsGrantSetExist() {
        expect(aclGrantSetRepository.isGrantSetExist(ACL_GRANT_SET_NAME)).andReturn(true).once();
        replay(aclGrantSetRepository);
        assertTrue(aclGrantSetService.isGrantSetExist(ACL_GRANT_SET_NAME));
        verify(aclGrantSetRepository);
    }

    @Test
    public void testGetAll() {
        List<AclGrantSet> grantSets = List.of(new AclGrantSet());
        expect(aclGrantSetRepository.findAll()).andReturn(grantSets).once();
        replay(aclGrantSetRepository);
        assertSame(grantSets, aclGrantSetService.getAll());
        verify(aclGrantSetRepository);
    }

    @Test
    public void testGetGrantPeriods() {
        List<Integer> grantPeriods = List.of(202212, 202206, 202112);
        expect(aclGrantSetRepository.findGrantPeriods()).andReturn(grantPeriods).once();
        replay(aclGrantSetRepository);
        assertSame(grantPeriods, aclGrantSetService.getGrantPeriods());
        verify(aclGrantSetRepository);
    }

    @Test
    public void testDeleteAclGrantSet() {
        mockStatic(RupContextUtils.class);
        AclGrantSet grantSet = buildAclGrantSet();
        grantSet.setId(RupPersistUtils.generateUuid());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        aclGrantDetailService.deleteGrantDetails(grantSet.getId());
        expectLastCall().once();
        aclGrantSetRepository.deleteById(grantSet.getId());
        expectLastCall().once();
        replay(RupContextUtils.class, aclGrantDetailService, aclGrantSetRepository);
        aclGrantSetService.deleteAclGrantSet(grantSet);
        verify(RupContextUtils.class, aclGrantDetailService, aclGrantSetRepository);
    }

    @Test
    public void testGetGrantSetsByLicenseTypeAndPeriod() {
        List<AclGrantSet> grantSets = List.of(buildAclGrantSet());
        expect(aclGrantSetRepository.findGrantSetsByLicenseTypeAndPeriod("ACL", 202212, true)).andReturn(grantSets)
            .once();
        replay(aclGrantSetRepository);
        assertSame(grantSets, aclGrantSetService.getGrantSetsByLicenseTypeAndPeriod("ACL", 202212, true));
        verify(aclGrantSetRepository);
    }

    @Test
    public void testCopyGrantSet() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        AclGrantSet grantSet = buildAclGrantSet();
        Capture<String> targetGrantSetId = newCapture();
        String sourceGrantSetId = "08cfb683-7b1a-473c-b2e3-1e7a14b5e8d8";
        aclGrantSetRepository.insert(grantSet);
        expectLastCall().once();
        expect(aclGrantDetailService.copyGrantDetails(eq(sourceGrantSetId), capture(targetGrantSetId),
            eq(USER_NAME))).andReturn(1).once();
        replay(RupContextUtils.class, aclGrantSetRepository, aclGrantDetailService);
        aclGrantSetService.copyGrantSet(grantSet, sourceGrantSetId);
        assertEquals(grantSet.getId(), targetGrantSetId.getValue());
        assertEquals(grantSet.getCreateUser(), USER_NAME);
        assertEquals(grantSet.getUpdateUser(), USER_NAME);
        verify(RupContextUtils.class, aclGrantSetRepository, aclGrantDetailService);
    }

    private AclGrantSet buildAclGrantSet() {
        AclGrantSet grantSet = new AclGrantSet();
        grantSet.setName(ACL_GRANT_SET_NAME);
        grantSet.setGrantPeriod(202112);
        grantSet.setPeriods(Set.of(202106, 202112));
        grantSet.setLicenseType("ACL");
        grantSet.setEditable(true);
        return grantSet;
    }
}
