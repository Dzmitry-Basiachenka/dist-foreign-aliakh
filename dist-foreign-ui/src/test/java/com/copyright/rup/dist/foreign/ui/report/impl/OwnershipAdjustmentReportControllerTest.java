package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.report.api.IOwnershipAdjustmentReportWidget;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link OwnershipAdjustmentReportController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/27/2019
 *
 * @author Aliaksandr Liakh
 */
public class OwnershipAdjustmentReportControllerTest {

    @Test
    public void testGetScenarios() {
        OwnershipAdjustmentReportController controller = new OwnershipAdjustmentReportController();
        IScenarioService scenarioService = createMock(IScenarioService.class);
        Whitebox.setInternalState(controller, scenarioService);
        List<Scenario> scenarios = Collections.emptyList();
        expect(scenarioService.getScenarios()).andReturn(scenarios).once();
        replay(scenarioService);
        assertEquals(scenarios, controller.getScenarios());
        verify(scenarioService);
    }

    @Test
    public void testGetOwnershipAdjustmentReportStreamSource() {
        OwnershipAdjustmentReportController controller = new OwnershipAdjustmentReportController();
        IStreamSource streamSource = controller.getOwnershipAdjustmentReportStreamSource();
        assertNotNull(streamSource);
        assertEquals(OwnershipAdjustmentCsvReportExportStreamSource.class, streamSource.getClass());
    }

    @Test
    public void testInstantiateWidget() {
        OwnershipAdjustmentReportController controller = new OwnershipAdjustmentReportController();
        IOwnershipAdjustmentReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(OwnershipAdjustmentReportWidget.class, widget.getClass());
    }
}
