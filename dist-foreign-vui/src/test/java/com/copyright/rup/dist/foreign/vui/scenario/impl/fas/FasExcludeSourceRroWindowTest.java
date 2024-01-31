package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterComponent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifySize;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWidth;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializablePredicate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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

    private final List<Rightsholder> rightsholders = List.of(
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
        verifyWindow(window, "Exclude Details by Source RRO", "880px", "500px", Unit.PIXELS, true);
        var content = (VerticalLayout) getDialogContent(window);
        assertEquals(2, content.getComponentCount());
        verifySearchWidget(content.getComponentAt(0));
        verifyGrid(content.getComponentAt(1));
        verifyButton(getFooterComponent(window, 1), "Cancel", true);
    }

    @Test
    public void testGridValues() {
        Grid<?> grid = (Grid<?>) ((VerticalLayout) getDialogContent(window)).getComponentAt(1);
        Object[][] expectedCells = {
            {"2000017004", "Access Copyright, The Canadian Copyright Agency", "Exclude"},
            {"2000017006", "CAL, Copyright Agency Limited", "Exclude"}
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

    private void verifySearchWidget(Component component) {
        assertNotNull(component);
        assertThat(component, instanceOf(SearchWidget.class));
        var searchWidget = (SearchWidget) component;
        TextField textField = Whitebox.getInternalState(searchWidget, TextField.class);
        verifyWidth(textField, "70%", Unit.PERCENTAGE);
        assertEquals("Enter Source RRO Name/Account #", textField.getPlaceholder());
    }

    private void verifyGrid(Component component) {
        assertNotNull(component);
        assertThat(component, instanceOf(Grid.class));
        Grid<Rightsholder> grid = (Grid<Rightsholder>) component;
        assertEquals("exclude-details-by-rro-grid", grid.getId().orElseThrow());
        assertEquals("exclude-details-by-rro-grid", grid.getClassName());
        verifySize(grid, "100%", Unit.PERCENTAGE, "100%", Unit.PERCENTAGE);
        UiTestHelper.verifyGrid(grid, List.of(
            Pair.of("Source RRO Account #", null),
            Pair.of("Source RRO Name", null),
            Pair.of(StringUtils.EMPTY, "95px")
        ));
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        var rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }
}
