package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

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
 * Verifies {@link ViewAaclFundPoolMediator}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 02/05/2020
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class ViewAaclFundPoolMediatorTest {

    private Button deleteButton;
    private ViewAaclFundPoolMediator mediator;

    @Before
    public void setUp() {
        deleteButton = new Button();
        mediator = new ViewAaclFundPoolMediator();
        mediator.setDeleteButton(deleteButton);
    }

    @Test
    public void testApplyPermissionsDistributionManagerOrViewOnly() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission("FDA_DELETE_FUND_POOL")).andReturn(false).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(deleteButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionSpecialist() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_DELETE_FUND_POOL")).andReturn(true).once();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(deleteButton.isVisible());
        verify(SecurityUtils.class);
    }
}
