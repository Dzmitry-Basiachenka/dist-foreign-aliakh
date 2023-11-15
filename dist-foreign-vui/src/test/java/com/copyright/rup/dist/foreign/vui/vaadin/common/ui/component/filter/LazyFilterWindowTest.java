package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifySize;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.Query;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Verifies {@link LazyFilterWindow}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 05/02/2018
 *
 * @author Ihar Suvorau
 */
public class LazyFilterWindowTest {

    private static final String CAPTION = "Test filter";
    private final ILazyFilterWindowController<Rightsholder> controller = createMock(ILazyFilterWindowController.class);
    private LazyFilterWindow<Rightsholder> filterWindow;

    @Test
    public void testWindowStructure() {
        expect(controller.getGridColumnValueProvider()).andReturn(s -> "12345678: Rightsholder").once();
        replay(controller);
        filterWindow = new LazyFilterWindow<>(CAPTION, controller);
        assertEquals("Test filter", filterWindow.getHeaderTitle());
        verifyWindow(filterWindow, CAPTION, "550.0px", "530.0px", Unit.PIXELS, false);
        VerticalLayout content = (VerticalLayout) getDialogContent(filterWindow);
        assertFalse(content.isSpacing());
        assertFalse(content.isMargin());
        Component component = content.getComponentAt(0);
        assertThat(component, instanceOf(SearchWidget.class));
        verifyGrid(content.getComponentAt(1));
        verifyButtonsLayout(getFooterLayout(filterWindow), true, "Save", "Clear", "Close");
        verify(controller);
    }

    @Test
    public void testGetSelectedItemsIds() {
        expect(controller.getGridColumnValueProvider()).andReturn(s -> "12345679: Rightsholder").once();
        expect(controller.getBeansCount(null)).andReturn(1).once();
        replay(controller);
        filterWindow = new LazyFilterWindow<>(CAPTION, controller);
        assertTrue(filterWindow.getSelectedItemsIds().isEmpty());
        Set<Rightsholder> selectedItemsIds = Set.of(buildRightsholder());
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        assertEquals(selectedItemsIds, filterWindow.getSelectedItemsIds());
        verify(controller);
    }

    @Test
    public void testRefreshDataProvider() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        expect(controller.getGridColumnValueProvider()).andReturn(s -> "12345678: Rightsholder").once();
        String searchValue = "search";
        expect(searchWidget.getSearchValue()).andReturn(searchValue).times(2);
        expect(controller.getBeansCount(searchValue)).andReturn(1).once();
        List<Rightsholder> expectedRightsholders = List.of(buildRightsholder());
        expect(controller.loadBeans(searchValue, 0, 1, List.of())).andReturn(expectedRightsholders).once();
        replay(controller, searchWidget);
        filterWindow = new LazyFilterWindow<>(CAPTION, controller);
        Whitebox.setInternalState(filterWindow, searchWidget);
        CallbackDataProvider<?, ?> dataProvider = Whitebox.getInternalState(filterWindow, "dataProvider");
        assertEquals(1, dataProvider.size(new Query<>()));
        Stream<?> stream = dataProvider.fetch(new Query<>(0, 1, List.of(), null, null));
        assertEquals(expectedRightsholders, stream.collect(Collectors.toList()));
        verify(controller, searchWidget);
    }

    private void verifyGrid(Component component) {
        assertThat(component, instanceOf(Grid.class));
        Grid grid = (Grid) component;
        verifySize(grid, "100%", Unit.PERCENTAGE, "100%", Unit.PERCENTAGE);
        assertEquals("lazy-filter-widget-grid", grid.getId().orElseThrow());
        assertEquals("lazy-filter-widget-grid", grid.getClassName());
        List<Column<Rightsholder>> columns = grid.getColumns();
        assertEquals(1, columns.size());
    }

    private Rightsholder buildRightsholder() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(12345678L);
        rightsholder.setName("Rightsholder");
        return rightsholder;
    }
}
