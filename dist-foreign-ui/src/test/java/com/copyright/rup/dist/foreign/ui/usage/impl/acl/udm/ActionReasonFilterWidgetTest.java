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

import com.copyright.rup.dist.foreign.domain.UdmActionReason;
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
 * Verifies {@link ActionReasonFilterWidget}.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 05/24/2023
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class ActionReasonFilterWidgetTest {

    private final UdmActionReason actionReason = buildActionReason();
    private final ActionReasonFilterWidget actionReasonFilterWidget =
        new ActionReasonFilterWidget(() -> List.of(actionReason), Set.of());

    @Test
    public void testLoadBeans() {
        List<UdmActionReason> actionReasons = actionReasonFilterWidget.loadBeans();
        assertEquals(1, actionReasons.size());
        assertEquals(buildActionReason(), actionReasons.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(UdmActionReason.class, actionReasonFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals("Arbitrary RFA search result order", actionReasonFilterWidget.getBeanItemCaption(actionReason));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent<UdmActionReason> filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Set.of(actionReason)).once();
        replay(filterSaveEvent);
        actionReasonFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        FilterWindow filterWindow = createMock(FilterWindow.class);
        Capture<ValueProvider<UdmActionReason, List<String>>> providerCapture = newCapture();
        expect(Windows.showFilterWindow(eq("Action Reasons filter"), same(actionReasonFilterWidget),
            capture(providerCapture))).andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Set.of());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("action-reason-filter-window");
        expectLastCall().once();
        filterWindow.setSelectAllButtonVisible();
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Action Reason");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        actionReasonFilterWidget.showFilterWindow();
        assertEquals(List.of("Arbitrary RFA search result order"), providerCapture.getValue().apply(actionReason));
        verify(filterWindow, Windows.class);
    }

    private UdmActionReason buildActionReason() {
        UdmActionReason reason = new UdmActionReason();
        reason.setId("97fd8093-7f36-4a09-99f1-1bfe36a5c3f4");
        reason.setReason("Arbitrary RFA search result order");
        return reason;
    }
}
