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
import com.vaadin.ui.Grid;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.components.grid.GridSelectionModel;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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

    private MenuBar udmBatchMenuBar;
    private MenuBar assignmentMenuBar;
    private MenuBar.MenuItem assignItem;
    private MenuBar.MenuItem unassignItem;
    private Registration registration;
    private Grid<UdmUsageDto> uasgeGrid;
    private SelectionEvent<UdmUsageDto> selectionEvent;
    private GridSelectionModel<UdmUsageDto> selectionModel;
    private Capture<SelectionListener<UdmUsageDto>> capture;
    private UdmUsageMediator mediator;

    @Before
    public void setUp() {
        capture = Capture.newInstance();
        udmBatchMenuBar = new MenuBar();
        assignmentMenuBar = new MenuBar();
        uasgeGrid = createMock(Grid.class);
        selectionEvent = createMock(SelectionEvent.class);
        selectionModel = createMock(GridSelectionModel.class);
        assignItem = createMock(MenuBar.MenuItem.class);
        unassignItem = createMock(MenuBar.MenuItem.class);
        registration = createMock(Registration.class);
        mediator = new UdmUsageMediator();
        mediator.setUsageGrid(uasgeGrid);
        mediator.setBatchMenuBar(udmBatchMenuBar);
        mediator.setAssignmentMenuBar(assignmentMenuBar);
        mediator.setUnassignItem(unassignItem);
        mediator.setAssignItem(assignItem);
    }

    @Test
    public void testApplyViewOnlyPermissions() {
        mockViewOnlyPermissions();
        replay(uasgeGrid, selectionModel, SecurityUtils.class);
        mediator.applyPermissions();
        assertFalse(udmBatchMenuBar.isVisible());
        assertFalse(assignmentMenuBar.isVisible());
        verify(uasgeGrid, selectionModel, SecurityUtils.class);
    }

    @Test
    public void testApplyManagerPermissions() {
        mockManagerPermissions(Sets.newHashSet(new UdmUsageDto()), true);
        replay(uasgeGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
        mediator.applyPermissions();
        capture.getValue().selectionChange(selectionEvent);
        assertFalse(udmBatchMenuBar.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        verify(uasgeGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
    }

    @Test
    public void testApplyManagerPermissionsUsagesNotSelected() {
        mockManagerPermissions(Sets.newHashSet(), false);
        replay(uasgeGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
        mediator.applyPermissions();
        capture.getValue().selectionChange(selectionEvent);
        assertFalse(udmBatchMenuBar.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        verify(uasgeGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
    }

    @Test
    public void testApplySpecialistPermissions() {
        mockSpecialistPermissions(Sets.newHashSet(new UdmUsageDto()), true);
        replay(uasgeGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
        mediator.applyPermissions();
        capture.getValue().selectionChange(selectionEvent);
        assertTrue(udmBatchMenuBar.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        verify(uasgeGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
    }

    @Test
    public void testApplySpecialistPermissionsUsagesNotSelected() {
        mockSpecialistPermissions(Sets.newHashSet(), false);
        replay(uasgeGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
        mediator.applyPermissions();
        capture.getValue().selectionChange(selectionEvent);
        assertTrue(udmBatchMenuBar.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        verify(uasgeGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
    }

    @Test
    public void testApplyResearcherPermissions() {
        mockResearcherPermissions(Sets.newHashSet(new UdmUsageDto()), true);
        replay(uasgeGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
        mediator.applyPermissions();
        capture.getValue().selectionChange(selectionEvent);
        assertFalse(udmBatchMenuBar.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        verify(uasgeGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
    }

    @Test
    public void testApplyResearcherPermissionsUsagesNotSelected() {
        mockResearcherPermissions(Sets.newHashSet(), false);
        replay(uasgeGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
        mediator.applyPermissions();
        capture.getValue().selectionChange(selectionEvent);
        assertFalse(udmBatchMenuBar.isVisible());
        assertTrue(assignmentMenuBar.isVisible());
        verify(uasgeGrid, selectionModel, selectionEvent, assignItem, unassignItem, registration, SecurityUtils.class);
    }

    private void mockResearcherPermissions(Set<UdmUsageDto> usages, boolean areItemsEnabled) {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_RESEARCHER_PERMISSION)).andReturn(true).once();
        expect(uasgeGrid.setSelectionMode(Grid.SelectionMode.MULTI)).andReturn(selectionModel).once();
        expect(uasgeGrid.addSelectionListener(capture(capture))).andReturn(registration).once();
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
        expect(uasgeGrid.setSelectionMode(Grid.SelectionMode.NONE)).andReturn(selectionModel).once();
    }

    private void mockManagerPermissions(Set<UdmUsageDto> usages, boolean areItemsEnabled) {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(false).once();
        expect(SecurityUtils.hasPermission(FDA_MANAGER_PERMISSION)).andReturn(true).once();
        expect(uasgeGrid.setSelectionMode(Grid.SelectionMode.MULTI)).andReturn(selectionModel).once();
        expect(uasgeGrid.addSelectionListener(capture(capture))).andReturn(registration).once();
        expect(selectionEvent.getAllSelectedItems()).andReturn(usages).once();
        assignItem.setEnabled(areItemsEnabled);
        expectLastCall().once();
        unassignItem.setEnabled(areItemsEnabled);
        expectLastCall().once();
    }

    private void mockSpecialistPermissions(Set<UdmUsageDto> usages, boolean areItemsEnabled) {
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.hasPermission(FDA_SPECIALIST_PERMISSION)).andReturn(true).once();
        expect(uasgeGrid.setSelectionMode(Grid.SelectionMode.MULTI)).andReturn(selectionModel).once();
        expect(uasgeGrid.addSelectionListener(capture(capture))).andReturn(registration).once();
        expect(selectionEvent.getAllSelectedItems()).andReturn(usages).once();
        assignItem.setEnabled(areItemsEnabled);
        expectLastCall().once();
        unassignItem.setEnabled(areItemsEnabled);
        expectLastCall().once();
    }
}
