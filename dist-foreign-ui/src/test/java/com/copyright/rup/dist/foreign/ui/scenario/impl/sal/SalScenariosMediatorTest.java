package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.vaadin.security.SecurityUtils;

import com.vaadin.ui.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link SalScenariosMediator}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/22/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class SalScenariosMediatorTest {

    private SalScenariosMediator mediator;
    private Button deleteButton;

    @Before
    public void setUp() {
        mockStatic(SecurityUtils.class);
        mediator = new SalScenariosMediator();
        deleteButton = new Button("Delete");
        mediator.setDeleteButton(deleteButton);
    }

    @Test
    public void testApplyPermissionsViewOnly() {
        mockViewOnlyPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(deleteButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionManager() {
        mockManagerPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(deleteButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testApplyPermissionsDistributionSpecialist() {
        mockSpecialistPermissions();
        replay(SecurityUtils.class);
        mediator.applyPermissions();
        assertTrue(deleteButton.isVisible());
        verify(SecurityUtils.class);
    }

    @Test
    public void testSelectedScenarioChangedNullScenario() {
        mediator.selectedScenarioChanged(null);
        assertFalse(deleteButton.isEnabled());
    }

    @Test
    public void testSelectedScenarioChangedInProgress() {
        Scenario scenario = new Scenario();
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        mediator.selectedScenarioChanged(scenario);
        assertTrue(deleteButton.isEnabled());
    }

    private void mockViewOnlyPermissions() {
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
    }

    private void mockManagerPermissions() {
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
    }

    private void mockSpecialistPermissions() {
        expect(SecurityUtils.hasPermission(anyString())).andStubReturn(false);
        expect(SecurityUtils.hasPermission("FDA_DELETE_SCENARIO")).andReturn(true).once();
    }
}
