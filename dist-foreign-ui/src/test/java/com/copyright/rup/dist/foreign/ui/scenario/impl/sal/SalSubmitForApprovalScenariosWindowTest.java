package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link SalSubmitForApprovalScenariosWindow}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/08/23
 *
 * @author Mikita Maistrenka
 */
public class SalSubmitForApprovalScenariosWindowTest {

    private static final String SCENARIO_ID_1 = "ae86765c-0669-40fe-b16d-d246e2cb42aa";
    private static final String SCENARIO_ID_2 = "96a7e995-bf59-4852-b6e9-49b5b74ec35d";
    private Scenario scenario1;
    private Scenario scenario2;

    private SalSubmitForApprovalScenariosWindow window;

    @Before
    public void setUp() {
        scenario1 = buildScenario(SCENARIO_ID_1, "SAL Distribution 02/08/2023");
        scenario2 = buildScenario(SCENARIO_ID_2, "SAL Distribution 02/09/2023");
        ISalScenariosController controller = createMock(ISalScenariosController.class);
        expect(controller.getScenariosByStatus(ScenarioStatusEnum.IN_PROGRESS))
            .andReturn(List.of(scenario1, scenario2)).once();
        replay(controller);
        window = new SalSubmitForApprovalScenariosWindow(controller);
        verify(controller);
    }

    @Test
    public void testComponentStructure() {
        assertEquals(500, window.getWidth(), 0);
        assertEquals(400, window.getHeight(), 0);
        assertEquals("sal-choose-scenarios-window", window.getStyleName());
        assertEquals("sal-choose-scenarios-window", window.getId());
        assertEquals(Sizeable.Unit.PIXELS, window.getWidthUnits());
        assertEquals(VerticalLayout.class, window.getContent().getClass());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals("Choose Scenarios to Submit for Approval", window.getCaption());
        assertEquals(3, content.getComponentCount());
        Component searchWidget = content.getComponent(0);
        assertThat(searchWidget, instanceOf(SearchWidget.class));
        verifyGrid((Grid) content.getComponent(1));
        verifyButtonsLayout((HorizontalLayout) content.getComponent(2));
    }

    @Test
    public void testSubmitButtonState() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        Grid<Scenario> grid = (Grid<Scenario>) content.getComponent(1);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button submitButton = (Button) buttonsLayout.getComponent(0);
        grid.select(scenario1);
        assertTrue(submitButton.isEnabled());
        grid.select(scenario2);
        assertTrue(submitButton.isEnabled());
        grid.deselectAll();
        assertFalse(submitButton.isEnabled());
    }

    private void verifyGrid(Grid grid) {
        assertNull(grid.getCaption());
        List<Column> columns = grid.getColumns();
        assertEquals(List.of("Scenario Name"), columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        assertEquals(List.of(-1.0), columns.stream().map(Column::getWidth).collect(Collectors.toList()));
        Object[][] expectedCells = {
            {"SAL Distribution 02/08/2023"},
            {"SAL Distribution 02/09/2023"}
        };
        verifyGridItems(grid, List.of(scenario1, scenario2), expectedCells);
    }

    private void verifyButtonsLayout(HorizontalLayout buttonsLayout) {
        assertEquals(2, buttonsLayout.getComponentCount());
        Component submitButton = buttonsLayout.getComponent(0);
        assertEquals(Button.class, submitButton.getClass());
        assertEquals("Submit for Approval", submitButton.getCaption());
        assertFalse(submitButton.isEnabled());
        Component closeButton = buttonsLayout.getComponent(1);
        assertEquals(Button.class, closeButton.getClass());
        assertEquals("Close", closeButton.getCaption());
    }

    private static Scenario buildScenario(String id, String name) {
        Scenario scenario = new Scenario();
        scenario.setId(id);
        scenario.setName(name);
        return scenario;
    }
}
