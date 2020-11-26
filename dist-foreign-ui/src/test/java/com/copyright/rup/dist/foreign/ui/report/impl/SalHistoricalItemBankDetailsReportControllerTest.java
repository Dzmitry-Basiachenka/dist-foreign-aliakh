package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.ui.report.api.ISalHistoricalItemBankDetailsReportWidget;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link SalHistoricalItemBankDetailsReportController}.
 * <p>
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/25/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalHistoricalItemBankDetailsReportControllerTest {

    private SalHistoricalItemBankDetailsReportController controller;

    @Before
    public void setUp() {
        controller = new SalHistoricalItemBankDetailsReportController();
    }

    @Test
    public void testInstantiateWidget() {
        ISalHistoricalItemBankDetailsReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(SalHistoricalItemBankDetailsReportWidget.class, widget.getClass());
    }

    @Test
    public void testGetSalLicensees() {
        IUsageBatchService usageBatchService = createMock(IUsageBatchService.class);
        Whitebox.setInternalState(controller, usageBatchService);
        List<SalLicensee> salLicensees = Collections.singletonList(new SalLicensee());
        expect(usageBatchService.getSalLicensees()).andReturn(salLicensees).once();
        replay(usageBatchService);
        assertEquals(salLicensees, controller.getSalLicensees());
        verify(usageBatchService);
    }
}
