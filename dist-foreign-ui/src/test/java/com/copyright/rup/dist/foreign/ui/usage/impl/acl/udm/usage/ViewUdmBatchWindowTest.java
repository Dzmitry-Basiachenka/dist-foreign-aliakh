package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link ViewUdmBatchWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/30/21
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class, ViewUdmBatchWindow.class})
public class ViewUdmBatchWindowTest {

    private static final String UDM_BATCH_UID = "ae13d4ff-e018-478d-b51b-d38a73699027";
    private static final String UDM_BATCH_NAME = "UDM Batch 2021 June";
    private static final String UNCHECKED = "unchecked";
    private ViewUdmBatchWindow viewUdmBatchWindow;
    private Grid<UdmBatch> udmBatchGrid;
    private IUdmUsageController controller;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IUdmUsageController.class);
        udmBatchGrid = createMock(Grid.class);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(true).once();
        expect(controller.getUdmBatches()).andReturn(List.of(buildUdmBatch()));
        replay(controller, ForeignSecurityUtils.class);
        viewUdmBatchWindow = new ViewUdmBatchWindow(controller);
        Whitebox.setInternalState(viewUdmBatchWindow, "grid", udmBatchGrid);
        verify(controller, ForeignSecurityUtils.class);
        reset(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testGridValues() {
        Grid grid = (Grid) ((VerticalLayout) viewUdmBatchWindow.getContent()).getComponent(1);
        Object[][] expectedCells = {
            {UDM_BATCH_NAME, 202006, UdmUsageOriginEnum.SS, UdmChannelEnum.CCC, "user@copyright.com",
                "09/01/2022 12:00 AM"}
        };
        verifyGridItems(grid, List.of(buildUdmBatch()), expectedCells);
    }

    @Test
    public void testStructure() {
        verifyWindow(viewUdmBatchWindow, "View UDM Usage Batch", 1000, 550, Sizeable.Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) viewUdmBatchWindow.getContent();
        assertEquals(3, content.getComponentCount());
        assertThat(content.getComponent(0), instanceOf(SearchWidget.class));
        Component component = content.getComponent(1);
        assertThat(component, instanceOf(Grid.class));
        Grid grid = (Grid) component;
        verifyGrid(grid, Arrays.asList(
            Triple.of("Usage Batch Name", -1.0, 1),
            Triple.of("Period", 180.0, -1),
            Triple.of("Usage Origin", 180.0, -1),
            Triple.of("Channel", 120.0, -1),
            Triple.of("Created By", 170.0, -1),
            Triple.of("Created Date", 170.0, -1)
        ));
        verifyButtonsLayout(content.getComponent(2), "Delete", "Close");
        assertEquals("view-udm-batch-window", viewUdmBatchWindow.getStyleName());
        assertEquals("view-udm-batch-window", viewUdmBatchWindow.getId());
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteClickListenerInProgressBatch() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(udmBatchGrid.getSelectedItems()).andReturn(Set.of(buildUdmBatch())).once();
        expect(controller.isUdmBatchProcessingCompleted(UDM_BATCH_UID)).andReturn(false).once();
        Windows.showNotificationWindow(
            "'UDM Batch 2021 June' batch cannot be deleted because processing is not completed yet");
        expectLastCall().once();
        replay(controller, confirmWindowCapture, udmBatchGrid, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, udmBatchGrid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteClickListenerBatchHasBaselineUsages() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(udmBatchGrid.getSelectedItems()).andReturn(Set.of(buildUdmBatch())).once();
        expect(controller.isUdmBatchProcessingCompleted(UDM_BATCH_UID)).andReturn(true).once();
        expect(controller.isUdmBatchContainsBaselineUsages(UDM_BATCH_UID)).andReturn(true).once();
        Windows.showNotificationWindow(
            "'UDM Batch 2021 June' batch cannot be deleted because the batch contains usage(s) published to baseline");
        expectLastCall().once();
        replay(controller, confirmWindowCapture, udmBatchGrid, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, udmBatchGrid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteClickListener() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(udmBatchGrid.getSelectedItems()).andReturn(Set.of(buildUdmBatch())).once();
        expect(controller.isUdmBatchProcessingCompleted(UDM_BATCH_UID)).andReturn(true).once();
        expect(controller.isUdmBatchContainsBaselineUsages(UDM_BATCH_UID)).andReturn(false).once();
        expect(Windows.showConfirmDialog(
            eq("Are you sure you want to delete <i><b>'UDM Batch 2021 June'</b></i> UDM batch?"),
            anyObject())).andReturn(confirmWindowCapture).once();
        replay(controller, confirmWindowCapture, udmBatchGrid, Windows.class);
        listener.buttonClick(null);
        verify(controller, confirmWindowCapture, udmBatchGrid, Windows.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteButtonEnabled() {
        VerticalLayout content = (VerticalLayout) viewUdmBatchWindow.getContent();
        Grid<UdmBatch> grid = (Grid<UdmBatch>) content.getComponent(1);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        assertFalse(deleteButton.isEnabled());
        grid.select(buildUdmBatch());
        assertTrue(deleteButton.isEnabled());
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(viewUdmBatchWindow, searchWidget);
        expect(udmBatchGrid.getDataProvider()).andReturn(new ListDataProvider(List.of())).once();
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        udmBatchGrid.recalculateColumnWidths();
        expectLastCall().once();
        replay(searchWidget, udmBatchGrid);
        viewUdmBatchWindow.performSearch();
        verify(searchWidget, udmBatchGrid);
    }

    private Button.ClickListener getDeleteButtonClickListener() {
        VerticalLayout content = (VerticalLayout) viewUdmBatchWindow.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        Collection<?> listeners = deleteButton.getListeners(Button.ClickEvent.class);
        assertEquals(2, CollectionUtils.size(listeners));
        return (Button.ClickListener) listeners.iterator().next();
    }

    private UdmBatch buildUdmBatch() {
        UdmBatch udmBatch = new UdmBatch();
        udmBatch.setId(UDM_BATCH_UID);
        udmBatch.setName(UDM_BATCH_NAME);
        udmBatch.setPeriod(202006);
        udmBatch.setUsageOrigin(UdmUsageOriginEnum.SS);
        udmBatch.setChannel(UdmChannelEnum.CCC);
        udmBatch.setCreateUser("user@copyright.com");
        udmBatch.setCreateDate(Date.from(LocalDate.of(2022, 9, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return udmBatch;
    }
}
