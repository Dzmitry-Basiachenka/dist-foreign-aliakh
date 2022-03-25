package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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
        verifyWindow(window, "Refresh Scenario", 800, 400, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        assertTrue(content.getComponent(1) instanceof Grid);
        Grid grid = (Grid) content.getComponent(1);
        verifyGrid(grid, Arrays.asList(
            Triple.of("Detail ID", 130.0, -1),
            Triple.of("Detail Status", -1.0, -1),
            Triple.of("Product Family", -1.0, -1),
            Triple.of("Usage Batch Name", 135.0, -1),
            Triple.of("RRO Account #", -1.0, -1),
            Triple.of("RRO Name", 135.0, -1),
            Triple.of("RH Account #", -1.0, -1),
            Triple.of("RH Name", 300.0, -1),
            Triple.of("Wr Wrk Inst", -1.0, -1),
            Triple.of("System Title", 300.0, -1),
            Triple.of("Standard Number", 125.0, -1),
            Triple.of("Standard Number Type", 155.0, -1),
            Triple.of("Fiscal Year", -1.0, -1),
            Triple.of("Payment Date", -1.0, -1),
            Triple.of("Title", 300.0, -1),
            Triple.of("Article", 135.0, -1),
            Triple.of("Publisher", 135.0, -1),
            Triple.of("Pub Date", 80.0, -1),
            Triple.of("Number of Copies", -1.0, -1),
            Triple.of("Reported Value", 113.0, -1),
            Triple.of("Gross Amt in USD", 130.0, -1),
            Triple.of("Batch Amt in USD", 130.0, -1),
            Triple.of("Market", 135.0, -1),
            Triple.of("Market Period From", 150.0, -1),
            Triple.of("Market Period To", 145.0, -1),
            Triple.of("Author", 300.0, -1),
            Triple.of("Comment", 200.0, -1)
        ));
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        verifyButtonsLayout(content.getComponent(2), "Ok", "Cancel");
        HorizontalLayout horizontalLayout = (HorizontalLayout) content.getComponent(2);
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(new MarginInfo(false, false, false, false), horizontalLayout.getMargin());
    }

    @Test
    public void testOnOkButtonClick() {
        IFasScenariosController controller = createMock(IFasScenariosController.class);
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
        IFasScenariosController controller = createMock(IFasScenariosController.class);
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
}
