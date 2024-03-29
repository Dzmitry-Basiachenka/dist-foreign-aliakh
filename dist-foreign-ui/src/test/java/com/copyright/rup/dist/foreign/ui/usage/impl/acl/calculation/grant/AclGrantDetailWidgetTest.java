package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFooterItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyMenuBar;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
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

import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Date;
import java.util.List;
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
@PrepareForTest({AclGrantDetailWidget.class, ForeignSecurityUtils.class, Windows.class, JavaScript.class})
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
        expect(controller.getExportAclGrantDetailsStreamSource()).andReturn(streamSource).once();
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
    public void testWidgetStructureForOthers() {
        setOthersExpectations();
        verifyStructure(true, false, false, true);
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
        Set<AclGrantDetailDto> grants = Set.of(grantDetailDto);
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

    @Test
    public void testGridValues() {
        mockStatic(JavaScript.class);
        List<AclGrantDetailDto> grantDetails = List.of(buildAclGrantDetailDto());
        setSpecialistExpectations();
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.loadBeans(0, Integer.MAX_VALUE, List.of())).andReturn(grantDetails).once();
        replay(JavaScript.class, ForeignSecurityUtils.class, controller, streamSource);
        initWidget();
        Grid grid = (Grid) ((VerticalLayout) aclGrantDetailWidget.getSecondComponent()).getComponent(1);
        DataProvider dataProvider = grid.getDataProvider();
        dataProvider.refreshAll();
        Object[][] expectedCells = {
            {"Grant set name", 202212, "ACL", "Print&Digital", "GRANT", "N", 122857215L, "Applied catalysis",
                1000009641L, "Elsevier Science & Technology Journals", "PRINT", "08/31/2022", "08/31/2022", "N"}
        };
        verifyGridItems(grid, grantDetails, expectedCells);
        verify(JavaScript.class, ForeignSecurityUtils.class, controller, streamSource);
        Object[][] expectedFooterColumns = {
            {"grantSetName", "Grant Details Count: 1", null},
        };
        verifyFooterItems(grid, expectedFooterColumns);
    }

    private void verifyStructure(boolean... buttonsVisibility) {
        replay(controller, ForeignSecurityUtils.class, streamSource);
        initWidget();
        assertTrue(aclGrantDetailWidget.isLocked());
        assertEquals(270, aclGrantDetailWidget.getSplitPosition(), 0);
        verifyWindow(aclGrantDetailWidget, null, 100, 100, Unit.PERCENTAGE);
        assertThat(aclGrantDetailWidget.getFirstComponent(), instanceOf(AclGrantDetailFilterWidget.class));
        Component secondComponent = aclGrantDetailWidget.getSecondComponent();
        assertThat(secondComponent, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifyWindow(layout, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0), buttonsVisibility);
        Grid grid = (Grid) layout.getComponent(1);
        verifyGrid(grid, List.of(
            Triple.of("Grant Set Name", 200.0, -1),
            Triple.of("Grant Period", 110.0, -1),
            Triple.of("License Type", 130.0, -1),
            Triple.of("TOU Status", 150.0, -1),
            Triple.of("Grant Status", 120.0, -1),
            Triple.of("Eligible", 100.0, -1),
            Triple.of("Wr Wrk Inst", 100.0, -1),
            Triple.of("System Title", 300.0, -1),
            Triple.of("RH Account #", 150.0, -1),
            Triple.of("RH Name", 300.0, -1),
            Triple.of("TOU", 120.0, -1),
            Triple.of("Created Date", 100.0, -1),
            Triple.of("Updated Date", 100.0, -1),
            Triple.of("Manual Upload Flag", 150.0, -1)));
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(1, layout.getExpandRatio(grid), 0);
        verify(controller, ForeignSecurityUtils.class, streamSource);
    }

    private void verifyButtonsLayout(HorizontalLayout layout, boolean... buttonsVisibility) {
        verifyMenuBar(layout.getComponent(0), "Grant Set", buttonsVisibility[0], List.of("Create", "View"));
        verifyButton(layout.getComponent(1), "Upload", buttonsVisibility[1]);
        verifyButton(layout.getComponent(2), "Edit", buttonsVisibility[2]);
        verifyButton(layout.getComponent(3), "Export", buttonsVisibility[3]);
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

    private void setOthersExpectations() {
        setPermissionsExpectations(false, false);
    }

    private void setPermissionsExpectations(boolean isSpecialist, boolean isManager) {
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andStubReturn(isSpecialist);
        expect(ForeignSecurityUtils.hasManagerPermission()).andStubReturn(isManager);
    }

    private HorizontalLayout getButtonsLayout() {
        return (HorizontalLayout) ((VerticalLayout) aclGrantDetailWidget.getSecondComponent()).getComponent(0);
    }

    private AclGrantDetailDto buildAclGrantDetailDto() {
        AclGrantDetailDto grantDetail = new AclGrantDetailDto();
        grantDetail.setLicenseType("ACL");
        grantDetail.setGrantSetName("Grant set name");
        grantDetail.setTypeOfUseStatus("Print&Digital");
        grantDetail.setGrantStatus("GRANT");
        grantDetail.setEligible(false);
        grantDetail.setWrWrkInst(122857215L);
        grantDetail.setSystemTitle("Applied catalysis");
        grantDetail.setRhAccountNumber(1000009641L);
        grantDetail.setRhName("Elsevier Science & Technology Journals");
        grantDetail.setTypeOfUse("PRINT");
        grantDetail.setCreateDate(Date.from(LocalDate.of(2022, 8, 31).atStartOfDay(
            ZoneId.systemDefault()).toInstant()));
        grantDetail.setUpdateDate(grantDetail.getCreateDate());
        grantDetail.setGrantPeriod(202212);
        grantDetail.setManualUploadFlag(false);
        return grantDetail;
    }
}
