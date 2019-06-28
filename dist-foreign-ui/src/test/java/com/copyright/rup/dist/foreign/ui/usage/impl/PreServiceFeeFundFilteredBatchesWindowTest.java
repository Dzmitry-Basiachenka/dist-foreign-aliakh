package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterCell;
import com.vaadin.ui.components.grid.FooterRow;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Verifies {@link PreServiceFeeFundFilteredBatchesWindow}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/29/2019
 *
 * @author Aliaksandr Liakh
 */
public class PreServiceFeeFundFilteredBatchesWindowTest {

    private static final String USAGE_BATCH_ID = "2358deb3-caa3-4c4e-85cd-c353fcc8e6b9";
    private static final String USAGE_BATCH_NAME = "Copibec 25May18";
    private static final BigDecimal USAGE_BATCH_GROSS_AMOUNT = BigDecimal.ONE;

    @Test
    public void testConstructor() {
        IUsagesController controller = createMock(IUsagesController.class);
        List<UsageBatch> batches = Collections.singletonList(buildUsageBatch());
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(controller.getPreServiceFeeFundBatchesStreamSource(batches, USAGE_BATCH_GROSS_AMOUNT))
            .andReturn(streamSource).once();
        replay(controller, streamSource);
        PreServiceFeeFundFilteredBatchesWindow
            window = new PreServiceFeeFundFilteredBatchesWindow(controller, batches,
            createMock(PreServiceFeeFundBatchesFilterWindow.class));
        verify(controller, streamSource);
        assertEquals("Filtered batches", window.getCaption());
        verifySize(window, Unit.PIXELS, 700, Unit.PIXELS, 400);
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
        assertEquals(Arrays.asList("Usage Batch Name", "Gross NTS Withdrawn Amount"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        assertEquals(-1.0, columns.get(0).getWidth(), 0);
        assertEquals(200, columns.get(1).getWidth(), 0);
        FooterRow footerRow = grid.getFooterRow(0);
        assertNotNull(footerRow);
        FooterCell totalCell = footerRow.getCell("name");
        assertNotNull(totalCell);
        assertEquals("Total", totalCell.getText());
        FooterCell amountCell = footerRow.getCell("grossAmount");
        assertNotNull(amountCell);
        assertEquals("1.00", amountCell.getText());
        assertEquals("v-align-right", amountCell.getStyleName());
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        Button exportButton = verifyButton(layout.getComponent(0), "Export");
        Button continueButton = verifyButton(layout.getComponent(1), "Continue");
        Button cancelButton = verifyButton(layout.getComponent(2), "Cancel");
        assertEquals(0, exportButton.getListeners(ClickEvent.class).size());
        assertEquals(1, continueButton.getListeners(ClickEvent.class).size());
        assertEquals(1, cancelButton.getListeners(ClickEvent.class).size());
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
