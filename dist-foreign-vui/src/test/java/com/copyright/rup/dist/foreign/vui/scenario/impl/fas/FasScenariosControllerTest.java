package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasExcludePayeeController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenariosWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.security.SecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Verifies {@link FasScenariosController}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class FasScenariosControllerTest {

    private static final String SCENARIO_ID = "80750abe-4bf9-4806-a357-4c977ec841f2";
    private static final String SCENARIO_NAME = "Scenario name";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private FasScenariosController scenariosController;
    private IFasExcludePayeeController excludePayeesController;
    private IScenarioService scenarioService;
    private IFasScenariosWidget scenariosWidget;
    private IProductFamilyProvider productFamilyProvider;
    private Scenario scenario;

    @Before
    public void setUp() {
        excludePayeesController = createMock(IFasExcludePayeeController.class);
        scenarioService = createMock(IScenarioService.class);
        scenariosWidget = createMock(IFasScenariosWidget.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        buildScenario();
        scenariosController = new FasScenariosController();
        Whitebox.setInternalState(scenariosController, excludePayeesController);
        Whitebox.setInternalState(scenariosController, scenarioService);
        Whitebox.setInternalState(scenariosController, "widget", scenariosWidget);
        Whitebox.setInternalState(scenariosController, productFamilyProvider);
        verify(SecurityUtils.class);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(scenariosController.instantiateWidget(), instanceOf(FasScenariosWidget.class));
    }

    @Test
    public void testOnExcludePayeesButtonClicked() {
        mockStatic(Windows.class);
        var widget = new FasExcludePayeeWidget();
        expect(excludePayeesController.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replay(excludePayeesController, Windows.class);
        scenariosController.onExcludePayeesButtonClicked();
        verify(excludePayeesController, Windows.class);
    }

    @Test
    public void testGetScenarios() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(scenarioService.getScenarios(FAS_PRODUCT_FAMILY)).andReturn(List.of()).once();
        replay(scenarioService, productFamilyProvider);
        assertEquals(List.of(), scenariosController.getScenarios());
        verify(scenarioService, productFamilyProvider);
    }

    @Test
    public void testGetCriteriaHtmlRepresentation() {
        var scenarioUsageFilter = buildScenarioUsageFilter();
        IScenarioUsageFilterService scenarioUsageFilterService = createMock(IScenarioUsageFilterService.class);
        IRightsholderService rightsholderService = createMock(IRightsholderService.class);
        Whitebox.setInternalState(scenariosController, scenarioUsageFilterService);
        Whitebox.setInternalState(scenariosController, rightsholderService);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(scenarioUsageFilterService.getByScenarioId(SCENARIO_ID)).andReturn(scenarioUsageFilter).once();
        expect(rightsholderService.updateAndGetRightsholders(Set.of(1000000001L, 1000000002L))).andReturn(
            Map.of(1000000001L, buildRightsholder(1000000001L, "Rothchild Consultants"), 1000000002L,
                buildRightsholder(1000000002L, "Royal Society of Victoria"))).once();
        replay(scenariosWidget, scenarioUsageFilterService, rightsholderService);
        assertEquals("<b>Selection Criteria:</b>" +
            "<ul><li><b><i>Product Family </i></b>(FAS)</li>" +
            "<li><b><i>Batch in </i></b>(BatchName)</li>" +
            "<li><b><i>RRO in </i></b>(1000000001: Rothchild Consultants, 1000000002: Royal Society of Victoria)</li>" +
            "<li><b><i>Payment Date To </i></b>(01/01/2010)</li>" +
            "<li><b><i>Status </i></b>(ELIGIBLE)</li>" +
            "<li><b><i>Fiscal Year To </i></b>(2018)</li>" +
            "</ul>", scenariosController.getCriteriaHtmlRepresentation());
        verify(scenariosWidget, scenarioUsageFilterService, rightsholderService);
    }

    private void buildScenario() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName(SCENARIO_NAME);
        scenario.setProductFamily(FAS_PRODUCT_FAMILY);
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        var rightsholder = new Rightsholder();
        rightsholder.setName(name);
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }

    private ScenarioUsageFilter buildScenarioUsageFilter() {
        var usageBatch = new UsageBatch();
        usageBatch.setId("batchId");
        usageBatch.setName("BatchName");
        var scenarioUsageFilter = new ScenarioUsageFilter();
        scenarioUsageFilter.setFiscalYear(2018);
        scenarioUsageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        scenarioUsageFilter.setProductFamily(FAS_PRODUCT_FAMILY);
        scenarioUsageFilter.setRhAccountNumbers(Set.of(1000000001L, 1000000002L));
        scenarioUsageFilter.setPaymentDate(LocalDate.of(2010, 1, 1));
        scenarioUsageFilter.setUsageBatches(Set.of(usageBatch));
        return scenarioUsageFilter;
    }
}
