package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifySearchWidget;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link ViewUsageBatchWindow}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/27/2019
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class ViewUsageBatchWindowTest {

    private static final String USAGE_BATCH_ID = "410ee825-6a6b-422b-8d27-57950e70d810";

    private Grid<UsageBatch> grid;
    private ViewUsageBatchWindow window;
    private IFasUsageController controller;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IFasUsageController.class);
        grid = createMock(Grid.class);
        expect(ForeignSecurityUtils.hasDeleteUsagePermission()).andReturn(true).once();
        expect(controller.getSelectedProductFamily()).andReturn("FAS").once();
        expect(controller.getUsageBatches("FAS")).andReturn(List.of(buildUsageBatch())).once();
        replay(ForeignSecurityUtils.class, controller);
        window = new ViewUsageBatchWindow(controller);
        Whitebox.setInternalState(window, grid);
        verify(ForeignSecurityUtils.class, controller);
        reset(ForeignSecurityUtils.class, controller);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testStructure() {
        verifyWindow(window, "View Usage Batch", "1150px", "550px", Unit.PIXELS, true);
        var content = (VerticalLayout) getDialogContent(window);
        assertEquals(2, content.getComponentCount());
        verifySearchWidget(content.getComponentAt(0),
            "Enter Batch Name or Payment Date (mm/dd/yyyy) or Source RRO Name/Account #");
        verifyGrid((Grid) content.getComponentAt(1), List.of(
            Pair.of("Usage Batch Name", "200px"),
            Pair.of("RRO Account #", "160px"),
            Pair.of("RRO Name", "300px"),
            Pair.of("Payment Date", "145px"),
            Pair.of("Fiscal Year", "130px"),
            Pair.of("Batch Amt in USD", "170px"),
            Pair.of("Created By", "330px"),
            Pair.of("Created Date", "150px")
        ));
        verifyButtonsLayout(getFooterLayout(window), true, "Delete", "Close");
    }

    @Test
    public void testGridValues() {
        List<UsageBatch> expectedItems = List.of(buildUsageBatch());
        Object[][] expectedCells = {{
            "FAS batch", "1000000008", "ProLitteris", "09/12/2022", "FY2022", "5000.00", "user@copyright.com",
            "09/01/2022 12:00 AM"
        }};
        verifyGridItems((Grid<?>) ((VerticalLayout) getDialogContent(window)).getComponentAt(1),
            expectedItems, expectedCells);
    }

    @Test
    public void testDeleteClickListenerAssociatedFunds() {
        mockStatic(Windows.class);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(anyString()))
            .andReturn(List.of("Batch 1", "Batch 2")).once();
        Windows.showNotificationWindow("Usage batch cannot be deleted because it is associated with the following " +
            "additional funds:<ul><li>Batch 1</li><li>Batch 2</li></ul>");
        expectLastCall().once();
        replay(Windows.class, grid, controller);
        getDeleteButton().click();
        verify(Windows.class, grid, controller);
    }

    @Test
    public void testDeleteClickListenerWithAssociatedScenarios() {
        mockStatic(Windows.class);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(List.of("Scenario 1", "Scenario 2")).once();
        Windows.showNotificationWindow("Usage batch cannot be deleted because it is associated with the following " +
            "scenarios:<ul><li>Scenario 1</li><li>Scenario 2</li></ul>");
        expectLastCall().once();
        replay(Windows.class, grid, controller);
        getDeleteButton().click();
        verify(Windows.class, grid, controller);
    }

    @Test
    public void testDeleteClickListenerInProgressBatch() {
        mockStatic(Windows.class);
        Dialog confirmWindow = createMock(Dialog.class);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.isBatchProcessingCompleted(USAGE_BATCH_ID)).andReturn(false).once();
        Windows.showNotificationWindow("'FAS batch' batch cannot be deleted because processing is not completed yet");
        expectLastCall().once();
        replay(Windows.class, grid, controller, confirmWindow);
        getDeleteButton().click();
        verify(Windows.class, grid, controller, confirmWindow);
    }

    @Test
    public void testDeleteClickListenerEmptyAssociatedScenarios() {
        mockStatic(Windows.class);
        Dialog confirmWindow = createMock(Dialog.class);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.isBatchProcessingCompleted(USAGE_BATCH_ID)).andReturn(true).once();
        expect(Windows.showConfirmDialog(eq("Are you sure you want to delete <i><b>'FAS batch'</b></i> usage batch?"),
            anyObject())).andReturn(confirmWindow).once();
        replay(Windows.class, grid, controller, confirmWindow);
        getDeleteButton().click();
        verify(Windows.class, grid, controller, confirmWindow);
    }

    @Test
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(window, searchWidget);
        expect(grid.getDataProvider()).andReturn(new ListDataProvider(List.of())).once();
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        replay(searchWidget, grid);
        window.performSearch();
        verify(searchWidget, grid);
    }

    private Button getDeleteButton() {
        return (Button) getFooterLayout(window).getComponentAt(0);
    }

    private UsageBatch buildUsageBatch() {
        var usageBatch = new UsageBatch();
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
        var rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
