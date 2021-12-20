package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link PeriodFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/2021
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class PeriodFilterWidgetTest {

    private static final Integer PERIOD = 202106;
    private final PeriodFilterWidget periodFilterWidget =
        new PeriodFilterWidget(() -> Collections.singletonList(PERIOD));

    @Test
    public void testLoadBeans() {
        List<Integer> periods = periodFilterWidget.loadBeans();
        assertEquals(1, periods.size());
        assertEquals(PERIOD, periods.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(Integer.class, periodFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(String.valueOf(PERIOD), periodFilterWidget.getBeanItemCaption(PERIOD));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Collections.singleton(PERIOD)).once();
        replay(filterSaveEvent);
        periodFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }
}
