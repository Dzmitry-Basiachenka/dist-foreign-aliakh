package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.mockStaticPartial;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.impl.UsageService;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesWidget;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.widget.api.IWidget;

import com.google.common.collect.Lists;
import com.vaadin.ui.HorizontalLayout;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
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
@RunWith(PowerMockRunner.class)
@PrepareForTest({SecurityUtils.class, LocalDate.class, UsagesController.class})
public class UsagesControllerTest {

    private static final String RRO_ACCOUNT_NAME = "Account Name";
    private static final Long RRO_ACCOUNT_NUMBER = 12345678L;
    private UsagesController controller;
    private UsageService usageService;
    private IUsagesFilterController filterController;
    private IUsagesWidget usagesWidget;
    private IUsageBatchService usageBatchService;
    private IPrmIntegrationService prmIntegrationService;

    @Before
    public void setUp() {
        controller = new UsagesController();
        usageService = createMock(UsageService.class);
        usageBatchService = createMock(IUsageBatchService.class);
        filterController = createMock(IUsagesFilterController.class);
        usagesWidget = createMock(IUsagesWidget.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        Whitebox.setInternalState(controller, "usageBatchService", usageBatchService);
        Whitebox.setInternalState(controller, "usageService", usageService);
        Whitebox.setInternalState(controller, "usageBatchService", usageBatchService);
        Whitebox.setInternalState(controller, IWidget.class, usagesWidget);
        Whitebox.setInternalState(controller, "filterController", filterController);
        Whitebox.setInternalState(controller, "prmIntegrationService", prmIntegrationService);
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
        mockStaticPartial(LocalDate.class, "now");
        expect(LocalDate.now()).andReturn(LocalDate.of(2017, 1, 2)).once();
        expect(LocalDate.now()).andReturn(LocalDate.of(2009, 11, 22)).once();
        replay(LocalDate.class, UsagesController.class);
        assertEquals("export_usage_01_02_2017.csv", controller.getFileName());
        assertEquals("export_usage_11_22_2009.csv", controller.getFileName());
        verify(LocalDate.class, UsagesController.class);
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
    public void testUsageBatchExists() {
        expect(usageBatchService.usageBatchExists("Usage Batch Name")).andReturn(false).once();
        replay(usageBatchService);
        assertFalse(controller.usageBatchExists("Usage Batch Name"));
        verify(usageBatchService);
    }

    @Test
    public void testLoadUsageBatch() {
        IUsagesFilterWidget filterWidgetMock = createMock(IUsagesFilterWidget.class);
        UsageBatch usageBatch = new UsageBatch();
        List<Usage> usages = Lists.newArrayList(new Usage());
        String userName = "User Name";
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        expect(usageBatchService.insertUsageBatch(usageBatch, usages, userName)).andReturn(1).once();
        replay(usageBatchService, filterController, filterWidgetMock);
        assertEquals(1, controller.loadUsageBatch(usageBatch, usages, userName));
        verify(usageBatchService, filterController, filterWidgetMock);
    }

    @Test
    public void testDeleteUsageBatch() {
        mockStatic(SecurityUtils.class);
        UsageBatch usageBatch = new UsageBatch();
        IUsagesFilterWidget filterWidgetMock = createMock(IUsagesFilterWidget.class);
        expect(SecurityUtils.getUserName()).andReturn("user@copyright.com").once();
        usageBatchService.deleteUsageBatch(usageBatch, "user@copyright.com");
        expectLastCall().once();
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidgetMock, SecurityUtils.class);
        controller.deleteUsageBatch(usageBatch);
        verify(usageBatchService, filterController, filterWidgetMock, SecurityUtils.class);
    }

    @Test
    public void testGetRroName() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setName(RRO_ACCOUNT_NAME);
        rightsholder.setAccountNumber(RRO_ACCOUNT_NUMBER);
        expect(prmIntegrationService.getRightsholder(RRO_ACCOUNT_NUMBER)).andReturn(rightsholder).once();
        replay(prmIntegrationService);
        assertEquals(RRO_ACCOUNT_NAME, controller.getRroName(RRO_ACCOUNT_NUMBER));
        verify(prmIntegrationService);
    }

    @Test
    public void testGetNullRroName() {
        expect(prmIntegrationService.getRightsholder(RRO_ACCOUNT_NUMBER)).andReturn(null).once();
        replay(prmIntegrationService);
        assertNull(controller.getRroName(RRO_ACCOUNT_NUMBER));
        verify(prmIntegrationService);
    }
}
