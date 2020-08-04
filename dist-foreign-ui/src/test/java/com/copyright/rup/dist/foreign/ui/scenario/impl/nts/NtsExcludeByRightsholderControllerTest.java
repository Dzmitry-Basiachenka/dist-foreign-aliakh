package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsScenarioService;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsExcludeByRightsholderController;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies functionality on {@link NtsExcludeByRightsholderController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/04/2020
 *
 * @author Anton Azarenka
 */
public class NtsExcludeByRightsholderControllerTest {

    private static final String SCENARIO_ID = "d022cb1e-7c63-42b1-a50b-4bb03661c492";
    private static final String REASON = "reason";

    private INtsExcludeByRightsholderController controller;
    private INtsScenarioService service;
    private IUsageService usageService;

    @Before
    public void setUp() {
        usageService = createMock(IUsageService.class);
        service = createMock(INtsScenarioService.class);
        controller = new NtsExcludeByRightsholderController();
        Scenario scenario = buildScenario();
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, service);
        Whitebox.setInternalState(controller, scenario);
    }

    @Test
    public void testExcludeDetails() {
        Set<Long> accountNumbers = Collections.singleton(2000017566L);
        usageService.deleteFromScenarioByRightsHolders(SCENARIO_ID, accountNumbers, REASON);
        expectLastCall().once();
        replay(usageService);
        controller.excludeDetails(accountNumbers, REASON);
        verify(usageService);
    }

    @Test
    public void testGetRightsholderPayeePair() {
        List<RightsholderPayeePair> list = Lists.newArrayList(buildRightsholderPayeePair(
            buildRightsholder(1000033963L, "Alfred R. Lindesmith"),
            buildRightsholder(2000148821L, "ABR Company, Ltd")),
            buildRightsholderPayeePair(
                buildRightsholder(7000425474L, "American Dialect Society"),
                buildRightsholder(2000196395L, "Advance Central Services")));
        expect(service.getRightsholdersByScenarioId(SCENARIO_ID)).andReturn(list).once();
        replay(service);
        controller.getRightsholderPayeePair();
        verify(service);
    }

    private Scenario buildScenario() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        return scenario;
    }

    private RightsholderPayeePair buildRightsholderPayeePair(Rightsholder rightsholder, Rightsholder payee) {
        RightsholderPayeePair pair = new RightsholderPayeePair();
        pair.setPayee(payee);
        pair.setRightsholder(rightsholder);
        return pair;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
