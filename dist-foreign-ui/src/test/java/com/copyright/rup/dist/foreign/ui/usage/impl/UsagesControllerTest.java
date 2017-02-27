package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.impl.UsageService;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesWidget;
import com.copyright.rup.vaadin.widget.api.IWidget;

import com.vaadin.ui.HorizontalLayout;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.PipedOutputStream;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Verifies {@link UsagesController}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 1/18/17
 *
 * @author Aliaksandr Radkevich
 */
public class UsagesControllerTest {

    private UsagesController controller;
    private UsageService usageService;
    private IUsagesFilterController filterController;
    private IUsagesWidget usagesWidget;
    private IUsageBatchService usageBatchService;

    @Before
    public void setUp() {
        controller = new UsagesController();
        usageService = createMock(UsageService.class);
        usageBatchService = createMock(IUsageBatchService.class);
        filterController = createMock(IUsagesFilterController.class);
        usagesWidget = createMock(IUsagesWidget.class);
        Whitebox.setInternalState(controller, "usageService", usageService);
        Whitebox.setInternalState(controller, "usageBatchService", usageBatchService);
        Whitebox.setInternalState(controller, IWidget.class, usagesWidget);
        Whitebox.setInternalState(controller, "filterController", filterController);
    }

    @Test
    public void testGetSize() {
        IUsagesFilterWidget filterWidgetMock = createMock(IUsagesFilterWidget.class);
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2017);
        expect(filterWidgetMock.getAppliedFilter()).andReturn(filter).once();
        expect(usageService.getUsagesCount(filter)).andReturn(1).once();
        replay(filterWidgetMock, usageService, filterController);
        assertEquals(1, controller.getSize());
        verify(filterWidgetMock, usageService, filterController);
    }

    @Test
    public void testLoadBeans() {
        IUsagesFilterWidget filterWidgetMock = createMock(IUsagesFilterWidget.class);
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2017);
        Capture<Pageable> pageableCapture = new Capture<>();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(filter).once();
        expect(usageService.getUsages(eq(filter), capture(pageableCapture), isNull()))
            .andReturn(Collections.emptyList()).once();
        replay(filterWidgetMock, usageService, filterController);
        List<UsageDto> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(filterWidgetMock, usageService, filterController);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testInitUsagesFilterWidget() {
        UsagesFilterWidget filterWidget = new UsagesFilterWidget();
        Whitebox.setInternalState(controller, "filterController", filterController);
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        replay(filterController);
        assertSame(filterWidget, controller.initUsagesFilterWidget());
        verify(filterController);
    }

    @Test
    public void testOnFilterChanged() {
        FilterChangedEvent filterChangedEvent = new FilterChangedEvent(new HorizontalLayout());
        usagesWidget.refresh();
        expectLastCall().once();
        replay(usagesWidget);
        controller.onFilterChanged(filterChangedEvent);
        verify(usagesWidget);
    }

    @Test
    public void testGetStream() {
        ExecutorService executorService = createMock(ExecutorService.class);
        Whitebox.setInternalState(controller, executorService);
        Capture<Runnable> captureRunnable = new Capture<>();
        executorService.execute(capture(captureRunnable));
        expectLastCall().once();
        replay(usageService, filterController, executorService);
        assertNotNull(controller.getStream());
        Runnable runnable = captureRunnable.getValue();
        assertNotNull(runnable);
        assertSame(controller, Whitebox.getInternalState(runnable, "arg$1"));
        assertTrue(Whitebox.getInternalState(runnable, "arg$2") instanceof PipedOutputStream);
        verify(usageService, filterController, executorService);
    }

    @Test
    public void testGetFileName() {
        LocalDate localDate = LocalDate.now();
        assertEquals(String.format("export_usage_%s_%s_%s.csv", localDate.getMonthValue(), localDate.getDayOfMonth(),
            localDate.getYear()), controller.getFileName());
    }

    @Test
    public void testGetScenariosNamesAssociatedWithUsageBatch() {
        assertEquals(Collections.emptyList(),
            controller.getScenariosNamesAssociatedWithUsageBatch(RupPersistUtils.generateUuid()));
    }

    @Test
    public void testGetUsageBatches() {
        expect(usageBatchService.getUsageBatches()).andReturn(Collections.emptyList()).once();
        replay(usageBatchService);
        controller.getUsageBatches();
        verify(usageBatchService);
    }

    @Test
    public void testDeleteUsageBatch() {
        String batchId = RupPersistUtils.generateUuid();
        IUsagesFilterWidget filterWidgetMock = createMock(IUsagesFilterWidget.class);
        usageBatchService.deleteUsageBatch(batchId, "User@copyright.com");
        expectLastCall().once();
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidgetMock);
        controller.deleteUsageBatch(batchId);
        verify(usageBatchService, filterController, filterWidgetMock);
    }
}
