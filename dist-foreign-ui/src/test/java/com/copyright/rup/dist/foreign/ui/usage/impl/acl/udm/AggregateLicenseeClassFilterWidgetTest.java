package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AggregateLicenseeClassFilterWidget}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 09/01/2021
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class AggregateLicenseeClassFilterWidgetTest {

    private final AggregateLicenseeClass aggregateLicenseeClass = buildAggregateLicenseeClass();
    private final AggregateLicenseeClassFilterWidget aggregateLicenseeClassFilterWidget =
        new AggregateLicenseeClassFilterWidget(() -> Collections.singletonList(aggregateLicenseeClass),
            Collections.emptySet());

    @Test
    public void testLoadBeans() {
        List<AggregateLicenseeClass> aggregateLicenseeClasses = aggregateLicenseeClassFilterWidget.loadBeans();
        assertEquals(1, aggregateLicenseeClasses.size());
        assertEquals(aggregateLicenseeClass, aggregateLicenseeClasses.get(0));
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(AggregateLicenseeClass.class, aggregateLicenseeClassFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals("26 - Law Firms", aggregateLicenseeClassFilterWidget.getBeanItemCaption(aggregateLicenseeClass));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testOnSave() {
        FilterSaveEvent<AggregateLicenseeClass> filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Collections.singleton(aggregateLicenseeClass)).once();
        replay(filterSaveEvent);
        aggregateLicenseeClassFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass() {
        AggregateLicenseeClass licenseeClass = new AggregateLicenseeClass();
        licenseeClass.setId(26);
        licenseeClass.setDescription("Law Firms");
        return licenseeClass;
    }
}
