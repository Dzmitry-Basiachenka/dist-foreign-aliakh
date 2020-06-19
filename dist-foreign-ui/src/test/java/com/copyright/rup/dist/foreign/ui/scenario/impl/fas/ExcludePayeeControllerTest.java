package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeFilterWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeWidget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link ExcludePayeeController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/17/19
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
public class ExcludePayeeControllerTest {

    private static final String REASON = "reason";
    private IExcludePayeeController controller;
    private IUsageService usageService;
    private IFasUsageService fasUsageService;
    private IExcludePayeeFilterController filterController;
    private Scenario scenario;

    @Before
    public void setUp() {
        usageService = createMock(IUsageService.class);
        fasUsageService = createMock(IFasUsageService.class);
        controller = new ExcludePayeeController();
        filterController = createMock(IExcludePayeeFilterController.class);
        scenario = buildScenario();
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, fasUsageService);
        Whitebox.setInternalState(controller, filterController);
        Whitebox.setInternalState(controller, scenario);
    }

    @Test
    public void testRedesignateDetails() {
        Set<Long> accountNumbers = Collections.singleton(2000017566L);
        fasUsageService.redesignateToNtsWithdrawnByPayees(scenario.getId(), accountNumbers, REASON);
        expectLastCall().once();
        replay(fasUsageService);
        controller.redesignateDetails(accountNumbers, REASON);
        verify(fasUsageService);
    }

    @Test
    public void testExcludeDetails() {
        Set<Long> accountNumbers = Collections.singleton(2000017566L);
        fasUsageService.deleteFromScenarioByPayees(scenario.getId(), accountNumbers, REASON);
        expectLastCall().once();
        replay(fasUsageService);
        controller.excludeDetails(accountNumbers, REASON);
        verify(fasUsageService);
    }

    @Test
    public void testGetPayeeTotalHolders() {
        IExcludePayeeFilterWidget filterWidget = createMock(IExcludePayeeFilterWidget.class);
        IExcludePayeeWidget widget = createMock(IExcludePayeeWidget.class);
        Whitebox.setInternalState(controller, widget);
        List<PayeeTotalHolder> payeeTotalHolders = Collections.singletonList(new PayeeTotalHolder());
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(widget.getSearchValue()).andReturn("Search value").once();
        expect(usageService.getPayeeTotalHoldersByFilter(filter)).andReturn(payeeTotalHolders).once();
        replay(usageService, widget, filterWidget, filterController);
        assertEquals(payeeTotalHolders, controller.getPayeeTotalHolders());
        verify(usageService, widget, filterWidget, filterController);
    }

    private Scenario buildScenario() {
        Scenario newScenario = new Scenario();
        newScenario.setId(RupPersistUtils.generateUuid());
        return newScenario;
    }
}
