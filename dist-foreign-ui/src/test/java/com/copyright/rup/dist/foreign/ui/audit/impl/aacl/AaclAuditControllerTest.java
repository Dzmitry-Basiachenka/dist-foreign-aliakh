package com.copyright.rup.dist.foreign.ui.audit.impl.aacl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
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
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.api.aacl.IAaclAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.api.aacl.IAaclAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.api.aacl.IAaclAuditWidget;
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
 * Verifies {@link AaclAuditController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/12/2020
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, OffsetDateTime.class, StreamSource.class})
public class AaclAuditControllerTest {

    private AaclAuditController controller;
    private IUsageAuditService usageAuditService;
    private ICommonAuditFilterController auditFilterController;
    private IUsageService usageService;
    private IAaclUsageService aaclUsageService;
    private IAaclAuditWidget auditWidget;
    private ICommonAuditFilterWidget filterWidget;
    private IReportService reportService;
    private IStreamSourceHandler streamSourceHandler;

    @Before
    public void setUp() {
        usageAuditService = createMock(IUsageAuditService.class);
        auditFilterController = createMock(IAaclAuditFilterController.class);
        usageService = createMock(IUsageService.class);
        aaclUsageService = createMock(IAaclUsageService.class);
        auditWidget = createMock(IAaclAuditWidget.class);
        filterWidget = createMock(IAaclAuditFilterWidget.class);
        reportService = createMock(IReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        controller = new AaclAuditController();
        Whitebox.setInternalState(controller, auditWidget);
        Whitebox.setInternalState(controller, usageAuditService);
        Whitebox.setInternalState(controller, auditFilterController);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, aaclUsageService);
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
        expect(aaclUsageService.getCountForAudit(filter)).andReturn(1).once();
        replay(filterWidget, auditWidget, auditFilterController, aaclUsageService);
        assertEquals(1, controller.getSize());
        verify(filterWidget, auditWidget, auditFilterController, aaclUsageService);
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
        Capture<Pageable> pageableCapture = newCapture();
        Capture<Sort> sortCapture = newCapture();
        AuditFilter filter = new AuditFilter();
        filter.setStatuses(EnumSet.of(UsageStatusEnum.ELIGIBLE));
        expect(auditFilterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(auditWidget.getSearchValue()).andReturn(StringUtils.EMPTY).once();
        expect(aaclUsageService.getForAudit(eq(filter), capture(pageableCapture), capture(sortCapture)))
            .andReturn(Collections.emptyList()).once();
        replay(filterWidget, auditWidget, auditFilterController, aaclUsageService);
        List<UsageDto> result = controller.loadBeans(0, 10, null);
        assertEquals(Collections.emptyList(), result);
        verify(filterWidget, auditWidget, auditFilterController, aaclUsageService);
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
        Capture<UsageHistoryWindow> windowCapture = newCapture();
        String usageId = RupPersistUtils.generateUuid();
        String detailId = RupPersistUtils.generateUuid();
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
        reportService.writeAuditAaclCsvReport(filter, pos);
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
        assertThat(controller.instantiateWidget(), instanceOf(AaclAuditWidget.class));
    }
}
