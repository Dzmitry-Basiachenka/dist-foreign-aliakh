package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

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
 * Verifies {@link ViewAclGrantSetMediator}.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/22/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class ViewAclGrantSetMediatorTest {

    private Button deleteButton;
    private ViewAclGrantSetMediator mediator;

    @Before
    public void setUp() {
        deleteButton = new Button();
        mediator = new ViewAclGrantSetMediator();
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
