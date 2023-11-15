package com.copyright.rup.dist.foreign.vui.vaadin.common.widget;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link SearchWidget}.
 * <p/>
 * Copyright (C) 2015 copyright.com
 * <p/>
 * Date: 02/24/2015
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SearchWidget.class)
public class SearchWidgetTest {

    private static final String EXPECTED_VALID_VALUE = "Expected Value";
    private static final String SEARCH_VALUE = "search value";
    private static final String EXPECTED_WIDGET_STYLE = "search-toolbar";
    private static final String EXPECTED_WIDGET_WIDTH = "calc(99.9% - 0rem)";

    private SearchWidget searchWidget;
    private SearchWidget.ISearchController searchController;

    @Before
    public void setUp() {
        searchController = createMock(SearchWidget.ISearchController.class);
    }

    @Test
    public void testConstructorWithArgument() {
        searchWidget = new SearchWidget(searchController);
        assertNotNull(searchWidget);
        assertEquals(searchController, searchWidget.getController());
        assertEquals(EXPECTED_WIDGET_WIDTH, searchWidget.getWidth());
        assertEquals(EXPECTED_WIDGET_STYLE, searchWidget.getClassName());
        verifySearchField(searchWidget.getComponentAt(0));
        verifyButtons(searchWidget.getComponentAt(1), searchWidget.getComponentAt(2));
    }

    @Test
    public void testSetPrompt() {
        searchWidget = new SearchWidget(searchController);
        searchWidget.setPrompt(EXPECTED_VALID_VALUE);
        assertEquals(EXPECTED_VALID_VALUE, ((TextField) searchWidget.getComponentAt(0)).getPlaceholder());
    }

    @Test
    public void testClearSearchValue() {
        searchWidget = new SearchWidget(searchController);
        TextField textField = (TextField) searchWidget.getComponentAt(0);
        textField.setValue(EXPECTED_VALID_VALUE);
        searchWidget.clearSearchValue();
        assertEquals(StringUtils.EMPTY, searchWidget.getSearchValue());
    }

    @Test
    public void testGetSearchValueWithoutSearchPerforming() {
        searchWidget = new SearchWidget(searchController);
        TextField textField = (TextField) searchWidget.getComponentAt(0);
        textField.setValue(EXPECTED_VALID_VALUE);
        assertEquals(StringUtils.EMPTY, searchWidget.getSearchValue());
    }

    @Test
    public void testSetSearchValue() {
        searchWidget = new SearchWidget(searchController);
        searchWidget.setSearchValue(SEARCH_VALUE);
        assertEquals(SEARCH_VALUE, searchWidget.getSearchValue());
    }

    private void verifySearchField(Component component) {
        assertNotNull(component);
        assertEquals(TextField.class, component.getClass());
        assertEquals(EXPECTED_WIDGET_WIDTH, ((TextField) component).getWidth());
    }

    private void verifyButtons(Component searchButton, Component clearButton) {
        verifyButtonProperties(searchButton);
        verifyButtonProperties(clearButton);
    }

    private void verifyButtonProperties(Component component) {
        assertNotNull(component);
        assertEquals(Button.class, component.getClass());
    }
}
