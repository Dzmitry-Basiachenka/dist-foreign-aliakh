package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageWidget;

import com.vaadin.ui.HorizontalLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AaclUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/23/2019
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class, OffsetDateTime.class})
public class AaclUsageControllerTest {

    private AaclUsageController controller;
    private IAaclUsageWidget usagesWidget;
    private IAaclUsageFilterController filterController;
    private IAaclUsageFilterWidget filterWidgetMock;
    private IUsageBatchService usageBatchService;

    @Before
    public void setUp() {
        controller = new AaclUsageController();
        usagesWidget = createMock(IAaclUsageWidget.class);
        usageBatchService = createMock(IUsageBatchService.class);
        filterController = createMock(IAaclUsageFilterController.class);
        filterWidgetMock = createMock(IAaclUsageFilterWidget.class);
        Whitebox.setInternalState(controller, usagesWidget);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, usagesWidget);
        Whitebox.setInternalState(controller, filterController);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testOnFilterChanged() {
        FilterChangedEvent filterChangedEvent = new FilterChangedEvent(new HorizontalLayout());
        usagesWidget.refresh();
        expectLastCall().once();
        replay(usagesWidget);
        controller.onFilterChanged(filterChangedEvent);
        verify(usagesWidget);
    }

    @Test
    public void testLoadUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        List<Usage> usages = Collections.singletonList(new Usage());
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        expect(usageBatchService.insertAaclBatch(usageBatch, usages)).andReturn(1).once();
        usageBatchService.sendForMatching(usages);
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidgetMock);
        assertEquals(1, controller.loadUsageBatch(usageBatch, usages));
        verify(usageBatchService, filterController, filterWidgetMock);
    }
}
