package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Verifies {@link SalSendToLmScenariosWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/04/20
 *
 * @author Uladzislau Shalamitski
 */
@SuppressWarnings("unchecked")
public class SalSendToLmScenariosWindowTest {

    private static final String SCENARIO_ID_1 = "32894a7f-b218-4347-b8f9-8236f75c9b2a";
    private static final String SCENARIO_ID_2 = "69a22caf-57da-45fa-afbb-35cf96ffd0ba";
    private Scenario scenario1;
    private Scenario scenario2;
    private SalSendToLmScenariosWindow window;

    @Before
    public void setUp() {
        scenario1 = buildScenario(SCENARIO_ID_1, "SAL Distribution 10/10/2020");
        scenario2 = buildScenario(SCENARIO_ID_2, "SAL Distribution 10/12/2020");
        ISalScenariosController controller = createMock(ISalScenariosController.class);
        expect(controller.getScenariosByStatus(ScenarioStatusEnum.APPROVED))
            .andReturn(List.of(scenario1, scenario2)).once();
        controller.sendToLm(Set.of(scenario1, scenario2));
        expectLastCall().once();
        replay(controller);
        window = new SalSendToLmScenariosWindow(controller);
        window.performAction(Set.of(scenario1, scenario2));
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
        assertEquals("Choose Scenarios to Send to LM", window.getCaption());
        assertEquals(3, content.getComponentCount());
        Component searchWidget = content.getComponent(0);
        assertThat(searchWidget, instanceOf(SearchWidget.class));
        verifyGrid((Grid) content.getComponent(1));
        verifyButtonsLayout((HorizontalLayout) content.getComponent(2));
    }

    private void verifyGrid(Grid grid) {
        assertNull(grid.getCaption());
        List<Column> columns = grid.getColumns();
        assertEquals(List.of("Scenario Name"), columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        assertEquals(List.of(-1.0), columns.stream().map(Column::getWidth).collect(Collectors.toList()));
        Object[][] expectedCells = {
            {"SAL Distribution 10/10/2020"},
            {"SAL Distribution 10/12/2020"}
        };
        verifyGridItems(grid, List.of(scenario1, scenario2), expectedCells);
    }

    private void verifyButtonsLayout(HorizontalLayout buttonsLayout) {
        assertEquals(2, buttonsLayout.getComponentCount());
        Component sendToLmButton = buttonsLayout.getComponent(0);
        assertEquals(Button.class, sendToLmButton.getClass());
        assertEquals("Send to LM", sendToLmButton.getCaption());
        assertFalse(sendToLmButton.isEnabled());
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
