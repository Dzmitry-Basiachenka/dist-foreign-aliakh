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
    private Button loadButton;
    private Button addToScenarioButton;
    private UsagesMediator mediator;

    @Before
    public void setUp() {
        deleteButton = new Button();
        loadButton = new Button();
        addToScenarioButton = new Button();
        mediator = new UsagesMediator();
        mediator.setLoadUsageButton(loadButton);
        mediator.setDeleteUsageButton(deleteButton);
        mediator.setAddToScenarioButton(addToScenarioButton);
    }

    @Test
    public void testApplyPermissionsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(deleteButton.isVisible());
        assertFalse(loadButton.isVisible());
        assertFalse(addToScenarioButton.isVisible());
    }

    @Test
    public void testApplyPermissionsDistributionManager() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(deleteButton.isVisible());
        assertFalse(loadButton.isVisible());
        assertFalse(addToScenarioButton.isVisible());
    }

    @Test
    public void testApplyPermissionsDistributionSpecialist() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(deleteButton.isVisible());
        assertTrue(loadButton.isVisible());
        assertTrue(addToScenarioButton.isVisible());
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
        expect(SecurityUtils.hasPermission("FDA_CREATE_EDIT_SCENARIO")).andReturn(true).anyTimes();
    }
}
