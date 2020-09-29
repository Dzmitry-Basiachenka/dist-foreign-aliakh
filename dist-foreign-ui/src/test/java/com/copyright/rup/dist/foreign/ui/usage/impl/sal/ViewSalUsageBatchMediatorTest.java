package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
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
 * Verifies {@link ViewSalUsageBatchMediator}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/28/20
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class ViewSalUsageBatchMediatorTest {

    private Button deleteBatchButton;
    private Button deleteUsageDetailsButton;
    private ViewSalUsageBatchMediator mediator;

    @Before
    public void setUp() {
        deleteUsageDetailsButton = new Button();
        deleteBatchButton = new Button();
        mediator = new ViewSalUsageBatchMediator();
        mediator.setDeleteUsageDetailsButton(deleteUsageDetailsButton);
        mediator.setDeleteBatchButton(deleteBatchButton);
    }

    @Test
    public void testApplyPermissionsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(deleteBatchButton.isVisible());
        assertTrue(deleteUsageDetailsButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionManager() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(deleteBatchButton.isVisible());
        assertTrue(deleteUsageDetailsButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionSpecialist() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(deleteBatchButton.isVisible());
        assertTrue(deleteUsageDetailsButton.isVisible());
        verify(SecurityUtils.class);
    }

    private void mockViewOnlyPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_DELETE_USAGE")).andReturn(true).anyTimes();
    }

    private void mockManagerPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_DELETE_USAGE")).andReturn(true).anyTimes();
    }

    private void mockSpecialistPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission("FDA_DELETE_USAGE")).andReturn(true).anyTimes();
    }
}
