package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.FilterSaveEvent;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link UsageBatchFilterWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 2/15/17
 *
 * @author Mikalai Bezmen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class UsageBatchFilterWidgetTest {

    private static final String USAGE_BATCH_NAME = "Usage batch";
    private static final String USAGE_BATCH_ID = "Usage batch id";

    private IUsagesFilterController controller;
    private UsageBatchFilterWidget usageBatchFilterWidget;

    @Before
    public void setUp() {
        controller = EasyMock.createMock(IUsagesFilterController.class);
        usageBatchFilterWidget = new UsageBatchFilterWidget(controller);
    }

    @Test
    public void testLoadBeans() {
        UsageBatch usageBatch = buildUsageBatch();
        expect(controller.getUsageBatches()).andReturn(Lists.newArrayList(usageBatch)).once();
        replay(controller);
        List<UsageBatch> usageBatches = usageBatchFilterWidget.loadBeans();
        assertEquals(1, usageBatches.size());
        assertEquals(usageBatch, usageBatches.get(0));
        verify(controller);
    }

    @Test
    public void testGetBeanClass() {
        assertEquals(UsageBatch.class, usageBatchFilterWidget.getBeanClass());
    }

    @Test
    public void testGetBeanItemCaption() {
        assertEquals(USAGE_BATCH_NAME, usageBatchFilterWidget.getBeanItemCaption(buildUsageBatch()));
    }

    @Test
    public void testGetIdForBean() {
        assertEquals(USAGE_BATCH_ID, usageBatchFilterWidget.getIdForBean(buildUsageBatch()));
    }

    @Test
    public void testOnSave() {
        FilterSaveEvent<String> filterSaveEvent = createMock(FilterSaveEvent.class);
        Set usageBatches = Sets.newHashSet(USAGE_BATCH_NAME);
        expect(filterSaveEvent.getSelectedItemsIds()).andReturn(usageBatches).once();
        replay(filterSaveEvent);
        usageBatchFilterWidget.onSave(filterSaveEvent);
        verify(filterSaveEvent);
    }

    @Test
    public void testShowFilterWindow() {
        FilterWindow<Long, Rightsholder> filterWindow = createMock(FilterWindow.class);
        mockStatic(Windows.class);
        Windows.showFilterWindow("Batches filter", usageBatchFilterWidget, "name");
        expectLastCall().andReturn(filterWindow).once();
        filterWindow.setSelectedItemsIds(null);
        expectLastCall().once();
        expect(filterWindow.getId()).andReturn("id").once();
        filterWindow.addStyleName("batches-filter-window");
        expectLastCall().once();
        filterWindow.setSearchPromptString("Enter Usage Batch name");
        expectLastCall().once();
        replay(filterWindow, Windows.class);
        usageBatchFilterWidget.showFilterWindow();
        verify(filterWindow, Windows.class);
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(USAGE_BATCH_NAME);
        usageBatch.setId(USAGE_BATCH_ID);
        return usageBatch;
    }
}
