package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyMenuBar;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.expectNew;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Verifies {@link AclGrantDetailWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/28/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AclGrantDetailWidget.class, ForeignSecurityUtils.class, Windows.class})
public class AclGrantDetailWidgetTest {

    private AclGrantDetailWidget aclGrantDetailWidget;
    private IAclGrantDetailController controller;
    private IStreamSource streamSource;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IAclGrantDetailController.class);
        AclGrantDetailFilterWidget filterWidget =
            new AclGrantDetailFilterWidget(createMock(IAclGrantDetailFilterController.class));
        expect(controller.initAclGrantDetailFilterWidget()).andReturn(filterWidget).once();
        streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
    }

    @Test
    public void testWidgetStructureForSpecialist() {
        setSpecialistExpectations();
        verifyStructure(true, true, true, true);
    }

    @Test
    public void testWidgetStructureForManager() {
        setManagerExpectations();
        verifyStructure(true, false, false, true);
    }

    @Test
    public void testWidgetStructureForViewOnly() {
        setViewOnlyExpectations();
        verifyStructure(false, false, false, true);
    }

    @Test
    public void testUploadButtonClick() throws Exception {
        mockStatic(Windows.class);
        setSpecialistExpectations();
        UploadGrantDetailWindow mockWindow = createMock(UploadGrantDetailWindow.class);
        expectNew(UploadGrantDetailWindow.class, controller).andReturn(mockWindow).once();
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(controller, Windows.class, UploadGrantDetailWindow.class, ForeignSecurityUtils.class, streamSource);
        initWidget();
        Button editButton = (Button) getButtonsLayout().getComponent(1);
        editButton.click();
        verify(controller, Windows.class, UploadGrantDetailWindow.class, ForeignSecurityUtils.class, streamSource);
    }

    @Test
    public void testEditButtonClick() throws Exception {
        mockStatic(Windows.class);
        setSpecialistExpectations();
        AclGrantDetailDto grantDetailDto = new AclGrantDetailDto();
        grantDetailDto.setId("884c8968-28fa-48ef-b13e-01571a8902fa");
        grantDetailDto.setEditable(true);
        EditAclGrantDetailWindow mockWindow = createMock(EditAclGrantDetailWindow.class);
        Set<AclGrantDetailDto> grants = Collections.singleton(grantDetailDto);
        expectNew(EditAclGrantDetailWindow.class, eq(grants), eq(controller), anyObject(ClickListener.class))
            .andReturn(mockWindow).once();
        Windows.showModalWindow(mockWindow);
        expectLastCall().once();
        replay(controller, Windows.class, EditAclGrantDetailWindow.class, ForeignSecurityUtils.class, streamSource);
        initWidget();
        Grid<AclGrantDetailDto> grid =
            (Grid<AclGrantDetailDto>) ((VerticalLayout) aclGrantDetailWidget.getSecondComponent()).getComponent(1);
        grid.setItems(grants);
        grid.select(grantDetailDto);
        Button editButton = (Button) getButtonsLayout().getComponent(2);
        editButton.click();
        verify(controller, Windows.class, EditAclGrantDetailWindow.class, ForeignSecurityUtils.class, streamSource);
    }

    private void verifyStructure(boolean... buttonsVisibility) {
        replay(controller, ForeignSecurityUtils.class, streamSource);
        initWidget();
        assertTrue(aclGrantDetailWidget.isLocked());
        assertEquals(270, aclGrantDetailWidget.getSplitPosition(), 0);
        verifyWindow(aclGrantDetailWidget, null, 100, 100, Unit.PERCENTAGE);
        assertTrue(aclGrantDetailWidget.getFirstComponent() instanceof AclGrantDetailFilterWidget);
        Component secondComponent = aclGrantDetailWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifyWindow(layout, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0), buttonsVisibility);
        Grid grid = (Grid) layout.getComponent(1);
        verifyGrid(grid, Arrays.asList(
            Triple.of("License Type", 200.0, -1),
            Triple.of("TOU Status", 150.0, -1),
            Triple.of("Grant Status", 120.0, -1),
            Triple.of("Eligible", 100.0, -1),
            Triple.of("Wr Wrk Inst", 100.0, -1),
            Triple.of("System Title", 200.0, -1),
            Triple.of("RH Account #", 150.0, -1),
            Triple.of("RH Name", 260.0, -1),
            Triple.of("TOU", 120.0, -1),
            Triple.of("Created Date", 100.0, -1),
            Triple.of("Updated Date", 100.0, -1),
            Triple.of("Grant Period", 110.0, -1),
            Triple.of("Manual Upload Flag", 150.0, -1)));
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(1, layout.getExpandRatio(grid), 0);
        verify(controller, ForeignSecurityUtils.class, streamSource);
    }

    private void verifyButtonsLayout(HorizontalLayout layout, boolean... buttonsVisibility) {
        verifyMenuBar(layout.getComponent(0), "Grant Set", buttonsVisibility[0], Arrays.asList("Create", "View"));
        verifyButton(layout.getComponent(1), "Upload", buttonsVisibility[1]);
        verifyButton(layout.getComponent(2), "Edit", buttonsVisibility[2]);
        verifyButton(layout.getComponent(3), "Export", buttonsVisibility[3]);
    }

    private void verifyButton(Component component, String name, boolean isVisible) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals(name, button.getCaption());
        assertEquals(isVisible, button.isVisible());
    }

    private void initWidget() {
        aclGrantDetailWidget = new AclGrantDetailWidget();
        aclGrantDetailWidget.setController(controller);
        aclGrantDetailWidget.init();
        aclGrantDetailWidget.initMediator().applyPermissions();
    }

    private void setSpecialistExpectations() {
        setPermissionsExpectations(true, false);
    }

    private void setManagerExpectations() {
        setPermissionsExpectations(false, true);
    }

    private void setViewOnlyExpectations() {
        setPermissionsExpectations(false, false);
    }

    private void setPermissionsExpectations(boolean isSpecialist, boolean isManager) {
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andStubReturn(isSpecialist);
        expect(ForeignSecurityUtils.hasManagerPermission()).andStubReturn(isManager);
        expect(controller.getExportAclGrantDetailsStreamSource()).andReturn(streamSource).once();
    }

    private HorizontalLayout getButtonsLayout() {
        return (HorizontalLayout) ((VerticalLayout) aclGrantDetailWidget.getSecondComponent()).getComponent(0);
    }
}
