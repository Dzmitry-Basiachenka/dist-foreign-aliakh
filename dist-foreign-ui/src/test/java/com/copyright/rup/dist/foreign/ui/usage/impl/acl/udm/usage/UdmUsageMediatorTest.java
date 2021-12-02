package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

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

    private static final String FDA_RESEARCHER_PERMISSION = "FDA_RESEARCHER_PERMISSION";
    private static final String FDA_MANAGER_PERMISSION = "FDA_MANAGER_PERMISSION";
    private static final String FDA_SPECIALIST_PERMISSION = "FDA_SPECIALIST_PERMISSION";

    private final Button multipleEditButton = new Button("Multiple Edit");
    private final Button publishButton = new Button("Publish");
    private final MenuBar udmBatchMenuBar = new MenuBar();
    private final MenuBar assignmentMenuBar = new MenuBar();
    private UdmUsageMediator mediator;

    @Before
    public void setUp() {
        mediator = new UdmUsageMediator();
        mediator.setBatchMenuBar(udmBatchMenuBar);
        mediator.setAssignmentMenuBar(assignmentMenuBar);
        mediator.setMultipleEditButton(multipleEditButton);
        mediator.setPublishButton(publishButton);
    }

    @Test
    public void testApplyViewOnlyPermissions() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(udmBatchMenuBar.isVisible());
        assertFalse(assignmentMenuBar.isVisible());
        assertFalse(multipleEditButton.isVisible());
        assertFalse(publishButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyManagerPermissions() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(udmBatchMenuBar.isVisible());
        assertFalse(publishButton.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        assertTrue(multipleEditButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplySpecialistPermissions() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(udmBatchMenuBar.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        assertTrue(multipleEditButton.isVisible());
        assertTrue(publishButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyResearcherPermissions() {
        mockResearcherPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(udmBatchMenuBar.isVisible());
        assertFalse(publishButton.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        assertTrue(multipleEditButton.isVisible());
        verify(SecurityUtils.class);
    }

    private void mockResearcherPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_RESEARCHER_PERMISSION)).andReturn(true).once();
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
}
