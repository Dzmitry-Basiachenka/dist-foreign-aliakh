package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineValueService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueWidget;

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
 * Verifies {@link UdmBaselineValueController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(StreamSource.class)
public class UdmBaselineValueControllerTest {

    private static final OffsetDateTime DATE = OffsetDateTime.of(2021, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));

    private final UdmBaselineValueController controller = new UdmBaselineValueController();
    private final UdmBaselineValueFilter udmValueFilter = new UdmBaselineValueFilter();

    private IUdmBaselineValueFilterController filterController;
    private IUdmBaselineValueFilterWidget filterWidget;
    private IUdmBaselineValueService udmBaselineValueService;
    private IUdmBaselineValueWidget udmBaselineValueWidget;
    private IUdmReportService udmReportService;
    private IStreamSourceHandler streamSourceHandler;

    @Before
    public void setUp() {
        filterController = createMock(IUdmBaselineValueFilterController.class);
        filterWidget = createMock(IUdmBaselineValueFilterWidget.class);
        udmBaselineValueWidget = createMock(IUdmBaselineValueWidget.class);
        udmBaselineValueService = createMock(IUdmBaselineValueService.class);
        udmReportService = createMock(IUdmReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        Whitebox.setInternalState(controller, filterController);
        Whitebox.setInternalState(controller, filterWidget);
        Whitebox.setInternalState(controller, udmBaselineValueWidget);
        Whitebox.setInternalState(controller, udmBaselineValueService);
        Whitebox.setInternalState(controller, udmReportService);
        Whitebox.setInternalState(controller, streamSourceHandler);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(UdmBaselineValueWidget.class));
    }

    @Test
    public void testInitBaselineFilterWidget() {
        expect(filterController.initWidget()).andReturn(filterWidget).once();
        replay(filterController);
        controller.initBaselineValuesFilterWidget();
        verify(filterController);
    }

    @Test
    public void testLoadBeans() {
        List<UdmValueBaselineDto> udmValues = List.of(new UdmValueBaselineDto());
        Capture<Pageable> pageableCapture = newCapture();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(udmValueFilter).once();
        expect(udmBaselineValueService.getValueDtos(eq(udmValueFilter), capture(pageableCapture), isNull()))
            .andReturn(udmValues).once();
        replay(filterController, filterWidget, udmBaselineValueService, udmBaselineValueWidget);
        assertSame(udmValues, controller.loadBeans(0, 10, null));
        assertEquals(10, pageableCapture.getValue().getLimit());
        assertEquals(0, pageableCapture.getValue().getOffset());
        verify(filterController, filterWidget, udmBaselineValueService, udmBaselineValueWidget);
    }

    @Test
    public void testGetBeansCount() {
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(udmValueFilter).once();
        expect(udmBaselineValueService.getBaselineValueCount(udmValueFilter)).andReturn(10).once();
        replay(filterController, filterWidget, udmBaselineValueService, udmBaselineValueWidget);
        assertEquals(10, controller.getBeansCount());
        verify(filterController, filterWidget, udmBaselineValueService, udmBaselineValueWidget);
    }

    @Test
    public void testGetExportBaselineValuesStreamSource() {
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = "export_udm_baseline_value_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
            () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(DATE).once();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(udmValueFilter).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        udmReportService.writeUdmBaselineValuesCsvReport(udmValueFilter, pos);
        replay(OffsetDateTime.class, filterController, filterWidget, udmReportService, streamSourceHandler);
        IStreamSource streamSource = controller.getExportBaselineValuesStreamSource();
        assertEquals("export_udm_baseline_value_01_02_2021_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        assertNotNull(posConsumer);
        verify(OffsetDateTime.class, filterController, filterWidget, udmReportService, streamSourceHandler);
    }
}
