package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.security.SecurityUtils;

import com.vaadin.ui.MenuBar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link AclFundPoolMediator}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/29/2022
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class AclFundPoolMediatorTest {

    private static final String FDA_SPECIALIST_PERMISSION = "FDA_SPECIALIST_PERMISSION";
    private static final String FDA_MANAGER_PERMISSION = "FDA_MANAGER_PERMISSION";
    private static final String FDA_VIEW_ONLY_PERMISSION = "FDA_VIEW_ONLY_PERMISSION";

    private final MenuBar fundPoolMenuBar = new MenuBar();

    private AclFundPoolMediator mediator;
    private MenuBar.MenuItem createMenuItem;
    private MenuBar.MenuItem viewMenuItem;

    @Before
    public void setUp() {
        mediator = new AclFundPoolMediator();
        mediator.setFundPoolMenuBar(fundPoolMenuBar);
        createMenuItem = fundPoolMenuBar.addItem("Create");
        viewMenuItem = fundPoolMenuBar.addItem("View");
        mediator.setCreateMenuItem(createMenuItem);
        mediator.setViewMenuItem(viewMenuItem);
    }

    @Test
    public void testApplySpecialistPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(true).once();
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_VIEW_ONLY_PERMISSION)).andReturn(false).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(fundPoolMenuBar.isVisible());
        assertTrue(createMenuItem.isVisible());
        assertTrue(viewMenuItem.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyManagerPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(true).once();
        expect(SecurityUtils.hasPermission(FDA_VIEW_ONLY_PERMISSION)).andReturn(false).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(fundPoolMenuBar.isVisible());
        assertFalse(createMenuItem.isVisible());
        assertTrue(viewMenuItem.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyViewOnlyPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_VIEW_ONLY_PERMISSION)).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(fundPoolMenuBar.isVisible());
        assertFalse(createMenuItem.isVisible());
        assertTrue(viewMenuItem.isVisible());
        verify(SecurityUtils.class);
    }
}
