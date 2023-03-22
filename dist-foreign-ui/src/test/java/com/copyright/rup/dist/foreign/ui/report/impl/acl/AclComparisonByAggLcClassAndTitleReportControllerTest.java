package com.copyright.rup.dist.foreign.ui.report.impl.acl;

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

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.service.api.acl.IAclCalculationReportService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclCommonReportWidget;

import org.easymock.Capture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.OutputStream;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Verifies {@link AclComparisonByAggLcClassAndTitleReportController}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/22/2023
 *
 * @author Mikita Maistrenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
public class AclComparisonByAggLcClassAndTitleReportControllerTest {

    @Test
    public void testInstantiateWidget() {
        AclComparisonByAggLcClassAndTitleReportController controller =
            new AclComparisonByAggLcClassAndTitleReportController();
        assertThat(controller.instantiateWidget(), instanceOf(AclComparisonByAggLcClassAndTitleReportWidget.class));
    }

    @Test
    public void testGetCsvStreamSource() {
        AclComparisonByAggLcClassAndTitleReportController controller =
            new AclComparisonByAggLcClassAndTitleReportController();
        IAclCalculationReportService aclReportService = createMock(IAclCalculationReportService.class);
        Whitebox.setInternalState(controller, aclReportService);
        OffsetDateTime now = OffsetDateTime.of(2021, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        AclCalculationReportsInfoDto reportInfo = new AclCalculationReportsInfoDto();
        reportInfo.setPeriod(202012);
        reportInfo.setPreviousPeriod(202112);
        reportInfo.setScenarios(List.of(new AclScenario()));
        reportInfo.setPreviousScenarios(List.of(new AclScenario()));
        IAclCommonReportWidget widget = createMock(IAclCommonReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        Capture<OutputStream> osCapture = newCapture();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(widget.getReportInfo()).andReturn(reportInfo).once();
        aclReportService.writeAclComparisonByAggLcClassAndTitleReport(eq(reportInfo), capture(osCapture));
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, aclReportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("comparison_by_agg_lc_class_and_title_report_01_02_2021_03_04.csv",
            streamSource.getSource().getKey().get());
        assertNotNull(streamSource.getSource().getValue().get());
        assertNotNull(osCapture.getValue());
        verify(OffsetDateTime.class, widget, aclReportService);
    }
}
