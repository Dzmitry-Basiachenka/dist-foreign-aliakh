package com.copyright.rup.dist.foreign.ui.common;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;

import com.google.common.collect.Sets;
import com.vaadin.server.Sizeable.Unit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;

/**
 * Verifies {@link ProductFamilyFilterWidget}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 2/19/18
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class ProductFamiliesFilterWidgetTest {

    private static final String FAS_PRODUCT_FAMILY = "FAS";

    private ProductFamilyFilterWidget productFamilyFilterWidget;

    @Before
    public void setUp() {
        productFamilyFilterWidget = new ProductFamilyFilterWidget(Collections.emptyList());
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(String.class, productFamilyFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(FAS_PRODUCT_FAMILY, productFamilyFilterWidget.getBeanItemCaption(FAS_PRODUCT_FAMILY));
    }

    @Test
    public void testGetIdForBean() {
        assertEquals(FAS_PRODUCT_FAMILY, productFamilyFilterWidget.getIdForBean(FAS_PRODUCT_FAMILY));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent<String> filterSaveEvent = createMock(FilterSaveEvent.class);
        expect(filterSaveEvent.getSelectedItemsIds())
            .andReturn(Sets.newHashSet(FAS_PRODUCT_FAMILY))
            .once();
        replay(filterSaveEvent);
        productFamilyFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        FilterWindow<String, String> filterWindow = createMock(FilterWindow.class);
        mockStatic(Windows.class);
        Windows.showFilterWindow("Product Families filter", productFamilyFilterWidget);
        expectLastCall().andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(null);
        expectLastCall().once();
        filterWindow.setWidth(350, Unit.PIXELS);
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn(FAS_PRODUCT_FAMILY).once();
        filterWindow.addStyleName("product-families-filter-window");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        productFamilyFilterWidget.showFilterWindow();
        verify(filterWindow, Windows.class);
    }
}
