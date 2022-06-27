package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclFundPoolRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

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
@PrepareForTest({RupContextUtils.class, RupPersistUtils.class})
public class AclFundPoolServiceTest {

    private static final String USER_NAME = "user@copyright.com";
    private static final String LICENSE_TYPE = "ACL";
    private IAclFundPoolRepository fundPoolRepository;
    private AclFundPoolService service;

    @Before
    public void setUp() {
        service = new AclFundPoolService();
        fundPoolRepository = createMock(IAclFundPoolRepository.class);
        Whitebox.setInternalState(service, fundPoolRepository);
    }

    @Test
    public void testInsertManualAclFundPool() {
        mockStatic(RupContextUtils.class);
        AclFundPool fundPool = buildFundPool();
        AclFundPoolDetail fundPoolDetail = buildFundPoolDetail();
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).times(2);
        fundPoolRepository.insert(fundPool);
        expectLastCall().once();
        fundPoolRepository.insertDetail(fundPoolDetail);
        expectLastCall().once();
        replay(RupContextUtils.class, fundPoolRepository);
        service.insertManualAclFundPool(fundPool, Collections.singletonList(fundPoolDetail));
        verify(RupContextUtils.class, fundPoolRepository);
    }

    @Test
    public void testInsertLdmtAclFundPool() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        mockStatic(RupPersistUtils.class);
        String id = "b56e607d-5611-45cf-ba36-d421b9a8674c";
        expect(RupPersistUtils.generateUuid()).andReturn(id).once();
        AclFundPool fundPool = buildFundPool();
        fundPoolRepository.insert(fundPool);
        expectLastCall().once();
        expect(fundPoolRepository.addLdmtDetailsToFundPool(id, fundPool.getLicenseType(), USER_NAME))
            .andReturn(1).once();
        replay(RupContextUtils.class, RupPersistUtils.class, fundPoolRepository);
        assertEquals(1, service.insertLdmtAclFundPool(fundPool));
        verify(RupContextUtils.class, RupPersistUtils.class, fundPoolRepository);
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

    @Test
    public void testFundPoolExists() {
        expect(fundPoolRepository.isFundPoolExists("ACL Fund Pool 202012")).andReturn(true).once();
        replay(fundPoolRepository);
        assertTrue(service.fundPoolExists("ACL Fund Pool 202012"));
        verify(fundPoolRepository);
    }

    @Test
    public void testIsLdmtDetailExist() {
        expect(fundPoolRepository.isLdmtDetailExist(LICENSE_TYPE)).andReturn(true).once();
        replay(fundPoolRepository);
        assertTrue(service.isLdmtDetailExist(LICENSE_TYPE));
        verify(fundPoolRepository);
    }

    @Test
    public void testGetDtos() {
        List<AclFundPoolDetailDto> fundPoolDetails = Collections.singletonList(new AclFundPoolDetailDto());
        AclFundPoolDetailFilter filter = new AclFundPoolDetailFilter();
        filter.setLicenseType(LICENSE_TYPE);
        expect(fundPoolRepository.findDtosByFilter(filter)).andReturn(fundPoolDetails).once();
        replay(fundPoolRepository);
        assertSame(fundPoolDetails, service.getDtosByFilter(filter));
        verify(fundPoolRepository);
    }

    @Test
    public void testGetDtosEmptyFilter() {
        List<AclFundPoolDetailDto> result = service.getDtosByFilter(new AclFundPoolDetailFilter());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAll() {
        List<AclFundPool> fundPools = Collections.singletonList(buildFundPool());
        expect(fundPoolRepository.findAll()).andReturn(fundPools).once();
        replay(fundPoolRepository);
        assertSame(fundPools, service.getAll());
        verify(fundPoolRepository);
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = Collections.singletonList(202006);
        expect(fundPoolRepository.findPeriods()).andReturn(periods).once();
        replay(fundPoolRepository);
        assertSame(periods, service.getPeriods());
        verify(fundPoolRepository);
    }

    @Test
    public void testDeleteAclFundPool() {
        mockStatic(RupContextUtils.class);
        AclFundPool fundPool = buildFundPool();
        fundPool.setId("39c41cc6-7615-4f74-ab86-66bdb33be233");
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        fundPoolRepository.deleteDetailsByFundPoolId(fundPool.getId());
        fundPoolRepository.deleteById(fundPool.getId());
        expectLastCall().once();
        replay(RupContextUtils.class, fundPoolRepository);
        service.deleteAclFundPool(fundPool);
        verify(RupContextUtils.class, fundPoolRepository);
    }

    @Test
    public void testGetFundPoolsByLicenseTypeAndPeriod() {
        List<AclFundPool> fundPools = Collections.singletonList(buildFundPool());
        expect(fundPoolRepository.findFundPoolsByLicenseTypeAndPeriod("ACl", 202212)).andReturn(fundPools).once();
        replay(fundPoolRepository);
        assertSame(fundPools, service.getFundPoolsByLicenseTypeAndPeriod("ACl", 202212));
        verify(fundPoolRepository);
    }

    private AclFundPoolDetail buildFundPoolDetail() {
        AclFundPoolDetail aclFundPoolDetail = new AclFundPoolDetail();
        aclFundPoolDetail.setFundPoolId("4f01a2fc-c5d4-4738-9715-c7dafc0c1fad");
        aclFundPoolDetail.setLicenseType(LICENSE_TYPE);
        aclFundPoolDetail.setGrossAmount(new BigDecimal("0.55"));
        return aclFundPoolDetail;
    }

    private AclFundPool buildFundPool() {
        AclFundPool aclFundPool = new AclFundPool();
        aclFundPool.setName("Fund Pool Name");
        aclFundPool.setManualUploadFlag(true);
        aclFundPool.setLicenseType(LICENSE_TYPE);
        return aclFundPool;
    }
}
