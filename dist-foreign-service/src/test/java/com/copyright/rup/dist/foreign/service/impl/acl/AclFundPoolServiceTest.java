package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.repository.api.IAclFundPoolRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * Verifies {@link AclFundPoolService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/28/2022
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RupContextUtils.class)
public class AclFundPoolServiceTest {

    private static final String USER_NAME = "user@copyright.com";
    private IAclFundPoolRepository fundPoolRepository;
    private AclFundPoolService service;

    @Before
    public void setUp() {
        service = new AclFundPoolService();
        fundPoolRepository = createMock(IAclFundPoolRepository.class);
        Whitebox.setInternalState(service, fundPoolRepository);
    }

    @Test
    public void testInsert() {
        mockStatic(RupContextUtils.class);
        AclFundPool fundPool = buildFundPool();
        AclFundPoolDetail fundPoolDetail = buildFundPoolDetail();
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).times(2);
        fundPoolRepository.insert(fundPool);
        expectLastCall().once();
        fundPoolRepository.insertDetail(fundPoolDetail);
        expectLastCall().once();
        replay(RupContextUtils.class, fundPoolRepository);
        service.insertAclFundPool(fundPool, Collections.singletonList(fundPoolDetail));
        verify(RupContextUtils.class, fundPoolRepository);
    }

    @Test
    public void testInsertDetails() {
        mockStatic(RupContextUtils.class);
        AclFundPoolDetail fundPoolDetail = buildFundPoolDetail();
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        fundPoolRepository.insertDetail(fundPoolDetail);
        expectLastCall().once();
        replay(RupContextUtils.class, fundPoolRepository);
        service.insertAclFundPoolDetails(Collections.singletonList(fundPoolDetail));
        verify(RupContextUtils.class, fundPoolRepository);
    }

    private AclFundPoolDetail buildFundPoolDetail() {
        AclFundPoolDetail aclFundPoolDetail = new AclFundPoolDetail();
        aclFundPoolDetail.setFundPoolId("4f01a2fc-c5d4-4738-9715-c7dafc0c1fad");
        aclFundPoolDetail.setLicenseType("ACL");
        aclFundPoolDetail.setGrossAmount(new BigDecimal("0.55"));
        return aclFundPoolDetail;
    }

    private AclFundPool buildFundPool() {
        AclFundPool aclFundPool = new AclFundPool();
        aclFundPool.setName("Fund Pool Name");
        aclFundPool.setManualUploadFlag(true);
        aclFundPool.setLicenseType("ACL");
        return aclFundPool;
    }
}
