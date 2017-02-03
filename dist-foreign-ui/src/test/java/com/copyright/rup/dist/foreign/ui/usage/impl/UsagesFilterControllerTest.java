package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UsagesFilterController}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 1/20/17
 *
 * @author Aliaksandr Radkevich
 */
public class UsagesFilterControllerTest {

    private static final Integer FISCAL_YEAR = 2017;
    private UsagesFilterController controller;

    @Before
    public void setUp() {
        controller = new UsagesFilterController();
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testOnUsageBatchFilterClick() {
        IUsagesFilterWidget filterWidgetMock = PowerMock.createMock(IUsagesFilterWidget.class);
        UsageBatchesFilterController batchesFilterController = createMock(UsageBatchesFilterController.class);
        FilterWindow<String, UsageBatch> filterWindow = createMock(FilterWindow.class);
        Whitebox.setInternalState(controller, "batchesFilterController", batchesFilterController);
        Whitebox.setInternalState(controller, "widget", filterWidgetMock);
        batchesFilterController.setFilterWidget(filterWidgetMock);
        expectLastCall().once();
        expect(batchesFilterController.showFilterWindow()).andReturn(filterWindow).once();
        replay(batchesFilterController, filterWidgetMock, filterWindow);
        controller.onUsageBatchFilterClick();
        verify(batchesFilterController, filterWidgetMock, filterWindow);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testOnRightsholderFilterClick() {
        IUsagesFilterWidget filterWidgetMock = PowerMock.createMock(IUsagesFilterWidget.class);
        RightsholdersFilterController rightsholdersFilterController =
            createMock(RightsholdersFilterController.class);
        FilterWindow<Long, Rightsholder> filterWindow = createMock(FilterWindow.class);
        Whitebox.setInternalState(controller, "rightsholdersFilterController", rightsholdersFilterController);
        Whitebox.setInternalState(controller, "widget", filterWidgetMock);
        rightsholdersFilterController.setFilterWidget(filterWidgetMock);
        expectLastCall().once();
        expect(rightsholdersFilterController.showFilterWindow()).andReturn(filterWindow).once();
        replay(rightsholdersFilterController, filterWidgetMock, filterWindow);
        controller.onRightsholderFilterClick();
        verify(rightsholdersFilterController, filterWidgetMock, filterWindow);
    }

    @Test
    public void testGetFiscalYears() {
        IUsageBatchService usageBatchService = createMock(IUsageBatchService.class);
        Whitebox.setInternalState(controller, "usageBatchService", usageBatchService);
        expect(usageBatchService.getFiscalYears()).andReturn(Collections.singletonList(FISCAL_YEAR)).once();
        replay(usageBatchService);
        List<Integer> fiscalYears = controller.getFiscalYears();
        assertTrue(CollectionUtils.isNotEmpty(fiscalYears));
        assertEquals(1, fiscalYears.size());
        assertTrue(fiscalYears.contains(FISCAL_YEAR));
        verify(usageBatchService);
    }
}
