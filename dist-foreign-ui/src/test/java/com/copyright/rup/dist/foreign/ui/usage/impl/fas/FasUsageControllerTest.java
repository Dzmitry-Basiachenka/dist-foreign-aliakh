package com.copyright.rup.dist.foreign.ui.usage.impl.fas;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IResearchService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.UsageService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;

import com.copyright.rup.dist.foreign.ui.usage.api.fas.IFasUsageWidget;
import com.google.common.collect.Lists;

import com.vaadin.ui.HorizontalLayout;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link FasUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/09/19
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class, OffsetDateTime.class, ByteArrayStreamSource.class, StreamSource.class})
public class FasUsageControllerTest {

    private static final OffsetDateTime DATE = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
    private static final String RRO_ACCOUNT_NAME = "Account Name";
    private static final Long RRO_ACCOUNT_NUMBER = 12345678L;

    private FasUsageController controller;
    private IUsageService usageService;
    private IUsagesFilterController filterController;
    private IUsagesFilterWidget filterWidgetMock;
    private IFasUsageWidget usagesWidget;
    private IUsageBatchService usageBatchService;
    private IPrmIntegrationService prmIntegrationService;
    private IResearchService researchService;
    private IReportService reportService;
    private IStreamSourceHandler streamSourceHandler;
    private UsageFilter usageFilter;

    @Before
    public void setUp() {
        controller = new FasUsageController();
        usageService = createMock(UsageService.class);
        usageBatchService = createMock(IUsageBatchService.class);
        filterController = createMock(IUsagesFilterController.class);
        usagesWidget = createMock(IFasUsageWidget.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        researchService = createMock(IResearchService.class);
        filterWidgetMock = createMock(IUsagesFilterWidget.class);
        reportService = createMock(IReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, usagesWidget);
        Whitebox.setInternalState(controller, filterController);
        Whitebox.setInternalState(controller, prmIntegrationService);
        Whitebox.setInternalState(controller, researchService);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
        usageFilter = new UsageFilter();
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
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
        assertNotNull(controller.getSendForResearchUsagesStreamSource().getSource().getValue().get());
        verify(usageService, filterWidgetMock, filterController);
    }

    @Test
    public void testSendForResearchUsagesStreamSourceFileName() {
        mockStatic(OffsetDateTime.class);
        expect(OffsetDateTime.now()).andReturn(DATE).once();
        replay(OffsetDateTime.class);
        assertEquals("send_for_research_01_02_2019_03_04.csv",
            controller.getSendForResearchUsagesStreamSource().getSource().getKey().get());
        verify(OffsetDateTime.class);
    }

    @Test
    public void testGetExportUsagesStreamSource() {
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = new Capture<>();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = new Capture<>();
        String fileName = "export_usage_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> isSupplier = () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(DATE).once();
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", isSupplier)).once();
        reportService.writeFasUsageCsvReport(usageFilter, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, filterWidgetMock, filterController, streamSourceHandler, reportService);
        IStreamSource streamSource = controller.getExportUsagesStreamSource();
        assertEquals("export_usage_01_02_2019_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        assertNotNull(posConsumer);
        verify(OffsetDateTime.class, filterWidgetMock, filterController, streamSourceHandler, reportService);
    }

    @Test
    public void testGetClaAccountNumber() {
        Long claAccountNumber = 2000017000L;
        expect(usageService.getClaAccountNumber()).andReturn(claAccountNumber).once();
        replay(usageService);
        assertEquals(claAccountNumber, controller.getClaAccountNumber());
        verify(usageService);
    }

    @Test
    public void testLoadUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        List<Usage> usages = Lists.newArrayList(new Usage());
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        expect(usageBatchService.insertFasBatch(usageBatch, usages)).andReturn(1).once();
        usageBatchService.sendForMatching(usages);
        expectLastCall().once();
        usageBatchService.sendForGettingRights(usages, usageBatch.getName());
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidgetMock);
        assertEquals(1, controller.loadUsageBatch(usageBatch, usages));
        verify(usageBatchService, filterController, filterWidgetMock);
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
        IFasUsageWidget usageWidgetMock = createMock(IFasUsageWidget.class);
        ScenarioCreateEvent eventMock = createMock(ScenarioCreateEvent.class);
        Whitebox.setInternalState(controller, "widget", usageWidgetMock);
        usageWidgetMock.fireWidgetEvent(eventMock);
        expectLastCall().once();
        replay(usageWidgetMock, eventMock);
        controller.onScenarioCreated(eventMock);
        verify(usageWidgetMock, eventMock);
    }

    private void prepareGetAppliedFilterExpectations(UsageFilter expectedUsageFilter) {
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(expectedUsageFilter).once();
    }
}
