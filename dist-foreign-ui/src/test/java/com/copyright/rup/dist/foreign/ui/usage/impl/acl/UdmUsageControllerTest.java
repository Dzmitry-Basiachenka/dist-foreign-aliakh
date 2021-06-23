package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

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

import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.UdmCsvProcessor;
import com.copyright.rup.dist.foreign.ui.audit.impl.UsageHistoryWindow;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterWidget;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    private final UdmUsageController controller = new UdmUsageController();
    private final UdmUsageFilter udmUsageFilter = new UdmUsageFilter();
    private IUdmUsageService udmUsageService;
    private IUdmBatchService udmBatchService;
    private IUdmUsageAuditService udmUsageAuditService;
    private CsvProcessorFactory csvProcessorFactory;
    private IUdmUsageFilterController udmUsageFilterController;
    private IUdmUsageFilterWidget udmUsageFilterWidget;
    private IUdmUsageWidget udmUsageWidget;

    @Before
    public void setUp() {
        udmUsageService = createMock(IUdmUsageService.class);
        udmBatchService = createMock(IUdmBatchService.class);
        udmUsageAuditService = createMock(IUdmUsageAuditService.class);
        csvProcessorFactory = createMock(CsvProcessorFactory.class);
        udmUsageFilterController = createMock(IUdmUsageFilterController.class);
        udmUsageFilterWidget = createMock(IUdmUsageFilterWidget.class);
        udmUsageWidget = createMock(IUdmUsageWidget.class);
        Whitebox.setInternalState(controller, csvProcessorFactory);
        Whitebox.setInternalState(controller, udmUsageService);
        Whitebox.setInternalState(controller, udmUsageFilterController);
        Whitebox.setInternalState(controller, udmUsageFilterWidget);
        Whitebox.setInternalState(controller, udmBatchService);
        Whitebox.setInternalState(controller, udmUsageAuditService);
        Whitebox.setInternalState(controller, udmUsageWidget);
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
        assertTrue(udmBatchService.udmBatchExists(batchName));
        verify(udmBatchService);
    }

    @Test
    public void testShowUdmUsageHistory() {
        mockStatic(Windows.class);
        Capture<UsageHistoryWindow> windowCapture = newCapture();
        String udmUsageId = "432320b8-5029-47dd-8137-99007cb69bf1";
        List<UsageAuditItem> auditItems = Collections.emptyList();
        expect(udmUsageAuditService.getUdmUsageAudit(udmUsageId)).andReturn(auditItems).once();
        Windows.showModalWindow(capture(windowCapture));
        expectLastCall().once();
        replay(Windows.class, udmUsageAuditService);
        controller.showUdmUsageHistory(udmUsageId);
        verify(Windows.class, udmUsageAuditService);
        assertNotNull(windowCapture.getValue());
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
        udmUsage.setQuantity(7);
        return udmUsage;
    }
}
