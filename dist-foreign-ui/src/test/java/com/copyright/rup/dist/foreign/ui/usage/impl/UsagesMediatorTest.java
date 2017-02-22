package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;

import com.copyright.rup.vaadin.security.SecurityUtils;

import com.vaadin.ui.Button;

import org.easymock.EasyMock;
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

    private static final String USERNAME = "username@copyright.com";

    private Button deleteButton;
    private Button loadButton;
    private UsagesMediator mediator;

    @Before
    public void setUp() {
        deleteButton = new Button();
        loadButton = new Button();
        mediator = new UsagesMediator();
        mediator.setLoadUsageButton(loadButton);
        mediator.setDeleteUsageButton(deleteButton);
    }

    @Test
    public void testApplyPermissionsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(deleteButton.isVisible());
        assertFalse(loadButton.isVisible());
    }

    @Test
    public void testApplyPermissionsDistributionManager() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(deleteButton.isVisible());
        assertFalse(loadButton.isVisible());
    }

    @Test
    public void testApplyPermissionsDistributionSpecialist() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(deleteButton.isVisible());
        assertTrue(loadButton.isVisible());
    }

    private void mockViewOnlyPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.getUserName()).andReturn(USERNAME).anyTimes();
        expect(SecurityUtils.hasPermission(EasyMock.anyObject())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_ACCESS_APPLICATION")).andReturn(true).anyTimes();
    }

    private void mockManagerPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.getUserName()).andReturn(USERNAME).anyTimes();
        expect(SecurityUtils.hasPermission(EasyMock.anyObject())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_ACCESS_APPLICATION")).andReturn(true).anyTimes();
    }

    private void mockSpecialistPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.getUserName()).andReturn(USERNAME).anyTimes();
        expect(SecurityUtils.hasPermission(EasyMock.anyObject())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_ACCESS_APPLICATION")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_DELETE_USAGE")).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_LOAD_USAGE")).andReturn(true).anyTimes();
    }
}
