package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyMenuBar;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.vaadin.data.provider.CallbackDataProvider;
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

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
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
        verifyWidgetStructure(NoSelectionModel.class, false, false, true);
    }

    @Test
    public void testWidgetStructureForViewOnly() {
        setViewOnlyExpectations();
        verifyWidgetStructure(NoSelectionModel.class, false, false,true);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testEditButtonClickListenerForbiddenUsageNotEditable() {
        setSpecialistExpectations();
        mockStatic(Windows.class);
        AclUsageDto aclUsageDtoFirst = new AclUsageDto();
        aclUsageDtoFirst.setId("5b5526c7-0af0-4895-b3b5-cb5a555d3375");
        aclUsageDtoFirst.setEditable(false);
        AclUsageDto aclUsageDtoSecond = new AclUsageDto();
        aclUsageDtoSecond.setId("e27fabb0-89b1-40af-9e77-e32adfc72816");
        aclUsageDtoSecond.setEditable(true);
        Set<AclUsageDto> aclUsages = Sets.newHashSet(aclUsageDtoFirst, aclUsageDtoSecond);
        Windows.showNotificationWindow("One of selected usages is not editable");
        expectLastCall().once();
        replay(controller, streamSource, Windows.class, ForeignSecurityUtils.class);
        initWidget();
        Grid<AclUsageDto> grid =
            (Grid<AclUsageDto>) ((VerticalLayout) aclUsageWidget.getSecondComponent()).getComponent(1);
        grid.setItems(aclUsages);
        grid.select(aclUsageDtoFirst);
        grid.select(aclUsageDtoSecond);
        Button editButton = (Button) getButtonsLayout().getComponent(1);
        editButton.click();
        verify(controller, streamSource, Windows.class, ForeignSecurityUtils.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectAllCheckBoxVisible() {
        mockStatic(JavaScript.class);
        setSpecialistExpectations();
        List<AclUsageDto> aclUsageDtos = ImmutableList.of(new AclUsageDto(), new AclUsageDto());
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, 2, Collections.emptyList())).andReturn(aclUsageDtos).once();
        expect(controller.getBeansCount()).andReturn(aclUsageDtos.size()).once();
        expect(controller.getRecordThreshold()).andReturn(RECORD_THRESHOLD).once();
        replay(controller, streamSource, ForeignSecurityUtils.class, JavaScript.class);
        initWidget();
        Grid<AclUsageDto> grid =
            (Grid<AclUsageDto>) ((VerticalLayout) aclUsageWidget.getSecondComponent()).getComponent(1);
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(aclUsageDtos, dataProvider.fetch(new Query<>(0, 2, Collections.emptyList(), null,
            null)).collect(Collectors.toList()));
        assertEquals(aclUsageDtos.size(), dataProvider.size(new Query<>()));
        assertTrue(grid.getSelectionModel() instanceof MultiSelectionModelImpl);
        assertTrue(((MultiSelectionModelImpl<?>) grid.getSelectionModel()).isSelectAllCheckBoxVisible());
        verify(controller, streamSource, ForeignSecurityUtils.class, JavaScript.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectAllCheckBoxNotVisible() {
        mockStatic(JavaScript.class);
        setSpecialistExpectations();
        List<AclUsageDto> aclUsageDtos = ImmutableList.of(new AclUsageDto(), new AclUsageDto());
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, 2, Collections.emptyList())).andReturn(aclUsageDtos).once();
        expect(controller.getBeansCount()).andReturn(EXCEEDED_RECORD_THRESHOLD).once();
        expect(controller.getRecordThreshold()).andReturn(RECORD_THRESHOLD).once();
        replay(controller, streamSource, ForeignSecurityUtils.class, JavaScript.class);
        initWidget();
        Grid<AclUsageDto> grid =
            (Grid<AclUsageDto>) ((VerticalLayout) aclUsageWidget.getSecondComponent()).getComponent(1);
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(aclUsageDtos, dataProvider.fetch(new Query<>(0, 2, Collections.emptyList(), null,
            null)).collect(Collectors.toList()));
        assertEquals(EXCEEDED_RECORD_THRESHOLD, dataProvider.size(new Query<>()));
        assertTrue(grid.getSelectionModel() instanceof MultiSelectionModelImpl);
        assertFalse(((MultiSelectionModelImpl<?>) grid.getSelectionModel()).isSelectAllCheckBoxVisible());
        verify(controller, streamSource, ForeignSecurityUtils.class, JavaScript.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testSelectAllCheckBoxNotVisibleWhenGridEmpty() {
        mockStatic(JavaScript.class);
        setSpecialistExpectations();
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(controller.loadBeans(0, 2, Collections.emptyList())).andReturn(Collections.emptyList()).once();
        expect(controller.getBeansCount()).andReturn(0).once();
        replay(controller, streamSource, ForeignSecurityUtils.class, JavaScript.class);
        initWidget();
        Grid<AclUsageDto> grid =
            (Grid<AclUsageDto>) ((VerticalLayout) aclUsageWidget.getSecondComponent()).getComponent(1);
        CallbackDataProvider<?, ?> dataProvider = (CallbackDataProvider) grid.getDataProvider();
        assertEquals(Collections.emptyList(), dataProvider.fetch(new Query<>(0, 2, Collections.emptyList(), null,
            null)).collect(Collectors.toList()));
        assertEquals(0, dataProvider.size(new Query<>()));
        assertTrue(grid.getSelectionModel() instanceof MultiSelectionModelImpl);
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

    private void verifyWidgetStructure(Class<?> selectionModel, boolean... buttonsVisibility) {
        replay(ForeignSecurityUtils.class, controller, streamSource);
        initWidget();
        assertTrue(aclUsageWidget.isLocked());
        assertEquals(270, aclUsageWidget.getSplitPosition(), 0);
        verifyWindow(aclUsageWidget, null, 100, 100, Unit.PERCENTAGE);
        assertTrue(aclUsageWidget.getFirstComponent() instanceof VerticalLayout);
        Component secondComponent = aclUsageWidget.getSecondComponent();
        assertTrue(secondComponent instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) secondComponent;
        verifyWindow(layout, null, 100, 100, Unit.PERCENTAGE);
        assertEquals(2, layout.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) layout.getComponent(0), buttonsVisibility);
        Grid grid = (Grid) layout.getComponent(1);
        assertEquals(selectionModel, grid.getSelectionModel().getClass());
        verifyGrid(grid, Arrays.asList(
            Triple.of("Detail ID", 200.0, -1),
            Triple.of("Period", 100.0, -1),
            Triple.of("Usage Origin", 100.0, -1),
            Triple.of("Channel", 100.0, -1),
            Triple.of("Usage Detail ID", 130.0, -1),
            Triple.of("Wr Wrk Inst", 100.0, -1),
            Triple.of("System Title", 200.0, -1),
            Triple.of("Det LC ID", 100.0, -1),
            Triple.of("Det LC Name", 100.0, -1),
            Triple.of("Agg LC ID", 100.0, -1),
            Triple.of("Agg LC Name", 100.0, -1),
            Triple.of("Survey Country", 120.0, -1),
            Triple.of("Pub Type", 150.0, -1),
            Triple.of("Content Unit Price", 200.0, -1),
            Triple.of("TOU", 120.0, -1),
            Triple.of("Annualized Copies", 130.0, -1),
            Triple.of("Updated By", 150.0, -1),
            Triple.of("Updated Date", 110.0, -1)));
        verifyGridFooter(grid);
        assertEquals(1, layout.getExpandRatio(grid), 0);
        verify(ForeignSecurityUtils.class, controller, streamSource);
    }

    private void verifyButtonsLayout(HorizontalLayout layout, boolean... buttonsVisibility) {
        verifyMenuBar(layout.getComponent(0), "Usage Batch", buttonsVisibility[0], Collections.singletonList("Create"));
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
        return (HorizontalLayout) ((VerticalLayout)aclUsageWidget.getSecondComponent()).getComponent(0);
    }

    private void initWidget() {
        aclUsageWidget = new AclUsageWidget();
        aclUsageWidget.setController(controller);
        aclUsageWidget.init();
        aclUsageWidget.initMediator().applyPermissions();
    }
}
