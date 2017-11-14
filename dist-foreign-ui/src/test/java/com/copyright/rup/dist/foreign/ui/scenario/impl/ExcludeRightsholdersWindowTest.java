package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioController;
import com.copyright.rup.vaadin.ui.component.SelectableTable;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.google.common.collect.Lists;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link ExcludeRightsholdersWindow}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/2/2017
 *
 * @author Uladzislau_Shalamitski
 */
public class ExcludeRightsholdersWindowTest {

    private ExcludeRightsholdersWindow window;

    @Before
    public void setUp() {
        IScenarioController scenarioController = createMock(ScenarioController.class);
        expect(scenarioController.getRightsholdersPayeePairs(1000009522L))
            .andReturn(Lists.newArrayList(
                buildRightsholderPayeePair(
                    buildRightsholder(1000033963L, "Alfred R. Lindesmith"),
                    buildRightsholder(2000148821L, "ABR Company, Ltd")),
                buildRightsholderPayeePair(
                    buildRightsholder(7000425474L, "American Dialect Society"),
                    buildRightsholder(2000196395L, "Advance Central Services"))))
            .once();
        replay(scenarioController);
        window = new ExcludeRightsholdersWindow(1000009522L, scenarioController);
        verify(scenarioController);
    }

    @Test
    public void testStructure() {
        assertEquals("Exclude RH Details for Source RRO #: 1000009522", window.getCaption());
        assertEquals(500, window.getHeight(), 0);
        assertEquals(830, window.getWidth(), 0);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        verifyTable(content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2));
    }

    @Test
    public void testContent() {
        VerticalLayout content = (VerticalLayout) window.getContent();
        SelectableTable table = (SelectableTable) content.getComponent(1);
        verifyItem(table.getItem(1000033963L), buildRightsholder(2000148821L, "ABR Company, Ltd"),
            buildRightsholder(1000033963L, "Alfred R. Lindesmith"));
        verifyItem(table.getItem(7000425474L), buildRightsholder(2000196395L, "Advance Central Services"),
            buildRightsholder(7000425474L, "American Dialect Society"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testPerformSearch() {
        BeanContainer<Long, String> container = createMock(BeanContainer.class);
        Whitebox.setInternalState(window, container);
        container.removeAllContainerFilters();
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(window, searchWidget);
        expect(searchWidget.getSearchValue()).andReturn("value").once();
        Capture<Filter> filterCapture = new Capture<>();
        container.addContainerFilter(capture(filterCapture));
        expectLastCall();
        replay(container, searchWidget);
        window.performSearch();
        assertTrue(filterCapture.getValue().appliesToProperty("payee.accountNumber"));
        assertTrue(filterCapture.getValue().appliesToProperty("payee.name"));
        assertTrue(filterCapture.getValue().appliesToProperty("rightsholder.accountNumber"));
        assertTrue(filterCapture.getValue().appliesToProperty("rightsholder.name"));
        verify(container, searchWidget);
    }

    private void verifyTable(Component component) {
        assertEquals(SelectableTable.class, component.getClass());
        SelectableTable table = (SelectableTable) component;
        assertArrayEquals(new Object[]{"selected", "payee.accountNumber", "payee.name", "rightsholder.accountNumber",
            "rightsholder.name"}, table.getVisibleColumns());
        assertArrayEquals(new Object[]{"<p/>", "Payee Account #", "Payee Name", "RH Account #", "RH Name"},
            table.getColumnHeaders());
    }

    private void verifyItem(Item item, Rightsholder payee, Rightsholder rightsholder) {
        assertNotNull(item);
        assertEquals(payee.getAccountNumber(), item.getItemProperty("payee.accountNumber").getValue());
        assertEquals(payee.getName(), item.getItemProperty("payee.name").getValue());
        assertEquals(rightsholder.getAccountNumber(), item.getItemProperty("rightsholder.accountNumber").getValue());
        assertEquals(rightsholder.getName(), item.getItemProperty("rightsholder.name").getValue());
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(3, horizontalLayout.getComponentCount());
        Button confirmButton = (Button) horizontalLayout.getComponent(0);
        assertEquals("Confirm", confirmButton.getCaption());
        assertEquals("Confirm", confirmButton.getId());
        Button clearButton = (Button) horizontalLayout.getComponent(1);
        assertEquals("Clear", clearButton.getCaption());
        assertEquals("Clear", clearButton.getId());
        Button closeButton = (Button) horizontalLayout.getComponent(2);
        assertEquals("Close", closeButton.getCaption());
        assertEquals("Close", closeButton.getId());
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(new MarginInfo(false, false, false, false), horizontalLayout.getMargin());
    }

    private RightsholderPayeePair buildRightsholderPayeePair(Rightsholder rightsholder, Rightsholder payee) {
        RightsholderPayeePair pair = new RightsholderPayeePair();
        pair.setPayee(payee);
        pair.setRightsholder(rightsholder);
        return pair;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
