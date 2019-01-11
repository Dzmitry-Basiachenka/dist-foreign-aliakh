package com.copyright.rup.dist.foreign.ui.audit.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link StatusFilterWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/22/18
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class StatusFilterWidgetTest {

    private static final String SELECTED_ITEMS_IDS = "selectedItemsIds";

    private final StatusFilterWidget widget = new StatusFilterWidget();

    @Test
    public void testLoadBeans() {
        List<UsageStatusEnum> statuses = Lists.newArrayList(UsageStatusEnum.values());
        statuses.remove(UsageStatusEnum.US_TAX_COUNTRY);
        assertEquals(statuses, widget.loadBeans());
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(UsageStatusEnum.class, widget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals("ELIGIBLE", widget.getBeanItemCaption(UsageStatusEnum.ELIGIBLE));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent event = createMock(FilterSaveEvent.class);
        Set<UsageStatusEnum> values = Sets.newHashSet(UsageStatusEnum.ELIGIBLE);
        expect(event.getSelectedItemsIds()).andReturn(values).once();
        replay(event);
        widget.onSave(event);
        assertEquals(values, Whitebox.getInternalState(widget, "selectedItemsIds"));
        verify(event);
    }

    @Test
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        FilterWindow filterWindow = createMock(FilterWindow.class);
        expect(Windows.showFilterWindow("Status filter", widget)).andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(new HashSet());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn(null).once();
        filterWindow.setId("status-filter-window");
        expectLastCall().once();
        filterWindow.addStyleName("status-filter-window");
        expectLastCall().once();
        replay(Windows.class, filterWindow);
        widget.showFilterWindow();
        verify(Windows.class, filterWindow);
    }

    @Test
    public void testReset() {
        Whitebox.setInternalState(widget, SELECTED_ITEMS_IDS, Sets.newHashSet(UsageStatusEnum.ELIGIBLE));
        widget.reset();
        assertEquals(new HashSet<>(), Whitebox.getInternalState(widget, SELECTED_ITEMS_IDS));
    }
}
