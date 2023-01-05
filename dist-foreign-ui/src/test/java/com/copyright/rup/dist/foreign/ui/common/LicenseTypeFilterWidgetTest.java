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
 * Verifies {@link LicenseTypeFilterWidget}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 03/10/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class LicenseTypeFilterWidgetTest {

    private static final String LICENSE_TYPE = "ACL";
    private final LicenseTypeFilterWidget licenseTypeFilterWidget =
        new LicenseTypeFilterWidget(() -> List.of(LICENSE_TYPE), Collections.emptySet());

    @Test
    public void testLoadBeans() {
        List<String> licenseTypes = licenseTypeFilterWidget.loadBeans();
        assertEquals(1, licenseTypes.size());
        assertEquals(LICENSE_TYPE, licenseTypes.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(String.class, licenseTypeFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(LICENSE_TYPE, licenseTypeFilterWidget.getBeanItemCaption(LICENSE_TYPE));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Collections.singleton(LICENSE_TYPE)).once();
        replay(filterSaveEvent);
        licenseTypeFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        FilterWindow filterWindow = createMock(FilterWindow.class);
        Capture<ValueProvider<String, List<String>>> providerCapture = newCapture();
        expect(Windows.showFilterWindow(eq("License Types filter"), same(licenseTypeFilterWidget),
            capture(providerCapture))).andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Collections.emptySet());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("license-type-filter-window");
        expectLastCall().once();
        filterWindow.setSelectAllButtonVisible();
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter License Type");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        licenseTypeFilterWidget.showFilterWindow();
        assertEquals(List.of(LICENSE_TYPE), providerCapture.getValue().apply(LICENSE_TYPE));
        verify(filterWindow, Windows.class);
    }
}
