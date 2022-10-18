package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

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
 * Verifies {@link AclGrantDetailMediator}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 02/09/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class AclGrantDetailMediatorTest {

    private static final String FDA_MANAGER_PERMISSION = "FDA_MANAGER_PERMISSION";
    private static final String FDA_SPECIALIST_PERMISSION = "FDA_SPECIALIST_PERMISSION";

    private final MenuBar grantSetMenuBar = new MenuBar();
    private final Button editButton = new Button();
    private final Button uploadButton = new Button();

    private AclGrantDetailMediator mediator;
    private MenuBar.MenuItem createMenuItem;
    private MenuBar.MenuItem viewMenuItem;

    @Before
    public void setUp() {
        mediator = new AclGrantDetailMediator();
        createMenuItem = grantSetMenuBar.addItem("Create");
        viewMenuItem = grantSetMenuBar.addItem("View");
        mediator.setCreateMenuItem(createMenuItem);
        mediator.setEditButton(editButton);
        mediator.setUploadButton(uploadButton);
    }

    @Test
    public void testApplySpecialistPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(true).once();
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(false).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(grantSetMenuBar.isVisible());
        assertTrue(createMenuItem.isVisible());
        assertTrue(viewMenuItem.isVisible());
        assertTrue(editButton.isVisible());
        assertTrue(uploadButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyManagerPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(grantSetMenuBar.isVisible());
        assertTrue(createMenuItem.isVisible());
        assertTrue(viewMenuItem.isVisible());
        assertFalse(editButton.isVisible());
        assertFalse(uploadButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyNoPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(false).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(grantSetMenuBar.isVisible());
        assertFalse(createMenuItem.isVisible());
        assertTrue(viewMenuItem.isVisible());
        assertFalse(editButton.isVisible());
        assertFalse(uploadButton.isVisible());
        verify(SecurityUtils.class);
    }
}
