package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.AclciLicenseTypeEnum;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.integration.telesales.api.ITelesalesService;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchStatusService;
import com.copyright.rup.dist.foreign.service.impl.aclci.AclciUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.AclciUsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageWidget;

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
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link AclciUsageController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/21/2022
 *
 * @author Aliaksanr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ProcessingResult.class, OffsetDateTime.class, StreamSource.class})
public class AclciUsageControllerTest {

    private static final String USAGE_BATCH_NAME = "ACLCI Usage Batch";
    private static final String FUND_POOL_NAME = "ACLCI Fund Pool";
    private static final String ACLCI_PRODUCT_FAMILY = "ACLCI";
    private static final OffsetDateTime DATE = OffsetDateTime.of(2020, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
    private static final Long LICENSEE_ACCOUNT_NUMBER = 1111L;
    private static final String LICENSEE_NAME = "Acuson Corporation";

    private AclciUsageController controller;
    private IAclciUsageFilterController filterController;
    private IAclciUsageFilterWidget filterWidget;
    private IUsageBatchService usageBatchService;
    private IReportService reportService;
    private CsvProcessorFactory csvProcessorFactory;
    private IStreamSourceHandler streamSourceHandler;
    private AclciUsageService aclciUsageService;
    private ITelesalesService telesalesService;
    private IFundPoolService fundPoolService;
    private UsageFilter usageFilter;
    private IAclciUsageWidget usagesWidget;

    @Before
    public void setUp() {
        controller = new AclciUsageController();
        filterController = createMock(IAclciUsageFilterController.class);
        filterWidget = createMock(IAclciUsageFilterWidget.class);
        usageBatchService = createMock(IUsageBatchService.class);
        reportService = createMock(IReportService.class);
        csvProcessorFactory = createMock(CsvProcessorFactory.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        aclciUsageService = createMock(AclciUsageService.class);
        telesalesService = createMock(ITelesalesService.class);
        fundPoolService = createMock(IFundPoolService.class);
        usagesWidget = createMock(IAclciUsageWidget.class);
        Whitebox.setInternalState(controller, filterController);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, csvProcessorFactory);
        Whitebox.setInternalState(controller, streamSourceHandler);
        Whitebox.setInternalState(controller, aclciUsageService);
        Whitebox.setInternalState(controller, telesalesService);
        Whitebox.setInternalState(controller, fundPoolService);
        Whitebox.setInternalState(controller, usagesWidget);
        usageFilter = new UsageFilter();
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(AclciUsageWidget.class));
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
    public void testLoadUsageData() {
        //TODO: implement
    }

    @Test
    public void testGetBeansCount() {
        usageFilter.setLicenseTypes(Set.of(AclciLicenseTypeEnum.CURR_REPUB_K12));
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        expect(aclciUsageService.getUsagesCount(usageFilter)).andReturn(1).once();
        replay(filterWidget, aclciUsageService, filterController);
        assertEquals(1, controller.getBeansCount());
        verify(filterWidget, aclciUsageService, filterController);
    }

    @Test
    public void testLoadBeans() {
        usageFilter.setLicenseTypes(Set.of(AclciLicenseTypeEnum.CURR_REPUB_K12));
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        Capture<Pageable> pageableCapture = newCapture();
        expect(aclciUsageService.getUsageDtos(eq(usageFilter), capture(pageableCapture), isNull()))
            .andReturn(List.of(new UsageDto())).once();
        replay(filterWidget, aclciUsageService, filterController);
        List<UsageDto> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(filterWidget, aclciUsageService, filterController);
    }

    @Test
    public void testGetExportUsagesStreamSource() {
        mockStatic(OffsetDateTime.class);
        usageFilter.setProductFamily(ACLCI_PRODUCT_FAMILY);
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
        reportService.writeAclciUsageCsvReport(usageFilter, pos);
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
        //TODO: implement
    }

    @Test
    public void testIsBatchProcessingCompleted() {
        String batchId = "17f8e1a0-8e78-4e4a-9a1b-7e408288abb8";
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
        UsageBatch usageBatch = new UsageBatch();
        usageBatchService.deleteAclciUsageBatch(usageBatch);
        expectLastCall().once();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        filterWidget.clearFilter();
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidget);
        controller.deleteUsageBatch(usageBatch);
        verify(usageBatchService, filterController, filterWidget);
    }

    @Test
    public void testGetAclciUsageCsvProcessor() {
        AclciUsageCsvProcessor processor = createMock(AclciUsageCsvProcessor.class);
        expect(csvProcessorFactory.getAclciUsageCsvProcessor()).andReturn(processor).once();
        replay(csvProcessorFactory);
        assertSame(processor, controller.getAclciUsageCsvProcessor());
        verify(csvProcessorFactory);
    }

