package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.vaadin.data.ValueProvider;
import org.easymock.Capture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link LastValuePeriodFilterWidget}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 09/23/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class LastValuePeriodFilterWidgetTest {

    private static final String LAST_VALUE_PERIOD = "062021";
    private final LastValuePeriodFilterWidget lastValuePeriodFilterWidget =
        new LastValuePeriodFilterWidget(() -> Collections.singletonList(LAST_VALUE_PERIOD), Collections.emptySet());

    @Test
    public void testLoadBeans() {
        List<String> assignees = lastValuePeriodFilterWidget.loadBeans();
        assertEquals(1, assignees.size());
        assertEquals(LAST_VALUE_PERIOD, assignees.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(String.class, lastValuePeriodFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(LAST_VALUE_PERIOD, lastValuePeriodFilterWidget.getBeanItemCaption(LAST_VALUE_PERIOD));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Collections.singleton(LAST_VALUE_PERIOD)).once();
        replay(filterSaveEvent);
        lastValuePeriodFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        FilterWindow filterWindow = createMock(FilterWindow.class);
        Capture<ValueProvider<String, List<String>>> providerCapture = newCapture();
        expect(Windows.showFilterWindow(eq("Last Value Periods filter"), same(lastValuePeriodFilterWidget),
            capture(providerCapture))).andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Collections.emptySet());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("last-value-period-filter-window");
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Last Value Period");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        lastValuePeriodFilterWidget.showFilterWindow();
        assertEquals(Collections.singletonList(LAST_VALUE_PERIOD), providerCapture.getValue().apply(LAST_VALUE_PERIOD));
        verify(filterWindow, Windows.class);
    }
}
