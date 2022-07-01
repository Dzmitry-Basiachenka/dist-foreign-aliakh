package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioWidget;
import com.copyright.rup.vaadin.widget.api.IWidget;

import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclScenarioController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/28/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclScenarioControllerTest {

    private static final String SCENARIO_UID = "2398769d-8862-42e8-9504-9cbe19376b4b";

    private AclScenarioController controller;
    private IAclScenarioService aclScenarioService;
    private IAclUsageService aclUsageService;
    private AclScenario scenario;

    @Before
    public void setUp() {
        scenario = buildAclScenario();
        controller = new AclScenarioController();
        controller.setScenario(scenario);
        aclScenarioService = createMock(IAclScenarioService.class);
        aclUsageService = createMock(IAclUsageService.class);
        Whitebox.setInternalState(controller, aclScenarioService);
        Whitebox.setInternalState(controller, aclUsageService);
    }

    @Test
    public void testGetScenario() {
        assertEquals(scenario, controller.getScenario());
    }

    @Test
    public void testSetScenario() {
        controller.setScenario(scenario);
        assertSame(scenario, controller.getScenario());
    }

    @Test
    public void testPerformSearch() {
        IAclScenarioWidget widget = createMock(IAclScenarioWidget.class);
        Whitebox.setInternalState(controller, IWidget.class, widget);
        widget.applySearch();
        expectLastCall().once();
        replay(widget);
        controller.performSearch();
        verify(widget);
    }

    @Test
    public void testGetAclScenarioWithAmountsAndLastAction() {
        AclScenarioDto scenarioDto = new AclScenarioDto();
        scenarioDto.setId(SCENARIO_UID);
        expect(
            aclScenarioService.getAclScenarioWithAmountsAndLastAction(scenario.getId())).andReturn(scenarioDto).once();
        replay(aclScenarioService);
        assertSame(scenarioDto, controller.getAclScenarioWithAmountsAndLastAction());
        verify(aclScenarioService);
    }

    @Test
    public void testLoadBeans() {
        AclScenarioDto scenarioDto = new AclScenarioDto();
        scenarioDto.setId(SCENARIO_UID);
        Capture<Pageable> pageableCapture = newCapture();
        expect(aclUsageService.getAclRightsholderTotalsHoldersByScenarioId(eq(scenario.getId()), anyString(),
            capture(pageableCapture), isNull())).andReturn(Collections.emptyList()).once();
        expect(
            aclScenarioService.getAclScenarioWithAmountsAndLastAction(scenario.getId())).andReturn(scenarioDto).once();
        replay(aclScenarioService, aclUsageService);
        controller.initWidget();
        List<AclRightsholderTotalsHolder> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(aclScenarioService, aclUsageService);
    }

    @Test
    public void testGetSize() {
        AclScenarioDto scenarioDto = new AclScenarioDto();
        scenarioDto.setId(SCENARIO_UID);
        expect(aclUsageService.getAclRightsholderTotalsHolderCountByScenarioId(scenario.getId(),
            StringUtils.EMPTY)).andReturn(1).once();
        expect(controller.getAclScenarioWithAmountsAndLastAction()).andReturn(scenarioDto).once();
        replay(aclScenarioService, aclUsageService);
        controller.initWidget();
        assertEquals(1, controller.getSize());
        verify(aclScenarioService, aclUsageService);
    }

    @Test
    public void testInstantiateWidget() {
        IAclScenarioWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(AclScenarioWidget.class, widget.getClass());
    }

    private AclScenario buildAclScenario() {
        AclScenario aclScenario = new AclScenario();
        aclScenario.setId(SCENARIO_UID);
        aclScenario.setName("Scenario name");
        return aclScenario;
    }
}
