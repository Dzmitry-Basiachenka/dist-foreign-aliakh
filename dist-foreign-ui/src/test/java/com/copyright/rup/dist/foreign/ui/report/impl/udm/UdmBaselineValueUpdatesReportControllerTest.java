package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonUserNamesReportWidget;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UdmBaselineValueUpdatesReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/26/2022
 *
 * @author Dzmitry Basiachenka
 */
public class UdmBaselineValueUpdatesReportControllerTest {

    private UdmBaselineValueUpdatesReportController controller;
    private IUdmValueService udmValueService;

    @Before
    public void setUp() {
        controller = new UdmBaselineValueUpdatesReportController();
        udmValueService = createMock(IUdmValueService.class);
        Whitebox.setInternalState(controller, udmValueService);
    }

    @Test
    public void testInstantiateWidget() {
        IUdmCommonUserNamesReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(UdmCommonUserNamesReportWidget.class, widget.getClass());
    }

    @Test
    public void testGetAllPeriods() {
        List<Integer> periods = Collections.singletonList(202112);
        expect(udmValueService.getPeriods()).andReturn(periods).once();
        replay(udmValueService);
        assertEquals(periods, controller.getAllPeriods());
        verify(udmValueService);
    }

    @Test
    public void testGetUserNames() {
        //TODO {dbasiachenka} implement
    }

    @Test
    public void testGetCsvStreamSource() {
        //TODO {dbasiachenka} implement
    }
}
