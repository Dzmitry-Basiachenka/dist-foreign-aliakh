package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;

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
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link AclFundPoolByAggLcReportController}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/23/2023
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class, AclFundPoolByAggLcReportController.class})
public class AclFundPoolByAggLcReportControllerTest {

    private AclFundPoolByAggLcReportController controller;
    private IAclFundPoolService fundPoolService;

    @Before
    public void setUp() {
        controller = new AclFundPoolByAggLcReportController();
        fundPoolService = createMock(IAclFundPoolService.class);
        Whitebox.setInternalState(controller, fundPoolService);
    }

    @Test
    public void testGetFundPools() {
        Set<Integer> periods = Set.of(202212);
        List<AclFundPool> fundPools = List.of(new AclFundPool());
        expect(fundPoolService.getFundPoolsByPeriods(periods)).andReturn(fundPools).once();
        replay(fundPoolService);
        assertSame(fundPools, controller.getFundPools(periods));
        verify(fundPoolService);
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = List.of(202212, 202206);
        expect(fundPoolService.getPeriods()).andReturn(periods).once();
        replay(fundPoolService);
        assertSame(periods, controller.getPeriods());
        verify(fundPoolService);
    }

    @Test
    public void testGetCsvStreamSource() {
        IAclCalculationReportService aclReportService = createMock(IAclCalculationReportService.class);
        Whitebox.setInternalState(controller, aclReportService);
        OffsetDateTime now = OffsetDateTime.of(2021, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        AclFundPoolByAggLcReportWidget widget = createMock(AclFundPoolByAggLcReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        Capture<OutputStream> osCapture = newCapture();
        expect(OffsetDateTime.now()).andReturn(now).once();
        Set<String> fundPoolIds = Set.of("f9902a54-cf18-4fea-9fe5-f9aff53449a1");
        expect(widget.getFundPoolIds()).andReturn(fundPoolIds).once();
        aclReportService.writeAclFundPoolByAggLcReport(eq(fundPoolIds), capture(osCapture));
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, aclReportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("fund_pools_by_aggregate_licensee_class_report_01_02_2021_03_04.csv",
            streamSource.getSource().getKey().get());
        assertNotNull(streamSource.getSource().getValue().get());
        assertNotNull(osCapture.getValue());
        verify(OffsetDateTime.class, widget, aclReportService);
    }
}
