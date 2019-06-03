package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.scenario.api.IReconcileRightsholdersController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;

import com.vaadin.server.Extension;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link RightsholderDiscrepanciesWindow}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/25/18
 *
 * @author Ihar Suvorau
 */
public class RightsholderDiscrepanciesWindowTest {

    @Test
    public void testConstructor() {
        IReconcileRightsholdersController reconcileRightsholdersController =
            createMock(IReconcileRightsholdersController.class);
        IScenariosController scenariosController = createMock(IScenariosController.class);
        replay(reconcileRightsholdersController);
        RightsholderDiscrepanciesWindow window =
            new RightsholderDiscrepanciesWindow(reconcileRightsholdersController, scenariosController);
        assertEquals("Reconcile Rightsholders", window.getCaption());
        verify(reconcileRightsholdersController);
        assertEquals(900, window.getWidth(), 0);
        assertEquals(530, window.getHeight(), 0);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(2, content.getComponentCount());
        verifyGrid(content.getComponent(0));
        verifyButtonsLayout(content.getComponent(1));
    }

    private void verifyGrid(Component component) {
        assertTrue(component instanceof Grid);
        Grid grid = (Grid) component;
        verifySize(grid);
        assertEquals("rightsholder-discrepancies-grid", grid.getId());
        List<Column> columns = grid.getColumns();
        assertEquals(
            Arrays.asList("RH Account #", "RH Name", "New RH Account #", "New RH Name", "Wr Wrk Inst", "Title"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }


    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals("rightsholder-discrepancies-buttons-layout", layout.getId());
        assertEquals(3, layout.getComponentCount());
        Button exportButton = verifyButton(layout.getComponent(0), "Export", 0);
        Collection<Extension> extensions = exportButton.getExtensions();
        assertTrue(CollectionUtils.isNotEmpty(extensions));
        assertEquals(1, extensions.size());
        assertTrue(extensions.iterator().next() instanceof OnDemandFileDownloader);
        verifyButton(layout.getComponent(1), "Approve", 1);
        verifyButton(layout.getComponent(2), "Cancel", 1);
    }

    private Button verifyButton(Component component, String caption, int listenerCount) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        assertEquals(caption, button.getId());
        assertEquals(listenerCount, button.getListeners(ClickEvent.class).size());
        return button;
    }
}
