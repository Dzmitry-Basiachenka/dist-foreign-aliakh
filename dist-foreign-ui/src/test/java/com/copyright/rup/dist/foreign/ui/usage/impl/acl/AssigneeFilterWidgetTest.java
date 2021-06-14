package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

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

import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;
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
 * Verifies {@link AssigneeFilterWidget}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 06/10/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class AssigneeFilterWidgetTest {

    private static final String ASSIGNEE = "user@copyright.com";
    private final AssigneeFilterWidget assigneeFilterWidget =
        new AssigneeFilterWidget(() -> Collections.singletonList(ASSIGNEE));

    @Test
    public void testLoadBeans() {
        List<String> assignees = assigneeFilterWidget.loadBeans();
        assertEquals(1, assignees.size());
        assertEquals(ASSIGNEE, assignees.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(String.class, assigneeFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(ASSIGNEE, assigneeFilterWidget.getBeanItemCaption(ASSIGNEE));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Collections.singleton(ASSIGNEE)).once();
        replay(filterSaveEvent);
        assigneeFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        FilterWindow filterWindow = createMock(FilterWindow.class);
        mockStatic(Windows.class);
        Capture<ValueProvider<String, List<String>>> providerCapture = newCapture();
        Windows.showFilterWindow(eq("Assignees filter"), same(assigneeFilterWidget), capture(providerCapture));
        expectLastCall().andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(Collections.emptySet());
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("assignee-filter-window");
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Assignee");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        assigneeFilterWidget.showFilterWindow();
        assertEquals(Collections.singletonList(ASSIGNEE), providerCapture.getValue().apply(ASSIGNEE));
        verify(filterWindow, Windows.class);
    }
}
