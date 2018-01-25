package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.scenario.api.IReconcileRightsholdersController;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

import java.util.Collections;

/**
 * Verifies {@link ReconcileRightsholdersWindow}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/26/18
 *
 * @author Ihar Suvorau
 */
public class ReconcileRightsholdersWindowTest {

    @Test
    public void testConstructor() {
        IReconcileRightsholdersController rightsholderDiscrepancyController =
            createMock(IReconcileRightsholdersController.class);
        expect(rightsholderDiscrepancyController.getDiscrepancies()).andReturn(Collections.emptyList()).once();
        replay(rightsholderDiscrepancyController);
        ReconcileRightsholdersWindow window = new ReconcileRightsholdersWindow(rightsholderDiscrepancyController);
        verify(rightsholderDiscrepancyController);
        assertEquals(830, window.getWidth(), 0);
        assertEquals(500, window.getHeight(), 0);
        assertTrue(window.getContent() instanceof VerticalLayout);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(2, content.getComponentCount());
        verifyTabSheet(content.getComponent(0));
        verifyButtonsLayout(content.getComponent(1));
    }

    private void verifyTabSheet(Component component) {
        assertTrue(component instanceof TabSheet);
        TabSheet tabSheet = (TabSheet) component;
        assertEquals(100, tabSheet.getHeight(), 0);
        assertEquals(100, tabSheet.getWidth(), 0);
        assertEquals(Sizeable.Unit.PERCENTAGE, tabSheet.getHeightUnits());
        assertEquals(Sizeable.Unit.PERCENTAGE, tabSheet.getWidthUnits());
        assertEquals(2, tabSheet.getComponentCount());
        TabSheet.Tab rightsholdersTab = tabSheet.getTab(0);
        assertEquals("Rightsholders Updates", rightsholdersTab.getCaption());
        assertTrue(rightsholdersTab.getComponent() instanceof RightsholderDiscrepanciesWindow);
        TabSheet.Tab serviceFeeTab = tabSheet.getTab(1);
        assertEquals("Service Fee Updates", serviceFeeTab.getCaption());
        assertTrue(serviceFeeTab.getComponent() instanceof ServiceFeeDiscrepanciesWindow);
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals("rightsholder-discrepancies-buttons-layout", layout.getId());
        assertEquals(2, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Approve", 1);
        verifyButton(layout.getComponent(1), "Cancel", 2);
    }

    private void verifyButton(Component component, String caption, int listenerCount) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        assertEquals(caption, button.getId());
        assertEquals(listenerCount, button.getListeners(ClickEvent.class).size());
    }
}
