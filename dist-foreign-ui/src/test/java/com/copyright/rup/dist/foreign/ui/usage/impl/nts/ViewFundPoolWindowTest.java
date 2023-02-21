package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

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
import com.copyright.rup.dist.foreign.domain.UsageBatch.NtsFields;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.INtsUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
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
 * Verifies {@link ViewFundPoolWindow}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/27/19
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class})
public class ViewFundPoolWindowTest {

    private static final String USAGE_BATCH_ID = "34874581-347e-44ec-99be-721e586ec80d";
    private static final String UNCHECKED = "unchecked";

    private Grid<UsageBatch> grid;
    private ViewFundPoolWindow viewFundPoolWindow;
    private INtsUsageController controller;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(INtsUsageController.class);
        grid = createMock(Grid.class);
        expect(ForeignSecurityUtils.hasDeleteUsagePermission()).andReturn(true).once();
        expect(controller.getSelectedProductFamily()).andReturn("NTS").once();
        expect(controller.getUsageBatches("NTS")).andReturn(List.of(buildUsageBatch())).once();
        replay(controller, ForeignSecurityUtils.class);
        viewFundPoolWindow = new ViewFundPoolWindow(controller);
        Whitebox.setInternalState(viewFundPoolWindow, "grid", grid);
        verify(controller, ForeignSecurityUtils.class);
        reset(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testStructure() {
        verifyWindow(viewFundPoolWindow, "View Fund Pool", 1000, 550, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) viewFundPoolWindow.getContent();
        assertEquals(3, content.getComponentCount());
        assertEquals(SearchWidget.class, content.getComponent(0).getClass());
        Component component = content.getComponent(1);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component, List.of(
            Triple.of("Fund Pool Name", 150.0, -1),
            Triple.of("RRO Account #", 120.0, -1),
            Triple.of("RRO Name", 150.0, -1),
            Triple.of("Payment Date", 100.0, -1),
            Triple.of("Fiscal Year", 90.0, -1),
            Triple.of("STM Amount", 100.0, -1),
            Triple.of("Non-STM Amount", 115.0, -1),
            Triple.of("STM Minimum Amount", 140.0, -1),
            Triple.of("Non-STM Minimum Amount", 160.0, -1),
            Triple.of("Market(s)", 140.0, -1),
            Triple.of("Market Period From", 140.0, -1),
            Triple.of("Market Period To", 125.0, -1),
            Triple.of("Created By", 170.0, -1),
            Triple.of("Created Date", -1.0, -1)
        ));
        assertEquals(1, content.getExpandRatio(component), 0);
        Grid.Column createDateColumn = ((Grid<?>) component).getColumns().get(13);
        assertNotNull(createDateColumn.getComparator(SortDirection.ASCENDING));
        verifyButtonsLayout(content.getComponent(2), "Delete", "Close");
    }

    @Test
    public void testGridValues() {
        Grid<?> usageBatchGrid = (Grid<?>) ((VerticalLayout) viewFundPoolWindow.getContent()).getComponent(1);
        Object[][] expectedCells = {
            {"NTS batch", 1000000008L, "ProLitteris", LocalDate.of(2022, 9, 12), "FY2022", new BigDecimal("50000"),
                new BigDecimal("500000"), new BigDecimal("50"), new BigDecimal("7"), "Bus", 2000, 2010,
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
            .andReturn(List.of("Fund 1", "Fund 2")).once();
        Windows.showNotificationWindow("Fund pool cannot be deleted because it is associated with the following " +
            "additional funds:<ul><li>Fund 1</li><li>Fund 2</li></ul>");
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
        Windows.showNotificationWindow("Fund pool cannot be deleted because it is associated with the following " +
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
        Windows.showNotificationWindow("'NTS batch' batch cannot be deleted because processing is not completed yet");
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
        expect(Windows.showConfirmDialog(eq("Are you sure you want to delete <i><b>'NTS batch'</b></i> fund pool?"),
            anyObject())).andReturn(confirmWindowCapture).once();
        replay(controller, confirmWindowCapture, grid, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, grid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(viewFundPoolWindow, searchWidget);
        expect(grid.getDataProvider()).andReturn(new ListDataProvider(List.of())).once();
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        grid.recalculateColumnWidths();
        expectLastCall().once();
        replay(searchWidget, grid);
        viewFundPoolWindow.performSearch();
        verify(searchWidget, grid);
    }

    private Button.ClickListener getDeleteButtonClickListener() {
        VerticalLayout content = (VerticalLayout) viewFundPoolWindow.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        Collection<?> listeners = deleteButton.getListeners(Button.ClickEvent.class);
        assertEquals(2, CollectionUtils.size(listeners));
        return (Button.ClickListener) listeners.iterator().next();
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(USAGE_BATCH_ID);
        usageBatch.setName("NTS batch");
        usageBatch.setRro(buildRightsholder(1000000008L, "ProLitteris"));
        usageBatch.setPaymentDate(LocalDate.of(2022, 9, 12));
        usageBatch.setFiscalYear(2022);
        usageBatch.setNtsFields(buildNtsFields());
        usageBatch.setCreateUser("user@copyright.com");
        usageBatch.setCreateDate(Date.from(LocalDate.of(2022, 9, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return usageBatch;
    }

    private NtsFields buildNtsFields() {
        NtsFields ntsFields = new NtsFields();
        ntsFields.setStmAmount(new BigDecimal("50000"));
        ntsFields.setNonStmAmount(new BigDecimal("500000"));
        ntsFields.setStmMinimumAmount(new BigDecimal("50"));
        ntsFields.setNonStmMinimumAmount(new BigDecimal("7"));
        ntsFields.setMarkets(Set.of("Bus"));
        ntsFields.setFundPoolPeriodFrom(2000);
        ntsFields.setFundPoolPeriodTo(2010);
        return ntsFields;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
