package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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
import com.vaadin.ui.Grid.Column;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        expect(controller.getUdmBatches()).andReturn(Collections.singletonList(buildUdmBatch()));
        replay(controller, ForeignSecurityUtils.class);
        viewUdmBatchWindow = new ViewUdmBatchWindow(controller);
        Whitebox.setInternalState(viewUdmBatchWindow, "grid", udmBatchGrid);
        verify(controller, ForeignSecurityUtils.class);
        reset(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testStructure() {
        assertEquals("View UDM Usage Batch", viewUdmBatchWindow.getCaption());
        verifySize(viewUdmBatchWindow);
        VerticalLayout content = (VerticalLayout) viewUdmBatchWindow.getContent();
        assertEquals(3, content.getComponentCount());
        assertEquals(SearchWidget.class, content.getComponent(0).getClass());
        Component component = content.getComponent(1);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component);
        assertEquals(1, content.getExpandRatio(component), 0);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        assertEquals(2, buttonsLayout.getComponentCount());
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        Button closeButton = (Button) buttonsLayout.getComponent(1);
        assertEquals("Delete", deleteButton.getCaption());
        assertEquals("Close", closeButton.getCaption());
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testDeleteClickListenerInProgressBatch() {
        mockStatic(Windows.class);
        Window confirmWindowCapture = createMock(Window.class);
        Button.ClickListener listener = getDeleteButtonClickListener();
        expect(udmBatchGrid.getSelectedItems()).andReturn(Collections.singleton(buildUdmBatch())).once();
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
        expect(udmBatchGrid.getSelectedItems()).andReturn(Collections.singleton(buildUdmBatch())).once();
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
        expect(udmBatchGrid.getSelectedItems()).andReturn(Collections.singleton(buildUdmBatch())).once();
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
        expect(udmBatchGrid.getDataProvider()).andReturn(new ListDataProvider(Collections.EMPTY_LIST)).once();
        expect(searchWidget.getSearchValue()).andReturn("Search").once();
        udmBatchGrid.recalculateColumnWidths();
        expectLastCall().once();
        replay(searchWidget, udmBatchGrid);
        viewUdmBatchWindow.performSearch();
        verify(searchWidget, udmBatchGrid);
    }

    private void verifySize(Component component) {
        assertEquals(1000, component.getWidth(), 0);
        assertEquals(550, component.getHeight(), 0);
        assertEquals(Sizeable.Unit.PIXELS, component.getHeightUnits());
        assertEquals(Sizeable.Unit.PIXELS, component.getWidthUnits());
    }

    @SuppressWarnings(UNCHECKED)
    private void verifyGrid(Grid grid) {
        assertNull(grid.getCaption());
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Usage Batch Name", "Period", "Usage Origin", "Channel",
            "Created By", "Created Date"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
        assertEquals(Arrays.asList(-1.0, 180.0, 180.0, 120.0, 170.0, 170.0),
            columns.stream().map(Grid.Column::getWidth).collect(Collectors.toList()));
    }

    private Button.ClickListener getDeleteButtonClickListener() {
        VerticalLayout content = (VerticalLayout) viewUdmBatchWindow.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button deleteButton = (Button) buttonsLayout.getComponent(0);
        Collection<?> listeners = deleteButton.getListeners(Button.ClickEvent.class);
        assertEquals(1, CollectionUtils.size(listeners));
        return (Button.ClickListener) listeners.iterator().next();
    }

    private UdmBatch buildUdmBatch() {
        UdmBatch udmBatch = new UdmBatch();
        udmBatch.setId(UDM_BATCH_UID);
        udmBatch.setName(UDM_BATCH_NAME);
        udmBatch.setPeriod(202006);
        udmBatch.setChannel(UdmChannelEnum.CCC);
        udmBatch.setUsageOrigin(UdmUsageOriginEnum.SS);
        return udmBatch;
    }
}
