package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

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
    private static final String FDA_MANAGER_PERMISSION = "FDA_MANAGER_PERMISSION";
    private static final String FDA_RESEARCHER_PERMISSION = "FDA_RESEARCHER_PERMISSION";

    private final Button populateButton = new Button("Populate Value Batch");
    private final MenuBar assignmentMenuBar = new MenuBar();
    private final Button calculateProxyValuesButton = new Button("Calculate Proxies");
    private final Button publishButton = new Button("Publish");
    private UdmValueMediator mediator;

    @Before
    public void setUp() {
        mediator = new UdmValueMediator();
        mediator.setPopulateButton(populateButton);
        mediator.setAssignmentMenuBar(assignmentMenuBar);
        mediator.setCalculateProxyValuesButton(calculateProxyValuesButton);
        mediator.setPublishButton(publishButton);
    }

    @Test
    public void testApplyViewOnlyPermissions() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(populateButton.isVisible());
        assertFalse(assignmentMenuBar.isVisible());
        assertFalse(calculateProxyValuesButton.isVisible());
        assertFalse(publishButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyManagerPermissions() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(populateButton.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        assertFalse(calculateProxyValuesButton.isVisible());
        assertFalse(publishButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplySpecialistPermissions() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(populateButton.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        assertTrue(calculateProxyValuesButton.isVisible());
        assertTrue(publishButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyResearcherPermissions() {
        mockResearcherPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(populateButton.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        assertFalse(calculateProxyValuesButton.isVisible());
        assertFalse(publishButton.isVisible());
        verify(SecurityUtils.class);
    }

    private void mockViewOnlyPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_RESEARCHER_PERMISSION)).andReturn(false).once();
    }

    private void mockManagerPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(true).once();
        expect(SecurityUtils.hasPermission(FDA_RESEARCHER_PERMISSION)).andReturn(false).once();
    }

    private void mockSpecialistPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(true).once();
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_RESEARCHER_PERMISSION)).andReturn(false).once();
    }

    private void mockResearcherPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_RESEARCHER_PERMISSION)).andReturn(true).once();
    }
}
