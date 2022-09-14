package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;

/**
 * Verifies functionality on {@link NtsExcludeRightsholderWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/04/20
 *
 * @author Anton Azarenka
 */
public class NtsExcludeRightsholderWidgetTest {

    private final List<RightsholderPayeePair> rightsholderPayeePairs = Arrays.asList(
        buildRightsholderPayeePair(
            buildRightsholder(1000033963L, "Alfred R. Lindesmith"),
            buildRightsholder(2000148821L, "ABR Company, Ltd")),
        buildRightsholderPayeePair(
            buildRightsholder(7000425474L, "American Dialect Society"),
            buildRightsholder(2000196395L, "Advance Central Services")));
    private final NtsExcludeRightsholderWidget widget = new NtsExcludeRightsholderWidget();

    @Before
    public void setUp() {
        NtsExcludeRightsholderController controller = createMock(NtsExcludeRightsholderController.class);
        expect(controller.getRightsholderPayeePairs()).andReturn(rightsholderPayeePairs).once();
        replay(controller);
        Whitebox.setInternalState(widget, "controller", controller);
        widget.init();
        verify(controller);
    }

    @Test
    public void testStructure() {
        UiTestHelper.verifyWindow(widget, "Exclude Details By Rightsholder", 800, 500, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(3, content.getComponentCount());
        Grid grid = (Grid) content.getComponent(1);
        verifyGrid(grid, Arrays.asList(
            Triple.of("RH Account #", -1.0, -1),
            Triple.of("RH Name", -1.0, -1),
            Triple.of("Payee Account #", -1.0, -1),
            Triple.of("Payee Name", -1.0, -1)
        ));
        verifyButtonsLayout(content.getComponent(2), "Exclude Details", "Clear", "Close");
    }

    @Test
    public void testGridValues() {
        Grid<?> grid = (Grid<?>) ((VerticalLayout) widget.getContent()).getComponent(1);
        Object[][] expectedCells = {
            {1000033963L, "Alfred R. Lindesmith", 2000148821L, "ABR Company, Ltd"},
            {7000425474L, "American Dialect Society", 2000196395L, "Advance Central Services"},
        };
        verifyGridItems(grid, rightsholderPayeePairs, expectedCells);
    }

    private RightsholderPayeePair buildRightsholderPayeePair(Rightsholder rightsholder, Rightsholder payee) {
        RightsholderPayeePair pair = new RightsholderPayeePair();
        pair.setPayee(payee);
        pair.setRightsholder(rightsholder);
        return pair;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
