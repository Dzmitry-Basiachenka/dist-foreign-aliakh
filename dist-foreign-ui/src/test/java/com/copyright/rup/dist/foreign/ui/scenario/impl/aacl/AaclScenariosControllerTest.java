package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenariosWidget;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AaclScenariosController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/31/2020
 *
 * @author Aliaksandr Liakh
 */
public class AaclScenariosControllerTest {

    private static final String SCENARIO_ID = "2fe241c4-7625-437b-9c8f-ff7ba4d3cb07";
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final String SCENARIO_NAME = "Scenario name";
    private AaclScenariosController scenariosController;
    private ILicenseeClassService licenseeClassService;
    private IAaclScenariosWidget scenariosWidget;
    private Scenario scenario;

    @Before
    public void setUp() {
        licenseeClassService = createMock(ILicenseeClassService.class);
        scenariosController = new AaclScenariosController();
        buildScenario();
        scenariosWidget = createMock(IAaclScenariosWidget.class);
        Whitebox.setInternalState(scenariosController, licenseeClassService);
        Whitebox.setInternalState(scenariosController, "widget", scenariosWidget);
    }

    @Test
    public void testGetDetailLicenseeClassesByScenarioId() {
        String scenarioId = "43e6b6e8-4c80-40ba-9836-7b27b2bbca5f";
        List<DetailLicenseeClass> detailLicenseeClasses = Collections.singletonList(new DetailLicenseeClass());
        expect(licenseeClassService.getDetailLicenseeClassesByScenarioId(scenarioId))
            .andReturn(detailLicenseeClasses).once();
        replay(licenseeClassService);
        assertSame(detailLicenseeClasses, scenariosController.getDetailLicenseeClassesByScenarioId(scenarioId));
        verify(licenseeClassService);
    }

    @Test
    public void testGetCriteriaHtmlRepresentation() {
        IScenarioUsageFilterService scenarioUsageFilterService = createMock(IScenarioUsageFilterService.class);
        Whitebox.setInternalState(scenariosController, scenarioUsageFilterService);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        ScenarioUsageFilter scenarioUsageFilter = new ScenarioUsageFilter();
        scenarioUsageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        scenarioUsageFilter.setProductFamily(AACL_PRODUCT_FAMILY);
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId("batchId");
        usageBatch.setName("BatchName");
        scenarioUsageFilter.setUsageBatches(Collections.singleton(usageBatch));
        expect(scenarioUsageFilterService.getByScenarioId(SCENARIO_ID)).andReturn(scenarioUsageFilter).once();
        PowerMock.replay(scenariosWidget, scenarioUsageFilterService);
        String result = scenariosController.getCriteriaHtmlRepresentation();
        assertTrue(result.contains("<b>Selection Criteria:</b>"));
        assertTrue(result.contains("<li><b><i>Product Family </i></b>(AACL)</li>"));
        assertTrue(result.contains("<li><b><i>Batch in </i></b>(BatchName)</li>"));
        assertTrue(result.contains("<li><b><i>Status </i></b>(ELIGIBLE)</li>"));
        PowerMock.verify(scenariosWidget, scenarioUsageFilterService);
    }

    private void buildScenario() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName(SCENARIO_NAME);
        scenario.setProductFamily(AACL_PRODUCT_FAMILY);
    }
}
