package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;

import com.copyright.rup.dist.foreign.domain.WithdrawnFundPool;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.impl.DeleteAdditionalFundsWindow.SearchController;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow.IListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collection;
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
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class DeleteAdditionalFundsWindowTest {

    private DeleteAdditionalFundsWindow deleteWindow;
    private WithdrawnFundPool fundPool;

    @Before
    public void setUp() {
        fundPool = new WithdrawnFundPool();
        fundPool.setName("Test Fund");
        IUsagesController controller = createMock(IUsagesController.class);
        expect(controller.getAdditionalFunds()).andReturn(Collections.singletonList(fundPool)).once();
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
    public void testDeleteClickListener() {
        mockStatic(Windows.class);
        Capture<IListener> listenerCapture = new Capture<>();
        Window confirmWindowCapture = PowerMock.createMock(Window.class);
        VerticalLayout content = (VerticalLayout) deleteWindow.getContent();
        Grid grid = (Grid) content.getComponent(1);
        Button button = (Button) grid.getColumn("delete").getValueProvider().apply(fundPool);
        assertEquals("Delete", button.getCaption());
        Collection<?> listeners = button.getListeners(ClickEvent.class);
        assertEquals(1, listeners.size());
        ClickListener listener = (ClickListener) listeners.iterator().next();
        expect(
            Windows.showConfirmDialog(eq("Are you sure you want to delete <i><b>'Test Fund'</b></i> additional fund?"),
                capture(listenerCapture))).andReturn(confirmWindowCapture).once();
        PowerMock.replay(confirmWindowCapture, Windows.class);
        listener.buttonClick(null);
        PowerMock.verify(confirmWindowCapture, Windows.class);
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
