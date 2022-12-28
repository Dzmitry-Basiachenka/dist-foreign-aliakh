package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.SalGradeGroupEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchStatusService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalScenarioService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageWidget;

import com.google.common.collect.Sets;
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
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link SalUsageController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/28/2020
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, StreamSource.class})
public class SalUsageControllerTest {

    private static final OffsetDateTime DATE = OffsetDateTime.of(2020, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
    private static final String FUND_POOL_ID = "76b16c8d-0bea-4135-9611-6c52e53bfbea";
    private static final String SCENARIO_NAME = "SAL Scenario 2020";
    private static final String USAGE_ID = "05e79246-1289-4652-aac6-d69fec90c091";
    private static final Long RH_ACCOUNT_NUMBER = 123456785L;
    private static final String REASON = "Manual RH update";

    private ISalUsageService salUsageService;
    private IUsageService usageService;
    private IUsageBatchService usageBatchService;
    private IFundPoolService fundPoolService;
    private ISalScenarioService salScenarioService;
    private SalUsageController controller;
    private ISalUsageWidget usagesWidget;
    private ISalUsageFilterController filterController;
    private ISalUsageFilterWidget filterWidget;
    private IStreamSourceHandler streamSourceHandler;
    private UsageFilter usageFilter;
    private IReportService reportService;

    @Before
    public void setUp() {
        controller = new SalUsageController();
        usagesWidget = createMock(ISalUsageWidget.class);
        salUsageService = createMock(ISalUsageService.class);
        usageService = createMock(IUsageService.class);
        usageBatchService = createMock(IUsageBatchService.class);
        fundPoolService = createMock(IFundPoolService.class);
        salScenarioService = createMock(ISalScenarioService.class);
        filterController = createMock(ISalUsageFilterController.class);
        filterWidget = createMock(ISalUsageFilterWidget.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        reportService = createMock(IReportService.class);
        Whitebox.setInternalState(controller, streamSourceHandler);
        Whitebox.setInternalState(controller, usagesWidget);
        Whitebox.setInternalState(controller, salUsageService);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, fundPoolService);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, salScenarioService);
        Whitebox.setInternalState(controller, filterController);
        Whitebox.setInternalState(controller, reportService);
        usageFilter = new UsageFilter();
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(SalUsageWidget.class));
    }

    @Test
    public void testOnFilterChanged() {
        usagesWidget.refresh();
        expectLastCall().once();
        replay(usagesWidget);
        controller.onFilterChanged(new FilterChangedEvent(new HorizontalLayout()));
        verify(usagesWidget);
    }

    @Test
    public void testLoadItemBank() {
        UsageBatch itemBank = new UsageBatch();
        List<String> usageIds = new ArrayList<>();
        List<Usage> usages = Collections.singletonList(new Usage());
        expect(usageBatchService.insertSalBatch(itemBank, usages)).andReturn(usageIds).once();
        salUsageService.sendForMatching(usageIds, itemBank.getName());
        expectLastCall().once();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        filterWidget.clearFilter();
        expectLastCall().once();
        replay(usageBatchService, salUsageService, filterController, filterWidget);
        controller.loadItemBank(itemBank, usages);
        verify(usageBatchService, salUsageService, filterController, filterWidget);
    }

    @Test
    public void testLoadUsageData() {
        UsageBatch itemBank = new UsageBatch();
        List<Usage> usages = Collections.singletonList(new Usage());
        usageBatchService.addUsageDataToSalBatch(itemBank, usages);
        expectLastCall().once();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        filterWidget.clearFilter();
        expectLastCall().once();
        replay(salUsageService, filterController, filterWidget);
        controller.loadUsageData(itemBank, usages);
        verify(salUsageService, filterController, filterWidget);
    }

    @Test
    public void testGetBeansCount() {
        usageFilter.setSalDetailType(SalDetailTypeEnum.IB);
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        expect(salUsageService.getUsagesCount(usageFilter)).andReturn(1).once();
        replay(filterWidget, salUsageService, filterController);
        assertEquals(1, controller.getBeansCount());
        verify(filterWidget, salUsageService, filterController);
    }

    @Test
    public void testLoadBeans() {
        usageFilter.setSalDetailType(SalDetailTypeEnum.IB);
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        Capture<Pageable> pageableCapture = newCapture();
        expect(salUsageService.getUsageDtos(eq(usageFilter), capture(pageableCapture), isNull()))
            .andReturn(Collections.singletonList(new UsageDto())).once();
        replay(filterWidget, salUsageService, filterController);
        List<UsageDto> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(filterWidget, salUsageService, filterController);
    }

    @Test
    public void testGetExportUsagesStreamSource() {
        mockStatic(OffsetDateTime.class);
        usageFilter.setProductFamily("SAL");
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
        reportService.writeSalUsageCsvReport(usageFilter, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, filterWidget, filterController, streamSourceHandler, reportService);
        IStreamSource streamSource = controller.getExportUsagesStreamSource();
        assertEquals("export_usage_01_02_2020_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        assertNotNull(posConsumer);
        posConsumer.accept(pos);
        verify(OffsetDateTime.class, filterWidget, filterController, streamSourceHandler, reportService);
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
    public void testIsValidStatusFilterAppliedRhNotFound() {
        usageFilter.setUsageStatus(UsageStatusEnum.RH_NOT_FOUND);
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        replay(filterController, filterWidget, usageService);
        assertTrue(controller.isValidStatusFilterApplied());
        verify(filterController, filterWidget, usageService);
    }

    @Test
    public void testIsValidStatusFilterAppliedWorkNotGranted() {
        usageFilter.setUsageStatus(UsageStatusEnum.WORK_NOT_GRANTED);
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        replay(filterController, filterWidget, usageService);
        assertTrue(controller.isValidStatusFilterApplied());
        verify(filterController, filterWidget, usageService);
    }

    @Test
    public void testIsValidStatusFilterAppliedWorkEligible() {
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        replay(filterController, filterWidget, usageService);
        assertFalse(controller.isValidStatusFilterApplied());
        verify(filterController, filterWidget, usageService);
    }

    @Test
    public void testGetIneligibleBatchesNames() {
        Set<String> batchIds = Collections.singleton("0ddb1cbf-4516-40b2-bffa-33d8876fa774");
        List<String> batchNames = Collections.singletonList("batch name");
        expect(usageBatchService.getIneligibleBatchesNames(batchIds)).andReturn(batchNames).once();
        replay(usageBatchService);
        assertEquals(batchNames, controller.getIneligibleBatchesNames(batchIds));
        verify(usageBatchService);
    }

    @Test
    public void testGetProcessingBatchesNames() {
        Set<String> batchIds = Collections.singleton("7709a5ef-72b7-406c-8428-3422833f6e46");
        List<String> batchNames = Collections.singletonList("batch name");
        expect(usageBatchService.getProcessingSalBatchesNames(batchIds)).andReturn(batchNames).once();
        replay(usageBatchService);
        assertEquals(batchNames, controller.getProcessingBatchesNames(batchIds));
        verify(usageBatchService);
    }

    @Test
    public void testGetFundPoolsNotAttachedToScenario() {
        List<FundPool> fundPools = Collections.singletonList(new FundPool());
        expect(fundPoolService.getSalNotAttachedToScenario()).andReturn(fundPools).once();
        replay(fundPoolService);
        assertEquals(fundPools, controller.getFundPoolsNotAttachedToScenario());
        verify(fundPoolService);
    }

    @Test
    public void testGetUsageDataGradeGroups() {
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        List<SalGradeGroupEnum> gradeGroups = Collections.singletonList(SalGradeGroupEnum.ITEM_BANK);
        expect(salUsageService.getUsageDataGradeGroups(usageFilter)).andReturn(gradeGroups).once();
        replay(filterController, filterWidget, salUsageService);
        assertSame(gradeGroups, controller.getUsageDataGradeGroups());
        verify(filterController, filterWidget, salUsageService);
    }

    @Test
    public void testGetFundPools() {
        List<FundPool> fundPools = Collections.singletonList(new FundPool());
        expect(fundPoolService.getFundPools("SAL")).andReturn(fundPools).once();
        replay(fundPoolService);
        assertEquals(fundPools, controller.getFundPools());
        verify(fundPoolService);
    }

    @Test
    public void testGetScenarioNameAssociatedWithFundPool() {
        expect(salScenarioService.getScenarioNameByFundPoolId(FUND_POOL_ID)).andReturn("SAL Scenario").once();
        replay(salScenarioService);
        assertEquals("SAL Scenario", controller.getScenarioNameAssociatedWithFundPool(FUND_POOL_ID));
        verify(salScenarioService);
    }

    @Test
    public void testDeleteFundPool() {
        FundPool fundPool = new FundPool();
        fundPoolService.deleteSalFundPool(fundPool);
        expectLastCall().once();
        replay(fundPoolService);
        controller.deleteFundPool(fundPool);
        verify(fundPoolService);
    }

    @Test
    public void testDeleteUsageData() {
        UsageBatch usageBatch = new UsageBatch();
        salUsageService.deleteUsageData(usageBatch);
        expectLastCall().once();
        controller.refreshWidget();
        expectLastCall();
        replay(salScenarioService);
        controller.deleteUsageData(usageBatch);
        verify(salScenarioService);
    }

    @Test
    public void testIsBatchProcessingCompleted() {
        String batchId = "dde634cb-148b-4f1f-bade-6c0b7328b2b9";
        IUsageBatchStatusService usageBatchStatusService = createMock(IUsageBatchStatusService.class);
        Whitebox.setInternalState(controller, usageBatchStatusService);
        expect(usageBatchStatusService.isBatchProcessingCompleted(batchId,
            Sets.newHashSet(UsageStatusEnum.NEW, UsageStatusEnum.WORK_FOUND, UsageStatusEnum.RH_FOUND)))
            .andReturn(true).once();
        replay(usageBatchStatusService);
        assertTrue(controller.isBatchProcessingCompleted(batchId));
        verify(usageBatchStatusService);
    }

    @Test
    public void testDeleteUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatchService.deleteSalUsageBatch(usageBatch);
        expectLastCall().once();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        filterWidget.clearFilter();
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidget);
        controller.deleteUsageBatch(usageBatch);
        verify(usageBatchService, filterController, filterWidget);
    }

    @Test
    public void testUsageDataExists() {
        String batchId = "5f59edf0-422b-40fd-8e54-c8e64f2af385";
        expect(salUsageService.usageDataExists(batchId)).andReturn(true).once();
        replay(salUsageService);
        assertTrue(controller.usageDataExists(batchId));
        verify(salUsageService);
    }

    @Test
    public void testGetBatchesNotAttachedToScenario() {
        List<UsageBatch> batches = Collections.singletonList(new UsageBatch());
        expect(usageBatchService.getSalNotAttachedToScenario()).andReturn(batches).once();
        replay(usageBatchService);
        assertEquals(batches, controller.getBatchesNotAttachedToScenario());
        verify(usageBatchService);
    }

    @Test
    public void testCalculateFundPoolAmounts() {
        FundPool fundPool = new FundPool();
        expect(fundPoolService.calculateSalFundPoolAmounts(fundPool)).andReturn(fundPool).once();
        replay(fundPoolService);
        assertEquals(fundPool, controller.calculateFundPoolAmounts(fundPool));
        verify(fundPoolService);
    }

    @Test
    public void testCreateFundPool() {
        FundPool fundPool = new FundPool();
        fundPoolService.createSalFundPool(fundPool);
        expectLastCall().once();
        replay(fundPoolService);
        controller.createFundPool(fundPool);
        verify(fundPoolService);
    }

    @Test
    public void testGetSelectedUsageBatch() {
        String batchId = "5b5793c4-6efc-494c-be2a-ffd1b0564bf8";
        usageFilter.setUsageBatchesIds(Collections.singleton(batchId));
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(batchId);
        expect(usageBatchService.getUsageBatchById("5b5793c4-6efc-494c-be2a-ffd1b0564bf8"))
            .andReturn(usageBatch).once();
        replay(usageBatchService, filterController, filterWidget);
        assertSame(usageBatch, controller.getSelectedUsageBatch());
        verify(usageBatchService, filterController, filterWidget);
    }

    @Test
    public void testCreateSalScenario() {
        Scenario scenario = new Scenario();
        scenario.setName(SCENARIO_NAME);
        expect(filterController.getWidget()).andReturn(filterWidget).times(2);
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        filterWidget.clearFilter();
        expectLastCall().once();
        expect(salScenarioService.createScenario(SCENARIO_NAME, FUND_POOL_ID, "description", usageFilter))
            .andReturn(scenario).once();
        replay(filterController, filterWidget, salScenarioService);
        assertSame(scenario, controller.createSalScenario(SCENARIO_NAME, FUND_POOL_ID, "description"));
        verify(filterController, filterWidget, salScenarioService);
    }

    @Test
    public void testGetUsageDtosForRhUpdate() {
        usageFilter.setUsageBatchesIds(Collections.singleton("05e79246-1289-4652-aac6-d69fec90c091"));
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        expect(salUsageService.getUsageDtos(eq(usageFilter), isNull(), isNull()))
            .andReturn(Collections.singletonList(new UsageDto())).once();
        replay(filterWidget, salUsageService, filterController);
        List<UsageDto> result = controller.getUsageDtosForRhUpdate();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(filterWidget, salUsageService, filterController);
    }

    @Test
    public void testUpdateToEligibleWithRhAccountNumber() {
        salUsageService.updateToEligibleWithRhAccountNumber(Collections.singleton(USAGE_ID), RH_ACCOUNT_NUMBER, REASON);
        expectLastCall().once();
        replay(salUsageService);
        controller.updateToEligibleWithRhAccountNumber(Collections.singleton(USAGE_ID), RH_ACCOUNT_NUMBER, REASON);
        verify(salUsageService);
    }
}
