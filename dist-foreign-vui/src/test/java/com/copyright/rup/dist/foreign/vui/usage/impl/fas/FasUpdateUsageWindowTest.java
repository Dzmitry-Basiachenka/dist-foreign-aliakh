package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.AbstractGridMultiSelectionModel;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridMultiSelectionModel.SelectAllCheckboxVisibility;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Verifies {@link FasUpdateUsageWindow}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/09/2023
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class FasUpdateUsageWindowTest {

    private static final String USAGES_GRID = "usagesGrid";
    private static final int RECORD_THRESHOLD = 10000;

    private IFasUsageController controller;
    private FasUpdateUsageWindow window;

    @Before
    public void setUp() {
        controller = createMock(IFasUsageController.class);
        expect(controller.getUsageDtosToUpdate()).andReturn(List.of()).once();
        replay(controller);
        window = new FasUpdateUsageWindow(controller);
        verify(controller);
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        verifyWindow(window, "Update Usages", "1280px", "530px", Unit.PIXELS, true);
        assertEquals("update-usages-window", window.getId().get());
        var contentLayout = (VerticalLayout) getDialogContent(window);
        assertEquals(2, contentLayout.getComponentCount());
        assertEquals(SearchWidget.class, contentLayout.getComponentAt(0).getClass());
        verifyGrid((Grid<?>) contentLayout.getComponentAt(1), List.of(
            Pair.of("Detail ID", "300px"),
            Pair.of("Status", "180px"),
            Pair.of("Usage Batch Name", "200px"),
            Pair.of("Wr Wrk Inst", "140px"),
            Pair.of("System Title", null),
            Pair.of("RH Account #", "170px"),
            Pair.of("RH Name", "300px")
        ));
        verifyButtonsLayout(getFooterLayout(window), true, "Update", "Close");
    }

    @Test
    public void testOnMultipleEditClicked() {
        mockStatic(Windows.class);
        Windows.showModalWindow(anyObject(FasEditMultipleUsagesWindow.class));
        expectLastCall().once();
        Grid<UsageDto> usagesGrid = createMock(Grid.class);
        expect(usagesGrid.getSelectedItems()).andReturn(Set.of(new UsageDto())).once();
        Whitebox.setInternalState(window, USAGES_GRID, usagesGrid);
        var buttonsLayout = (HorizontalLayout) getFooterLayout(window);
        var updateButton = (Button) buttonsLayout.getComponentAt(0);
        replay(Windows.class, usagesGrid);
        updateButton.click();
        verify(Windows.class, usagesGrid);
    }

    @Test
    public void testSelectAllCheckboxVisibilityVisible() {
        expect(controller.getUsageDtosToUpdate()).andReturn(List.of(new UsageDto())).once();
        expect(controller.getRecordsThreshold()).andReturn(RECORD_THRESHOLD).once();
        replay(controller);
        window = new FasUpdateUsageWindow(controller);
        Grid<UsageDto> grid = Whitebox.getInternalState(window, USAGES_GRID);
        var selectionModel = (AbstractGridMultiSelectionModel<UsageDto>) grid.getSelectionModel();
        assertEquals(SelectAllCheckboxVisibility.VISIBLE, selectionModel.getSelectAllCheckboxVisibility());
        verify(controller);
    }

    @Test
    public void testSelectAllCheckboxVisibilityHiddenNoUsages() {
        expect(controller.getUsageDtosToUpdate()).andReturn(List.of()).once();
        replay(controller);
        window = new FasUpdateUsageWindow(controller);
        Grid<UsageDto> grid = Whitebox.getInternalState(window, USAGES_GRID);
        var selectionModel = (AbstractGridMultiSelectionModel<UsageDto>) grid.getSelectionModel();
        assertEquals(SelectAllCheckboxVisibility.HIDDEN, selectionModel.getSelectAllCheckboxVisibility());
        verify(controller);
    }

    @Test
    public void testSelectAllCheckboxVisibilityHiddenUsagesThresholdExceeded() {
        List<UsageDto> usages = IntStream
            .rangeClosed(0, RECORD_THRESHOLD)
            .mapToObj(i -> new UsageDto())
            .collect(Collectors.toList());
        expect(controller.getUsageDtosToUpdate()).andReturn(usages).once();
        expect(controller.getRecordsThreshold()).andReturn(RECORD_THRESHOLD).once();
        replay(controller);
        window = new FasUpdateUsageWindow(controller);
        Grid<UsageDto> grid = Whitebox.getInternalState(window, USAGES_GRID);
        var selectionModel = (AbstractGridMultiSelectionModel<UsageDto>) grid.getSelectionModel();
        assertEquals(SelectAllCheckboxVisibility.HIDDEN, selectionModel.getSelectAllCheckboxVisibility());
        verify(controller);
    }
}
