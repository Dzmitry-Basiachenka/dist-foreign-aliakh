package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;


/**
 * Verifies {@link FasExcludeSourceRroWindow}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/2/2017
 *
 * @author Uladzislau_Shalamitski
 */
public class FasExcludeSourceRroWindowTest {

    private final List<Rightsholder> rightsholders = Arrays.asList(
        buildRightsholder(2000017004L, "Access Copyright, The Canadian Copyright Agency"),
        buildRightsholder(2000017006L, "CAL, Copyright Agency Limited"));

    private FasExcludeSourceRroWindow window;

    @Before
    public void setUp() {
        IFasScenarioController scenarioController = createMock(IFasScenarioController.class);
        expect(scenarioController.getSourceRros()).andReturn(rightsholders).once();
        replay(scenarioController);
        window = new FasExcludeSourceRroWindow(scenarioController);
        verify(scenarioController);
    }

    @Test
    public void testStructure() {
        verifyWindow(window, "Exclude Details by Source RRO", 880, 500, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertEquals(3, content.getComponentCount());
        assertEquals(Grid.class, content.getComponent(1).getClass());
        Grid grid = (Grid) (content.getComponent(1));
        verifyGrid(grid, Arrays.asList(
            Triple.of("Source RRO Account #", -1.0, 2),
            Triple.of("Source RRO Name", -1.0, 4),
            Triple.of(StringUtils.EMPTY, 95.0, -1)
        ));
        verifyButtonsLayout(content.getComponent(2), "Cancel");
    }

    @Test
    public void testGridValues() {
        Grid<?> grid = (Grid<?>) ((VerticalLayout) window.getContent()).getComponent(1);
        Object[][] expectedCells = {
            {2000017004L, "Access Copyright, The Canadian Copyright Agency", "Exclude"},
            {2000017006L, "CAL, Copyright Agency Limited", "Exclude"}
        };
        verifyGridItems(grid, rightsholders, expectedCells);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testPerformSearch() {
        SearchWidget searchWidget = createMock(SearchWidget.class);
        Whitebox.setInternalState(window, searchWidget);
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(window, grid);
        ListDataProvider provider = new ListDataProvider(List.of());
        expect(grid.getDataProvider()).andReturn(provider).once();
        expect(searchWidget.getSearchValue()).andReturn("Access").once();
        replay(searchWidget, grid);
        window.performSearch();
        SerializablePredicate filter = provider.getFilter();
        assertTrue(filter.test(buildRightsholder(2000017004L, "Access Copyright, The Canadian Copyright Agency")));
        assertFalse(filter.test(buildRightsholder(2000017006L, "CAL, Copyright Agency Limited")));
        verify(searchWidget, grid);
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
