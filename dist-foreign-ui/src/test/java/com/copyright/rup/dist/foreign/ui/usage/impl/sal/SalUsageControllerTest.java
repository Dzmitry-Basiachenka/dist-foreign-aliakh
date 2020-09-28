package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalScenarioService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.ISalUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.ISalUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageWidget;

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

    private ISalUsageService salUsageService;
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
        fundPoolService = createMock(IFundPoolService.class);
        salScenarioService = createMock(ISalScenarioService.class);
        filterController = createMock(ISalUsageFilterController.class);
        filterWidget = createMock(ISalUsageFilterWidget.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        reportService = createMock(IReportService.class);
        Whitebox.setInternalState(controller, streamSourceHandler);
        Whitebox.setInternalState(controller, usagesWidget);
        Whitebox.setInternalState(controller, salUsageService);
        Whitebox.setInternalState(controller, fundPoolService);
        Whitebox.setInternalState(controller, salScenarioService);
        Whitebox.setInternalState(controller, filterController);
        Whitebox.setInternalState(controller, reportService);
        usageFilter = new UsageFilter();
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
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
        IUsageBatchService usageBatchService = createMock(IUsageBatchService.class);
        Whitebox.setInternalState(controller, usageBatchService);
        UsageBatch itemBank = new UsageBatch();
        List<String> usageIds = new ArrayList<>();
        List<Usage> usages = Collections.singletonList(new Usage());
        expect(usageBatchService.insertSalBatch(itemBank, usages)).andReturn(usageIds).once();
        salUsageService.sendForMatching(usageIds, itemBank.getName());
        expectLastCall().once();
        replay(usageBatchService);
        controller.loadItemBank(itemBank, usages);
        verify(usageBatchService);
    }

    @Test
    public void getBeansCount() {
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
        Capture<Pageable> pageableCapture = new Capture<>();
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
        Capture<Supplier<String>> fileNameSupplierCapture = new Capture<>();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = new Capture<>();
        String fileName = "export_usage_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> isSupplier = () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(DATE).once();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(usageFilter).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", isSupplier)).once();
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
}
