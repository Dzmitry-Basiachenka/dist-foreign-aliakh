package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditWidget;
import com.copyright.rup.dist.foreign.ui.audit.impl.UsageHistoryWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;
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
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link SalAuditController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/16/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, OffsetDateTime.class, StreamSource.class})
public class SalAuditControllerTest {

    private SalAuditController controller;
    private ISalAuditWidget auditWidget;
    private ICommonAuditFilterWidget filterWidget;
    private ICommonAuditFilterController auditFilterController;
    private IUsageAuditService usageAuditService;
    private IUsageService usageService;
    private ISalUsageService salUsageService;
    private IReportService reportService;
    private IStreamSourceHandler streamSourceHandler;

    @Before
    public void setUp() {
        auditWidget = createMock(ISalAuditWidget.class);
        filterWidget = createMock(ISalAuditFilterWidget.class);
        auditFilterController = createMock(ISalAuditFilterController.class);
        usageAuditService = createMock(IUsageAuditService.class);
        usageService = createMock(IUsageService.class);
        salUsageService = createMock(ISalUsageService.class);
        reportService = createMock(IReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        controller = new SalAuditController();
        Whitebox.setInternalState(controller, auditWidget);
        Whitebox.setInternalState(controller, auditFilterController);
        Whitebox.setInternalState(controller, usageAuditService);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, salUsageService);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
    }

    @Test
    public void testGetSizeEmptyFilter() {
        expect(auditFilterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(new AuditFilter()).once();
        expect(auditWidget.getSearchValue()).andReturn(StringUtils.EMPTY).once();
        replay(filterWidget, auditWidget, auditFilterController, usageService);
        assertEquals(0, controller.getSize());
        verify(filterWidget, auditWidget, auditFilterController, usageService);
    }

    @Test
    public void testGetSize() {
        AuditFilter filter = new AuditFilter();
        expect(auditFilterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(auditWidget.getSearchValue()).andReturn(StringUtils.EMPTY).once();
        filter.setStatuses(EnumSet.of(UsageStatusEnum.ELIGIBLE));
        expect(salUsageService.getCountForAudit(filter)).andReturn(1).once();
        replay(filterWidget, auditWidget, auditFilterController, salUsageService);
        assertEquals(1, controller.getSize());
        verify(filterWidget, auditWidget, auditFilterController, salUsageService);
    }

    @Test
    public void testOnFilterChanged() {
        auditWidget.refresh();
        expectLastCall().once();
        replay(auditWidget);
        controller.onFilterChanged();
        verify(auditWidget);
    }

    @Test
    public void testLoadBeans() {
        Capture<Pageable> pageableCapture = new Capture<>();
        Capture<Sort> sortCapture = new Capture<>();
        AuditFilter filter = new AuditFilter();
        filter.setStatuses(EnumSet.of(UsageStatusEnum.ELIGIBLE));
        expect(auditFilterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(auditWidget.getSearchValue()).andReturn(StringUtils.EMPTY).once();
        expect(salUsageService.getForAudit(eq(filter), capture(pageableCapture), capture(sortCapture)))
            .andReturn(Collections.emptyList()).once();
        replay(filterWidget, auditWidget, auditFilterController, salUsageService);
        List<UsageDto> result = controller.loadBeans(0, 10, null);
        assertEquals(Collections.emptyList(), result);
        verify(filterWidget, auditWidget, auditFilterController, salUsageService);
    }

    @Test
    public void testLoadBeansEmptyFilter() {
        AuditFilter filter = new AuditFilter();
        expect(auditFilterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(auditWidget.getSearchValue()).andReturn(StringUtils.EMPTY).once();
        replay(filterWidget, auditWidget, auditFilterController, usageService);
        assertEquals(Collections.emptyList(), controller.loadBeans(0, 10, null));
        verify(filterWidget, auditWidget, auditFilterController, usageService);
    }

    @Test
    public void testShowUsageHistory() {
        mockStatic(Windows.class);
        Capture<UsageHistoryWindow> windowCapture = new Capture<>();
        String usageId = "ddc391df-87e6-4b10-92ea-1cc2e295b487";
        String detailId = "11960c0e-1374-4eba-8658-bfe5e11bb304";
        List<UsageAuditItem> items = Collections.emptyList();
        expect(usageAuditService.getUsageAudit(usageId)).andReturn(items).once();
        Windows.showModalWindow(capture(windowCapture));
        expectLastCall().once();
        replay(Windows.class, usageAuditService);
        controller.showUsageHistory(usageId, detailId);
        verify(Windows.class, usageAuditService);
        assertNotNull(windowCapture.getValue());
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        AuditFilter filter = new AuditFilter();
        Capture<Supplier<String>> fileNameSupplierCapture = new Capture<>();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = new Capture<>();
        String fileName = "export_usage_audit_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> isSupplier = () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(auditFilterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", isSupplier)).once();
        reportService.writeAuditSalCsvReport(filter, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, auditFilterController, filterWidget, streamSourceHandler, reportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("export_usage_audit_01_02_2019_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        verify(OffsetDateTime.class, auditFilterController, filterWidget, streamSourceHandler, reportService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(SalAuditWidget.class));
    }
}
