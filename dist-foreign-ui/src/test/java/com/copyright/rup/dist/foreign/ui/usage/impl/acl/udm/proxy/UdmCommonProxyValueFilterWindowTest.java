package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.google.common.collect.ImmutableSet;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link UdmCommonProxyValueFilterWindow}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 11/27/21
 *
 * @author Uladzislau Shalamitski
 */
public class UdmCommonProxyValueFilterWindowTest {

    private static final String UNCHECKED = "unchecked";
    private static final String[] PUB_TYPE_CODES = {"BK", "PK", "LM"};

    private UdmCommonProxyValueFilterWindow<String> filterWindow;

    @Before
    @SuppressWarnings(UNCHECKED)
    public void setUp() {
        IFilterWindowController<String> controllerMock = createMock(IFilterWindowController.class);
        expect(controllerMock.loadBeans()).andReturn(List.of(PUB_TYPE_CODES)).once();
        replay(controllerMock);
        filterWindow = new UdmCommonProxyValueFilterWindow<>("Filter window", controllerMock,
            (ValueProvider<String, List<String>>) List::of);
        verify(controllerMock);
        reset(controllerMock);
    }

    @Test
    public void testWindowStructure() {
        verifyWindow(filterWindow, "Filter window", 450, 400, Sizeable.Unit.PIXELS);
        assertFalse(filterWindow.isResizable());
        VerticalLayout content = (VerticalLayout) filterWindow.getContent();
        assertTrue(content.isSpacing());
        assertEquals(new MarginInfo(true), content.getMargin());
        Iterator<Component> iterator = content.iterator();
        Component component = iterator.next();
        assertThat(component, instanceOf(SearchWidget.class));
        verifyPanel((Panel) iterator.next());
        verifyButtonsLayout(iterator.next(), "Save", "Select All", "Clear", "Close");
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testGetSelectedItemsIds() {
        assertTrue(filterWindow.getSelectedItemsIds().isEmpty());
        ImmutableSet<String> selectedItemsIds = ImmutableSet.of("NK", "HL");
        filterWindow.setSelectedItemsIds(selectedItemsIds);
        assertEquals(selectedItemsIds, filterWindow.getSelectedItemsIds());
    }

    @Test
    public void testSetSelectedItemsIdsWithEmptySet() {
        assertTrue(filterWindow.getSelectedItemsIds().isEmpty());
        filterWindow.setSelectedItemsIds(Set.of());
        assertEquals(Set.of(PUB_TYPE_CODES), filterWindow.getSelectedItemsIds());
    }

    @Test
    public void testSetSearchPromptString() {
        String promptString = "Enter some value";
        filterWindow.setSearchPromptString(promptString);
        SearchWidget searchWidget = (SearchWidget) ((VerticalLayout) filterWindow.getContent()).getComponent(0);
        assertEquals(promptString, ((TextField) searchWidget.getComponent(0)).getPlaceholder());
    }

    @SuppressWarnings(UNCHECKED)
    private void verifyPanel(Panel panel) {
        verifyWindow(panel, null, 100, 100, Sizeable.Unit.PERCENTAGE);
        assertEquals(ValoTheme.LABEL_LIGHT, panel.getStyleName());
        Iterator<Component> iterator = panel.iterator();
        CheckBoxGroup<String> optionGroup = (CheckBoxGroup<String>) iterator.next();
        assertTrue(optionGroup.isHtmlContentAllowed());
        assertNull(optionGroup.getCaption());
        assertFalse(iterator.hasNext());
        assertNull(((ListDataProvider<String>) optionGroup.getDataProvider()).getFilter());
    }
}
