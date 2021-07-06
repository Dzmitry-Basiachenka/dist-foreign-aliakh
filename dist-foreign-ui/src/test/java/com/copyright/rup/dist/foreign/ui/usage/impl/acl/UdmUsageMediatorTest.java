package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.vaadin.security.SecurityUtils;

import com.google.common.collect.Sets;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.components.grid.GridSelectionModel;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.Set;

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

    private final Button editButton = new Button("Edit Usage");
    private final MenuBar udmBatchMenuBar = new MenuBar();
    private final MenuBar assignmentMenuBar = new MenuBar();
    private MenuBar.MenuItem assignItem;
    private MenuBar.MenuItem unassignItem;
    private Registration registration;
    private Grid<UdmUsageDto> usageGrid;
    private SelectionEvent<UdmUsageDto> selectionEvent;
    private GridSelectionModel<UdmUsageDto> selectionModel;
    private Capture<SelectionListener<UdmUsageDto>> capture;
    private UdmUsageMediator mediator;

    @Before
    public void setUp() {
        capture = Capture.newInstance();
        usageGrid = createMock(Grid.class);
        selectionEvent = createMock(SelectionEvent.class);
        selectionModel = createMock(GridSelectionModel.class);
        assignItem = createMock(MenuBar.MenuItem.class);
        unassignItem = createMock(MenuBar.MenuItem.class);
        registration = createMock(Registration.class);
        mediator = new UdmUsageMediator();
        mediator.setUsageGrid(usageGrid);
        mediator.setBatchMenuBar(udmBatchMenuBar);
        mediator.setAssignmentMenuBar(assignmentMenuBar);
        mediator.setUnassignItem(unassignItem);
        mediator.setAssignItem(assignItem);
        mediator.setEditButton(editButton);
    }

    @Test
    public void testApplyViewOnlyPermissions() {
        mockViewOnlyPermissions();
        replay(usageGrid, selectionModel, SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(udmBatchMenuBar.isVisible());
        assertFalse(assignmentMenuBar.isVisible());
        assertFalse(editButton.isVisible());
        verify(usageGrid, selectionModel, SecurityUtils.class);
    }

    @Test
    public void testApplyManagerPermissionsSingleUsageSelected() {
        mockManagerPermissions(Collections.singleton(buildUdmUsageDto("26f9cd5b-3883-4d0f-8cee-6f3bfc0cac3c")), true);
        replay(usageGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
        mediator.applyPermissions();
        capture.getValue().selectionChange(selectionEvent);
        assertFalse(udmBatchMenuBar.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        assertTrue(editButton.isVisible());
        assertTrue(editButton.isEnabled());
        verify(usageGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
    }

    @Test
    public void testApplyManagerPermissionsMultipleUsagesSelected() {
        mockManagerPermissions(Sets.newHashSet(buildUdmUsageDto("082a0b1a-fe14-428f-9f28-a24e1ccb9a63"),
            buildUdmUsageDto("125c0bdd-4a38-48bc-ab15-164d16acb68f")), true);
        replay(usageGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
        mediator.applyPermissions();
        capture.getValue().selectionChange(selectionEvent);
        assertFalse(udmBatchMenuBar.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        assertTrue(editButton.isVisible());
        assertFalse(editButton.isEnabled());
        verify(usageGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
    }

    @Test
    public void testApplyManagerPermissionsUsagesNotSelected() {
        mockManagerPermissions(Collections.emptySet(), false);
        replay(usageGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
        mediator.applyPermissions();
        capture.getValue().selectionChange(selectionEvent);
        assertFalse(udmBatchMenuBar.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        assertTrue(editButton.isVisible());
        assertFalse(editButton.isEnabled());
        verify(usageGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
    }

    @Test
    public void testApplySpecialistPermissionsSingleUsageSelected() {
        mockSpecialistPermissions(Collections.singleton(buildUdmUsageDto("7c19a861-fe7b-45c5-a2e1-11995a5b9801")),
            true);
        replay(usageGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
        mediator.applyPermissions();
        capture.getValue().selectionChange(selectionEvent);
        assertTrue(udmBatchMenuBar.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        assertTrue(editButton.isVisible());
        assertTrue(editButton.isEnabled());
        verify(usageGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
    }

    @Test
    public void testApplySpecialistPermissionsMultipleUsagesSelected() {
        mockSpecialistPermissions(Sets.newHashSet(buildUdmUsageDto("0195dd34-2f7e-4826-8e99-28ff6f2ae63c"),
            buildUdmUsageDto("0f7531ee-5bdb-4a0d-a55c-4002b2ebb9ea")), true);
        replay(usageGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
        mediator.applyPermissions();
        capture.getValue().selectionChange(selectionEvent);
        assertTrue(udmBatchMenuBar.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        assertTrue(editButton.isVisible());
        assertFalse(editButton.isEnabled());
        verify(usageGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
    }

    @Test
    public void testApplySpecialistPermissionsUsagesNotSelected() {
        mockSpecialistPermissions(Collections.emptySet(), false);
        replay(usageGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
        mediator.applyPermissions();
        capture.getValue().selectionChange(selectionEvent);
        assertTrue(udmBatchMenuBar.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        assertTrue(editButton.isVisible());
        assertFalse(editButton.isEnabled());
        verify(usageGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
    }

    @Test
    public void testApplyResearcherPermissions() {
        mockResearcherPermissions(Collections.singleton(buildUdmUsageDto("8868bd7b-dace-443d-bf6f-ffa27b61058d")),
            true);
        replay(usageGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
        mediator.applyPermissions();
        capture.getValue().selectionChange(selectionEvent);
        assertFalse(udmBatchMenuBar.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        assertFalse(editButton.isVisible());
        verify(usageGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
    }

    @Test
    public void testApplyResearcherPermissionsUsagesNotSelected() {
        mockResearcherPermissions(Collections.emptySet(), false);
        replay(usageGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
        mediator.applyPermissions();
        capture.getValue().selectionChange(selectionEvent);
        assertFalse(udmBatchMenuBar.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        assertFalse(editButton.isVisible());
        verify(usageGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
    }

    private void mockResearcherPermissions(Set<UdmUsageDto> usages, boolean areItemsEnabled) {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_RESEARCHER_PERMISSION)).andReturn(true).once();
        expect(usageGrid.setSelectionMode(Grid.SelectionMode.MULTI)).andReturn(selectionModel).once();
        expect(usageGrid.addSelectionListener(capture(capture))).andReturn(registration).once();
        expect(selectionEvent.getAllSelectedItems()).andReturn(usages).once();
        assignItem.setEnabled(areItemsEnabled);
        expectLastCall().once();
        unassignItem.setEnabled(areItemsEnabled);
        expectLastCall().once();
    }

    private void mockViewOnlyPermissions() {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_RESEARCHER_PERMISSION)).andReturn(false).once();
        expect(usageGrid.setSelectionMode(Grid.SelectionMode.NONE)).andReturn(selectionModel).once();
    }

    private void mockManagerPermissions(Set<UdmUsageDto> usages, boolean areItemsEnabled) {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(true).once();
        expect(usageGrid.setSelectionMode(Grid.SelectionMode.MULTI)).andReturn(selectionModel).once();
        expect(usageGrid.addSelectionListener(capture(capture))).andReturn(registration).once();
        expect(selectionEvent.getAllSelectedItems()).andReturn(usages).once();
        assignItem.setEnabled(areItemsEnabled);
        expectLastCall().once();
        unassignItem.setEnabled(areItemsEnabled);
        expectLastCall().once();
    }

    private void mockSpecialistPermissions(Set<UdmUsageDto> usages, boolean areItemsEnabled) {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(true).once();
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(false).once();
        expect(usageGrid.setSelectionMode(Grid.SelectionMode.MULTI)).andReturn(selectionModel).once();
        expect(usageGrid.addSelectionListener(capture(capture))).andReturn(registration).once();
        expect(selectionEvent.getAllSelectedItems()).andReturn(usages).once();
        assignItem.setEnabled(areItemsEnabled);
        expectLastCall().once();
        unassignItem.setEnabled(areItemsEnabled);
        expectLastCall().once();
    }

    private UdmUsageDto buildUdmUsageDto(String id) {
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setId(id);
        return udmUsageDto;
    }
}
