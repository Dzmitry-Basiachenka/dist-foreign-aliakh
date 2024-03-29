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

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
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
 * Verifies {@link DetailLicenseeClassFilterWidget}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 06/10/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class DetailLicenseeClassFilterWidgetTest {

    private final DetailLicenseeClass detailLicenseeClass = buildDetailLicenseeClass();
    private final DetailLicenseeClassFilterWidget detailLcFilterWidget = new DetailLicenseeClassFilterWidget(() ->
        List.of(detailLicenseeClass), Set.of());

    @Test
    public void testLoadBeans() {
        List<DetailLicenseeClass> detailLicenseeClasses = detailLcFilterWidget.loadBeans();
        assertEquals(1, detailLicenseeClasses.size());
        assertEquals(detailLicenseeClass, detailLicenseeClasses.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(DetailLicenseeClass.class, detailLcFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals("26 - Law Firms", detailLcFilterWidget.getBeanItemCaption(detailLicenseeClass));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Set.of(detailLicenseeClass)).once();
        replay(filterSaveEvent);
        detailLcFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        FilterWindow filterWindow = createMock(FilterWindow.class);
        Capture<ValueProvider<DetailLicenseeClass, List<String>>> providerCapture = newCapture();
        expect(Windows.showFilterWindow(eq("Detail Licensee Classes filter"), same(detailLcFilterWidget),
            capture(providerCapture))).andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Set.of());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("detail-licensee-class-filter-window");
        expectLastCall().once();
        filterWindow.setSelectAllButtonVisible();
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Detail Licensee Class Name/Id");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        detailLcFilterWidget.showFilterWindow();
        assertEquals(List.of("26", "Law Firms"), providerCapture.getValue().apply(detailLicenseeClass));
        verify(filterWindow, Windows.class);
    }

    private DetailLicenseeClass buildDetailLicenseeClass() {
        DetailLicenseeClass licenseeClass = new DetailLicenseeClass();
        licenseeClass.setId(26);
        licenseeClass.setDescription("Law Firms");
        return licenseeClass;
    }
}
