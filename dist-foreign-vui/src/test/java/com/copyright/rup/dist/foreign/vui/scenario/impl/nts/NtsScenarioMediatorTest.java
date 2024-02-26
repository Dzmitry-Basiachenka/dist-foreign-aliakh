package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.vui.vaadin.common.security.SecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link NtsScenarioMediator}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 29/07/2020
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class NtsScenarioMediatorTest {

    private static final String PERMISSION_NAME = "FDA_EXCLUDE_FROM_SCENARIO";
    private final Button excludeByRhButton = new Button();
    private final OnDemandFileDownloader exportDetailsFileDownloader = new OnDemandFileDownloader();
    private final OnDemandFileDownloader exportScenarioFileDownloader = new OnDemandFileDownloader();
    private final SearchWidget searchWidget = new SearchWidget(() -> {/*stub*/});
    private final Grid grid = new Grid();
    private final VerticalLayout emptyUsagesLayout = new VerticalLayout();
    private final Button menuButton = new Button();
    private NtsScenarioMediator mediator;
    private Scenario scenario;

    @Before
    public void setUp() {
        mockStatic(SecurityUtils.class);
        scenario = new Scenario();
        scenario.setProductFamily("NTS");
        mediator = new NtsScenarioMediator();
        mediator.setExcludeRhButton(excludeByRhButton);
        mediator.setExportDetailsFileDownloader(exportDetailsFileDownloader);
        mediator.setExportScenarioFileDownloader(exportScenarioFileDownloader);
        mediator.setSearchWidget(searchWidget);
        mediator.setRightsholderGrid(grid);
        mediator.setEmptyUsagesLayout(emptyUsagesLayout);
        mediator.setMenuButton(menuButton);
    }

    @Test
    public void testApplyPermissions() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(excludeByRhButton.isVisible());
        assertTrue(exportDetailsFileDownloader.isVisible());
        assertTrue(exportScenarioFileDownloader.isVisible());
        assertTrue(grid.isVisible());
        assertTrue(emptyUsagesLayout.isVisible());
        assertTrue(searchWidget.isVisible());
        assertTrue(menuButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsNoExcludePermission() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(false).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(excludeByRhButton.isVisible());
        assertTrue(exportDetailsFileDownloader.isVisible());
        assertTrue(exportScenarioFileDownloader.isVisible());
        assertTrue(grid.isVisible());
        assertTrue(emptyUsagesLayout.isVisible());
        assertTrue(searchWidget.isVisible());
        assertTrue(menuButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testOnScenarioUpdatedNotEmptyScenario() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        mediator.onScenarioUpdated(false, scenario);
        assertTrue(excludeByRhButton.isEnabled());
        assertTrue(exportDetailsFileDownloader.isEnabled());
        assertTrue(exportScenarioFileDownloader.isEnabled());
        assertTrue(grid.isVisible());
        assertFalse(emptyUsagesLayout.isVisible());
        assertTrue(searchWidget.isVisible());
        assertTrue(menuButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testOnScenarioUpdatedNotEmptyScenarioSubmitStatus() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        scenario.setStatus(ScenarioStatusEnum.SUBMITTED);
        mediator.onScenarioUpdated(false, scenario);
        assertFalse(excludeByRhButton.isEnabled());
        assertTrue(exportDetailsFileDownloader.isEnabled());
        assertTrue(exportScenarioFileDownloader.isEnabled());
        assertTrue(grid.isVisible());
        assertFalse(emptyUsagesLayout.isVisible());
        assertTrue(searchWidget.isVisible());
        assertTrue(menuButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testOnScenarioUpdatedEmptyScenario() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        mediator.onScenarioUpdated(true, scenario);
        assertFalse(excludeByRhButton.isEnabled());
        assertFalse(exportDetailsFileDownloader.isEnabled());
        assertFalse(exportScenarioFileDownloader.isEnabled());
        assertFalse(grid.isVisible());
        assertTrue(emptyUsagesLayout.isVisible());
        assertFalse(searchWidget.isVisible());
        assertFalse(menuButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testOnScenarioUpdatedEmptyScenarioNoExcludePermissions() {
        expect(SecurityUtils.hasPermission(PERMISSION_NAME)).andReturn(false).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        mediator.onScenarioUpdated(true, scenario);
        assertFalse(excludeByRhButton.isVisible());
        assertFalse(excludeByRhButton.isEnabled());
        assertFalse(exportDetailsFileDownloader.isEnabled());
        assertFalse(exportScenarioFileDownloader.isEnabled());
        assertFalse(grid.isVisible());
        assertTrue(emptyUsagesLayout.isVisible());
        assertFalse(searchWidget.isVisible());
        assertFalse(menuButton.isVisible());
        verify(SecurityUtils.class);
    }
}
