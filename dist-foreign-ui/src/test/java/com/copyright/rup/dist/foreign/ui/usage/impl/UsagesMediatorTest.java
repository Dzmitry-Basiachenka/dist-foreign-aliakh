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

    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private Button deleteButton;
    private Button loadResearchedUsagesButton;
    private Button addToScenarioButton;
    private Button sendForResearchButton;
    private Button assignClassificationButton;
    private MenuBar withdrawnFundMenuBar;
    private MenuBar usageBatchMenuBar;
    private MenuBar fundPoolMenuBar;
    private MenuBar.MenuItem loadUsageBatchMenuItem;
    private MenuBar.MenuItem loadFundPoolMenuItem;
    private UsagesMediator mediator;

    @Before
    public void setUp() {
        deleteButton = new Button();
        loadResearchedUsagesButton = new Button();
        addToScenarioButton = new Button();
        sendForResearchButton = new Button();
        assignClassificationButton = new Button();
        withdrawnFundMenuBar = new MenuBar();
        usageBatchMenuBar = new MenuBar();
        fundPoolMenuBar = new MenuBar();
        loadUsageBatchMenuItem = usageBatchMenuBar.new MenuItem(StringUtils.EMPTY, null, null);
        loadFundPoolMenuItem = fundPoolMenuBar.new MenuItem(StringUtils.EMPTY, null, null);
        mediator = new UsagesMediator();
        mediator.setFundPoolMenuBar(fundPoolMenuBar);
        mediator.setUsageBatchMenuBar(usageBatchMenuBar);
        mediator.setLoadFundPoolMenuItem(loadFundPoolMenuItem);
        mediator.setLoadUsageBatchMenuItem(loadUsageBatchMenuItem);
        mediator.setLoadResearchedUsagesButton(loadResearchedUsagesButton);
        mediator.setDeleteUsageButton(deleteButton);
        mediator.setAddToScenarioButton(addToScenarioButton);
        mediator.setSendForResearchButton(sendForResearchButton);
        mediator.setAssignClassificationButton(assignClassificationButton);
        mediator.setWithdrawnFundMenuBar(withdrawnFundMenuBar);
    }

    @Test
    public void testApplyPermissionsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(deleteButton.isVisible());
        assertTrue(usageBatchMenuBar.isVisible());
        assertTrue(fundPoolMenuBar.isVisible());
        assertFalse(loadUsageBatchMenuItem.isVisible());
        assertFalse(loadFundPoolMenuItem.isVisible());
        assertFalse(loadResearchedUsagesButton.isVisible());
        assertFalse(addToScenarioButton.isVisible());
        assertFalse(sendForResearchButton.isVisible());
        assertFalse(assignClassificationButton.isVisible());
        assertFalse(withdrawnFundMenuBar.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionManager() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(deleteButton.isVisible());
        assertTrue(usageBatchMenuBar.isVisible());
        assertTrue(fundPoolMenuBar.isVisible());
        assertFalse(loadUsageBatchMenuItem.isVisible());
        assertFalse(loadFundPoolMenuItem.isVisible());
        assertFalse(loadResearchedUsagesButton.isVisible());
        assertFalse(addToScenarioButton.isVisible());
        assertFalse(sendForResearchButton.isVisible());
        assertFalse(assignClassificationButton.isVisible());
        assertFalse(withdrawnFundMenuBar.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionSpecialist() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(deleteButton.isVisible());
        assertTrue(usageBatchMenuBar.isVisible());
        assertTrue(fundPoolMenuBar.isVisible());
        assertTrue(loadUsageBatchMenuItem.isVisible());
        assertTrue(loadFundPoolMenuItem.isVisible());
        assertTrue(loadResearchedUsagesButton.isVisible());
        assertTrue(addToScenarioButton.isVisible());
        assertTrue(sendForResearchButton.isVisible());
        assertTrue(assignClassificationButton.isVisible());
        assertTrue(withdrawnFundMenuBar.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testOnProductFamilyChangedFasViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.onProductFamilyChanged(FAS_PRODUCT_FAMILY);
        assertFalse(deleteButton.isVisible());
        assertTrue(usageBatchMenuBar.isVisible());
        assertFalse(fundPoolMenuBar.isVisible());
        assertFalse(loadUsageBatchMenuItem.isVisible());
        assertFalse(loadFundPoolMenuItem.isVisible());
        assertFalse(loadResearchedUsagesButton.isVisible());
        assertFalse(addToScenarioButton.isVisible());
        assertFalse(sendForResearchButton.isVisible());
        assertFalse(withdrawnFundMenuBar.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testOnProductFamilyChangedFasSpecialist() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.onProductFamilyChanged(FAS_PRODUCT_FAMILY);
        assertTrue(deleteButton.isVisible());
        assertTrue(usageBatchMenuBar.isVisible());
        assertFalse(fundPoolMenuBar.isVisible());
        assertTrue(loadUsageBatchMenuItem.isVisible());
        assertFalse(loadFundPoolMenuItem.isVisible());
        assertTrue(loadResearchedUsagesButton.isVisible());
        assertTrue(addToScenarioButton.isVisible());
        assertTrue(sendForResearchButton.isVisible());
        assertFalse(assignClassificationButton.isVisible());
        assertFalse(withdrawnFundMenuBar.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testOnProductFamilyChangedFasManager() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.onProductFamilyChanged(FAS_PRODUCT_FAMILY);
        assertFalse(deleteButton.isVisible());
        assertTrue(usageBatchMenuBar.isVisible());
        assertFalse(fundPoolMenuBar.isVisible());
        assertFalse(loadUsageBatchMenuItem.isVisible());
        assertFalse(loadFundPoolMenuItem.isVisible());
        assertFalse(loadResearchedUsagesButton.isVisible());
        assertFalse(addToScenarioButton.isVisible());
        assertFalse(sendForResearchButton.isVisible());
        assertFalse(withdrawnFundMenuBar.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testOnProductFamilyChangedNtsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.onProductFamilyChanged(NTS_PRODUCT_FAMILY);
        assertFalse(deleteButton.isVisible());
        assertFalse(usageBatchMenuBar.isVisible());
        assertTrue(fundPoolMenuBar.isVisible());
        assertFalse(loadUsageBatchMenuItem.isVisible());
        assertFalse(loadFundPoolMenuItem.isVisible());
        assertFalse(loadResearchedUsagesButton.isVisible());
        assertFalse(addToScenarioButton.isVisible());
        assertFalse(sendForResearchButton.isVisible());
        assertFalse(withdrawnFundMenuBar.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testOnProductFamilyChangedNtsSpecialist() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.onProductFamilyChanged(NTS_PRODUCT_FAMILY);
        assertTrue(deleteButton.isVisible());
        assertFalse(usageBatchMenuBar.isVisible());
        assertTrue(fundPoolMenuBar.isVisible());
        assertFalse(loadUsageBatchMenuItem.isVisible());
        assertTrue(loadFundPoolMenuItem.isVisible());
        assertFalse(loadResearchedUsagesButton.isVisible());
        assertTrue(addToScenarioButton.isVisible());
        assertFalse(sendForResearchButton.isVisible());
        assertTrue(assignClassificationButton.isVisible());
        assertTrue(withdrawnFundMenuBar.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testOnProductFamilyChangedNtsManager() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.onProductFamilyChanged(NTS_PRODUCT_FAMILY);
        assertFalse(deleteButton.isVisible());
        assertFalse(usageBatchMenuBar.isVisible());
        assertTrue(fundPoolMenuBar.isVisible());
        assertFalse(loadUsageBatchMenuItem.isVisible());
        assertFalse(loadFundPoolMenuItem.isVisible());
        assertFalse(loadResearchedUsagesButton.isVisible());
        assertFalse(addToScenarioButton.isVisible());
        assertFalse(sendForResearchButton.isVisible());
        assertFalse(withdrawnFundMenuBar.isVisible());
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
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_ACCESS_APPLICATION")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_DELETE_USAGE")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_LOAD_USAGE")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_LOAD_FUND_POOL")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_LOAD_RESEARCHED_USAGE")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_CREATE_EDIT_SCENARIO")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_SEND_FOR_WORK_RESEARCH")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_ASSIGN_CLASSIFICATION")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_CREATE_DELETE_FUND")).andReturn(true).anyTimes();
    }
}
