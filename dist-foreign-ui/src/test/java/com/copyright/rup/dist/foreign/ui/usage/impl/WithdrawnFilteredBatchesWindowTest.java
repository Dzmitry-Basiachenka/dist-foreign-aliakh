package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link WithdrawnFilteredBatchesWindow}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/29/2019
 *
 * @author Aliaksandr Liakh
 */
public class WithdrawnFilteredBatchesWindowTest {

    private static final String USAGE_BATCH_ID = "2358deb3-caa3-4c4e-85cd-c353fcc8e6b9";
    private static final String USAGE_BATCH_NAME = "Copibec 25May18";
    private static final BigDecimal USAGE_BATCH_GROSS_AMOUNT = BigDecimal.ONE;

    @Test
    public void testConstructor() {
        IUsagesController controller = createMock(IUsagesController.class);
        List<UsageBatch> batches = Collections.singletonList(buildUsageBatch());
        expect(controller.getWithdrawnBatchesStreamSource(batches, USAGE_BATCH_GROSS_AMOUNT))
            .andReturn(createMock(IStreamSource.class)).once();
        replay(controller);
        WithdrawnFilteredBatchesWindow window = new WithdrawnFilteredBatchesWindow(controller, batches);
        verify(controller);
        assertEquals("Filtered batches", window.getCaption());
        verifySize(window, Unit.PIXELS, 450, Unit.PIXELS, 400);
        assertEquals("batches-filter-window", window.getStyleName());
        verifyRootLayout(window.getContent());
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyGrid(verticalLayout.getComponent(0));
        verifyButtonsLayout(verticalLayout.getComponent(1));
    }

    private void verifyGrid(Component component) {
        assertTrue(component instanceof Grid);
        Grid grid = (Grid) component;
        assertNull(grid.getCaption());
        verifySize(grid, Unit.PERCENTAGE, 100, Unit.PERCENTAGE, 100);
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Usage Batch Name", "NTS Withdrawn Amount"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        assertEquals(-1.0, columns.get(0).getWidth(), 0);
        assertEquals(200, columns.get(1).getWidth(), 0);
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        Button exportButton = verifyButton(layout.getComponent(0), "Export");
        Button continueButton = verifyButton(layout.getComponent(1), "Continue");
        Button closeButton = verifyButton(layout.getComponent(2), "Close");
        assertEquals(0, exportButton.getListeners(ClickEvent.class).size());
        assertEquals(0, continueButton.getListeners(ClickEvent.class).size());
        assertEquals(1, closeButton.getListeners(ClickEvent.class).size());
    }

    private Button verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
        return (Button) component;
    }

    private void verifySize(Component component, Unit widthUnit, float width, Unit heightUnit, float height) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(heightUnit, component.getHeightUnits());
        assertEquals(widthUnit, component.getWidthUnits());
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(USAGE_BATCH_ID);
        usageBatch.setName(USAGE_BATCH_NAME);
        usageBatch.setGrossAmount(USAGE_BATCH_GROSS_AMOUNT);
        return usageBatch;
    }
}
