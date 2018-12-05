package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;

import com.copyright.rup.vaadin.security.SecurityUtils;

import com.vaadin.ui.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link UsagesMediator}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/23/17
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class UsagesMediatorTest {

    private Button deleteButton;
    private Button loadUsageBatchButton;
    private Button loadFundPoolButton;
    private Button loadResearchedUsagesButton;
    private Button addToScenarioButton;
    private Button sendForResearchButton;
    private UsagesMediator mediator;

    @Before
    public void setUp() {
        deleteButton = new Button();
        loadUsageBatchButton = new Button();
        loadFundPoolButton = new Button();
        loadResearchedUsagesButton = new Button();
        addToScenarioButton = new Button();
        sendForResearchButton = new Button();
        mediator = new UsagesMediator();
        mediator.setLoadUsageBatchButton(loadUsageBatchButton);
        mediator.setLoadFundPoolButton(loadFundPoolButton);
        mediator.setLoadResearchedUsagesButton(loadResearchedUsagesButton);
        mediator.setDeleteUsageButton(deleteButton);
        mediator.setAddToScenarioButton(addToScenarioButton);
        mediator.setSendForResearchButton(sendForResearchButton);
    }

    @Test
    public void testApplyPermissionsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(deleteButton.isVisible());
        assertFalse(loadUsageBatchButton.isVisible());
        assertFalse(loadFundPoolButton.isVisible());
        assertFalse(loadResearchedUsagesButton.isVisible());
        assertFalse(addToScenarioButton.isVisible());
        assertFalse(sendForResearchButton.isVisible());
    }

    @Test
    public void testApplyPermissionsDistributionManager() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(deleteButton.isVisible());
        assertFalse(loadUsageBatchButton.isVisible());
        assertFalse(loadFundPoolButton.isVisible());
        assertFalse(loadResearchedUsagesButton.isVisible());
        assertFalse(addToScenarioButton.isVisible());
        assertFalse(sendForResearchButton.isVisible());
    }

    @Test
    public void testApplyPermissionsDistributionSpecialist() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(deleteButton.isVisible());
        assertTrue(loadUsageBatchButton.isVisible());
        assertTrue(loadFundPoolButton.isVisible());
        assertTrue(loadResearchedUsagesButton.isVisible());
        assertTrue(addToScenarioButton.isVisible());
        assertTrue(sendForResearchButton.isVisible());
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
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_ACCESS_APPLICATION")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_DELETE_USAGE")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_LOAD_USAGE")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_LOAD_FUND_POOL")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_LOAD_RESEARCHED_USAGE")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_CREATE_EDIT_SCENARIO")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_SEND_FOR_WORK_RESEARCH")).andReturn(true).anyTimes();
    }
}
