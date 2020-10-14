package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenariosReportWidget;

import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link LiabilitiesSummaryByRhAndWorkReportController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/14/2020
 *
 * @author Aliaksandr Liakh
 */
public class LiabilitiesSummaryByRhAndWorkReportControllerTest {

    private static final String SAL_PRODUCT_FAMILY = "SAL";

    private IScenarioService scenarioService;
    private IProductFamilyProvider productFamilyProvider;
    private LiabilitiesSummaryByRhAndWorkReportController controller;

    @Before
    public void setUp() {
        controller = new LiabilitiesSummaryByRhAndWorkReportController();
        scenarioService = createMock(IScenarioService.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        Whitebox.setInternalState(controller, scenarioService);
        Whitebox.setInternalState(controller, productFamilyProvider);
    }

    @Test
    public void testGetScenarios() {
        List<Scenario> scenarios = Collections.emptyList();
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(SAL_PRODUCT_FAMILY).once();
        expect(scenarioService.getScenariosByProductFamiliesAndStatuses(
            Collections.singleton(SAL_PRODUCT_FAMILY), Sets.newHashSet(ScenarioStatusEnum.values())))
            .andReturn(scenarios).once();
        replay(scenarioService, productFamilyProvider);
        assertEquals(scenarios, controller.getScenarios());
        verify(scenarioService, productFamilyProvider);
    }

    @Test
    public void testGetCsvStreamSource() {
        // TODO {aliakh} test when the service is implemented
    }

    @Test
    public void testInstantiateWidget() {
        ICommonScenariosReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(CommonScenariosReportWidget.class, widget.getClass());
    }
}
