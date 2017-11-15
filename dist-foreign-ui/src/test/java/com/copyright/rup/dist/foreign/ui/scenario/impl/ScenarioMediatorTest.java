package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link ScenarioMediator}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/14/17
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class ScenarioMediatorTest {

    private static final String PERMISSION_NAME = "FDA_EXCLUDE_FROM_SCENARIO";

    private ScenarioMediator mediator;
    private Button excludeButton = new Button();
    private Button exportButton = new Button();
    private SearchWidget searchWidget = new SearchWidget(() -> {/*stub*/});
    private Table table = new Table();
    private VerticalLayout emptyUsagesLayout = new VerticalLayout();

    @Before
    public void setUp() {
        mockStatic(SecurityUtils.class);
        mediator = new ScenarioMediator();
        mediator.setExcludeButton(excludeButton);
        mediator.setExportButton(exportButton);
        mediator.setSearchWidget(searchWidget);
        mediator.setRightsholdersTable(table);
        mediator.setEmptyUsagesLayout(emptyUsagesLayout);
    }

    @Test
    public void testApplyPermissions() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(true);
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        verify(SecurityUtils.class);
        assertTrue(excludeButton.isVisible());
        assertTrue(exportButton.isVisible());
        assertTrue(table.isVisible());
        assertTrue(emptyUsagesLayout.isVisible());
        assertTrue(searchWidget.isVisible());
    }

    @Test
    public void testApplyPermissionsNoExcludePermission() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(false);
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        verify(SecurityUtils.class);
        assertFalse(excludeButton.isVisible());
        assertTrue(exportButton.isVisible());
        assertTrue(table.isVisible());
        assertTrue(emptyUsagesLayout.isVisible());
        assertTrue(searchWidget.isVisible());
    }

    @Test
    public void testOnScenarioUpdatedNotEmptyScenario() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(true);
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        mediator.onScenarioUpdated(false);
        verify(SecurityUtils.class);
        assertTrue(excludeButton.isEnabled());
        assertTrue(exportButton.isEnabled());
        assertTrue(table.isVisible());
        assertFalse(emptyUsagesLayout.isVisible());
        assertTrue(searchWidget.isVisible());
    }

    @Test
    public void testOnScenarioUpdatedEmptyScenario() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(true);
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        mediator.onScenarioUpdated(true);
        verify(SecurityUtils.class);
        assertFalse(excludeButton.isEnabled());
        assertFalse(exportButton.isEnabled());
        assertFalse(table.isVisible());
        assertTrue(emptyUsagesLayout.isVisible());
        assertFalse(searchWidget.isVisible());
    }

    @Test
    public void testOnScenarioUpdatedEmptyScenarioNoExcludePermissions() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(false);
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        mediator.onScenarioUpdated(true);
        verify(SecurityUtils.class);
        assertFalse(excludeButton.isVisible());
        assertFalse(excludeButton.isEnabled());
        assertFalse(exportButton.isEnabled());
        assertFalse(table.isVisible());
        assertTrue(emptyUsagesLayout.isVisible());
        assertFalse(searchWidget.isVisible());
    }
}
