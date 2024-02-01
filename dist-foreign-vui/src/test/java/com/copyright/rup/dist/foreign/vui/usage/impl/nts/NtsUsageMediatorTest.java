package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.vui.vaadin.common.security.SecurityUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.menubar.MenuBar;

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
 * Date: 12/05/2019
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class NtsUsageMediatorTest {

    private Button addToScenarioButton;
    private Button assignClassificationButton;
    private MenuBar additionalFundsMenuBar;
    private MenuBar fundPoolMenuBar;
    private MenuItem loadFundPoolMenuItem;
    private NtsUsageMediator mediator;

    @Before
    public void setUp() {
        addToScenarioButton = new Button();
        assignClassificationButton = new Button();
        additionalFundsMenuBar = new MenuBar();
        fundPoolMenuBar = new MenuBar();
        loadFundPoolMenuItem = fundPoolMenuBar
            .addItem(StringUtils.EMPTY)
            .getSubMenu()
            .addItem(StringUtils.EMPTY, null);
        mediator = new NtsUsageMediator();
        mediator.setLoadFundPoolMenuItem(loadFundPoolMenuItem);
        mediator.setAddToScenarioButton(addToScenarioButton);
        mediator.setAssignClassificationButton(assignClassificationButton);
        mediator.setAdditionalFundsMenuBar(additionalFundsMenuBar);
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
        assertFalse(additionalFundsMenuBar.isVisible());
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
        assertFalse(additionalFundsMenuBar.isVisible());
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
        assertTrue(additionalFundsMenuBar.isVisible());
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
