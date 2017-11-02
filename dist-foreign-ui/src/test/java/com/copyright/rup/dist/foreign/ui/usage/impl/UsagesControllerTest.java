package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
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
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.impl.UsageService;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvProcessingResult;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CreateScenarioWindow.ScenarioCreateEvent;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.widget.api.IWidget;

import com.google.common.collect.Lists;
import com.vaadin.ui.HorizontalLayout;

import org.apache.commons.lang3.StringUtils;
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
@PrepareForTest({LocalDate.class, UsagesController.class})
public class UsagesControllerTest {

    private static final String RRO_ACCOUNT_NAME = "Account Name";
    private static final String USAGE_BATCH_ID = RupPersistUtils.generateUuid();
    private static final Long RRO_ACCOUNT_NUMBER = 12345678L;
    private static final String SCENARIO_NAME = "Scenario Name";
    private static final String DESCRIPTION = "Description";
    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();
    private UsagesController controller;
    private UsageService usageService;
    private IUsagesFilterController filterController;
    private IUsagesWidget usagesWidget;
    private IUsageBatchService usageBatchService;
    private IPrmIntegrationService prmIntegrationService;
    private IScenarioService scenarioService;

    @Before
    public void setUp() {
        controller = new UsagesController();
        usageService = createMock(UsageService.class);
        usageBatchService = createMock(IUsageBatchService.class);
        filterController = createMock(IUsagesFilterController.class);
        usagesWidget = createMock(IUsagesWidget.class);
        scenarioService = createMock(IScenarioService.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        Whitebox.setInternalState(controller, "usageBatchService", usageBatchService);
        Whitebox.setInternalState(controller, "usageService", usageService);
        Whitebox.setInternalState(controller, "usageBatchService", usageBatchService);
        Whitebox.setInternalState(controller, IWidget.class, usagesWidget);
        Whitebox.setInternalState(controller, "filterController", filterController);
        Whitebox.setInternalState(controller, "prmIntegrationService", prmIntegrationService);
        Whitebox.setInternalState(controller, "scenarioService", scenarioService);
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
    public void testGetExportStream() {
        IStreamSource exportStreamSource = controller.getExportUsagesStreamSource();
        ExecutorService executorService = createMock(ExecutorService.class);
        Whitebox.setInternalState(exportStreamSource, executorService);
        Capture<Runnable> captureRunnable = new Capture<>();
        executorService.execute(capture(captureRunnable));
        expectLastCall().once();
        replay(usageService, filterController, executorService);
        assertNotNull(exportStreamSource.getStream());
        Runnable runnable = captureRunnable.getValue();
        assertNotNull(runnable);
        assertSame(exportStreamSource, Whitebox.getInternalState(runnable, "arg$1"));
        assertTrue(Whitebox.getInternalState(runnable, "arg$2") instanceof PipedOutputStream);
        verify(usageService, filterController, executorService);
    }

    @Test
    public void testGetErrorsStream() {
        CsvProcessingResult csvProcessingResult = new CsvProcessingResult(Collections.emptyList(), StringUtils.EMPTY);
        IStreamSource errorStreamSource =
            controller.getErrorResultStreamSource(csvProcessingResult);
        ExecutorService executorService = createMock(ExecutorService.class);
        Whitebox.setInternalState(errorStreamSource, executorService);
        Capture<Runnable> captureRunnable = new Capture<>();
        executorService.execute(capture(captureRunnable));
        expectLastCall().once();
        replay(usageService, filterController, executorService);
        assertNotNull(errorStreamSource.getStream());
        Runnable runnable = captureRunnable.getValue();
        assertNotNull(runnable);
        assertSame(errorStreamSource, Whitebox.getInternalState(runnable, "arg$1"));
        assertTrue(Whitebox.getInternalState(runnable, "arg$2") instanceof PipedOutputStream);
        verify(usageService, filterController, executorService);
    }

    @Test
    public void testGetExportFileName() {
        mockStaticPartial(LocalDate.class, "now");
        expect(LocalDate.now()).andReturn(LocalDate.of(2017, 1, 2)).once();
        expect(LocalDate.now()).andReturn(LocalDate.of(2009, 11, 22)).once();
        replay(LocalDate.class, UsagesController.class);
        assertEquals("export_usage_01_02_2017.csv", controller.getExportUsagesStreamSource().getFileName());
        assertEquals("export_usage_11_22_2009.csv", controller.getExportUsagesStreamSource().getFileName());
        verify(LocalDate.class, UsagesController.class);
    }

    @Test
    public void testGetErrorsFileName() {
        CsvProcessingResult csvProcessingResult = new CsvProcessingResult(Collections.emptyList(), "fileName.csv");
        assertEquals("Error_for_fileName.csv",
            controller.getErrorResultStreamSource(csvProcessingResult).getFileName());
    }

    @Test
    public void testGetScenariosNamesAssociatedWithUsageBatch() {
        expect(scenarioService.getScenariosNamesByUsageBatchId(USAGE_BATCH_ID))
            .andReturn(Collections.emptyList()).once();
        replay(scenarioService);
        controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID);
        verify(scenarioService);
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
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        expect(usageBatchService.insertUsageBatch(usageBatch, usages)).andReturn(1).once();
        replay(usageBatchService, filterController, filterWidgetMock);
        assertEquals(1, controller.loadUsageBatch(usageBatch, usages));
        verify(usageBatchService, filterController, filterWidgetMock);
    }

    @Test
    public void testDeleteUsageBatch() {
        IUsagesFilterWidget filterWidgetMock = createMock(IUsagesFilterWidget.class);
        UsageBatch usageBatch = new UsageBatch();
        usageBatchService.deleteUsageBatch(usageBatch);
        expectLastCall().once();
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidgetMock);
        controller.deleteUsageBatch(usageBatch);
        verify(usageBatchService, filterController, filterWidgetMock);
    }

