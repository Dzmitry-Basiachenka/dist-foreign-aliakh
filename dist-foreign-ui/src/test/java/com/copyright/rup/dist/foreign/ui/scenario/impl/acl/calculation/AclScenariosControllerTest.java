package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosWidget;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Verifies {@link AclScenariosController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/22/2022
 *
 * @author Dzmitry Basiahenka
 */
public class AclScenariosControllerTest {

    private AclScenariosController aclScenariosController;
    private IAclScenarioService aclScenarioService;

    @Before
    public void setUp() {
        aclScenariosController = new AclScenariosController();
        aclScenarioService = createMock(IAclScenarioService.class);
        Whitebox.setInternalState(aclScenariosController, aclScenarioService);
    }

    @Test
    public void testGetScenarios() {
        List<AclScenario> scenarios = Collections.singletonList(buildAclScenario());
        expect(aclScenarioService.getScenarios()).andReturn(scenarios).once();
        replay(aclScenarioService);
        assertSame(scenarios, aclScenariosController.getScenarios());
        verify(aclScenarioService);
    }

    @Test
    public void testInstantiateWidget() {
        IAclScenariosWidget widget = aclScenariosController.instantiateWidget();
        assertNotNull(widget);
        assertEquals(AclScenariosWidget.class, widget.getClass());
    }

    @Test
    public void testGetScenarioWithAmountsAndLastAction() {
        // TODO {aliakh} implement
    }

    @Test
    public void testGetCriteriaHtmlRepresentation() {
        // TODO {aliakh} implement
    }

    private AclScenario buildAclScenario() {
        AclScenario aclScenario = new AclScenario();
        aclScenario.setId("7ed0e17d-6baf-454c-803f-1d9be3cb3192");
        aclScenario.setName("ACL Scenario name");
        aclScenario.setDescription("Description");
        aclScenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        aclScenario.setEditableFlag(false);
        aclScenario.setPeriodEndDate(202212);
        aclScenario.setLicenseType("ACL");
        aclScenario.setCreateDate(Date.from(LocalDate.of(2022, 6, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        aclScenario.setCreateUser("user@copyright.com");
        return aclScenario;
    }
}
