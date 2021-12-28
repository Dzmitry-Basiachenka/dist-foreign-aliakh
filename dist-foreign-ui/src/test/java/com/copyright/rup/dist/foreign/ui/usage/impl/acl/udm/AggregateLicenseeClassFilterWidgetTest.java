package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

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

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.ValueProvider;

import org.easymock.Capture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AggregateLicenseeClassFilterWidget}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 09/01/2021
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class AggregateLicenseeClassFilterWidgetTest {

    private final AggregateLicenseeClass aggregateLicenseeClass = buildAggregateLicenseeClass();
    private final AggregateLicenseeClassFilterWidget aggregateLicenseeClassFilterWidget =
        new AggregateLicenseeClassFilterWidget(() -> Collections.singletonList(aggregateLicenseeClass),
            Collections.emptySet());

    @Test
    public void testLoadBeans() {
        List<AggregateLicenseeClass> aggregateLicenseeClasses = aggregateLicenseeClassFilterWidget.loadBeans();
        assertEquals(1, aggregateLicenseeClasses.size());
        assertEquals(aggregateLicenseeClass, aggregateLicenseeClasses.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(AggregateLicenseeClass.class, aggregateLicenseeClassFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals("26 - Law Firms", aggregateLicenseeClassFilterWidget.getBeanItemCaption(aggregateLicenseeClass));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testOnSave() {
        FilterSaveEvent<AggregateLicenseeClass> filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Collections.singleton(aggregateLicenseeClass)).once();
        replay(filterSaveEvent);
        aggregateLicenseeClassFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        FilterWindow<AggregateLicenseeClass> filterWindow = createMock(FilterWindow.class);
        Capture<ValueProvider<AggregateLicenseeClass, List<String>>> providerCapture = newCapture();
        expect(Windows.showFilterWindow(eq("Aggregate Licensee Classes filter"),
            same(aggregateLicenseeClassFilterWidget), capture(providerCapture))).andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Collections.emptySet());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("baseline-aggregate-licensee-class-filter-window");
        expectLastCall().once();
        filterWindow.setSelectAllButtonVisible();
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Aggregate Licensee Class Name/Id");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        aggregateLicenseeClassFilterWidget.showFilterWindow();
        assertEquals(Arrays.asList("26", "Law Firms"), providerCapture.getValue().apply(aggregateLicenseeClass));
        verify(filterWindow, Windows.class);
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass() {
        AggregateLicenseeClass licenseeClass = new AggregateLicenseeClass();
        licenseeClass.setId(26);
        licenseeClass.setDescription("Law Firms");
        return licenseeClass;
    }
}
