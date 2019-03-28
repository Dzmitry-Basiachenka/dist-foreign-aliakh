package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.impl.DeleteAdditionalFundsWindow.SearchController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link DeleteAdditionalFundsWindow}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/28/19
 *
 * @author Ihar Suvorau
 */
public class DeleteAdditionalFundsWindowTest {

    private DeleteAdditionalFundsWindow deleteWindow;

    @Before
    public void setUp() {
        IUsagesController controller = createMock(IUsagesController.class);
        expect(controller.getAdditionalFunds()).andReturn(Collections.emptyList()).once();
        replay(controller);
        deleteWindow = new DeleteAdditionalFundsWindow(controller);
        verify(controller);
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        assertEquals("Delete NTS Pre-Service Funds", deleteWindow.getCaption());
        verifySize(deleteWindow, 700, Unit.PIXELS, 450, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) deleteWindow.getContent();
        assertEquals(new MarginInfo(true), content.getMargin());
        assertTrue(content.isSpacing());
        verifySize(content, 100, Unit.PERCENTAGE, 100, Unit.PERCENTAGE);
        assertEquals(3, content.getComponentCount());
        assertEquals(SearchWidget.class, content.getComponent(0).getClass());
        Component component = content.getComponent(1);
        assertEquals(Grid.class, component.getClass());
        verifyGrid((Grid) component);
        assertEquals(1, content.getExpandRatio(component), 0);
        component = content.getComponent(2);
        assertEquals(Button.class, component.getClass());
        assertEquals("Close", component.getCaption());
        assertEquals(Alignment.MIDDLE_RIGHT, content.getComponentAlignment(component));
    }

    @Test
    public void testSearchController() {
        SearchController searchController = deleteWindow.new SearchController();
        SearchWidget searchWidget = Whitebox.getInternalState(deleteWindow, "searchWidget");
        searchWidget.setSearchValue("value");
        Grid grid = Whitebox.getInternalState(deleteWindow, "grid");
        ListDataProvider dataProvider = (ListDataProvider) grid.getDataProvider();
        assertNull(dataProvider.getFilter());
        searchController.performSearch();
        assertNotNull(dataProvider.getFilter());
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
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Fund Name", "Fund Amount", "Create User", StringUtils.EMPTY),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        assertEquals(100, columns.get(1).getWidth(), 0);
        assertEquals(140, columns.get(2).getWidth(), 0);
        assertEquals(90, columns.get(3).getWidth(), 0);
    }
}
