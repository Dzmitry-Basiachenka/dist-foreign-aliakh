package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IResearchService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchStatusService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.ResearchedUsagesCsvProcessor;
import com.copyright.rup.dist.foreign.vui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.vui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.dist.foreign.vui.usage.api.IFasNtsUsageFilterWidget;
import com.copyright.rup.dist.foreign.vui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageWidget;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
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
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link FasUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/09/2019
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class, OffsetDateTime.class, ByteArrayStreamSource.class, StreamSource.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class FasUsageControllerTest {

    private static final OffsetDateTime DATE = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
    private static final List<String> USAGE_IDS = List.of("8932b53e-564e-47c2-81fd-5bf421eece0c");
    private static final Long WR_WRK_INST = 122820638L;
    private static final String RRO_ACCOUNT_NAME = "Rothchild Consultants";
    private static final Long RRO_ACCOUNT_NUMBER = 1000000001L;
    private static final String REASON = "some reason";

    private FasUsageController controller;
    private IUsageService usageService;
    private IFasUsageService fasUsageService;
    private IFasNtsUsageFilterController filterController;
    private IFasNtsUsageFilterWidget filterWidget;
    private IUsageBatchService usageBatchService;
    private IPrmIntegrationService prmIntegrationService;
    private IResearchService researchService;
    private IReportService reportService;
    private IStreamSourceHandler streamSourceHandler;
    private CsvProcessorFactory csvProcessorFactory;
    private UsageFilter usageFilter;

    @Before
    public void setUp() {
        controller = new FasUsageController();
        usageService = createMock(IUsageService.class);
        fasUsageService = createMock(IFasUsageService.class);
        usageBatchService = createMock(IUsageBatchService.class);
        filterController = createMock(IFasNtsUsageFilterController.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        researchService = createMock(IResearchService.class);
        filterWidget = createMock(IFasNtsUsageFilterWidget.class);
        reportService = createMock(IReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        csvProcessorFactory = createMock(CsvProcessorFactory.class);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, fasUsageService);
        Whitebox.setInternalState(controller, filterController);
        Whitebox.setInternalState(controller, prmIntegrationService);
        Whitebox.setInternalState(controller, researchService);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
        Whitebox.setInternalState(controller, csvProcessorFactory);
        usageFilter = new UsageFilter();
    }

    @Test
    public void testGetBeansCount() {
        usageFilter.setFiscalYear(2017);
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        expect(fasUsageService.getUsagesCount(usageFilter)).andReturn(1).once();
        replay(filterWidget, usageService, fasUsageService, filterController);
        assertEquals(1, controller.getBeansCount());
        verify(filterWidget, usageService, fasUsageService, filterController);
    }

    @Test
    public void testLoadBeans() {
        usageFilter.setFiscalYear(2017);
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        Capture<Pageable> pageableCapture = newCapture();
        expect(fasUsageService.getUsageDtos(eq(usageFilter), capture(pageableCapture), isNull()))
            .andReturn(List.of()).once();
        replay(filterWidget, usageService, fasUsageService, filterController);
        List<UsageDto> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(filterWidget, usageService, fasUsageService, filterController);
    }

    @Test
    public void testIsBatchProcessingCompleted() {
        String batchId = "d900119b-f44d-495b-94f5-c13ba4ded983";
        IUsageBatchStatusService usageBatchStatusService = createMock(IUsageBatchStatusService.class);
        Whitebox.setInternalState(controller, usageBatchStatusService);
        expect(usageBatchStatusService.isBatchProcessingCompleted(batchId,
            Set.of(UsageStatusEnum.NEW, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_FOUND))).andReturn(true).once();
        replay(usageBatchStatusService);
        assertTrue(controller.isBatchProcessingCompleted(batchId));
        verify(usageBatchStatusService);
    }

    @Test
    public void testDeleteUsageBatch() {
        var usageBatch = new UsageBatch();
        usageBatchService.deleteUsageBatch(usageBatch);
        expectLastCall().once();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        filterWidget.clearFilter();
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidget);
        controller.deleteUsageBatch(usageBatch);
        verify(usageBatchService, filterController, filterWidget);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(FasUsageWidget.class));
    }

    @Test
    public void testSendForResearchUsagesStreamSource() {
        prepareGetAppliedFilterExpectations(usageFilter);
        researchService.sendForResearch(anyObject(UsageFilter.class), anyObject(OutputStream.class));
        expectLastCall().once();
        replay(usageService, filterWidget, filterController);
        assertNotNull(controller.getSendForResearchUsagesStreamSource().getSource().getValue().get());
        verify(usageService, filterWidget, filterController);
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
    public void testGetResearchedUsagesCsvProcessor() {
        ResearchedUsagesCsvProcessor processor = createMock(ResearchedUsagesCsvProcessor.class);
        expect(csvProcessorFactory.getResearchedUsagesCsvProcessor()).andReturn(processor).once();
        replay(csvProcessorFactory);
        assertSame(processor, controller.getResearchedUsagesCsvProcessor());
        verify(csvProcessorFactory);
    }

    @Test
    public void testGetExportUsagesStreamSource() {
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = "export_usage_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
            () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(DATE).once();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        reportService.writeFasUsageCsvReport(usageFilter, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, filterWidget, filterController, streamSourceHandler, reportService);
        IStreamSource streamSource = controller.getExportUsagesStreamSource();
        assertEquals("export_usage_01_02_2019_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        assertNotNull(posConsumer);
        verify(OffsetDateTime.class, filterWidget, filterController, streamSourceHandler, reportService);
    }

    @Test
    public void testGetClaAccountNumber() {
        Long claAccountNumber = 2000017000L;
        expect(fasUsageService.getClaAccountNumber()).andReturn(claAccountNumber).once();
        replay(fasUsageService);
        assertEquals(claAccountNumber, controller.getClaAccountNumber());
        verify(fasUsageService);
    }

    @Test
    public void testLoadUsageBatch() {
        var usageBatch = new UsageBatch();
        List<Usage> usages = List.of(new Usage());
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        filterWidget.clearFilter();
        expectLastCall().once();
        expect(usageBatchService.insertFasBatch(usageBatch, usages)).andReturn(1).once();
        usageService.sendForMatching(usages);
        expectLastCall().once();
        usageService.sendForGettingRights(usages, usageBatch.getName());
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidget);
        assertEquals(1, controller.loadUsageBatch(usageBatch, usages));
        verify(usageBatchService, filterController, filterWidget);
    }

    @Test
    public void testLoadResearchedUsages() {
        List<ResearchedUsage> researchedUsages = List.of(new ResearchedUsage());
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        filterWidget.clearFilter();
        expectLastCall().once();
        fasUsageService.loadResearchedUsages(researchedUsages);
        expectLastCall().once();
        fasUsageService.sendForGettingRights(researchedUsages);
        expectLastCall().once();
        replay(fasUsageService, filterController, filterWidget);
        controller.loadResearchedUsages(researchedUsages);
        verify(fasUsageService, filterController, filterWidget);
    }

    @Test
    public void testGetRightsholder() {
        var rightsholder = new Rightsholder();
        rightsholder.setName(RRO_ACCOUNT_NAME);
        rightsholder.setAccountNumber(RRO_ACCOUNT_NUMBER);
        expect(prmIntegrationService.getRightsholder(RRO_ACCOUNT_NUMBER)).andReturn(rightsholder).once();
        replay(prmIntegrationService);
        assertEquals(rightsholder, controller.getRightsholder(RRO_ACCOUNT_NUMBER));
        verify(prmIntegrationService);
    }

    @Test
    public void testGetRightsholderNullName() {
        expect(prmIntegrationService.getRightsholder(RRO_ACCOUNT_NUMBER)).andReturn(null).once();
        replay(prmIntegrationService);
        assertEquals(new Rightsholder(), controller.getRightsholder(RRO_ACCOUNT_NUMBER));
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

    @Test
    public void testIsValidFilteredUsageStatus() {
        usageFilter.setUsageStatus(UsageStatusEnum.WORK_NOT_FOUND);
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        expect(usageService.isValidFilteredUsageStatus(usageFilter, UsageStatusEnum.WORK_NOT_FOUND))
            .andReturn(true).once();
        replay(filterController, filterWidget, usageService);
        assertTrue(controller.isValidFilteredUsageStatus(UsageStatusEnum.WORK_NOT_FOUND));
        verify(filterController, filterWidget, usageService);
    }

    @Test
    public void testGetUsageDtosToUpdate() {
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        expect(fasUsageService.getUsageDtos(usageFilter, null, null)).andReturn(List.of(new UsageDto())).once();
        replay(filterWidget, fasUsageService, filterController);
        assertEquals(1, controller.getUsageDtosToUpdate().size());
        verify(filterWidget, fasUsageService, filterController);
    }

    @Test
    public void testGetRecordsThreshold() {
        expect(fasUsageService.getRecordsThreshold()).andReturn(10000).once();
        replay(fasUsageService);
        assertEquals(10000, controller.getRecordsThreshold());
        verify(fasUsageService);
    }

    @Test
    public void testUpdateUsages() {
        fasUsageService.updateUsages(USAGE_IDS, WR_WRK_INST, REASON);
        expectLastCall().once();
        replay(fasUsageService);
        controller.updateUsages(USAGE_IDS, WR_WRK_INST, REASON);
        verify(fasUsageService);
    }

    private void prepareGetAppliedFilterExpectations(UsageFilter expectedUsageFilter) {
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(expectedUsageFilter).once();
    }
}
