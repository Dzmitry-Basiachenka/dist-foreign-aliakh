package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.IWorkClassificationController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Verifies {@link WorkClassificationWindow}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/12/2019
 *
 * @author Ihar Suvorau
 */
public class WorkClassificationWindowTest {

    private final Set<String> batchesIds = Collections.singleton(RupPersistUtils.generateUuid());
    private WorkClassificationWindow window;

    @Before
    public void setUp() {
        IWorkClassificationController workClassificationController = createMock(IWorkClassificationController.class);
        replay(workClassificationController);
        window = new WorkClassificationWindow(batchesIds, workClassificationController);
        verify(workClassificationController);
    }

    @Test
    public void testComponentStructure() {
        assertEquals("Works Classification", window.getCaption());
        verifySize(window, 1000, Unit.PIXELS, 530, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(new MarginInfo(true), content.getMargin());
        assertTrue(content.isSpacing());
        verifySize(content, 100, Unit.PERCENTAGE, 100, Unit.PERCENTAGE);
        assertEquals(3, content.getComponentCount());
        assertEquals(SearchWidget.class, content.getComponent(0).getClass());
        Component component = content.getComponent(1);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component);
        assertEquals(1, content.getExpandRatio(component), 0);
        component = content.getComponent(2);
        assertEquals(HorizontalLayout.class, component.getClass());
        verifyButtonsLayout((HorizontalLayout) component);
        assertEquals(Alignment.BOTTOM_RIGHT, content.getComponentAlignment(component));
    }

    private void verifySize(Component component, float width, Unit widthUnit, float height, Unit heightUnit) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(heightUnit, component.getHeightUnits());
        assertEquals(widthUnit, component.getWidthUnits());
    }

    @SuppressWarnings("unchecked")
    private void verifyGrid(Grid grid) {
        assertNull(grid.getCaption());
        verifySize(grid, 100, Unit.PERCENTAGE, 100, Unit.PERCENTAGE);
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Wr Wrk Inst", "System Title", "Classification", "Standard Number",
            "Standard Number Type", "RH Account #", "RH Name"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        assertEquals(100, columns.get(0).getWidth(), 0);
        assertEquals(285, columns.get(1).getWidth(), 0);
        assertEquals(110, columns.get(2).getWidth(), 0);
        assertEquals(140, columns.get(3).getWidth(), 0);
        assertEquals(155, columns.get(4).getWidth(), 0);
        assertEquals(100, columns.get(5).getWidth(), 0);
        assertEquals(200, columns.get(6).getWidth(), 0);
    }

    private void verifyButtonsLayout(HorizontalLayout buttonsLayout) {
        assertTrue(buttonsLayout.isSpacing());
        assertEquals(new MarginInfo(false), buttonsLayout.getMargin());
        assertEquals(6, buttonsLayout.getComponentCount());
        assertEquals("Mark as STM", buttonsLayout.getComponent(0).getCaption());
        assertEquals("Mark as Non-STM", buttonsLayout.getComponent(1).getCaption());
        assertEquals("Mark as Belletristic", buttonsLayout.getComponent(2).getCaption());
        assertEquals("Delete Classification", buttonsLayout.getComponent(3).getCaption());
        assertEquals("Clear", buttonsLayout.getComponent(4).getCaption());
        assertEquals("Close", buttonsLayout.getComponent(5).getCaption());
    }
}
