package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageWidget;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclUsageController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/30/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclUsageControllerTest {

    private static final String ACL_USAGE_BATCH_NAME = "ACL Usage Batch 2021";

    private final AclUsageController controller = new AclUsageController();

    private IUdmUsageService udmUsageService;
    private IAclUsageBatchService aclUsageBatchService;

    @Before
    public void setUp() {
        udmUsageService = createMock(IUdmUsageService.class);
        aclUsageBatchService = createMock(IAclUsageBatchService.class);
        Whitebox.setInternalState(controller, udmUsageService);
        Whitebox.setInternalState(controller, aclUsageBatchService);
    }

    @Test
    public void testLoadBeans() {
        //TODO {dbasiachenka} implement
        assertEquals(Collections.emptyList(), controller.loadBeans(0, 10, null));
    }

    @Test
    public void testGetBeansCount() {
        //TODO {dbasiachenka} implement
        assertEquals(0, controller.getBeansCount());
    }

    @Test
    public void testGetAllPeriods() {
        List<Integer> periods = Collections.singletonList(202106);
        expect(udmUsageService.getPeriods()).andReturn(periods).once();
        replay(udmUsageService);
        assertEquals(periods, controller.getAllPeriods());
        verify(udmUsageService);
    }

    @Test
    public void testIsAclUsageBatchExist() {
        expect(aclUsageBatchService.isAclUsageBatchExist(ACL_USAGE_BATCH_NAME)).andReturn(true).once();
        replay(aclUsageBatchService);
        assertTrue(controller.isAclUsageBatchExist(ACL_USAGE_BATCH_NAME));
        verify(aclUsageBatchService);
    }

    @Test
    public void testInsertAclUsageBatch() {
        expect(aclUsageBatchService.insert(buildAclUsageBatch())).andReturn(1).once();
        replay(aclUsageBatchService);
        assertEquals(1, controller.insertAclUsageBatch(buildAclUsageBatch()));
        verify(aclUsageBatchService);
    }

    @Test
    public void testInstantiateWidget() {
        IAclUsageWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(AclUsageWidget.class, widget.getClass());
    }

    private AclUsageBatch buildAclUsageBatch() {
        AclUsageBatch usageBatch = new AclUsageBatch();
        usageBatch.setName(ACL_USAGE_BATCH_NAME);
        usageBatch.setDistributionPeriod(202112);
        usageBatch.setPeriods(Sets.newHashSet(202106, 202112));
        usageBatch.setEditable(true);
        return usageBatch;
    }
}
