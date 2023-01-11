package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

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

import com.copyright.rup.dist.foreign.domain.AclGrantSet;
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

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link AclGrantSetFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 02/01/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class AclGrantSetFilterWidgetTest {

    private static final String ACL_GRANT_SET_UID = "d3a93635-38d2-479c-b50d-6cbc8d84b924";
    private static final String ACL_GRANT_SET_NAME = "ACL Grant Set 2021";

    private AclGrantSetFilterWidget aclGrantSetFilterWidget;

    @Before
    public void setUp() {
        aclGrantSetFilterWidget = new AclGrantSetFilterWidget(List::of);
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(AclGrantSet.class, aclGrantSetFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(ACL_GRANT_SET_NAME, aclGrantSetFilterWidget.getBeanItemCaption(buildAclGrantSet()));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent<AclGrantSet> filterSaveEvent = createMock(FilterSaveEvent.class);
        Set<AclGrantSet> grantSets = Set.of(new AclGrantSet());
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(grantSets).once();
        replay(filterSaveEvent);
        aclGrantSetFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
        assertEquals(grantSets, Whitebox.getInternalState(aclGrantSetFilterWidget, "selectedItemsIds"));
    }

    @Test
    public void testShowFilterWindow() {
        FilterWindow filterWindow = createMock(FilterWindow.class);
        mockStatic(Windows.class);
        Capture<ValueProvider<AclGrantSet, List<String>>> providerCapture = newCapture();
        expect(Windows.showFilterWindow(eq("Grant Sets filter"), same(aclGrantSetFilterWidget),
            capture(providerCapture))).andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Set.of());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("acl-grant-sets-filter-window");
        expectLastCall().once();
        filterWindow.setSelectAllButtonVisible();
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Grant Set Name");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        aclGrantSetFilterWidget.showFilterWindow();
        assertEquals(List.of(ACL_GRANT_SET_NAME), providerCapture.getValue().apply(buildAclGrantSet()));
        verify(filterWindow, Windows.class);
    }

    private AclGrantSet buildAclGrantSet() {
        AclGrantSet grantSet = new AclGrantSet();
        grantSet.setId(ACL_GRANT_SET_UID);
        grantSet.setName(ACL_GRANT_SET_NAME);
        return grantSet;
    }
}
