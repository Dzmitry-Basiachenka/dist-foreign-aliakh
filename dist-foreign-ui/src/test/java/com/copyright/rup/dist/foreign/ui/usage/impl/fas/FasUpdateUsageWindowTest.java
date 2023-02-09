package com.copyright.rup.dist.foreign.ui.usage.impl.fas;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;
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
import com.copyright.rup.dist.foreign.ui.usage.api.fas.IFasUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.MultiSelectionModel.SelectAllCheckBoxVisibility;
import com.vaadin.ui.components.grid.MultiSelectionModelImpl;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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
public class FasUpdateUsageWindowTest {

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
        verifyWindow(window, "Update Usages", 1280, 530, Unit.PIXELS);
        assertEquals("update-usages-window", window.getId());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        assertEquals(SearchWidget.class, content.getComponent(0).getClass());
        verifyGrid((Grid) content.getComponent(1), List.of(
            Triple.of("Detail ID", 250.0, -1),
            Triple.of("Status", 150.0, -1),
            Triple.of("Usage Batch Name", 170.0, -1),
            Triple.of("Wr Wrk Inst", 100.0, -1),
            Triple.of("System Title", 220.0, -1),
            Triple.of("RH Account #", 100.0, -1),
            Triple.of("RH Name", 260.0, -1)
        ));
        verifyButtonsLayout(content.getComponent(2), "Update", "Close");
    }

    @Test
    public void testOnMultipleEditClicked() {
        mockStatic(Windows.class);
        Windows.showModalWindow(anyObject(FasEditMultipleUsagesWindow.class));
        expectLastCall().once();
        Grid<UsageDto> usagesGrid = createMock(Grid.class);
        Whitebox.setInternalState(window, "usagesGrid", usagesGrid);
        VerticalLayout content = (VerticalLayout) window.getContent();
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button updateButton = (Button) buttonsLayout.getComponent(0);
        Collection<?> listeners = updateButton.getListeners(Button.ClickEvent.class);
        assertEquals(1, listeners.size());
        Button.ClickListener listener = (Button.ClickListener) listeners.iterator().next();
        expect(usagesGrid.getSelectedItems()).andReturn(Set.of(new UsageDto())).once();
        replay(Windows.class, usagesGrid);
        listener.buttonClick(null);
        verify(Windows.class, usagesGrid);
    }

    @Test
    public void testRefreshDataProviderSelectAllCheckBoxVisibilityVisible() {
        expect(controller.getUsageDtosToUpdate()).andReturn(List.of(new UsageDto())).once();
        replay(controller);
        window.refreshDataProvider();
        Grid<UsageDto> grid = Whitebox.getInternalState(window, "usagesGrid");
        MultiSelectionModelImpl<UsageDto> selectionModel = (MultiSelectionModelImpl<UsageDto>) grid.getSelectionModel();
        assertEquals(SelectAllCheckBoxVisibility.VISIBLE, selectionModel.getSelectAllCheckBoxVisibility());
        verify(controller);
    }

    @Test
    public void testRefreshDataProviderSelectAllCheckBoxVisibilityHidden() {
        expect(controller.getUsageDtosToUpdate()).andReturn(List.of()).once();
        replay(controller);
        window.refreshDataProvider();
        Grid<UsageDto> grid = Whitebox.getInternalState(window, "usagesGrid");
        MultiSelectionModelImpl<UsageDto> selectionModel = (MultiSelectionModelImpl<UsageDto>) grid.getSelectionModel();
        assertEquals(SelectAllCheckBoxVisibility.HIDDEN, selectionModel.getSelectAllCheckBoxVisibility());
        verify(controller);
    }
}
