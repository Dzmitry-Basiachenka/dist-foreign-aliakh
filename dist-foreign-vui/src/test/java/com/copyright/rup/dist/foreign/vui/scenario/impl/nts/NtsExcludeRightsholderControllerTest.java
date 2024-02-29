package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;

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

    private static final String SCENARIO_ID = "747308ff-f4c7-4ae9-8e20-ed90a2c2dd20";
    private static final String REASON = "reason";

    private INtsUsageService ntsUsageService;
    private IRightsholderService rightsholderService;
    private NtsExcludeRightsholderController controller;

    @Before
    public void setUp() {
        rightsholderService = createMock(IRightsholderService.class);
        ntsUsageService = createMock(INtsUsageService.class);
        controller = new NtsExcludeRightsholderController();
        Whitebox.setInternalState(controller, ntsUsageService);
        Whitebox.setInternalState(controller, rightsholderService);
        Whitebox.setInternalState(controller, buildScenario());
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

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(NtsExcludeRightsholderWidget.class));
    }

    private Scenario buildScenario() {
        var scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        return scenario;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        var rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }

    private RightsholderPayeePair buildRightsholderPayeePair(Rightsholder rightsholder, Rightsholder payee) {
        var pair = new RightsholderPayeePair();
        pair.setPayee(payee);
        pair.setRightsholder(rightsholder);
        return pair;
    }
}
