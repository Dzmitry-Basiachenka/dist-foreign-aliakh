package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.CompanyInformation;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmAuditFieldToValuesMap;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.integration.telesales.api.ITelesalesService;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.service.impl.acl.UdmAnnualizedCopiesCalculator;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.UdmCsvProcessor;
import com.copyright.rup.dist.foreign.ui.audit.impl.UsageHistoryWindow;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.ImmutableMap;
import com.vaadin.ui.Window;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.InputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
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
 * Verifies {@link UdmUsageController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/03/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StreamSource.class, Windows.class, ForeignSecurityUtils.class})
public class UdmUsageControllerTest {

    private static final String UDM_BATCH_UID = "5acc58a4-49c0-4c20-b96e-39e637a0657f";
    private static final String UDM_USAGE_UID_1 = "35c42bac-c6f6-4559-a788-206c376dc969";
    private static final String UDM_USAGE_UID_2 = "1854c871-3a1b-40e9-84e6-d854dbc77a76";
    private static final String UDM_USAGE_ORIGIN_UID_1 = "OGN674GHHSB291";
    private static final String UDM_USAGE_ORIGIN_UID_2 = "OGN674GHHSB293";
    private static final OffsetDateTime DATE = OffsetDateTime.of(2021, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));

    private final UdmUsageController controller = new UdmUsageController();
    private final UdmUsageFilter udmUsageFilter = new UdmUsageFilter();
    private IUdmUsageService udmUsageService;
    private IUdmBatchService udmBatchService;
    private IUdmUsageAuditService udmUsageAuditService;
    private CsvProcessorFactory csvProcessorFactory;
    private IUdmUsageFilterController udmUsageFilterController;
    private IUdmUsageFilterWidget udmUsageFilterWidget;
    private IUdmUsageWidget udmUsageWidget;
    private IUdmReportService udmReportService;
    private IStreamSourceHandler streamSourceHandler;

    @Before
    public void setUp() {
        udmUsageService = createMock(IUdmUsageService.class);
        udmBatchService = createMock(IUdmBatchService.class);
        udmUsageAuditService = createMock(IUdmUsageAuditService.class);
        csvProcessorFactory = createMock(CsvProcessorFactory.class);
        udmUsageFilterController = createMock(IUdmUsageFilterController.class);
        udmUsageFilterWidget = createMock(IUdmUsageFilterWidget.class);
        udmUsageWidget = createMock(IUdmUsageWidget.class);
        udmReportService = createMock(IUdmReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        Whitebox.setInternalState(controller, csvProcessorFactory);
        Whitebox.setInternalState(controller, udmUsageService);
        Whitebox.setInternalState(controller, udmUsageFilterController);
        Whitebox.setInternalState(controller, udmUsageFilterWidget);
        Whitebox.setInternalState(controller, udmBatchService);
        Whitebox.setInternalState(controller, udmUsageAuditService);
        Whitebox.setInternalState(controller, udmUsageWidget);
        Whitebox.setInternalState(controller, udmReportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
    }

    @Test
    public void testLoadBeans() {
        List<UdmUsageDto> udmUsages = Collections.singletonList(new UdmUsageDto());
        Capture<Pageable> pageableCapture = newCapture();
        expect(udmUsageFilterController.getWidget()).andReturn(udmUsageFilterWidget).once();
        expect(udmUsageFilterWidget.getAppliedFilter()).andReturn(udmUsageFilter).once();
        expect(udmUsageWidget.getSearchValue()).andReturn(StringUtils.EMPTY).once();
        expect(udmUsageService.getUsageDtos(eq(udmUsageFilter), capture(pageableCapture), isNull()))
            .andReturn(udmUsages).once();
        replay(udmUsageFilterController, udmUsageFilterWidget, udmUsageService, udmUsageWidget);
        assertSame(udmUsages, controller.loadBeans(0, 10, null));
        assertEquals(10, pageableCapture.getValue().getLimit());
        assertEquals(0, pageableCapture.getValue().getOffset());
        verify(udmUsageFilterController, udmUsageFilterWidget, udmUsageService, udmUsageWidget);
    }

    @Test
    public void testGetBeansCount() {
        expect(udmUsageFilterController.getWidget()).andReturn(udmUsageFilterWidget).once();
        expect(udmUsageFilterWidget.getAppliedFilter()).andReturn(udmUsageFilter).once();
        expect(udmUsageWidget.getSearchValue()).andReturn(StringUtils.EMPTY).once();
        expect(udmUsageService.getUsagesCount(udmUsageFilter)).andReturn(10).once();
        replay(udmUsageFilterController, udmUsageFilterWidget, udmUsageService, udmUsageWidget);
        assertEquals(10, controller.getBeansCount());
        verify(udmUsageFilterController, udmUsageFilterWidget, udmUsageService, udmUsageWidget);
    }

    @Test
    public void testInstantiateWidget() {
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(false).once();
        expect(ForeignSecurityUtils.hasManagerPermission()).andReturn(false).once();
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(false).once();
        replay(ForeignSecurityUtils.class);
        udmUsageWidget = controller.instantiateWidget();
        assertNotNull(udmUsageWidget);
        verify(ForeignSecurityUtils.class);
    }

    @Test
    public void testGetUdmUsageCsvProcessor() {
        UdmCsvProcessor processorMock = createMock(UdmCsvProcessor.class);
        expect(csvProcessorFactory.getUdmCsvProcessor()).andReturn(processorMock).once();
        replay(csvProcessorFactory);
        assertSame(processorMock, controller.getCsvProcessor());
        verify(csvProcessorFactory);
    }

    @Test
    public void testGetPeriods() {
        List<Integer> expectedPeriods = Collections.singletonList(202106);
        expect(udmUsageService.getPeriods()).andReturn(expectedPeriods).once();
        replay(udmUsageService);
        List<Integer> periods = controller.getPeriods();
        assertEquals(expectedPeriods.get(0), periods.get(0));
        verify(udmUsageService);
    }

    @Test
    public void testPublishUdmUsagesToBaseline() {
        expect(udmUsageService.publishUdmUsagesToBaseline(202106)).andReturn(Pair.of(5, 3)).once();
        replay(udmUsageService);
        Pair<Integer, Integer> integerIntegerPair = controller.publishUdmUsagesToBaseline(202106);
        assertEquals(Integer.valueOf(5), integerIntegerPair.getLeft());
        assertEquals(Integer.valueOf(3), integerIntegerPair.getRight());
        verify(udmUsageService);
    }

    @Test
    public void testLoadUdmBatch() {
        UdmBatch udmBatch = new UdmBatch();
        List<UdmUsage> udmUsages = Arrays.asList(
            buildUdmUsage(UDM_USAGE_UID_1, UDM_USAGE_ORIGIN_UID_1),
            buildUdmUsage(UDM_USAGE_UID_2, UDM_USAGE_ORIGIN_UID_2));
        expect(udmUsageFilterController.getWidget()).andReturn(udmUsageFilterWidget).once();
        udmUsageFilterWidget.clearFilter();
        expectLastCall().once();
        udmBatchService.insertUdmBatch(udmBatch, udmUsages);
        expectLastCall().once();
        udmUsageService.sendForMatching(udmUsages);
        expectLastCall().once();
        udmUsageWidget.clearSearch();
        expectLastCall().once();
        replay(udmBatchService, udmUsageService, udmUsageFilterController, udmUsageFilterWidget);
        controller.loadUdmBatch(udmBatch, udmUsages);
        verify(udmBatchService, udmUsageService, udmUsageFilterController, udmUsageFilterWidget);
    }

    @Test
    public void testBatchExists() {
        String batchName = "Name";
        expect(udmBatchService.udmBatchExists(batchName)).andReturn(true).once();
        replay(udmBatchService);
        assertTrue(controller.udmBatchExists(batchName));
        verify(udmBatchService);
    }

    @Test
    public void testGetActionReasons() {
        List<UdmActionReason> actionReasons = Arrays.asList(
            new UdmActionReason("1c8f6e43-2ca8-468d-8700-ce855e6cd8c0", "Aggregated Content"),
            new UdmActionReason("97fd8093-7f36-4a09-99f1-1bfe36a5c3f4", "Arbitrary RFA search result order"));
        expect(udmUsageService.getAllActionReasons()).andReturn(actionReasons).once();
        replay(udmUsageService);
        assertEquals(actionReasons, controller.getAllActionReasons());
        verify(udmUsageService);
    }

    @Test
    public void testGetIneligibleReasons() {
        List<UdmIneligibleReason> ineligibleReasons = Arrays.asList(
            new UdmIneligibleReason("b60a726a-39e8-4303-abe1-6816da05b858", "Invalid survey"),
            new UdmIneligibleReason("0d5a129c-0f8f-4e48-98b2-8b980cdb9333", "Misc - See Comments"));
        expect(udmUsageService.getAllIneligibleReasons()).andReturn(ineligibleReasons).once();
        replay(udmUsageService);
        assertEquals(ineligibleReasons, controller.getAllIneligibleReasons());
        verify(udmUsageService);
    }

    @Test
    public void testGetIdsToDetailLicenseeClasses() {
        ILicenseeClassService licenseeClassService = createMock(ILicenseeClassService.class);
        Whitebox.setInternalState(controller, licenseeClassService);
        DetailLicenseeClass licenseeClass1 = new DetailLicenseeClass();
        licenseeClass1.setId(1);
        DetailLicenseeClass licenseeClass2 = new DetailLicenseeClass();
        licenseeClass2.setId(2);
        expect(licenseeClassService.getDetailLicenseeClasses("ACL"))
            .andReturn(Arrays.asList(licenseeClass1, licenseeClass2)).once();
        replay(licenseeClassService);
        Map<Integer, DetailLicenseeClass> idsToDetailLicenseeClasses = controller.getIdsToDetailLicenseeClasses();
        assertEquals(2, idsToDetailLicenseeClasses.size());
        assertEquals(licenseeClass1, idsToDetailLicenseeClasses.get(1));
        assertEquals(licenseeClass2, idsToDetailLicenseeClasses.get(2));
        verify(licenseeClassService);
    }

    @Test
    public void testShowUdmUsageHistory() {
        mockStatic(Windows.class);
        Window.CloseListener closeListener = createMock(Window.CloseListener.class);
        Capture<UsageHistoryWindow> windowCapture = newCapture();
        String udmUsageId = "432320b8-5029-47dd-8137-99007cb69bf1";
        List<UsageAuditItem> auditItems = Collections.emptyList();
        expect(udmUsageAuditService.getUdmUsageAudit(udmUsageId)).andReturn(auditItems).once();
        Windows.showModalWindow(capture(windowCapture));
        expectLastCall().once();
        replay(Windows.class, closeListener, udmUsageAuditService);
        controller.showUdmUsageHistory(udmUsageId, closeListener);
        assertNotNull(windowCapture.getValue());
        verify(Windows.class, closeListener, udmUsageAuditService);
    }

    @Test
    public void testAssignUsages() {
        Set<UdmUsageDto> udmUsages = Collections.singleton(new UdmUsageDto());
        udmUsageService.assignUsages(udmUsages);
        expectLastCall().once();
        replay(udmUsageService);
        controller.assignUsages(udmUsages);
        verify(udmUsageService);
    }

    @Test
    public void testUnassignUsages() {
        Set<UdmUsageDto> udmUsages = Collections.singleton(new UdmUsageDto());
        udmUsageService.unassignUsages(udmUsages);
        expectLastCall().once();
        replay(udmUsageService);
        controller.unassignUsages(udmUsages);
        verify(udmUsageService);
    }

    @Test
    public void testUpdateUsage() {
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        UdmAuditFieldToValuesMap fieldToValueChangesMap = new UdmAuditFieldToValuesMap(udmUsageDto);
        udmUsageService.updateUsage(udmUsageDto, fieldToValueChangesMap, false);
        expectLastCall().once();
        udmUsageService.sendForMatching(Collections.singleton(udmUsageDto));
        expectLastCall().once();
        replay(udmUsageService);
        controller.updateUsage(udmUsageDto, fieldToValueChangesMap, false);
        verify(udmUsageService);
    }

    @Test
    public void testUpdateUsages() {
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        Map<UdmUsageDto, UdmAuditFieldToValuesMap> udmUsageDtoToFieldValuesMap =
            ImmutableMap.of(udmUsageDto, new UdmAuditFieldToValuesMap());
        udmUsageService.updateUsages(udmUsageDtoToFieldValuesMap, false);
        expectLastCall().once();
        udmUsageService.sendForMatching(Collections.singleton(udmUsageDto));
        expectLastCall().once();
        replay(udmUsageService);
        controller.updateUsages(udmUsageDtoToFieldValuesMap, false);
        verify(udmUsageService);
    }

    @Test
    public void testDeleteUsageBatch() {
        UdmBatch usageBatch = new UdmBatch();
        udmBatchService.deleteUdmBatch(usageBatch);
        expectLastCall().once();
        expect(udmUsageFilterController.getWidget()).andReturn(udmUsageFilterWidget).once();
        udmUsageFilterWidget.clearFilter();
        expectLastCall().once();
        replay(udmBatchService, udmUsageFilterController, udmUsageFilterWidget);
        controller.deleteUdmBatch(usageBatch);
        verify(udmBatchService, udmUsageFilterController, udmUsageFilterWidget);
    }

    @Test
    public void testGetUdmBathes() {
        List<UdmBatch> udmBatches = Collections.singletonList(new UdmBatch());
        expect(udmBatchService.getUdmBatches()).andReturn(udmBatches).once();
        replay(udmBatchService);
        assertEquals(udmBatches, controller.getUdmBatches());
        verify(udmBatchService);
    }

    @Test
    public void testIsUdmBatchProcessingCompleted() {
        expect(udmBatchService.isUdmBatchProcessingCompleted(UDM_BATCH_UID)).andReturn(true).once();
        replay(udmBatchService);
        assertTrue(controller.isUdmBatchProcessingCompleted(UDM_BATCH_UID));
        verify(udmBatchService);
    }

    @Test
    public void testIsUdmBatchContainsBaselineUsages() {
        expect(udmBatchService.isUdmBatchContainsBaselineUsages(UDM_BATCH_UID)).andReturn(true).once();
        replay(udmBatchService);
        assertTrue(controller.isUdmBatchContainsBaselineUsages(UDM_BATCH_UID));
        verify(udmBatchService);
    }

    @Test
    public void testGetCompanyInformation() {
        ITelesalesService telesalesService = createMock(ITelesalesService.class);
        Whitebox.setInternalState(controller, telesalesService);
        CompanyInformation companyInformation = new CompanyInformation();
        companyInformation.setId(1136L);
        companyInformation.setName("Albany International Corp.");
        companyInformation.setDetailLicenseeClassId(333);
        expect(telesalesService.getCompanyInformation(1136L)).andReturn(companyInformation).once();
        replay(telesalesService);
        assertEquals(companyInformation, controller.getCompanyInformation(1136L));
        verify(telesalesService);
    }

    @Test
    public void testCalculateAnnualizedCopies() {
        UdmAnnualizedCopiesCalculator calculator = createMock(UdmAnnualizedCopiesCalculator.class);
        Whitebox.setInternalState(controller, calculator);
        String reportedTypeOfUse = "COPY_FOR_MYSELF";
        Long quantity = 1L;
        Integer annualMultiplier = 1;
        BigDecimal statisticalMultiplier = BigDecimal.TEN;
        BigDecimal annualizedCopies = BigDecimal.TEN;
        expect(calculator.calculate(reportedTypeOfUse, quantity, annualMultiplier, statisticalMultiplier))
            .andReturn(annualizedCopies).once();
        replay(calculator);
        assertEquals(annualizedCopies,
            controller.calculateAnnualizedCopies(reportedTypeOfUse, quantity, annualMultiplier, statisticalMultiplier));
        verify(calculator);
    }

    @Test
    public void testGetExportUsagesStreamSourceSpecialistManagerRoles() {
        testGetExportUsagesStreamSource(controller::getExportUdmUsagesStreamSourceSpecialistManagerRoles,
            pos -> udmReportService.writeUdmUsageCsvReportSpecialistManager(udmUsageFilter, pos));
    }

    @Test
    public void testGetExportUsagesStreamSourceResearcherRole() {
        testGetExportUsagesStreamSource(controller::getExportUdmUsagesStreamSourceResearcherRole,
            pos -> udmReportService.writeUdmUsageCsvReportResearcher(udmUsageFilter, pos));
    }

    @Test
    public void testGetExportUsagesStreamSourceViewRole() {
        testGetExportUsagesStreamSource(controller::getExportUdmUsagesStreamSourceViewRole,
            pos -> udmReportService.writeUdmUsageCsvReportView(udmUsageFilter, pos));
    }

    @Test
    public void testGetUdmRecordThreshold() {
        expect(udmUsageService.getUdmRecordThreshold()).andReturn(10000).once();
        replay(udmUsageService);
        assertEquals(10000, controller.getUdmRecordThreshold());
        verify(udmUsageService);
    }

    private void testGetExportUsagesStreamSource(Supplier<IStreamSource> streamSourceSupplier,
                                                 Consumer<PipedOutputStream> consumer) {
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = new Capture<>();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = new Capture<>();
        String fileName = "export_udm_usage_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> isSupplier = () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(DATE).once();
        expect(udmUsageFilterController.getWidget()).andReturn(udmUsageFilterWidget).once();
        expect(udmUsageFilterWidget.getAppliedFilter()).andReturn(udmUsageFilter).once();
        udmUsageWidget.clearSearch();
        expectLastCall().once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", isSupplier)).once();
        consumer.accept(pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, udmUsageFilterWidget, udmUsageFilterController, streamSourceHandler,
            udmReportService);
        assertEquals("export_udm_usage_01_02_2021_03_04.csv", streamSourceSupplier.get().getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        assertNotNull(posConsumer);
        verify(OffsetDateTime.class, udmUsageFilterWidget, udmUsageFilterController, streamSourceHandler,
            udmReportService);
    }

    private UdmUsage buildUdmUsage(String usageId, String originalDetailId) {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setId(usageId);
        udmUsage.setOriginalDetailId(originalDetailId);
        udmUsage.setBatchId(UDM_BATCH_UID);
        udmUsage.setStatus(UsageStatusEnum.NEW);
        udmUsage.setPeriodEndDate(LocalDate.of(2021, 12, 31));
        udmUsage.setUsageDate(LocalDate.of(2021, 12, 12));
        udmUsage.setWrWrkInst(122825347L);
        udmUsage.setReportedStandardNumber("0927-7765");
        udmUsage.setReportedTitle("Colloids and surfaces. B, Biointerfaces");
        udmUsage.setReportedPubType("Journal");
        udmUsage.setPubFormat("format");
        udmUsage.setArticle("Green chemistry");
        udmUsage.setLanguage("English");
        udmUsage.setCompanyId(45489489L);
        udmUsage.setSurveyRespondent("fa0276c3-55d6-42cd-8ffe-e9124acae02f");
        udmUsage.setIpAddress("ip24.12.119.203");
        udmUsage.setSurveyCountry("United States");
        udmUsage.setSurveyStartDate(LocalDate.of(2021, 12, 12));
        udmUsage.setSurveyEndDate(LocalDate.of(2021, 12, 12));
        udmUsage.setReportedTypeOfUse("COPY_FOR_MYSELF");
        udmUsage.setQuantity(7L);
        return udmUsage;
    }
}
