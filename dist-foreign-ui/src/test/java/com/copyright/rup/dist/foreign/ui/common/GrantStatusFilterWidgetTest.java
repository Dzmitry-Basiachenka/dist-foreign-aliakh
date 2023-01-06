package com.copyright.rup.dist.foreign.ui.common;

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
 * Verifies {@link GrantStatusFilterWidget}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 03/10/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class GrantStatusFilterWidgetTest {

    private static final String GRANT_STATUS = "GRANT";
    private final GrantStatusFilterWidget grantStatusFilterWidget =
        new GrantStatusFilterWidget(() -> List.of(GRANT_STATUS), Collections.emptySet());

    @Test
    public void testLoadBeans() {
        List<String> grantStatuses = grantStatusFilterWidget.loadBeans();
        assertEquals(1, grantStatuses.size());
        assertEquals(GRANT_STATUS, grantStatuses.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(String.class, grantStatusFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(GRANT_STATUS, grantStatusFilterWidget.getBeanItemCaption(GRANT_STATUS));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Collections.singleton(GRANT_STATUS)).once();
        replay(filterSaveEvent);
        grantStatusFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        FilterWindow filterWindow = createMock(FilterWindow.class);
        Capture<ValueProvider<String, List<String>>> providerCapture = newCapture();
        expect(Windows.showFilterWindow(eq("Grant Statuses filter"), same(grantStatusFilterWidget),
            capture(providerCapture))).andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Collections.emptySet());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("grant-status-filter-window");
        expectLastCall().once();
        filterWindow.setSelectAllButtonVisible();
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Grant Status");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        grantStatusFilterWidget.showFilterWindow();
        assertEquals(List.of(GRANT_STATUS), providerCapture.getValue().apply(GRANT_STATUS));
        verify(filterWindow, Windows.class);
    }
}
