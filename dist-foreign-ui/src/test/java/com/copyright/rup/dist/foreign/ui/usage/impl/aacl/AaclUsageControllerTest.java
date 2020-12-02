package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IResearchService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclScenarioService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.AaclFundPoolCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.ClassifiedUsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageWidget;

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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link AaclUsageController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/23/2019
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ByteArrayStreamSource.class, OffsetDateTime.class, StreamSource.class})
public class AaclUsageControllerTest {

    private static final String FUND_POOL_ID = "76b16c8d-0bea-4135-9611-6c52e53bfbea";
    private static final String FUND_POOL_NAME = "fund pool name";
    private static final OffsetDateTime DATE = OffsetDateTime.of(2020, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));

    private AaclUsageController controller;
    private IAaclUsageWidget usagesWidget;
    private IAaclUsageFilterController filterController;
    private IAaclUsageFilterWidget filterWidgetMock;
    private IUsageBatchService usageBatchService;
    private IResearchService researchService;
    private IAaclUsageService aaclUsageService;
    private IFundPoolService fundPoolService;
    private IAaclScenarioService aaclScenarioService;
    private CsvProcessorFactory csvProcessorFactory;
    private UsageFilter usageFilter;
    private IStreamSourceHandler streamSourceHandler;
    private IReportService reportService;
    private IPublicationTypeService publicationTypeService;
    private ILicenseeClassService licenseeClassService;

    @Before
    public void setUp() {
        controller = new AaclUsageController();
        usagesWidget = createMock(IAaclUsageWidget.class);
        usageBatchService = createMock(IUsageBatchService.class);
        filterController = createMock(IAaclUsageFilterController.class);
        filterWidgetMock = createMock(IAaclUsageFilterWidget.class);
        researchService = createMock(IResearchService.class);
        aaclUsageService = createMock(IAaclUsageService.class);
        fundPoolService = createMock(IFundPoolService.class);
        aaclScenarioService = createMock(IAaclScenarioService.class);
        csvProcessorFactory = createMock(CsvProcessorFactory.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        reportService = createMock(IReportService.class);
        publicationTypeService = createMock(IPublicationTypeService.class);
        licenseeClassService = createMock(ILicenseeClassService.class);
        Whitebox.setInternalState(controller, usagesWidget);
        Whitebox.setInternalState(controller, usageBatchService);
        Whitebox.setInternalState(controller, researchService);
        Whitebox.setInternalState(controller, usagesWidget);
        Whitebox.setInternalState(controller, filterController);
        Whitebox.setInternalState(controller, aaclUsageService);
        Whitebox.setInternalState(controller, fundPoolService);
        Whitebox.setInternalState(controller, createMock(IScenarioService.class));
        Whitebox.setInternalState(controller, aaclScenarioService);
        Whitebox.setInternalState(controller, csvProcessorFactory);
        Whitebox.setInternalState(controller, streamSourceHandler);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, publicationTypeService);
        Whitebox.setInternalState(controller, licenseeClassService);
        usageFilter = new UsageFilter();
    }

    @Test
    public void getBeansCount() {
        usageFilter.setFiscalYear(2017);
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        expect(aaclUsageService.getUsagesCount(usageFilter)).andReturn(1).once();
        replay(filterWidgetMock, aaclUsageService, filterController);
        assertEquals(1, controller.getBeansCount());
        verify(filterWidgetMock, aaclUsageService, filterController);
    }

    @Test
    public void testLoadBeans() {
        usageFilter.setFiscalYear(2017);
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        Capture<Pageable> pageableCapture = new Capture<>();
        expect(aaclUsageService.getUsageDtos(eq(usageFilter), capture(pageableCapture), isNull()))
            .andReturn(Collections.emptyList()).once();
        replay(filterWidgetMock, aaclUsageService, filterController);
        List<UsageDto> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(filterWidgetMock, aaclUsageService, filterController);
    }

    @Test
    public void testIsValidFilteredUsageStatus() {
        usageFilter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        expect(aaclUsageService.isValidFilteredUsageStatus(usageFilter, UsageStatusEnum.RH_FOUND))
            .andReturn(true).once();
        replay(filterController, filterWidgetMock, aaclUsageService);
        assertTrue(controller.isValidFilteredUsageStatus(UsageStatusEnum.RH_FOUND));
        verify(filterController, filterWidgetMock, aaclUsageService);
    }

    @Test
    public void testGetInvalidRightsholders() {
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        expect(aaclUsageService.getInvalidRightsholdersByFilter(usageFilter))
            .andReturn(Collections.singletonList(7000000001L)).once();
        replay(filterController, filterWidgetMock, aaclUsageService);
        assertEquals(Collections.singletonList(7000000001L), controller.getInvalidRightsholders());
        verify(filterController, filterWidgetMock, aaclUsageService);
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
        replay(usageBatchService, filterController, filterWidgetMock, researchService);
        controller.onFilterChanged(filterChangedEvent);
        verify(usageBatchService, filterController, filterWidgetMock, researchService);
    }

    @Test
    public void testLoadUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName("AACL Batch");
        List<Usage> usages = Collections.singletonList(new Usage());
        List<String> insertedUsageIds =
            Arrays.asList("2ad91b97-5288-47f4-a454-ed3a4388993b", "7c8f870f-97e6-4f9e-a8f8-e4b088dad057");
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        expect(usageBatchService.insertAaclBatch(usageBatch, usages)).andReturn(insertedUsageIds).once();
        aaclUsageService.sendForMatching(insertedUsageIds, "AACL Batch");
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidgetMock, researchService);
        assertEquals(2, controller.loadUsageBatch(usageBatch, usages));
        verify(usageBatchService, filterController, filterWidgetMock, researchService);
    }

    @Test
    public void testGetFundPoolById() {
        FundPool fundPool = new FundPool();
        expect(fundPoolService.getFundPoolById(FUND_POOL_ID)).andReturn(fundPool).once();
        replay(fundPoolService);
        assertSame(fundPool, controller.getFundPoolById(FUND_POOL_ID));
        verify(fundPoolService);
    }

    @Test
    public void testGetFundPools() {
        List<FundPool> fundPools = Collections.singletonList(new FundPool());
        expect(fundPoolService.getFundPools("AACL")).andReturn(fundPools).once();
        replay(fundPoolService);
        assertEquals(fundPools, controller.getFundPools());
        verify(fundPoolService);
    }

    @Test
    public void testGetFundPoolsNotAttachedToScenario() {
        List<FundPool> fundPools = Collections.singletonList(new FundPool());
        expect(fundPoolService.getAaclNotAttachedToScenario()).andReturn(fundPools).once();
        replay(fundPoolService);
        assertEquals(fundPools, controller.getFundPoolsNotAttachedToScenario());
        verify(fundPoolService);
    }

    @Test
    public void testGetFundPoolDetails() {
        List<FundPoolDetail> details = Collections.singletonList(new FundPoolDetail());
        expect(fundPoolService.getDetailsByFundPoolId(FUND_POOL_ID)).andReturn(details).once();
        replay(fundPoolService);
        assertEquals(details, controller.getFundPoolDetails(FUND_POOL_ID));
        verify(fundPoolService);
    }

    @Test
    public void testGetScenarioNameAssociatedWithFundPool() {
        expect(aaclScenarioService.getScenarioNameByFundPoolId(FUND_POOL_ID)).andReturn("Scenario 1").once();
        replay(aaclScenarioService);
        assertEquals("Scenario 1", controller.getScenarioNameAssociatedWithFundPool(FUND_POOL_ID));
        verify(aaclScenarioService);
    }

    @Test
    public void testDeleteFundPool() {
        FundPool fundPool = new FundPool();
        fundPoolService.deleteAaclFundPool(fundPool);
        expectLastCall().once();
        replay(fundPoolService);
        controller.deleteFundPool(fundPool);
        verify(fundPoolService);
    }

    @Test
    public void testDeleteUsageBatch() {
        usageFilter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        UsageBatch batch = new UsageBatch();
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        usageBatchService.deleteAaclUsageBatch(batch);
        expectLastCall().once();
        replay(usageBatchService, filterController, filterWidgetMock);
        controller.deleteUsageBatch(batch);
        verify(usageBatchService, filterController, filterWidgetMock);
    }

    @Test
    public void testGetSendForClassificationUsagesStreamSource() throws IOException {
        UsageFilter filter = new UsageFilter();
        filter.setProductFamily("FAS");
        Capture<OutputStream> outputStreamCapture = new Capture<>();
        OffsetDateTime date = OffsetDateTime.parse("2020-01-21T02:10:37-05:00");
        mockStatic(OffsetDateTime.class);
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(filter).once();
        researchService.sendForClassification(same(filter), capture(outputStreamCapture));
        expectLastCall().andAnswer(() -> {
            OutputStream out = outputStreamCapture.getValue();
            IOUtils.write("report content", out);
            IOUtils.closeQuietly(out);
            return null;
        }).once();
        expect(OffsetDateTime.now()).andReturn(date).once();
        replay(OffsetDateTime.class, usageBatchService, filterController, filterWidgetMock, researchService);
        IStreamSource streamSource = controller.getSendForClassificationUsagesStreamSource();
        assertEquals("send_for_classification_01_21_2020_02_10.csv", streamSource.getSource().getKey().get());
        assertEquals("report content", IOUtils.toString(streamSource.getSource().getValue().get()));
        verify(OffsetDateTime.class, usageBatchService, filterController, filterWidgetMock, researchService);
    }

    @Test
    public void testGetExportUsagesStreamSource() {
        mockStatic(OffsetDateTime.class);
        usageFilter.setProductFamily("AACL");
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
        reportService.writeAaclUsageCsvReport(usageFilter, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, filterWidgetMock, filterController, streamSourceHandler, reportService);
        IStreamSource streamSource = controller.getExportUsagesStreamSource();
        assertEquals("export_usage_01_02_2020_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        assertNotNull(posConsumer);
        posConsumer.accept(pos);
        verify(OffsetDateTime.class, filterWidgetMock, filterController, streamSourceHandler, reportService);
    }

    @Test
    public void testGetClassifiedUsageCsvProcessor() {
        ClassifiedUsageCsvProcessor processorMock = createMock(ClassifiedUsageCsvProcessor.class);
        expect(csvProcessorFactory.getClassifiedUsageCsvProcessor()).andReturn(processorMock).once();
        replay(csvProcessorFactory);
        assertSame(processorMock, controller.getClassifiedUsageCsvProcessor());
        replay(csvProcessorFactory);
    }

    @Test
    public void testLoadClassifiedUsages() {
        List<AaclClassifiedUsage> classifiedUsages =
            Arrays.asList(new AaclClassifiedUsage(), new AaclClassifiedUsage());
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        filterWidgetMock.clearFilter();
        expectLastCall().once();
        expect(aaclUsageService.updateClassifiedUsages(classifiedUsages)).andReturn(1).once();
        replay(aaclUsageService, filterController, filterWidgetMock);
        controller.loadClassifiedUsages(classifiedUsages);
        verify(aaclUsageService, filterController, filterWidgetMock);
    }

    @Test
    public void testFundPoolExists() {
        expect(fundPoolService.fundPoolExists(FdaConstants.AACL_PRODUCT_FAMILY, FUND_POOL_NAME)).andReturn(true).once();
        replay(fundPoolService);
        assertTrue(controller.fundPoolExists(FUND_POOL_NAME));
        verify(fundPoolService);
    }

    @Test
    public void testGetResearchedUsagesCsvProcessor() {
        AaclFundPoolCsvProcessor processor = createMock(AaclFundPoolCsvProcessor.class);
        expect(csvProcessorFactory.getAaclFundPoolCsvProcessor()).andReturn(processor).once();
        replay(csvProcessorFactory);
        assertSame(processor, controller.getAaclFundPoolCsvProcessor());
        replay(csvProcessorFactory);
    }

    @Test
    public void testInsertFundPool() {
        FundPool fundPool = new FundPool();
        List<FundPoolDetail> details = Collections.singletonList(new FundPoolDetail());
        expect(fundPoolService.createAaclFundPool(fundPool, details)).andReturn(1).once();
        replay(fundPoolService);
        assertEquals(1, controller.insertFundPool(fundPool, details));
        verify(fundPoolService);
    }

    @Test
    public void testGetDefaultUsageAges() {
        List<UsageAge> usageAges = buildUsageAges();
        expect(aaclUsageService.getDefaultUsageAges(Lists.newArrayList(2020, 2018))).andReturn(usageAges).once();
        replay(aaclUsageService, filterWidgetMock, filterController);
        assertEquals(usageAges, controller.getDefaultUsageAges(Lists.newArrayList(2020, 2018)));
        verify(aaclUsageService, filterWidgetMock, filterController);
    }

    @Test
    public void testGetUsageAges() {
        List<UsageAge> usageAges = buildUsageAges();
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        expect(aaclUsageService.getUsageAges(usageFilter)).andReturn(usageAges).once();
        replay(aaclUsageService, filterWidgetMock, filterController);
        assertEquals(usageAges, controller.getUsageAges());
        verify(aaclUsageService, filterWidgetMock, filterController);
    }

    @Test
    public void testGetProcessingBatchesNames() {
        Set<String> batchIds = Collections.singleton("5d4f7674-4f0a-404e-ad23-7096b667402b");
        List<String> batchNames = Collections.singletonList("batch name");
        expect(usageBatchService.getProcessingAaclBatchesNames(batchIds)).andReturn(batchNames).once();
        replay(usageBatchService);
        assertEquals(batchNames, controller.getProcessingBatchesNames(batchIds));
        verify(usageBatchService);
    }

    @Test
    public void testGetIneligibleBatchesNames() {
        Set<String> batchIds = Collections.singleton("5d4f7674-4f0a-404e-ad23-7096b667402b");
        List<String> batchNames = Collections.singletonList("batch name");
        expect(usageBatchService.getIneligibleBatchesNames(batchIds)).andReturn(batchNames).once();
        replay(usageBatchService);
        assertEquals(batchNames, controller.getIneligibleBatchesNames(batchIds));
        verify(usageBatchService);
    }

    @Test
    public void testGetBatchesNamesToScenariosNames() {
        Set<String> batchIds = Collections.singleton("5d4f7674-4f0a-404e-ad23-7096b667402b");
        Map<String, String> batchToScenarioNames = Collections.singletonMap("batch name", "scenario name");
        expect(usageBatchService.getBatchesNamesToScenariosNames(batchIds)).andReturn(batchToScenarioNames).once();
        replay(usageBatchService);
        assertEquals(batchToScenarioNames, controller.getBatchesNamesToScenariosNames(batchIds));
        verify(usageBatchService);
    }

    @Test
    public void testIsValidForClassification() {
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        expect(aaclUsageService.isValidForClassification(usageFilter)).andReturn(true).once();
        replay(aaclUsageService, filterWidgetMock, filterController);
        assertTrue(controller.isValidForClassification());
        verify(aaclUsageService, filterWidgetMock, filterController);
    }

    @Test
    public void testIsValidForClassificationNegativeResult() {
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        expect(aaclUsageService.isValidForClassification(usageFilter)).andReturn(false).once();
        replay(aaclUsageService, filterWidgetMock, filterController);
        assertFalse(controller.isValidForClassification());
        verify(aaclUsageService, filterWidgetMock, filterController);
    }

    @Test
    public void testGetPublicationTypes() {
        List<PublicationType> pubTypes = Collections.singletonList(buildPublicationType("Book", "1.00"));
        expect(publicationTypeService.getPublicationTypes()).andReturn(pubTypes).once();
        replay(publicationTypeService);
        assertEquals(pubTypes, controller.getPublicationTypes());
        verify(publicationTypeService);
    }

    @Test
    public void testGetDetailLicenseeClasses() {
        List<DetailLicenseeClass> detailLicenseeClasses = Collections.singletonList(new DetailLicenseeClass());
        expect(licenseeClassService.getDetailLicenseeClasses()).andReturn(detailLicenseeClasses).once();
        replay(licenseeClassService);
        assertEquals(detailLicenseeClasses, controller.getDetailLicenseeClasses());
        verify(licenseeClassService);
    }

    @Test
    public void testGetAggregateClassesNotToBeDistributed() {
        List<DetailLicenseeClass> detailLicenseeClasses = Collections.singletonList(new DetailLicenseeClass());
        List<AggregateLicenseeClass> aggregateLicenseeClasses = Collections.singletonList(new AggregateLicenseeClass());
        expect(filterController.getWidget()).andReturn(filterWidgetMock).once();
        expect(filterWidgetMock.getAppliedFilter()).andReturn(usageFilter).once();
        expect(
            aaclUsageService.getAggregateClassesNotToBeDistributed(FUND_POOL_ID, usageFilter, detailLicenseeClasses))
            .andReturn(aggregateLicenseeClasses)
            .once();
        replay(aaclUsageService, filterWidgetMock, filterController);
        assertEquals(aggregateLicenseeClasses,
            controller.getAggregateClassesNotToBeDistributed(FUND_POOL_ID, detailLicenseeClasses));
        verify(aaclUsageService, filterWidgetMock, filterController);
    }

    private List<UsageAge> buildUsageAges() {
        return Lists.newArrayList(
            buildUsageAge(2020, new BigDecimal("1.00")),
            buildUsageAge(2018, new BigDecimal("0.75")));
    }

    private UsageAge buildUsageAge(Integer period, BigDecimal weight) {
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(period);
        usageAge.setWeight(weight);
        return usageAge;
    }

    private PublicationType buildPublicationType(String name, String weight) {
        PublicationType pubType = new PublicationType();
        pubType.setName(name);
        pubType.setWeight(new BigDecimal(weight));
        return pubType;
    }
}
