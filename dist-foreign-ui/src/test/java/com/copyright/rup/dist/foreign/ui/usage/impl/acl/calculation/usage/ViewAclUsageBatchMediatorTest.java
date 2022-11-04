package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.security.SecurityUtils;

import com.vaadin.ui.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link ViewAclUsageBatchMediator}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 11/03/2022
 *
 * @author Mikita Maistrenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class ViewAclUsageBatchMediatorTest {

    private Button deleteButton;
    private ViewAclUsageBatchMediator mediator;

    @Before
    public void setUp() {
        deleteButton = new Button();
        mediator = new ViewAclUsageBatchMediator();
        mediator.setDeleteButton(deleteButton);
    }

    @Test
    public void testApplySpecialistPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission("FDA_SPECIALIST_PERMISSION")).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(deleteButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyManagerPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission("FDA_SPECIALIST_PERMISSION")).andReturn(false).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(deleteButton.isVisible());
        verify(SecurityUtils.class);
    }
}
