package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.vui.vaadin.common.security.SecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;

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
 * Verifies {@link AaclUsageMediator}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 12/23/2019
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class AaclUsageMediatorTest {

    private MenuBar usageBatchMenuBar;
    private MenuItem loadUsageBatchMenuItem;
    private MenuBar fundPoolMenuBar;
    private MenuItem loadFundPoolMenuItem;
    private Button sendForClassificationButton;
    private OnDemandFileDownloader sendForClassificationDownloader;
    private Button loadClassifiedUsagesButton;
    private Button addToScenarioButton;
    private AaclUsageMediator mediator;

    @Before
    public void setUp() {
        usageBatchMenuBar = new MenuBar();
        loadUsageBatchMenuItem = usageBatchMenuBar
            .addItem(StringUtils.EMPTY)
            .getSubMenu()
            .addItem(StringUtils.EMPTY, null);
        fundPoolMenuBar = new MenuBar();
        loadFundPoolMenuItem = fundPoolMenuBar
            .addItem(StringUtils.EMPTY)
            .getSubMenu()
            .addItem(StringUtils.EMPTY, null);
        sendForClassificationButton = new Button();
        sendForClassificationDownloader = new OnDemandFileDownloader();
        loadClassifiedUsagesButton = new Button();
        addToScenarioButton = new Button();
        mediator = new AaclUsageMediator();
        mediator.setLoadUsageBatchMenuItem(loadUsageBatchMenuItem);
        mediator.setLoadFundPoolMenuItem(loadFundPoolMenuItem);
        mediator.setSendForClassificationButton(sendForClassificationButton);
        mediator.setSendForClassificationDownloader(sendForClassificationDownloader);
        mediator.setLoadClassifiedUsagesButton(loadClassifiedUsagesButton);
        mediator.setAddToScenarioButton(addToScenarioButton);
    }

    @Test
    public void testApplyPermissionsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(usageBatchMenuBar.isVisible());
        assertFalse(loadUsageBatchMenuItem.isVisible());
        assertTrue(fundPoolMenuBar.isVisible());
        assertFalse(loadFundPoolMenuItem.isVisible());
        assertFalse(sendForClassificationButton.isVisible());
        assertFalse(sendForClassificationDownloader.isVisible());
        assertFalse(loadClassifiedUsagesButton.isVisible());
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
        assertTrue(fundPoolMenuBar.isVisible());
        assertFalse(loadFundPoolMenuItem.isVisible());
        assertFalse(sendForClassificationButton.isVisible());
        assertFalse(sendForClassificationDownloader.isVisible());
        assertFalse(loadClassifiedUsagesButton.isVisible());
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
        assertTrue(fundPoolMenuBar.isVisible());
        assertTrue(loadFundPoolMenuItem.isVisible());
        assertTrue(sendForClassificationButton.isVisible());
        assertTrue(sendForClassificationDownloader.isVisible());
        assertTrue(loadClassifiedUsagesButton.isVisible());
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
        expect(SecurityUtils.hasPermission("FDA_LOAD_RESEARCHED_USAGE")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_CREATE_EDIT_SCENARIO")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_SEND_FOR_WORK_RESEARCH")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_SEND_FOR_CLASSIFICATION")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_LOAD_CLASSIFIED_USAGE")).andReturn(true).anyTimes();
    }
}
