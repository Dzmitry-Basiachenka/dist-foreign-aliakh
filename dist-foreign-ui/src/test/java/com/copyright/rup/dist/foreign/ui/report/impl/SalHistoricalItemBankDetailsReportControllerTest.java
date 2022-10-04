package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.ISalHistoricalItemBankDetailsReportWidget;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.OutputStream;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link SalHistoricalItemBankDetailsReportController}.
 * <p>
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/25/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
public class SalHistoricalItemBankDetailsReportControllerTest {

    private SalHistoricalItemBankDetailsReportController controller;
    private IReportService reportService;

    @Before
    public void setUp() {
        controller = new SalHistoricalItemBankDetailsReportController();
        reportService = createMock(IReportService.class);
        Whitebox.setInternalState(controller, reportService);
    }

    @Test
    public void testInstantiateWidget() {
        ISalHistoricalItemBankDetailsReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(SalHistoricalItemBankDetailsReportWidget.class, widget.getClass());
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        Long licenseeAccountNumber = 1114L;
        Integer periodEndYearFrom = 2019;
        Integer periodEndYearTo = 2020;
        ISalHistoricalItemBankDetailsReportWidget widget = createMock(ISalHistoricalItemBankDetailsReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        Capture<OutputStream> osCapture = new Capture<>();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(widget.getLicenseeAccountNumber()).andReturn(licenseeAccountNumber).once();
        expect(widget.getPeriodEndYearFrom()).andReturn(periodEndYearFrom).once();
        expect(widget.getPeriodEndYearTo()).andReturn(periodEndYearTo).once();
        reportService.writeSalHistoricalItemBankDetailsReport(eq(licenseeAccountNumber), eq(periodEndYearFrom),
            eq(periodEndYearTo), capture(osCapture));
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, reportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("SAL_historical_item_bank_01_02_2019_03_04.csv",
            streamSource.getSource().getKey().get());
        assertNotNull(streamSource.getSource().getValue().get());
        assertNotNull(osCapture.getValue());
        verify(OffsetDateTime.class, widget, reportService);
    }

    @Test
    public void testGetSalLicensees() {
        IUsageBatchService usageBatchService = createMock(IUsageBatchService.class);
        Whitebox.setInternalState(controller, usageBatchService);
        List<SalLicensee> licensees = Collections.singletonList(new SalLicensee());
        expect(usageBatchService.getSalLicensees()).andReturn(licensees).once();
        replay(usageBatchService);
        assertSame(licensees, controller.getSalLicensees());
        verify(usageBatchService);
    }
}
