package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.ui.report.api.INtsPreServiceFeeFundReportWidget;

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
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link NtsPreServiceFeeFundReportController}.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 01/31/2024
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, StreamSource.class})
public class NtsPreServiceFeeFundReportControllerTest {

    private IFundPoolService fundPoolService;
    private NtsPreServiceFeeFundReportController controller;

    @Before
    public void setUp() {
        fundPoolService = createMock(IFundPoolService.class);
        controller = new NtsPreServiceFeeFundReportController();
        Whitebox.setInternalState(controller, fundPoolService);
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2023, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        INtsPreServiceFeeFundReportWidget widget = createMock(INtsPreServiceFeeFundReportWidget.class);
        IStreamSourceHandler streamSourceHandler = createMock(IStreamSourceHandler.class);
        IReportService reportService = createMock(IReportService.class);
        Whitebox.setInternalState(controller, widget);
        Whitebox.setInternalState(controller, reportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
        var fundPool = new FundPool();
        fundPool.setId("cd5571b2-6f59-4bce-bf0f-38df7bdbe814");
        fundPool.setProductFamily("NTS");
        fundPool.setName("Fund name");
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = "nts_pre_service_fee_fund_report_Fund name_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
            () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(widget.getFundPool()).andReturn(fundPool).times(2);
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        reportService.writeNtsPreServiceFeeFundCsvReport(fundPool, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, streamSourceHandler);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("nts_pre_service_fee_fund_report_Fund_name_01_02_2023_03_04.csv",
            streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        verify(OffsetDateTime.class, widget, streamSourceHandler);
    }

    @Test
    public void testGetPreServiceSeeFunds() {
        List<FundPool> preServiceFeeFunds = List.of(new FundPool());
        expect(fundPoolService.getFundPools("NTS")).andReturn(preServiceFeeFunds).once();
        replay(fundPoolService);
        assertSame(preServiceFeeFunds, controller.getPreServiceFeeFunds());
        verify(fundPoolService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(NtsPreServiceFeeFundReportWidget.class));
    }
}
