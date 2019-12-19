package com.copyright.rup.dist.foreign.ui.audit.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Verifies {@link StatusFilterWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/22/18
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class StatusFilterWidgetTest {

    private static final String SELECTED_ITEMS_IDS = "selectedItemsIds";

    private static final Set<UsageStatusEnum> FAS_FAS2_STATUSES =
        Sets.newHashSet(UsageStatusEnum.NEW, UsageStatusEnum.WORK_NOT_FOUND, UsageStatusEnum.WORK_RESEARCH,
            UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_NOT_FOUND, UsageStatusEnum.RH_FOUND,
            UsageStatusEnum.SENT_FOR_RA, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.LOCKED, UsageStatusEnum.SENT_TO_LM,
            UsageStatusEnum.PAID, UsageStatusEnum.ARCHIVED);

    private static final Set<UsageStatusEnum> NTS_STATUSES =
        Sets.newHashSet(UsageStatusEnum.NTS_WITHDRAWN, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_FOUND,
            UsageStatusEnum.UNCLASSIFIED, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.TO_BE_DISTRIBUTED,
            UsageStatusEnum.NTS_EXCLUDED, UsageStatusEnum.NON_STM_RH, UsageStatusEnum.US_TAX_COUNTRY,
            UsageStatusEnum.LOCKED, UsageStatusEnum.SENT_TO_LM, UsageStatusEnum.PAID, UsageStatusEnum.ARCHIVED);

    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String NTS_PRODUCT_FAMILY = "NTS";

    @Test
    public void testLoadBeansFas() {
        StatusFilterWidget widget = new StatusFilterWidget(FAS_PRODUCT_FAMILY);
        assertEquals(FAS_FAS2_STATUSES, widget.loadBeans());
    }

    @Test
    public void testLoadBeansNts() {
        StatusFilterWidget widget = new StatusFilterWidget(NTS_PRODUCT_FAMILY);
        assertEquals(NTS_STATUSES, widget.loadBeans());
    }

    @Test
    public void testGetBeanClass() {
        StatusFilterWidget widget = new StatusFilterWidget(FAS_PRODUCT_FAMILY);
        assertEquals(UsageStatusEnum.class, widget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        StatusFilterWidget widget = new StatusFilterWidget(FAS_PRODUCT_FAMILY);
        assertEquals("ELIGIBLE", widget.getBeanItemCaption(UsageStatusEnum.ELIGIBLE));
    }

    @Test
    public void testOnSave() {
        StatusFilterWidget widget = new StatusFilterWidget(FAS_PRODUCT_FAMILY);
        FilterSaveEvent event = createMock(FilterSaveEvent.class);
        Set<UsageStatusEnum> values = Sets.newHashSet(UsageStatusEnum.ELIGIBLE);
        expect(event.getSelectedItemsIds()).andReturn(values).once();
        replay(event);
        widget.onSave(event);
        assertEquals(values, Whitebox.getInternalState(widget, "selectedItemsIds"));
        verify(event);
    }

    @Test
    public void testShowFilterWindow() {
        StatusFilterWidget widget = new StatusFilterWidget(FAS_PRODUCT_FAMILY);
        mockStatic(Windows.class);
        FilterWindow filterWindow = createMock(FilterWindow.class);
        expect(Windows.showFilterWindow("Status filter", widget)).andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(EnumSet.noneOf(UsageStatusEnum.class));
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn(null).once();
        filterWindow.setId("status-filter-window");
        expectLastCall().once();
        filterWindow.addStyleName("status-filter-window");
        expectLastCall().once();
        replay(Windows.class, filterWindow);
        widget.showFilterWindow();
        verify(Windows.class, filterWindow);
    }

    @Test
    public void testReset() {
        StatusFilterWidget widget = new StatusFilterWidget(FAS_PRODUCT_FAMILY);
        Whitebox.setInternalState(widget, SELECTED_ITEMS_IDS, EnumSet.of(UsageStatusEnum.ELIGIBLE));
        widget.reset();
        assertEquals(new HashSet<>(), Whitebox.getInternalState(widget, SELECTED_ITEMS_IDS));
    }
}
