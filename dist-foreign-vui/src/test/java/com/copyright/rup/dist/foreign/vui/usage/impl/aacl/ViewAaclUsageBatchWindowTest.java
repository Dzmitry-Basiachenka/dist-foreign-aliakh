package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.vui.IVaadinJsonConverter.assertJsonSnapshot;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGrid;
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
import com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder;
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

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

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
public class ViewAaclUsageBatchWindowTest implements IVaadinComponentFinder {

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
        replay(controller, ForeignSecurityUtils.class);
        window = new ViewAaclUsageBatchWindow(controller);
        verify(controller, ForeignSecurityUtils.class);
        reset(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testStructure() {
        replay(controller, ForeignSecurityUtils.class);
        verifyWindow(window, "View Usage Batch", "800px", "550px", Unit.PIXELS, true);
        var content = (VerticalLayout) getDialogContent(window);
        assertEquals(2, content.getComponentCount());
        verifySearchWidget(content.getComponentAt(0), "Enter Batch Name or Period End Date (mm/dd/yyyy)");
        verifyGrid((Grid<?>) content.getComponentAt(1), List.of(
            Pair.of("Usage Batch Name", null),
            Pair.of("Period End Date", "120px"),
            Pair.of("Number of Baseline Years", "180px"),
            Pair.of("Created By", "170px"),
            Pair.of("Created Date", "170px")));
        verifyButtonsLayout(getFooterLayout(window), true, "Delete", "Close");
    }

    @Test
    public void testJsonSnapshot() {
        assertJsonSnapshot("usage/impl/aacl/view-aacl-usage-batch-window.json", window);
    }

    @Test
    public void testDeleteClickListener() {
        mockStatic(Windows.class);
        Dialog confirmWindowCapture = createMock(Dialog.class);
        expect(Windows.showConfirmDialog(eq("Are you sure you want to delete <i><b>'Usage Batch'</b></i> usage batch?"),
            anyObject())).andReturn(confirmWindowCapture).once();
        Grid<UsageBatch> grid = createMock(Grid.class);
        Whitebox.setInternalState(window, "grid", grid);
        expect(grid.getSelectedItems()).andReturn(Set.of(buildUsageBatch())).once();
        expect(controller.getAdditionalFundNamesByUsageBatchId(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.getScenariosNamesAssociatedWithUsageBatch(USAGE_BATCH_ID)).andReturn(List.of()).once();
        expect(controller.isBatchProcessingCompleted(USAGE_BATCH_ID)).andReturn(true).once();
        replay(Windows.class, confirmWindowCapture, grid, controller);
        getDeleteButton().click();
        verify(Windows.class, confirmWindowCapture, grid, controller);
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

    private Button getDeleteButton() {
        var buttonsLayout = getFooterLayout(window);
        var deleteButton = (Button) buttonsLayout.getComponentAt(0);
        assertEquals("Delete", deleteButton.getText());
        return deleteButton;
    }

    private UsageBatch buildUsageBatch() {
        var usageBatch = new UsageBatch();
        usageBatch.setId(USAGE_BATCH_ID);
        usageBatch.setName(USAGE_BATCH_NAME);
        return usageBatch;
    }
}
