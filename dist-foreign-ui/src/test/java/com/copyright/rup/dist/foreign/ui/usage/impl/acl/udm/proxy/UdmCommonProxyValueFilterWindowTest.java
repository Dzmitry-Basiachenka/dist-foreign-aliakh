package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
    private static final String[] PUB_TYPE_CODES = new String[]{"BK", "PK", "LM"};

    private UdmCommonProxyValueFilterWindow<String> filterWindow;

    @Before
    @SuppressWarnings(UNCHECKED)
    public void setUp() {
        IFilterWindowController<String> controllerMock = createMock(IFilterWindowController.class);
        expect(controllerMock.loadBeans()).andReturn(Arrays.asList(PUB_TYPE_CODES)).once();
        replay(controllerMock);
        filterWindow = new UdmCommonProxyValueFilterWindow<>("Filter window", controllerMock,
            (ValueProvider<String, List<String>>) bean -> Collections.singletonList(bean.toString()));
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
        assertTrue(component instanceof SearchWidget);
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
        filterWindow.setSelectedItemsIds(Collections.emptySet());
        assertEquals(Sets.newHashSet(PUB_TYPE_CODES), filterWindow.getSelectedItemsIds());
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
        assertEquals(Cornerstone.LABEL_LIGHT, panel.getStyleName());
        Iterator<Component> iterator = panel.iterator();
        CheckBoxGroup<String> optionGroup = (CheckBoxGroup<String>) iterator.next();
        assertTrue(optionGroup.isHtmlContentAllowed());
        assertNull(optionGroup.getCaption());
        assertFalse(iterator.hasNext());
        assertNull(((ListDataProvider<String>) optionGroup.getDataProvider()).getFilter());
    }
}
