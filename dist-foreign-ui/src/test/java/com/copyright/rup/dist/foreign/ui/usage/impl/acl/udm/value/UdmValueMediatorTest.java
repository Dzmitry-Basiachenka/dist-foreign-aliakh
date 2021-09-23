package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

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
 * Verifies {@link UdmValueMediator}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/21
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class UdmValueMediatorTest {

    private static final String FDA_SPECIALIST_PERMISSION = "FDA_SPECIALIST_PERMISSION";

    private final Button populateButton = new Button("Populate Value Batch");
    private UdmValueMediator mediator;

    @Before
    public void setUp() {
        mediator = new UdmValueMediator();
        mediator.setPopulateButton(populateButton);
    }

    @Test
    public void testApplyViewOnlyPermissions() {
        mockPermissions(false);
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(populateButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyManagerPermissions() {
        mockPermissions(false);
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(populateButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplySpecialistPermissions() {
        mockPermissions(true);
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(populateButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyResearcherPermissions() {
        mockPermissions(false);
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(populateButton.isVisible());
        verify(SecurityUtils.class);
    }

    private void mockPermissions(boolean isSpecialist) {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(isSpecialist).once();
    }
}
