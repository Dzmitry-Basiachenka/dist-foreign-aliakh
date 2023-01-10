package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeFilterWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeWidget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link FasExcludePayeeController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/17/19
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
public class FasExcludePayeeControllerTest {

    private static final String REASON = "reason";
    private static final String SCENARIO_ID = "44bd227a-8ea7-464c-b283-831ef2f10007";
    private IFasExcludePayeeController controller;
    private IUsageService usageService;
    private IFasUsageService fasUsageService;
    private IFasExcludePayeeFilterController filterController;
    private IFasExcludePayeeFilterWidget filterWidget;

    @Before
    public void setUp() {
        usageService = createMock(IUsageService.class);
        fasUsageService = createMock(IFasUsageService.class);
        controller = new FasExcludePayeeController();
        filterController = createMock(IFasExcludePayeeFilterController.class);
        filterWidget = createMock(IFasExcludePayeeFilterWidget.class);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, fasUsageService);
        Whitebox.setInternalState(controller, filterController);
    }

    @Test
    public void testRedesignateDetails() {
        Set<Long> accountNumbers = Set.of(2000017566L);
        Set<String> scenarioIds = Set.of(SCENARIO_ID);
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(scenarioIds);
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(filter).once();
        fasUsageService.redesignateToNtsWithdrawnByPayees(scenarioIds, accountNumbers, REASON);
        expectLastCall().once();
        replay(fasUsageService, filterController, filterWidget);
        controller.redesignateDetails(accountNumbers, REASON);
        verify(fasUsageService, filterController, filterWidget);
    }

    @Test
    public void testExcludeDetails() {
        Set<Long> accountNumbers = Set.of(2000017566L);
        Set<String> scenarioIds = Set.of(SCENARIO_ID);
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(scenarioIds);
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(filter).once();
        fasUsageService.deleteFromScenarioByPayees(scenarioIds, accountNumbers, REASON);
        expectLastCall().once();
        replay(fasUsageService, filterController, filterWidget);
        controller.excludeDetails(accountNumbers, REASON);
        verify(fasUsageService, filterController, filterWidget);
    }

    @Test
    public void testGetAccountNumbersInvalidForExclude() {
        Set<Long> accountNumbers = Set.of(2000017566L);
        Set<String> scenarioIds = Set.of(SCENARIO_ID);
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(scenarioIds);
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(fasUsageService.getAccountNumbersInvalidForExclude(scenarioIds, accountNumbers))
            .andReturn(accountNumbers).once();
        replay(fasUsageService, filterController, filterWidget);
        assertSame(accountNumbers, controller.getAccountNumbersInvalidForExclude(accountNumbers));
        verify(fasUsageService, filterController, filterWidget);
    }

    @Test
    public void testGetPayeeTotalHolders() {
        IFasExcludePayeeWidget widget = createMock(IFasExcludePayeeWidget.class);
        Whitebox.setInternalState(controller, widget);
        List<PayeeTotalHolder> payeeTotalHolders = List.of(new PayeeTotalHolder());
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(usageService.getPayeeTotalHoldersByFilter(filter)).andReturn(payeeTotalHolders).once();
        replay(usageService, widget, filterWidget, filterController);
        assertEquals(payeeTotalHolders, controller.getPayeeTotalHolders());
        verify(usageService, widget, filterWidget, filterController);
    }
}
