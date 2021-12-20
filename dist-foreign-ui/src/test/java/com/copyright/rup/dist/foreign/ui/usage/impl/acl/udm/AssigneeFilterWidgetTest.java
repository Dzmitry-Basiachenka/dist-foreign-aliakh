package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.window.Windows;

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
        new AssigneeFilterWidget(() -> Collections.singletonList(ASSIGNEE), Collections.emptySet());

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
}
