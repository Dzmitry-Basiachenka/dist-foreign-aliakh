package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

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
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class RefreshScenarioWindowTest {

    @Test
    public void testStructure() {
        var window = new RefreshScenarioWindow(value -> null, value -> 0, null);
        verifyWindow(window, "Refresh Scenario", "1000px", "600px", Unit.PIXELS, true);
        var content = (VerticalLayout) UiTestHelper.getDialogContent(window);
        assertEquals(2, content.getComponentCount());
        assertThat(content.getComponentAt(1), instanceOf(Grid.class));
        Grid grid = (Grid) content.getComponentAt(1);
        verifyGrid(grid, List.of(
            Pair.of("Detail ID", "135px"),
            Pair.of("Detail Status", null),
            Pair.of("Product Family", null),
            Pair.of("Usage Batch Name", "190px"),
            Pair.of("RRO Account #", null),
            Pair.of("RRO Name", "165px"),
            Pair.of("RH Account #", null),
            Pair.of("RH Name", "360px"),
            Pair.of("Wr Wrk Inst", null),
            Pair.of("System Title", "360px"),
            Pair.of("Reported Standard Number", "260px"),
            Pair.of("Standard Number", "185px"),
            Pair.of("Standard Number Type", "230px"),
            Pair.of("Fiscal Year", null),
            Pair.of("Payment Date", null),
            Pair.of("Reported Title", "350px"),
            Pair.of("Article", "185px"),
            Pair.of("Publisher", "140px"),
            Pair.of("Pub Date", "112px"),
            Pair.of("Number of Copies", null),
            Pair.of("Reported Value", "170px"),
            Pair.of("Gross Amt in USD", "175px"),
            Pair.of("Batch Amt in USD", "175px"),
            Pair.of("Market", "135px"),
            Pair.of("Market Period From", "205px"),
            Pair.of("Market Period To", "200px"),
            Pair.of("Author", "300px"),
            Pair.of("Comment", "200px")
        ));
        verifyButtonsLayout(UiTestHelper.getFooterComponent(window, 2), true, "Ok", "Cancel");
    }

    @Test
    public void testGridValues() {
        //TODO: {vaadin23} will implement later
    }

    @Test
    public void testOnOkButtonClick() {
        IFasScenariosController controller = createMock(IFasScenariosController.class);
        var window = new RefreshScenarioWindow(value -> null, value -> 0, controller);
        var buttonsLayout = (HorizontalLayout) UiTestHelper.getFooterComponent(window, 2);
        var okButton = (Button) buttonsLayout.getComponentAt(0);
        expect(controller.getInvalidRightsholders()).andReturn(List.of()).once();
        controller.refreshScenario();
        expectLastCall().once();
        replay(controller);
        okButton.click();
        verify(controller);
    }

    @Test
    public void testOnOkButtonClickInvalidRightsholders() {
        IFasScenariosController controller = createMock(IFasScenariosController.class);
        var window = new RefreshScenarioWindow(value -> null, value -> 0, controller);
        var buttonsLayout = (HorizontalLayout) UiTestHelper.getFooterComponent(window, 2);
        var okButton = (Button) buttonsLayout.getComponentAt(0);
        mockStatic(Windows.class);
        expect(controller.getInvalidRightsholders()).andReturn(List.of(100000000L)).once();
        Windows.showNotificationWindow("Scenario cannot be refreshed. The following rightsholder(s) are absent in " +
            "PRM: <i><b>[100000000]</b></i>");
        expectLastCall().once();
        replay(controller, Windows.class);
        okButton.click();
        verify(controller, Windows.class);
    }
}
