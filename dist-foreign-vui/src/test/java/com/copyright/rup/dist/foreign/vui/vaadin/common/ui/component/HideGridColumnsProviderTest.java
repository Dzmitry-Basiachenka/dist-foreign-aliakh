package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.HideGridColumnsProvider.HideColumnsContextMenu;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Verify {@link HideGridColumnsProvider}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 05/17/2023
 *
 * @author Anton Azarenka
 */
public class HideGridColumnsProviderTest {

    private static final String ID = "Id";
    private static final String NAME = "Name";

    private Button menuButton;
    private Grid<TestDomainObject> grid;
    private HideGridColumnsProvider<TestDomainObject> hideGridColumnsProvider;

    @Before
    public void setUp() {
        grid = new Grid<>();
        grid.addColumn(TestDomainObject::getId).setHeader(ID);
        grid.addColumn(TestDomainObject::getName).setHeader(NAME);
    }

    @Test
    public void testAddAllColumns() {
        hideGridColumnsProvider = new HideGridColumnsProvider<>(grid);
        menuButton = hideGridColumnsProvider.getMenuButton();
        assertNotNull(menuButton);
        assertEquals("Hide/Unhide", menuButton.getTooltip().getText());
        HideColumnsContextMenu<TestDomainObject> contextMenu = hideGridColumnsProvider.getContextMenu();
        List<MenuItem> menuItems = contextMenu.getItems();
        assertEquals(2, menuItems.size());
        assertEquals(ID, menuItems.get(0).getText());
        assertEquals(NAME, menuItems.get(1).getText());
    }

    @Test
    public void testHideColumn() {
        hideGridColumnsProvider = new HideGridColumnsProvider<>();
        menuButton = hideGridColumnsProvider.getMenuButton();
        assertNotNull(menuButton);
        Column<TestDomainObject> testDomainObjectColumn = grid.getColumns().get(0);
        hideGridColumnsProvider.hideColumn(testDomainObjectColumn);
        HideColumnsContextMenu<TestDomainObject> contextMenu = hideGridColumnsProvider.getContextMenu();
        List<MenuItem> menuItems = contextMenu.getItems();
        assertEquals(1, menuItems.size());
        assertEquals(ID, menuItems.get(0).getText());
    }

    @Test
    public void testHideColumnsWithExcluded() {
        hideGridColumnsProvider = new HideGridColumnsProvider<>();
        menuButton = hideGridColumnsProvider.getMenuButton();
        assertNotNull(menuButton);
        hideGridColumnsProvider.hideColumns(grid.getColumns(), ID);
        HideColumnsContextMenu<TestDomainObject> contextMenu = hideGridColumnsProvider.getContextMenu();
        List<MenuItem> menuItems = contextMenu.getItems();
        assertEquals(1, menuItems.size());
        assertEquals(NAME, menuItems.get(0).getText());
    }

    private static class TestDomainObject {

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
