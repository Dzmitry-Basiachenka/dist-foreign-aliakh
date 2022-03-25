package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IReconcileRightsholdersController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Test;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.function.Supplier;

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
        IFasScenariosController scenariosController = createMock(IFasScenariosController.class);
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(reconcileRightsholdersController.getCsvStreamSource()).andReturn(streamSource).once();
        replay(reconcileRightsholdersController, streamSource);
        RightsholderDiscrepanciesWindow window =
            new RightsholderDiscrepanciesWindow(reconcileRightsholdersController, scenariosController);
        assertEquals("Reconcile Rightsholders", window.getCaption());
        verify(reconcileRightsholdersController, streamSource);
        assertEquals(900, window.getWidth(), 0);
        assertEquals(530, window.getHeight(), 0);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(2, content.getComponentCount());
        assertTrue(content.getComponent(0) instanceof Grid);
        Grid grid = (Grid) content.getComponent(0);
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        assertEquals("rightsholder-discrepancies-grid", grid.getId());
        verifyGrid(grid, Arrays.asList(
            Triple.of("RH Account #", 115.0, -1),
            Triple.of("RH Name", -1.0, -1),
            Triple.of("New RH Account #", 140.0, -1),
            Triple.of("New RH Name", -1.0, -1),
            Triple.of("Wr Wrk Inst", 110.0, -1),
            Triple.of("Title", -1.0, -1)
        ));
        verifyButtonsLayout(content.getComponent(1), "Export", "Approve", "Cancel");
        HorizontalLayout layout = (HorizontalLayout) content.getComponent(1);
        assertEquals("rightsholder-discrepancies-buttons-layout", layout.getId());
    }
}
