package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclUsageFilterController}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 03/31/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclUsageFilterControllerTest {

    private final AclUsageFilterController controller = new AclUsageFilterController();
    private IAclUsageBatchService aclUsageBatchService;

    @Before
    public void setUp() {
        aclUsageBatchService = createMock(IAclUsageBatchService.class);
        Whitebox.setInternalState(controller, aclUsageBatchService);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testGetAllAclUsageBatches() {
        List<AclUsageBatch> aclUsageBatches = Collections.singletonList(new AclUsageBatch());
        expect(aclUsageBatchService.getAll()).andReturn(aclUsageBatches).once();
        replay(aclUsageBatchService);
        assertSame(aclUsageBatches, controller.getAllAclUsageBatches());
        verify(aclUsageBatchService);
    }
}
