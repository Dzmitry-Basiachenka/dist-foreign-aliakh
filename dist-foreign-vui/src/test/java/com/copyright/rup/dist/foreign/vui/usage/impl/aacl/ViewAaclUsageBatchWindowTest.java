package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder.getButton;
import static com.copyright.rup.dist.foreign.vui.IVaadinJsonConverter.assertJsonSnapshot;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifySearchWidget;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.vui.GridColumnVerifier;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link ViewAaclUsageBatchWindow}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 02/12/2020
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class, ViewAaclUsageBatchWindow.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class ViewAaclUsageBatchWindowTest {

    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final String USAGE_BATCH_ID = "ec49ea0f-af5c-407c-8696-0ece57482a20";
    private static final String USAGE_BATCH_NAME = "Usage Batch";

    private ViewAaclUsageBatchWindow window;
    private IAaclUsageController controller;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IAaclUsageController.class);
        expect(ForeignSecurityUtils.hasDeleteUsagePermission()).andReturn(true).once();
        expect(controller.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).once();
        expect(controller.getUsageBatches(AACL_PRODUCT_FAMILY)).andReturn(List.of(new UsageBatch())).once();
        replay(ForeignSecurityUtils.class, controller);
        window = new ViewAaclUsageBatchWindow(controller);
        verify(ForeignSecurityUtils.class, controller);
        reset(ForeignSecurityUtils.class, controller);
    }

    @Test
    public void testStructure() {
        replay(ForeignSecurityUtils.class, controller);
        verifyWindow(window, "View Usage Batch", "1150px", "550px", Unit.PIXELS, true);
        var content = (VerticalLayout) getDialogContent(window);
        assertEquals(2, content.getComponentCount());
        verifySearchWidget(content.getComponentAt(0), "Enter Batch Name or Period End Date (mm/dd/yyyy)");
        verifyGrid((Grid<UsageBatch>) content.getComponentAt(1));
        verifyButtonsLayout(getFooterLayout(window), true, "Delete", "Close");
    }

    @Test
    public void testJsonSnapshot() {
        assertJsonSnapshot("usage/impl/aacl/view-aacl-usage-batch-window.json", window);
    }

    @Test
    public void testDeleteClickListener() {
        mockStatic(Windows.class);
        Dialog confirmWindow = createMock(Dialog.class);
        expect(Windows.showConfirmDialog(eq("Are you sure you want to delete <i><b>'Usage Batch'</b></i> usage batch?"),
            anyObject())).andReturn(confirmWindow).once();
        Grid<UsageBatch> grid = createMock(Grid.class);
        Whitebox.setInternalState(window, "grid", grid);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.isBatchProcessingCompleted(USAGE_BATCH_ID)).andReturn(true).once();
        replay(Windows.class, confirmWindow, grid, controller);
        getDeleteButton().click();
        verify(Windows.class, confirmWindow, grid, controller);
    }

    @Test
    public void testDeleteClickListenerInProgressBatch() {
        mockStatic(Windows.class);
        Windows.showNotificationWindow("'Usage Batch' batch cannot be deleted because processing is not completed yet");
        expectLastCall().once();
        Grid<UsageBatch> grid = createMock(Grid.class);
        Whitebox.setInternalState(window, "grid", grid);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.isBatchProcessingCompleted(USAGE_BATCH_ID)).andReturn(false).once();
        replay(Windows.class, grid, controller);
        getDeleteButton().click();
        verify(Windows.class, grid, controller);
    }

    @Test
    public void testDeleteClickListenerWithAssociatedScenarios() {
        mockStatic(Windows.class);
        Windows.showNotificationWindow("Usage batch cannot be deleted because it is associated with the following " +
            "scenarios:<ul><li>Scenario 1</li><li>Scenario 2</li></ul>");
        expectLastCall().once();
        Grid<UsageBatch> grid = createMock(Grid.class);
        Whitebox.setInternalState(window, grid);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID))
            .andReturn(List.of("Scenario 1", "Scenario 2")).once();
        replay(Windows.class, grid, controller);
        getDeleteButton().click();
        verify(Windows.class, grid, controller);
    }

    @Test
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Grid<?> grid = createMock(Grid.class);
        Whitebox.setInternalState(window, searchWidget);
        Whitebox.setInternalState(window, grid);
        expect(grid.getDataProvider()).andReturn(new ListDataProvider(List.of())).once();
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        replay(searchWidget, grid);
        window.performSearch();
        verify(searchWidget, grid);
    }

    private void verifyGrid(Grid<UsageBatch> grid) {
        UiTestHelper.verifyGrid(grid, List.of(
            Pair.of("Usage Batch Name", "200px"),
            Pair.of("Period End Date", "155px"),
            Pair.of("Number of Baseline Years", "180px"),
            Pair.of("Created By", "330px"),
            Pair.of("Created Date", "150px")));
        assertEquals(5, grid.getColumns().size());
        new GridColumnVerifier<>(grid, 0, UsageBatch::new, UsageBatch::setName)
            .verifyComparator("Usage Batch 1", "USAGE BATCH 2")
            .verifyClassNameGenerator(null);
        new GridColumnVerifier<>(grid, 1, UsageBatch::new, UsageBatch::setPaymentDate)
            .verifyDataProvider(LocalDate.of(2020, 1, 2), "01/02/2020")
            .verifyDataProvider(null, StringUtils.EMPTY)
            .verifyComparator(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2))
            .verifyClassNameGenerator(null);
        new GridColumnVerifier<>(grid, 2, UsageBatch::new, UsageBatch::setNumberOfBaselineYears)
            .verifyDataProvider(1, "1")
            .verifyComparator(1, 2)
            .verifyClassNameGenerator(null);
        new GridColumnVerifier<>(grid, 3, UsageBatch::new, UsageBatch::setCreateUser)
            .verifyComparator("user1", "USER2")
            .verifyClassNameGenerator(null);
        new GridColumnVerifier<>(grid, 4, UsageBatch::new, UsageBatch::setCreateDate)
            .verifyDataProvider(new Date(0L), "12/31/1969 7:00 PM")
            .verifyComparator(new Date(1), new Date(2))
            .verifyClassNameGenerator(null);
    }

    private Button getDeleteButton() {
        return getButton(window, "Delete");
    }

    private UsageBatch buildUsageBatch() {
        var usageBatch = new UsageBatch();
        usageBatch.setId(USAGE_BATCH_ID);
        usageBatch.setName(USAGE_BATCH_NAME);
        return usageBatch;
    }
}
