package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

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
 * Verifies {@link AclUsageMediator}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/07/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class AclUsageMediatorTest {

    private static final String FDA_SPECIALIST_PERMISSION = "FDA_SPECIALIST_PERMISSION";

    private final MenuBar aclUsageBatchMenuBar = new MenuBar();
    private final Button editButton = new Button("Edit");
    private AclUsageMediator mediator;

    @Before
    public void setUp() {
        mediator = new AclUsageMediator();
        mediator.setAclUsageBatchMenuBar(aclUsageBatchMenuBar);
        mediator.setEditButton(editButton);
    }

    @Test
    public void testApplySpecialistPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(true).times(1);
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(aclUsageBatchMenuBar.isVisible());
        assertTrue(editButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyManagerOrViewOnlyPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).times(1);
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(aclUsageBatchMenuBar.isVisible());
        assertFalse(editButton.isVisible());
        verify(SecurityUtils.class);
    }
}
