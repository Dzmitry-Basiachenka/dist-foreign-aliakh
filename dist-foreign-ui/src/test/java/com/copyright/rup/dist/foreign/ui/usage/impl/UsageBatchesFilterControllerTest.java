package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.ui.common.domain.FakeDataGenerator;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;

import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Verifies {@link UsageBatchesFilterController}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 1/20/17
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
public class UsageBatchesFilterControllerTest {

    private UsageBatchesFilterController controller;

    @Before
    public void setUp() {
        controller = new UsageBatchesFilterController();
    }

    @Test
    @PrepareForTest(FakeDataGenerator.class)
    public void testLoadBeans() {
        mockStatic(FakeDataGenerator.class);
        expect(FakeDataGenerator.getUsageBatches()).andReturn(Collections.emptyList()).once();
        replay(FakeDataGenerator.class);
        assertEquals(Collections.emptyList(), controller.loadBeans());
        verify(FakeDataGenerator.class);
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(UsageBatch.class, controller.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        UsageBatch usageBatch = new UsageBatch();
        String batchName = "Name";
        usageBatch.setName(batchName);
        assertEquals(batchName, controller.getBeanItemCaption(usageBatch));
    }

    @Test
    public void testGetIdForBean() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(RupPersistUtils.generateUuid());
        assertEquals(usageBatch.getId(), controller.getIdForBean(usageBatch));
    }

    @Test
    public void testOnSave() {
        HashSet<String> selectedItemsIds = Sets.newHashSet(RupPersistUtils.generateUuid());
        IUsagesFilterWidget filterWidget = createMock(IUsagesFilterWidget.class);
        controller.setFilterWidget(filterWidget);
        filterWidget.setSelectedUsageBatches(selectedItemsIds);
        expectLastCall().once();
        replay(filterWidget);
        controller.onSave(new FilterSaveEvent<>(filterWidget, selectedItemsIds));
        verify(filterWidget);
    }

    @Test
    @PrepareForTest({Windows.class, VaadinUtils.class})
    public void testShowFilterWindow() {
        mockStatic(Windows.class);
        mockStatic(VaadinUtils.class);
        IUsagesFilterWidget filterWidgetMock = createMock(IUsagesFilterWidget.class);
        UsageFilter filterMock = createMock(UsageFilter.class);
        FilterWindow filterWindowMock = createMock(FilterWindow.class);
        Set<String> selectedItemsIds = Sets.newHashSet(RupPersistUtils.generateUuid());
        controller.setFilterWidget(filterWidgetMock);
        VaadinUtils.addComponentStyle(filterWindowMock, "batches-filter-window");
        expectLastCall().once();
        expect(Windows.showFilterWindow("Batches filter", controller, "name", "rro.accountNumber"))
            .andReturn(filterWindowMock).once();
        expect(filterWidgetMock.getFilter()).andReturn(filterMock).once();
        expect(filterMock.getUsageBatchesIds()).andReturn(selectedItemsIds).once();
        filterWindowMock.setSelectedItemsIds(selectedItemsIds);
        expectLastCall().once();
        replay(filterWidgetMock, filterMock, filterWindowMock, Windows.class, VaadinUtils.class);
        assertSame(filterWindowMock, controller.showFilterWindow());
        verify(filterWidgetMock, filterMock, filterWindowMock, Windows.class, VaadinUtils.class);
    }
}
