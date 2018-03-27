package com.copyright.rup.dist.foreign.ui.audit.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.AuditFilter;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditFilterController;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditWidget;
import com.copyright.rup.dist.foreign.ui.common.ExportStreamSource;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.IWidget;

import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 * Verifies {@link AuditController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/20/18
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class AuditControllerTest {

    private AuditController controller;
    private IUsageAuditService usageAuditService;
    private IAuditFilterController auditFilterController;
    private IUsageService usageService;
    private IAuditWidget auditWidget;
    private IAuditFilterWidget filterWidget;

    @Before
    public void setUp() {
        usageAuditService = createMock(IUsageAuditService.class);
        auditFilterController = createMock(IAuditFilterController.class);
        usageService = createMock(IUsageService.class);
        auditWidget = createMock(IAuditWidget.class);
        filterWidget = createMock(IAuditFilterWidget.class);
        controller = new AuditController();
        Whitebox.setInternalState(controller, IWidget.class, auditWidget);
        Whitebox.setInternalState(controller, "usageAuditService", usageAuditService);
        Whitebox.setInternalState(controller, "auditFilterController", auditFilterController);
        Whitebox.setInternalState(controller, "usageService", usageService);
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
        expect(usageService.getAuditItemsCount(filter)).andReturn(1).once();
        replay(filterWidget, auditWidget, auditFilterController, usageService);
        assertEquals(1, controller.getSize());
        verify(filterWidget, auditWidget, auditFilterController, usageService);
    }

    @Test
    public void testOnFilterChanged() {
        auditWidget.refresh();
        expectLastCall().once();
        replay(auditWidget);
        controller.onFilterChanged();
        verify(auditWidget);
    }

/*    @Test
    public void testLoadBeans() {
        Capture<Pageable> pageableCapture = new Capture<>();
        Capture<Sort> sortCapture = new Capture<>();
        AuditFilter filter = new AuditFilter();
        filter.setStatuses(EnumSet.of(UsageStatusEnum.ELIGIBLE));
        expect(auditFilterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(auditWidget.getSearchValue()).andReturn(StringUtils.EMPTY).once();
        expect(usageService.getForAudit(eq(filter), capture(pageableCapture), capture(sortCapture)))
            .andReturn(Collections.emptyList()).once();
        replay(filterWidget, auditWidget, auditFilterController, usageService);
        List<UsageDto> result = controller.loadBeans(0, 10, new Object[]{}, false);
        assertEquals(Collections.emptyList(), result);
        verify(filterWidget, auditWidget, auditFilterController, usageService);
    }

    @Test
    public void testLoadBeansEmptyFilter() {
        AuditFilter filter = new AuditFilter();
        expect(auditFilterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(auditWidget.getSearchValue()).andReturn(StringUtils.EMPTY).once();
        replay(filterWidget, auditWidget, auditFilterController, usageService);
        assertEquals(Collections.emptyList(), controller.loadBeans(0, 10, new Object[]{}, false));
        verify(filterWidget, auditWidget, auditFilterController, usageService);
    }*/

    @Test
    public void testShowUsageHistory() throws Exception {
        mockStatic(Windows.class);
        Capture<UsageHistoryWindow> windowCapture = new Capture<>();
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
    public void testGetExportUsagesStreamSource() {
        IStreamSource streamSource = controller.getExportUsagesStreamSource();
        assertTrue(streamSource instanceof ExportStreamSource);
        assertNotNull(streamSource.getStream());
        assertEquals("export_usage_audit_" + LocalDate.now().format(DateTimeFormatter.ofPattern("MM_dd_YYYY")) + ".csv",
            streamSource.getFileName());
    }

    @Test
    public void testInstantiateWidget() {
        assertTrue(controller.instantiateWidget() instanceof AuditWidget);
    }
}
