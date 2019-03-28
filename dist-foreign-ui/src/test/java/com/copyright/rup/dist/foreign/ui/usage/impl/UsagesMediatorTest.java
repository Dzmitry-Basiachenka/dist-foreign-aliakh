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
    private Button loadUsageBatchButton;
    private Button loadFundPoolButton;
    private Button loadResearchedUsagesButton;
    private Button addToScenarioButton;
    private Button sendForResearchButton;
    private Button assignClassificationButton;
    private MenuBar withdrawnFundMenuBar;
    private UsagesMediator mediator;

    @Before
    public void setUp() {
        deleteButton = new Button();
        loadUsageBatchButton = new Button();
        loadFundPoolButton = new Button();
        loadResearchedUsagesButton = new Button();
        addToScenarioButton = new Button();
        sendForResearchButton = new Button();
        assignClassificationButton = new Button();
        withdrawnFundMenuBar = new MenuBar();
        mediator = new UsagesMediator();
        mediator.setLoadUsageBatchButton(loadUsageBatchButton);
        mediator.setLoadFundPoolButton(loadFundPoolButton);
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
        assertFalse(loadUsageBatchButton.isVisible());
        assertFalse(loadFundPoolButton.isVisible());
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
        assertFalse(loadUsageBatchButton.isVisible());
        assertFalse(loadFundPoolButton.isVisible());
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
        assertTrue(loadUsageBatchButton.isVisible());
        assertTrue(loadFundPoolButton.isVisible());
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
        assertFalse(loadUsageBatchButton.isVisible());
        assertFalse(loadFundPoolButton.isVisible());
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
        assertTrue(loadUsageBatchButton.isVisible());
        assertFalse(loadFundPoolButton.isVisible());
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
        assertFalse(loadUsageBatchButton.isVisible());
        assertFalse(loadFundPoolButton.isVisible());
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
        assertFalse(loadUsageBatchButton.isVisible());
        assertFalse(loadFundPoolButton.isVisible());
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
        assertFalse(loadUsageBatchButton.isVisible());
        assertTrue(loadFundPoolButton.isVisible());
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
        assertFalse(loadUsageBatchButton.isVisible());
        assertFalse(loadFundPoolButton.isVisible());
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
