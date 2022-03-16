package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantSetRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantSetService;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
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
    private static final String GRANT_SET_NAME = "ACL Grant Set 2021";
    private static final Long WR_WRK_INST = 122825347L;
    private static final String SYSTEM_TITLE = "Wall Street journal";

    private IAclGrantSetService aclGrantSetService;
    private IUdmBaselineRepository udmBaselineRepository;
    private IAclGrantService aclGrantService;
    private IAclGrantSetRepository aclGrantSetRepository;
    private IAclGrantDetailService aclGrantDetailService;
    private IRightsholderService rightsholderService;

    @Before
    public void setUp() {
        aclGrantSetService = new AclGrantSetService();
        udmBaselineRepository = createMock(IUdmBaselineRepository.class);
        aclGrantService = createMock(IAclGrantService.class);
        aclGrantSetRepository = createMock(IAclGrantSetRepository.class);
        aclGrantDetailService = createMock(IAclGrantDetailService.class);
        rightsholderService = createMock(IRightsholderService.class);
        Whitebox.setInternalState(aclGrantSetService, udmBaselineRepository);
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
        expect(udmBaselineRepository.findWrWrkInstToSystemTitles(grantSet.getPeriods()))
            .andReturn(ImmutableMap.of(WR_WRK_INST, SYSTEM_TITLE)).once();
        expect(aclGrantService.createAclGrantDetails(grantSet, ImmutableMap.of(WR_WRK_INST, SYSTEM_TITLE), USER_NAME))
            .andReturn(Collections.singletonList(grantDetail)).once();
        aclGrantSetRepository.insert(grantSet);
        expectLastCall().once();
        grantDetail.setSystemTitle(SYSTEM_TITLE);
        aclGrantDetailService.insert(eq(Lists.newArrayList(grantDetail)));
        expectLastCall().once();
        expect(rightsholderService.updateRightsholders(Collections.singleton(7000813806L)))
            .andReturn(Collections.singletonList(new Rightsholder()));
        replay(RupContextUtils.class, udmBaselineRepository, aclGrantService, aclGrantSetRepository,
            aclGrantDetailService);
        assertEquals(1, aclGrantSetService.insert(grantSet));
        verify(RupContextUtils.class, udmBaselineRepository, aclGrantService, aclGrantSetRepository,
            aclGrantDetailService);
    }

    @Test
    public void testIsGrantSetExist() {
        expect(aclGrantSetRepository.isGrantSetExist(GRANT_SET_NAME)).andReturn(true).once();
        replay(aclGrantSetRepository);
        assertTrue(aclGrantSetService.isGrantSetExist(GRANT_SET_NAME));
        verify(aclGrantSetRepository);
    }

    @Test
    public void testIsGrantSetNotExist() {
        expect(aclGrantSetRepository.isGrantSetExist(GRANT_SET_NAME)).andReturn(false).once();
        replay(aclGrantSetRepository);
        assertFalse(aclGrantSetService.isGrantSetExist(GRANT_SET_NAME));
        verify(aclGrantSetRepository);
    }

    @Test
    public void testGetAll() {
        List<AclGrantSet> grantSets = Collections.singletonList(new AclGrantSet());
        expect(aclGrantSetRepository.findAll()).andReturn(grantSets).once();
        replay(aclGrantSetRepository);
        assertSame(grantSets, aclGrantSetService.getAll());
        verify(aclGrantSetRepository);
    }

    @Test
    public void testGetGrantPeriods() {
        List<Integer> grantPeriods = Arrays.asList(202212, 202206, 202112);
        expect(aclGrantSetRepository.findGrantPeriods()).andReturn(grantPeriods).once();
        replay(aclGrantSetRepository);
        assertSame(grantPeriods, aclGrantSetService.getGrantPeriods());
        verify(aclGrantSetRepository);
    }

    private AclGrantSet buildAclGrantSet() {
        AclGrantSet grantSet = new AclGrantSet();
        grantSet.setName("ACL Grant Set 2021");
        grantSet.setGrantPeriod(202112);
        grantSet.setPeriods(Sets.newHashSet(202106, 202112));
        grantSet.setLicenseType("ACL");
        grantSet.setEditable(true);
        return grantSet;
    }
}
