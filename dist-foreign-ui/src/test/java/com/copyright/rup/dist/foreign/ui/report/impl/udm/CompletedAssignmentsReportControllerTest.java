package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.report.api.udm.ICompletedAssignmentsReportWidget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link CompletedAssignmentsReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/06/2022
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
public class CompletedAssignmentsReportControllerTest {

    private CompletedAssignmentsReportController controller;
    private IUdmUsageService udmUsageService;

    @Before
    public void setUp() {
        controller = new CompletedAssignmentsReportController();
        udmUsageService = createMock(IUdmUsageService.class);
        Whitebox.setInternalState(controller, udmUsageService);
    }

    @Test
    public void testInstantiateWidget() {
        ICompletedAssignmentsReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(CompletedAssignmentsReportWidget.class, widget.getClass());
    }

    @Test
    public void testGetAllPeriods() {
        List<Integer> periods = Collections.singletonList(202112);
        expect(udmUsageService.getPeriods()).andReturn(periods).once();
        replay(udmUsageService);
        assertEquals(periods, controller.getAllPeriods());
        verify(udmUsageService);
    }
}
