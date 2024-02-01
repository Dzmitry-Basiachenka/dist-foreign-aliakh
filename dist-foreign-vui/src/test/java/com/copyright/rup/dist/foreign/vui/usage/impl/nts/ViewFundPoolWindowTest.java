package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWidth;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
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
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
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
 * Verifies {@link ViewFundPoolWindow}.
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
public class ViewFundPoolWindowTest {

    private static final String USAGE_BATCH_ID = "34874581-347e-44ec-99be-721e586ec80d";

    private Grid<UsageBatch> grid;
    private ViewFundPoolWindow window;
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
        window = new ViewFundPoolWindow(controller);
        Whitebox.setInternalState(window, grid);
        verify(controller, ForeignSecurityUtils.class);
        reset(controller, ForeignSecurityUtils.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testStructure() {
        verifyWindow(window, "View Fund Pool", "1150px", "550px", Unit.PIXELS, true);
        VerticalLayout content = (VerticalLayout) getDialogContent(window);
        assertEquals(2, content.getComponentCount());
        verifySearchWidget(content.getComponentAt(0));
        verifyGrid((Grid) content.getComponentAt(1), List.of(
            Pair.of("Fund Pool Name", "150px"),
            Pair.of("RRO Account #", "120px"),
            Pair.of("RRO Name", "150px"),
            Pair.of("Payment Date", "100px"),
            Pair.of("Fiscal Year", "90px"),
            Pair.of("STM Amount", "100px"),
            Pair.of("Non-STM Amount", "115px"),
            Pair.of("STM Minimum Amount", "140px"),
            Pair.of("Non-STM Minimum Amount", "160px"),
            Pair.of("Market(s)", "140px"),
            Pair.of("Market Period From", "140px"),
            Pair.of("Market Period To", "125px"),
            Pair.of("Created By", "170px"),
            Pair.of("Created Date", null)
        ));
        verifyButtonsLayout(getFooterLayout(window), true, "Delete", "Close");
    }

    @Test
    public void testGridValues() {
        List<UsageBatch> expectedItems = List.of(buildUsageBatch());
        Object[][] expectedCells = {{
            "NTS batch", "1000000008", "ProLitteris", "09/12/2022", "FY2022", "50000", "500000", "50", "7", "Bus",
            "2000", "2010", "user@copyright.com", "09/01/2022 12:00 AM"
        }};
        verifyGridItems((Grid<?>) ((VerticalLayout) getDialogContent(window)).getComponentAt(1),
            expectedItems, expectedCells);
    }

    @Test
    public void testDeleteClickListenerAssociatedFunds() {
        mockStatic(Windows.class);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(anyString()))
            .andReturn(List.of("Fund 1", "Fund 2")).once();
        Windows.showNotificationWindow("Fund pool cannot be deleted because it is associated with the following " +
            "additional funds:<ul><li>Fund 1</li><li>Fund 2</li></ul>");
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
        Windows.showNotificationWindow("Fund pool cannot be deleted because it is associated with the following " +
            "scenarios:<ul><li>Scenario 1</li><li>Scenario 2</li></ul>");
        expectLastCall().once();
        replay(Windows.class, grid, controller);
        getDeleteButton().click();
        verify(Windows.class, grid, controller);
    }

    @Test
    public void testDeleteClickListenerInProgressBatch() {
        mockStatic(Windows.class);
        Dialog confirmWindowCapture = createMock(Dialog.class);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.isBatchProcessingCompleted(USAGE_BATCH_ID)).andReturn(false).once();
        Windows.showNotificationWindow("'NTS batch' batch cannot be deleted because processing is not completed yet");
        expectLastCall().once();
        replay(Windows.class, grid, controller, confirmWindowCapture);
        getDeleteButton().click();
        verify(Windows.class, grid, controller, confirmWindowCapture);
    }

    @Test
    public void testDeleteClickListenerEmptyAssociatedScenarios() {
        mockStatic(Windows.class);
        Dialog confirmWindowCapture = createMock(Dialog.class);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.isBatchProcessingCompleted(USAGE_BATCH_ID)).andReturn(true).once();
        expect(Windows.showConfirmDialog(eq("Are you sure you want to delete <i><b>'NTS batch'</b></i> fund pool?"),
            anyObject())).andReturn(confirmWindowCapture).once();
        replay(Windows.class, grid, controller, confirmWindowCapture);
        getDeleteButton().click();
        verify(Windows.class, grid, controller, confirmWindowCapture);
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

    private void verifySearchWidget(Component component) {
        assertThat(component, instanceOf(SearchWidget.class));
        var searchWidget = (SearchWidget) component;
        var textField = Whitebox.getInternalState(searchWidget, TextField.class);
        verifyWidth(textField, "70%", Unit.PERCENTAGE);
        assertEquals("Enter Fund Pool Name or Payment Date (mm/dd/yyyy) or Source RRO Name/Account #",
            textField.getPlaceholder());
    }

    private Button getDeleteButton() {
        return (Button) getFooterLayout(window).getComponentAt(0);
    }

    private UsageBatch buildUsageBatch() {
        var usageBatch = new UsageBatch();
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

    private UsageBatch.NtsFields buildNtsFields() {
        var ntsFields = new UsageBatch.NtsFields();
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
        var rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
