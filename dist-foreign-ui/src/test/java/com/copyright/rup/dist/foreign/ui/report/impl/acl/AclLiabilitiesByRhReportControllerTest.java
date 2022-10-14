package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
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

/**
 * Verifies {@link AclLiabilitiesByRhReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/13/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class, AclLiabilitiesByRhReportController.class})
public class AclLiabilitiesByRhReportControllerTest {

    @Test
    public void testGetCsvStreamSource() {
        AclLiabilitiesByRhReportController controller = new AclLiabilitiesByRhReportController();
        IAclCalculationReportService aclReportService = createMock(IAclCalculationReportService.class);
        Whitebox.setInternalState(controller, aclReportService);
        OffsetDateTime now = OffsetDateTime.of(2021, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        AclCalculationReportsInfoDto reportInfo = new AclCalculationReportsInfoDto();
        reportInfo.setPeriod(202012);
        reportInfo.setUser("user@copyroght.com");
        IAclCommonReportWidget widget = createMock(IAclCommonReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        Capture<OutputStream> osCapture = newCapture();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(widget.getReportInfo()).andReturn(reportInfo).once();
        aclReportService.writeAclLiabilitiesByRhReport(eq(reportInfo), capture(osCapture));
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, aclReportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("liabilities_by_rightsholder_report_01_02_2021_03_04.csv",
            streamSource.getSource().getKey().get());
        assertNotNull(streamSource.getSource().getValue().get());
        assertNotNull(osCapture.getValue());
        verify(OffsetDateTime.class, widget, aclReportService);
    }
}
