package com.copyright.rup.dist.foreign.ui.common;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditFilterController;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.google.common.collect.Sets;

import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Verifies {@link LazyRightsholderFilterWindow}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 05/02/2018
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(JavaScript.class)
public class LazyRightsholderFilterWindowTest {

    private ICommonAuditFilterController controller;
    private LazyRightsholderFilterWindow filterWindow;

    @Before
    public void setUp() {
        controller = createMock(ICommonAuditFilterController.class);
        filterWindow = new LazyRightsholderFilterWindow("Rightsholders", controller);
    }

    @Test
    public void testWindowStructure() {
        assertEquals("Rightsholders filter", filterWindow.getCaption());
        verifySize(filterWindow, 450, Unit.PIXELS, 400, Unit.PIXELS);
        assertFalse(filterWindow.isResizable());
        VerticalLayout content = (VerticalLayout) filterWindow.getContent();
        assertTrue(content.isSpacing());
        assertEquals(new MarginInfo(true), content.getMargin());
        Iterator<Component> iterator = content.iterator();
        Component component = iterator.next();
        assertTrue(component instanceof SearchWidget);
        verifyPanel((Panel) iterator.next());
        verifyButtonsLayout((HorizontalLayout) iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testGetSelectedItemsIds() {
        assertTrue(filterWindow.getSelectedItemsIds().isEmpty());
        HashSet<Rightsholder> selectedItemsIds = Sets.newHashSet();
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        assertEquals(selectedItemsIds, filterWindow.getSelectedItemsIds());
    }

    @Test
    public void testRefreshDataProvider() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(filterWindow, searchWidget);
        List<Rightsholder> rightsholders = Collections.singletonList(new Rightsholder());
        mockStatic(JavaScript.class);
        expect(JavaScript.getCurrent()).andReturn(createMock(JavaScript.class)).times(2);
        expect(searchWidget.getSearchValue()).andReturn(StringUtils.EMPTY).times(2);
        expect(controller.getProductFamily()).andReturn("FAS").times(2);
        expect(controller.getBeansCount("FAS", null)).andReturn(1).once();
        expect(controller.loadBeans("FAS", null, 0, 1, Collections.emptyList()))
            .andReturn(rightsholders).once();
        CallbackDataProvider<?, ?> dataProvider = Whitebox.getInternalState(filterWindow, "dataProvider");
        replay(controller, searchWidget, JavaScript.class);
        assertEquals(1, dataProvider.size(new Query<>()));
        Stream<?> stream = dataProvider.fetch(new Query<>(0, 1, Collections.emptyList(), null, null));
        assertEquals(rightsholders, stream.collect(Collectors.toList()));
        verify(controller, searchWidget, JavaScript.class);
    }

    private void verifyPanel(Panel panel) {
        verifySize(panel, 100, Unit.PERCENTAGE, 100, Unit.PERCENTAGE);
        assertEquals(Cornerstone.LABEL_LIGHT, panel.getStyleName());
        Iterator<Component> iterator = panel.iterator();
        Grid grid = (Grid) iterator.next();
        assertFalse(grid.isHeaderVisible());
        List<Column> columns = grid.getColumns();
        assertEquals(1, columns.size());
        assertFalse(iterator.hasNext());
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertTrue(layout.isSpacing());
        Iterator<Component> iterator = layout.iterator();
        Button button = (Button) iterator.next();
        verifyButton(button, "Save");
        button = (Button) iterator.next();
        verifyButton(button, "Clear");
        verifyButton((Button) iterator.next(), "Close");
        assertFalse(iterator.hasNext());
    }

    private void verifyButton(Button button, String caption) {
        assertEquals(caption, button.getCaption());
        assertEquals(1, button.getListeners(ClickEvent.class).size());
    }

    private void verifySize(Component component, float width, Unit widthUnits, float height, Unit heightUnits) {
        assertEquals(width, component.getWidth(), 0);
        assertEquals(height, component.getHeight(), 0);
        assertEquals(heightUnits, component.getHeightUnits());
        assertEquals(widthUnits, component.getWidthUnits());
    }
}
