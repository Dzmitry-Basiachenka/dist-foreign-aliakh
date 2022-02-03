package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantDetailRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailService;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclGrantDetailService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/28/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclGrantDetailServiceTest {

    private static final String ACL_GRANT_SET_NAME = "ACL Grant Set 2021";

    private IAclGrantDetailService aclGrantDetailService;
    private IAclGrantDetailRepository aclGrantDetailRepository;

    @Before
    public void setUp() {
        aclGrantDetailService = new AclGrantDetailService();
        aclGrantDetailRepository = createMock(IAclGrantDetailRepository.class);
        Whitebox.setInternalState(aclGrantDetailService, aclGrantDetailRepository);
    }

    @Test
    public void testInsert() {
        AclGrantDetail grantDetail = new AclGrantDetail();
        aclGrantDetailRepository.insert(grantDetail);
        expectLastCall().once();
        replay(aclGrantDetailRepository);
        aclGrantDetailService.insert(Lists.newArrayList(grantDetail));
        verify(aclGrantDetailRepository);
    }

    @Test
    public void testGetCount() {
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        filter.setGrantSetNames(Collections.singleton(ACL_GRANT_SET_NAME));
        expect(aclGrantDetailRepository.findCountByFilter(filter)).andReturn(1).once();
        replay(aclGrantDetailRepository);
        assertEquals(1, aclGrantDetailService.getCount(filter));
        verify(aclGrantDetailRepository);
    }

    @Test
    public void testGetCountEmptyFilter() {
        assertEquals(0, aclGrantDetailService.getCount(new AclGrantDetailFilter()));
    }

    @Test
    public void testGetDtos() {
        List<AclGrantDetailDto> grantDetails = Collections.singletonList(new AclGrantDetailDto());
        Pageable pageable = new Pageable(0, 1);
        Sort sort = new Sort("licenseType", Sort.Direction.ASC);
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        filter.setGrantSetNames(Collections.singleton(ACL_GRANT_SET_NAME));
        expect(aclGrantDetailRepository.findDtosByFilter(filter, pageable, sort)).andReturn(grantDetails).once();
        replay(aclGrantDetailRepository);
        assertSame(grantDetails, aclGrantDetailService.getDtos(filter, pageable, sort));
        verify(aclGrantDetailRepository);
    }

    @Test
    public void testGetDtosEmptyFilter() {
        List<AclGrantDetailDto> result = aclGrantDetailService.getDtos(new AclGrantDetailFilter(), null, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
