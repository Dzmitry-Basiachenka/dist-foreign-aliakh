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
public class UdmProxyValuePubTypeCodeFilterWidgetTest {

    private IUdmProxyValueFilterController filterController;
    private UdmProxyValuePubTypeCodeFilterWidget pubTypeCodeFilterWidget;

    @Before
    public void setUp() {
        filterController = createMock(IUdmProxyValueFilterController.class);
        pubTypeCodeFilterWidget = new UdmProxyValuePubTypeCodeFilterWidget(filterController);
    }

    @Test
    public void testLoadBeans() {
        List<String> pubTypeCodes = List.of("BK", "NP");
        expect(filterController.getPublicationTypeCodes()).andReturn(pubTypeCodes).once();
        replay(filterController);
        assertEquals(pubTypeCodes, pubTypeCodeFilterWidget.loadBeans());
        verify(filterController);
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(String.class, pubTypeCodeFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals("BK", pubTypeCodeFilterWidget.getBeanItemCaption("BK"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testOnSave() {
        FilterSaveEvent<String> filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(Set.of("NP")).once();
        replay(filterSaveEvent);
        pubTypeCodeFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }
}
