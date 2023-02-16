package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.MultiSelectionModel.SelectAllCheckBoxVisibility;
import com.vaadin.ui.components.grid.MultiSelectionModelImpl;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link SalPerformScenariosActionsCommonWindow}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/16/23
 *
 * @author Mikita Maistrenka
 */
public class SalPerformScenariosActionsCommonWindowTest {

    private static final String SCENARIO_ID_1 = "149ae663-0532-4839-bf79-dfff83028f81";
    private static final String SCENARIO_ID_2 = "f0b2ba78-cdbc-4e74-a4c8-d5e21a2ce9ff";
    private Scenario scenario1;
    private Scenario scenario2;
    private SalPerformScenariosActionsCommonWindow window;
    private ISalScenariosController controller;

    @Before
    public void setUp() {
        scenario1 = buildScenario(SCENARIO_ID_1, "SAL Distribution 02/08/2023");
        scenario2 = buildScenario(SCENARIO_ID_2, "SAL Distribution 02/09/2023");
        controller = createMock(ISalScenariosController.class);
        expect(controller.getScenariosByStatus(ScenarioStatusEnum.IN_PROGRESS))
            .andReturn(List.of(scenario1, scenario2)).once();
        replay(controller);
        window = new SalPerformScenariosActionsCommonWindowMock(controller);
        verify(controller);
        reset(controller);
    }

    @Test
    public void testActionButtonState() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        Grid<Scenario> grid = (Grid<Scenario>) content.getComponent(1);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button button = (Button) buttonsLayout.getComponent(0);
        grid.select(scenario1);
        assertTrue(button.isEnabled());
        grid.select(scenario2);
        assertTrue(button.isEnabled());
        grid.deselectAll();
        assertFalse(button.isEnabled());
    }

    @Test
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(window, searchWidget);
        expect(searchWidget.getSearchValue()).andReturn("09").once();
        replay(searchWidget);
        VerticalLayout content = (VerticalLayout) window.getContent();
        Grid<Scenario> grid = (Grid<Scenario>) content.getComponent(1);
        ListDataProvider<Scenario> provider = (ListDataProvider<Scenario>) grid.getDataProvider();
        grid.select(scenario1);
        grid.select(scenario2);
        window.performSearch();
        SerializablePredicate filter = provider.getFilter();
        assertTrue(filter.test(scenario2));
        assertEquals(Set.of(scenario1, scenario2), grid.getSelectedItems());
        verify(searchWidget);
    }

    @Test
    public void testSelectAllCheckBoxVisibilityHidden() {
        expect(controller.getScenariosByStatus(ScenarioStatusEnum.IN_PROGRESS)).andReturn(List.of()).once();
        replay(controller);
        SalPerformScenariosActionsCommonWindow emptyWindow = new SalPerformScenariosActionsCommonWindowMock(controller);
        VerticalLayout content = (VerticalLayout) emptyWindow.getContent();
        Grid<Scenario> grid = (Grid<Scenario>) content.getComponent(1);
        MultiSelectionModelImpl<Scenario> selectionModel = (MultiSelectionModelImpl<Scenario>) grid.getSelectionModel();
        assertEquals(SelectAllCheckBoxVisibility.HIDDEN, selectionModel.getSelectAllCheckBoxVisibility());
        verify(controller);
    }

    @Test
    public void testSelectAllCheckBoxVisibilityVisible() {
        expect(controller.getScenariosByStatus(ScenarioStatusEnum.IN_PROGRESS))
            .andReturn(List.of(scenario1, scenario2)).once();
        replay(controller);
        SalPerformScenariosActionsCommonWindow windowWithScenarios =
            new SalPerformScenariosActionsCommonWindowMock(controller);
        VerticalLayout content = (VerticalLayout) windowWithScenarios.getContent();
        Grid<Scenario> grid = (Grid<Scenario>) content.getComponent(1);
        MultiSelectionModelImpl<Scenario> selectionModel = (MultiSelectionModelImpl<Scenario>) grid.getSelectionModel();
        assertEquals(SelectAllCheckBoxVisibility.VISIBLE, selectionModel.getSelectAllCheckBoxVisibility());
    }

    private static Scenario buildScenario(String id, String name) {
        Scenario scenario = new Scenario();
        scenario.setId(id);
        scenario.setName(name);
        return scenario;
    }

    private class SalPerformScenariosActionsCommonWindowMock extends SalPerformScenariosActionsCommonWindow {

        /**
         * Constructor.
         *
         * @param controller                   instance of {@link ISalScenariosController}
         */
        SalPerformScenariosActionsCommonWindowMock(ISalScenariosController controller) {
            super(controller, "button.submit", ScenarioStatusEnum.IN_PROGRESS);
        }

        @Override
        protected void performAction(Set<Scenario> selectedScenarios) {
            controller.handleAction(ScenarioActionTypeEnum.SUBMITTED, selectedScenarios);
        }
    }
}
