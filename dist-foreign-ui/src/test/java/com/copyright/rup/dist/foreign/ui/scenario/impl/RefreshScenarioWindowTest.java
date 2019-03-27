package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link RefreshScenarioWindow}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/12/18
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class RefreshScenarioWindowTest {

    @Test
    public void testStructure() {
        RefreshScenarioWindow window = new RefreshScenarioWindow(value -> null, value -> 0, null);
        assertEquals("Refresh Scenario", window.getCaption());
        assertEquals(400, window.getHeight(), 0);
        assertEquals(800, window.getWidth(), 0);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        verifyGrid(content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2));
    }

    @Test
    public void testOnOkButtonClick() {
        IScenariosController controller = createMock(IScenariosController.class);
        RefreshScenarioWindow window = new RefreshScenarioWindow(value -> null, value -> 0, controller);
        VerticalLayout layout = (VerticalLayout) window.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) layout.getComponent(2);
        Button okButton = (Button) buttonsLayout.getComponent(0);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.getInvalidRightsholders()).andReturn(Collections.emptyList()).once();
        controller.refreshScenario();
        expectLastCall().once();
        replay(controller, clickEvent);
        Collection<?> listeners = okButton.getListeners(ClickEvent.class);
        assertEquals(1, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent);
    }

    @Test
    public void testOnOkButtonClickInvalidRightsholders() {
        IScenariosController controller = createMock(IScenariosController.class);
        RefreshScenarioWindow window = new RefreshScenarioWindow(value -> null, value -> 0, controller);
        VerticalLayout layout = (VerticalLayout) window.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) layout.getComponent(2);
        Button okButton = (Button) buttonsLayout.getComponent(0);
        mockStatic(Windows.class);
        ClickEvent clickEvent = createMock(ClickEvent.class);
        expect(controller.getInvalidRightsholders()).andReturn(Collections.singletonList(100000000L)).once();
        Windows.showNotificationWindow("Scenario cannot be refreshed. The following rightsholder(s) are absent in " +
            "PRM: <i><b>[100000000]</b></i>");
        replay(controller, clickEvent, Windows.class);
        Collection<?> listeners = okButton.getListeners(ClickEvent.class);
        assertEquals(1, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(controller, clickEvent, Windows.class);
    }

    private void verifyGrid(Component component) {
        assertTrue(component instanceof Grid);
        Grid grid = (Grid) component;
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Detail ID", "Detail Status", "Product Family", "Usage Batch Name",
            "Fiscal Year", "RRO Account #", "RRO Name", "Payment Date", "Title", "Article", "Standard Number",
            "Standard Number Type", "Wr Wrk Inst", "System Title", "RH Account #", "RH Name", "Publisher", "Pub Date",
            "Number of Copies", "Reported value", "Amt in USD", "Gross Amt in USD", "Market", "Market Period From",
            "Market Period To", "Author", "Comment"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        verifySize(grid);
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        Button okButton = (Button) horizontalLayout.getComponent(0);
        assertEquals("Ok", okButton.getCaption());
        assertEquals("Ok", okButton.getId());
        Button cancelButton = (Button) horizontalLayout.getComponent(1);
        assertEquals("Cancel", cancelButton.getCaption());
        assertEquals("Cancel", cancelButton.getId());
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(new MarginInfo(false, false, false, false), horizontalLayout.getMargin());
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }
}
