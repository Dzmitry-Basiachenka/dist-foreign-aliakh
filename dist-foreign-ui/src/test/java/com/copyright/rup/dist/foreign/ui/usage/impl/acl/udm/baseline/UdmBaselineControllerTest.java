package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmReportService;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterWidget;

import com.google.common.collect.ImmutableSet;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Verifies {@link UdmBaselineController}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StreamSource.class, RupContextUtils.class})
public class UdmBaselineControllerTest {

    private final UdmBaselineFilter baselineFilter = new UdmBaselineFilter();
    private final UdmBaselineController udmBaselineController = new UdmBaselineController();
    private IUdmBaselineService udmBaselineService;
    private IUdmBaselineFilterWidget udmBaselineFilterWidget;
    private IUdmBaselineFilterController udmBaselineFilterController;
    private IUdmReportService udmReportService;
    private IStreamSourceHandler streamSourceHandler;

    @Before
    public void setUp() {
        udmBaselineFilterController = createMock(IUdmBaselineFilterController.class);
        udmBaselineFilterWidget = createMock(IUdmBaselineFilterWidget.class);
        udmBaselineService = createMock(IUdmBaselineService.class);
        udmReportService = createMock(IUdmReportService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        Whitebox.setInternalState(udmBaselineController, udmBaselineService);
        Whitebox.setInternalState(udmBaselineController, udmBaselineFilterController);
        Whitebox.setInternalState(udmBaselineController, udmReportService);
        Whitebox.setInternalState(udmBaselineController, streamSourceHandler);
    }

    @Test
    public void testGetBeansCount() {
        expect(udmBaselineFilterController.getWidget()).andReturn(udmBaselineFilterWidget).once();
        expect(udmBaselineFilterWidget.getAppliedFilter()).andReturn(baselineFilter).once();
        expect(udmBaselineService.getBaselineUsagesCount(baselineFilter)).andReturn(5).once();
        replay(udmBaselineService, udmBaselineFilterController, udmBaselineFilterWidget);
        assertEquals(5, udmBaselineController.getBeansCount());
        verify(udmBaselineService, udmBaselineFilterController, udmBaselineFilterWidget);
    }

    @Test
    public void testLoadBeans() {
        Capture<Pageable> pageableCapture = newCapture();
        expect(udmBaselineFilterController.getWidget()).andReturn(udmBaselineFilterWidget).once();
        expect(udmBaselineFilterWidget.getAppliedFilter()).andReturn(baselineFilter).once();
        expect(udmBaselineService.getBaselineUsageDtos(eq(baselineFilter), capture(pageableCapture), isNull()))
            .andReturn(List.of()).once();
        replay(udmBaselineService, udmBaselineFilterController, udmBaselineFilterWidget);
        assertEquals(new ArrayList<>(), udmBaselineController.loadBeans(0, 10, null));
        verify(udmBaselineService, udmBaselineFilterController, udmBaselineFilterWidget);
        assertEquals(10, pageableCapture.getValue().getLimit());
        assertEquals(0, pageableCapture.getValue().getOffset());
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(udmBaselineController.instantiateWidget(), instanceOf(UdmBaselineWidget.class));
    }

    @Test
    public void testGetExportUdmBaselineUsagesStreamSource() {
        OffsetDateTime date = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        Capture<Supplier<String>> fileNameSupplierCapture = newCapture();
        Capture<Consumer<PipedOutputStream>> posConsumerCapture = newCapture();
        String fileName = "export_udm_baseline_usage_";
        Supplier<String> fileNameSupplier = () -> fileName;
        Supplier<InputStream> inputStreamSupplier =
                () -> IOUtils.toInputStream(StringUtils.EMPTY, StandardCharsets.UTF_8);
        PipedOutputStream pos = new PipedOutputStream();
        expect(OffsetDateTime.now()).andReturn(date).once();
        expect(udmBaselineFilterController.getWidget()).andReturn(udmBaselineFilterWidget).once();
        expect(udmBaselineFilterWidget.getAppliedFilter()).andReturn(baselineFilter).once();
        expect(streamSourceHandler.getCsvStreamSource(capture(fileNameSupplierCapture), capture(posConsumerCapture)))
            .andReturn(new StreamSource(fileNameSupplier, "csv", inputStreamSupplier)).once();
        udmReportService.writeUdmBaselineUsageCsvReport(baselineFilter, pos);
        expectLastCall().once();
        replay(OffsetDateTime.class, udmBaselineFilterWidget, udmBaselineFilterController,
            streamSourceHandler, udmReportService);
        IStreamSource streamSource = udmBaselineController.getExportUdmBaselineUsagesStreamSource();
        assertEquals("export_udm_baseline_usage_01_02_2019_03_04.csv", streamSource.getSource().getKey().get());
        assertEquals(fileName, fileNameSupplierCapture.getValue().get());
        Consumer<PipedOutputStream> posConsumer = posConsumerCapture.getValue();
        posConsumer.accept(pos);
        assertNotNull(posConsumer);
        verify(OffsetDateTime.class, udmBaselineFilterWidget, udmBaselineFilterController, streamSourceHandler,
            udmReportService);
    }

    @Test
    public void testDeleteFromBaseline() {
        mockStatic(RupContextUtils.class);
        Set<String> usageIds = ImmutableSet.of("f6e201bf-7b34-470f-937c-7e3bdeac0efe");
        expect(RupContextUtils.getUserName()).andReturn("user@copyright.com").once();
        udmBaselineService.deleteFromBaseline(usageIds, "Reason to delete", "user@copyright.com");
        expectLastCall().once();
        replay(udmBaselineService, RupContextUtils.class);
        udmBaselineController.deleteFromBaseline(usageIds, "Reason to delete");
        verify(udmBaselineService, RupContextUtils.class);
    }

    @Test
    public void testGetUdmRecordThreshold() {
        expect(udmBaselineService.getUdmRecordThreshold()).andReturn(1000).once();
        replay(udmBaselineService);
        assertEquals(1000, udmBaselineController.getUdmRecordThreshold());
        verify(udmBaselineService);
    }
}
