package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.foreign.domain.UdmProxyValueDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmProxyValueService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueWidget;
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
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link UdmProxyValueController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ForeignSecurityUtils.class, OffsetDateTime.class, StreamSource.class,})
public class UdmProxyValueControllerTest {

    private static final OffsetDateTime DATE = OffsetDateTime.of(2021, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));

    private final UdmProxyValueFilter udmUsageFilter = new UdmProxyValueFilter();
    private IUdmProxyValueService udmProxyValueService;
    private IUdmProxyValueFilterController udmProxyValueFilterController;
    private IUdmProxyValueFilterWidget udmProxyValueFilterWidget;
    private UdmProxyValueController controller;
    private IUdmReportService udmReportService;
    private IStreamSourceHandler streamSourceHandler;

    @Before
    public void setUp() {
        controller = new UdmProxyValueController();
        udmProxyValueService = createMock(IUdmProxyValueService.class);
        udmProxyValueFilterController = createMock(IUdmProxyValueFilterController.class);
        udmProxyValueFilterWidget = createMock(IUdmProxyValueFilterWidget.class);
        udmReportService = createMock(IUdmReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        Whitebox.setInternalState(controller, udmProxyValueService);
        Whitebox.setInternalState(controller, udmProxyValueFilterController);
        Whitebox.setInternalState(controller, udmReportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
    }

    @Test
    public void testGetProxyValues() {
        List<UdmProxyValueDto> valueDtos = Arrays.asList(new UdmProxyValueDto(), new UdmProxyValueDto());
        UdmProxyValueFilter filter = new UdmProxyValueFilter();
        expect(udmProxyValueFilterController.getWidget()).andReturn(udmProxyValueFilterWidget).once();
        expect(udmProxyValueFilterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(udmProxyValueService.getDtosByFilter(filter)).andReturn(valueDtos).once();
        replay(udmProxyValueService, udmProxyValueFilterController, udmProxyValueFilterWidget);
        assertEquals(valueDtos, controller.getProxyValues());
        verify(udmProxyValueService, udmProxyValueFilterController, udmProxyValueFilterWidget);
    }

    @Test
    public void testInstantiateWidget() {
        IUdmProxyValueWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(UdmProxyValueWidget.class, widget.getClass());
    }

    @Test
    public void testGetExportUdmProxyValuesStreamSource() {
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = new Capture<>();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = new Capture<>();
        String fileName = "export_udm_proxy_value_";
        Supplier<InputStream> isSupplier = () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(DATE).once();
        expect(udmProxyValueFilterController.getWidget()).andReturn(udmProxyValueFilterWidget).once();
        expect(udmProxyValueFilterWidget.getAppliedFilter()).andReturn(udmUsageFilter).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(() -> fileName, "csv", isSupplier)).once();
        udmReportService.writeUdmProxyValueCsvReport(udmUsageFilter, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, udmProxyValueFilterWidget, udmProxyValueFilterController,
            streamSourceHandler, udmReportService);
        assertEquals("export_udm_proxy_value_01_02_2021_03_04.csv",
            controller.getExportUdmProxyValuesStreamSource().getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        assertNotNull(posConsumer);
        verify(OffsetDateTime.class, udmProxyValueFilterWidget, udmProxyValueFilterController,
            streamSourceHandler, udmReportService);
    }
}
