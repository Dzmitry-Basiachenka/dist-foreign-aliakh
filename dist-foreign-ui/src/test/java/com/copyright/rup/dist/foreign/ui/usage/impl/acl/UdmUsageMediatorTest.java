package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.anyString;
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
 * Verifies {@link UdmUsageMediator}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/11/21
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class UdmUsageMediatorTest {

    private static final String FDA_ACCESS_APPLICATION = "FDA_ACCESS_APPLICATION";

    private Button loadButton;
    private UdmUsageMediator mediator;

    @Before
    public void setUp() {
        loadButton = new Button();
        mediator = new UdmUsageMediator();
        mediator.setLoadButton(loadButton);
    }

    @Test
    public void testApplyPermissionsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(loadButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyManagerPermissions() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(loadButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplySpecialistPermissions() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(loadButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyResearcherPermissions() {
        mockResearcherPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(loadButton.isVisible());
        verify(SecurityUtils.class);
    }

    private void mockResearcherPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission(FDA_ACCESS_APPLICATION)).andReturn(true).anyTimes();
    }

    private void mockViewOnlyPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission(FDA_ACCESS_APPLICATION)).andReturn(true).anyTimes();
    }

    private void mockManagerPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission(FDA_ACCESS_APPLICATION)).andReturn(true).anyTimes();
    }

    private void mockSpecialistPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_ACCESS_APPLICATION)).andReturn(true).anyTimes();
        expect(SecurityUtils.hasPermission("FDA_SPECIALIST_PERMISSION")).andReturn(true).once();
    }
}
