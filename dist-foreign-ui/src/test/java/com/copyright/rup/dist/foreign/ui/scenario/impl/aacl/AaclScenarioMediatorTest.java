package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Scenario;
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
 * Verifies {@link AaclScenarioMediator}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/05/2020
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class AaclScenarioMediatorTest {

    private static final String PERMISSION_NAME = "FDA_EXCLUDE_FROM_SCENARIO";
    private final Button excludeByPayeeButton = new Button();
    private final Button exportDetailsButton = new Button();
    private final Button exportButton = new Button();
    private final SearchWidget searchWidget = new SearchWidget(() -> {/*stub*/});
    private final Grid grid = new Grid();
    private final VerticalLayout emptyUsagesLayout = new VerticalLayout();
    private AaclScenarioMediator mediator;
    private Scenario scenario;

    @Before
    public void setUp() {
        mockStatic(SecurityUtils.class);
        scenario = new Scenario();
        scenario.setProductFamily("FAS");
        mediator = new AaclScenarioMediator();
        mediator.setExcludeByPayeeButton(excludeByPayeeButton);
        mediator.setExportDetailsButton(exportDetailsButton);
        mediator.setExportButton(exportButton);
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
        assertTrue(excludeByPayeeButton.isVisible());
        assertTrue(exportDetailsButton.isVisible());
        assertTrue(exportButton.isVisible());
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
        assertFalse(excludeByPayeeButton.isVisible());
        assertTrue(exportDetailsButton.isVisible());
        assertTrue(exportButton.isVisible());
        assertTrue(grid.isVisible());
        assertTrue(emptyUsagesLayout.isVisible());
        assertTrue(searchWidget.isVisible());
    }

    @Test
    public void testOnScenarioUpdatedNotEmptyScenario() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        mediator.onScenarioUpdated(false, scenario);
        verify(SecurityUtils.class);
        assertTrue(excludeByPayeeButton.isEnabled());
        assertTrue(exportDetailsButton.isEnabled());
        assertTrue(exportButton.isEnabled());
        assertTrue(grid.isVisible());
        assertFalse(emptyUsagesLayout.isVisible());
        assertTrue(searchWidget.isVisible());
    }

    @Test
    public void testOnScenarioUpdatedNotEmptyScenarioSubmitStatus() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        scenario.setStatus(ScenarioStatusEnum.SUBMITTED);
        mediator.onScenarioUpdated(false, scenario);
        verify(SecurityUtils.class);
        assertFalse(excludeByPayeeButton.isEnabled());
        assertTrue(exportDetailsButton.isEnabled());
        assertTrue(exportButton.isEnabled());
        assertTrue(grid.isVisible());
        assertFalse(emptyUsagesLayout.isVisible());
        assertTrue(searchWidget.isVisible());
    }

    @Test
    public void testOnScenarioUpdatedEmptyScenario() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        mediator.onScenarioUpdated(true, scenario);
        verify(SecurityUtils.class);
        assertFalse(excludeByPayeeButton.isEnabled());
        assertFalse(exportDetailsButton.isEnabled());
        assertFalse(exportButton.isEnabled());
        assertFalse(grid.isVisible());
        assertTrue(emptyUsagesLayout.isVisible());
        assertFalse(searchWidget.isVisible());
    }

    @Test
    public void testOnScenarioUpdatedEmptyScenarioNoExcludePermissions() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(false).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        mediator.onScenarioUpdated(true, scenario);
        verify(SecurityUtils.class);
        assertFalse(excludeByPayeeButton.isVisible());
        assertFalse(excludeByPayeeButton.isEnabled());
        assertFalse(exportDetailsButton.isEnabled());
        assertFalse(exportButton.isEnabled());
        assertFalse(grid.isVisible());
        assertTrue(emptyUsagesLayout.isVisible());
        assertFalse(searchWidget.isVisible());
    }
}
