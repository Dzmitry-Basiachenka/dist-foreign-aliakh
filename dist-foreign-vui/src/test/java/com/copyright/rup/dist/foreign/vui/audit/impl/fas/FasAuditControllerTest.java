package com.copyright.rup.dist.foreign.vui.audit.impl.fas;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.api.ICommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.vui.audit.api.fas.IFasAuditFilterController;
import com.copyright.rup.dist.foreign.vui.audit.api.fas.IFasAuditFilterWidget;
import com.copyright.rup.dist.foreign.vui.audit.api.fas.IFasAuditWidget;
import com.copyright.rup.dist.foreign.vui.audit.impl.UsageHistoryWindow;
import com.copyright.rup.dist.foreign.vui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

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
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link FasAuditController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/20/18
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class, StreamSource.class, Windows.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class FasAuditControllerTest {

    private FasAuditController controller;
    private IUsageAuditService usageAuditService;
    private ICommonAuditFilterController auditFilterController;
    private IFasAuditWidget auditWidget;
    private ICommonAuditFilterWidget filterWidget;
    private IUsageService usageService;
    private IReportService reportService;
    private IStreamSourceHandler streamSourceHandler;

    @Before
    public void setUp() {
        usageAuditService = createMock(IUsageAuditService.class);
        auditFilterController = createMock(IFasAuditFilterController.class);
        auditWidget = createMock(IFasAuditWidget.class);
        filterWidget = createMock(IFasAuditFilterWidget.class);
        usageService = createMock(IUsageService.class);
        reportService = createMock(IReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        controller = new FasAuditController();
        Whitebox.setInternalState(controller, usageAuditService);
        Whitebox.setInternalState(controller, auditFilterController);
        Whitebox.setInternalState(controller, auditWidget);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
    }

    @Test
    public void testGetSize() {
        var filter = new AuditFilter();
        expect(auditFilterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(auditWidget.getSearchValue()).andReturn(StringUtils.EMPTY).once();
        filter.setStatuses(EnumSet.of(UsageStatusEnum.ELIGIBLE));
        expect(usageService.getCountForAudit(filter)).andReturn(1).once();
        replay(filterWidget, auditWidget, auditFilterController, usageService);
        assertEquals(1, controller.getSize());
        verify(filterWidget, auditWidget, auditFilterController, usageService);
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
    public void testLoadBeans() {
        Capture<Pageable> pageableCapture = newCapture();
        Capture<Sort> sortCapture = newCapture();
        var filter = new AuditFilter();
        filter.setStatuses(EnumSet.of(UsageStatusEnum.ELIGIBLE));
        expect(auditFilterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(auditWidget.getSearchValue()).andReturn(StringUtils.EMPTY).once();
        expect(usageService.getForAudit(eq(filter), capture(pageableCapture), capture(sortCapture)))
            .andReturn(List.of()).once();
        replay(filterWidget, auditWidget, auditFilterController, usageService);
        List<UsageDto> result = controller.loadBeans(0, 10, null);
        assertEquals(List.of(), result);
        verify(filterWidget, auditWidget, auditFilterController, usageService);
    }

    @Test
    public void testLoadBeansEmptyFilter() {
        expect(auditFilterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(new AuditFilter()).once();
        expect(auditWidget.getSearchValue()).andReturn(StringUtils.EMPTY).once();
        replay(filterWidget, auditWidget, auditFilterController, usageService);
        assertEquals(List.of(), controller.loadBeans(0, 10, null));
        verify(filterWidget, auditWidget, auditFilterController, usageService);
    }

    @Test
    public void testShowUsageHistory() {
        mockStatic(Windows.class);
        Capture<UsageHistoryWindow> windowCapture = newCapture();
        var usageId = "01e7c4c8-6b04-4920-9b23-08a2f551c926";
        var detailId = "8830c9f1-b806-467d-927e-81fcf17ba013";
        expect(usageAuditService.getUsageAudit(usageId)).andReturn(List.of()).once();
        Windows.showModalWindow(capture(windowCapture));
        expectLastCall().once();
        replay(Windows.class, usageAuditService);
        controller.showUsageHistory(usageId, detailId);
        var window = windowCapture.getValue();
        assertNotNull(windowCapture.getValue());
        assertEquals("History for usage detail " + detailId, window.getHeaderTitle());
        verify(Windows.class, usageAuditService);
    }

    @Test
    public void testGetCsvStreamSource() {
        var now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        var filter = new AuditFilter();
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = "export_usage_audit_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
            () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(auditFilterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        reportService.writeAuditFasCsvReport(filter, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, auditFilterController, filterWidget, streamSourceHandler, reportService);
        var streamSource = controller.getCsvStreamSource();
        assertEquals("export_usage_audit_01_02_2019_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        verify(OffsetDateTime.class, auditFilterController, filterWidget, streamSourceHandler, reportService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(FasAuditWidget.class));
    }
}
