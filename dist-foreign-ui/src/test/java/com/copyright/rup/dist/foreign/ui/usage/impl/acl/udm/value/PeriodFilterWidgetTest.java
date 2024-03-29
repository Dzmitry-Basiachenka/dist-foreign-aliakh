package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.ValueProvider;

import org.easymock.Capture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link PeriodFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/2021
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class PeriodFilterWidgetTest {

    private static final Integer PERIOD = 202106;
    private static final List<Integer> PERIODS = List.of(202212, PERIOD, 202006, 201512);

    private final PeriodFilterWidget periodFilterWidget = new PeriodFilterWidget(() -> PERIODS);

    @Test
    public void testLoadBeans() {
        List<Integer> periods = periodFilterWidget.loadBeans();
        assertEquals(4, periods.size());
        assertSame(PERIODS, periods);
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(Integer.class, periodFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(String.valueOf(PERIOD), periodFilterWidget.getBeanItemCaption(PERIOD));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Set.of(PERIOD)).once();
        replay(filterSaveEvent);
        periodFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        FilterWindow filterWindow = createMock(FilterWindow.class);
        Capture<ValueProvider<Integer, List<String>>> providerCapture = newCapture();
        expect(Windows.showFilterWindow(eq("Periods filter"), same(periodFilterWidget),
            capture(providerCapture))).andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Set.of());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("udm-values-periods-filter-window");
        expectLastCall().once();
        filterWindow.setSelectAllButtonVisible();
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Period");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        periodFilterWidget.showFilterWindow();
        assertEquals(List.of(String.valueOf(PERIOD)), providerCapture.getValue().apply(PERIOD));
        verify(filterWindow, Windows.class);
    }

    @Test
    public void testConstructorWithSelectedItems() {
        Set<Integer> selectedPeriods = Set.of(PERIOD);
        PeriodFilterWidget widget = new PeriodFilterWidget(() -> PERIODS, selectedPeriods);
        assertEquals(selectedPeriods, widget.getSelectedItemsIds());
        assertEquals("Periods", widget.getComponent(1).getCaption());
    }
}
