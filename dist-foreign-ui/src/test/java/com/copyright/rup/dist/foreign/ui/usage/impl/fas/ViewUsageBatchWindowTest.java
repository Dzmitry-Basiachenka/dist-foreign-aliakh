package com.copyright.rup.dist.foreign.ui.usage.impl.fas;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.fas.IFasUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections.CollectionUtils;
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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link ViewUsageBatchWindow}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/27/19
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class})
public class ViewUsageBatchWindowTest {

    private static final String USAGE_BATCH_ID = "410ee825-6a6b-422b-8d27-57950e70d810";
    private static final String UNCHECKED = "unchecked";

    private Grid<UsageBatch> grid;
    private ViewUsageBatchWindow viewUsageBatchWindow;
    private IFasUsageController controller;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IFasUsageController.class);
        grid = createMock(Grid.class);
        expect(ForeignSecurityUtils.hasDeleteUsagePermission()).andReturn(true).once();
        expect(controller.getSelectedProductFamily()).andReturn("FAS").once();
        expect(controller.getUsageBatches("FAS")).andReturn(List.of(buildUsageBatch())).once();
        replay(controller, ForeignSecurityUtils.class);
        viewUsageBatchWindow = new ViewUsageBatchWindow(controller);
        Whitebox.setInternalState(viewUsageBatchWindow, "grid", grid);
        verify(controller, ForeignSecurityUtils.class);
        reset(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testStructure() {
        verifyWindow(viewUsageBatchWindow, "View Usage Batch", 1000, 550, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) viewUsageBatchWindow.getContent();
        assertEquals(3, content.getComponentCount());
        assertEquals(SearchWidget.class, content.getComponent(0).getClass());
        Grid componentGrid = (Grid) content.getComponent(1);
        verifyGrid(componentGrid, List.of(
            Triple.of("Usage Batch Name", 150.0, -1),
            Triple.of("RRO Account #", 120.0, -1),
            Triple.of("RRO Name", 150.0, -1),
            Triple.of("Payment Date", 100.0, -1),
            Triple.of("Fiscal Year", 90.0, -1),
            Triple.of("Batch Amt in USD", 130.0, -1),
            Triple.of("Created By", 170.0, -1),
            Triple.of("Created Date", -1.0, -1)
        ));
        Grid.Column createDateColumn = (Column) componentGrid.getColumns().get(7);
        assertNotNull(createDateColumn.getComparator(SortDirection.ASCENDING));
        assertEquals(1, content.getExpandRatio(componentGrid), 0);
        verifyButtonsLayout(content.getComponent(2), "Delete", "Close");
    }

    @Test
    public void testGridValues() {
        Grid<?> usageBatchGrid = (Grid<?>) ((VerticalLayout) viewUsageBatchWindow.getContent()).getComponent(1);
        Object[][] expectedCells = {
            {"FAS batch", 1000000008L, "ProLitteris", LocalDate.of(2022, 9, 12), "FY2022", new BigDecimal("5000.00"),
                "user@copyright.com", "09/01/2022 12:00 AM"}
        };
        verifyGridItems(usageBatchGrid, List.of(buildUsageBatch()), expectedCells);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteClickListenerAssociatedFunds() {
        mockStatic(Windows.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(anyString()))
            .andReturn(List.of("Batch 1", "Batch 2")).once();
        Windows.showNotificationWindow("Usage batch cannot be deleted because it is associated with the following " +
            "additional funds:<ul><li>Batch 1</li><li>Batch 2</li></ul>");
        expectLastCall().once();
        replay(controller, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteClickListenerWithAssociatedScenarios() {
        mockStatic(Windows.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(List.of("Scenario 1", "Scenario 2")).once();
        Windows.showNotificationWindow("Usage batch cannot be deleted because it is associated with the following " +
            "scenarios:<ul><li>Scenario 1</li><li>Scenario 2</li></ul>");
        expectLastCall().once();
        replay(controller, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteClickListenerInProgressBatch() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.isBatchProcessingCompleted(USAGE_BATCH_ID)).andReturn(false).once();
        Windows.showNotificationWindow("'FAS batch' batch cannot be deleted because processing is not completed yet");
        expectLastCall().once();
        replay(controller, confirmWindowCapture, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteClickListenerEmptyAssociatedScenarios() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.isBatchProcessingCompleted(USAGE_BATCH_ID)).andReturn(true).once();
        expect(Windows.showConfirmDialog(eq("Are you sure you want to delete <i><b>'FAS batch'</b></i> usage batch?"),
            anyObject())).andReturn(confirmWindowCapture).once();
        replay(controller, confirmWindowCapture, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(viewUsageBatchWindow, searchWidget);
        expect(grid.getDataProvider()).andReturn(new ListDataProvider(List.of())).once();
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        grid.recalculateColumnWidths();
        expectLastCall().once();
        replay(searchWidget, grid);
        viewUsageBatchWindow.performSearch();
        verify(searchWidget, grid);
    }

    private Button.ClickListener getDeleteButtonClickListener() {
        VerticalLayout content = (VerticalLayout) viewUsageBatchWindow.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        Collection<?> listeners = deleteButton.getListeners(Button.ClickEvent.class);
        assertEquals(2, CollectionUtils.size(listeners));
        return (Button.ClickListener) listeners.iterator().next();
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(USAGE_BATCH_ID);
        usageBatch.setName("FAS batch");
        usageBatch.setRro(buildRightsholder(1000000008L, "ProLitteris"));
        usageBatch.setPaymentDate(LocalDate.of(2022, 9, 12));
        usageBatch.setFiscalYear(2022);
        usageBatch.setGrossAmount(new BigDecimal("5000.00"));
        usageBatch.setCreateUser("user@copyright.com");
        usageBatch.setCreateDate(Date.from(LocalDate.of(2022, 9, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return usageBatch;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
