package com.copyright.rup.dist.foreign.vui.usage.impl;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.vui.vaadin.common.security.SecurityUtils;

import com.vaadin.flow.component.button.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link ViewUsageBatchMediator}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 05/27/2019
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class ViewBatchesMediatorTest {

    private Button deleteButton;
    private ViewUsageBatchMediator mediator;

    @Before
    public void setUp() {
        deleteButton = new Button();
        mediator = new ViewUsageBatchMediator();
        mediator.setDeleteButton(deleteButton);
    }

    @Test
    public void testApplyPermissionsDistributionManagerOrViewOnly() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission("FDA_DELETE_USAGE")).andReturn(false).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(deleteButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionSpecialist() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_DELETE_USAGE")).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(deleteButton.isVisible());
        verify(SecurityUtils.class);
    }
}
