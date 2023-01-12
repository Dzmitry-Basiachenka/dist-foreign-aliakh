package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueFilterController;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link UdmProxyValuePeriodFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/27/21
 *
 * @author Uladzislau Shalamitski
 */
public class UdmProxyValuePeriodFilterWidgetTest {

    private static final Integer PERIOD = 202106;
    private IUdmProxyValueFilterController filterController;
    private UdmProxyValuePeriodFilterWidget valuePeriodFilterWidget;

    @Before
    public void setUp() {
        filterController = createMock(IUdmProxyValueFilterController.class);
        valuePeriodFilterWidget = new UdmProxyValuePeriodFilterWidget(filterController);
    }

    @Test
    public void testLoadBeans() {
        List<Integer> periods = List.of(201212, 202012);
        expect(filterController.getPeriods()).andReturn(periods).once();
        replay(filterController);
        assertEquals(periods, valuePeriodFilterWidget.loadBeans());
        verify(filterController);
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(Integer.class, valuePeriodFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(String.valueOf(PERIOD), valuePeriodFilterWidget.getBeanItemCaption(PERIOD));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testOnSave() {
        FilterSaveEvent<Integer> filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Set.of(PERIOD)).once();
        replay(filterSaveEvent);
        valuePeriodFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }
}
