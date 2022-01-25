package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.repository.api.IAclGrantDetailRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailService;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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

    private static final String USER_NAME = "user@copyright.com";
    private static final String ACL_GRANT_SET_ID = "80c2b98e-6902-4d81-b7fc-56b93135d089";

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
        aclGrantDetailService.insert(ACL_GRANT_SET_ID, Lists.newArrayList(grantDetail), USER_NAME);
        verify(aclGrantDetailRepository);
        assertEquals(USER_NAME, grantDetail.getCreateUser());
        assertEquals(USER_NAME, grantDetail.getUpdateUser());
        assertNotNull(grantDetail.getId());
        assertEquals(ACL_GRANT_SET_ID, grantDetail.getGrantSetId());
    }
}
