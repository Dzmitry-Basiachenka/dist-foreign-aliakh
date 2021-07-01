package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.powermock.api.easymock.PowerMock.mockStatic;

import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link ViewUdmBatchWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/30/21
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class, ViewUdmBatchWindow.class})
public class ViewUdmBatchWindowTest {

    private static final String UNCHECKED = "unchecked";
    private ViewUdmBatchWindow viewUdmBatchWindow;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        viewUdmBatchWindow = new ViewUdmBatchWindow();
    }

    @Test
    public void testStructure() {
        assertEquals("View UDM Usage Batch", viewUdmBatchWindow.getCaption());
        verifySize(viewUdmBatchWindow);
        VerticalLayout content = (VerticalLayout) viewUdmBatchWindow.getContent();
        assertEquals(3, content.getComponentCount());
        assertEquals(SearchWidget.class, content.getComponent(0).getClass());
        Component component = content.getComponent(1);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component);
        assertEquals(1, content.getExpandRatio(component), 0);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        assertEquals(2, buttonsLayout.getComponentCount());
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        Button closeButton = (Button) buttonsLayout.getComponent(1);
        assertEquals("Delete", deleteButton.getCaption());
        assertEquals("Close", closeButton.getCaption());
    }

    private void verifySize(Component component) {
        assertEquals(1000, component.getWidth(), 0);
        assertEquals(550, component.getHeight(), 0);
        assertEquals(Sizeable.Unit.PIXELS, component.getHeightUnits());
        assertEquals(Sizeable.Unit.PIXELS, component.getWidthUnits());
    }

    @SuppressWarnings(UNCHECKED)
    private void verifyGrid(Grid grid) {
        assertNull(grid.getCaption());
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Usage Batch Name", "Period", "Usage Origin", "Channel",
            "Create User", "Create Date"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
        assertEquals(Arrays.asList(-1.0, 180.0, 180.0, 120.0, 170.0, 170.0),
            columns.stream().map(Grid.Column::getWidth).collect(Collectors.toList()));
    }
}
