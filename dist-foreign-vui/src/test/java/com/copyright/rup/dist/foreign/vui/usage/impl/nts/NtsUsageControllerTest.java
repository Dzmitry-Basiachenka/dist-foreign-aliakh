package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

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
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchStatusService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsScenarioService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;
import com.copyright.rup.dist.foreign.vui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.dist.foreign.vui.usage.api.IFasNtsUsageFilterWidget;
import com.copyright.rup.dist.foreign.vui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageWidget;

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
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link NtsUsageController}.
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
//TODO {aliakh} adjust tests if necessary
public class NtsUsageControllerTest {

    private static final OffsetDateTime DATE = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
    private static final String RRO_ACCOUNT_NAME = "Account Name";
    private static final String USAGE_BATCH_ID = "47dbb3ec-ca8d-4feb-98dc-bce07bf9907e";
    private static final Long RRO_ACCOUNT_NUMBER = 12345678L;
    private static final String USER_NAME = "Test User Name";
    private static final String BATCH_NAME = "Test Batch Name";
    private static final String SCENARIO_NAME = "Test Scenario Name";
    private static final String DESCRIPTION = "Test Description";

    private NtsUsageController controller;
    private IUsageService usageService;
    private IFasUsageService fasUsageService;
    private INtsUsageService ntsUsageService;
    private IFasNtsUsageFilterController filterController;
    private IFasNtsUsageFilterWidget filterWidget;
    private IUsageBatchService usageBatchService;
    private IPrmIntegrationService prmIntegrationService;
    private INtsScenarioService ntsScenarioService;
    private IFundPoolService fundPoolService;
    private IReportService reportService;
    private IStreamSourceHandler streamSourceHandler;
    private UsageFilter usageFilter;

