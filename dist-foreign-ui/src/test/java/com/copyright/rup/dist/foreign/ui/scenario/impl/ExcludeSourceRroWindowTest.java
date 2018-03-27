package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
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
 * Verifies {@link ExcludeSourceRroWindow}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/2/2017
 *
 * @author Uladzislau_Shalamitski
 */
public class ExcludeSourceRroWindowTest {

    private ExcludeSourceRroWindow window;

    @Before
    public void setUp() {
        IScenarioController scenarioController = createMock(ScenarioController.class);
        expect(scenarioController.getSourceRros())
            .andReturn(Arrays.asList(
                buildRightsholder(2000017004L, "Access Copyright, The Canadian Copyright Agency"),
                buildRightsholder(2000017006L, "CAL, Copyright Agency Limited")))
            .once();
        replay(scenarioController);
        window = new ExcludeSourceRroWindow(scenarioController);
        verify(scenarioController);
    }

    @Test
    public void testStructure() {
        assertEquals("Exclude Details by Source RRO", window.getCaption());
        assertEquals(500, window.getHeight(), 0);
        assertEquals(880, window.getWidth(), 0);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        verifyGrid(content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(window, searchWidget);
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(window, grid);
        ListDataProvider provider = new ListDataProvider(Collections.EMPTY_LIST);
        expect(grid.getDataProvider()).andReturn(provider).once();
        expect(searchWidget.getSearchValue()).andReturn("Access").once();
        replay(searchWidget, grid);
        window.performSearch();
        SerializablePredicate filter = provider.getFilter();
        assertTrue(filter.test(buildRightsholder(2000017004L, "Access Copyright, The Canadian Copyright Agency")));
        assertFalse(filter.test(buildRightsholder(2000017006L, "CAL, Copyright Agency Limited")));
        verify(searchWidget, grid);
    }

    private void verifyGrid(Component component) {
        assertEquals(Grid.class, component.getClass());
        Grid grid = (Grid) component;
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Source RRO Account #", "Source RRO Name", StringUtils.EMPTY),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(1, horizontalLayout.getComponentCount());
        Button cancelButton = (Button) horizontalLayout.getComponent(0);
        assertEquals("Cancel", cancelButton.getCaption());
        assertEquals("Cancel", cancelButton.getId());
        assertTrue(horizontalLayout.isSpacing());
        assertEquals(new MarginInfo(false, false, false, false), horizontalLayout.getMargin());
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
