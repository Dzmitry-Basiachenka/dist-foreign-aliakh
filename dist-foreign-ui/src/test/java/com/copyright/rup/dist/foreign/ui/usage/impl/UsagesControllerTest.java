package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.anyObject;
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
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IResearchService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.impl.UsageService;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.CreateScenarioWindow.ScenarioCreateEvent;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.widget.api.IWidget;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vaadin.ui.HorizontalLayout;

import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
// TODO add testCreateScenario
public class UsagesControllerTest {

    private static final String RRO_ACCOUNT_NAME = "Account Name";
    private static final String USAGE_BATCH_ID = RupPersistUtils.generateUuid();
    private static final Long RRO_ACCOUNT_NUMBER = 12345678L;
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private UsagesController controller;
    private UsageService usageService;
    private IUsagesFilterController filterController;
    private IUsagesFilterWidget filterWidgetMock;
    private IUsagesWidget usagesWidget;
    private IUsageBatchService usageBatchService;
    private IPrmIntegrationService prmIntegrationService;
    private IScenarioService scenarioService;
    private IResearchService researchService;
    private UsageFilter usageFilter;

    @Before
    public void setUp() {
        controller = new UsagesController();
        usageService = createMock(UsageService.class);
        usageBatchService = createMock(IUsageBatchService.class);
        filterController = createMock(IUsagesFilterController.class);
        usagesWidget = createMock(IUsagesWidget.class);
        scenarioService = createMock(IScenarioService.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        researchService = createMock(IResearchService.class);
        filterWidgetMock = createMock(IUsagesFilterWidget.class);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, IWidget.class, usagesWidget);
        Whitebox.setInternalState(controller, filterController);
        Whitebox.setInternalState(controller, prmIntegrationService);
        Whitebox.setInternalState(controller, scenarioService);
        Whitebox.setInternalState(controller, researchService);
        usageFilter = new UsageFilter();
    }

    @Test
    public void testGetSize() {
        UsageFilter filter = new UsageFilter();
        filter.setFiscalYear(2017);
        prepareGetAppliedFilterExpectations(filter);
        expect(usageService.getUsagesCount(filter)).andReturn(1).once();
        replay(filterWidgetMock, usageService, filterController);
        assertEquals(1, controller.getSize());
        verify(filterWidgetMock, usageService, filterController);
    }

