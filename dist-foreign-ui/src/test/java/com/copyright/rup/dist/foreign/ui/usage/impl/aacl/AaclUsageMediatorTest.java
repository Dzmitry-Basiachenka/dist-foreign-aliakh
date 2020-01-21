package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

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
    private MenuBar.MenuItem loadUsageBatchMenuItem;
    private Button sendForClassificationButton;
    private AaclUsageMediator mediator;

    @Before
    public void setUp() {
        usageBatchMenuBar = new MenuBar();
        sendForClassificationButton = new Button();
        loadUsageBatchMenuItem = usageBatchMenuBar.new MenuItem(StringUtils.EMPTY, null, null);
        mediator = new AaclUsageMediator();
        mediator.setLoadUsageBatchMenuItem(loadUsageBatchMenuItem);
        mediator.setSendForClassificationButton(sendForClassificationButton);
    }

    @Test
    public void testApplyPermissionsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(usageBatchMenuBar.isVisible());
        assertFalse(loadUsageBatchMenuItem.isVisible());
        assertFalse(sendForClassificationButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionManager() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(usageBatchMenuBar.isVisible());
        assertFalse(loadUsageBatchMenuItem.isVisible());
        assertFalse(sendForClassificationButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionSpecialist() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(usageBatchMenuBar.isVisible());
        assertTrue(loadUsageBatchMenuItem.isVisible());
        assertTrue(sendForClassificationButton.isVisible());
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
        expect(SecurityUtils.hasPermission("FDA_LOAD_RESEARCHED_USAGE")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_CREATE_EDIT_SCENARIO")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_SEND_FOR_WORK_RESEARCH")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_SEND_FOR_CLASSIFICATION")).andReturn(true).anyTimes();
    }
}
