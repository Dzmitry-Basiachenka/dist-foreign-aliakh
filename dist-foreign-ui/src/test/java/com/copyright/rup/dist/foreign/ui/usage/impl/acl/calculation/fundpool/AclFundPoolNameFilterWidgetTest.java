package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.ValueProvider;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.Set;
/**
 * Verifies {@link AclFundPoolNameFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/11/2022
 *
 * @author Mikita Maistrenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class AclFundPoolNameFilterWidgetTest {

    private static final String FUND_POOL_NAME = "ACL Fund Pool 2022";

    private AclFundPoolNameFilterWidget aclFundPoolNameFilterWidget;

    @Before
    public void setUp() {
        aclFundPoolNameFilterWidget = new AclFundPoolNameFilterWidget(Collections::emptyList);
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(AclFundPool.class, aclFundPoolNameFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(FUND_POOL_NAME, aclFundPoolNameFilterWidget.getBeanItemCaption(buildAclFundPool()));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent<AclFundPool> filterSaveEvent = createMock(FilterSaveEvent.class);
        Set<AclFundPool> fundPools = Set.of(new AclFundPool());
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(fundPools).once();
        replay(filterSaveEvent);
        aclFundPoolNameFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
        assertEquals(fundPools, Whitebox.getInternalState(aclFundPoolNameFilterWidget, "selectedItemsIds"));
    }

    @Test
    public void testShowFilterWindow() {
        FilterWindow filterWindow = createMock(FilterWindow.class);
        mockStatic(Windows.class);
        Capture<ValueProvider<AclFundPool, List<String>>> providerCapture = newCapture();
        expect(Windows.showFilterWindow(eq("Fund Pool Names filter"), same(aclFundPoolNameFilterWidget),
            capture(providerCapture))).andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Set.of());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("acl-fund-pool-name-filter-window");
        expectLastCall().once();
        filterWindow.setSelectAllButtonVisible();
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Fund Pool Name");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        aclFundPoolNameFilterWidget.showFilterWindow();
        assertEquals(List.of(FUND_POOL_NAME), providerCapture.getValue().apply(buildAclFundPool()));
        verify(filterWindow, Windows.class);
    }

    private AclFundPool buildAclFundPool() {
        AclFundPool aclFundPool = new AclFundPool();
        aclFundPool.setName(FUND_POOL_NAME);
        aclFundPool.setPeriod(202206);
        aclFundPool.setLicenseType("ACL");
        aclFundPool.setManualUploadFlag(true);
        return aclFundPool;
    }
}
