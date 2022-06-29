package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Verifies {@link AclScenarioService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/23/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclScenarioServiceTest {

    private static final String SCENARIO_UID = "732f1f1f-1d63-45a4-9f07-357cba3429fc";

    private IAclScenarioService aclScenarioService;
    private IAclScenarioRepository aclScenarioRepository;

    @Before
    public void setUp() {
        aclScenarioRepository = createMock(IAclScenarioRepository.class);
        aclScenarioService = new AclScenarioService();
        Whitebox.setInternalState(aclScenarioService, aclScenarioRepository);
    }

    @Test
    public void testGetScenarios() {
        List<AclScenario> scenarios = Collections.singletonList(buildAclScenario());
        expect(aclScenarioRepository.findAll()).andReturn(scenarios).once();
        replay(aclScenarioRepository);
        assertSame(scenarios, aclScenarioService.getScenarios());
        verify(aclScenarioRepository);
    }

    @Test
    public void testAclScenarioExists() {
        expect(aclScenarioRepository.findCountByName("ACL Scenario 2022")).andReturn(1).once();
        replay(aclScenarioRepository);
        assertTrue(aclScenarioService.aclScenarioExists("ACL Scenario 2022"));
        verify(aclScenarioRepository);
    }

    @Test
    public void testGetScenarioWithAmountsAndLastAction() {
        AclScenarioDto scenario = new AclScenarioDto();
        scenario.setId(SCENARIO_UID);
        expect(aclScenarioRepository.findWithAmountsAndLastAction(scenario.getId())).andReturn(scenario).once();
        replay(aclScenarioRepository);
        assertSame(scenario, aclScenarioService.getAclScenarioWithAmountsAndLastAction(scenario.getId()));
        verify(aclScenarioRepository);
    }

    @Test
    public void testGetRightsholderAclTotalsHoldersByScenarioId() {
        //TODO {dbasiachenka} implement
    }

    @Test
    public void testGetRightsholderAclTotalsHolderCountByScenarioId() {
        //TODO {dbasiachenka} implement
    }

    private AclScenario buildAclScenario() {
        AclScenario aclScenario = new AclScenario();
        aclScenario.setId(SCENARIO_UID);
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
