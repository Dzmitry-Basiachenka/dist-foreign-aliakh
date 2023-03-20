package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyFooterItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyMenuBar;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.components.grid.MultiSelectionModelImpl;
import com.vaadin.ui.components.grid.NoSelectionModel;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Verifies {@link AclUsageWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/30/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ForeignSecurityUtils.class, Windows.class, JavaScript.class})
public class AclUsageWidgetTest {

    private static final String UNCHECKED = "unchecked";
    private static final int RECORD_THRESHOLD = 10000;
    private static final int EXCEEDED_RECORD_THRESHOLD = 10001;
    private AclUsageWidget aclUsageWidget;
    private IAclUsageController controller;
    private IStreamSource streamSource;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IAclUsageController.class);
        streamSource = createMock(IStreamSource.class);
        AclUsageFilterWidget filterWidget = new AclUsageFilterWidget(createMock(IAclUsageFilterController.class));
        expect(controller.initAclUsageFilterWidget()).andReturn(filterWidget).once();
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
        expect(controller.getExportAclUsagesStreamSource()).andReturn(streamSource).once();
    }

    @Test
    public void testWidgetStructureForSpecialist() {
        setSpecialistExpectations();
        verifyWidgetStructure(MultiSelectionModelImpl.class, true, true, true);
    }

    @Test
    public void testWidgetStructureForManager() {
        setManagerExpectations();
        verifyWidgetStructure(NoSelectionModel.class, true, false, true);
    }

    @Test
    public void testWidgetStructureForViewOnly() {
        setViewOnlyExpectations();
        verifyWidgetStructure(NoSelectionModel.class, true, false, true);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testEditButtonClickListenerForbiddenUsageNotEditable() {
        setSpecialistExpectations();
        mockStatic(Windows.class);
        AclUsageDto aclUsageDto1 = new AclUsageDto();
        aclUsageDto1.setId("5b5526c7-0af0-4895-b3b5-cb5a555d3375");
        aclUsageDto1.setEditable(false);
        AclUsageDto aclUsageDto2 = new AclUsageDto();
        aclUsageDto2.setId("e27fabb0-89b1-40af-9e77-e32adfc72816");
        aclUsageDto2.setEditable(true);
        Set<AclUsageDto> aclUsages = Set.of(aclUsageDto1, aclUsageDto2);
        Windows.showNotificationWindow("One of selected usages is not editable");
        expectLastCall().once();
        replay(controller, streamSource, Windows.class, ForeignSecurityUtils.class);
        initWidget();
        Grid<AclUsageDto> grid =
            (Grid<AclUsageDto>) ((VerticalLayout) aclUsageWidget.getSecondComponent()).getComponent(1);
        grid.setItems(aclUsages);
        grid.select(aclUsageDto1);
        grid.select(aclUsageDto2);
        Button editButton = (Button) getButtonsLayout().getComponent(1);
        editButton.click();
        verify(controller, streamSource, Windows.class, ForeignSecurityUtils.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectAllCheckBoxVisible() {
        mockStatic(JavaScript.class);
        setSpecialistExpectations();
        List<AclUsageDto> aclUsageDtos = List.of(new AclUsageDto(), new AclUsageDto());
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, 2, List.of())).andReturn(aclUsageDtos).once();
        expect(controller.getBeansCount()).andReturn(aclUsageDtos.size()).once();
        expect(controller.getRecordThreshold()).andReturn(RECORD_THRESHOLD).once();
        replay(controller, streamSource, ForeignSecurityUtils.class, JavaScript.class);
        initWidget();
        Grid<AclUsageDto> grid =
            (Grid<AclUsageDto>) ((VerticalLayout) aclUsageWidget.getSecondComponent()).getComponent(1);
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(aclUsageDtos, dataProvider.fetch(new Query<>(0, 2, List.of(), null,
            null)).collect(Collectors.toList()));
        assertEquals(aclUsageDtos.size(), dataProvider.size(new Query<>()));
        assertThat(grid.getSelectionModel(), instanceOf(MultiSelectionModelImpl.class));
        assertTrue(((MultiSelectionModelImpl<?>) grid.getSelectionModel()).isSelectAllCheckBoxVisible());
        verify(controller, streamSource, ForeignSecurityUtils.class, JavaScript.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectAllCheckBoxNotVisible() {
        mockStatic(JavaScript.class);
        setSpecialistExpectations();
        List<AclUsageDto> aclUsageDtos = List.of(new AclUsageDto(), new AclUsageDto());
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, 2, List.of())).andReturn(aclUsageDtos).once();
        expect(controller.getBeansCount()).andReturn(EXCEEDED_RECORD_THRESHOLD).once();
        expect(controller.getRecordThreshold()).andReturn(RECORD_THRESHOLD).once();
        replay(controller, streamSource, ForeignSecurityUtils.class, JavaScript.class);
        initWidget();
        Grid<AclUsageDto> grid =
            (Grid<AclUsageDto>) ((VerticalLayout) aclUsageWidget.getSecondComponent()).getComponent(1);
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(aclUsageDtos, dataProvider.fetch(new Query<>(0, 2, List.of(), null,
            null)).collect(Collectors.toList()));
        assertEquals(EXCEEDED_RECORD_THRESHOLD, dataProvider.size(new Query<>()));
        assertThat(grid.getSelectionModel(), instanceOf(MultiSelectionModelImpl.class));
        assertFalse(((MultiSelectionModelImpl<?>) grid.getSelectionModel()).isSelectAllCheckBoxVisible());
        verify(controller, streamSource, ForeignSecurityUtils.class, JavaScript.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectAllCheckBoxNotVisibleWhenGridEmpty() {
        mockStatic(JavaScript.class);
        setSpecialistExpectations();
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, 2, List.of())).andReturn(List.of()).once();
        expect(controller.getBeansCount()).andReturn(0).once();
        replay(controller, streamSource, ForeignSecurityUtils.class, JavaScript.class);
        initWidget();
        Grid<AclUsageDto> grid =
            (Grid<AclUsageDto>) ((VerticalLayout) aclUsageWidget.getSecondComponent()).getComponent(1);
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(List.of(), dataProvider.fetch(new Query<>(0, 2, List.of(), null,
            null)).collect(Collectors.toList()));
        assertEquals(0, dataProvider.size(new Query<>()));
        assertThat(grid.getSelectionModel(), instanceOf(MultiSelectionModelImpl.class));
        assertFalse(((MultiSelectionModelImpl<?>) grid.getSelectionModel()).isSelectAllCheckBoxVisible());
        verify(controller, streamSource, ForeignSecurityUtils.class, JavaScript.class);
    }

    @Test
    public void testSelectUsage() {
        setSpecialistExpectations();
        replay(controller, streamSource, ForeignSecurityUtils.class);
        initWidget();
        AclUsageDto aclUsageDto1 = new AclUsageDto();
        AclUsageDto aclUsageDto2 = new AclUsageDto();
        Grid<AclUsageDto> aclUsagesGrid = Whitebox.getInternalState(aclUsageWidget, "aclUsagesGrid");
        MenuBar batchMenuBar = Whitebox.getInternalState(aclUsageWidget, "aclUsageBatchMenuBar");
        Button editButton = Whitebox.getInternalState(aclUsageWidget, "editButton");
        assertTrue(aclUsagesGrid.getSelectionModel().isUserSelectionAllowed());
        assertTrue(batchMenuBar.isEnabled());
        assertFalse(editButton.isEnabled());
        aclUsagesGrid.setItems(aclUsageDto1, aclUsageDto2);
        aclUsagesGrid.select(aclUsageDto1);
        assertTrue(batchMenuBar.isEnabled());
        assertTrue(editButton.isEnabled());
        verify(controller, streamSource, ForeignSecurityUtils.class);
    }

    @Test
    public void testGridValues() {
        mockStatic(JavaScript.class);
        List<AclUsageDto> usages = List.of(buildAclUsageDto());
        setSpecialistExpectations();
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.getBeansCount()).andReturn(1).once();
        expect(controller.loadBeans(0, Integer.MAX_VALUE, List.of())).andReturn(usages).once();
        expect(controller.getRecordThreshold()).andReturn(RECORD_THRESHOLD).once();
        replay(JavaScript.class, ForeignSecurityUtils.class, controller, streamSource);
        initWidget();
        Grid grid = (Grid) ((VerticalLayout) aclUsageWidget.getSecondComponent()).getComponent(1);
        DataProvider dataProvider = grid.getDataProvider();
        dataProvider.refreshAll();
        Object[][] expectedCells = {
            {"48579d64-99b7-492a-975a-93c96499417a", 202012, UdmUsageOriginEnum.SS, UdmChannelEnum.CCC, "LUBRIZ0610EML",
                122815600L, "Tribology international", 5, "Chemicals", 51, "Materials", "International", "SJ",
                "6.0000000006", "3.0000000003", "2.0000000002", "N", "EMAIL_COPY", "DIGITAL", "1.00", "Y",
                "user@copyright.com", "08/31/2022"}
        };
        verifyGridItems(grid, usages, expectedCells);
        verify(JavaScript.class, ForeignSecurityUtils.class, controller, streamSource);
        Object[][] expectedFooterColumns = {
            {"detailId", "Usages Count: 1", null},
        };
        verifyFooterItems(grid, expectedFooterColumns);
    }

    private void verifyWidgetStructure(Class<?> selectionModel, boolean... buttonsVisibility) {
        replay(ForeignSecurityUtils.class, controller, streamSource);
        initWidget();
        assertTrue(aclUsageWidget.isLocked());
        assertEquals(270, aclUsageWidget.getSplitPosition(), 0);
        verifyWindow(aclUsageWidget, null, 100, 100, Unit.PERCENTAGE);
        assertThat(aclUsageWidget.getFirstComponent(), instanceOf(VerticalLayout.class));
        Component secondComponent = aclUsageWidget.getSecondComponent();
        assertThat(secondComponent, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifyWindow(layout, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0), buttonsVisibility);
        Grid grid = (Grid) layout.getComponent(1);
        assertEquals(selectionModel, grid.getSelectionModel().getClass());
        verifyGrid(grid, List.of(
            Triple.of("Detail ID", 250.0, -1),
            Triple.of("Period", 100.0, -1),
            Triple.of("Usage Origin", 100.0, -1),
            Triple.of("Channel", 100.0, -1),
            Triple.of("Usage Detail ID", 130.0, -1),
            Triple.of("Wr Wrk Inst", 100.0, -1),
            Triple.of("System Title", 300.0, -1),
            Triple.of("Det LC ID", 100.0, -1),
            Triple.of("Det LC Name", 250.0, -1),
            Triple.of("Agg LC ID", 100.0, -1),
            Triple.of("Agg LC Name", 100.0, -1),
            Triple.of("Survey Country", 120.0, -1),
            Triple.of("Pub Type", 150.0, -1),
            Triple.of("Price", 200.0, -1),
            Triple.of("Content", 200.0, -1),
            Triple.of("Content Unit Price", 200.0, -1),
            Triple.of("CUP Flag", 90.0, -1),
            Triple.of("Reported TOU", 120.0, -1),
            Triple.of("TOU", 120.0, -1),
            Triple.of("Annualized Copies", 130.0, -1),
            Triple.of("MDWMS Deleted", 120.0, -1),
            Triple.of("Updated By", 200.0, -1),
            Triple.of("Updated Date", 110.0, -1)));
        verifyGridFooter(grid);
        assertEquals(1, layout.getExpandRatio(grid), 0);
        verify(ForeignSecurityUtils.class, controller, streamSource);
    }

    private void verifyButtonsLayout(HorizontalLayout layout, boolean... buttonsVisibility) {
        verifyMenuBar(layout.getComponent(0), "Usage Batch", buttonsVisibility[0], List.of("Create", "View"));
        verifyButton(layout.getComponent(1), "Edit", buttonsVisibility[1]);
        verifyButton(layout.getComponent(2), "Export", buttonsVisibility[2]);
    }

    private void verifyGridFooter(Grid grid) {
        assertTrue(grid.isFooterVisible());
        FooterRow footerRow = grid.getFooterRow(0);
        assertEquals("Usages Count: 0", footerRow.getCell("detailId").getText());
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
    }

    private HorizontalLayout getButtonsLayout() {
        return (HorizontalLayout) ((VerticalLayout) aclUsageWidget.getSecondComponent()).getComponent(0);
    }

    private void initWidget() {
        aclUsageWidget = new AclUsageWidget();
        aclUsageWidget.setController(controller);
        aclUsageWidget.init();
        aclUsageWidget.initMediator().applyPermissions();
    }

    private AclUsageDto buildAclUsageDto() {
        AclUsageDto usage = new AclUsageDto();
        usage.setId("48579d64-99b7-492a-975a-93c96499417a");
        usage.setPeriod(202012);
        usage.setUsageOrigin(UdmUsageOriginEnum.SS);
        usage.setChannel(UdmChannelEnum.CCC);
        usage.setOriginalDetailId("LUBRIZ0610EML");
        usage.setWrWrkInst(122815600L);
        usage.setSystemTitle("Tribology international");
        usage.setDetailLicenseeClass(buildDetailLicenseeClass());
        usage.setAggregateLicenseeClassId(51);
        usage.setAggregateLicenseeClassName("Materials");
        usage.setSurveyCountry("International");
        usage.setPublicationType(buildPublicationType());
        usage.setPrice(new BigDecimal("6.0000000006"));
        usage.setContent(new BigDecimal("3.0000000003"));
        usage.setContentUnitPrice(new BigDecimal("2.0000000002"));
        usage.setContentUnitPriceFlag(false);
        usage.setReportedTypeOfUse("EMAIL_COPY");
        usage.setTypeOfUse("DIGITAL");
        usage.setAnnualizedCopies(new BigDecimal("1.00"));
        usage.setWorkDeletedFlag(true);
        usage.setUpdateUser("user@copyright.com");
        usage.setUpdateDate(Date.from(LocalDate.of(2022, 8, 31).atStartOfDay(
            ZoneId.systemDefault()).toInstant()));
        return usage;
    }

    private DetailLicenseeClass buildDetailLicenseeClass() {
        DetailLicenseeClass detLicClass = new DetailLicenseeClass();
        detLicClass.setId(5);
        detLicClass.setDescription("Chemicals");
        return detLicClass;
    }

    private PublicationType buildPublicationType() {
        PublicationType publicationType = new PublicationType();
        publicationType.setName("SJ");
        return publicationType;
    }
}
