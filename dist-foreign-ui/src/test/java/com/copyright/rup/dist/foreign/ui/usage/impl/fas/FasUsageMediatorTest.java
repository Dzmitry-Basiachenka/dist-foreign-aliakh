package com.copyright.rup.dist.foreign.ui.usage.impl.fas;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.security.SecurityUtils;

import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link FasUsageMediator}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 12/05/19
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class FasUsageMediatorTest {

    private MenuBar usageBatchMenuBar;
    private MenuBar.MenuItem loadUsageBatchMenuItem;
    private Button sendForResearchButton;
    private Button loadResearchedUsagesButton;
    private Button updateUsagesButton;
    private Button addToScenarioButton;
    private FasUsageMediator mediator;

    @Before
    public void setUp() {
        usageBatchMenuBar = new MenuBar();
        loadUsageBatchMenuItem = usageBatchMenuBar.new MenuItem(StringUtils.EMPTY, null, null);
        sendForResearchButton = new Button();
        loadResearchedUsagesButton = new Button();
        updateUsagesButton = new Button();
        addToScenarioButton = new Button();
        mediator = new FasUsageMediator();
        mediator.setLoadUsageBatchMenuItem(loadUsageBatchMenuItem);
        mediator.setSendForResearchButton(sendForResearchButton);
        mediator.setLoadResearchedUsagesButton(loadResearchedUsagesButton);
        mediator.setUpdateUsagesButton(updateUsagesButton);
        mediator.setAddToScenarioButton(addToScenarioButton);
    }

    @Test
    public void testApplyPermissionsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(usageBatchMenuBar.isVisible());
        assertFalse(loadUsageBatchMenuItem.isVisible());
        assertFalse(sendForResearchButton.isVisible());
        assertFalse(loadResearchedUsagesButton.isVisible());
        assertFalse(updateUsagesButton.isVisible());
        assertFalse(addToScenarioButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionManager() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(usageBatchMenuBar.isVisible());
        assertFalse(loadUsageBatchMenuItem.isVisible());
        assertFalse(sendForResearchButton.isVisible());
        assertFalse(loadResearchedUsagesButton.isVisible());
        assertFalse(updateUsagesButton.isVisible());
        assertFalse(addToScenarioButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionSpecialist() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(usageBatchMenuBar.isVisible());
        assertTrue(loadUsageBatchMenuItem.isVisible());
        assertTrue(sendForResearchButton.isVisible());
        assertTrue(loadResearchedUsagesButton.isVisible());
        assertTrue(updateUsagesButton.isVisible());
        assertTrue(addToScenarioButton.isVisible());
        verify(SecurityUtils.class);
    }

    private void mockViewOnlyPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_ACCESS_APPLICATION")).andReturn(true).anyTimes();
    }

    private void mockManagerPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_ACCESS_APPLICATION")).andReturn(true).anyTimes();
    }

    private void mockSpecialistPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission("FDA_ACCESS_APPLICATION")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_LOAD_USAGE")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_SEND_FOR_WORK_RESEARCH")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_LOAD_RESEARCHED_USAGE")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_UPDATE_RIGHTSHOLDER")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_CREATE_EDIT_SCENARIO")).andReturn(true).anyTimes();
    }
}