    @Test
    public void testGetRroName() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setName(RRO_ACCOUNT_NAME);
        rightsholder.setAccountNumber(RRO_ACCOUNT_NUMBER);
        rightsholder.setId(RupPersistUtils.generateUuid());
        expect(prmIntegrationService.getRightsholder(RRO_ACCOUNT_NUMBER)).andReturn(rightsholder).once();
        replay(prmIntegrationService);
        assertEquals(rightsholder, controller.getRro(RRO_ACCOUNT_NUMBER));
        verify(prmIntegrationService);
    }

    @Test
    public void testGetNullRroName() {
        expect(prmIntegrationService.getRightsholder(RRO_ACCOUNT_NUMBER)).andReturn(null).once();
        replay(prmIntegrationService);
        assertEquals(new Rightsholder(), controller.getRro(RRO_ACCOUNT_NUMBER));
        verify(prmIntegrationService);
    }

    @Test
    public void testOnScenarioCreated() {
        IUsagesWidget usagesWidgetMock = createMock(IUsagesWidget.class);
        ScenarioCreateEvent eventMock = createMock(ScenarioCreateEvent.class);
        Whitebox.setInternalState(controller, "widget", usagesWidgetMock);
        usagesWidgetMock.fireWidgetEvent(eventMock);
        expectLastCall().once();
        replay(usagesWidgetMock, eventMock);
        controller.onScenarioCreated(eventMock);
        verify(usagesWidgetMock, eventMock);
    }

    @Test
    public void testCreateScenario() {
        IUsagesFilterWidget filterWidgetMock = createMock(IUsagesFilterWidget.class);
        UsageFilter usageFilter = new UsageFilter();
        expect(filterController.getWidget()).andReturn(filterWidgetMock).times(2);
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        expect(scenarioService.createScenario(SCENARIO_NAME, DESCRIPTION, usageFilter)).andReturn(SCENARIO_ID).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        replay(scenarioService, filterController, filterWidgetMock);
        assertEquals(SCENARIO_ID, controller.createScenario(SCENARIO_NAME, DESCRIPTION));
        verify(scenarioService, filterController, filterWidgetMock);
    }
}