    @Before
    public void setUp() {
        controller = new NtsUsageController();
        usageService = createMock(IUsageService.class);
        fasUsageService = createMock(IFasUsageService.class);
        ntsUsageService = createMock(INtsUsageService.class);
        usageBatchService = createMock(IUsageBatchService.class);
        filterController = createMock(IFasNtsUsageFilterController.class);
        ntsScenarioService = createMock(INtsScenarioService.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        filterWidget = createMock(IFasNtsUsageFilterWidget.class);
        fundPoolService = createMock(IFundPoolService.class);
        reportService = createMock(IReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, fasUsageService);
        Whitebox.setInternalState(controller, ntsUsageService);
        Whitebox.setInternalState(controller, filterController);
        Whitebox.setInternalState(controller, prmIntegrationService);
        Whitebox.setInternalState(controller, ntsScenarioService);
        Whitebox.setInternalState(controller, fundPoolService);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
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
        var batchId = "65f27251-7ad0-4aba-9d4a-bf584edb4177";
        IUsageBatchStatusService usageBatchStatusService = createMock(IUsageBatchStatusService.class);
        Whitebox.setInternalState(controller, usageBatchStatusService);
        expect(usageBatchStatusService.isBatchProcessingCompleted(batchId,
            Set.of(UsageStatusEnum.WORK_FOUND, UsageStatusEnum.NON_STM_RH, UsageStatusEnum.US_TAX_COUNTRY,
                UsageStatusEnum.RH_FOUND))).andReturn(true).once();
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
    public void testIsValidFilteredUsageStatus() {
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        expect(usageService.isValidFilteredUsageStatus(usageFilter, UsageStatusEnum.ELIGIBLE)).andReturn(true).once();
        replay(filterController, filterWidget, usageService);
        assertTrue(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE));
        verify(filterController, filterWidget, usageService);
    }

    @Test
    public void testCreateNtsScenario() {
        var ntsFields = new Scenario.NtsFields();
        ntsFields.setRhMinimumAmount(new BigDecimal("300.00"));
        var scenario = new Scenario();
        expect(ntsScenarioService.createScenario(SCENARIO_NAME, ntsFields, DESCRIPTION, usageFilter))
            .andReturn(scenario).once();
        expect(filterController.getWidget()).andReturn(filterWidget).times(2);
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        filterWidget.clearFilter();
        expectLastCall().once();
        replay(filterController, filterWidget, ntsScenarioService);
        assertEquals(scenario, controller.createNtsScenario(SCENARIO_NAME, ntsFields, DESCRIPTION));
        verify(filterController, filterWidget, ntsScenarioService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(NtsUsageWidget.class));
    }

    @Test
    public void testGetExportUsagesStreamSource() {
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        var fileName = "export_usage_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
            () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(DATE).once();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        reportService.writeNtsUsageCsvReport(usageFilter, pos);
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
    public void testGetScenarioNameAssociatedWithAdditionalFund() {
        expect(ntsScenarioService.getScenarioNameByFundPoolId(USAGE_BATCH_ID)).andReturn(SCENARIO_NAME).once();
        replay(ntsScenarioService);
        assertSame(SCENARIO_NAME, controller.getScenarioNameAssociatedWithAdditionalFund(USAGE_BATCH_ID));
        verify(ntsScenarioService);
    }

    @Test
    public void testGetUsageBatchesForAdditionalFunds() {
        List<UsageBatch> usageBatches = List.of();
        expect(usageBatchService.getUsageBatchesForNtsFundPool()).andReturn(usageBatches).once();
        replay(usageBatchService);
        assertSame(usageBatches, controller.getUsageBatchesForAdditionalFunds());
        verify(usageBatchService);
    }

    @Test
    public void testGetMarkets() {
        var markets = List.of("Bus");
        expect(ntsUsageService.getMarkets()).andReturn(markets).once();
        replay(ntsUsageService);
        assertSame(markets, controller.getMarkets());
        verify(ntsUsageService);
    }

    @Test
    public void testLoadNtsBatch() {
        mockStatic(RupContextUtils.class);
        var usageBatch = new UsageBatch();
        usageBatch.setName(BATCH_NAME);
        var usage1 = new Usage();
        usage1.setId("d9440e59-4943-4cdc-bb32-84edcb7febbc");
        var usage2 = new Usage();
        usage2.setId("43f31060-acde-477a-8f73-80fadbafaa70");
        var usageIds = List.of(usage1.getId(), usage2.getId());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(usageBatchService.insertNtsBatch(usageBatch, USER_NAME)).andReturn(usageIds).once();
        ntsUsageService.sendForGettingRights(usageIds, BATCH_NAME);
        expectLastCall().once();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        filterWidget.clearFilter();
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidget, RupContextUtils.class);
        assertEquals(2, controller.loadNtsBatch(usageBatch));
        verify(usageBatchService, filterController, filterWidget, RupContextUtils.class);
    }

    @Test
    public void testGetPreServiceSeeFunds() {
        List<FundPool> fundPools = List.of(new FundPool());
        IProductFamilyProvider productFamilyProvider = createMock(IProductFamilyProvider.class);
        Whitebox.setInternalState(controller, productFamilyProvider);
        expect(controller.getSelectedProductFamily()).andReturn("NTS").once();
        expect(fundPoolService.getFundPools("NTS")).andReturn(fundPools).once();
        replay(productFamilyProvider, fundPoolService);
        assertEquals(fundPools, controller.getAdditionalFunds());
        verify(productFamilyProvider, fundPoolService);
    }

    @Test
    public void testGetAdditionalFundsNotAttachedToScenario() {
        var fundPools = List.of(new FundPool());
        expect(fundPoolService.getNtsNotAttachedToScenario()).andReturn(fundPools).once();
        replay(fundPoolService);
        assertEquals(fundPools, controller.getAdditionalFundsNotAttachedToScenario());
        verify(fundPoolService);
    }

    @Test
    public void testDeleteAdditionalFund() {
        var fundPool = new FundPool();
        fundPoolService.deleteNtsFundPool(fundPool);
        expectLastCall().once();
        replay(fundPoolService);
        controller.deleteAdditionalFund(fundPool);
        verify(fundPoolService);
    }

    @Test
    public void testGetUsagesCountForNtsBatch() {
        var usageBatch = new UsageBatch();
        expect(ntsUsageService.getUsagesCountForBatch(usageBatch)).andReturn(1).once();
        replay(ntsUsageService);
        assertEquals(1, controller.getUsagesCountForNtsBatch(usageBatch));
        verify(ntsUsageService);
    }

    @Test
    public void testGetRightsholder() {
        var rightsholder = new Rightsholder();
        rightsholder.setName(RRO_ACCOUNT_NAME);
        rightsholder.setAccountNumber(RRO_ACCOUNT_NUMBER);
        rightsholder.setId("53686adc-1f20-473a-b57f-25ff23425372");
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
        INtsUsageWidget usageWidgetMock = createMock(INtsUsageWidget.class);
        ScenarioCreateEvent eventMock = createMock(ScenarioCreateEvent.class);
        Whitebox.setInternalState(controller, "widget", usageWidgetMock);
        usageWidgetMock.fireWidgetEvent(eventMock);
        expectLastCall().once();
        replay(usageWidgetMock, eventMock);
        controller.onScenarioCreated(eventMock);
        verify(usageWidgetMock, eventMock);
    }

    @Test
    public void testGetAdditionalFundBatchesStreamSource() {
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        var fileName = "pre_service_fee_fund_batches_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
            () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(DATE).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        reportService.writeNtsWithdrawnBatchesCsvReport(List.of(new UsageBatch()), BigDecimal.ONE, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, streamSourceHandler, reportService);
        IStreamSource streamSource = controller.getAdditionalFundBatchesStreamSource(
            List.of(new UsageBatch()), BigDecimal.ONE);
        assertEquals("pre_service_fee_fund_batches_01_02_2019_03_04.csv",
            streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        verify(OffsetDateTime.class, streamSourceHandler, reportService);
    }

    @Test
    public void testGetBatchNamesWithUnclassifiedUsages() {
        expect(usageBatchService.getBatchNamesWithUnclassifiedWorks(Set.of(USAGE_BATCH_ID)))
            .andReturn(List.of("Batch with unclassified usages")).once();
        replay(usageBatchService);
        controller.getBatchNamesWithUnclassifiedWorks(Set.of(USAGE_BATCH_ID));
        verify(usageBatchService);
    }

    @Test
    public void testGetBatchNamesWithInvalidStmOrNonStmUsagesState() {
        var batchIds = Set.of(USAGE_BATCH_ID);
        expect(usageBatchService.getClassifcationToBatchNamesWithoutUsagesForStmOrNonStm(batchIds))
            .andReturn(Map.of()).once();
        replay(usageBatchService);
        controller.getBatchNamesWithInvalidStmOrNonStmUsagesState(batchIds);
        verify(usageBatchService);
    }
}
