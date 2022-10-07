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

import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
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
 * Verifies {@link ReportedTypeOfUseFilterWidget}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 10/07/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class ReportedTypeOfUseFilterWidgetTest {

    private static final String REPORTED_TYPE_OF_USE = "COPY_FOR_MYSELF";

    private final ReportedTypeOfUseFilterWidget widget = new ReportedTypeOfUseFilterWidget(
        () -> Collections.singletonList(REPORTED_TYPE_OF_USE), Collections.emptySet());

    @Test
    public void testLoadBeans() {
        List<String> reportedTypeOfUses = widget.loadBeans();
        assertEquals(1, reportedTypeOfUses.size());
        assertEquals(REPORTED_TYPE_OF_USE, reportedTypeOfUses.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(String.class, widget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(REPORTED_TYPE_OF_USE, widget.getBeanItemCaption(REPORTED_TYPE_OF_USE));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Collections.singleton(REPORTED_TYPE_OF_USE)).once();
        replay(filterSaveEvent);
        widget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        FilterWindow filterWindow = createMock(FilterWindow.class);
        Capture<ValueProvider<String, List<String>>> providerCapture = newCapture();
        expect(Windows.showFilterWindow(eq("Reported Types of Use filter"), same(widget),
            capture(providerCapture))).andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Collections.emptySet());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("reported-type-of-use-filter-window");
        expectLastCall().once();
        filterWindow.setSelectAllButtonVisible();
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Reported Type of Use");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        widget.showFilterWindow();
        assertEquals(Collections.singletonList(REPORTED_TYPE_OF_USE),
            providerCapture.getValue().apply(REPORTED_TYPE_OF_USE));
        verify(filterWindow, Windows.class);
    }
}