    @Test
    public void testLoadAclciUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(USAGE_BATCH_NAME);
        List<Usage> usages = List.of(new Usage());
        List<String> insertedUsageIds = List.of("407be4e7-4f2e-4d5f-8610-09070c37880f");
        expect(usageBatchService.insertAclciBatch(usageBatch, usages)).andReturn(insertedUsageIds).once();
        aclciUsageService.sendForMatching(insertedUsageIds, USAGE_BATCH_NAME);
        expectLastCall().once();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        filterWidget.clearFilter();
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidget);
        controller.loadAclciUsageBatch(usageBatch, usages);
        verify(usageBatchService, filterController, filterWidget);
    }

    @Test
    public void testGetErrorResultStreamSource() {
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = "Error_for_aclci usages";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
            () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(streamSourceHandler.getCsvStreamSource(
            capture(fileNameSupplierCapture), eq(null), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, null, "csv", inputStreamSupplier)).once();
        ProcessingResult<Usage> processingResult = createMock(ProcessingResult.class);
        processingResult.writeToFile(pos);
        expectLastCall().once();
        replay(streamSourceHandler, processingResult);
        IStreamSource streamSource = controller.getErrorResultStreamSource("aclci usages.csv", processingResult);
        assertEquals("Error_for_aclci_usages.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        assertNotNull(posConsumer);
        verify(streamSourceHandler, processingResult);
    }

    @Test
    public void testGetLicenseeName() {
        expect(telesalesService.getLicenseeName(LICENSEE_ACCOUNT_NUMBER)).andReturn(LICENSEE_NAME).once();
        replay(telesalesService);
        assertSame(LICENSEE_NAME, controller.getLicenseeName(LICENSEE_ACCOUNT_NUMBER));
        verify(telesalesService);
    }

    @Test
    public void testAclciFundPoolExists() {
        expect(fundPoolService.fundPoolExists(ACLCI_PRODUCT_FAMILY, FUND_POOL_NAME)).andReturn(true).once();
        replay(fundPoolService);
        assertTrue(controller.aclciFundPoolExists(FUND_POOL_NAME));
        verify(fundPoolService);
    }

    @Test
    public void testCalculateAclciFundPoolAmounts() {
        FundPool fundPool = new FundPool();
        expect(fundPoolService.calculateAclciFundPoolAmounts(fundPool)).andReturn(fundPool).once();
        replay(fundPoolService);
        assertSame(fundPool, controller.calculateAclciFundPoolAmounts(fundPool));
        verify(fundPoolService);
    }

    @Test
    public void testCreateAclciFundPool() {
        FundPool fundPool = new FundPool();
        fundPoolService.createAclciFundPool(fundPool);
        expectLastCall().once();
        replay(fundPoolService);
        controller.createAclciFundPool(fundPool);
        verify(fundPoolService);
    }

    @Test
    public void testIsValidStatusFilterAppliedRhNotFound() {
        usageFilter.setUsageStatus(UsageStatusEnum.RH_NOT_FOUND);
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        replay(filterController, filterWidget);
        assertTrue(controller.isValidStatusFilterApplied());
        verify(filterController, filterWidget);
    }

    @Test
    public void testIsValidStatusFilterAppliedWorkNotGranted() {
        usageFilter.setUsageStatus(UsageStatusEnum.WORK_NOT_GRANTED);
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        replay(filterController, filterWidget);
        assertTrue(controller.isValidStatusFilterApplied());
        verify(filterController, filterWidget);
    }

    @Test
    public void testIsValidStatusFilterAppliedWorkEligible() {
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        replay(filterController, filterWidget);
        assertFalse(controller.isValidStatusFilterApplied());
        verify(filterController, filterWidget);
    }

    @Test
    public void testGetUsageDtosToUpdate() {
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        expect(aclciUsageService.getUsageDtos(usageFilter, null, null)).andReturn(List.of(new UsageDto())).once();
        replay(filterWidget, aclciUsageService, filterController);
        List<UsageDto> result = controller.getUsageDtosToUpdate();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(filterWidget, aclciUsageService, filterController);
    }

    @Test
    public void testUpdateToEligibleByIds() {
        Set<String> usageIds = Set.of("ed475bf5-afd5-4241-809d-77c00a860247");
        String reason = "invalid work";
        aclciUsageService.updateToEligibleByIds(usageIds, 2000017001L, null, reason);
        expectLastCall().once();
        replay(aclciUsageService);
        controller.updateToEligibleByIds(usageIds,  2000017001L, null, reason);
        verify(aclciUsageService);
    }
}
