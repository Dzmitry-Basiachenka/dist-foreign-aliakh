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
 * Verifies {@link TypeOfUseFilterWidget}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 06/10/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class TypeOfUseFilterWidgetTest {

    private static final String REPORTED_TYPE_OF_USE = "COPY_FOR_MYSELF";
    private final TypeOfUseFilterWidget typeOfUseFilterWidget =
        new TypeOfUseFilterWidget(() -> Collections.singletonList(REPORTED_TYPE_OF_USE), Collections.emptySet());

    @Test
    public void testLoadBeans() {
        List<String> typeOfUses = typeOfUseFilterWidget.loadBeans();
        assertEquals(1, typeOfUses.size());
        assertEquals(REPORTED_TYPE_OF_USE, typeOfUses.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(String.class, typeOfUseFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(REPORTED_TYPE_OF_USE, typeOfUseFilterWidget.getBeanItemCaption(REPORTED_TYPE_OF_USE));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Collections.singleton(REPORTED_TYPE_OF_USE)).once();
        replay(filterSaveEvent);
        typeOfUseFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        FilterWindow filterWindow = createMock(FilterWindow.class);
        Capture<ValueProvider<String, List<String>>> providerCapture = newCapture();
        expect(Windows.showFilterWindow(eq("Types of Use filter"), same(typeOfUseFilterWidget),
            capture(providerCapture))).andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Collections.emptySet());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("type-of-use-filter-window");
        expectLastCall().once();
        filterWindow.setSelectAllButtonVisible();
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Type of Use");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        typeOfUseFilterWidget.showFilterWindow();
        assertEquals(Collections.singletonList(REPORTED_TYPE_OF_USE),
            providerCapture.getValue().apply(REPORTED_TYPE_OF_USE));
        verify(filterWindow, Windows.class);
    }
}
