package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getButtonFromFooter;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getCheckboxGroup;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifySearchWidget;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifySize;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.reset;

import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Verifies {@link FilterWindow}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 1/16/17
 *
 * @author Aliaksandr Radkevich
 */
public class FilterWindowTest {

    private static final String CAPTION = "Caption";
    private static final String ACL = "ACL";
    private static final String FAS = "FAS";
    private static final String DPS = "DPS";

    private FilterWindow<EntityMock> filterWindow;

    @Before
    public void setUp() {
        setUpMock(createMock(IFilterWindowController.class),
            controller -> new FilterWindow<>(CAPTION, controller,
                (ValueProvider<EntityMock, List<String>>) bean -> Collections.singletonList(bean.getName())));
    }

    @Test
    public void testWindowStructure() {
        assertEquals(CAPTION, filterWindow.getHeaderTitle());
        verifyWindow(filterWindow, CAPTION, "450px", "400px", Unit.PIXELS, false);
        assertFalse(filterWindow.isResizable());
        VerticalLayout content = (VerticalLayout) getDialogContent(filterWindow);
        assertTrue(content.isSpacing());
        Component component = content.getComponentAt(0);
        verifySearchWidget(component, "Enter search value", "100%");
        verifyPanel((Scroller) content.getComponentAt(1));
        verifyButtonsLayout(getFooterLayout(filterWindow), true, "Save", "Select All", "Clear", "Close");
    }

    @Test
    public void testWindowStructureWithButtonCaptions() {
        setUpMock(createMock(IFilterWindowController.class),
            controller -> new FilterWindow<>(CAPTION, controller,
                "Continue", "Reset",
                (ValueProvider<EntityMock, List<String>>) bean -> Collections.singletonList(bean.getName())));
        assertEquals(CAPTION, filterWindow.getHeaderTitle());
        assertFalse(filterWindow.isResizable());
        VerticalLayout content = (VerticalLayout) getDialogContent(filterWindow);
        verifySize(content, "100%", Unit.PERCENTAGE, "100%", Unit.PERCENTAGE);
        assertTrue(content.isSpacing());
        Component component = content.getComponentAt(0);
        assertThat(component, instanceOf(SearchWidget.class));
        verifyPanel((Scroller) content.getComponentAt(1));
        verifyButtonsLayout(getFooterLayout(filterWindow), true, "Continue", "Select All", "Reset", "Close");
    }

    @Test
    public void testSetSearchPromptString() {
        String promptString = "new prompt value";
        filterWindow.setSearchPromptString(promptString);
        SearchWidget searchWidget = (SearchWidget) ((VerticalLayout) getDialogContent(filterWindow)).getComponentAt(0);
        TextField textField = (TextField) searchWidget.getComponentAt(0);
        assertEquals(promptString, textField.getPlaceholder());
    }

    @Test
    public void testPerformSearch() {
        ListDataProvider<EntityMock> listDataProvider = createMock(ListDataProvider.class);
        SearchWidget searchWidgetMock = createMock(SearchWidget.class);
        Whitebox.setInternalState(filterWindow, "searchWidget", searchWidgetMock);
        Whitebox.setInternalState(filterWindow, "listDataProvider", listDataProvider);
        listDataProvider.clearFilters();
        expectLastCall().once();
        expect(searchWidgetMock.getSearchValue()).andReturn("o").once();
        replay(listDataProvider, searchWidgetMock);
        filterWindow.performSearch();
        verify(listDataProvider, searchWidgetMock);
    }

    @Test
    public void testPerformSearchEmptySearchValue() {
        ListDataProvider<EntityMock> listDataProvider = createMock(ListDataProvider.class);
        SearchWidget searchWidgetMock = createMock(SearchWidget.class);
        Whitebox.setInternalState(filterWindow, "searchWidget", searchWidgetMock);
        Whitebox.setInternalState(filterWindow, "listDataProvider", listDataProvider);
        listDataProvider.clearFilters();
        expectLastCall().once();
        expect(searchWidgetMock.getSearchValue()).andReturn(StringUtils.EMPTY).once();
        replay(listDataProvider, searchWidgetMock);
        filterWindow.performSearch();
        verify(listDataProvider, searchWidgetMock);
    }

    @Test
    public void testSaveButtonClickListener() {
        IFilterWindowController<String> controller = createMock(IFilterWindowController.class);
        expect(controller.loadBeans()).andReturn(Arrays.asList(FAS, DPS, ACL)).once();
        expect(controller.getBeanItemCaption(FAS)).andReturn(FAS).times(3);
        expect(controller.getBeanItemCaption(DPS)).andReturn(DPS).times(3);
        expect(controller.getBeanItemCaption(ACL)).andReturn(ACL).times(3);
        replay(controller);
        FilterWindow<String> window = new FilterWindow<>("Filter window", controller) {

            @Override
            protected void fireEvent(ComponentEvent event) {
                assertEquals(FilterSaveEvent.class, event.getClass());
                FilterSaveEvent filterSaveEvent = (FilterSaveEvent) event;
                assertNotNull(event);
                assertEquals(Set.of(FAS, DPS), filterSaveEvent.getSelectedItemsIds());
            }
        };
        window.getCheckBoxGroup().select(FAS, DPS);
        Button saveButton = getButtonFromFooter(window, 0);
        saveButton.click();
        verify(controller);
    }

    private void setUpMock(IFilterWindowController<EntityMock> controller,
                       Function<IFilterWindowController<EntityMock>, FilterWindow<EntityMock>> filterWindowFunction) {
        EntityMock one = new EntityMock("One");
        EntityMock two = new EntityMock("Two");
        EntityMock three = new EntityMock(null);
        expect(controller.loadBeans()).andReturn(Arrays.asList(one, two, three)).once();
        expect(controller.getBeanItemCaption(one)).andReturn(ACL).times(2);
        expect(controller.getBeanItemCaption(two)).andReturn(ACL).times(2);
        expect(controller.getBeanItemCaption(three)).andReturn(ACL).times(2);
        replay(controller);
        filterWindow = filterWindowFunction.apply(controller);
        verify(controller);
        reset(controller);
    }

    private void verifyPanel(Scroller panel) {
        CheckboxGroup checkBoxGroup = getCheckboxGroup(panel);
        assertNotNull(checkBoxGroup.getItemLabelGenerator());
        assertNotNull(checkBoxGroup.getItemEnabledProvider());
        assertNotNull(checkBoxGroup.getDataProvider());
    }

    private static class EntityMock {

        private String name;

        /**
         * Constructor.
         *
         * @param name name
         */
        EntityMock(String name) {
            this.name = name;
        }

        /**
         * @return name.
         */
        public String getName() {
            return name;
        }

        /**
         * Sets name.
         *
         * @param name name
         */
        public void setName(String name) {
            this.name = name;
        }
    }
}
