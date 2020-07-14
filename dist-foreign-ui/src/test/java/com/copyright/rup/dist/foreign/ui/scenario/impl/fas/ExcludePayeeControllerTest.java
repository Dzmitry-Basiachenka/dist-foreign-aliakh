package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeFilterWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeWidget;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.easymock.Capture;
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
    private static final String SCENARIO_ID = "44bd227a-8ea7-464c-b283-831ef2f10007";
    private IExcludePayeeController controller;
    private IUsageService usageService;
    private IFasUsageService fasUsageService;
    private IExcludePayeeFilterController filterController;
    private IExcludePayeeFilterWidget filterWidget;

    @Before
    public void setUp() {
        usageService = createMock(IUsageService.class);
        fasUsageService = createMock(IFasUsageService.class);
        controller = new ExcludePayeeController();
        filterController = createMock(IExcludePayeeFilterController.class);
        filterWidget = createMock(IExcludePayeeFilterWidget.class);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, fasUsageService);
        Whitebox.setInternalState(controller, filterController);
    }

    @Test
    public void testRedesignateDetails() {
        Set<Long> accountNumbers = Collections.singleton(2000017566L);
        Set<String> scenarioIds = Collections.singleton(SCENARIO_ID);
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
        Set<Long> accountNumbers = Collections.singleton(2000017566L);
        Set<String> scenarioIds = Collections.singleton(SCENARIO_ID);
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
        Set<Long> accountNumbers = Collections.singleton(2000017566L);
        Set<String> scenarioIds = Collections.singleton(SCENARIO_ID);
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
        IExcludePayeeWidget widget = createMock(IExcludePayeeWidget.class);
        Whitebox.setInternalState(controller, widget);
        List<PayeeTotalHolder> payeeTotalHolders = Collections.singletonList(new PayeeTotalHolder());
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        Capture<Pageable> pageableCapture = new Capture<>();
        Capture<Sort> sortCapture = new Capture<>();
        expect(filterController.getWidget()).andReturn(filterWidget).once();
        expect(filterWidget.getAppliedFilter()).andReturn(filter).once();
        expect(widget.getSearchValue()).andReturn("Search value").once();
        expect(usageService.getPayeeTotalHoldersByFilter(eq(filter), capture(pageableCapture), capture(sortCapture)))
            .andReturn(payeeTotalHolders).once();
        replay(usageService, widget, filterWidget, filterController);
        assertEquals(payeeTotalHolders, controller.getPayeeTotalHolders(0, 20,
            Collections.singletonList(new QuerySortOrder("SORT_PROPERTY", SortDirection.DESCENDING))));
        assertEquals(0, pageableCapture.getValue().getOffset());
        assertEquals(20, pageableCapture.getValue().getLimit());
        assertEquals("SORT_PROPERTY", sortCapture.getValue().getProperty());
        verify(usageService, widget, filterWidget, filterController);
    }
}
