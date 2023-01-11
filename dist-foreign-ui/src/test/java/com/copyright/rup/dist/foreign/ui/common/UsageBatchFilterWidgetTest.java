package com.copyright.rup.dist.foreign.ui.common;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.Sets;
import com.vaadin.data.ValueProvider;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link UsageBatchFilterWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 2/15/17
 *
 * @author Mikalai Bezmen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class UsageBatchFilterWidgetTest {

    private static final String USAGE_BATCH_NAME = "Usage batch";
    private static final String USAGE_BATCH_ID = "Usage batch id";

    private UsageBatchFilterWidget usageBatchFilterWidget;

    @Before
    public void setUp() {
        usageBatchFilterWidget = new UsageBatchFilterWidget(List::of);
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(UsageBatch.class, usageBatchFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(USAGE_BATCH_NAME, usageBatchFilterWidget.getBeanItemCaption(buildUsageBatch()));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        Set usageBatches = Sets.newHashSet(USAGE_BATCH_NAME);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(usageBatches).once();
        replay(filterSaveEvent);
        usageBatchFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        FilterWindow filterWindow = createMock(FilterWindow.class);
        mockStatic(Windows.class);
        Capture<ValueProvider<UsageBatch, List<String>>> providerCapture = newCapture();
        Windows.showFilterWindow(eq("Batches filter"), same(usageBatchFilterWidget), capture(providerCapture));
        expectLastCall().andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(new HashSet<>());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("batches-filter-window");
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Usage Batch Name");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        usageBatchFilterWidget.showFilterWindow();
        assertEquals(List.of(USAGE_BATCH_NAME), providerCapture.getValue().apply(buildUsageBatch()));
        verify(filterWindow, Windows.class);
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(USAGE_BATCH_NAME);
        usageBatch.setId(USAGE_BATCH_ID);
        return usageBatch;
    }
}
