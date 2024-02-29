package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterComponent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifySearchWidget;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Verifies functionality on {@link NtsExcludeRightsholderWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/04/2020
 *
 * @author Anton Azarenka
 */
public class NtsExcludeRightsholderWidgetTest {

    private final List<RightsholderPayeePair> rightsholderPayeePairs = List.of(
        buildRightsholderPayeePair(
            buildRightsholder(1000033963L, "Alfred R. Lindesmith"),
            buildRightsholder(2000148821L, "ABR Company, Ltd")),
        buildRightsholderPayeePair(
            buildRightsholder(7000425474L, "American Dialect Society"),
            buildRightsholder(2000196395L, "Advance Central Services")));
    private NtsExcludeRightsholderWidget widget;

    @Before
    public void setUp() {
        NtsExcludeRightsholderController controller = createMock(NtsExcludeRightsholderController.class);
        expect(controller.getRightsholderPayeePairs()).andReturn(rightsholderPayeePairs).once();
        replay(controller);
        widget = new NtsExcludeRightsholderWidget();
        widget.setController(controller);
        widget.init();
        verify(controller);
    }

    @Test
    public void testWidgetStructure() {
        verifyWindow(widget, "Exclude Details By Rightsholder", "830px", "500px", Unit.PIXELS, true);
        var content = (VerticalLayout) getDialogContent(widget);
        assertEquals(2, content.getComponentCount());
        verifySearchWidget(content.getComponentAt(0), "Enter Payee/RH Name/Account #");
        verifyGrid(content.getComponentAt(1));
        verifyButtonsLayout(getFooterComponent(widget, 1), true, "Exclude Details", "Clear", "Close");
    }

    @Test
    public void testGridValues() {
        Grid<?> grid = (Grid<?>) ((VerticalLayout) getDialogContent(widget)).getComponentAt(1);
        Object[][] expectedCells = {
            {"1000033963", "Alfred R. Lindesmith", "2000148821", "ABR Company, Ltd"},
            {"7000425474", "American Dialect Society", "2000196395", "Advance Central Services"},
        };
        verifyGridItems(grid, rightsholderPayeePairs, expectedCells);
    }

    private void verifyGrid(Component component) {
        assertNotNull(component);
        assertThat(component, instanceOf(Grid.class));
        Grid<RightsholderPayeePair> grid = (Grid<RightsholderPayeePair>) component;
        UiTestHelper.verifyGrid(grid, List.of(
            Pair.of("RH Account #", null),
            Pair.of("RH Name", null),
            Pair.of("Payee Account #", null),
            Pair.of("Payee Name", null)
        ));
        assertEquals("exclude-details-by-rightsholder-grid", grid.getClassName());
    }

    private RightsholderPayeePair buildRightsholderPayeePair(Rightsholder rightsholder, Rightsholder payee) {
        var pair = new RightsholderPayeePair();
        pair.setPayee(payee);
        pair.setRightsholder(rightsholder);
        return pair;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        var rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
