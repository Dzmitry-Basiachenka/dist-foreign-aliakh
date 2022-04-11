package com.copyright.rup.dist.foreign.ui.audit.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.audit.impl.aacl.AaclStatusFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.impl.fas.FasStatusFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.impl.nts.NtsStatusFilterWidget;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.FilterSaveEvent;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
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
public class CommonStatusFilterWidgetTest {

    private static final String SELECTED_ITEMS_IDS = "selectedItemsIds";

    private static final Set<UsageStatusEnum> FAS_FAS2_STATUSES =
        Sets.newHashSet(UsageStatusEnum.NEW, UsageStatusEnum.WORK_NOT_FOUND, UsageStatusEnum.WORK_RESEARCH,
            UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_NOT_FOUND, UsageStatusEnum.RH_FOUND,
            UsageStatusEnum.SENT_FOR_RA, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.LOCKED, UsageStatusEnum.SENT_TO_LM,
            UsageStatusEnum.PAID, UsageStatusEnum.ARCHIVED, UsageStatusEnum.NTS_WITHDRAWN,
            UsageStatusEnum.TO_BE_DISTRIBUTED);

    private static final Set<UsageStatusEnum> NTS_STATUSES =
        Sets.newHashSet(UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_FOUND,
            UsageStatusEnum.UNCLASSIFIED, UsageStatusEnum.ELIGIBLE, UsageStatusEnum.SCENARIO_EXCLUDED,
            UsageStatusEnum.NON_STM_RH, UsageStatusEnum.US_TAX_COUNTRY, UsageStatusEnum.LOCKED,
            UsageStatusEnum.SENT_TO_LM, UsageStatusEnum.PAID, UsageStatusEnum.ARCHIVED);

    private static final Set<UsageStatusEnum> AACL_STATUSES =
        Sets.newHashSet(UsageStatusEnum.NEW, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.WORK_NOT_FOUND,
            UsageStatusEnum.WORK_RESEARCH, UsageStatusEnum.RH_FOUND, UsageStatusEnum.ELIGIBLE,
            UsageStatusEnum.SCENARIO_EXCLUDED, UsageStatusEnum.LOCKED, UsageStatusEnum.SENT_TO_LM, UsageStatusEnum.PAID,
            UsageStatusEnum.ARCHIVED);

    @Test
    public void testLoadBeansFas() {
        CommonStatusFilterWidget widget = new FasStatusFilterWidget();
        assertEquals(FAS_FAS2_STATUSES, widget.loadBeans());
    }

    @Test
    public void testLoadBeansNts() {
        CommonStatusFilterWidget widget = new NtsStatusFilterWidget();
        assertEquals(NTS_STATUSES, widget.loadBeans());
    }

    @Test
    public void testLoadBeansAacl() {
        CommonStatusFilterWidget widget = new AaclStatusFilterWidget();
        assertEquals(AACL_STATUSES, widget.loadBeans());
    }

    @Test
    public void testGetBeanClass() {
        CommonStatusFilterWidget widget = new FasStatusFilterWidget();
        assertEquals(UsageStatusEnum.class, widget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        CommonStatusFilterWidget widget = new FasStatusFilterWidget();
        assertEquals("ELIGIBLE", widget.getBeanItemCaption(UsageStatusEnum.ELIGIBLE));
    }

    @Test
    public void testOnSave() {
        CommonStatusFilterWidget widget = new FasStatusFilterWidget();
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
        CommonStatusFilterWidget widget = new FasStatusFilterWidget();
        mockStatic(Windows.class);
        FilterWindow filterWindow = createMock(FilterWindow.class);
        expect(Windows.showFilterWindow("Statuses filter", widget)).andReturn(filterWindow).once();
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
        CommonStatusFilterWidget widget = new FasStatusFilterWidget();
        Whitebox.setInternalState(widget, SELECTED_ITEMS_IDS, EnumSet.of(UsageStatusEnum.ELIGIBLE));
        widget.reset();
        assertEquals(new HashSet<>(), Whitebox.getInternalState(widget, SELECTED_ITEMS_IDS));
    }
}
