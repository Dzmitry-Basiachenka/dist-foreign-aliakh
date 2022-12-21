package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.common.ByteArrayStreamSource;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenariosReportWidget;

import com.google.common.collect.Sets;

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
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link SalLiabilitiesSummaryByRhAndWorkReportController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/14/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({OffsetDateTime.class, ByteArrayStreamSource.class})
public class SalLiabilitiesSummaryByRhAndWorkReportControllerTest {

    private static final String SAL_PRODUCT_FAMILY = "SAL";

    private IScenarioService scenarioService;
    private IProductFamilyProvider productFamilyProvider;
    private IReportService reportService;
    private SalLiabilitiesSummaryByRhAndWorkReportController controller;

    @Before
    public void setUp() {
        controller = new SalLiabilitiesSummaryByRhAndWorkReportController();
        scenarioService = createMock(IScenarioService.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        reportService = createMock(IReportService.class);
        Whitebox.setInternalState(controller, scenarioService);
        Whitebox.setInternalState(controller, productFamilyProvider);
        Whitebox.setInternalState(controller, reportService);
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
        OffsetDateTime now = OffsetDateTime.of(2019, 1, 2, 3, 4, 5, 6, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        List<Scenario> scenarios = Collections.singletonList(new Scenario());
        ICommonScenariosReportWidget widget = createMock(ICommonScenariosReportWidget.class);
        Whitebox.setInternalState(controller, widget);
        Capture<OutputStream> osCapture = newCapture();
        expect(OffsetDateTime.now()).andReturn(now).once();
        expect(widget.getSelectedScenarios()).andReturn(scenarios).once();
        reportService.writeSalLiabilitiesSummaryByRhAndWorkCsvReport(same(scenarios), capture(osCapture));
        expectLastCall().once();
        replay(OffsetDateTime.class, widget, reportService);
        IStreamSource streamSource = controller.getCsvStreamSource();
        assertEquals("liabilities_summary_by_rightsholder_and_work_01_02_2019_03_04.csv",
            streamSource.getSource().getKey().get());
        assertNotNull(streamSource.getSource().getValue().get());
        assertNotNull(osCapture.getValue());
        verify(OffsetDateTime.class, widget, reportService);
    }

    @Test
    public void testInstantiateWidget() {
        ICommonScenariosReportWidget widget = controller.instantiateWidget();
        assertNotNull(controller.instantiateWidget());
        assertEquals(CommonScenariosReportWidget.class, widget.getClass());
    }
}
