package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

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
 * Verifies {@link LastValuePeriodFilterWidget}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 09/23/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class LastValuePeriodFilterWidgetTest {

    private static final String LAST_VALUE_PERIOD = "202106";
    private static final String IS_NULL = "IS_NULL";
    private static final String IS_NOT_NULL = "IS_NOT_NULL";

    private final LastValuePeriodFilterWidget lastValuePeriodFilterWidget =
        new LastValuePeriodFilterWidget(() -> Collections.singletonList(LAST_VALUE_PERIOD), Collections.emptySet());

    @Test
    public void testLoadBeans() {
        List<String> assignees = lastValuePeriodFilterWidget.loadBeans();
        assertEquals(3, assignees.size());
        assertEquals(LAST_VALUE_PERIOD, assignees.get(0));
        assertEquals(IS_NULL, assignees.get(1));
        assertEquals(IS_NOT_NULL, assignees.get(2));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(String.class, lastValuePeriodFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(LAST_VALUE_PERIOD, lastValuePeriodFilterWidget.getBeanItemCaption(LAST_VALUE_PERIOD));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent<String> filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Collections.singleton(LAST_VALUE_PERIOD)).once();
        replay(filterSaveEvent);
        lastValuePeriodFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }
}
