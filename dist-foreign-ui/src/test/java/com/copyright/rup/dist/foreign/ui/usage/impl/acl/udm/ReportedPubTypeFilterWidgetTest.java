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

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link ReportedPubTypeFilterWidget}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 06/10/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class ReportedPubTypeFilterWidgetTest {

    private static final String REPORTED_PUB_TYPE = "Journal";
    private final ReportedPubTypeFilterWidget reportedPubTypeFilterWidget =
        new ReportedPubTypeFilterWidget(() -> List.of(REPORTED_PUB_TYPE), Set.of());

    @Test
    public void testLoadBeans() {
        List<String> pubTypes = reportedPubTypeFilterWidget.loadBeans();
        assertEquals(1, pubTypes.size());
        assertEquals(REPORTED_PUB_TYPE, pubTypes.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(String.class, reportedPubTypeFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(REPORTED_PUB_TYPE, reportedPubTypeFilterWidget.getBeanItemCaption(REPORTED_PUB_TYPE));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Set.of(REPORTED_PUB_TYPE)).once();
        replay(filterSaveEvent);
        reportedPubTypeFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        FilterWindow filterWindow = createMock(FilterWindow.class);
        Capture<ValueProvider<String, List<String>>> providerCapture = newCapture();
        expect(Windows.showFilterWindow(eq("Reported Pub Types filter"), same(reportedPubTypeFilterWidget),
            capture(providerCapture))).andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Set.of());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("reported-pub-type-filter-window");
        expectLastCall().once();
        filterWindow.setSelectAllButtonVisible();
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Reported Pub Type");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        reportedPubTypeFilterWidget.showFilterWindow();
        assertEquals(List.of(REPORTED_PUB_TYPE), providerCapture.getValue().apply(REPORTED_PUB_TYPE));
        verify(filterWindow, Windows.class);
    }
}
