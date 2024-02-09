package com.copyright.rup.dist.foreign.vui.audit.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.vui.audit.impl.fas.FasStatusFilterWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.FilterWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Verifies {@link CommonStatusFilterWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/22/18
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class CommonStatusFilterWidgetTest {

    private static final String SELECTED_ITEMS_IDS = "selectedItemsIds";

    private static final Set<UsageStatusEnum> FAS_FAS2_STATUSES =
        Set.of(UsageStatusEnum.NEW, UsageStatusEnum.WORK_NOT_FOUND, UsageStatusEnum.WORK_RESEARCH,
            UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_NOT_FOUND, UsageStatusEnum.RH_FOUND,
            UsageStatusEnum.SENT_FOR_RA, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.LOCKED, UsageStatusEnum.SENT_TO_LM,
            UsageStatusEnum.PAID, UsageStatusEnum.ARCHIVED, UsageStatusEnum.NTS_WITHDRAWN,
            UsageStatusEnum.TO_BE_DISTRIBUTED);

    @Test
    public void testLoadBeansFas() {
        var widget = new FasStatusFilterWidget();
        assertEquals(FAS_FAS2_STATUSES, widget.loadBeans());
    }

    @Test
    public void testGetBeanClass() {
        var widget = new FasStatusFilterWidget();
        assertEquals(UsageStatusEnum.class, widget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        var widget = new FasStatusFilterWidget();
        assertEquals("ELIGIBLE", widget.getBeanItemCaption(UsageStatusEnum.ELIGIBLE));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testOnComponentEvent() {
        var widget = new FasStatusFilterWidget();
        FilterSaveEvent filterSaveEvent = createMock(FilterSaveEvent.class);
        Set<UsageStatusEnum> values = Set.of(UsageStatusEnum.ELIGIBLE);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(values).once();
        replay(filterSaveEvent);
        widget.onComponentEvent(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testShowFilterWindow() {
        var widget = new FasStatusFilterWidget();
        mockStatic(Windows.class);
        FilterWindow filterWindow = createMock(FilterWindow.class);
        expect(Windows.showFilterWindow("Statuses filter", widget)).andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(EnumSet.noneOf(UsageStatusEnum.class));
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn(Optional.empty()).once();
        filterWindow.setId("status-filter-window");
        expectLastCall().once();
        filterWindow.addClassName("status-filter-window");
        expectLastCall().once();
        replay(Windows.class, filterWindow);
        widget.showFilterWindow();
        verify(Windows.class, filterWindow);
    }

    @Test
    public void testReset() {
        var widget = new FasStatusFilterWidget();
        Whitebox.setInternalState(widget, SELECTED_ITEMS_IDS, EnumSet.of(UsageStatusEnum.ELIGIBLE));
        widget.reset();
        assertEquals(new HashSet<>(), Whitebox.getInternalState(widget, SELECTED_ITEMS_IDS));
    }
}
