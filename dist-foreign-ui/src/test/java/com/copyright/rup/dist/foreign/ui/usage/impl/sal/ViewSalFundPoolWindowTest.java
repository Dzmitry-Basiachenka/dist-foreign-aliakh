package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Verifies {@link ViewSalFundPoolWindow}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/25/2020
 *
 * @author Aliaksandr Liakh
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class, ViewSalFundPoolWindow.class})
public class ViewSalFundPoolWindowTest {

    private static final String FUND_POOL_ID = "0c7cf263-26fc-4bbd-82d0-f438e8e04dda";

    private ViewSalFundPoolWindow viewSalFundPoolWindow;
    private ISalUsageController controller;
    private final FundPool fundPool = buildFundPool();

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(ISalUsageController.class);
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource())
            .andReturn(new SimpleImmutableEntry(createMock(Supplier.class), createMock(Supplier.class))).times(1);
        expect(controller.getExportFundPoolsStreamSource()).andReturn(streamSource).once();
        expect(ForeignSecurityUtils.hasDeleteFundPoolPermission()).andReturn(true).once();
        expect(controller.getFundPools()).andReturn(List.of(fundPool)).once();
        replay(controller, streamSource, ForeignSecurityUtils.class);
        viewSalFundPoolWindow = new ViewSalFundPoolWindow(controller);
        verify(controller, streamSource, ForeignSecurityUtils.class);
        reset(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testStructure() {
        assertEquals("View Fund Pool", viewSalFundPoolWindow.getCaption());
        verifySize(viewSalFundPoolWindow);
        VerticalLayout content = (VerticalLayout) viewSalFundPoolWindow.getContent();
        assertEquals(3, content.getComponentCount());
        assertEquals(SearchWidget.class, content.getComponent(0).getClass());
        Component component = content.getComponent(1);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component);
        assertEquals(1, content.getExpandRatio(component), 0);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        assertEquals(3, buttonsLayout.getComponentCount());
        verifyButtonsLayout(buttonsLayout, "Export", "Delete", "Close");
        assertEquals(1, buttonsLayout.getComponent(0).getExtensions().size());
    }

    @Test
    public void testDeleteClickListener() {
        mockStatic(Windows.class);
        Window confirmWindowMock = createMock(Window.class);
        Grid<FundPool> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewSalFundPoolWindow, "grid", grid);
        Button.ClickListener listener = getButtonClickListener(1);
        expect(grid.getSelectedItems()).andReturn(Set.of(fundPool)).once();
        expect(controller.getScenarioNameAssociatedWithFundPool(FUND_POOL_ID)).andReturn(null).once();
        expect(Windows.showConfirmDialog(
            eq("Are you sure you want to delete <i><b>'SAL Fund Pool'</b></i> fund pool?"), anyObject()))
            .andReturn(confirmWindowMock).once();
        replay(controller, confirmWindowMock, grid, Windows.class);
        listener.buttonClick(createMock(ClickEvent.class));
        verify(controller, confirmWindowMock, grid, Windows.class);
    }

    @Test
    public void testDeleteClickListenerWithAssociatedScenarios() {
        mockStatic(Windows.class);
        Grid<FundPool> grid = createMock(Grid.class);
        Whitebox.setInternalState(viewSalFundPoolWindow, "grid", grid);
        Button.ClickListener listener = getButtonClickListener(1);
        expect(grid.getSelectedItems()).andReturn(Set.of(fundPool)).once();
        expect(controller.getScenarioNameAssociatedWithFundPool(FUND_POOL_ID)).andReturn("Scenario 1").once();
        Windows.showNotificationWindow(
            eq("Fund pool cannot be deleted because it is associated with the following scenario: Scenario 1"));
        expectLastCall().once();
        replay(controller, grid, Windows.class);
        listener.buttonClick(createMock(ClickEvent.class));
        verify(controller, grid, Windows.class);
    }

    @Test
    public void testSelectionChangedListener() {
        Button deleteButton = getButton(1);
        assertFalse(deleteButton.isEnabled());
        Grid<FundPool> grid = (Grid) ((VerticalLayout) viewSalFundPoolWindow.getContent()).getComponent(1);
        grid.select(fundPool);
        assertTrue(deleteButton.isEnabled());
        grid.deselectAll();
        assertFalse(deleteButton.isEnabled());
    }

    @Test
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(viewSalFundPoolWindow, searchWidget);
        Whitebox.setInternalState(viewSalFundPoolWindow, grid);
        expect(grid.getDataProvider()).andReturn(new ListDataProvider(List.of())).once();
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        grid.recalculateColumnWidths();
        expectLastCall().once();
        replay(searchWidget, grid);
        viewSalFundPoolWindow.performSearch();
        verify(searchWidget, grid);
    }

    @Test
    public void testGridValueRendering() {
        FundPool fundPoolToRender = buildFundPool();
        VerticalLayout content = (VerticalLayout) viewSalFundPoolWindow.getContent();
        List<Grid.Column> columns = ((Grid) content.getComponent(1)).getColumns();
        assertEquals("SAL Fund Pool", columns.get(0).getValueProvider().apply(fundPoolToRender));
        assertEquals("12/24/2020", columns.get(1).getValueProvider().apply(fundPoolToRender));
        assertEquals("FY2020 COG", columns.get(2).getValueProvider().apply(fundPoolToRender));
        assertEquals(1000008985L, columns.get(3).getValueProvider().apply(fundPoolToRender));
        assertEquals("FarmField Inc.", columns.get(4).getValueProvider().apply(fundPoolToRender));
        assertEquals("1,000.00", columns.get(5).getValueProvider().apply(fundPoolToRender));
        assertEquals("25.0", columns.get(6).getValueProvider().apply(fundPoolToRender));
        assertEquals("2.5", columns.get(7).getValueProvider().apply(fundPoolToRender));
        assertEquals(10, columns.get(8).getValueProvider().apply(fundPoolToRender));
        assertEquals(5, columns.get(9).getValueProvider().apply(fundPoolToRender));
        assertEquals(0, columns.get(10).getValueProvider().apply(fundPoolToRender));
        assertEquals("25.00", columns.get(11).getValueProvider().apply(fundPoolToRender));
        assertEquals("487.49", columns.get(12).getValueProvider().apply(fundPoolToRender));
        assertEquals("243.74", columns.get(13).getValueProvider().apply(fundPoolToRender));
        assertEquals("0.00", columns.get(14).getValueProvider().apply(fundPoolToRender));
    }

    private void verifySize(Component component) {
        assertEquals(900, component.getWidth(), 0);
        assertEquals(550, component.getHeight(), 0);
        assertEquals(Sizeable.Unit.PIXELS, component.getHeightUnits());
        assertEquals(Sizeable.Unit.PIXELS, component.getWidthUnits());
    }

    private void verifyGrid(Grid grid) {
        assertNull(grid.getCaption());
        List<Grid.Column> columns = grid.getColumns();
        assertEquals(List.of("Fund Pool Name", "Date Received", "Assessment Name", "Licensee Account #",
            "Licensee Name", "Gross Amount", "Service Fee %", "Item Bank Split %",
            "Grade K-5 Number of Students", "Grade 6-8 Number of Students", "Grade 9-12 Number of Students",
            "Item Bank Gross Amount", "Grade K-5 Gross Amount", "Grade 6-8 Gross Amount", "Grade 9-12 Gross Amount"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
        assertEquals(List.of(-1.0, -1.0, 180.0, 150.0, 300.0, 170.0, 115.0, 150.0, 190.0, 190.0, 190.0, 170.0,
            170.0, 170.0, 170.0),
            columns.stream().map(Grid.Column::getWidth).collect(Collectors.toList()));
        assertEquals(List.of(1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1),
            columns.stream().map(Grid.Column::getExpandRatio).collect(Collectors.toList()));
        Grid.Column createDateColumn = columns.get(3);
        assertNotNull(createDateColumn.getComparator(SortDirection.ASCENDING));
    }

    private Button getButton(int buttonIndex) {
        VerticalLayout content = (VerticalLayout) viewSalFundPoolWindow.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        return (Button) buttonsLayout.getComponent(buttonIndex);
    }

    private Button.ClickListener getButtonClickListener(int buttonIndex) {
        Collection<?> listeners = getButton(buttonIndex).getListeners(ClickEvent.class);
        assertEquals(2, CollectionUtils.size(listeners));
        return (Button.ClickListener) listeners.iterator().next();
    }

    private FundPool buildFundPool() {
        FundPool salFundPool = new FundPool();
        FundPool.SalFields salFields = new FundPool.SalFields();
        salFields.setDateReceived(LocalDate.of(2020, 12, 24));
        salFields.setAssessmentName("FY2020 COG");
        salFields.setLicenseeAccountNumber(1000008985L);
        salFields.setLicenseeName("FarmField Inc.");
        salFields.setGradeKto5NumberOfStudents(10);
        salFields.setGrade6to8NumberOfStudents(5);
        salFields.setGrossAmount(new BigDecimal("1000.00"));
        salFields.setItemBankSplitPercent(new BigDecimal("0.02500"));
        salFields.setServiceFee(new BigDecimal("0.25000"));
        salFields.setItemBankGrossAmount(new BigDecimal("25.00"));
        salFields.setGradeKto5GrossAmount(new BigDecimal("487.49"));
        salFields.setGrade6to8GrossAmount(new BigDecimal("243.74"));
        salFields.setGrade9to12GrossAmount(new BigDecimal("0.00"));
        salFundPool.setSalFields(salFields);
        salFundPool.setId(FUND_POOL_ID);
        salFundPool.setName("SAL Fund Pool");
        return salFundPool;
    }
}
