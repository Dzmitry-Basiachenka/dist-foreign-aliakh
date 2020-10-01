package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

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
 * Verifies {@link SalUsageMediator}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 10/01/2020
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class SalUsageMediatorTest {

    private MenuBar usageBatchMenuBar;
    private MenuBar fundPoolMenuBar;
    private MenuBar.MenuItem loadItemBankMenuItem;
    private MenuBar.MenuItem loadUsageDataMenuItem;
    private MenuBar.MenuItem loadFundPoolMenuItem;
    private Button addToScenarioButton;
    private SalUsageMediator mediator;

    @Before
    public void setUp() {
        usageBatchMenuBar = new MenuBar();
        fundPoolMenuBar = new MenuBar();
        loadItemBankMenuItem = usageBatchMenuBar.new MenuItem(StringUtils.EMPTY, null, null);
        loadUsageDataMenuItem = usageBatchMenuBar.new MenuItem(StringUtils.EMPTY, null, null);
        loadFundPoolMenuItem = fundPoolMenuBar.new MenuItem(StringUtils.EMPTY, null, null);
        addToScenarioButton = new Button();
        mediator = new SalUsageMediator();
        mediator.setLoadItemBankMenuItem(loadItemBankMenuItem);
        mediator.setLoadUsageDataMenuItem(loadUsageDataMenuItem);
        mediator.setLoadFundPoolMenuItem(loadFundPoolMenuItem);
        mediator.setAddToScenarioButton(addToScenarioButton);
    }

    @Test
    public void testApplyPermissionsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(usageBatchMenuBar.isVisible());
        assertFalse(loadItemBankMenuItem.isVisible());
        assertFalse(loadUsageDataMenuItem.isVisible());
        assertTrue(fundPoolMenuBar.isVisible());
        assertFalse(loadFundPoolMenuItem.isVisible());
        assertFalse(addToScenarioButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionManager() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(usageBatchMenuBar.isVisible());
        assertFalse(loadItemBankMenuItem.isVisible());
        assertFalse(loadUsageDataMenuItem.isVisible());
        assertTrue(fundPoolMenuBar.isVisible());
        assertFalse(loadFundPoolMenuItem.isVisible());
        assertFalse(addToScenarioButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionSpecialist() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(usageBatchMenuBar.isVisible());
        assertTrue(loadItemBankMenuItem.isVisible());
        assertTrue(loadUsageDataMenuItem.isVisible());
        assertTrue(fundPoolMenuBar.isVisible());
        assertTrue(loadFundPoolMenuItem.isVisible());
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
        expect(SecurityUtils.hasPermission("FDA_LOAD_FUND_POOL")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_CREATE_EDIT_SCENARIO")).andReturn(true).anyTimes();
    }
}
