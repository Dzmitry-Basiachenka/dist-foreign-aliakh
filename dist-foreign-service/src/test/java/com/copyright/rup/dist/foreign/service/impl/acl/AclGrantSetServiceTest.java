package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantSetRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmBaselineRepository;
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

import java.util.Collections;

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

    @Before
    public void setUp() {
        aclGrantSetService = new AclGrantSetService();
        udmBaselineRepository = createMock(IUdmBaselineRepository.class);
        aclGrantService = createMock(IAclGrantService.class);
        aclGrantSetRepository = createMock(IAclGrantSetRepository.class);
        aclGrantDetailService = createMock(IAclGrantDetailService.class);
        Whitebox.setInternalState(aclGrantSetService, udmBaselineRepository);
        Whitebox.setInternalState(aclGrantSetService, aclGrantService);
        Whitebox.setInternalState(aclGrantSetService, aclGrantSetRepository);
        Whitebox.setInternalState(aclGrantSetService, aclGrantDetailService);
    }

    @Test
    public void testInsert() {
        mockStatic(RupContextUtils.class);
        AclGrantSet grantSet = buildAclGrantSet();
        AclGrantDetail grantDetail = new AclGrantDetail();
        grantDetail.setWrWrkInst(WR_WRK_INST);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(udmBaselineRepository.findWrWrkInstToSystemTitles(grantSet.getPeriods()))
            .andReturn(ImmutableMap.of(WR_WRK_INST, SYSTEM_TITLE)).once();
        expect(aclGrantService.createAclGrantDetails(grantSet, ImmutableMap.of(WR_WRK_INST, SYSTEM_TITLE)))
            .andReturn(Collections.singletonList(grantDetail)).once();
        aclGrantSetRepository.insert(grantSet);
        expectLastCall().once();
        grantDetail.setSystemTitle(SYSTEM_TITLE);
        aclGrantDetailService.insert(eq(Lists.newArrayList(grantDetail)));
        expectLastCall().once();
        replay(RupContextUtils.class, udmBaselineRepository, aclGrantService, aclGrantSetRepository,
            aclGrantDetailService);
        aclGrantSetService.insert(grantSet);
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
