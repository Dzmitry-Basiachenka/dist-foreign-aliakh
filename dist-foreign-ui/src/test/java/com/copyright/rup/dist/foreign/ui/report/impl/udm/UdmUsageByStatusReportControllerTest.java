package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UdmUsageByStatusReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 04/20/2022
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
public class UdmUsageByStatusReportControllerTest {

    private UdmUsageByStatusReportController controller;
    private IUdmUsageService udmUsageService;

    @Before
    public void setUp() {
        controller = new UdmUsageByStatusReportController();
        udmUsageService = createMock(IUdmUsageService.class);
        Whitebox.setInternalState(controller, udmUsageService);
    }

    @Test
    public void testInstantiateWidget() {
        assertTrue(controller.instantiateWidget() instanceof UdmCommonStatusReportWidget);
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = Collections.singletonList(202112);
        expect(udmUsageService.getPeriods()).andReturn(periods).once();
        replay(udmUsageService);
        assertEquals(periods, controller.getPeriods());
        verify(udmUsageService);
    }

    @Test
    public void testGetCsvStreamSource() {
        OffsetDateTime now = OffsetDateTime.of(2021, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        expect(OffsetDateTime.now()).andReturn(now).once();
        replay(OffsetDateTime.class);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("usage_details_by_status_report_01_02_2021_03_04.csv", streamSource.getSource().getKey().get());
        assertNotNull(streamSource.getSource().getValue().get());
        verify(OffsetDateTime.class);
    }
}
