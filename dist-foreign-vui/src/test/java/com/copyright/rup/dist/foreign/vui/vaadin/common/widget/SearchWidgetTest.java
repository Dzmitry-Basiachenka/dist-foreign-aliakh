package com.copyright.rup.dist.foreign.vui.vaadin.common.widget;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.vui.UiTestHelper;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

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
    private static final String DEFAULT_WIDTH = "100%";

    private SearchWidget searchWidget;
    private SearchWidget.ISearchController searchController;

    @Before
    public void setUp() {
        searchController = createMock(SearchWidget.ISearchController.class);
    }

    @Test
    public void testConstructor() {
        searchWidget = new SearchWidget(searchController);
        assertNotNull(searchWidget);
        assertEquals(searchController, searchWidget.getController());
        assertEquals(EXPECTED_WIDGET_STYLE, searchWidget.getClassName());
        UiTestHelper.verifyWidth(searchWidget, DEFAULT_WIDTH, Unit.PERCENTAGE);
        verifySearchField(searchWidget.getComponentAt(0), DEFAULT_WIDTH);
        verifyButtons(searchWidget.getComponentAt(1), searchWidget.getComponentAt(2));
    }

    @Test
    public void testConstructorWithArguments() {
        searchWidget = new SearchWidget(searchController, "search prompt", "70%");
        assertNotNull(searchWidget);
        assertEquals(searchController, searchWidget.getController());
        assertEquals(EXPECTED_WIDGET_STYLE, searchWidget.getClassName());
        UiTestHelper.verifyWidth(searchWidget, DEFAULT_WIDTH, Unit.PERCENTAGE);
        verifySearchField(searchWidget.getComponentAt(0), "70%");
        assertEquals("search prompt",
            Whitebox.getInternalState(searchWidget, TextField.class).getPlaceholder());
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

    @Test
    public void testSearchButtonClicked() {
        searchWidget = new SearchWidget(searchController);
        TextField searchField = Whitebox.getInternalState(searchWidget, "searchField");
        searchField.setValue(SEARCH_VALUE);
        Button searchButton = Whitebox.getInternalState(searchWidget, "searchButton");
        searchController.performSearch();
        expectLastCall().once();
        replay(searchController);
        searchButton.click();
        assertEquals(SEARCH_VALUE, searchWidget.getSearchValue());
        verify(searchController);
    }

    private void verifySearchField(Component component, String expectedWidth) {
        assertNotNull(component);
        assertEquals(TextField.class, component.getClass());
        assertEquals(expectedWidth, ((TextField) component).getWidth());
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
