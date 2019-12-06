package com.copyright.rup.dist.foreign.ui.usage.impl;

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
 * Verifies {@link NtsUsageMediator}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 12/05/19
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class NtsUsageMediatorTest {

    private Button addToScenarioButton;
    private Button assignClassificationButton;
    private MenuBar withdrawnFundMenuBar;
    private MenuBar fundPoolMenuBar;
    private MenuBar.MenuItem loadFundPoolMenuItem;
    private NtsUsageMediator mediator;

    @Before
    public void setUp() {
        addToScenarioButton = new Button();
        assignClassificationButton = new Button();
        withdrawnFundMenuBar = new MenuBar();
        fundPoolMenuBar = new MenuBar();
        loadFundPoolMenuItem = fundPoolMenuBar.new MenuItem(StringUtils.EMPTY, null, null);
        mediator = new NtsUsageMediator();
        mediator.setLoadFundPoolMenuItem(loadFundPoolMenuItem);
        mediator.setAddToScenarioButton(addToScenarioButton);
        mediator.setAssignClassificationButton(assignClassificationButton);
        mediator.setWithdrawnFundMenuBar(withdrawnFundMenuBar);
    }

    @Test
    public void testApplyPermissionsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(fundPoolMenuBar.isVisible());
        assertFalse(loadFundPoolMenuItem.isVisible());
        assertFalse(addToScenarioButton.isVisible());
        assertFalse(assignClassificationButton.isVisible());
        assertFalse(withdrawnFundMenuBar.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionManager() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(fundPoolMenuBar.isVisible());
        assertFalse(loadFundPoolMenuItem.isVisible());
        assertFalse(addToScenarioButton.isVisible());
        assertFalse(assignClassificationButton.isVisible());
        assertFalse(withdrawnFundMenuBar.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionSpecialist() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(fundPoolMenuBar.isVisible());
        assertTrue(loadFundPoolMenuItem.isVisible());
        assertTrue(addToScenarioButton.isVisible());
        assertTrue(assignClassificationButton.isVisible());
        assertTrue(withdrawnFundMenuBar.isVisible());
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
        expect(SecurityUtils.hasPermission("FDA_LOAD_FUND_POOL")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_CREATE_EDIT_SCENARIO")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_ASSIGN_CLASSIFICATION")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_CREATE_DELETE_FUND")).andReturn(true).anyTimes();
    }
}
