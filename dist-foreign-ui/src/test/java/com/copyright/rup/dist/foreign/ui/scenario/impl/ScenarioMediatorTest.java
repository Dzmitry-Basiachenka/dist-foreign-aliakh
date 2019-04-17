package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
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
    private final Button excludeButton = new Button();
    private final Button exportDetailsButton = new Button();
    private final Button exportScenarioButton = new Button();
    private final SearchWidget searchWidget = new SearchWidget(() -> {/*stub*/});
    private final Grid grid = new Grid();
    private final VerticalLayout emptyUsagesLayout = new VerticalLayout();
    private ScenarioMediator mediator;

    @Before
    public void setUp() {
        mockStatic(SecurityUtils.class);
        mediator = new ScenarioMediator();
        mediator.setExcludeButton(excludeButton);
        mediator.setExportDetailsButton(exportDetailsButton);
        mediator.setExportScenarioButton(exportScenarioButton);
        mediator.setSearchWidget(searchWidget);
        mediator.setRightsholderGrid(grid);
        mediator.setEmptyUsagesLayout(emptyUsagesLayout);
    }

    @Test
    public void testApplyPermissions() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        verify(SecurityUtils.class);
        assertTrue(excludeButton.isVisible());
        assertTrue(exportDetailsButton.isVisible());
        assertTrue(exportScenarioButton.isVisible());
        assertTrue(grid.isVisible());
        assertTrue(emptyUsagesLayout.isVisible());
        assertTrue(searchWidget.isVisible());
    }

    @Test
    public void testApplyPermissionsNoExcludePermission() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(false).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        verify(SecurityUtils.class);
        assertFalse(excludeButton.isVisible());
        assertTrue(exportDetailsButton.isVisible());
        assertTrue(exportScenarioButton.isVisible());
        assertTrue(grid.isVisible());
        assertTrue(emptyUsagesLayout.isVisible());
        assertTrue(searchWidget.isVisible());
    }

    @Test
    public void testOnScenarioUpdatedNotEmptyScenario() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        mediator.onScenarioUpdated(false, ScenarioStatusEnum.IN_PROGRESS);
        verify(SecurityUtils.class);
        assertTrue(excludeButton.isEnabled());
        assertTrue(exportDetailsButton.isEnabled());
        assertTrue(exportScenarioButton.isEnabled());
        assertTrue(grid.isVisible());
        assertFalse(emptyUsagesLayout.isVisible());
        assertTrue(searchWidget.isVisible());
    }

    @Test
    public void testOnScenarioUpdatedNotEmptyScenarioSubmitStatus() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        mediator.onScenarioUpdated(false, ScenarioStatusEnum.SUBMITTED);
        verify(SecurityUtils.class);
        assertFalse(excludeButton.isEnabled());
        assertTrue(exportDetailsButton.isEnabled());
        assertTrue(exportScenarioButton.isEnabled());
        assertTrue(grid.isVisible());
        assertFalse(emptyUsagesLayout.isVisible());
        assertTrue(searchWidget.isVisible());
    }

    @Test
    public void testOnScenarioUpdatedEmptyScenario() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        mediator.onScenarioUpdated(true, ScenarioStatusEnum.IN_PROGRESS);
        verify(SecurityUtils.class);
        assertFalse(excludeButton.isEnabled());
        assertFalse(exportDetailsButton.isEnabled());
        assertFalse(exportScenarioButton.isEnabled());
        assertFalse(grid.isVisible());
        assertTrue(emptyUsagesLayout.isVisible());
        assertFalse(searchWidget.isVisible());
    }

    @Test
    public void testOnScenarioUpdatedEmptyScenarioNoExcludePermissions() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(false).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        mediator.onScenarioUpdated(true, ScenarioStatusEnum.IN_PROGRESS);
        verify(SecurityUtils.class);
        assertFalse(excludeButton.isVisible());
        assertFalse(excludeButton.isEnabled());
        assertFalse(exportDetailsButton.isEnabled());
        assertFalse(exportScenarioButton.isEnabled());
        assertFalse(grid.isVisible());
        assertTrue(emptyUsagesLayout.isVisible());
        assertFalse(searchWidget.isVisible());
    }
}
