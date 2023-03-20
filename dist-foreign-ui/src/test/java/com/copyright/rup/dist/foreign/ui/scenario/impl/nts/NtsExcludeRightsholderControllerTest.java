package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsExcludeRightsholderController;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.Set;

/**
 * Verifies functionality on {@link NtsExcludeRightsholderController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/04/2020
 *
 * @author Anton Azarenka
 */
public class NtsExcludeRightsholderControllerTest {

    private static final String SCENARIO_ID = "d022cb1e-7c63-42b1-a50b-4bb03661c492";
    private static final String REASON = "reason";

    private INtsExcludeRightsholderController controller;
    private INtsUsageService ntsUsageService;
    private IRightsholderService rightsholderService;

    @Before
    public void setUp() {
        rightsholderService = createMock(IRightsholderService.class);
        ntsUsageService = createMock(INtsUsageService.class);
        controller = new NtsExcludeRightsholderController();
        Scenario scenario = buildScenario();
        Whitebox.setInternalState(controller, ntsUsageService);
        Whitebox.setInternalState(controller, rightsholderService);
        Whitebox.setInternalState(controller, scenario);
    }

    @Test
    public void testExcludeDetails() {
        Set<Long> accountNumbers = Set.of(2000017566L);
        ntsUsageService.deleteFromScenarioByRightsholders(SCENARIO_ID, accountNumbers, REASON);
        expectLastCall().once();
        replay(ntsUsageService);
        controller.excludeDetails(accountNumbers, REASON);
        verify(ntsUsageService);
    }

    @Test
    public void testGetRightsholderPayeePair() {
        List<RightsholderPayeePair> list = List.of(buildRightsholderPayeePair(
            buildRightsholder(1000033963L, "Alfred R. Lindesmith"),
            buildRightsholder(2000148821L, "ABR Company, Ltd")),
            buildRightsholderPayeePair(
                buildRightsholder(7000425474L, "American Dialect Society"),
                buildRightsholder(2000196395L, "Advance Central Services")));
        expect(rightsholderService.getRhPayeePairByScenarioId(SCENARIO_ID)).andReturn(list).once();
        replay(rightsholderService);
        controller.getRightsholderPayeePairs();
        verify(rightsholderService);
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
