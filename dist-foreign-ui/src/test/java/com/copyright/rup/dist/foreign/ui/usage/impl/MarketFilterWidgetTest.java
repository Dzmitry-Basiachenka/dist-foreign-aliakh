package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.Sets;
import com.vaadin.server.Sizeable.Unit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.HashSet;

/**
 * Verifies {@link MarketFilterWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/29/2018
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class MarketFilterWidgetTest {

    private static final String MARKET = "Bus";

    private MarketFilterWidget marketFilterWidget;

    @Before
    public void setUp() {
        marketFilterWidget = new MarketFilterWidget(Collections::emptyList);
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(String.class, marketFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(MARKET, marketFilterWidget.getBeanItemCaption(MARKET));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent<String> filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Sets.newHashSet(MARKET)).once();
        replay(filterSaveEvent);
        marketFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        FilterWindow<String> filterWindow = createMock(FilterWindow.class);
        mockStatic(Windows.class);
        Windows.showFilterWindow("Markets filter", marketFilterWidget);
        expectLastCall().andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(new HashSet<>());
        expectLastCall().once();
        filterWindow.setWidth(350, Unit.PIXELS);
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn(MARKET).once();
        filterWindow.addStyleName("markets-filter-window");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        marketFilterWidget.showFilterWindow();
        verify(filterWindow, Windows.class);
    }
}
