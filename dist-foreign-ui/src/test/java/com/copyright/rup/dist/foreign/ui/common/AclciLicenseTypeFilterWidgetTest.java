package com.copyright.rup.dist.foreign.ui.common;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclciLicenseTypeEnum;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.ImmutableSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.Set;

/**
 * Verifies {@link AclciLicenseTypeFilterWidget}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 12/14/2022
 *
 * @author Mikita Maistrenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class AclciLicenseTypeFilterWidgetTest {


    private static final Set<AclciLicenseTypeEnum> LICENSE_TYPES =
        ImmutableSet.of(AclciLicenseTypeEnum.CURR_REPUB_HE, AclciLicenseTypeEnum.CURR_REPUB_K12,
            AclciLicenseTypeEnum.CURR_REUSE_K12, AclciLicenseTypeEnum.CURR_SHARE_K12);
    private final AclciLicenseTypeFilterWidget licenseTypeFilterWidget =
        new AclciLicenseTypeFilterWidget(LICENSE_TYPES, Collections.emptySet());

    @Test
    public void testLoadBeans() {
        Set<AclciLicenseTypeEnum> licenseTypes = licenseTypeFilterWidget.loadBeans();
        assertEquals(4, licenseTypes.size());
        assertEquals(LICENSE_TYPES, licenseTypes);
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(AclciLicenseTypeEnum.class, licenseTypeFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals("CURR_REPUB_HE", licenseTypeFilterWidget.getBeanItemCaption(AclciLicenseTypeEnum.CURR_REPUB_HE));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(LICENSE_TYPES).once();
        replay(filterSaveEvent);
        licenseTypeFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        FilterWindow filterWindow = createMock(FilterWindow.class);
        expect(Windows.showFilterWindow(eq("License Types filter"), same(licenseTypeFilterWidget)))
            .andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Collections.emptySet());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("aclci-license-type-filter-window");
        expectLastCall().once();
        filterWindow.setSelectAllButtonVisible();
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        licenseTypeFilterWidget.showFilterWindow();
        verify(filterWindow, Windows.class);
    }
}
