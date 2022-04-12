package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageWidget;

import com.google.common.collect.Sets;

import org.easymock.Capture;
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
    private IAclUsageService aclUsageService;
    private IAclUsageFilterController aclUsageFilterController;
    private IAclUsageFilterWidget aclUsageFilterWidget;

    @Before
    public void setUp() {
        udmUsageService = createMock(IUdmUsageService.class);
        aclUsageBatchService = createMock(IAclUsageBatchService.class);
        aclUsageService = createMock(IAclUsageService.class);
        aclUsageFilterController = createMock(IAclUsageFilterController.class);
        aclUsageFilterWidget = createMock(IAclUsageFilterWidget.class);
        Whitebox.setInternalState(controller, udmUsageService);
        Whitebox.setInternalState(controller, aclUsageBatchService);
        Whitebox.setInternalState(controller, aclUsageService);
        Whitebox.setInternalState(controller, aclUsageFilterController);
    }

    @Test
    public void testLoadBeans() {
        List<AclUsageDto> aclUsageDtos = Collections.singletonList(new AclUsageDto());
        AclUsageFilter filter = buildAclUsageFilter();
        Capture<Pageable> pageableCapture = newCapture();
        expect(aclUsageFilterController.getWidget()).andReturn(aclUsageFilterWidget).once();
        expect(aclUsageFilterWidget.getAppliedFilter()).andReturn(filter);
        expect(aclUsageService.getDtos(eq(filter), capture(pageableCapture), isNull())).andReturn(aclUsageDtos).once();
        replay(aclUsageFilterController, aclUsageFilterWidget, aclUsageService);
        assertSame(aclUsageDtos, controller.loadBeans(0, 10, null));
        assertEquals(10, pageableCapture.getValue().getLimit());
        assertEquals(0, pageableCapture.getValue().getOffset());
        verify(aclUsageFilterController, aclUsageFilterWidget, aclUsageService);
    }

    @Test
    public void testGetBeansCount() {
        AclUsageFilter filter = buildAclUsageFilter();
        expect(aclUsageFilterController.getWidget()).andReturn(aclUsageFilterWidget).once();
        expect(aclUsageFilterWidget.getAppliedFilter()).andReturn(filter);
        expect(aclUsageService.getCount(filter)).andReturn(10).once();
        replay(aclUsageFilterController, aclUsageFilterWidget, aclUsageService);
        assertEquals(10, controller.getBeansCount());
        verify(aclUsageFilterController, aclUsageFilterWidget, aclUsageService);
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
        expect(aclUsageFilterController.getWidget()).andReturn(aclUsageFilterWidget).once();
        aclUsageFilterWidget.clearFilter();
        expectLastCall().once();
        replay(aclUsageFilterController, aclUsageFilterWidget, aclUsageBatchService);
        assertEquals(1, controller.insertAclUsageBatch(buildAclUsageBatch()));
        verify(aclUsageFilterController, aclUsageFilterWidget, aclUsageBatchService);
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

    private AclUsageFilter buildAclUsageFilter() {
        AclUsageFilter aclUsageFilter = new AclUsageFilter();
        aclUsageFilter.setUsageBatchName(ACL_USAGE_BATCH_NAME);
        return aclUsageFilter;
    }
}
