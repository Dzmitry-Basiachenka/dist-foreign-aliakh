package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
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
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
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
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasNtsUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasNtsUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.INtsUsageWidget;

import com.google.common.collect.Sets;
import com.vaadin.ui.HorizontalLayout;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.InputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link NtsUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/09/19
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class, OffsetDateTime.class, ByteArrayStreamSource.class, StreamSource.class})
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
    private IFasNtsUsageFilterWidget filterWidgetMock;
    private INtsUsageWidget usagesWidget;
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
        usagesWidget = createMock(INtsUsageWidget.class);
        ntsScenarioService = createMock(INtsScenarioService.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        filterWidgetMock = createMock(IFasNtsUsageFilterWidget.class);
        fundPoolService = createMock(IFundPoolService.class);
        reportService = createMock(IReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, fasUsageService);
        Whitebox.setInternalState(controller, ntsUsageService);
        Whitebox.setInternalState(controller, usagesWidget);
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
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        expect(fasUsageService.getUsagesCount(usageFilter)).andReturn(1).once();
        replay(filterWidgetMock, usageService, fasUsageService, filterController);
        assertEquals(1, controller.getBeansCount());
        verify(filterWidgetMock, usageService, fasUsageService, filterController);
    }

    @Test
    public void testLoadBeans() {
        usageFilter.setFiscalYear(2017);
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        Capture<Pageable> pageableCapture = new Capture<>();
        expect(fasUsageService.getUsageDtos(eq(usageFilter), capture(pageableCapture), isNull()))
            .andReturn(Collections.emptyList()).once();
        replay(filterWidgetMock, usageService, fasUsageService, filterController);
        List<UsageDto> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(filterWidgetMock, usageService, fasUsageService, filterController);
    }

    @Test
    public void testIsBatchProcessingCompleted() {
        String batchId = "65f27251-7ad0-4aba-9d4a-bf584edb4177";
        IUsageBatchStatusService usageBatchStatusService = PowerMock.createMock(IUsageBatchStatusService.class);
        Whitebox.setInternalState(controller, usageBatchStatusService);
        expect(usageBatchStatusService.isBatchProcessingCompleted(batchId,
            Sets.newHashSet(UsageStatusEnum.WORK_FOUND, UsageStatusEnum.NON_STM_RH, UsageStatusEnum.US_TAX_COUNTRY,
                UsageStatusEnum.RH_FOUND)))
            .andReturn(true).once();
        replay(usageBatchStatusService);
        assertTrue(controller.isBatchProcessingCompleted(batchId));
        verify(usageBatchStatusService);
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
    public void testIsValidFilteredUsageStatus() {
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        expect(usageService.isValidFilteredUsageStatus(usageFilter, UsageStatusEnum.ELIGIBLE))
            .andReturn(true).once();
        replay(filterController, filterWidgetMock, usageService);
        assertTrue(controller.isValidFilteredUsageStatus(UsageStatusEnum.ELIGIBLE));
        verify(filterController, filterWidgetMock, usageService);
    }

    @Test
    public void testCreateNtsScenario() {
        NtsFields ntsFields = new NtsFields();
        ntsFields.setRhMinimumAmount(new BigDecimal("300.00"));
        Scenario scenario = new Scenario();
        expect(ntsScenarioService.createScenario(SCENARIO_NAME, ntsFields, DESCRIPTION, usageFilter))
            .andReturn(scenario).once();
        expect(filterController.getWidget()).andReturn(filterWidgetMock).times(2);
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        replay(filterController, filterWidgetMock, ntsScenarioService);
        assertEquals(scenario, controller.createNtsScenario(SCENARIO_NAME, ntsFields, DESCRIPTION));
        verify(filterController, filterWidgetMock, ntsScenarioService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(NtsUsageWidget.class));
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
    public void testGetExportUsagesStreamSource() {
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = new Capture<>();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = new Capture<>();
        String fileName = "export_usage_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
                () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(DATE).once();
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        reportService.writeNtsUsageCsvReport(usageFilter, pos);
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
    public void testGetScenarioNameAssociatedWithAdditionalFund() {
        expect(ntsScenarioService.getScenarioNameByFundPoolId(USAGE_BATCH_ID)).andReturn(SCENARIO_NAME).once();
        replay(ntsScenarioService);
        assertEquals(SCENARIO_NAME, controller.getScenarioNameAssociatedWithAdditionalFund(USAGE_BATCH_ID));
        verify(ntsScenarioService);
    }

    @Test
    public void testGetUsageBatchesForAdditionalFunds() {
        expect(usageBatchService.getUsageBatchesForNtsFundPool()).andReturn(Collections.emptyList()).once();
        replay(usageBatchService);
        controller.getUsageBatchesForAdditionalFunds();
        verify(usageBatchService);
    }

    @Test
    public void testGetMarkets() {
        List<String> markets = Collections.singletonList("Bus");
        expect(ntsUsageService.getMarkets()).andReturn(markets).once();
        replay(ntsUsageService);
        assertEquals(markets, controller.getMarkets());
        verify(ntsUsageService);
    }

    @Test
    public void testLoadNtsBatch() {
        mockStatic(RupContextUtils.class);
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(BATCH_NAME);
        Usage usage1 = new Usage();
        usage1.setId("d9440e59-4943-4cdc-bb32-84edcb7febbc");
        Usage usage2 = new Usage();
        usage2.setId("43f31060-acde-477a-8f73-80fadbafaa70");
        List<String> ntsUsageIds = Arrays.asList(usage1.getId(), usage2.getId());
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        expect(usageBatchService.insertNtsBatch(usageBatch, USER_NAME)).andReturn(ntsUsageIds).once();
        ntsUsageService.sendForGettingRights(ntsUsageIds, BATCH_NAME);
        expectLastCall().once();
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidgetMock, RupContextUtils.class);
        assertEquals(2, controller.loadNtsBatch(usageBatch));
        verify(usageBatchService, filterController, filterWidgetMock, RupContextUtils.class);
    }

    @Test
    public void testGetPreServiceSeeFunds() {
        List<FundPool> additionalFunds = Collections.singletonList(new FundPool());
        IProductFamilyProvider productFamilyProvider = createMock(IProductFamilyProvider.class);
        Whitebox.setInternalState(controller, productFamilyProvider);
        expect(controller.getSelectedProductFamily()).andReturn("NTS").once();
        expect(fundPoolService.getFundPools("NTS")).andReturn(additionalFunds).once();
        replay(productFamilyProvider, fundPoolService);
        assertEquals(additionalFunds, controller.getAdditionalFunds());
        verify(productFamilyProvider, fundPoolService);
    }

    @Test
    public void testGetAdditionalFundsNotAttachedToScenario() {
        List<FundPool> additionalFunds = Collections.singletonList(new FundPool());
        expect(fundPoolService.getNtsNotAttachedToScenario()).andReturn(additionalFunds).once();
        replay(fundPoolService);
        assertEquals(additionalFunds, controller.getAdditionalFundsNotAttachedToScenario());
        verify(fundPoolService);
    }

    @Test
    public void testDeleteAdditionalFund() {
        FundPool fund = new FundPool();
        fundPoolService.deleteNtsFundPool(fund);
        expectLastCall().once();
        replay(fundPoolService);
        controller.deleteAdditionalFund(fund);
        verify(fundPoolService);
    }

    @Test
    public void testGetUsagesCountForNtsBatch() {
        UsageBatch usageBatch = new UsageBatch();
        expect(ntsUsageService.getUsagesCountForBatch(usageBatch)).andReturn(5).once();
        replay(ntsUsageService);
        assertEquals(5, controller.getUsagesCountForNtsBatch(usageBatch));
        verify(ntsUsageService);
    }

    @Test
    public void testGetRightsholder() {
        Rightsholder rightsholder = new Rightsholder();
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
        Capture<Supplier<String>> fileNameSupplierCapture = new Capture<>();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = new Capture<>();
        String fileName = "pre_service_fee_fund_batches_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
                () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(DATE).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        reportService.writeNtsWithdrawnBatchesCsvReport(
            Collections.singletonList(new UsageBatch()), BigDecimal.ONE, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, streamSourceHandler, reportService);
        IStreamSource streamSource = controller.getAdditionalFundBatchesStreamSource(
            Collections.singletonList(new UsageBatch()), BigDecimal.ONE);
        assertEquals("pre_service_fee_fund_batches_01_02_2019_03_04.csv",
            streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        verify(OffsetDateTime.class, streamSourceHandler, reportService);
    }

    @Test
    public void testGetBatchNamesWithUnclassifiedUsages() {
        expect(usageBatchService.getBatchNamesWithUnclassifiedWorks(Collections.singleton(USAGE_BATCH_ID)))
            .andReturn(Collections.singletonList("Batch with unclassified usages")).once();
        replay(usageBatchService);
        controller.getBatchNamesWithUnclassifiedWorks(Collections.singleton(USAGE_BATCH_ID));
        verify(usageBatchService);
    }

    @Test
    public void testGetBatchNamesWithInvalidStmOrNonStmUsagesState() {
        Set<String> batchIds = Collections.singleton(USAGE_BATCH_ID);
        expect(usageBatchService.getClassifcationToBatchNamesWithoutUsagesForStmOrNonStm(batchIds))
            .andReturn(Collections.emptyMap()).once();
        replay(usageBatchService);
        controller.getBatchNamesWithInvalidStmOrNonStmUsagesState(batchIds);
        verify(usageBatchService);
    }
}
