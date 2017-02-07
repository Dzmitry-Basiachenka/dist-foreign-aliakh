package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;

import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.Set;

/**
 * Verifies {@link RightsholdersFilterController}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 1/20/17
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
public class RightsholdersFilterControllerTest {

    private static final long ACCOUNT_NUMBER = 10000L;

    private RightsholdersFilterController controller;

    @Before
    public void setUp() {
        controller = new RightsholdersFilterController();
    }

    @Test
    public void testLoadBeans() {
        IRightsholderService rightsholderService = createMock(IRightsholderService.class);
        Whitebox.setInternalState(controller, rightsholderService);
        expect(rightsholderService.getRros()).andReturn(Collections.emptyList()).once();
        replay(rightsholderService);
        assertEquals(Collections.emptyList(), controller.loadBeans());
        verify(rightsholderService);
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(Rightsholder.class, controller.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(ACCOUNT_NUMBER);
        rightsholder.setName("Name");
        assertEquals("10000 - Name", controller.getBeanItemCaption(rightsholder));
    }

    @Test
    public void testGetIdForBean() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(ACCOUNT_NUMBER);
        assertEquals(ACCOUNT_NUMBER, controller.getIdForBean(rightsholder), 0);
    }

    @Test
    public void testOnSave() {
        Set<Long> selectedItemsIds = Sets.newHashSet(ACCOUNT_NUMBER);
        IUsagesFilterWidget filterWidgetMock = createMock(IUsagesFilterWidget.class);
        controller.setFilterWidget(filterWidgetMock);
        filterWidgetMock.setSelectedRightsholders(selectedItemsIds);
        expectLastCall().once();
        replay(filterWidgetMock);
        controller.onSave(new FilterSaveEvent<>(filterWidgetMock, selectedItemsIds));
        verify(filterWidgetMock);
    }

    @Test
    @PrepareForTest({Windows.class, VaadinUtils.class})
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        mockStatic(VaadinUtils.class);
        IUsagesFilterWidget filterWidgetMock = createMock(IUsagesFilterWidget.class);
        UsageFilter filterMock = createMock(UsageFilter.class);
        FilterWindow filterWindowMock = createMock(FilterWindow.class);
        Set<Long> selectedItemsIds = Sets.newHashSet(10000L);
        controller.setFilterWidget(filterWidgetMock);
        VaadinUtils.addComponentStyle(filterWindowMock, "rightsholders-filter-window");
        expectLastCall().once();
        expect(Windows.showFilterWindow("RROs filter", controller, "name", "accountNumber"))
            .andReturn(filterWindowMock).once();
        expect(filterWidgetMock.getFilter()).andReturn(filterMock).once();
        expect(filterMock.getRhAccountNumbers()).andReturn(selectedItemsIds).once();
        filterWindowMock.setSelectedItemsIds(selectedItemsIds);
        expectLastCall().once();
        replay(filterWidgetMock, filterMock, filterWindowMock, Windows.class, VaadinUtils.class);
        assertSame(filterWindowMock, controller.showFilterWindow());
        verify(filterWidgetMock, filterMock, filterWindowMock, Windows.class, VaadinUtils.class);
    }
}