    @Test
    public void testLoadBeans() {
        usageFilter.setFiscalYear(2017);
        prepareGetAppliedFilterExpectations(usageFilter);
        Capture<Pageable> pageableCapture = new Capture<>();
        expect(usageService.getUsages(eq(usageFilter), capture(pageableCapture), isNull()))
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
    public void testSendForResearchUsagesStreamSource() {
        prepareGetAppliedFilterExpectations(usageFilter);
        researchService.sendForResearch(anyObject(UsageFilter.class), anyObject(OutputStream.class));
        expectLastCall().once();
        replay(usageService, filterWidgetMock, filterController);
        assertNotNull(controller.getSendForResearchUsagesStreamSource().getStream());
        verify(usageService, filterWidgetMock, filterController);
    }

    @Test
    public void testSendForResearchUsagesStreamSourceFileName() {
        assertEquals("send_for_research_" + CommonDateUtils.format(OffsetDateTime.now(), "MM_dd_YYYY_HH_mm") + ".csv",
            controller.getSendForResearchUsagesStreamSource().getFileName());
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
        ProcessingResult csvProcessingResult = Whitebox.newInstance(ProcessingResult.class);
        IStreamSource errorStreamSource =
            controller.getErrorResultStreamSource(StringUtils.EMPTY, csvProcessingResult);
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
        assertEquals("export_usage_" + CommonDateUtils.format(OffsetDateTime.now(), "MM_dd_YYYY_HH_mm") + ".csv",
            controller.getExportUsagesStreamSource().getFileName());
    }

    @Test
    public void testGetErrorsFileName() {
        ProcessingResult csvProcessingResult = Whitebox.newInstance(ProcessingResult.class);
        assertEquals("Error_for_fileName.csv",
            controller.getErrorResultStreamSource("fileName.csv", csvProcessingResult).getFileName());
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
    public void testGetMarkets() {
        List<String> markets = Collections.singletonList("Bus");
        expect(usageService.getMarkets()).andReturn(markets).once();
        replay(usageService);
        assertEquals(markets, controller.getMarkets());
        verify(usageService);
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
        UsageBatch usageBatch = new UsageBatch();
        List<Usage> usages = Lists.newArrayList(new Usage());
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        expect(usageBatchService.insertUsageBatch(usageBatch, usages)).andReturn(1).once();
        usageBatchService.sendForMatching(usages);
        expectLastCall().once();
        usageBatchService.sendForGettingRights(usages);
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidgetMock);
        assertEquals(1, controller.loadUsageBatch(usageBatch, usages));
        verify(usageBatchService, filterController, filterWidgetMock);
    }

    @Test
    public void testLoadNtsBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatchService.insertNtsBatch(usageBatch);
        expectLastCall().once();
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidgetMock);
        controller.loadNtsBatch(usageBatch);
        verify(usageBatchService, filterController, filterWidgetMock);
    }

    @Test
    public void testGetUsagesCountForNtsBatch() {
        UsageBatch usageBatch = new UsageBatch();
        expect(usageService.getUsagesCountForNtsBatch(usageBatch)).andReturn(5).once();
        replay(usageService);
        assertEquals(5, controller.getUsagesCountForNtsBatch(usageBatch));
        verify(usageService);
    }

    @Test
    public void testLoadResearchedUsages() {
        List<ResearchedUsage> researchedUsages = Lists.newArrayList(new ResearchedUsage());
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        usageService.loadResearchedUsages(researchedUsages);
        expectLastCall().once();
        replay(usageService, filterController, filterWidgetMock);
        controller.loadResearchedUsages(researchedUsages);
        verify(usageService, filterController, filterWidgetMock);
    }

    @Test
    public void testDeleteUsageBatch() {
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
    public void testIsProductFamilyAndStatusFiltersAppliedStatusNull() {
        prepareGetAppliedFilterExpectations(usageFilter);
        replay(filterController, filterWidgetMock);
        assertFalse(controller.isProductFamilyAndStatusFiltersApplied());
        verify(filterController, filterWidgetMock);
    }

    @Test
    public void testIsProductFamilyAndStatusFilterApplied() {
        usageFilter.setUsageStatus(UsageStatusEnum.NEW);
        testIsProductFamilyAndStatusFilterApplied(false, usageFilter);
        usageFilter = new UsageFilter();
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        usageFilter.setProductFamilies(Sets.newHashSet(FAS_PRODUCT_FAMILY, "NTS"));
        testIsProductFamilyAndStatusFilterApplied(true, usageFilter);
    }

    @Test
    public void testIsWorkNotFoundStatusApplied() {
        usageFilter.setUsageStatus(UsageStatusEnum.WORK_NOT_FOUND);
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        replay(filterController, filterWidgetMock);
        assertTrue(controller.isWorkNotFoundStatusApplied());
        verify(filterController, filterWidgetMock);
    }

    @Test
    public void testIsWorkNotFoundStatusAppliedWorkFound() {
        usageFilter.setUsageStatus(UsageStatusEnum.WORK_FOUND);
        prepareGetAppliedFilterExpectations(usageFilter);
        replay(filterController, filterWidgetMock);
        assertFalse(controller.isWorkNotFoundStatusApplied());
        verify(filterController, filterWidgetMock);
    }

    @Test
    public void testIsSingleProductFamilySelected() {
        testIsSingleProductFamilySelected(true, ImmutableSet.of("NTS"));
        testIsSingleProductFamilySelected(false, Collections.EMPTY_SET);
        testIsSingleProductFamilySelected(false, ImmutableSet.of("FAS", "FAS2"));
    }

    @Test
    public void testOnScenarioCreated() {
        IUsagesWidget usageWidgetMock = createMock(IUsagesWidget.class);
        ScenarioCreateEvent eventMock = createMock(ScenarioCreateEvent.class);
        Whitebox.setInternalState(controller, "widget", usageWidgetMock);
        usageWidgetMock.fireWidgetEvent(eventMock);
        expectLastCall().once();
        replay(usageWidgetMock, eventMock);
        controller.onScenarioCreated(eventMock);
        verify(usageWidgetMock, eventMock);
    }

    private void testIsProductFamilyAndStatusFilterApplied(boolean expectedResult, UsageFilter actualAppliedFilter) {
        prepareGetAppliedFilterExpectations(actualAppliedFilter);
        replay(filterController, filterWidgetMock);
        assertEquals(expectedResult, controller.isProductFamilyAndStatusFiltersApplied());
        verify(filterController, filterWidgetMock);
        reset(filterController, filterWidgetMock);
    }

    private void testIsSingleProductFamilySelected(boolean expectedResult, Set<String> actualProductFamilies) {
        usageFilter = new UsageFilter();
        usageFilter.setProductFamilies(actualProductFamilies);
        prepareGetAppliedFilterExpectations(usageFilter);
        replay(filterController, filterWidgetMock);
        assertEquals(expectedResult, controller.isSingleProductFamilySelected());
        verify(filterController, filterWidgetMock);
        reset(filterController, filterWidgetMock);
    }

    private void prepareGetAppliedFilterExpectations(UsageFilter expectedUsageFilter) {
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(expectedUsageFilter).once();
    }
}
