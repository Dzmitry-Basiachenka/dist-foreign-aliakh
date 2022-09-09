package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.reset;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IReconcileRightsholdersController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
@RunWith(PowerMockRunner.class)
@PrepareForTest(JavaScript.class)
public class RightsholderDiscrepanciesWindowTest {

    private final List<RightsholderDiscrepancy> rightsholderDiscrepancies =
        loadExpectedRightsholderDiscrepancy("rightsholder_discrepancy_123456789.json");
    private IReconcileRightsholdersController controller;
    private RightsholderDiscrepanciesWindow window;

    @Before
    public void setUp() throws Exception {
        controller = createMock(IReconcileRightsholdersController.class);
        IFasScenariosController scenariosController = createMock(IFasScenariosController.class);
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        replay(controller, streamSource);
        window = new RightsholderDiscrepanciesWindow(controller, scenariosController);
        verify(controller, streamSource);
        reset(controller, streamSource);
    }

    @Test
    public void testConstructor() {
        assertEquals("Reconcile Rightsholders", window.getCaption());
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

    @Test
    public void testGridValues() {
        mockStatic(JavaScript.class);
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, Integer.MAX_VALUE, Collections.emptyList()))
            .andReturn(rightsholderDiscrepancies).once();
        expect(controller.getBeansCount()).andReturn(1).once();
        replay(JavaScript.class, controller);
        Grid<?> grid = (Grid<?>) ((VerticalLayout) window.getContent()).getComponent(0);
        Object[][] expectedCells = {
            {1000009522L, "Zoological Society of Pakistan [T]", 2000070936L, "Frances Lincoln Ltd", 123456789L,
                "504 absolutely essential words"}
        };
        verifyGridItems(grid, rightsholderDiscrepancies, expectedCells);
        verify(JavaScript.class, controller);
    }

    private List<RightsholderDiscrepancy> loadExpectedRightsholderDiscrepancy(String fileName) {
        try {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, new TypeReference<List<RightsholderDiscrepancy>>() {
            });
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
