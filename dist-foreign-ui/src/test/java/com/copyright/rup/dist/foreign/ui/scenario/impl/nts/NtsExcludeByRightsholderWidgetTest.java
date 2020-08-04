package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies functionality on {@link NtsExcludeByRightsholderWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/04/20
 *
 * @author Anton Azarenka
 */
public class NtsExcludeByRightsholderWidgetTest {

    private final NtsExcludeByRightsholderWidget widget = new NtsExcludeByRightsholderWidget();

    @Before
    public void setUp() {
        NtsExcludeByRightsholderController controller = createMock(NtsExcludeByRightsholderController.class);
        expect(controller.getRightsholderPayeePair())
            .andReturn(Arrays.asList(
                buildRightsholderPayeePair(
                    buildRightsholder(1000033963L, "Alfred R. Lindesmith"),
                    buildRightsholder(2000148821L, "ABR Company, Ltd")),
                buildRightsholderPayeePair(
                    buildRightsholder(7000425474L, "American Dialect Society"),
                    buildRightsholder(2000196395L, "Advance Central Services"))))
            .once();
        replay(controller);
        Whitebox.setInternalState(widget, "controller", controller);
        widget.init();
        verify(controller);
    }

    @Test
    public void testStructure() {
        assertEquals("Exclude Details By Rightsholder", widget.getCaption());
        assertEquals(500, widget.getHeight(), 0);
        assertEquals(800, widget.getWidth(), 0);
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(3, content.getComponentCount());
        verifyGrid(content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2));
    }

    private void verifyGrid(Component component) {
        assertEquals(Grid.class, component.getClass());
        Grid grid = (Grid) component;
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("RH Account #", "RH Name", "Payee Account #", "Payee Name"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(3, horizontalLayout.getComponentCount());
        Button confirmButton = (Button) horizontalLayout.getComponent(0);
        assertEquals("Exclude Details", confirmButton.getCaption());
        Button clearButton = (Button) horizontalLayout.getComponent(1);
        assertEquals("Clear", clearButton.getCaption());
        assertEquals("Clear", clearButton.getId());
        Button closeButton = (Button) horizontalLayout.getComponent(2);
        assertEquals("Close", closeButton.getCaption());
        assertEquals("Close", closeButton.getId());
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(new MarginInfo(false, false, false, false), horizontalLayout.getMargin());
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
