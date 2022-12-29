package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.MultiSelectionModel.SelectAllCheckBoxVisibility;
import com.vaadin.ui.components.grid.MultiSelectionModelImpl;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link AclciUsageUpdateWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/29/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclciUsageUpdateWindowTest {

    private IAclciUsageController controller;
    private AclciUsageUpdateWindow window;

    @Before
    public void setUp() {
        controller = createMock(IAclciUsageController.class);
        expect(controller.getUsageDtosToUpdate()).andReturn(Collections.emptyList()).once();
        replay(controller);
        window = new AclciUsageUpdateWindow(controller);
        verify(controller);
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        verifyWindow(window, "Update Usages", 1000, 530, Unit.PIXELS);
        assertEquals("update-usages-window", window.getId());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(new MarginInfo(true), content.getMargin());
        assertTrue(content.isSpacing());
        verifySize(content, 100, Unit.PERCENTAGE, 100, Unit.PERCENTAGE);
        assertEquals(3, content.getComponentCount());
        Component searchWidgetComponent = content.getComponent(0);
        assertEquals(SearchWidget.class, searchWidgetComponent.getClass());
        Component gridComponent = content.getComponent(1);
        assertEquals(Grid.class, gridComponent.getClass());
        verifyGrid((Grid) gridComponent);
        assertEquals(1, content.getExpandRatio(gridComponent), 0);
        Component buttonsLayoutComponent = content.getComponent(2);
        assertEquals(HorizontalLayout.class, buttonsLayoutComponent.getClass());
        verifyButtonsLayout((HorizontalLayout) buttonsLayoutComponent);
        assertEquals(Alignment.BOTTOM_RIGHT, content.getComponentAlignment(buttonsLayoutComponent));
    }

    @Test
    public void testOnMultipleEditClicked() {
        //TODO {dbasiachenka} implement
    }

    @Test
    public void testRefreshDataProviderSelectAllCheckBoxVisibilityVisible() {
        expect(controller.getUsageDtosToUpdate()).andReturn(Collections.singletonList(new UsageDto())).once();
        replay(controller);
        window.refreshDataProvider();
        Grid<UsageDto> grid = Whitebox.getInternalState(window, "usagesGrid");
        MultiSelectionModelImpl<UsageDto> selectionModel = (MultiSelectionModelImpl<UsageDto>) grid.getSelectionModel();
        assertEquals(SelectAllCheckBoxVisibility.VISIBLE, selectionModel.getSelectAllCheckBoxVisibility());
        verify(controller);
    }

    @Test
    public void testRefreshDataProviderSelectAllCheckBoxVisibilityHidden() {
        expect(controller.getUsageDtosToUpdate()).andReturn(Collections.emptyList()).once();
        replay(controller);
        window.refreshDataProvider();
        Grid<UsageDto> grid = Whitebox.getInternalState(window, "usagesGrid");
        MultiSelectionModelImpl<UsageDto> selectionModel = (MultiSelectionModelImpl<UsageDto>) grid.getSelectionModel();
        assertEquals(SelectAllCheckBoxVisibility.HIDDEN, selectionModel.getSelectAllCheckBoxVisibility());
        verify(controller);
    }

    private void verifySize(Component component, float width, Unit widthUnit, float height, Unit heightUnit) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(heightUnit, component.getHeightUnits());
        assertEquals(widthUnit, component.getWidthUnits());
    }

    @SuppressWarnings("unchecked")
    private void verifyGrid(Grid grid) {
        assertNull(grid.getCaption());
        verifySize(grid, 100, Unit.PERCENTAGE, 100, Unit.PERCENTAGE);
        List<Grid.Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Detail ID", "Status", "Usage Batch Name", "Wr Wrk Inst", "System Title"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
        assertEquals(250, columns.get(0).getWidth(), 0);
        assertEquals(170, columns.get(1).getWidth(), 0);
        assertEquals(200, columns.get(2).getWidth(), 0);
        assertEquals(130, columns.get(3).getWidth(), 0);
        assertEquals(250, columns.get(4).getWidth(), 0);
    }

    private void verifyButtonsLayout(HorizontalLayout buttonsLayout) {
        assertTrue(buttonsLayout.isSpacing());
        assertEquals(new MarginInfo(false), buttonsLayout.getMargin());
        assertEquals(2, buttonsLayout.getComponentCount());
        assertEquals("Multiple Edit", buttonsLayout.getComponent(0).getCaption());
        assertEquals("Close", buttonsLayout.getComponent(1).getCaption());
    }
}
