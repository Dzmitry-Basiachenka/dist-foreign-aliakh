package com.copyright.rup.dist.foreign.vui.common;

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
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.FilterWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.function.ValueProvider;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Verifies {@link UsageBatchFilterWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 2/15/2017
 *
 * @author Mikalai Bezmen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class UsageBatchFilterWidgetTest {

    private static final String USAGE_BATCH_ID = "92639315-5c29-440d-9f52-2039bfd87f5c";
    private static final String USAGE_BATCH_NAME = "Usage Batch";

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
    public void testOnComponentEvent() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        Set<String> usageBatches = Set.of(USAGE_BATCH_NAME);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(usageBatches).once();
        replay(filterSaveEvent);
        usageBatchFilterWidget.onComponentEvent(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        FilterWindow filterWindow = createMock(FilterWindow.class);
        mockStatic(Windows.class);
        Capture<ValueProvider<UsageBatch, List<String>>> providerCapture = newCapture();
        Windows.showFilterWindow(eq("Batches filter"), same(usageBatchFilterWidget), capture(providerCapture));
        expectLastCall().andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Set.of());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn(Optional.empty()).once();
        filterWindow.setId("batches-filter-window");
        expectLastCall().once();
        filterWindow.addClassName("batches-filter-window");
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
        usageBatch.setId(USAGE_BATCH_ID);
        usageBatch.setName(USAGE_BATCH_NAME);
        return usageBatch;
    }
}
